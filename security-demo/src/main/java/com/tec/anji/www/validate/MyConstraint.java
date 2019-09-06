package com.tec.anji.www.validate;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Constraint(validatedBy = {MyConstraintValidator.class})
@Target({METHOD, FIELD})
@Retention(RUNTIME)
public @interface MyConstraint {

    String message() default "{com.tec.anji.www.validate.MyConstraint.message}";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
