package io.renren.utils.excel.test;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class AddProjectDataListener extends AnalysisEventListener<Map<Integer, String>> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AddProjectDataListener.class);

    private List<String> projects = Lists.newArrayList();

    private static String DELETE_ONE_PROJECT = "DELETE FROM t_market_property_project WHERE id=(SELECT id FROM(\n" +
            "SELECT id FROM t_market_property_project WHERE property_project_name=%s AND `status`=1 LIMIT 1) AS t);\n";

    private static String DELETE_PROJECT_BYDATE = "UPDATE t_market_property_project SET `status` = 0 WHERE update_time < '2019-11-01';\n";

    private List<String> deletes = Lists.newArrayList();

    @Override
    public void invoke(Map<Integer, String> data, AnalysisContext analysisContext) {
        LOGGER.info("解析到一条数据:{}", JSON.toJSONString(data));
        String projectSql = String.format(ExcelTest.ADD_PROJECT_SQL_FORMAT2, String.format(ExcelTest.PARAM_FORMAT, data.get(2)),
                String.format(ExcelTest.PARAM_FORMAT, data.get(0)), String.format(ExcelTest.PARAM_FORMAT, data.get(2)));
        deletes.add(String.format(DELETE_ONE_PROJECT, String.format(ExcelTest.PARAM_FORMAT, data.get(2))));
        projects.add(projectSql);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        try {
            saveData();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveData() throws IOException {
        File file = new File("D:\\add_project.sql");
        FileUtils.writeByteArrayToFile(file, DELETE_PROJECT_BYDATE.getBytes("UTF-8"),false);
        for (String sql : projects) {
            byte[] sourceBytes = sql.getBytes("UTF-8");
            if(null != sourceBytes){
                FileUtils.writeByteArrayToFile(file, sourceBytes,true);
            }
        }

        File file2 = new File("D:\\还原.sql");
        FileUtils.writeByteArrayToFile(file2, "".getBytes("UTF-8"),false);
        for (String sql : deletes) {
            byte[] sourceBytes = sql.getBytes("UTF-8");
            if(null != sourceBytes){
                FileUtils.writeByteArrayToFile(file2, sourceBytes,true);
            }
        }
    }
}
