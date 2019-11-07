package io.renren.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;
import java.io.IOException;

/**
 * @ClassName IOUtil
 * @Description 流工具类
 * @Author lipingyu
 * @Date 2019/10/22 10:22
 * @Version 1.0
 */
public class IOUtil {

    private static final Logger logger = LoggerFactory.getLogger(IOUtil.class);

    /**
     * @param io
     */
    public static void close(Closeable... io) {
        for(Closeable temp: io) {
            try {
                if (null != temp) temp.close();
            } catch (IOException e) {
                logger.error("IOUtil: close", e);
            }
        }
    }

    /**
     * @param io
     */
    public static <T extends Closeable> void closeAll(T... io) {
        for(Closeable temp: io) {
            try {
                if (null != temp) temp.close();
            } catch (IOException e) {
                logger.error("IOUtil: closeAll", e);
            }
        }
    }
}
