package com.rentit.sales.rest;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rentit.RentitApplication;
import com.rentit.common.application.dto.BusinessPeriodDTO;
import com.rentit.inventory.application.dto.PlantInventoryEntryDTO;
import com.rentit.inventory.application.dto.PlantInventoryItemDTO;
import com.rentit.inventory.application.dto.PlantReservationDTO;
import com.rentit.inventory.domain.repository.PlantInventoryEntryRepository;
import com.rentit.sales.application.dto.PurchaseOrderDTO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import static com.rentit.sales.domain.model.POStatus.ACCEPTED;
import static com.rentit.sales.domain.model.POStatus.CANCELLED;
import static com.rentit.sales.domain.model.POStatus.PENDING_APPROVAL;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.text.IsEmptyString.isEmptyOrNullString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = RentitApplication.class) // Check if the name of this class is correct or not
@WebAppConfiguration
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class SalesRestControllerTests {
    @Autowired
    PlantInventoryEntryRepository repo;

    @Autowired
    private WebApplicationContext wac;
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    @Sql("/plants-dataset.sql")
    public void testFindAvailablePlants() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/plants?name=Exc&startDate=2017-04-14&endDate=2017-04-25"))
                .andExpect(status().isOk())
                .andExpect(header().string("Location", isEmptyOrNullString()))
                .andReturn();

        List<PlantInventoryEntryDTO> plants = mapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<PlantInventoryEntryDTO>>() {});

        assertThat(plants.size()).isEqualTo(3);
    }

    @Test
    @Sql("/plants-dataset.sql")
    public void testCreatePODefaultScenario() throws Exception {
        PurchaseOrderDTO order = new PurchaseOrderDTO();

        PlantInventoryEntryDTO plantToBeReserved = findAnyPlant();

        order.setPlant(plantToBeReserved);

        order.setRentalPeriod(BusinessPeriodDTO.of(LocalDate.now(), LocalDate.now().plusWeeks(1)));

        MvcResult createdPOAsMvcResult = mockMvc.perform(post("/api/orders").content(mapper.writeValueAsString(order)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        PurchaseOrderDTO createdPO = mapper.readValue(createdPOAsMvcResult.getResponse().getContentAsString(), new TypeReference<PurchaseOrderDTO>() {});

        assertNotNull(createdPO);
        assertNotNull(createdPO.get_id());
        assertNull(createdPO.getTotal());
        assertNotNull(createdPO.getStatus());
        assertEquals(PENDING_APPROVAL, createdPO.getStatus());

        // check reference to a plant inventory entry
        assertNotNull(createdPO.getPlant());
        assertNotNull(createdPO.getPlant().get_id());
        assertNotNull(createdPO.getPlant().getName());
        assertEquals(plantToBeReserved.get_id(), createdPO.getPlant().get_id());
        assertEquals(plantToBeReserved.getName(), createdPO.getPlant().getName());

        // check rental period
        assertNotNull(createdPO.getRentalPeriod());
        assertNotNull(createdPO.getRentalPeriod().getStartDate());
        assertNotNull(createdPO.getRentalPeriod().getEndDate());
        // start < end date
        assertThat(createdPO.getRentalPeriod().getEndDate().isAfter(createdPO.getRentalPeriod().getStartDate()));
        // period must be in the future
        assertThat(createdPO.getRentalPeriod().getStartDate().isAfter(LocalDate.now().minusDays(1)));
    }

    @Test
    @Sql("/plants-dataset.sql")
    public void testCancelPODefaultScenario() throws Exception {
        PurchaseOrderDTO order = new PurchaseOrderDTO();

        PlantInventoryEntryDTO plantToBeReserved = findAnyPlant();

        order.setPlant(plantToBeReserved);

        order.setRentalPeriod(BusinessPeriodDTO.of(LocalDate.now(), LocalDate.now().plusWeeks(1)));

        MvcResult createdPOAsMvcResult = mockMvc.perform(post("/api/orders").content(mapper.writeValueAsString(order)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        PurchaseOrderDTO createdPO = mapper.readValue(createdPOAsMvcResult.getResponse().getContentAsString(), new TypeReference<PurchaseOrderDTO>() {});

        Long itemId = findAnyItemForPlant(plantToBeReserved.get_id(), order.getRentalPeriod());

        MvcResult canceledPOAsMvcResult = mockMvc.perform(post("/api/orders/" + createdPO.get_id() + "/cancel").content(mapper.writeValueAsString(itemId)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        PurchaseOrderDTO canceledPO = mapper.readValue(canceledPOAsMvcResult.getResponse().getContentAsString(), new TypeReference<PurchaseOrderDTO>() {});

        assertNotNull(canceledPO);
        assertNotNull(canceledPO.get_id());
        assertEquals(createdPO.get_id(), canceledPO.get_id());
        assertNotNull(canceledPO.getStatus());
        assertEquals(CANCELLED, canceledPO.getStatus());
    }

/*    @Test
    @Sql("/plants-dataset.sql")
    public void testCancelPORejectedScenario() throws Exception {

        PurchaseOrderDTO order = new PurchaseOrderDTO();

        PlantInventoryEntryDTO plantToBeReserved = findAnyPlant();

        order.setPlant(plantToBeReserved);

        order.setRentalPeriod(BusinessPeriodDTO.of(LocalDate.now(), LocalDate.now().plusWeeks(1)));

        MvcResult createdPOAsMvcResult = mockMvc.perform(post("/api/sales/orders").content(mapper.writeValueAsString(order)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        PurchaseOrderDTO createdPO = mapper.readValue(createdPOAsMvcResult.getResponse().getContentAsString(), new TypeReference<PurchaseOrderDTO>() {});

        Long itemId = findAnyItemForPlant(plantToBeReserved.get_id(), order.getRentalPeriod());

        MvcResult acceptedPOAsMvcResult = mockMvc.perform(post("/api/sales/orders/" + createdPO.get_id() + "/accept").content(mapper.writeValueAsString(itemId)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        PurchaseOrderDTO acceptedPO = mapper.readValue(acceptedPOAsMvcResult.getResponse().getContentAsString(), new TypeReference<PurchaseOrderDTO>() {});

        MvcResult dispatchedPOAsMvcResult = mockMvc.perform(post("/api/sales/orders/" + acceptedPO.get_id() + "/dispatch").content(mapper.writeValueAsString(itemId)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        PurchaseOrderDTO dispatchedPO = mapper.readValue(dispatchedPOAsMvcResult.getResponse().getContentAsString(), new TypeReference<PurchaseOrderDTO>() {});


        MvcResult canceledPOAsMvcResult = mockMvc.perform(post("/api/sales/orders/" + dispatchedPO.get_id() + "/cancel").content(mapper.writeValueAsString(itemId)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        PurchaseOrderDTO canceledPO = mapper.readValue(canceledPOAsMvcResult.getResponse().getContentAsString(), new TypeReference<PurchaseOrderDTO>() {});

        assertNotNull(canceledPO);
        assertNotNull(canceledPO.get_id());
        assertEquals(createdPO.get_id(), canceledPO.get_id());
        assertNotNull(canceledPO.getStatus());
        assertEquals(CANCELLED, canceledPO.getStatus());

    }
*/
    @Test
    @Sql("/plants-dataset.sql")
    public void testCancelAcceptedPOScenario() throws Exception {
        PurchaseOrderDTO order = new PurchaseOrderDTO();

        PlantInventoryEntryDTO plantToBeReserved = findAnyPlant();

        order.setPlant(plantToBeReserved);

        order.setRentalPeriod(BusinessPeriodDTO.of(LocalDate.now(), LocalDate.now().plusWeeks(1)));

        MvcResult createdPOAsMvcResult = mockMvc.perform(post("/api/orders").content(mapper.writeValueAsString(order)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        PurchaseOrderDTO createdPO = mapper.readValue(createdPOAsMvcResult.getResponse().getContentAsString(), new TypeReference<PurchaseOrderDTO>() {});

        Long itemId = findAnyItemForPlant(plantToBeReserved.get_id(), order.getRentalPeriod());

        MvcResult acceptedPOAsMvcResult = mockMvc.perform(post("/api/orders/" + createdPO.get_id() + "/accept").content(mapper.writeValueAsString(itemId)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        PurchaseOrderDTO acceptedPO = mapper.readValue(acceptedPOAsMvcResult.getResponse().getContentAsString(), new TypeReference<PurchaseOrderDTO>() {});


        MvcResult canceledPOAsMvcResult = mockMvc.perform(post("/api/orders/" + acceptedPO.get_id() + "/cancel").content(mapper.writeValueAsString(itemId)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        PurchaseOrderDTO canceledPO = mapper.readValue(canceledPOAsMvcResult.getResponse().getContentAsString(), new TypeReference<PurchaseOrderDTO>() {});

        assertNotNull(canceledPO);
        assertNotNull(canceledPO.get_id());
        assertEquals(createdPO.get_id(), canceledPO.get_id());
        assertNotNull(canceledPO.getStatus());
        assertEquals(CANCELLED, canceledPO.getStatus());

    }


    @Test
    @Sql("/plants-dataset.sql")
    public void testAcceptPODefaultScenario() throws Exception {
        PurchaseOrderDTO order = new PurchaseOrderDTO();

        PlantInventoryEntryDTO plantToBeReserved = findAnyPlant();

        order.setPlant(plantToBeReserved);

        order.setRentalPeriod(BusinessPeriodDTO.of(LocalDate.now(), LocalDate.now().plusWeeks(1)));

        MvcResult createdPOAsMvcResult = mockMvc.perform(post("/api/orders").content(mapper.writeValueAsString(order)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        PurchaseOrderDTO createdPO = mapper.readValue(createdPOAsMvcResult.getResponse().getContentAsString(), new TypeReference<PurchaseOrderDTO>() {});

        Long itemId = findAnyItemForPlant(plantToBeReserved.get_id(), order.getRentalPeriod());

        MvcResult acceptedPOAsMvcResult = mockMvc.perform(post("/api/orders/" + createdPO.get_id() + "/accept").content(mapper.writeValueAsString(itemId)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        PurchaseOrderDTO acceptedPO = mapper.readValue(acceptedPOAsMvcResult.getResponse().getContentAsString(), new TypeReference<PurchaseOrderDTO>() {});

        assertNotNull(acceptedPO);
        assertNotNull(acceptedPO.get_id());
        assertEquals(createdPO.get_id(), acceptedPO.get_id());
        assertNotNull(acceptedPO.getTotal());
        assertThat(acceptedPO.getTotal().signum() > 0);
        assertNotNull(acceptedPO.getStatus());
        assertEquals(ACCEPTED, acceptedPO.getStatus());

        // check reference to a plant inventory entry
        assertNotNull(acceptedPO.getPlant());
        assertNotNull(acceptedPO.getPlant().get_id());
        assertNotNull(acceptedPO.getPlant().getName());
        assertEquals(plantToBeReserved.get_id(), acceptedPO.getPlant().get_id());
        assertEquals(plantToBeReserved.getName(), acceptedPO.getPlant().getName());

        // check rental period
        assertNotNull(acceptedPO.getRentalPeriod());
        assertNotNull(acceptedPO.getRentalPeriod().getStartDate());
        assertNotNull(acceptedPO.getRentalPeriod().getEndDate());
        // start < end date
        assertThat(acceptedPO.getRentalPeriod().getEndDate().isAfter(acceptedPO.getRentalPeriod().getStartDate()));
        // period must be in the future
        assertThat(acceptedPO.getRentalPeriod().getStartDate().isAfter(LocalDate.now().minusDays(1)));

        // must have a reservation associated with order by now
		PlantReservationDTO reservation = findReservationForPO(acceptedPO.getPlant().get_id());
		assertNotNull(reservation.getSchedule());
		assertEquals(reservation.getSchedule().getStartDate(), acceptedPO.getRentalPeriod().getStartDate());
		assertEquals(reservation.getSchedule().getEndDate(), acceptedPO.getRentalPeriod().getEndDate());
    }

	@Test
    public void testCreatePOWithNonExistingPlant() throws Exception {
        PurchaseOrderDTO order = new PurchaseOrderDTO();
        PlantInventoryEntryDTO plantToBeReserved = new PlantInventoryEntryDTO();
        plantToBeReserved.set_id(1L);
        order.setPlant(plantToBeReserved);
        order.setRentalPeriod(BusinessPeriodDTO.of(LocalDate.now(), LocalDate.now().plusWeeks(1)));

        mockMvc.perform(post("/api/orders").content(mapper.writeValueAsString(order)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andReturn();
    }

    @Test
    @Sql("/plants-dataset.sql")
    public void testCreatePOWithInvalidPeriods() throws Exception {
        PurchaseOrderDTO order = new PurchaseOrderDTO();
        order.setPlant(findAnyPlant());

        order.setRentalPeriod(BusinessPeriodDTO.of(LocalDate.now().plusWeeks(1), LocalDate.now().plusDays(3)));

        mockMvc.perform(post("/api/orders").content(mapper.writeValueAsString(order)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andReturn();

        order.setRentalPeriod(BusinessPeriodDTO.of(null, LocalDate.now().plusWeeks(1)));

        mockMvc.perform(post("/api/orders").content(mapper.writeValueAsString(order)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andReturn();

        order.setRentalPeriod(BusinessPeriodDTO.of(LocalDate.now(), null));

        mockMvc.perform(post("/api/orders").content(mapper.writeValueAsString(order)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andReturn();

        order.setRentalPeriod(BusinessPeriodDTO.of(LocalDate.now().minusWeeks(1), LocalDate.now().minusDays(3)));

        mockMvc.perform(post("/api/orders").content(mapper.writeValueAsString(order)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andReturn();
    }

    //<editor-fold desc="Helpers">

	private PlantReservationDTO findReservationForPO(Long plantInfoId) throws Exception {
		MvcResult createdPOAsMvcResult = mockMvc.perform(get("/api/reservations"))
				.andExpect(status().is2xxSuccessful())
				.andReturn();

		List<PlantReservationDTO> reservations = mapper.readValue(createdPOAsMvcResult.getResponse().getContentAsString(), new TypeReference<List<PlantReservationDTO>>() {});
		List<PlantReservationDTO> ret = reservations.stream().filter(r -> r.getPlantInfo().getPlantInfo().get_id().equals(plantInfoId)).collect(Collectors.toList());

		if (ret.size() != 1) throw new IllegalStateException();

		return ret.get(0);
	}

    public PlantInventoryEntryDTO findAnyPlant() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/plants?name=Exc&startDate=2017-04-14&endDate=2017-04-25")).andReturn();

        List<PlantInventoryEntryDTO> plants = mapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<PlantInventoryEntryDTO>>() {});

        PlantInventoryEntryDTO plant = plants.get(1);

        return plant;
    }

    private Long findAnyItemForPlant(Long id, BusinessPeriodDTO rentalPeriod) throws Exception {
        MvcResult result = mockMvc.perform(get("/api/pitems/items?pieId="+id+"&startDate="+formatDate(rentalPeriod.getStartDate())+"&endDate="+formatDate(rentalPeriod.getEndDate()))).andReturn();

        List<PlantInventoryItemDTO> plants = mapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<PlantInventoryItemDTO>>() {});

        PlantInventoryItemDTO plant = plants.get(0);

        return plant.get_id();
    }

    private String formatDate(LocalDate localDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String ret = localDate.format(formatter);
        return ret;
    }
    //</editor-fold>

}
