package io.renren.controller;

import io.renren.constant.CommonCodeType;
import io.renren.entity.CommonDto;
import io.renren.entity.DataSourceInfo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sys/dataSource")
public class DataSourceController {

    @PostMapping("/setDataSource")
    public CommonDto setDataSource(@RequestBody DataSourceInfo dataSourceInfo) {

        return new CommonDto(CommonCodeType.SUCCESS);
    }
}
