package com.chr.admin.aspect;


import com.chr.common.annotation.AutoFill;
import com.chr.common.constant.AutoFillConstant;
import com.chr.common.enums.AutoFillType;
import com.chr.common.utils.context.BaseContext;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import java.lang.reflect.Method;
import java.time.LocalDateTime;

/**
 * 自定义切面，实现公共字段自动填充
 * 实体对象要作为第一个参数才会自动填充
 */
@Aspect
@Component
@Slf4j
public class AutoFillAspect {


    //设置切入点
    @Pointcut("execution(* com.chr.admin.mapper.*.*(..)) && @annotation(com.chr.common.annotation.AutoFill)")
    public void autoFillPointCut(){}

    @Pointcut("execution(* com.chr.admin.mapper.*.insert*(..)) || execution(* com.chr.admin.mapper.*.update*(..))")
    public void insertUpdatePointCut(){}

    public void concatPublicField(AutoFillType value, Object entity){
        //准备填充数据
        LocalDateTime now = LocalDateTime.now();
        Long id = BaseContext.getCurrentId();
        //根据对应的属性反射赋值
        if(value == AutoFillType.INSERT){
            try {
                //通过反射获取实体对象具体的方法  @param 方法名 @param 方法需要的参数 @return 具体的方法
                Method setUpdateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME, LocalDateTime.class);
                Method setCreateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_TIME, LocalDateTime.class);
                Method setUpdateUser = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_USER, Long.class);
                Method setCreateUser = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_USER, Long.class);
                //通过反射方法为对象赋值 @param 需要赋值的对象 @param 赋值的数据
                setUpdateTime.invoke(entity,now);
                setCreateTime.invoke(entity,now);
                setUpdateUser.invoke(entity,id);
                setCreateUser.invoke(entity,id);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }else{
            try {
                //通过反射获取实体对象具体的方法  @param 方法名 @param 方法需要的参数 @return 具体的方法
                Method setUpdateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME, LocalDateTime.class);
                Method setUpdateUser = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_USER, Long.class);
                //通过反射方法为对象赋值 @param 需要赋值的对象 @param 赋值的数据
                setUpdateTime.invoke(entity,now);
                setUpdateUser.invoke(entity,id);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Before("autoFillPointCut()")
    public void autoFill(JoinPoint joinPoint){
        log.info("开始进行公共字段自动填充.....");
        //获取到当前被拦截的方法上的数据库操作类型
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature(); //获取方法签名
        AutoFill autoFill = methodSignature.getMethod().getAnnotation(AutoFill.class); //获取方法对应注解对象
        AutoFillType value = autoFill.value(); //获取数据库操作类型
        //获取方法参数
        Object[] args = joinPoint.getArgs();//获取所有的方法参数
        if(args == null || args.length == 0){ //防止出现空指针
            return;
        }
        Object entity = args[0];//拿到需要填充的对象
        concatPublicField(value, entity);
    }

    @Before("insertUpdatePointCut()")
    public void insertUpdate(JoinPoint joinPoint){
        log.info("开始进行公共字段自动填充.....");
        //获取到当前被拦截的方法上的数据库操作类型
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature(); //获取方法签名
        String methodName = methodSignature.getName(); //获取方法的名称
        AutoFillType value = methodName.startsWith("insert") ? AutoFillType.INSERT : AutoFillType.UPDATE; //获取数据库操作类型
        //获取方法参数
        Object[] args = joinPoint.getArgs();//获取所有的方法参数
        if(args == null || args.length == 0){ //防止出现空指针
            return;
        }
        Object entity = args[0];//拿到需要填充的对象
        concatPublicField(value, entity);
    }

}
