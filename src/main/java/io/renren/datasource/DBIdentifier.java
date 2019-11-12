package io.renren.datasource;

import io.renren.entity.DataSourceInfo;
import org.springframework.stereotype.Component;

/**
 * 数据库信息管理类。
 * 
 * @author lipingyu
 * @version 2019-11-11
 */
@Component
public class DBIdentifier {

	private static ThreadLocal<DataSourceInfo> dataSourceInfoThreadLocal = new ThreadLocal<DataSourceInfo>();

	public static DataSourceInfo getDataSourceInfo() {
		return dataSourceInfoThreadLocal.get();
	}

	public static void setDataSourceInfo(DataSourceInfo dataSourceInfo) {
		dataSourceInfoThreadLocal.set(dataSourceInfo);
	}
}
