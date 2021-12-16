package rw.ac.rca.termOneExam.service;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;
import rw.ac.rca.termOneExam.domain.City;
import rw.ac.rca.termOneExam.dto.CreateCityDTO;
import rw.ac.rca.termOneExam.dto.UpdateCityDTO;
import rw.ac.rca.termOneExam.repository.ICityRepository;

import java.util.Arrays;
import java.util.Optional;

import static jdk.jfr.internal.jfc.model.Constraint.any;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CityServiceTest {

    @Mock
    private ICityRepository iCityRepositoryMock;
    @InjectMocks CityService cityService;



    @Test
    public void getAll_success() {
        when(iCityRepositoryMock.findAll()).thenReturn(Arrays.asList(
                new City(102,"kigali",34.5,5),
                new City(104,"Musanze",56,23)
        ));
        assertEquals(1,cityService.getAll().get(0).getId());
    }

    @Test
    public void getAll_fail() {
        when(iCityRepositoryMock.findAll()).thenReturn(Arrays.asList());
        assertEquals(0,cityService.getAll().size());
    }


    @Test
    public  void getById_success(){
        City city = new City(103,"Kigali",54,5);
        when(iCityRepositoryMock.findById(103)).thenReturn(Optional.of(city));
        assertEquals(103,cityService.getAll().get(0).getId());
        assertEquals("kigali",cityService.getAll().get(0).getName());

    }



    @Test
    public void  getById_NotFound() {
        City city = new City(1L, "Kigali", 54, 5);
        when(iCityRepositoryMock.findById(1L)).thenReturn(Optional.of(false));
        assertTrue(iCityRepositoryMock.existsByName("kigali")==false);

    }


    @Test
    public void AddNewCity_Success(){
        when(cityService.save(any(City.class))).
                thenReturn(new City(102,"Musanze",18,75.2));
        City city = cityService.save(new CreateCityDTO("Musanze",24.0));
        assertEquals("Musanze",city.getName());
        assertEquals(102,city.getId());
    }



    @Test
    public void AddNewCity_Fail(){
        when(iCityRepositoryMock.save(any(City.class))).thenReturn(null);
        City city = cityService.save(new CreateCityDTO("Musanze",18));
        assertNull(city);
    }





}
