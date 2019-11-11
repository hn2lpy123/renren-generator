package io.renren.datasource;

import io.renren.entity.DataSourceInfo;
import org.apache.tomcat.jdbc.pool.DataSource;

/**
 * 数据库标识管理类。用于区分数据源连接的不同数据库。
 * 
 * @author lipingyu
 * @version 2019-11-11
 */
public class DBIdentifier {

	private static ThreadLocal<DataSourceInfo> dataSourceInfoThreadLocal = new ThreadLocal<DataSourceInfo>();

	public static DataSourceInfo getDataSourceInfo() {
		return dataSourceInfoThreadLocal.get();
	}

	public static void setDataSourceInfo(DataSourceInfo dataSourceInfo) {
		dataSourceInfoThreadLocal.set(dataSourceInfo);
	}
}
