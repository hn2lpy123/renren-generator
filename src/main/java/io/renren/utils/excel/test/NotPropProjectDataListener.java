package io.renren.utils.excel.test;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.fastjson.JSON;
import io.renren.utils.excel.Listener.NoModleDataListener;
import io.renren.utils.excel.bean.CompanyInfo;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
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
            "t_market_integrating_mkt_company WHERE company_name=%S AND `status`=1;\n";

    public static final String PARAM_FORMAT = "'%s'";

    private List<String> datas = new ArrayList<String>();

    @Override
    public void invoke(Map<Integer, String> data, AnalysisContext context) {
        LOGGER.info("解析到一条数据:{}", JSON.toJSONString(data));
        ExcelTest.companyInfoSet.add(new CompanyInfo(String.format(PARAM_FORMAT, data.get(0)), String.format(PARAM_FORMAT, data.get(2))));
        String sql = String.format(SQL_FORMART, String.format(PARAM_FORMAT, data.get(1)), String.format(PARAM_FORMAT, data.get(0)));
        datas.add(sql);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        try {
            saveData(context.getCurrentSheet().getSheetName());
        } catch (IOException e) {
            e.printStackTrace();
        }
        LOGGER.info("所有数据解析完成！");
        datas.clear();
    }

    private void saveData(String sheetName) throws IOException {
        File file = null;
        if (sheetName.equals("地产公司")) {
            file = new File("D:\\step2 地产公司下属单位.sql");
        } else {
            file = new File("D:\\step5 其他产业集团下属单位.sql");
        }
        FileUtils.writeByteArrayToFile(file, "".getBytes("UTF-8"),false);
        for (String sql : datas) {
            byte[] sourceBytes = sql.getBytes("UTF-8");
            if(null != sourceBytes){
                FileUtils.writeByteArrayToFile(file, sourceBytes,true);
            }
        }
    }

    public List<String> getDatas() {
        return datas;
    }
}
