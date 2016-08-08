package com.goeuro.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.goeuro.utils.LocationDTODeserializer;
import com.goeuro.model.LocationType;

import java.util.List;

@JsonDeserialize(using = LocationDTODeserializer.class)
public class LocationDTO extends BaseDTO {

    private Long locationId;

    private String key;

    private String name;

    private String fullName;

    private LocationType locationType;

    private Boolean inEurope;

    private Double distance;

    private String iataAirportCode;

    private CountryDTO country;

    private GeoPositionDTO geoPosition;

    private List<NameDTO> names;

    private List<String> alternativeNames;

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public LocationType getLocationType() {
        return locationType;
    }

    public void setLocationType(LocationType locationType) {
        this.locationType = locationType;
    }

    public Boolean getInEurope() {
        return inEurope;
    }

    public void setInEurope(Boolean inEurope) {
        this.inEurope = inEurope;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public String getIataAirportCode() {
        return iataAirportCode;
    }

    public void setIataAirportCode(String iataAirportCode) {
        this.iataAirportCode = iataAirportCode;
    }

    public CountryDTO getCountry() {
        return country;
    }

    public void setCountry(CountryDTO country) {
        this.country = country;
    }

    public GeoPositionDTO getGeoPosition() {
        return geoPosition;
    }

    public void setGeoPosition(GeoPositionDTO geoPosition) {
        this.geoPosition = geoPosition;
    }

    public List<NameDTO> getNames() {
        return names;
    }

    public void setNames(List<NameDTO> names) {
        this.names = names;
    }

    public List<String> getAlternativeNames() {
        return alternativeNames;
    }

    public void setAlternativeNames(List<String> alternativeNames) {
        this.alternativeNames = alternativeNames;
    }
}
