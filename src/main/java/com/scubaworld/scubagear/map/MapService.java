package com.scubaworld.scubagear.map;

import com.scubaworld.scubagear.map.MapDao;

import java.util.List;

import com.scubaworld.scubagear.representation.ScubaSiteInfo;
import com.scubaworld.scubagear.representation.SiteCount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MapService {

    private final MapDao mapDao;

    @Autowired
    public MapService(MapDao mapDao){
        this.mapDao = mapDao;
    }

    public List<ScubaSiteInfo> getScubaSitesByCountry(String countryList){
        return mapDao.getSitesByCountry(countryList);
    }

    public List<SiteCount> getSiteCountByCountry(String countryList){
        return mapDao.getSiteCountByCountry(countryList);
    }
}
