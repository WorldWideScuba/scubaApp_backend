package com.scubaworld.scubagear.representation;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class SiteCount {
    @JsonProperty("name")
    private String name;
    @JsonProperty("count")
    private int count;
}
