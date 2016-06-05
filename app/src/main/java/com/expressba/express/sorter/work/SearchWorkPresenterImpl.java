package com.expressba.express.sorter.work;

import android.app.Activity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.expressba.express.main.MyApplication;
import com.expressba.express.model.ExpressEntity;
import com.expressba.express.net.VolleyHelper;
import com.expressba.express.R;

/**
 * Created by 黎明 on 2016/5/7.
 */
public class SearchWorkPresenterImpl extends VolleyHelper implements SearchWorkPresenter {
    private SearchWorkFragmentView fragmentview;
    private String turl, url;

    public SearchWorkPresenterImpl(Activity activity, SearchWorkFragmentView fragmentView) {
        super(activity);
        this.fragmentview = fragmentView;
        turl = activity.getResources().getString(R.string.base_url);
        url = turl;
    }

    @Override
    public void onDataReceive(Object jsonOrArray) {
        JSONArray jsonArray = (JSONArray) jsonOrArray;
        List<ExpressEntity> list = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            ExpressEntity expressEntity = new ExpressEntity();
            try {
                JSONObject object = (JSONObject) jsonArray.get(i);
                expressEntity.setId(object.getString("id"));
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date date1 = null;
                Date date3 = null;
                try {
                    try {
                        date1 = sdf.parse(object.getString("getTime"));
                        date3 = sdf.parse(object.getString("outTime"));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    String date4 = sdf.format(date3);
                    expressEntity.setOutTime(date4);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                String date2 = sdf.format(date1);
                expressEntity.setGetTime(date2);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            list.add(expressEntity);
        }
        fragmentview.onSuccess(list);
    }

    @Override
    public void searchWork(int EmployeesID, String start, int day) {
        turl += "/REST/Domain/getWork/employeeId/" + EmployeesID + "/starttime/" + start + "/days/" + day;
        try {
            doJsonArray(turl, VolleyHelper.GET, null);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            url = turl;
        }
    }

    @Override
    public void onError(String errorMessage) {
        url = turl;
        fragmentview.onError(errorMessage);
    }
}
