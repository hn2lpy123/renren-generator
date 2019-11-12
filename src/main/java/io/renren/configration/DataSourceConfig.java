package io.renren.configration;

import io.renren.datasource.DynamicDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

/**
 * 数据源配置管理。
 *
 * @author lipingyu
 * @version 2019-11-11
 */
@Configuration
@MapperScan(basePackages="io.renren.dao", value="sqlSessionFactory")
public class DataSourceConfig {

	/**
	 * 根据配置参数创建数据源。使用派生的子类。
	 *
	 * @return 数据源
	 */
	@Bean(name="dataSource")
	@ConfigurationProperties(prefix="spring.datasource")
	public DataSource getDataSource() {
		DataSourceBuilder builder = DataSourceBuilder.create();
		builder.type(DynamicDataSource.class);
		return builder.build();
	}

	/**
	 * 创建会话工厂。
	 *
	 * @param dataSource 数据源
	 * @return 会话工厂
	 */
	@Primary
	@Bean(name="sqlSessionFactory")
	public SqlSessionFactory getSqlSessionFactory(@Qualifier("dataSource") DataSource dataSource) {
		SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
		try {
			bean.setMapperLocations(
					// 设置mybatis的xml所在位置
					new PathMatchingResourcePatternResolver().getResources("classpath*:mapper/**/*.xml"));
			bean.setDataSource(dataSource);
			return bean.getObject();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Primary
	@Bean("sqlSessionTemplate")
	@Qualifier("sqlSessionFactory")
	public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sessionfactory) {
		SqlSessionTemplate template = new SqlSessionTemplate(sessionfactory);
		return template;
	}
}
