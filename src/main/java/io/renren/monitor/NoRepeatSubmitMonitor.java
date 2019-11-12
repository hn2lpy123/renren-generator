package io.renren.monitor;

import com.google.common.cache.Cache;
import io.renren.utils.RRException;
import io.renren.utils.annotation.NoRepeatSubmit;
import io.renren.utils.constant.CommonCodeType;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
/**
 * @功能描述 aop解析注解
 * @author www.gaozz.club
 * @date 2018-08-26
 */
public class NoRepeatSubmitMonitor {

    private static final Logger logger = LoggerFactory.getLogger(NoRepeatSubmitMonitor.class);

    @Autowired
    private Cache<String, Integer> cache;

    @Around("execution(* io.renren.controller.*Controller.*(..)) && @annotation(nrs)")
    public Object arround(ProceedingJoinPoint pjp, NoRepeatSubmit nrs) {
        try {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            String sessionId = RequestContextHolder.getRequestAttributes().getSessionId();
            HttpServletRequest request = attributes.getRequest();
            String key = sessionId + "-" + request.getServletPath();
            if (cache.getIfPresent(key) == null) {// 如果缓存中有这个url视为重复提交
                Object o = pjp.proceed();
                cache.put(key, 0);
                return o;
            } else {
                logger.error("重复提交");
                throw new RRException(CommonCodeType.REPEAT_SUBMIT);
            }
        } catch (Throwable e) {
            logger.error("验证重复提交时出现异常!", e);
            if (e instanceof RRException) {
                throw new RRException(CommonCodeType.REPEAT_SUBMIT);
            }
            throw new RuntimeException(e);
        }
    }
}
