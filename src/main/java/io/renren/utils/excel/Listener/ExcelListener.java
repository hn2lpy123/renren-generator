package io.renren.utils.excel.Listener;

import com.alibaba.excel.context.AnalysisContext;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author lipingyu
 * @Description 监听类，可以自定义
 * @Date 2018-06-05
 * @Time 16:58
 */
public class ExcelListener extends BaseListener {

    //自定义用于暂时存储data。
    //可以通过实例获取该值
    private List<Object> datas = new ArrayList<>();

    /**
     * 通过 AnalysisContext 对象还可以获取当前 sheet，当前行等数据
     */
    @Override
    public void invoke(Object object, AnalysisContext context) {
        //数据存储到list，供批量处理，或后续自己业务逻辑处理。
        datas.add(object);
        //根据业务自行 do something
        doSomething();

    }

    /**
     * 根据业务自行实现该方法
     */
    private void doSomething() {
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        /*
            datas.clear();
            解析结束销毁不用的资源
         */
    }

    public List<Object> getDatas() {
        return datas;
    }

    public void setDatas(List<Object> datas) {
        this.datas = datas;
    }
}
