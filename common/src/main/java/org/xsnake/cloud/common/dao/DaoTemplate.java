package org.xsnake.cloud.common.dao;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ArgumentPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapperResultSetExtractor;
import org.xsnake.cloud.common.exception.BugException;
import org.xsnake.cloud.common.search.IPage;
import org.xsnake.cloud.common.search.PageImpl;

/**
 * Oracle实现
 * 
 * @author Jerry.Zhao
 *
 */
@Configuration
@RefreshScope
public class DaoTemplate {

	@Autowired
	DataSource dataSource;

	JdbcTemplate jdbcTemplate;

	private String driver = "MYSQL";
	
	public DaoTemplate(){
		driver = "MYSQL";
	}
	
	public DaoTemplate(String driver){
		if("ORACLE".equalsIgnoreCase(driver) || "MYSQL".equalsIgnoreCase(driver)){
			this.driver = driver;
		}else{
			throw new BugException("不支持的数据库类型");
		}
	}
	
	@Bean
	public JdbcTemplate jdbcTemplate() {
		jdbcTemplate = new JdbcTemplate(dataSource);
		return jdbcTemplate;
	}

	public int execute(String sql) {
		return execute(sql, null);
	}

	public int execute(String sql, Object arg) {
		return execute(sql, new Object[] { arg });
	}

	public int execute(String sql, Object[] args) {
		return jdbcTemplate.update(sql, args);
	}

	private String getSQL(String sql, int start, int end){
		if("MYSQL".equalsIgnoreCase(driver)){
			return getMySQL(sql, start, end);
		}
		if("ORACLE".equalsIgnoreCase(driver)){
			return getOracle(sql, start, end);
		}
		throw new BugException("不支持的数据库类型");
	}
	
	private String getOracle(String sql, int start, int end) {
        StringBuffer oracleSql = new StringBuffer();
        oracleSql.append("SELECT * FROM  ( SELECT A.*, ROWNUM RN FROM ( ")
                 .append(sql)
                 .append(" ) A WHERE ROWNUM <= ")
                 .append(end)
                 .append(" ) WHERE RN > ")
                 .append(start);
        return oracleSql.toString();
	}
	
	private String getMySQL(String sql, int start, int end) {
        StringBuffer mysqSql = new StringBuffer();
        mysqSql.append("SELECT * FROM  ( ")
                 .append(sql)
                 .append(" ) _TEMP LIMIT ")
                 .append(start)
                 .append(",")
				 .append((end - start));
        return mysqSql.toString();
	}
	
	public <T> List<T> query(String sql,Class<T> clazz){
		return queryForClass(sql,null,clazz);
	}
	
	public List<Map<String, Object>> query(String sql){
		return queryForMap(sql,null);
	}
	
	public <T> List<T> query(String sql, Object[] args , Class<T> clazz){
		return queryForClass(sql,args,clazz);
	}
	
	public List<Map<String, Object>> query(String sql, Object[] args){
		return queryForMap(sql,args);
	}
	
	public <T> List<T> query(String sql,int firstResult, int maxResults,Class<T> clazz){
		 return query(sql,null,firstResult,maxResults,clazz);
	}
	
	public List<Map<String, Object>> query(String sql,int firstResult, int maxResults){
		return query(sql,null,firstResult,maxResults);
	}

	public <T> List<T> query(String sql, Object[] args, int firstResult, int maxResults,Class<T> clazz) {
		return queryForClass(getSQL(sql, firstResult, firstResult + maxResults),args,clazz);
	}
	
	public List<Map<String, Object>> query(String sql, Object[] args, int firstResult, int maxResults) {
		return queryForMap(getSQL(sql, firstResult, firstResult + maxResults),args);
	}

	public Long queryLong(String sql,Object arg){
		return queryLong(sql, new Object[]{arg});
	}
	
	public Long queryLong(String sql,Object[] args){
		return jdbcTemplate.queryForObject(sql, args,Long.class);
	}
	
	public Long queryLong(String sql){
		return jdbcTemplate.queryForObject(sql,Long.class);
	}
	
	public String queryString(String sql,Object arg){
		return jdbcTemplate.queryForObject(sql, new Object[]{arg},String.class);
	}
	
	public String queryString(String sql,Object[] args){
		return jdbcTemplate.queryForObject(sql, args,String.class);
	}
	
	public String queryString(String sql){
		return jdbcTemplate.queryForObject(sql,String.class);
	}
	
	public <T> T queryObject(String sql,Object[] args,Class<T> clazz){
		return jdbcTemplate.queryForObject(sql, args, new BeanPropertyRowMapper<T>(clazz));
	}
	
	public <T> T queryObject(String sql,String arg,Class<T> clazz){
		return queryObject(sql, new Object[]{arg}, clazz);
	}
	
	public <T> T queryObject(String sql,Class<T> clazz){
		return queryObject(sql, (Object[])null, clazz);
	}
	
	public IPage search(String sql, Object[] args, int currentPage ,int pageSize) {
		return search(sql, args, currentPage, pageSize, null);
	}
	
	public IPage search(String sql, Object[] args, int currentPage ,int pageSize,Class<?> clazz) {
		try {
			String thql = "select count(1) from (" + sql +") t ";
			BigDecimal _count = jdbcTemplate.queryForObject(thql, args, BigDecimal.class);
			int count = _count.intValue();
			List<?> results = null;
			if(clazz != null){
				results = query(sql, args, (currentPage - 1) * pageSize, pageSize,clazz);
			}else{
				results = query(sql, args, (currentPage - 1) * pageSize, pageSize);
			}
			return new PageImpl(results, currentPage, pageSize, count);
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	protected ResultSetExtractor<List<Map<String, Object>>> resultSetExtractor = new ResultSetExtractor<List<Map<String, Object>>>() {
		@Override
		public List<Map<String, Object>> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
			List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
			while (resultSet.next()) {
				Map<String, Object> map = new HashMap<String, Object>();
				ResultSetMetaData rsmd = resultSet.getMetaData();
				int count = rsmd.getColumnCount();
				for (int i = 1; i <= count; i++) {
					map.put(rsmd.getColumnName(i), resultSet.getObject(rsmd.getColumnName(i)));
				}
				result.add(map);
			}
			return result;
		}
	};
	
	private List<Map<String, Object>> queryForMap(String resultSQL,Object[] args) {
		return jdbcTemplate.query(resultSQL, new ArgumentPreparedStatementSetter(args), resultSetExtractor);
	}
	
	private <T> List<T> queryForClass(String resultSQL,Object[] args,Class<T> clazz) {
		RowMapperResultSetExtractor<T> extractor = new RowMapperResultSetExtractor<>(new BeanPropertyRowMapper<>(clazz));
		return jdbcTemplate.query(resultSQL, new ArgumentPreparedStatementSetter(args), extractor);
	}
	
}
