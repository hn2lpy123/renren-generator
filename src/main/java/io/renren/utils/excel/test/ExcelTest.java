package io.renren.utils.excel.test;

import com.alibaba.excel.EasyExcel;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class ExcelTest {

    public static final String UNIT_SQL_FORMART = "INSERT INTO t_market_integrating_mkt_unit(integrating_mkt_company_id, unit_name, is_prop_company, property_company_id)\n" +
            "SELECT id integrating_mkt_company_id, %s unit_name, b%s is_prop_company, %s FROM\n" +
            "t_market_integrating_mkt_company WHERE company_name=%s AND `status`=1;\n";

    public static final String PROJECT_SQL_FORMAT = "INSERT INTO t_market_property_project(property_company_id, property_project_name)\n" +
            "SELECT id property_company_id, %s property_project_name FROM t_market_integrating_mkt_company\n" +
            "WHERE company_name=%s AND `status`=1;\n";

    public static final String  UPDATE_PROJECT_SQL_FORMAT = "UPDATE t_market_property_project SET property_unit_id=(\n" +
            "SELECT id FROM t_market_integrating_mkt_unit WHERE `status`=1 AND unit_name=%s AND\n" +
            "integrating_mkt_company_id = (SELECT id FROM t_market_integrating_mkt_company WHERE `status`=1 AND\n" +
            "company_name = %s LIMIT 1)) WHERE `status`=1 AND property_project_name=%s AND\n" +
            "property_company_id = (SELECT id FROM t_market_integrating_mkt_company WHERE `status`=1 AND\n" +
            "company_name = %s LIMIT 1);\n";

    public static final String PARAM_FORMAT = "'%s'";

    public static Set<CompanyInfo> companyInfoSet = new HashSet<>();

    public static Map<String, List<String>> companyProjectRel = new HashMap<>();

    public static void main(String[] args) throws IOException {
        String fileName ="D:\\test.xlsx";
        System.out.println("====start====");
        companyInfoSet.clear();
        companyProjectRel.clear();
//        EasyExcel.read(fileName, new NotPropProjectDataListener()).sheet("地产公司").doRead();
//        EasyExcel.read(fileName, new NotPropProjectDataListener()).sheet("其他产业集团").doRead();
        EasyExcel.read(fileName, new PropProjectDataListener()).sheet("物业公司").doRead();
        saveCompanyProjectRel(companyProjectRel);
//        saveCompanyInfo(companyInfoSet);
        System.out.println("====end====");
    }

    private static void saveCompanyProjectRel(Map<String, List<String>> companyProjectRel) throws IOException {
        File file = new File("D:\\楼盘变更数据处理.sql");
        String updateStr = "UPDATE t_market_property_project SET property_company_id=(\n" +
                "SELECT id FROM t_market_integrating_mkt_company WHERE company_name='珠三角物业公司' AND `status`=1)\n" +
                "WHERE `status`=1 AND property_project_name NOT IN ('其他楼盘', '省物业公司领导', '省物业公司各部门') AND property_company_id=(\n" +
                "SELECT id FROM t_market_integrating_mkt_company WHERE company_name='广东物业公司' AND `status`=1);\n";
        String updateStr2 = "UPDATE t_market_property_project SET `status`=0 WHERE `status`=1 AND property_project_name IN ('省物业公司领导', '省物业公司各部门') AND \n" +
                "property_company_id IN (SELECT id FROM t_market_integrating_mkt_company WHERE company_name IN\n" +
                "('物业集团总部','北京物业公司','内蒙古物业公司','黑龙江物业公司','吉林物业公司','辽宁物业公司','山西物业公司','河南物业公司','中原物业公司','安徽物业公司','陕西物业公司','甘肃物业公司','湖北物业公司','山东物业公司',\n" +
                "'江苏物业公司','华东物业公司','四川物业公司','云南物业公司','重庆物业公司','贵州物业公司','广西物业公司','海南物业公司','福建物业公司','江西物业公司','湖南物业公司','珠三角物业公司','深圳物业公司','新疆物业公司'));\n" +
                "\n";
        FileUtils.writeByteArrayToFile(file, (updateStr + updateStr2).getBytes("UTF-8"),false);
        Iterator<Map.Entry<String, List<String>>> entries = companyProjectRel.entrySet().iterator();
        while(entries.hasNext()){
            Map.Entry<String, List<String>> entry = entries.next();
            String key = entry.getKey();
            List<String> value = entry.getValue();
            if (key.equals("物业集团总部")){
                String str = String.format(PROJECT_SQL_FORMAT, String.format(PARAM_FORMAT, "物业集团总部各部门"), String.format(PARAM_FORMAT, "物业集团总部"));
                FileUtils.writeByteArrayToFile(file, str.getBytes("UTF-8"),true);
                continue;
            }
            if (key.equals("广东物业公司")){
                continue;
            }

//            if (!value.contains("省物业公司领导")) {
//                String str = String.format(PROJECT_SQL_FORMAT, String.format(PARAM_FORMAT, "省物业公司领导"), String.format(PARAM_FORMAT, key));
//                FileUtils.writeByteArrayToFile(file, str.getBytes("UTF-8"),true);
//            }
//            if (!value.contains("省物业公司各部门")) {
//                String str = String.format(PROJECT_SQL_FORMAT, String.format(PARAM_FORMAT, "省物业公司各部门"), String.format(PARAM_FORMAT, key));
//                FileUtils.writeByteArrayToFile(file, str.getBytes("UTF-8"),true);
//            }
            String str1 = String.format(PROJECT_SQL_FORMAT, String.format(PARAM_FORMAT, "省物业公司领导"), String.format(PARAM_FORMAT, key));
            FileUtils.writeByteArrayToFile(file, str1.getBytes("UTF-8"),true);
            String str2 = String.format(PROJECT_SQL_FORMAT, String.format(PARAM_FORMAT, "省物业公司各部门"), String.format(PARAM_FORMAT, key));
            FileUtils.writeByteArrayToFile(file, str2.getBytes("UTF-8"),true);
        }
    }


    private static void saveCompanyInfo(Set<CompanyInfo> companyInfoSet) throws IOException {
        File file = new File("D:\\step1 公司.sql");
        String initSql = "UPDATE t_market_property_project SET `status`=0 WHERE property_company_id IN\n" +
                "(SELECT id FROM t_market_integrating_mkt_company WHERE company_name IN \n" +
                "(\n" +
                "'物业集团总部','北京物业公司','内蒙古物业公司','黑龙江物业公司','吉林物业公司','辽宁物业公司','山西物业公司','河南物业公司','中原物业公司','安徽物业公司',\n" +
                "'陕西物业公司','甘肃物业公司','湖北物业公司','山东物业公司','江苏物业公司','华东物业公司','四川物业公司','云南物业公司','重庆物业公司','贵州物业公司',\n" +
                "'广西物业公司','海南物业公司','福建物业公司','江西物业公司','湖南物业公司','珠三角物业公司','广东物业公司','深圳物业公司','新疆物业公司'\n" +
                "));\n" +
                "\n" +
                "UPDATE t_market_integrating_mkt_unit SET `status`=0 WHERE integrating_mkt_company_id IN\n" +
                "(SELECT id FROM t_market_integrating_mkt_company WHERE company_name IN \n" +
                "(\n" +
                "'地产集团总部', '北京公司', '内蒙古公司', '黑龙江公司', '吉林公司', '辽宁公司', '山西公司', '中原公司', '安徽公司', '陕西公司', '甘肃公司','湖北公司','山东公司','江苏公司','华东公司','四川公司','云南公司','重庆公司',\n" +
                "'贵州公司','广西公司','海南公司','福建公司','江西公司','湖南公司','珠三角公司','深圳公司','新疆公司','华东公司','物业集团总部','北京物业公司','内蒙古物业公司','黑龙江物业公司','吉林物业公司','辽宁物业公司','山西物业公司','河南物业公司','中原物业公司','安徽物业公司','陕西物业公司','甘肃物业公司','湖北物业公司','山东物业公司',\n" +
                "'江苏物业公司','华东物业公司','四川物业公司','云南物业公司','重庆物业公司','贵州物业公司','广西物业公司','海南物业公司','福建物业公司','江西物业公司','湖南物业公司','珠三角物业公司','广东物业公司','深圳物业公司','新疆物业公司',\n" +
                "'华东物业公司','沧州童世界','华北健康','东北童世界','东北童世界','河南童世界','华中健康','安徽童世界','西安童世界','西部健康陕西公司','湖北童世界','济南童世界','江苏童世界','华东健康','嘉凯城','四川童世界','西部健康','四川人寿',\n" +
                "'云南童世界','贵阳童世界','海花岛','湖南童世界','健康集团总部','健康集团物管中心','旅游集团总部','旅游集团物管中心','旅游集团启东','高科技集团'\n" +
                "));\n" +
                "\n";
        FileUtils.writeByteArrayToFile(file, initSql.getBytes("UTF-8"),false);
        for (CompanyInfo info : companyInfoSet) {
            String sql = String.format("CALL dealCompanyLpy(%s, %s);\n", info.getCompanyName(), info.getGroupName());
            byte[] sourceBytes = sql.getBytes("UTF-8");
            if(null != sourceBytes){
                FileUtils.writeByteArrayToFile(file, sourceBytes,true);
            }
        }
        FileUtils.writeByteArrayToFile(file, "DROP PROCEDURE IF EXISTS dealCompanyLpy;".getBytes("UTF-8"),true);
    }
}
