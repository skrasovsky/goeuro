package com.goeuro.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.goeuro.dto.CountryDTO;
import com.goeuro.dto.GeoPositionDTO;
import com.goeuro.dto.LocationDTO;
import com.goeuro.dto.NameDTO;
import com.goeuro.model.CountryCode;
import com.goeuro.model.Language;
import com.goeuro.model.LocationType;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Deserializer from JSON to {@link com.goeuro.dto.LocationDTO}
 *
 * Example of JSON for deserialization:
 *   {
 *        "_id": 376217,
 *        "key": null,
 *        "name": "Berlin",
 *        "fullName": "Berlin, Germany",
 *        "iata_airport_code": null,
 *        "type": "location",
 *        "country": "Germany",
 *        "geo_position": {
 *            "latitude": 52.52437,
 *            "longitude": 13.41053
 *        },
 *        "locationId": 8384,
 *        "inEurope": true,
 *        "countryId": 56,
 *        "countryCode": "DE",
 *        "coreCountry": true,
 *        "distance": null,
 *        "names": {
 *            "pt": "Berlim",
 *            "ru": "Берлин",
 *            "it": "Berlino",
 *            "is": "Berlín",
 *            "fi": "Berliini",
 *        },
 *        "alternativeNames": {}
 *   }
 *
 */
public class LocationDTODeserializer extends JsonDeserializer<LocationDTO> {

    @Override
    public LocationDTO deserialize(JsonParser jsonParser, DeserializationContext ctxt) throws IOException {

        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        return deserializeLocation(node);
    }

    private LocationDTO deserializeLocation(JsonNode node) {

        LocationDTO locationDTO = new LocationDTO();

        if (node.hasNonNull("_id")) {
            locationDTO.setId(node.get("_id").longValue());
        }

        if (node.hasNonNull("locationId")) {
            locationDTO.setLocationId(node.get("locationId").longValue());
        }

        if (node.hasNonNull("key")) {
            locationDTO.setKey(node.get("key").asText());
        }

        if (node.hasNonNull("name")) {
            locationDTO.setName(node.get("name").asText());
        }

        if (node.hasNonNull("fullName")) {
            locationDTO.setFullName(node.get("fullName").asText());
        }

        if (node.hasNonNull("iata_airport_code")) {
            String iataAirportCode = node.get("iata_airport_code").asText().toUpperCase();
            locationDTO.setIataAirportCode(iataAirportCode);
        }

        if (node.hasNonNull("type")) {
            String locationType = node.get("type").asText().toUpperCase();
            locationDTO.setLocationType(LocationType.valueOf(locationType));
        } else {
            locationDTO.setLocationType(LocationType.NONE);
        }

        locationDTO.setInEurope(node.get("inEurope").asBoolean());
        if (node.hasNonNull("distance")) {
            locationDTO.setDistance(node.get("distance").asDouble());
        }

        locationDTO.setCountry(deserializeCountry(node));
        locationDTO.setGeoPosition(deserializeGeoPosition(node));
        locationDTO.setNames(deserializeNames(node));
        locationDTO.setAlternativeNames(deserializeAlternativeNames(node));

        return locationDTO;
    }

    private GeoPositionDTO deserializeGeoPosition(JsonNode node) {

        GeoPositionDTO dto = new GeoPositionDTO();
        if (node.hasNonNull("geo_position")) {
            if (node.get("geo_position").hasNonNull("latitude")) {
                dto.setLatitude(node.get("geo_position").get("latitude").asDouble());
            }

            if (node.get("geo_position").hasNonNull("longitude")) {
                dto.setLongitude(node.get("geo_position").get("longitude").asDouble());
            }
        }

        return dto;
    }

    private CountryDTO deserializeCountry(JsonNode node) {

        CountryDTO dto = new CountryDTO();
        if (node.hasNonNull("countryId")) {
            dto.setId(node.get("countryId").longValue());
        }

        if (node.hasNonNull("country")) {
            dto.setName(node.get("country").asText());
        }

        if (node.hasNonNull("countryCode")) {
            String countryCode = node.get("countryCode").asText().toUpperCase();
            dto.setCode(CountryCode.valueOf(countryCode));
        } else {
            dto.setCode(CountryCode.NONE);
        }

        dto.setCoreCountry(node.get("coreCountry").asBoolean());

        return dto;
    }

    private List<NameDTO> deserializeNames(JsonNode node) {

        Iterator<Map.Entry<String, JsonNode>> names = node.get("names").fields();
        List<NameDTO> namesDTO = new ArrayList<>();

        while (names.hasNext()) {
            Map.Entry<String, JsonNode> name = names.next();

            NameDTO dto = new NameDTO();
            dto.setLanguage(Language.valueOf(name.getKey().toUpperCase()));
            dto.setValue(name.getValue().asText());

            namesDTO.add(dto);
        }

        return namesDTO;
    }

    private List<String> deserializeAlternativeNames(JsonNode node) {

        // TODO It is not supported now, because we don't know the format of 'alternativeNames'
        // JsonNode alternativeNames = node.get("alternativeNames");
        return Collections.emptyList();
    }

}
