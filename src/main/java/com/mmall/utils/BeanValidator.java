package com.mmall.utils;

/**
 * @ClassName BeanValidator
 * @Description TODO
 * @Author gaopeng
 * @Date 2020/11/14 9:34
 * @Version 1.0
 **/

import com.google.common.base.Preconditions;
import com.mmall.exception.ParamException;
import com.mmall.exception.PerssionException;
import org.apache.commons.collections.MapUtils;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSourceExtensionsKt;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.*;

/**
  * @Author gaopeng
  * @Description //TODO 校验请求参数(校验工具)
  * @Date 9:35 2020/11/14
  * @param
  * @return
  **/
public class BeanValidator {

    //全局的校验方法
    private static ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();


    public static <T> Map<String,String> validate(T t,Class... groups) {
        Validator validator = validatorFactory.getValidator();
        Set validateResult = validator.validate(t, groups);
        if (validateResult.isEmpty()) {//当集合为空时
            return Collections.emptyMap();//创建一个空map集合
        } else {
            LinkedHashMap errors = new LinkedHashMap();
            Iterator iterator = validateResult.iterator();
            while (iterator.hasNext()) {
                ConstraintViolation violation = (ConstraintViolation) iterator.next();
                //将错误信息放入error集合中
                errors.put(violation.getPropertyPath().toString(), violation.getMessage());//参数1：L参数字段 参数2：参数字段对应的错误信息
            }
            return errors;
        }

    }


    /**
      * @Author gaopeng
      * @Description //TODO 请求参数为集合的校验方法
      * @Date 10:05 2020/11/14
      * @param
      * @return
      **/
    public static Map<String, String> validatorList(Collection<?> collections) {

        Preconditions.checkNotNull(collections);//判断当前集合是否为空，为空抛出异常

        //遍历集合
        Iterator<?> iterator = collections.iterator();
        Map errors;

        do {
            //判断是否有值
            if (!iterator.hasNext()) {
                return Collections.emptyMap();//创建一个空集合返回
            }
            Object object = iterator.next();
            //调用单个的validator方法
            errors = validate(object,new Class[0]);
        } while (errors.isEmpty());

        return errors;

    }


    public static Map<String, String> validateObject(Object first,Object... objects) {

        if (objects != null && objects.length > 0) {//调用集合的方法
            return validateObject(first, objects);
        } else {//调用单个的validator方法
            return validate(first,new Class[0]);
        }
    }


    public static void check(Object param) throws ParamException{

        Map<String, String> map = BeanValidator.validateObject(param);
        if (MapUtils.isNotEmpty(map)) {
            throw new ParamException(map.toString());
        }
    }












}
