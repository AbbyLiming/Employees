package com.expressba.express.sorter.SorterIndex;

import android.app.Activity;

import org.json.JSONException;
import org.json.JSONObject;

import com.expressba.express.main.MyApplication;
import com.expressba.express.net.VolleyHelper;
import com.expressba.express.R;

/**
 * Created by 黎明 on 2016/5/4.
 */
public class LoadPackageDriverPresenterImpl extends VolleyHelper implements LoadPackageIntoDriverPresenter {
    private SorterIndexFragmentView fragmentview;
    String url, turl;
    int employeeId;

    @Override
    public void loadPackageIntoDriver(String result) {
        url += "/employeeId/" + employeeId + "/packageId/" + result;
        try {
            doJson(url, VolleyHelper.GET, null);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public LoadPackageDriverPresenterImpl(Activity activity, SorterIndexFragmentView sorterindexfragmentView) {
        super(activity);
        employeeId = ((MyApplication) activity.getApplication()).getEmployeesInfo().getId();
        this.fragmentview = sorterindexfragmentView;
        //turl="http://192.168.1.113:8080"+activity.getResources().getString(R.string.createPackage);
        turl = activity.getResources().getString(R.string.base_url) + "/REST/Domain/setDriverPackage";
        url = turl;
    }

    @Override
    public void onDataReceive(Object jsonOrArray) {
        JSONObject jsonObject = (JSONObject) jsonOrArray;
        try {
            if (jsonObject.getInt("state") == 1) {
                fragmentview.onSuccess();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onError(String errorMessage) {
        fragmentview.onError(errorMessage);
        url = turl;
    }
}
