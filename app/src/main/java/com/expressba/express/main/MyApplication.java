package com.expressba.express.main;

import android.app.Application;
import android.location.Address;

import cn.smssdk.SMSSDK;

import com.baidu.trace.LBSTraceClient;
import com.expressba.express.map.MyHistoryTrace;
import com.expressba.express.model.CustomerInfo;
import com.expressba.express.model.EmployeesEntity;
import com.expressba.express.model.UserAddress;
import com.expressba.express.model.UserInfo;

/**
 * Created by chao on 2016 /4/17.
 * 使用application保存全局变量
 */
public class MyApplication extends Application {
    //全局变量
     private UserInfo userInfo;
    private EmployeesEntity employeesInfo;
    @Override
    public void onCreate() {
        super.onCreate();
        MyHistoryTrace.client=new LBSTraceClient(this);
         userInfo = new UserInfo(getApplicationContext());
        employeesInfo = new EmployeesEntity(getApplicationContext());
        SMSSDK.initSDK(this, "12282c18097fb", "55a709db05d0213647f5bd05e29c24f6");//初始化短信发送sdk

    }

    /**
     * 测试用方法，待删除
     * 以后需要在application中添加测试程序就写在这个方法里
     */

    public EmployeesEntity getEmployeesInfo() {
        if (employeesInfo != null) {
            return employeesInfo;
        } else {
            employeesInfo=new EmployeesEntity(getApplicationContext());
            return employeesInfo;
        }
    }
    public UserInfo getUserInfo() {
        if (userInfo != null) {
            return userInfo;
        } else {
            userInfo=new UserInfo(getApplicationContext());
            return userInfo;
        }
    }
}
