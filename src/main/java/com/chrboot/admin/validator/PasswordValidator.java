package com.chrboot.admin.validator;

import com.chrboot.admin.annotation.Password;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<Password, String> {


    /**
     *
     * @param s 前端提交的数值
     * @param constraintValidatorContext 校验上下文
     * @return
     */
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {

        if(s==null){
            return true;
        }
        int passwordLength =  s.length();
        if(passwordLength<=10 && passwordLength>=5){
            return true;
        }
        return false;
    }
}
