package com.scubaworld.scubagear.DAO;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSourceExtensionsKt;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.transaction.annotation.Transactional;

import com.scubaworld.scubagear.JsonMapper;

public class SpringJdbcDao {
    @Value("${query.timeout}")
    private int QUERY_TIMEOUT;

    @Value("${query.maxrows}")
    private int QUERY_MAX_ROWS;

    private final JdbcTemplate template;
    private static Logger logger = LoggerFactory.getLogger(SpringJdbcDao.class);

    @Autowired
    private JsonMapper jsonMapper;

    public SpringJdbcDao (JdbcTemplate template) {
        this.template = template;
        this.template.setQueryTimeout(QUERY_TIMEOUT);
        this.template.setMaxRows(QUERY_MAX_ROWS);
    }

    public SimpleJdbcCall getNewSimpleJDBCCall(){
        return new SimpleJdbcCall(template);
    }

    // execute query via prepared statement (params as varargs) and map to a List<T>
    //  this method protects against sql injection if varargs are used to pass the params
    public <T> List<T> queryAndMapList(String sql, Class<T> mapTo, Object... params) {
        logger.info("SQL: {}, params: {}", sql, params);
        List<T> result = template.query(sql, new BeanPropertyRowMapper<T>(mapTo), params);

        logger.info("DB response: {}", jsonMapper.toJson(result));

        return result;
    }

    public <T> List<T> queryAndMapList(String sql, RowMapper<T> mapper, Object... params) {
        logger.info("SQL: {}, params: {}", sql, params);
        List<T> result = template.query(sql, mapper, params);

        logger.info("DB response: {}", jsonMapper.toJson(result));

        return result;
    }
    public List<Map<String,Object>> queryForList(String sql, Object... params) {
        logger.info("SQL: {}, params: {}", sql, params);
        List<Map<String,Object>> result = template.queryForList(sql, params);
        logger.info("DB response: {}", jsonMapper.toJson(result));
        return result;
    }

    public <T> List<T> queryAndMapSingleColumnList(String sql, Object... params) {
        logger.info("SQL: {}, params: {}", sql, params);
        List<T> result = template.query(sql, new SingleColumnRowMapper<T>(), params);

        logger.info("DB response: {}", jsonMapper.toJson(result));

        return result;
    }

    // execute query via prepared statement (params as varargs) and map to T
    //  this method protects against sql injection if varargs are used to pass the params
    public <T> T queryAndMapObject(String sql, Class<T> mapTo, Object... params) {
        try {
            logger.info("SQL: {}, params: {}", sql, params);
            T result = template.queryForObject(sql, new BeanPropertyRowMapper<T>(mapTo), params);
            String response = ObjectUtils.defaultIfNull(jsonMapper.toJson(result), result.toString());
            logger.info("DB response: {}", response);
            return result;
        } catch(EmptyResultDataAccessException e){ //Catches exception thrown when no results are returned.
            logger.info("Zero results returned for query.");
            return null;
        }

    }

    //Package the SQL statement + parameters in the CSC and the returned result sets are returned in the returnedParams
    public Map<String, Object> getMultipleResultSet(CallableStatementCreator csc, List<SqlParameter> returnedParams){
        return template.call(csc, returnedParams);
    }

    // execute query via prepared statement (params as varargs) and map to T
    //  this method protects against sql injection if varargs are used to pass the params
    public int queryAndCount(String sql, Object... params) {
        logger.info("SQL: {}, params: {}", sql, params);
        int count;
        try {
            count = template.queryForObject(sql, Integer.class, params);
            logger.info("DB result Number: {}", count);
            return count;
        } catch (EmptyResultDataAccessException e) {
            logger.info("Zero results returned for query.");
            return 0;
        }

    }

    public Long queryForId(String sql, Object... params){
        logger.debug("SQL {}, params: {}", sql, params);
        try{
            return template.query(sql, new SingleColumnRowMapper<>(Long.class),params).stream().findFirst().orElse(null);
        }
        catch(Exception e){
            logger.info("Exception in sql: ", e);
            return null;
        }
    }
    //This method can be used for batch updates to update or insert multiple records
    @Transactional
    public int batchUpdate(String sql, BatchPreparedStatementSetter preparedStatement){
        try {
            logger.info("Starting batch update of SQL {}, to update or insert {} records", sql, preparedStatement.getBatchSize());
            int[] count = template.batchUpdate(sql, preparedStatement);
            logger.info("Batch update completed. {} records updated. ", count.length);
            return count.length;
        }
        catch(Exception e){
            logger.error("Batch update exception for sql {}", sql, e);
            return 0;
        }
    }

    //This method can be used when executing sprocs that don't return a result set.
    @Transactional(rollbackFor = Exception.class)
    public int update(String sql, Object... params){
        logger.info("SQL: {}, params: {}", sql, params);
        int count = template.update(sql, params);
        logger.info("DB Execute Completed. count {}", count);
        return count;
    }

    public int insert(String sql, int [] types,Object...params) {
        logger.info("SQL: {}, params: {}", sql, params);
        int count = template.update(sql, params, types);
        logger.info("DB Execute Completed. count {}", count);
        return count;
    }

    public long insertAndReturnKey(String tableName, String keyToReturn, Map<String, Object> inputParams){
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(Objects.requireNonNull(template.getDataSource()));
        logger.debug("Insert {} into {}", inputParams.toString(), tableName);
        jdbcInsert.withTableName(tableName).usingColumns(inputParams.keySet().toArray(new String[0])).usingGeneratedKeyColumns(keyToReturn);
        Number id = jdbcInsert.executeAndReturnKey(inputParams);
        logger.debug("DB Execute Completed. id {} generated", id);
        return id.longValue();
    }
    protected JdbcTemplate getTemplate() {
        return template;
    }
    protected Logger getLogger() { return logger; }
    protected JsonMapper getJsonMapper() {
        return jsonMapper;
    }
}