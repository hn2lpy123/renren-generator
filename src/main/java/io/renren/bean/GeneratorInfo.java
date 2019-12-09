package io.renren.bean;

public class GeneratorInfo extends BaseBean {

    private String email;

    private String author;

    private String packageInfo;

    private String tablePrefix;

    public GeneratorInfo() {
    }

    public GeneratorInfo(String packageInfo, String author, String email, String tablePrefix) {
        this.author = author;
        this.email = email;
        this.packageInfo = packageInfo;
        this.tablePrefix = tablePrefix;
    }

    public String getTablePrefix() {
        return tablePrefix;
    }

    public void setTablePrefix(String tablePrefix) {
        this.tablePrefix = tablePrefix;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPackageInfo() {
        return packageInfo;
    }

    public void setPackageInfo(String packageInfo) {
        this.packageInfo = packageInfo;
    }
}
