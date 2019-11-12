package io.renren.datasource;

import org.apache.tomcat.jdbc.pool.DataSource;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Timer;

/**
 * 动态数据源管理器。
 *
 * @author lipingyu
 * @version 2019-11-11
 */
public class DDSHolder {
	
	/**
	 * 动态数据源
	 */
	private DataSource dataSource;

	
	private DDSHolder() {
		
	}
	
	/**
	 * 获取单例对象
	 */
	public static DDSHolder instance() {
		return DDSHolderBuilder.instance;
	}
	
	/**
	 * 添加动态数据源。
	 *
	 * @param dds dds
	 */
	public synchronized void setDDS(DataSource dds) {
		if (dataSource != null) {
			dataSource.close();
		}
		this.dataSource = dds;
	}

	/**
	 * 清理动态数据源。
	 */
	public synchronized void clearDDS() {
		if (dataSource != null) {
			dataSource.close();
		}
	}
	
	/**
	 * 查询动态数据源
	 *
	 * @return dds
	 */
	public synchronized DataSource getDDS() {
		return dataSource;
	}

	/**
	 * 单例构件类
	 * @author lipingyu
	 * @version 2019年11月11日
	 */
	private static class DDSHolderBuilder {
		private static DDSHolder instance = new DDSHolder();
	}
}
