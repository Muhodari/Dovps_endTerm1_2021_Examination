package rw.ac.rca.termOneExam.service;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;
import rw.ac.rca.termOneExam.domain.City;
import rw.ac.rca.termOneExam.dto.UpdateCityDTO;
import rw.ac.rca.termOneExam.repository.ICityRepository;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CityServiceTest {

    @Mock
    private ICityRepository iCityRepositoryMock;
    @InjectMocks CityService cityService;



    @Test
    public void getAll_success() {
        when(iCityRepositoryMock.findAll()).thenReturn(Arrays.asList(
                new City(1,"kigali",34.5,5),
                new City(2,"Musanze",56,23)
        ));
        assertEquals(1,cityService.getAll().get(0).getId());
    }


    @Test
    public  void getById(){
        UpdateCityDTO dto= new UpdateCityDTO("kigali",3);
        City city = new City(1L,"Kigali",54,5);
        when(iCityRepositoryMock.findById(1L)).thenReturn(Optional.of(city));
        assertEquals(1L,cityService.getAll().get(0).getId());
        assertEquals("kigali",cityService.getAll().get(0).getName());

    }







}
