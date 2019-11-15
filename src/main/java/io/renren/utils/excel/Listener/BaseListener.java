package io.renren.utils.excel.Listener;

import com.alibaba.excel.event.AnalysisEventListener;

import java.util.List;

public abstract class BaseListener<T> extends AnalysisEventListener<T> {
    public abstract List<T> getDatas();
}
