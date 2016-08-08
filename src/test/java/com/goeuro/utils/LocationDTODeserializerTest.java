package com.goeuro.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.goeuro.config.ApplicationTestConfig;
import com.goeuro.dto.LocationDTO;
import com.goeuro.model.CountryCode;
import com.goeuro.model.LocationType;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.fail;

@SpringApplicationConfiguration(classes = ApplicationTestConfig.class)
public class LocationDTODeserializerTest extends AbstractTestNGSpringContextTests {

    private ObjectMapper mapper;

    private LocationDTODeserializer deserializer;

    private String testJsonFile = "src/test/data/location.json";
    private String testJsonFileWithNull = "src/test/data/location_null_value.json";

    @BeforeTest
    public void setUp() {
        deserializer = new LocationDTODeserializer();
        mapper = new ObjectMapper();
    }

    @Test
    public void should_deserialize_json_to_location_dto() {

        File locationJsonFile = new File(testJsonFile);
        try {
            InputStream inputStream = new FileInputStream(locationJsonFile);

            JsonParser parser = mapper.getFactory().createParser(inputStream);
            DeserializationContext context = mapper.getDeserializationContext();
            LocationDTO locationDTO = deserializer.deserialize(parser, context);

            assertNotNull(locationDTO);
            assertEquals(locationDTO.getId().longValue(), 314826);
            assertEquals(locationDTO.getKey(), "key");
            assertEquals(locationDTO.getName(), "Berlin Tegel");
            assertEquals(locationDTO.getFullName(), "Berlin Tegel (TXL), Germany");
            assertEquals(locationDTO.getIataAirportCode(), "TXL");
            assertEquals(locationDTO.getLocationType(), LocationType.AIRPORT);

            assertNotNull(locationDTO.getCountry());
            assertEquals(locationDTO.getCountry().getId().longValue(), 56);
            assertEquals(locationDTO.getCountry().getName(), "Germany");
            assertEquals(locationDTO.getCountry().getCode(), CountryCode.DE);
            assertEquals(locationDTO.getCountry().isCoreCountry(), true);

            assertNotNull(locationDTO.getGeoPosition());
            assertEquals(locationDTO.getGeoPosition().getLatitude(), 52.5548);
            assertEquals(locationDTO.getGeoPosition().getLongitude(), 13.28903);

            assertEquals(locationDTO.getLocationId().longValue(), 1234);
            assertEquals(locationDTO.getDistance(), 55.55);
            assertEquals(locationDTO.getInEurope().booleanValue(), true);

            assertEquals(locationDTO.getNames().size(), 4);
            assertEquals(locationDTO.getAlternativeNames().size(), 0);
        } catch (IOException e) {
            fail("Something happened during testing deserialization of json", e);
        }
    }

    @Test
    public void should_deserialize_json_with_null_values_to_location_dto() {

        File locationJsonFile = new File(testJsonFileWithNull);
        try {
            InputStream inputStream = new FileInputStream(locationJsonFile);

            JsonParser parser = mapper.getFactory().createParser(inputStream);
            DeserializationContext context = mapper.getDeserializationContext();
            LocationDTO locationDTO = deserializer.deserialize(parser, context);

            assertNotNull(locationDTO);

            assertNull(locationDTO.getId());
            assertNull(locationDTO.getKey());
            assertNull(locationDTO.getName());
            assertNull(locationDTO.getFullName());
            assertNull(locationDTO.getIataAirportCode());
            assertEquals(locationDTO.getLocationType(), LocationType.NONE);

            assertNull(locationDTO.getCountry().getId());
            assertNull(locationDTO.getCountry().getName());
            assertEquals(locationDTO.getCountry().getCode(), CountryCode.NONE);
            assertEquals(locationDTO.getCountry().isCoreCountry(), false);

            assertNull(locationDTO.getGeoPosition().getLatitude());
            assertNull(locationDTO.getGeoPosition().getLongitude());

            assertNull(locationDTO.getLocationId());
            assertNull(locationDTO.getDistance());
            assertEquals(locationDTO.getInEurope().booleanValue(), false);

            assertEquals(locationDTO.getNames().size(), 0);
            assertEquals(locationDTO.getAlternativeNames().size(), 0);

        } catch (IOException e) {
            fail("Something happened during testing deserialization of json", e);
        }
    }

}
