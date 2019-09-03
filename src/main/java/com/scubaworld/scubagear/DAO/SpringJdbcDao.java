package com.scubaworld.scubagear.DAO;

import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import com.scubaworld.scubagear.JsonMapper;

public class SpringJdbcDao {
    @Value("${query.timeout}")
    private int QUERY_TIMEOUT;

    @Value("${query.maxrows}")
    private int QUERY_MAX_ROWS;

    private final JdbcTemplate template;

    @Autowired
    private JsonMapper jsonMapper;

    public SpringJdbcDao (JdbcTemplate template) {
        this.template = template;
        this.template.setQueryTimeout(QUERY_TIMEOUT);
        this.template.setMaxRows(QUERY_MAX_ROWS);
    }

    public <T> T queryAndMapObject(String sql, Class<T> mapTo, Object... params) {
        try {
            T result = template.queryForObject(sql, new BeanPropertyRowMapper<T>(mapTo), params);
            String response = ObjectUtils.defaultIfNull(jsonMapper.toJson(result), result.toString());
            return result;
        } catch (EmptyResultDataAccessException e) { //Catches exception thrown when no results are returned.
            return null;
        }

    }

    protected JdbcTemplate getTemplate() {
        return template;
    }


}