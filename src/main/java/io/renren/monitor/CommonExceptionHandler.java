package io.renren.monitor;

import io.renren.utils.constant.CommonCodeType;
import io.renren.utils.RRException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @author lipingyu
 * 公共异常处理
 * @date 2019/10/19 8:23
 */
@ControllerAdvice
public class CommonExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(CommonExceptionHandler.class);

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ExceptionResult> handleRuntimeException(RuntimeException e) {
        logger.error("handleRuntimeException", e);
        if (e instanceof RRException) {
            return ResponseEntity.status(200).body(new
                    ExceptionResult(((RRException)e).getCode(), e.getMessage()));
        } else {
            return ResponseEntity.status(200).body(new
                    ExceptionResult(CommonCodeType.FAILURE.getCode(), e.getMessage()));
        }
    }
}
