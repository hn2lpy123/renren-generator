package io.renren.datasource;

import io.renren.entity.DataSourceInfo;
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
        DataSourceInfo dataSourceInfo = DBIdentifier.getDataSourceInfo();
        DataSource dds = DDSHolder.instance().getDDS(dataSourceInfo.getDataSourceCode());

        //2、如果数据源不存在则创建
        if (dds == null) {
            try {
                DataSource newDDS = initDDS(dataSourceInfo);
                DDSHolder.instance().addDDS(dataSourceInfo.getDataSourceCode(), newDDS);
            } catch (IllegalArgumentException e) {
                logger.error("Init data source fail. dataSourceCode:" + dataSourceInfo.getDataSourceCode());
                return null;
            } catch (IllegalAccessException e) {
                logger.error("Init data source fail. dataSourceCode:" + dataSourceInfo.getDataSourceCode());
                return null;
            }
        }

        try {
            return dds.getConnection();
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
    private DataSource initDDS(DataSourceInfo dataSourceInfo) throws IllegalArgumentException, IllegalAccessException {

        DataSource dds = new DataSource();

        // 2、复制PoolConfiguration的属性
        PoolProperties property = new PoolProperties();
        Field[] pfields = PoolProperties.class.getDeclaredFields();
        for (Field f : pfields) {
            f.setAccessible(true);
            Object value = f.get(this.getPoolProperties());
            try {
                f.set(property, value);
            }
            catch (Exception e) {
                //有一些static final的属性不能修改。忽略。
                logger.info("Set value fail. attr name:" + f.getName());
                continue;
            }
        }
        dds.setPoolProperties(property);

        // 3、设置数据库名称和IP(一般来说，端口和用户名、密码都是统一固定的)
        String urlFormat = this.getUrl();
        String url = String.format(urlFormat, dataSourceInfo.getDataSourceIp(),
                dataSourceInfo.getDataSourcePort(), dataSourceInfo.getDataSourceName());
        dds.setUrl(url);
        dds.setUsername(dataSourceInfo.getUsername());
        dds.setPassword(dataSourceInfo.getPassword());
        return dds;
    }
}
