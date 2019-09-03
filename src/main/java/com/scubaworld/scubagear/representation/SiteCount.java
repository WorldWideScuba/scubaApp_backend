package com.scubaworld.scubagear.representation;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class SiteCount {
    @JsonProperty("country_short_name")
    private String country_short_name;
    @JsonProperty("site_count")
    private int site_count;
}
