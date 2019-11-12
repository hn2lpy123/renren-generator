package io.renren.entity;

import org.apache.commons.lang.StringUtils;

import java.io.Serializable;

public class DataSourceInfo implements Serializable {

    private String driverClassName;

    private String dataSourceIp;

    private String dataSourcePort;

    private String dataSourceName;

    private String username;

    private String password;

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof  DataSourceInfo) {
            DataSourceInfo compareObj = (DataSourceInfo)obj;
            if (compareObj.getDriverClassName().equals(driverClassName) &&
                    compareObj.getDataSourceIp().equals(dataSourceIp) &&
                    compareObj.getDataSourcePort().equals(dataSourcePort) &&
                    compareObj.getDataSourceName().equals(dataSourceName) &&
                    compareObj.getUsername().equals(username) && compareObj.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

    public String getDriverClassName() {
        return driverClassName;
    }

    public void setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
    }

    public String getDataSourceName() {
        return StringUtils.isNotBlank(dataSourceName) ? dataSourceName : "eif_member";
    }

    public void setDataSourceName(String dataSourceName) {
        this.dataSourceName = dataSourceName;
    }

    public String getDataSourceIp() {
        return StringUtils.isNotBlank(dataSourceIp) ? dataSourceIp : "172.16.81.185";
    }

    public void setDataSourceIp(String dataSourceIp) {
        this.dataSourceIp = dataSourceIp;
    }

    public String getDataSourcePort() {
        return StringUtils.isNotBlank(dataSourcePort) ? dataSourcePort : "43306";
    }

    public void setDataSourcePort(String dataSourcePort) {
        this.dataSourcePort = dataSourcePort;
    }

    public String getUsername() {
        return StringUtils.isNotBlank(username) ? username : "root";
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return StringUtils.isNotBlank(password) ? password : "123456";
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
