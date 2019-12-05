package io.renren.utils.excel.Listener;

import com.alibaba.excel.event.AnalysisEventListener;
import com.google.common.collect.Lists;

import java.util.List;

public abstract class BaseImportListener<T> extends AnalysisEventListener<T> {

    protected List<T> datas = Lists.newArrayList();

    public List<T> getDatas() {
        return datas;
    }
}
