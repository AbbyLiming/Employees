package com.expressba.express.sorter.ReceiverInfo;

import android.app.Activity;

import org.json.JSONException;
import org.json.JSONObject;

import com.expressba.express.main.MyApplication;
import com.expressba.express.net.VolleyHelper;
import com.expressba.express.R;

import java.util.Date;

/**
 * Created by 黎明 on 2016/5/3.
 */
public class ReceiverInfoPresenterImpl extends VolleyHelper implements ReceiverInfoPresenter {
    private ReceiverInfoFragmentView fragmentView;
    String url, turl;

    public ReceiverInfoPresenterImpl(Activity activity, ReceiverInfoFragmentView fragmentView) {
        super(activity);
        this.fragmentView = fragmentView;
        turl = activity.getResources().getString(R.string.base_url) + "/REST/Domain/updateExpressFree";
        url = turl;
    }

    @Override
    public void onError(String errorMessage) {
        url = turl;
        fragmentView.onFail(errorMessage);
    }

    @Override
    public void ReceiveExpress(String ID) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id",ID);
            Date outtime=new Date();
            jsonObject.put("outTime",outtime);
            doJson(url, VolleyHelper.POST,jsonObject);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            url = turl;
        }
    }

    @Override
    public void onDataReceive(Object jsonOrArray) {
        JSONObject jsonObject = new JSONObject();
        try {
            int state = jsonObject.getInt("state");
            if (state == 1)
                fragmentView.onSuccess();
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            url = turl;
        }
    }
}
