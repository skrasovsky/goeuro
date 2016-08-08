package com.goeuro.dto;


import java.io.Serializable;

public class BaseDTO implements Serializable {

    protected Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
