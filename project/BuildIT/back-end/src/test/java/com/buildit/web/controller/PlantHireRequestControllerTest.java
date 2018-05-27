package com.buildit.web.controller;

import com.buildit.common.application.dto.BusinessPeriodDTO;
import com.buildit.procurement.application.dto.PlantHireRequestDTO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.text.IsEmptyString.isEmptyOrNullString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringBootTest(classes = BuilditApplication.class)
//@Sql(scripts = "/plants-dataset.sql")
//@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = BuilditApplication.class) // Check if the name of this class is correct or not
@WebAppConfiguration
@DirtiesContext
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

    @Test
    @Sql("/plants-dataset.sql")
    public void createPlantHireRequestTest() throws Exception{
        CreatePlantHireRequestDTO dto = new CreatePlantHireRequestDTO();
        dto.setConstructionSiteId(10L);
        dto.setPlantHref("http://localhost:8090/api/plants/3");
        dto.setRentalPeriod(BusinessPeriodDTO.of(LocalDate.now().plusDays(1), LocalDate.now().plusDays(3)));
        dto.setSupplierId(10L);

        MvcResult result = mockMvc.perform(post("/api/requests")
                .content(mapper.writeValueAsString(dto))
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        PlantHireRequestDTO plantHireRequestDTO = mapper.readValue(result.getResponse().getContentAsString(), PlantHireRequestDTO.class);

        //Some of the details are checked
        assertThat(plantHireRequestDTO.get_id()).isEqualTo(2);
        assertThat(plantHireRequestDTO.getRentalCost()).isEqualTo("400.00");
        assertThat(plantHireRequestDTO.getRentalPeriod().getStartDate()).isEqualTo("2018-05-28");
        assertThat(plantHireRequestDTO.getRentalPeriod().getEndDate()).isEqualTo("2018-05-30");
        assertThat(plantHireRequestDTO.getPlant().getHref()).isEqualTo("http://localhost:8090/api/plants/3");
        assertThat(plantHireRequestDTO.getSupplier().getName()).isEqualTo("RentIt");
    }
}
