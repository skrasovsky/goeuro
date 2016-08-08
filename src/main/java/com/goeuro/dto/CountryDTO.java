package com.goeuro.dto;


import com.goeuro.model.CountryCode;

public class CountryDTO extends BaseDTO {

    private String name;

    private CountryCode code;

    private boolean coreCountry;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CountryCode getCode() {
        return code;
    }

    public void setCode(CountryCode code) {
        this.code = code;
    }

    public boolean isCoreCountry() {
        return coreCountry;
    }

    public void setCoreCountry(boolean coreCountry) {
        this.coreCountry = coreCountry;
    }
}
