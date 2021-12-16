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
import rw.ac.rca.termOneExam.dto.CreateCityDTO;
import rw.ac.rca.termOneExam.service.CityService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static jdk.jfr.internal.jfc.model.Constraint.any;
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
                new City(103,"Kigali",24,2),
                new City(101,"Musanze",4,4)
        );
        when(cityServiceMock.getAll()).thenReturn(cities);

        MockHttpServletRequestBuilder request= MockMvcRequestBuilders
                .get("/api/cities/all")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc
                .perform(request)
                .andExpect(status().isOk())
                .andExpect(content()
                        .json("[{\"id\":103,\"name\":\"Kigali\",\"weather\":24,\"fehrenheit\":2}," +
                                "{\"id\":101,\"name\":\"Rubavu\",\"weather\":20,\"fehrenheit\":4}]"))
                .andReturn();
    }




    public void getByid_success() throws Exception {

        Optional<City> city = Optional.of(new City(102, "Musanze", 18, 0.0));
        when(cityServiceMock.getById(101)).thenReturn(city);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/cities/id/101");
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\":102,\"name\":\"Musanze\",\"weather\":18,\"fahrenheit\":0}"));
    }



    public void getByOne_404() throws Exception {
        City city = new City(102,"Musanze",18,0);

        when(cityServiceMock.getById(city.getId())).thenReturn(Optional.empty());
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get("/api/cities/id/102")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc
                .perform(request)
                .andExpect(status().isNotFound())
                .andExpect(content().json("{\"status\":false,\"message\":\"City not found in our database \"}"))
                .andReturn();
    }



    @Test
    public void addNewCity_fail() throws Exception {
        when(cityServiceMock.existsByName("Gisagara")).thenReturn(true);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/cities/add")
                .content("{\"name\":\"Gisagara\",\"weather\":26}")
                .contentType("application/json");

        mockMvc.perform(requestBuilder)
                .andExpect(status().isBadRequest())
                .andExpect(content().json("{\"status\":false,\"message\":\"city already exits !!!!!  \"}"));
    }





    @Test
    public void AddNewCity_success() throws Exception {
        when(cityServiceMock.save(any(CreateCityDTO.class))).thenReturn(new City(103, "GISAGARA", 30, 0));

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/cities/add")
                .content("{\"name\":\"GISAGARA\",\"weather\":30}")
                .contentType("application/json");

        mockMvc.perform(requestBuilder)
                .andExpect(status().isCreated())
                .andExpect(content().json("{\"id\":103,\"name\":\"GISAGARA\",\"weather\":30,\"fahrenheit\":0}"));
    }











}
