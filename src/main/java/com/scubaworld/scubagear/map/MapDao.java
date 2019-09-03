package com.scubaworld.scubagear.map;

import com.scubaworld.scubagear.DAO.SpringJdbcDao;
import com.scubaworld.scubagear.representation.ScubaSiteInfo;
import com.scubaworld.scubagear.representation.SiteCount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MapDao extends SpringJdbcDao {

    @Autowired
    MapDao(JdbcTemplate template){
        super(template);
    }

    List<ScubaSiteInfo> getSitesByCountry(String countryList){
        String sql = "SELECT d.dive_site_identifier, d.site_name, d.region_name, c.country_short_name, d.longitude, d.latitude,\n" +
                "    t.dive_type_name, string_agg(a.animal_name, ',') AS animal_list\n" +
                "FROM dive.dive_site d  \n" +
                "INNER JOIN dive.dive_type t on t.dive_type_id = d.dive_type_id\n" +
                "INNER JOIN world.country c on c.country_code = d.dive_site_country_code\n" +
                "INNER JOIN dive.dive_animal_map m on m.dive_site_id = d.dive_site_identifier\n" +
                "INNER JOIN dive.animal a on a.animal_id = m.animal_id\n" +
                "WHERE c.country_short_name in ('" + countryList + "')\n" +
                "GROUP BY d.dive_site_identifier, d.site_name, d.region_name, c.country_short_name, d.longitude, d.latitude,\n" +
                "    t.dive_type_name";
        return getTemplate().query(sql, new BeanPropertyRowMapper<>(ScubaSiteInfo.class));
    }

     List<SiteCount> getSiteCountByCountry(String countryList){
        String sql = "SELECT c.country_short_name, COALESCE(COUNT(d.dive_site_identifier), 0) AS site_count\n" +
                    "FROM world.country c\n" +
                    "LEFT JOIN dive.dive_site d on c.country_code = d.dive_site_country_code\n" +
                    "WHERE c.country_short_name in (" + countryList + ")\n" +
                    "GROUP BY c.country_short_name";
         return getTemplate().query(sql, new BeanPropertyRowMapper<>(SiteCount.class));
     }
    /*List<ScubaSiteInfo> getSitesByContinent(){

    }

    List<ScubaSiteInfo> getSitesByAnimal(){

    }

    List<ScubaSiteInfo> getSitesByDiveType(){

    }
     */
}

