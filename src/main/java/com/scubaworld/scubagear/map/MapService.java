package com.scubaworld.scubagear.map;

import com.scubaworld.scubagear.map.MapDao;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.scubaworld.scubagear.representation.ScubaSiteInfo;
import com.scubaworld.scubagear.representation.SiteCount;
import com.scubaworld.scubagear.representation.countryInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MapService {

    private final MapDao mapDao;
    Logger logger = LoggerFactory.getLogger(MapService.class);

    @Autowired
    public MapService(MapDao mapDao){
        this.mapDao = mapDao;
    }

    public List<ScubaSiteInfo> getScubaSitesByZone(String zoneList){
        return mapDao.getSitesByCountry(zoneList);
    }

    public List<SiteCount> getSiteCountByZone(String[] regionList, String[] animalList, String[] scubaList){
        return mapDao.getSiteCountByZone(regionList, animalList, scubaList);
    }

    public countryInfo getCountryInfo(String country_short_name){
        return mapDao.getCountryInfo(country_short_name);
    }
}
