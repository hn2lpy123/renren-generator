package io.renren.utils.excel.handler;

import io.renren.bean.ExcelRow;

import java.util.List;

public abstract class ExcelDataHandler<T extends ExcelRow> {

    public void dealInvorkSuccessRow(List<T> rows, T t) {
        rows.add(t);
    }

    public void dealInvorkErrorRow(List<T> rows, T t){

    }

    public abstract void saveData(List<T> rows);

    public void onException(Exception ex) throws Exception {
        throw ex;
    }

    public String errorMsg() {
        return null;
    }
}
