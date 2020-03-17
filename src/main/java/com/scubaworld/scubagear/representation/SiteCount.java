package com.scubaworld.scubagear.representation;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class SiteCount {
    @JsonProperty("zone_short_name")
    private String zone_short_name;
    @JsonProperty("site_count")
    private int site_count;
}
