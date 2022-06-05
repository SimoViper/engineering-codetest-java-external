package com.awin.coffeebreak.dto;

import lombok.Getter;

@Getter
public enum Format {
    JSON("json"), XML("xml"), HTML("html");

    private String allowedFormats;

    Format(String allowedFormats) {
        this.allowedFormats = allowedFormats;
    }

}
