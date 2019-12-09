package io.renren.datasource;

import io.renren.bean.DataSourceInfo;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * 定义动态数据源派生类。从基础的DataSource派生，动态性自己实现。
 *
 * @author lipingyu
 * @version 2019-11-11
 */
public class DynamicDataSource extends DataSource {

    private static Logger logger = LoggerFactory.getLogger(DynamicDataSource.class);

    /**
     * 改写本方法是为了在请求不同工程的数据时去连接不同的数据库。
     */
    @Override
    public Connection getConnection(){

        //1、获取数据源
        DataSource dds = DDSHolder.instance().getDDS();

        //2、如果数据源不存在则创建
        try {
            if (dds == null) {
                dds = initDDS(DBIdentifier.getDataSourceInfo());
                DDSHolder.instance().setDDS(dds);
            }
            return dds.getConnection();
        } catch (IllegalArgumentException e) {
            logger.error("Init data source fail.", e);
            return null;
        } catch (IllegalAccessException e) {
            logger.error("Init data source fail.", e);
            return null;
        } catch (SQLException e) {
            logger.error("getConnection fail.");
            return null;
        }
    }

    /**
     * 以当前数据对象作为模板复制一份。
     *
     * @return dds
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     */
    public DataSource initDDS(DataSourceInfo dataSourceInfo) throws IllegalArgumentException, IllegalAccessException {

        DataSource dds = new DataSource();

        // 2、复制PoolConfiguration的属性
        PoolProperties property = new PoolProperties();
        Field[] pfields = PoolProperties.class.getDeclaredFields();
        for (Field f : pfields) {
            f.setAccessible(true);
            Object value = f.get(this.getPoolProperties());
            try {
                f.set(property, value);
            } catch (Exception e) {
                //有一些static final的属性不能修改。忽略。
                logger.info("Set value fail. attr name:" + f.getName());
                continue;
            }
        }
        dds.setPoolProperties(property);

        // 设置数据库连接默认属性
        dds.setValidationQuery("SELECT 1");
        dds.setMaxWait(12000);
        dds.setTestWhileIdle(true);
        dds.setTestOnBorrow(true);

        // 设置数据库连接connect属性
        dds.setUrl(dataSourceInfo.getUrl());
        dds.setDriverClassName(dataSourceInfo.getDriverClassName());
        dds.setUsername(dataSourceInfo.getUsername());
        dds.setPassword(dataSourceInfo.getPassword());
        return dds;
    }
}
