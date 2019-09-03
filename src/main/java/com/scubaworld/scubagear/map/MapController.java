package com.scubaworld.scubagear.map;

import java.util.List;

import com.scubaworld.scubagear.map.MapService;
import com.scubaworld.scubagear.representation.ScubaSiteInfo;
import com.scubaworld.scubagear.representation.SiteCount;

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

    @GetMapping("/map/countrySite")
    public List<ScubaSiteInfo> getScubaSitesByCountry(
            @RequestParam(value = "countryList") String countryList){
        return mapService.getScubaSitesByCountry(countryList);
    }

    @GetMapping("/map/SiteCountByCountry")
    public List<SiteCount> getSiteCountByCountry(
            @RequestParam(value = "countryList") String countryList){
        return mapService.getSiteCountByCountry(countryList);
    }


}
