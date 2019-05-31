package com.scubaworld.scubagear.appuser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.JdbcTemplate;

import com.scubaworld.scubagear.DAO.SpringJdbcDao;
import com.scubaworld.scubagear.appuser.UserInfo;

@Repository
public class AppuserDao extends SpringJdbcDao{

    @Autowired
    public AppuserDao(JdbcTemplate template){
        super(template);
    }

    public UserInfo readUserInfo(int userIdentifier){
        String sql = "select au.user_identifier, c.country_short_name, au.username , au.user_email from appuser.application_user as au\n" +
                "inner join world.country as c on c.country_code = au.user_country_code\n" +
                "where user_identifier = ?;";
        return queryAndMapObject(sql, UserInfo.class, userIdentifier);
    }

}
