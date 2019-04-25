package com.biz.fileup.config;


import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/*
 * mybatis-context.xml을 대신할 파일
 * 1. dataSource
 * 2. SqlSessionFactory
 * 3. mapper
 */

@Configuration
@MapperScan("com.biz.fileup.mapper")
@EnableTransactionManagement
public class MybatisConfig {
	/*
	 * dataSource 사용할 수 있도록 준비
	 * 
	 * <bean id="ds" class="...BasicDataSource>
	 */
	
	@Bean
	public DataSource ds() {
		
		BasicDataSource ds = new BasicDataSource();
		ds.setDriverClassName("oracle.jdbc.driver.OracleDriver");
		ds.setUrl("jdbc:oracle:thin:@localhost:1521:xe");
		ds.setUsername("mybts");
		ds.setPassword("1234");
		return ds;
	}
	
	/*
	 * sqlsessionFactory = 오라클과 연결할때 사용할Bean
	 */

	@Bean
	public SqlSessionFactoryBean sqlSessionFactory() {
		SqlSessionFactoryBean sf = new SqlSessionFactoryBean();
		sf.setDataSource(ds());
		// 앞으로 사용할 VO 클래스가 있는 패키지 경로를 지정한다.
		sf.setTypeAliasesPackage("com.biz.fileup.model");
		return sf;
	}
	
	/*
	 * Transsaction manager
	 * transactionManager : 이 method는 Spring에 의해 직접 호출되는 사항이어서
	 * 이름을 변경하면 안된다.
	 * tx: 최신버전 spring에서는 method 이름을 tx로 사용해도 된다
	 * 
	 * @EnableTransactionManager Annotation이 활성화(사용가능)
	 * 되려면 이 method가 선언되어 있어야 한다
	 */
	
	@Bean
	public DataSourceTransactionManager transactionManager() {
		DataSourceTransactionManager ts = new DataSourceTransactionManager(ds());
		return ts;
	}
}
