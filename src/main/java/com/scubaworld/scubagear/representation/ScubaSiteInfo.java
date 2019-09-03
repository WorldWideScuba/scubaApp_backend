package com.scubaworld.scubagear.representation;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ScubaSiteInfo {
    @JsonProperty("dive_site_identifier")
    private String dive_site_identifier;
    @JsonProperty("site_name")
    private String site_name;
    @JsonProperty("region_name")
    private String region_name;
    @JsonProperty("country_short_name")
    private String country_short_name;
    @JsonProperty("longitude")
    private String longitude;
    @JsonProperty("latitude")
    private String latitude;
    @JsonProperty("dive_type_name")
    private String dive_type_name;
    @JsonProperty("animalList")
    private String animalList;
}
