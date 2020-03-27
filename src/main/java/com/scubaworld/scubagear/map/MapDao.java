package com.scubaworld.scubagear.map;

import com.scubaworld.scubagear.DAO.SpringJdbcDao;
import com.scubaworld.scubagear.representation.ScubaSiteInfo;
import com.scubaworld.scubagear.representation.SiteCount;
import com.scubaworld.scubagear.representation.countryInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Repository
public class MapDao extends SpringJdbcDao {

    Logger logger = LoggerFactory.getLogger(MapDao.class);

    @Autowired
    MapDao(JdbcTemplate template){
        super(template);
    }

    Function<String, String> addQuotes = s -> "'" + s + "'";

    List<ScubaSiteInfo> getSitesByCountry(String zoneList){
        String sql = "SELECT d.dive_site_identifier, d.site_name, d.region_name, z.zone_short_name, d.longitude, d.latitude,\n" +
                "    t.dive_type_name, string_agg(a.animal_name, ',') AS animal_list\n" +
                "FROM dive.dive_site d  \n" +
                "INNER JOIN dive.dive_type t on t.dive_type_id = d.dive_type_id\n" +
                "INNER JOIN world.zone z on z.zone_id = d.dive_site_zone_id\n" +
                "LEFT JOIN dive.dive_animal_map m on m.dive_site_id = d.dive_site_identifier\n" +
                "LEFT JOIN dive.animal a on a.animal_id = m.animal_id\n" +
                "WHERE c.zone_short_name in ('" + zoneList + "')\n" +
                "GROUP BY d.dive_site_identifier, d.site_name, d.region_name, z.zone_short_name, d.longitude, d.latitude,\n" +
                "    t.dive_type_name";
        return getTemplate().query(sql, new BeanPropertyRowMapper<>(ScubaSiteInfo.class));
    }

     List<SiteCount> getSiteCountByZone(String[] regionList, String[] animalList, String[] scubaList){
        String sql = "SELECT z.zone_short_name, COALESCE(dives.site_count, 0) as site_count " +
                "FROM world.zone z " +
                "LEFT JOIN( " +
                "   SELECT z.zone_short_name as zone_short_name, COALESCE(COUNT(DISTINCT d.dive_site_identifier), 0) AS site_count " +
                "   FROM world.zone z\n" +
                "   LEFT JOIN world.ui_subregion s on z.ui_subregion_ID = s.ui_subregion_ID " +
                "   LEFT JOIN dive.dive_site d on z.zone_id = d.dive_site_zone_id " +
                "   LEFT JOIN dive.dive_animal_map m on m.dive_site_id = d.dive_site_identifier " +
                "   WHERE 1=1 ";
        if(animalList != null){
            String animal = Arrays.stream(animalList)
                    .map(addQuotes)
                    .collect(Collectors.joining(", "));
            sql += "AND m.animal_name in (" + animal + ") ";
        };
        if(scubaList != null){
            String scuba = Arrays.stream(scubaList)
                    .map(addQuotes)
                    .collect(Collectors.joining(", "));
            sql += "AND d.dive_type_name in (" + scuba + ") ";
        };
        if(regionList != null){
            String region = Arrays.stream(regionList)
                    .map(addQuotes)
                    .collect(Collectors.joining(", "));
            sql += "AND s.ui_subregion_name in (" + region + ") ";
        };

        sql +=  "GROUP BY z.zone_short_name " +
                ") dives on dives.zone_short_name = z.zone_short_name";
        return getTemplate().query(sql, new BeanPropertyRowMapper<>(SiteCount.class));
     }
    /*List<ScubaSiteInfo> getSitesByContinent(){

    }

    List<ScubaSiteInfo> getSitesByAnimal(){

    }

    List<ScubaSiteInfo> getSitesByDiveType(){

    }
     */

    countryInfo getCountryInfo(String country_name) {
        String sql = "SELECT zone_full_name, iso2, zone_short_name, " +
                        "continent_name AS continent, ui_subregion_name AS subRegion,\n" +
                        "population, capitol, major_geography, predominant_language, " +
                        "driving_side, peak_tourist_season, " +
                        "best_time_to_dive, bad_time_to_go, bodies_of_water, " +
                        "zone_description\n" +
                        "from world.zone co\n" +
                        "inner join world.continent c on c.continent_id = co.continent_id \n" +
                        "inner join world.ui_subregion s on s.ui_subregion_id = co.ui_subregion_id\n" +
                        "WHERE zone_short_name = CAST(? AS VARCHAR)";
        return queryAndMapObject(sql, countryInfo.class,country_name);
    }
}

