package io.renren.controller;

import io.renren.datasource.DBIdentifier;
import io.renren.datasource.DDSHolder;
import io.renren.bean.CommonDto;
import io.renren.bean.DataSourceInfo;
import io.renren.utils.constant.CommonCodeType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * 数据库接口类。
 *
 * @author lipingyu
 * @version 2019-11-11
 */
@RestController
@RequestMapping("/sys/dataSource")
public class DataSourceController {

    private final static Logger logger = LoggerFactory.getLogger(DataSourceController.class);

    @PostMapping("/setDataSource")
    public CommonDto setDataSource(@RequestBody DataSourceInfo dataSourceInfo) {
        if (!validDataSourceInfo(dataSourceInfo)) {
            return new CommonDto(CommonCodeType.DATABASE_CONNECT_FAIL);
        }
        if (!dataSourceInfo.equals(DBIdentifier.getDataSourceInfo())) {
            DBIdentifier.setDataSourceInfo(dataSourceInfo);
            DDSHolder.instance().clearDDS();
        }
        return new CommonDto(CommonCodeType.SUCCESS);
    }

    @GetMapping("/getDataSourceInfo")
    public CommonDto getDataSource() {
        return new CommonDto(CommonCodeType.SUCCESS, DBIdentifier.getDataSourceInfo());
    }

    @PostMapping("/testDataSource")
    public CommonDto testDataSource(@RequestBody DataSourceInfo dataSourceInfo) {
        if (validDataSourceInfo(dataSourceInfo)) {
            return new CommonDto(CommonCodeType.SUCCESS);
        }
        return new CommonDto(CommonCodeType.DATABASE_CONNECT_FAIL);
    }

    private boolean validDataSourceInfo(DataSourceInfo dataSourceInfo) {
        Connection connection = null;
        try {
            Class.forName(dataSourceInfo.getDriverClassName());
            connection = DriverManager.getConnection(dataSourceInfo.getUrl()
                    ,dataSourceInfo.getUsername(), dataSourceInfo.getPassword());
            return true;
        } catch (SQLException | ClassNotFoundException e) {
            return false;
        }finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    logger.error("数据库测试Connection关闭失败");
                }
            }
        }
    }
}
