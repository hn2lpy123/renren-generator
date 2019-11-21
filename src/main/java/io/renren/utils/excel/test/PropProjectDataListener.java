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
public class PropProjectDataListener extends AnalysisEventListener<Map<Integer, String>> {
    private static final Logger LOGGER = LoggerFactory.getLogger(NoModleDataListener.class);
    /**
     * 每隔1000条存储数据库，实际使用中可以3000条，然后清理list ，方便内存回收
     */
    private static final String UNIT_SQL_FORMART = "INSERT INTO t_market_integrating_mkt_unit(integrating_mkt_company_id, unit_name, is_prop_company, property_company_id)\n" +
            "SELECT id integrating_mkt_company_id, %s unit_name, b%s is_prop_company, %s FROM\n" +
            "t_market_integrating_mkt_company WHERE company_name=%s AND `status`=1;\n";

    public static final String PROJECT_SQL_FORMAT = "INSERT INTO t_market_property_project(property_company_id, property_project_name)\n" +
            "SELECT id property_company_id, %s property_project_name FROM t_market_integrating_mkt_company\n" +
            "WHERE company_name=%s AND `status`=1;\n";

    public static final String PARAM_FORMAT = "'%s'";

    private List<String> units = new ArrayList<String>();

    private List<String> projects = new ArrayList<String>();

    @Override
    public void invoke(Map<Integer, String> data, AnalysisContext context) {
        LOGGER.info("解析到一条数据:{}", JSON.toJSONString(data));
        ExcelTest.companyInfoSet.add(new CompanyInfo(String.format(PARAM_FORMAT, data.get(0)), String.format(PARAM_FORMAT, data.get(4))));
        String tmp = data.get(2);
        String unitSql;
        String projectSql;
        if ("是".equals(tmp)) {
            unitSql = String.format(UNIT_SQL_FORMART, String.format(PARAM_FORMAT, data.get(1)), String.format(PARAM_FORMAT, "1"),
                    "id property_company_id", String.format(PARAM_FORMAT, data.get(0)));
            projectSql = String.format(PROJECT_SQL_FORMAT, String.format(PARAM_FORMAT, data.get(3)), String.format(PARAM_FORMAT, data.get(0)));
            projects.add(projectSql);
        } else {
            unitSql = String.format(UNIT_SQL_FORMART, String.format(PARAM_FORMAT, data.get(1)), String.format(PARAM_FORMAT, "0"),
                    String.format(PARAM_FORMAT, "-1") + " property_company_id", String.format(PARAM_FORMAT, data.get(0)));
        }
        units.add(unitSql);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        try {
            saveData();
        } catch (IOException e) {
            e.printStackTrace();
        }
        LOGGER.info("所有数据解析完成！");
        units.clear();
    }

    private void saveData() throws IOException {
        File file = new File("D:\\step3 物业公司下属单位.sql");
        FileUtils.writeByteArrayToFile(file, "".getBytes("UTF-8"),false);
        for (String sql : units) {
            byte[] sourceBytes = sql.getBytes("UTF-8");
            if(null != sourceBytes){
                FileUtils.writeByteArrayToFile(file, sourceBytes,true);
            }
        }

        File file2 = new File("D:\\step4 物业公司楼盘.sql");
        FileUtils.writeByteArrayToFile(file2, "".getBytes("UTF-8"),false);
        for (String sql : projects) {
            byte[] sourceBytes = sql.getBytes("UTF-8");
            if(null != sourceBytes){
                FileUtils.writeByteArrayToFile(file2, sourceBytes,true);
            }
        }
    }

    public List<String> getUnits() {
        return units;
    }
}
