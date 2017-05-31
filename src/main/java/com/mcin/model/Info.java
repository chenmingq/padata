package com.mcin.model;

import java.util.Date;

/**
 * Created by Mcin on 2017/5/16.
 */
public class Info {

    /**
     * 主键id
     */
    private int id ;

    /**
     * 企业名称
     */
    private String companyName;

    /**
     * 用户信息
     */
    private String userInfo ;

    /**
     * 网站url
     */
    private String host;

    /**
     * 创建时间
     */
    private Date createTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public  Info setCompanyName(String companyName) {
        this.companyName = companyName;
        return this;
    }

    public String getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(String userInfo) {
        this.userInfo = userInfo;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }
}
