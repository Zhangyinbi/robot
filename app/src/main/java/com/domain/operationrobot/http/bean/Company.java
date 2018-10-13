package com.domain.operationrobot.http.bean;

/**
 * Project Name : OperationRobot
 * description:null
 *
 * @author : yinbi.zhang.o
 * Create at : 2018/10/13 05:21
 */
public class Company {
    private String companyName;
    private String adminName;
    private long companyId = -1;

    public Company(String companyName, String adminName, long companyId) {
        this.companyName = companyName;
        this.adminName = adminName;
        this.companyId = companyId;
    }

    public long getCompanyId() {
        return companyId;
    }

    public String getAdminName() {
        return adminName == null ? "" : adminName;
    }

    public String getCompanyName() {
        return companyName == null ? "" : companyName;
    }
}
