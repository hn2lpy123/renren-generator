package io.renren.controller;

import io.renren.utils.constant.CommonCodeType;
import io.renren.datasource.DBIdentifier;
import io.renren.datasource.DDSHolder;
import io.renren.entity.CommonDto;
import io.renren.entity.DataSourceInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;
import java.sql.SQLException;

@RestController
@RequestMapping("/sys/dataSource")
public class DataSourceController {

    private final static Logger logger = LoggerFactory.getLogger(DataSourceController.class);

    @Autowired
    private DataSource dataSource;

    @PostMapping("/setDataSource")
    public CommonDto setDataSource(@RequestBody DataSourceInfo dataSourceInfo) {
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

    @GetMapping("/testDataSource")
    public CommonDto testDataSource() {
        try {
            dataSource.getConnection();
            return new CommonDto(CommonCodeType.SUCCESS);
        } catch (SQLException e) {
            return new CommonDto(CommonCodeType.DATABASE_CONNECT_FAIL);
        }
    }
}
