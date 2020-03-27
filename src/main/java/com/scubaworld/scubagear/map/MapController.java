package com.scubaworld.scubagear.map;

import java.util.List;

import com.scubaworld.scubagear.map.MapService;
import com.scubaworld.scubagear.representation.ScubaSiteInfo;
import com.scubaworld.scubagear.representation.SiteCount;
import com.scubaworld.scubagear.representation.countryInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MapController {

    private MapService mapService;

    @Autowired
    public MapController(MapService mapService){
        this.mapService = mapService;
    }

    /*@GetMapping("/map/countrySite")
    public List<ScubaSiteInfo> getScubaSitesByZone(
            @RequestParam(value = "zoneList") String zoneList){
        return mapService.getScubaSitesByZone(zoneList);
    }*/

    @GetMapping("/map/SiteCountByZone")
    public List<SiteCount> getSiteCountByZone(
            @RequestParam(required = false, value = "regionList") String[] regionList,
            @RequestParam(required = false, value = "animalList") String[] animalList,
            @RequestParam(required = false, value = "scubaList") String[] scubaList){
        return mapService.getSiteCountByZone(regionList, animalList, scubaList);
    }
    @GetMapping("/map/CountryInfo")
    public countryInfo getCountryInfo(
            @RequestParam(required = true, value = "countryName") String country_short_name
    ){
        return mapService.getCountryInfo(country_short_name);
    }

}
