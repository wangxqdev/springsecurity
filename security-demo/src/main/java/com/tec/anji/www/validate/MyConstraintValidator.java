package com.tec.anji.www.validate;

import com.tec.anji.www.service.HelloService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class MyConstraintValidator implements ConstraintValidator<MyConstraint, Object> {

    private Log log = LogFactory.getLog(getClass());

    @Autowired
    private HelloService helloService;

    @Override
    public void initialize(MyConstraint constraintAnnotation) {
        log.info("MyConstraintValidator initialize");
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        log.info("MyConstraintValidator isValid");
        log.info(helloService.greeting((String) value));
        return false;
    }
}
