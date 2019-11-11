//package io.renren.monitor;
//
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.AfterReturning;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Pointcut;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Component;
//
//import java.util.Arrays;
//
///**
// * @ClassName MapperAspect
// * @Description dao层执行时间监控
// * @Author lipingyu
// * @Date 2019/10/24 10:04
// * @Version 1.0
// */
//@Aspect
//@Component
//public class MapperMonitor {
//
//    private static final Logger logger = LoggerFactory.getLogger(MapperMonitor.class);
//
//    @AfterReturning("execution(* com.eifmemberprobe.dao.*Dao.*(..))")
//    public void logServiceAccess(JoinPoint joinPoint) {
//    }
//
//
//    /**
//     * 监控com.eifmemberprobe.dao.*Dao包及其子包的所有public方法
//     */
//    @Pointcut("execution(* com.eifmemberprobe.dao.*Dao.*(..))")
//    private void pointCutMethod() {
//    }
//
//    /**
//     * 声明环绕通知
//     *
//     * @param pjp
//     * @return
//     * @throws Throwable
//     */
//    @Around("pointCutMethod()")
//    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
//        long begin = System.currentTimeMillis();
//        Object obj = pjp.proceed();
//        long end = System.currentTimeMillis();
//        if (end - begin > 3000) {
//            logger.warn("调用Mapper方法：{}，参数：{}，执行耗时：{}毫秒",
//                    pjp.getSignature().toString(), Arrays.toString(pjp.getArgs()),
//                    end - begin);
//        } else {
//            logger.debug("调用Mapper方法：{}，参数：{}，执行耗时：{}毫秒",
//                    pjp.getSignature().toString(), Arrays.toString(pjp.getArgs()),
//                    end - begin);
//        }
//        return obj;
//    }
//}