package com.rentit.sales.rest;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rentit.RentitApplication;
import com.rentit.common.application.dto.BusinessPeriodDTO;
import com.rentit.inventory.application.dto.PlantInventoryEntryDTO;
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
import java.util.List;

import static com.rentit.sales.domain.model.POStatus.PENDING;
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
@DirtiesContext
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
/*
    @Test
    @Sql("/plants-dataset.sql")
    @Ignore
    public void testGetAllPlants() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/sales/plants?name=Exc&startDate=2017-04-14&endDate=2017-04-25"))
                .andExpect(status().isOk())
                .andExpect(header().string("Location", isEmptyOrNullString()))
                .andReturn();

        List<PlantInventoryEntryDTO> plants = mapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<PlantInventoryEntryDTO>>() {});

        assertThat(plants.size()).isEqualTo(3);

        PurchaseOrderDTO order = new PurchaseOrderDTO();
        order.setPlant(plants.get(1));
        order.setRentalPeriod(BusinessPeriodDTO.of(LocalDate.now(), LocalDate.now()));

        mockMvc.perform(post("/api/sales/orders").content(mapper.writeValueAsString(order)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }*/

    // A recently created PO must have a valid reference to a plant inventory entry,
    // a valid rental period (e.g. start < end date,
    // period must be in the future, and both dates must be different from null),

    @Test
    @Sql("/plants-dataset.sql")
    public void testScenarios() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/sales/plants?name=Exc&startDate=2017-04-14&endDate=2017-04-25"))
                .andExpect(status().isOk())
                .andExpect(header().string("Location", isEmptyOrNullString()))
                .andReturn();

        List<PlantInventoryEntryDTO> plants = mapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<PlantInventoryEntryDTO>>() {});

        assertThat(plants.size()).isEqualTo(3);

        PurchaseOrderDTO order = new PurchaseOrderDTO();
        PlantInventoryEntryDTO plantToBeReserved = plants.get(1);
        order.setPlant(plantToBeReserved);
        order.setRentalPeriod(BusinessPeriodDTO.of(LocalDate.now(), LocalDate.now().plusWeeks(1)));

        MvcResult createdPOAsMvcResult = mockMvc.perform(post("/api/sales/orders").content(mapper.writeValueAsString(order)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        PurchaseOrderDTO createdPO = mapper.readValue(createdPOAsMvcResult.getResponse().getContentAsString(), new TypeReference<PurchaseOrderDTO>() {});

        assertNotNull(createdPO);
        assertNotNull(createdPO.get_id());
        assertNull(createdPO.getTotal());
        assertNotNull(createdPO.getStatus());
        assertEquals(PENDING, createdPO.getStatus());

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
        assertThat(createdPO.getRentalPeriod().getStartDate().isAfter(LocalDate.now()));
    }

}
