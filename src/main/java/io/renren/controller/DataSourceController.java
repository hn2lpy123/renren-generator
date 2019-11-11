package io.renren.controller;

import io.renren.constant.CommonCodeType;
import io.renren.datasource.DBIdentifier;
import io.renren.entity.CommonDto;
import io.renren.entity.DataSourceInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@RestController
@RequestMapping("/sys/dataSource")
public class DataSourceController {

    private final static Logger logger = LoggerFactory.getLogger(DataSourceController.class);

    @Autowired
    private DataSource dataSource;

    @PostMapping("/setDataSource")
    public CommonDto setDataSource(@RequestBody DataSourceInfo dataSourceInfo) {
        DBIdentifier.setDataSourceInfo(dataSourceInfo);
        return new CommonDto(CommonCodeType.SUCCESS);
    }

    @GetMapping("/getDataSourceInfo")
    public CommonDto setDataSource() {
        try {
            Connection connection = dataSource.getConnection();
        } catch (SQLException e) {

        }
        return new CommonDto(CommonCodeType.SUCCESS, DBIdentifier.getDataSourceInfo());
    }
}
