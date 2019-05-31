package com.scubaworld.scubagear.representation;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ScubaSiteInfo {
    @JsonProperty("siteID")
    private String siteID;
    @JsonProperty("site_name")
    private String site_name;
    @JsonProperty("region_name")
    private String region_name;
    @JsonProperty("countryCode")
    private String countryCode;
    @JsonProperty("longitude")
    private String longitude;
    @JsonProperty("latitude")
    private String latitude;
    @JsonProperty("diveType")
    private String diveType;
    @JsonProperty("animalList")
    private String animalList;
}
