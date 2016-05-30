package com.expressba.express.user.login;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;


import org.json.JSONException;
import org.json.JSONObject;

import com.expressba.express.main.MyApplication;
import com.expressba.express.map.MyHistoryTrace;
import com.expressba.express.net.VolleyHelper;
import com.expressba.express.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ming on 2016/4/16.
 */
public class LoginModelImpl extends VolleyHelper implements LoginModel {

    private LoginFragmentView loginView;
    private String loginUrl;
    private String registerUrl;

    private String telephone;
    private String mD5Password;
    private String name;
    private int job;
    private String jobText;
    private int status;
    private int outletsId;
    private Activity activity;
    private MyApplication application;//保存用户登录状态到全局appliction中
    private boolean isLogin;
    private MyHistoryTrace myHistoryTrace;

    public LoginModelImpl(Activity activity, LoginFragmentView loginView) {
        super(activity);
        application = (MyApplication) activity.getApplication();
        this.activity = activity;
        this.loginView = loginView;
        myHistoryTrace = new MyHistoryTrace();

        //loginUrl = "http://192.168.1.102:8080" + activity.getResources().getString(R.string.login_send_employee);
        loginUrl = activity.getResources().getString(R.string.base_url) + activity.getResources().getString(R.string.login_send_employee);
        //registerUrl="http://192.168.1.102:8080"+activity.getResources().getString(R.string.register_send_employee);
        registerUrl = activity.getResources().getString(R.string.base_url) + activity.getResources().getString(R.string.register_send_employee);
    }

    @Override
    public void startLogin(String tel, String password) {
        isLogin = true;
        String url = loginUrl;
        //加密传输
        this.telephone = tel;
        //tel = MD5.getMD5(tel);
        //password = MD5.getMD5(password);
        this.mD5Password = password;
        JSONObject jsonObject = new JSONObject();
        try {
             jsonObject.put("telephone", tel);
            jsonObject.put("password", password);
            doJson(url, VolleyHelper.POST, jsonObject);

        } catch (Exception e) {
            e.printStackTrace();
            loginView.showToast("登陆数据解析失败");
        }
    }

    @Override
    public void startRegister(String tel, String password, String name, int job, String jobText, int status, int outletsId) {
        isLogin = false;
        String url = registerUrl;
        //加密传输
        this.telephone = tel;
        this.name = name;
        this.job = job;
        this.jobText = jobText;
        this.outletsId = outletsId;
        this.status = status;
        //tel = MD5.getMD5(tel);
        //password = MD5.getMD5(password);
        this.mD5Password = password;

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("telephone", tel);
            jsonObject.put("password", password);
            jsonObject.put("name", name);
            jsonObject.put("job", job);
            jsonObject.put("jobText", jobText);
            jsonObject.put("status", status);
            jsonObject.put("outletsId", outletsId);
            doJson(url, VolleyHelper.POST, jsonObject);

        } catch (Exception e) {
            e.printStackTrace();
            loginView.showToast("注册数据解析失败");
        }
    }

    @Override
    public void onDataReceive(Object jsonOrArray) {
        JSONObject jsonObject = (JSONObject) jsonOrArray;
        if (isLogin) {
            try {
                String loginState = jsonObject.getString("loginstate");
                switch (loginState) {
                    case "true":
                        this.name = jsonObject.getString("name");//登陆成功后存储必要用户信息
                        application.getEmployeesInfo().setId(jsonObject.getInt("id"));
                        // application.getEmployeesInfo().setId(7);
                        application.getEmployeesInfo().setName(this.name);
                        application.getEmployeesInfo().setTelephone(telephone);
                        application.getEmployeesInfo().setPassword(mD5Password);
                        application.getEmployeesInfo().setJob(jsonObject.getInt("job"));
                        application.getEmployeesInfo().setJobText(jsonObject.getString("jobText"));
                        application.getEmployeesInfo().setStatus(jsonObject.getInt("status"));
                        application.getEmployeesInfo().setOutletsId(jsonObject.getInt("outletsId"));
                        application.getEmployeesInfo().setLoginState(true);
                        application.getEmployeesInfo().setToken(jsonObject.getString("token"));
                        loginView.showToast("登陆成功");
                        loginView.onback();
                        break;
                    case "false":
                        application.getEmployeesInfo().setLoginState(false);
                        loginView.showToast("登陆失败，请重试");
                        break;
                    default:
                        application.getEmployeesInfo().setLoginState(false);
                        loginView.showToast("登陆失败，请重试");
                        break;
                }
            } catch (JSONException e) {
                e.printStackTrace();
                loginView.showToast("异常，请重试");
            }
        } else {
            try {
                String registerState = jsonObject.getString("newEmployee");
                switch (registerState) {
                    case "true":
                        application.getEmployeesInfo().setLoginState(true);
                        application.getEmployeesInfo().setName(name);
                        application.getEmployeesInfo().setPassword(mD5Password);
                        application.getEmployeesInfo().setTelephone(telephone);
                        application.getEmployeesInfo().setJob(job);
                        application.getEmployeesInfo().setJobText(jobText);
                        application.getEmployeesInfo().setStatus(status);
                        application.getEmployeesInfo().setOutletsId(outletsId);
                        application.getEmployeesInfo().setId(jsonObject.getInt("id"));
                        application.getEmployeesInfo().setToken(jsonObject.getString("token"));
                        loginView.showToast("注册成功");

                        myHistoryTrace.addEntity(String.valueOf(jsonObject.getString("id")), new MyHistoryTrace.EntityListenerInterface() {
                            @Override
                            public void addEntityCallBack(String s) {
                                Pattern pattern = Pattern.compile("\\{\"status\":([0-9]*),");
                                Matcher matcher = pattern.matcher(s);
                                if (matcher.find()) {
                                    Integer status = Integer.valueOf(matcher.group(1));
                                    if (status == 0) {
                                        loginView.onback();
                                    } else {
                                        Log.e("addentityError", s);
                                    }
                                }
                            }

                            @Override
                            public void requestFailedCallBack(String s) {

                            }
                        });
                        break;
                    case "false":
                        // application.getEmployeesInfo().setLoginState(false);
                        loginView.showToast("注册失败");
                        break;
                    default:
                        break;
                }
            } catch (JSONException e) {
                e.printStackTrace();
                loginView.showToast("异常，请重试");
            }
        }
    }

    @Override
    public void onError(String errorMessage) {
        loginView.showToast(errorMessage);
    }
}
