package io.renren.monitor;

import io.renren.datasource.DBIdentifier;
import io.renren.entity.DataSourceInfo;
import io.renren.utils.generator.GenUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @ClassName InitApplicationRunner
 * @Description APP启动后执行的初始化数据
 * @Author lipingyu
 * @Date 2019/11/13 10:04
 * @Version 1.0
 */
@Component
public class InitApplicationRunner implements ApplicationRunner {

    @Autowired
    private DataSourceInfo defaultDataSourceInfo;

    private static final Logger logger = LoggerFactory.getLogger(InitApplicationRunner.class);

    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
        logger.info("==========start init application==========");
        DBIdentifier.setDataSourceInfo(defaultDataSourceInfo);
        GenUtils.initData.put(GenUtils.PACKAGE, GenUtils.getConfig().getString(GenUtils.PACKAGE));
        GenUtils.initData.put(GenUtils.AUTHOR, GenUtils.getConfig().getString(GenUtils.AUTHOR));
        GenUtils.initData.put(GenUtils.EMAIL, GenUtils.getConfig().getString(GenUtils.EMAIL));
        GenUtils.initData.put(GenUtils.TABLEPREFIX, GenUtils.getConfig().getString(GenUtils.TABLEPREFIX));
        logger.info("==========end init application==========");
    }
}
