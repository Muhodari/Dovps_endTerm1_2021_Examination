package rw.ac.rca.termOneExam.controller;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import rw.ac.rca.termOneExam.domain.City;
import rw.ac.rca.termOneExam.service.CityService;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(CityController.class)
public class CityControllerIntegrationTest {

    @MockBean
    private CityService cityServiceMock;
    @Autowired
    private MockMvc mockMvc;


    @Test
    public void getAll_success() throws Exception {
        List<City> cities= Arrays.asList(
                new City(1,"Kigali",2,2),
                new City(12,"Musanze",4,4)
        );
        when(cityServiceMock.getAll()).thenReturn(cities);

        MockHttpServletRequestBuilder request= MockMvcRequestBuilders
                .get("/api/cities/all")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc
                .perform(request)
                .andExpect(status().isOk())
                .andExpect(content()
                        .json("[{\"id\":1,\"name\":\"Kigali\",\"weather\":2,\"fehrenheit\":2}," +
                                "{\"id\":12,\"name\":\"Musanze\",\"weather\":4,\"fehrenheit\":4}]"))
                .andReturn();
    }



    public void getByid_success() throws Exception {
        City city = new City(1,"Musanze",1,10);

        when(cityServiceMock.getById(city.getId())).thenReturn(city);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get("/api/cities/id/{id}/1")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc
                .perform(request)
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\":1,\"name\":\"Musanze\",\"weather\":1,\"fehrenheit\":10}}"))
                .andReturn();
    }





    public void getByOne_404() throws Exception {
        City city = new City(1,"Musanze",1,10);

        when(cityServiceMock.getById(city.getId())).thenReturn(city);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get("/api/cities/id/{id}/2")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc
                .perform(request)
                .andExpect(status().isNotFound())
                .andExpect(content().json("{\"status\":false,\"message\":\"Item not found\"}"))
                .andReturn();
    }



    @Test
    void POSTwhenValidInput_thenReturns200() throws Exception {
       City city = new City("kigali", 2);

        mockMvc.perform(post("/api/cities/add", 42L)
                        .contentType("application/json")
                        .param("sendWelcomeMail", "true")
                        .content(objectMapper.writeValueAsString(city)))
                .andExpect(status().isOk());
    }











}
