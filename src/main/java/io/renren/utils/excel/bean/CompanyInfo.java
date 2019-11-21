package io.renren.utils.excel.bean;

import io.renren.utils.StringUtils;

public class CompanyInfo {

    private String companyName;

    private String groupName;

    public CompanyInfo(String companyName, String groupName) {
        this.companyName = companyName;
        this.groupName = groupName;
    }


    @Override
    public int hashCode() {
        return StringUtils.toHash(companyName + groupName);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof CompanyInfo)) {
            return false;
        }
        CompanyInfo tmp = (CompanyInfo) obj;
        if (tmp.getCompanyName().equals(companyName) && tmp.getGroupName().equals(groupName)) {
            return true;
        }
        return false;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}
