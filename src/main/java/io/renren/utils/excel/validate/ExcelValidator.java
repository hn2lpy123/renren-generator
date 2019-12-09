package io.renren.utils.excel.validate;

import io.renren.bean.ExcelRow;

public interface ExcelValidator<T extends ExcelRow> {
    boolean validate(T r);
}
