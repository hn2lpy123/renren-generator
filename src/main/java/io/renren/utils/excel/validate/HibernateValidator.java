package io.renren.utils.excel.validate;

import io.renren.bean.ExcelRow;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.groups.Default;
import java.util.Set;

public class HibernateValidator implements ExcelValidator {

    private static Validator validator = Validation.byProvider( org.hibernate.validator.HibernateValidator.class )
            .configure()
            .addProperty( "hibernate.validator.fail_fast", "true" )
            .buildValidatorFactory().getValidator();

    private HibernateValidator() {
    }

    public static HibernateValidator getInstance() {
        return HibernateValidatorBuilder.hibernateValidator;
    }

    private static class HibernateValidatorBuilder {
        private static HibernateValidator hibernateValidator = new HibernateValidator();
    }

    @Override
    public boolean validate(ExcelRow r) {
        Set<ConstraintViolation<ExcelRow>> validateSet = validator.validate(r, Default.class);
        if (validateSet != null && !validateSet.isEmpty()) {
            ConstraintViolation<ExcelRow> constraint = validateSet.stream().findAny().orElse(null);
            r.setValidateCode(ExcelRow.FAILED_CODE);
            r.setValidateMessage(constraint.getMessage());
            return false;
        }
        return true;
    }
}
