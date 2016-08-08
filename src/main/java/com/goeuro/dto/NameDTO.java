package com.goeuro.dto;

import com.goeuro.model.Language;

import java.io.Serializable;

public class NameDTO implements Serializable {

    private Language language;

    private String value;

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
