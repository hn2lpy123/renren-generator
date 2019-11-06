package io.renren.entity;

public class GeneratorInfo {

    private String email;

    private String author;

    private String packageInfo;

    public GeneratorInfo(String packageInfo, String author, String email) {
        this.author = author;
        this.email = email;
        this.packageInfo = packageInfo;
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
