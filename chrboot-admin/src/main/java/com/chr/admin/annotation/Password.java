package com.chr.admin.annotation;


import com.chr.admin.validator.PasswordValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;



@Documented
@Constraint(
        validatedBy = {
                PasswordValidator.class
        } //校验器实现真正的校验
)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Password {


    String message() default "{password.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
