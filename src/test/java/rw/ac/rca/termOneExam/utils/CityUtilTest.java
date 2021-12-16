package rw.ac.rca.termOneExam.utils;

import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import rw.ac.rca.termOneExam.domain.City;
import rw.ac.rca.termOneExam.repository.ICityRepository;
import rw.ac.rca.termOneExam.service.CityService;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class CityUtilTest {


    @Mock
    private ICityRepository cityRepository;

    @InjectMocks
    private CityService cityService;


    @Test
    public void No_cityHas_Weather_More_40() throws Exception {
        List<City> cities = Arrays.asList(
                new City(103,"Rubavu",20.0,0),
                new City(101, "Kigali", 24.0,0.0),
                new City(102,"Musanze",18.0,0.0),
                new City(104,"Nyagatare",28.0,0.0));
        when(cityRepository.findAll()).thenReturn(cities);
        assertFalse(is_grater_than_40(cityService.getAll()));
    }


    @Test
    public void No_CityHasWeather_lessThan_10(){
        List<City> cities = Arrays.asList(
                new City(103,"Rubavu",20.0,0),
                new City(101, "Kigali", 24.0,0.0),
                new City(102,"Musanze",18.0,0.0),
                new City(104,"Nyagatare",28.0,0.0));
        when(cityRepository.findAll()).thenReturn(cities);
        assertFalse(is_less_than_10(cityService.getAll()));
    }




    @Test
    public void testMocking(){
        List<City> cities = mock(List.class);
        assertEquals(cities.size(), 0);
        assertNull(cities.get(0));

        cities.add(new City(101, "Kigali", 24.0,75.2));
        cities.add(new City(101, "Kigali", 24.0,75.2));
        assertEquals(cities.size(), 0);

        when(cities.get(0)).thenReturn(new City(101, "Kigali", 24.0,75.2));
        when(cities.size()).thenReturn(3);

        assertEquals(cities.size(), 3);
        assertEquals(cities.get(0).getName(), "Kigali");
    }


    @Test
    public void testSpying(){
        List<City> cities = spy(List.class);
        assertEquals(cities.size(), 0);
        assertNull(cities.get(0));
        cities.add(new City(101, "Kigali", 24.0,75.2));
        assertEquals(cities.size(), 0);
        verify(cities, times(1)).add(any(City.class));
        when(cities.size()).thenReturn(5);
        assertEquals(cities.size(), 5);

        cities.add(new City(101, "Kigali", 24.0,75.2));
        assertEquals(cities.size(), 5);
    }




    @Test
    public void CitiesThatContains_MusanzeAnd_kigali(){
        List<City> cities = Arrays.asList(

                new City(101, "Kigali", 24.0,0.0),
                new City(102,"Musanze",18.0,0.0),
                new City(104,"Nyagatare",28.0,0.0));
        when(cityRepository.findAll()).thenReturn(cities);

        assertTrue(checkExistOfMusanzeANdKigali(cityService.getAll()));
    }



    private boolean checkExistOfMusanzeANdKigali(List<City> cities) {
        boolean exists = false;
        for (City city : cities) {
            if(Objects.equals(city.getName(), "Kigali") || Objects.equals(city.getName(), "Musanze")){
                exists = true;
                break;
            }
        }
        return exists;
    }


    private boolean is_less_than_10(List<City> cities) {
        for (City city : cities) {
            if(city.getWeather() < 10){
                return true;
            }
        }
        return false;
    }

    public boolean is_grater_than_40(List<City> cities){
        for (City city : cities) {
            if(city.getWeather() > 40){
                return true;
            }
        }
        return false;
    }







}
