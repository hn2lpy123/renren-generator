package io.renren.utils.excel.test;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.fastjson.JSON;
import io.renren.utils.excel.Listener.NoModleDataListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 直接用map接收数据
 *
 * @author Jiaju Zhuang
 */
public class NotPropProjectDataListener extends AnalysisEventListener<Map<Integer, String>> {
    private static final Logger LOGGER = LoggerFactory.getLogger(NoModleDataListener.class);
    /**
     * 每隔1000条存储数据库，实际使用中可以3000条，然后清理list ，方便内存回收
     */
    private static final String SQL_FORMART = "INSERT INTO t_market_integrating_mkt_unit(integrating_mkt_company_id, unit_name, is_prop_company, property_company_id)\n" +
            "SELECT id integrating_mkt_company_id, %S unit_name, b'0' is_prop_company, '-1' property_company_id FROM\n" +
            "t_market_integrating_mkt_company WHERE company_name=%S AND `status`=1;";

    public static final String PARAM_FORMAT = "'%s'";

    private static final int BATCH_COUNT = 1000;

    List<Map<Integer, String>> list = new ArrayList<Map<Integer, String>>();

    List<String> datas = new ArrayList<String>();

    @Override
    public void invoke(Map<Integer, String> data, AnalysisContext context) {
        LOGGER.info("解析到一条数据:{}", JSON.toJSONString(data));
        String sql = String.format(SQL_FORMART, String.format(PARAM_FORMAT, data.get(1)), String.format(PARAM_FORMAT, data.get(0)));
        LOGGER.info("sql-format:{}", sql);
        datas.add(sql);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        saveData();
        LOGGER.info("所有数据解析完成！");
    }

    /**
     * 加上存储数据库
     */
    private void saveData() {
        LOGGER.info("{}条数据，开始存储数据库！", list.size());
        LOGGER.info("存储数据库成功！");
    }
    public List<String> getDatas() {
        return datas;
    }
}
