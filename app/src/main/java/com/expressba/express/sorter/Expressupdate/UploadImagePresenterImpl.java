package com.expressba.express.sorter.Expressupdate;

import android.app.Activity;
import android.graphics.Bitmap;
import android.util.Base64;

import com.expressba.express.R;
import com.expressba.express.net.VolleyHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;

/**
 * Created by 黎明 on 2016/5/18.
 */
public class UploadImagePresenterImpl extends VolleyHelper implements UploadImagePresenter {

    private DeliverUpdateExpressFragmentView fragment;
    private String url;

    public UploadImagePresenterImpl(DeliverUpdateExpressFragmentView fragment, Activity activity) {
        super(activity);
        this.fragment = fragment;
        url = activity.getResources().getString(R.string.base_url) + "/REST/Domain/upLoadPicture";
    }

    @Override
    public void uploadImage(String id, int type, Bitmap bitmap) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
            byte[] appicon = baos.toByteArray();
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("expressId", id);
                jsonObject.put("picture", Base64.encodeToString(appicon, Base64.DEFAULT));
                jsonObject.put("whichOne", type);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                doJson(url, VolleyHelper.POST, jsonObject);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDataReceive(Object jsonOrArray) {
        //图片上传成功
        JSONObject jsonObject = (JSONObject) jsonOrArray;
        try {
            int state = jsonObject.getInt("state");
            if (state == 1)
                fragment.onFail("图片上传成功");
            else
                fragment.onFail("出现错误");
        } catch (JSONException e) {
            e.printStackTrace();
            fragment.onFail("出现错误");
        }
    }

    @Override
    public void onError(String errorMessage) {
        fragment.onFail(errorMessage);
    }
}
