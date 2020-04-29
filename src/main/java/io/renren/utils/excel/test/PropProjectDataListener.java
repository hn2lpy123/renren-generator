package io.renren.utils.excel.test;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 直接用map接收数据
 *
 * @author Jiaju Zhuang
 */
public class PropProjectDataListener extends AnalysisEventListener<Map<Integer, String>> {
    private static final Logger LOGGER = LoggerFactory.getLogger(PropProjectDataListener.class);

    private Set<String> units = Sets.newHashSet();

    private List<String> projects = Lists.newArrayList();

    private List<String> updateProjects = Lists.newArrayList();

    @Override
    public void invoke(Map<Integer, String> data, AnalysisContext context) {
        LOGGER.info("解析到一条数据:{}", JSON.toJSONString(data));
        ExcelTest.companyInfoSet.add(new CompanyInfo(String.format(ExcelTest.PARAM_FORMAT, data.get(0)), String.format(ExcelTest.PARAM_FORMAT, data.get(4))));
        String tmp = data.get(2);
        String unitSql;
        String projectSql;
        String updateProjectSql;
        dealCompanyProjectRel(data);
        if ("是".equals(tmp)) {
            unitSql = String.format(ExcelTest.UNIT_SQL_FORMART, String.format(ExcelTest.PARAM_FORMAT, data.get(1)), String.format(ExcelTest.PARAM_FORMAT, "1"),
                    "id property_company_id", String.format(ExcelTest.PARAM_FORMAT, data.get(0)));
            projectSql = String.format(ExcelTest.PROJECT_SQL_FORMAT, String.format(ExcelTest.PARAM_FORMAT, data.get(3)), String.format(ExcelTest.PARAM_FORMAT, data.get(0)));
            updateProjectSql = String.format(ExcelTest.UPDATE_PROJECT_SQL_FORMAT, String.format(ExcelTest.PARAM_FORMAT, data.get(1)), String.format(ExcelTest.PARAM_FORMAT, data.get(0)),
                    String.format(ExcelTest.PARAM_FORMAT, data.get(3)), String.format(ExcelTest.PARAM_FORMAT, data.get(0)));
            projects.add(projectSql);
            updateProjects.add(updateProjectSql);
        } else {
            unitSql = String.format(ExcelTest.UNIT_SQL_FORMART, String.format(ExcelTest.PARAM_FORMAT, data.get(1)), String.format(ExcelTest.PARAM_FORMAT, "0"),
                    String.format(ExcelTest.PARAM_FORMAT, "-1") + " property_company_id", String.format(ExcelTest.PARAM_FORMAT, data.get(0)));
        }
        units.add(unitSql);
    }

    private void dealCompanyProjectRel(Map<Integer, String> data) {
        List<String> tmp = ExcelTest.companyProjectRel.get(data.get(0));
        if (tmp == null) {
            tmp = Lists.newArrayList();
        }
        if ("是".equals(data.get(2))) {
            tmp.add(data.get(3));
        }
        ExcelTest.companyProjectRel.put(data.get(0), tmp);
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

        File file3 = new File("D:\\unit_project_relation.sql");
        FileUtils.writeByteArrayToFile(file3, "".getBytes("UTF-8"),false);
        for (String sql : updateProjects) {
            byte[] sourceBytes = sql.getBytes("UTF-8");
            if(null != sourceBytes){
                FileUtils.writeByteArrayToFile(file3, sourceBytes,true);
            }
        }
        updateProjects.clear();
    }

    public Set<String> getUnits() {
        return units;
    }
}
