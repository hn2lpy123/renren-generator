package io.renren.entity;

import org.apache.tomcat.jdbc.pool.DataSource;

import java.io.Serializable;

public class DataSourceInfo implements Serializable {

    private String dataSourceCode;

    private String dataSourceIp;

    private String dataSourcePort;

    private String dataSourceName;

    private String username;

    private String password;

    private DataSource dataSource;

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public String getDataSourceName() {
        return dataSourceName;
    }

    public void setDataSourceName(String dataSourceName) {
        this.dataSourceName = dataSourceName;
    }

    public String getDataSourceCode() {
        return dataSourceCode;
    }

    public void setDataSourceCode(String dataSourceCode) {
        this.dataSourceCode = dataSourceCode;
    }

    public String getDataSourceIp() {
        return dataSourceIp;
    }

    public void setDataSourceIp(String dataSourceIp) {
        this.dataSourceIp = dataSourceIp;
    }

    public String getDataSourcePort() {
        return dataSourcePort;
    }

    public void setDataSourcePort(String dataSourcePort) {
        this.dataSourcePort = dataSourcePort;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
