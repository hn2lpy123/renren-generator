package io.renren.utils.excel.listener;

import com.alibaba.excel.event.AnalysisEventListener;
import com.google.common.collect.Lists;

import java.util.List;

public abstract class BaseImportListener<T> extends AnalysisEventListener<T> {

    protected List<T> rows;

    public BaseImportListener() {
        rows = Lists.newArrayList();
    }

    public List<T> getRows() {
        return rows;
    }
}
