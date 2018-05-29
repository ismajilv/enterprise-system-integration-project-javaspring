package com.buildit.web.controller;

import com.buildit.common.application.dto.BusinessPeriodDTO;
import com.buildit.procurement.application.dto.PlantHireRequestDTO;
import com.buildit.procurement.application.dto.PlantInventoryEntryDTO;
import com.buildit.procurement.application.dto.SupplierDTO;
import com.buildit.procurement.domain.model.Supplier;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.buildit.procurement.domain.enums.PHRStatus;
import java.awt.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.buildit.BuilditApplication;
import com.buildit.procurement.application.services.PlantHireRequestService;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import com.buildit.procurement.application.dto.CreatePlantHireRequestDTO;
import com.buildit.procurement.domain.model.PlantHireRequest;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.text.IsEmptyString.isEmptyOrNullString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringBootTest(classes = BuilditApplication.class)
//@Sql(scripts = "/plants-dataset.sql")
//@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = BuilditApplication.class) // Check if the name of this class is correct or not
@WebAppConfiguration
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class PlantHireRequestControllerTest {
    @Autowired
    private PlantHireRequestService plantHireRequestService;

    @Autowired
    ObjectMapper mapper;

    @Autowired
    private WebApplicationContext wac;
    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    public CreatePlantHireRequestDTO createPHRDTO() {
        CreatePlantHireRequestDTO dto = new CreatePlantHireRequestDTO();
        dto.setConstructionSiteId(10L);
        dto.setPlantHref("http://localhost:8090/api/plants/3");
        dto.setRentalPeriod(BusinessPeriodDTO.of(LocalDate.now().plusDays(1), LocalDate.now().plusDays(3)));
        dto.setSupplierId(1L);

        return dto;
    }

    @Test
    @Sql("/plants-dataset.sql")
    public void createPlantHireRequestTest_CC1() throws Exception{

        CreatePlantHireRequestDTO dto = createPHRDTO();

        MvcResult result = mockMvc.perform(post("/api/requests")
                .content(mapper.writeValueAsString(dto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", isEmptyOrNullString()))
                .andReturn();

        PlantHireRequestDTO plantHireRequestDTO = mapper.readValue(result.getResponse().getContentAsString(), PlantHireRequestDTO.class);

        //Some of the details are checked
        assertThat(plantHireRequestDTO.get_id()).isEqualTo(14);
        assertThat(plantHireRequestDTO.getRentalCost()).isEqualTo("400.00");
        assertThat(plantHireRequestDTO.getRentalPeriod().getStartDate()).isEqualTo(LocalDate.now().plusDays(1));
        assertThat(plantHireRequestDTO.getRentalPeriod().getEndDate()).isEqualTo(LocalDate.now().plusDays(3));
        assertThat(plantHireRequestDTO.getPlant().getHref()).isEqualTo("http://localhost:8090/api/plants/3");
        assertThat(plantHireRequestDTO.getSupplier().getName()).isEqualTo("LocalRentIt");
    }

    @Test
    @Sql("/plants-dataset.sql")
    public void approvePlantHireRequestTest_CC2() throws Exception{
        CreatePlantHireRequestDTO dto = createPHRDTO();

        MvcResult result = mockMvc.perform(post("/api/requests")
                .content(mapper.writeValueAsString(dto))
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        PlantHireRequestDTO plantHireRequestDTO = mapper.readValue(result.getResponse().getContentAsString(), PlantHireRequestDTO.class);

        //Change details of CreatePlantHireRequestDTO
        dto.setPlantHref("http://localhost:8090/api/plants/4");
        dto.setConstructionSiteId(11L);
        dto.setRentalPeriod(BusinessPeriodDTO.of(LocalDate.now().plusDays(4), LocalDate.now().plusDays(6)));

        MvcResult result2 = mockMvc.perform(put("/api/requests/" + plantHireRequestDTO.get_id())
                .content(mapper.writeValueAsString(dto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(header().string("Location", isEmptyOrNullString()))
                .andReturn();

        PlantHireRequestDTO plantHireRequestDTO2 = mapper.readValue(result2.getResponse().getContentAsString(), PlantHireRequestDTO.class);

        assertThat(plantHireRequestDTO2.getPlant().getHref()).isEqualTo("http://localhost:8090/api/plants/4");
        assertThat(plantHireRequestDTO2.getSite().get_id()).isEqualTo(11L);
        assertThat(plantHireRequestDTO2.getRentalPeriod().getStartDate()).isEqualTo(LocalDate.now().plusDays(4));
        assertThat(plantHireRequestDTO2.getRentalPeriod().getEndDate()).isEqualTo(LocalDate.now().plusDays(6));
    }

    @Test
    @Sql("/plants-dataset.sql")
    public void cancelPlantHireRequestTest_CC3() throws Exception{
        CreatePlantHireRequestDTO dto = createPHRDTO();

        MvcResult result = mockMvc.perform(post("/api/requests")
                .content(mapper.writeValueAsString(dto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", isEmptyOrNullString()))
                .andReturn();

        PlantHireRequestDTO dto2 = mapper.readValue(result.getResponse().getContentAsString(), PlantHireRequestDTO.class);

        MvcResult result2 = mockMvc.perform(post("/api/requests/" + dto2.get_id() + "/cancel")
                .content(mapper.writeValueAsString(dto2))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(header().string("Location", isEmptyOrNullString()))
                .andReturn();

        dto2 = mapper.readValue(result2.getResponse().getContentAsString(), PlantHireRequestDTO.class);

        assertThat(dto2.getStatus()).isEqualTo(PHRStatus.CANCELLED);
    }

    @Test
    @Sql("/plants-dataset.sql")
    public void checkStatusPlantHireRequestTest_CC4() throws Exception {
        CreatePlantHireRequestDTO dto = createPHRDTO();

        MvcResult result = mockMvc.perform(post("/api/requests")
                .content(mapper.writeValueAsString(dto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", isEmptyOrNullString()))
                .andReturn();

        PlantHireRequestDTO dto2 = mapper.readValue(result.getResponse().getContentAsString(), PlantHireRequestDTO.class);

        assertThat(dto2.getStatus()).isEqualTo(PHRStatus.PENDING_WORKS_ENGINEER_APPROVAL);
    }

    @Test
    @Sql("/plants-dataset.sql")
    public void rejectPlantHireRequestTest_CC5() throws Exception {
        CreatePlantHireRequestDTO dto = createPHRDTO();

        MvcResult result = mockMvc.perform(post("/api/requests")
                .content(mapper.writeValueAsString(dto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", isEmptyOrNullString()))
                .andReturn();

        PlantHireRequestDTO dto2 = mapper.readValue(result.getResponse().getContentAsString(), PlantHireRequestDTO.class);

        MvcResult result2 = mockMvc.perform(post("/api/requests/" + dto2.get_id() + "/reject")
                .content(mapper.writeValueAsString(dto2))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(header().string("Location", isEmptyOrNullString()))
                .andReturn();

        dto2 = mapper.readValue(result2.getResponse().getContentAsString(), PlantHireRequestDTO.class);

        assertThat(dto2.getStatus()).isEqualTo(PHRStatus.REJECTED);
    }
}
