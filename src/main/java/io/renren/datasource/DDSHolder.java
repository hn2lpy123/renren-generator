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
 * @version 2019年11月8日
 */
public class DDSHolder {
	
	/**
	 * 管理动态数据源列表。<工程编码，数据源>
	 */
	private Map<String, DataSource> ddsMap = new HashMap<String, DataSource>();

	
	private DDSHolder() {
		
	}
	
	/*
	 * 获取单例对象
	 */
	public static DDSHolder instance() {
		return DDSHolderBuilder.instance;
	}
	
	/**
	 * 添加动态数据源。
	 * 
	 * @param projectCode 项目编码 
	 * @param dds dds
	 */
	public synchronized void addDDS(String projectCode, DataSource dds) {
		ddsMap.put(projectCode, dds);
	}
	
	/**
	 * 查询动态数据源
	 * 
	 * @param projectCode 项目编码
	 * @return dds
	 */
	public synchronized DataSource getDDS(String projectCode) {
		
		if (ddsMap.containsKey(projectCode)) {
			return ddsMap.get(projectCode);
		}
		
		return null;
	}
	
	/**
	 * 单例构件类
	 * @author lipingyu
	 * @version 2018年2月26日
	 */
	private static class DDSHolderBuilder {
		private static DDSHolder instance = new DDSHolder();
	}
}
