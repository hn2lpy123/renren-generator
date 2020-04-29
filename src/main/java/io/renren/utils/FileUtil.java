package io.renren.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

public class FileUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileUtil.class);

    public static void createFile(String path) {
        File file = new File(path);
        if (!file.exists()) {
            try {
                if (file.createNewFile()) {
                    LOGGER.info(path + "创建成功");
                } else {
                    LOGGER.info(path + "创建失败,");
                }
            } catch (IOException e) {
                LOGGER.info(path + "创建失败,", e);
            }
        }
    }
}
