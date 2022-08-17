package com.cydeo.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class ZipInfo {
    @JsonProperty("post code")
    private String postCode;
    String country;
    @JsonProperty("country abbreviation")
    String countryAbbreviation;
    List<Place> places;
}
