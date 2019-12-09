package io.renren.configration;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import io.renren.bean.DataSourceInfo;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.concurrent.TimeUnit;

@Configuration
/**
 * @功能描述 系统配置类
 * @author www.gaozz.club
 * @date 2018-08-26
 */
@PropertySource(value = { "dataSource.properties" })
public class SystemConfiguration {

    @Bean
    public Cache<String, Integer> getCache() {
        return CacheBuilder.newBuilder().expireAfterWrite(2L, TimeUnit.SECONDS).build();// 缓存有效期为2秒
    }

    @Bean(name = "defaultDataSource")
    @ConfigurationProperties(prefix="spring.datasource.default")
    public DataSourceInfo getDefaultDataSource() {
        return new DataSourceInfo();
    }
}