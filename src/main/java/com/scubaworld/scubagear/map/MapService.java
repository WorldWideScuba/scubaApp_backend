package com.scubaworld.scubagear.map;

import com.scubaworld.scubagear.map.MapDao;

import java.util.List;

import com.scubaworld.scubagear.representation.ScubaSiteInfo;
import com.scubaworld.scubagear.representation.SiteCount;
import com.scubaworld.scubagear.representation.countryInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MapService {

    private final MapDao mapDao;

    @Autowired
    public MapService(MapDao mapDao){
        this.mapDao = mapDao;
    }

    public List<ScubaSiteInfo> getScubaSitesByZone(String zoneList){
        return mapDao.getSitesByCountry(zoneList);
    }

    public List<SiteCount> getSiteCountByZone(int[] regionIDList, int[] animalIDList, int[] scubaIDList){
        return mapDao.getSiteCountByCountry(regionIDList, animalIDList, scubaIDList);
    }

    public countryInfo getCountryInfo(String country_short_name){
        return mapDao.getCountryInfo(country_short_name);
    }
}
