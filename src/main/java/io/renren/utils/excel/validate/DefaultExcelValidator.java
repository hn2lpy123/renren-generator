package io.renren.utils.excel.validate;

import io.renren.bean.ExcelRow;
import io.renren.utils.excel.annotation.NotDuplicate;

import javax.validation.ConstraintViolation;
import javax.validation.groups.Default;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 默认数据验证类
 *
 * @Author lipingyu
 * @Date 2019-12-09
 */
public class DefaultExcelValidator implements ExcelValidator {

    private Map<String, Set<Object>> fieldMap = new HashMap<>();

    @Override
    public boolean validate(ExcelRow r) {
        if (hibernateValidate(r)) {
            return duplicateValidate(r);
        }
        return false;
    }

    private boolean hibernateValidate(ExcelRow t) {
        Set<ConstraintViolation<ExcelRow>> validateSet = HibernateValidator.getValidator().validate(t, Default.class);
        if (validateSet != null && !validateSet.isEmpty()) {
            ConstraintViolation<ExcelRow> constraint = validateSet.stream().findAny().orElse(null);
            t.setValidateCode(ExcelRow.FAILED_CODE);
            t.setValidateMessage(constraint.getMessage());
            return false;
        }
        return true;
    }

    private boolean duplicateValidate(ExcelRow r) {
        Field[] fields = r.getClass().getDeclaredFields();
        for (Field field : fields) {
            String name = field.getName();
            field.setAccessible(true);
            Object value = null;
            try {
                value = field.get(r);
            } catch (IllegalAccessException e) {
                r.setValidateCode(ExcelRow.FAILED_CODE);
                r.setValidateMessage(name + "属性值无法获取");
                return false;
            }
            field.setAccessible(false);
            if (field.isAnnotationPresent(NotDuplicate.class)) {
                if (fieldMap.containsKey(name)) {
                    if (fieldMap.get(name).contains(value)) {
                        NotDuplicate notDuplicate = field.getAnnotation(NotDuplicate.class);
                        r.setValidateCode(ExcelRow.FAILED_CODE);
                        r.setValidateMessage(notDuplicate.message());
                        return false;
                    } else {
                        fieldMap.get(name).add(value);
                    }
                } else {
                    Set<Object> values = new HashSet<>();
                    values.add(value);
                    fieldMap.put(name, values);
                }
            }
            continue;
        }
        return true;
    }
}
