package com.expressba.express.user.telephone;

import android.app.Activity;

import org.json.JSONException;
import org.json.JSONObject;

import com.expressba.express.main.MyApplication;
import com.expressba.express.model.EmployeesEntity;
import com.expressba.express.model.UserInfo;
import com.expressba.express.net.VolleyHelper;
import com.expressba.express.R;

/**
 * 修改手机号presenter
 * Created by chao on 2016/4/28.
 */
public class ChangeTelPresenterImpl extends VolleyHelper implements ChangeTelPresenter {

    ChangeTelView changeTelView;
    String changeTelUrl;
    EmployeesEntity oldUserInfo;
    MyApplication application;
    String newTelephone;

    public ChangeTelPresenterImpl(Activity context, ChangeTelView changeTelView) {
        super(context);
        this.changeTelView = changeTelView;
        application = (MyApplication)context.getApplication();
        oldUserInfo = application.getEmployeesInfo();
        changeTelUrl = context.getResources().getString(R.string.base_url)+
                context.getResources().getString(R.string.employee_change_tel);
    }

    /**
     * 提交用户手机数据
     */
    @Override
    public void onSubmit(String tel) {
        this.newTelephone = tel;
        changeTelUrl = changeTelUrl.replace("{oldtel}",oldUserInfo.getTelephone());
        changeTelUrl = changeTelUrl.replace("{newtel}",tel);
        doJson(changeTelUrl,VolleyHelper.GET,null);
    }

    @Override
    public void onDataReceive(Object jsonOrArray) {
        JSONObject jsonObject = (JSONObject) jsonOrArray;
        try {
            if(jsonObject.getString("changetel").equals("true")){
                application.getEmployeesInfo().setTelephone(newTelephone);
                changeTelView.onSubmitSuccess();
            }else if(jsonObject.getString("changetel").equals("false")){
                changeTelView.onError("修改失败，请重试");
            }else if(jsonObject.getString("changetel").equals("deny1")){
                changeTelView.onError("手机号已经被人注册,请登录");
            }
            else if(jsonObject.getString("changetel").equals("deny2"))
            {
                changeTelView.onError("未根据旧手机号找到用户信息");
            }
        } catch (JSONException e) {
            e.printStackTrace();
            changeTelView.onError("修改失败，请重试");
        }
    }

    @Override
    public void onError(String errorMessage) {
        changeTelView.onError(errorMessage);
    }
}
