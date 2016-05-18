package com.expressba.express.sorter.Expressupdate;

import android.app.Activity;
import android.graphics.Bitmap;

import com.expressba.express.R;
import com.expressba.express.net.VolleyHelper;

/**
 * Created by 黎明 on 2016/5/18.
 */
public class UploadImagePresenterImpl extends VolleyHelper implements UploadImagePresenter {

    private DeliverUpdateExpressFragmentView fragment;
    private String url;
    public UploadImagePresenterImpl(DeliverUpdateExpressFragmentView fragment, Activity activity)
    {
        super(activity);
        this.fragment=fragment;
        url=activity.getResources().getString(R.string.base_url)+"";
    }
    @Override
    public void uploadImage(String id,int type,Bitmap bitmap) {
        try {
            doImage(url,bitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDataReceive(Object jsonOrArray) {

    }

    @Override
    public void onError(String errorMessage) {

    }
}
