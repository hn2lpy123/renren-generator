package io.renren.entity;

import java.io.Serializable;

public class DataSourceInfo implements Serializable {

    private String dataSourceType;

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

    public String getUrl() {
        String url = "";
        if (dataSourceType.equalsIgnoreCase("mysql")) {
            url = "jdbc:mysql://%s:%s/%s?useUnicode=true&characterEncoding=UTF-8&useSSL=false";
        } else if (dataSourceType.equalsIgnoreCase("oracle")) {
            url = "jdbc:oracle:thin:@%s:%s:%s";
        }
        return String.format(url, dataSourceIp, dataSourcePort, dataSourceName);
    }

    public String getDataSourceType() {
        return dataSourceType;
    }

    public void setDataSourceType(String dataSourceType) {
        this.dataSourceType = dataSourceType;
    }

    public String getDriverClassName() {
        return driverClassName;
    }

    public void setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
    }

    public String getDataSourceName() {
        return dataSourceName;
    }

    public void setDataSourceName(String dataSourceName) {
        this.dataSourceName = dataSourceName;
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
