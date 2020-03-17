package com.scubaworld.scubagear.map;

import com.scubaworld.scubagear.DAO.SpringJdbcDao;
import com.scubaworld.scubagear.representation.ScubaSiteInfo;
import com.scubaworld.scubagear.representation.SiteCount;
import com.scubaworld.scubagear.representation.countryInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Repository
public class MapDao extends SpringJdbcDao {

    @Autowired
    MapDao(JdbcTemplate template){
        super(template);
    }

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

     List<SiteCount> getSiteCountByCountry(int[] regionIDList, int[] animalIDList, int[] scubaIDList){
        String sql = "SELECT z.zone_short_name, COALESCE(COUNT(DISTINCT d.dive_site_identifier), 0) AS site_count\n" +
                    "FROM world.ui_subregion s\n" +
                    "INNER JOIN world.zone z on z.ui_subregion_ID = s.ui_subregion_ID\n" +
                    "LEFT JOIN dive.dive_site d on z.zone_id = d.dive_site_zone_id\n" +
                    "LEFT JOIN dive.dive_animal_map m on m.dive_site_id = d.dive_site_identifier\n" +
                    "WHERE 1=1\n";
        if(animalIDList != null){
            String animal = Arrays.toString(animalIDList).replaceAll("\\[|\\]", "");
            sql += "AND m.animal_id in (" + animal + ")\n";
        };
        if(scubaIDList != null){
            String scuba = Arrays.toString(scubaIDList).replaceAll("\\[|\\]", "");
            sql += "AND d.dive_type_id in (" + scuba + ")\n";
        };
        if(regionIDList != null){
            String region = Arrays.toString(regionIDList).replaceAll("\\[|\\]", "");
            sql += "AND s.ui_subregion_id in (" + region + ")\n";
        };

        sql += "GROUP BY z.zone_short_name";

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

