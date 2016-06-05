package com.expressba.express.sorter.ReceiverInfo;

import android.app.Activity;

import com.expressba.express.R;
import com.expressba.express.net.VolleyHelper;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by 黎明 on 2016/6/4.
 */
public class LoadPicPresenterImpl extends VolleyHelper implements LoadPicPresenter {
    private ReceiverInfoFragmentView fragment;
    private String url;

    public LoadPicPresenterImpl(ReceiverInfoFragmentView fragment, Activity activity) {
        super(activity);
        this.fragment = fragment;
        url = activity.getResources().getString(R.string.base_url);
    }

    @Override
    public void onDataReceive(Object jsonOrArray) {
        JSONObject jsonObject = (JSONObject) jsonOrArray;
        try {
            if (jsonObject.getString("picture") != null) {
                fragment.onSuccess(jsonObject.getString("picture"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onError(String errorMessage) {
        fragment.onFail(errorMessage);
    }

    @Override
    public void loadPicPresenter(String id, int whichOne) {
        url += "/REST/Domain/downLoadPicture/expressId/" + id + "/whichOne/" + whichOne;
        try {
            doJson(url, VolleyHelper.GET, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
