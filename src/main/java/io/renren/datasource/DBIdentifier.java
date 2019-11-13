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

	private static DataSourceInfo dataSourceInfo;

	public static DataSourceInfo getDataSourceInfo() {
		return dataSourceInfo;
	}

	public static void setDataSourceInfo(DataSourceInfo dataSourceInfo) {
		DBIdentifier.dataSourceInfo = dataSourceInfo;
	}
}
