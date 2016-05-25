package com.expressba.express.model;


import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.io.Serializable;

/**
 * Created by violet on 2016/5/3.
 */
public class EmployeesEntity implements Serializable {
    private boolean loginState = false;
    private int id;
    private String name;
    private String password;
    private String telephone;
    private Integer job;
    private String jobText;
    private Integer status;
    private int outletsId;
    private String sendPackageId;
    private String recvPackageId;
    private SharedPreferences spf;
    private Context context;
    private String token;


    public EmployeesEntity(Context context) {
        this.context = context;
        spf = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public EmployeesEntity(int id, String name, String password, String telephone, Integer job, String jobText, Integer status, int outletsId, String sendPackageId, String recvPackageId) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.telephone = telephone;
        this.job = job;
        this.jobText = jobText;
        this.status = status;
        this.outletsId = outletsId;
        this.sendPackageId = sendPackageId;
        this.recvPackageId = recvPackageId;
    }

    public void setPassword(String value) {
        SharedPreferences.Editor editor = spf.edit();
        editor.putString("password", value);
        editor.apply();
        this.password = value;
    }

    public String getPassword() {
        return password;
    }

    public String getPasswordFromPreference() {
        return spf.getString("password", password);
    }

    public void setName(String value) {
        SharedPreferences.Editor editor = spf.edit();
        editor.putString("name", value);
        editor.apply();
        this.name = value;
    }

    public String getName() {
        return name;
    }

    public String getNameFromPreference() {
        return spf.getString("name", "");
    }

    public boolean getLoginState() {
        return loginState;
    }

    public boolean getLoginStateFromPreference() {
        return spf.getBoolean("loginState", false);
    }

    public void setLoginState(boolean loginState) {
        SharedPreferences.Editor editor;
        if (!loginState) {
            editor = spf.edit();
            editor.putBoolean("loginState", false);
            editor.remove("name");
            editor.remove("telephone");
            editor.remove("password");
            editor.apply();
        }
        this.loginState = loginState;
    }

    public String getTelephone() {
        return telephone;
    }

    public String getTelephoneFromPreference() {
        return spf.getString("telephone", "");
    }

    public void setTelephone(String telephone) {
        SharedPreferences.Editor editor = spf.edit();
        editor.putString("telephone", telephone);
        editor.apply();
        this.telephone = telephone;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getJob() {
        return job;
    }

    public void setJob(Integer job) {
        this.job = job;
    }


    public String getJobText() {
        return jobText;
    }

    public void setJobText(String jobText) {
        this.jobText = jobText;
    }


    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }


    public int getOutletsId() {
        return outletsId;
    }

    public void setOutletsId(int outletsId) {
        this.outletsId = outletsId;
    }


    public String getSendPackageId() {
        return sendPackageId;
    }

    public void setSendPackageId(String sendPackageId) {
        this.sendPackageId = sendPackageId;
    }


    public String getRecvPackageId() {
        return recvPackageId;
    }

    public void setRecvPackageId(String recvPackageId) {
        this.recvPackageId = recvPackageId;
    }


    @Override
    public String toString() {
        return "EmployeesEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", telephone='" + telephone + '\'' +
                ", job=" + job +
                ", jobText='" + jobText + '\'' +
                ", status=" + status +
                ", outletsId=" + outletsId +
                ", sendPackageId='" + sendPackageId + '\'' +
                ", recvPackageId='" + recvPackageId + '\'' +
                '}';
    }
}
