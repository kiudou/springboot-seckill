package com.imooc.miaosha.validator;

import com.imooc.miaosha.util.ValidatorUtil;
import org.apache.commons.lang3.StringUtils;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IsMobileValidator implements ConstraintValidator<IsMobile, String> {

    private boolean required = false;

    @Override
    public void initialize(IsMobile constraintAnnotation) {
        required = constraintAnnotation.required();
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if(required) { //值是必须的
            return ValidatorUtil.isMobile(s);
        } else { //值不必须
            if(StringUtils.isEmpty(s)) { //空也可以
                return true;
            } else { //不为空的情况下，值必须符合要求
                return ValidatorUtil.isMobile(s);
            }

        }
    }
}
