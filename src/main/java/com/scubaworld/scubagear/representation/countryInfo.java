package com.scubaworld.scubagear.representation;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class countryInfo {
    @JsonProperty("country_full_name")
    String country_full_name;
    @JsonProperty("iso2")
    String iso2;
    @JsonProperty("country_short_name")
    String country_short_name;
    @JsonProperty("continent")
    String continent;
    @JsonProperty("sub_region")
    String sub_region;
    @JsonProperty("population")
    String population;
    @JsonProperty("capitol")
    String capitol;
    @JsonProperty("major_geography")
    String major_geography;
    @JsonProperty("predominant_language")
    String predominant_language;
    @JsonProperty("driving_side")
    String driving_side;
    @JsonProperty("peak_tourist_season")
    String peak_tourist_season;
    @JsonProperty("best_time_to_dive")
    String best_time_to_dive;
    @JsonProperty("best_time_to_go")
    String best_time_to_go;
    @JsonProperty("bad_time_to_go")
    String bad_time_to_go;
    @JsonProperty("bodies_of_water")
    String bodies_of_water;
    @JsonProperty("country_description")
    String country_description;
}
