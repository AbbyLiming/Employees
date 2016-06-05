package com.expressba.express.sorter.ReceiverInfo;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.expressba.express.main.UIFragment;
import com.expressba.express.model.ExpressInfo;
import com.expressba.express.R;
import com.expressba.express.sorter.Expressupdate.DeliverUpdateExpressFragmentView;
import com.expressba.express.sorter.Expressupdate.UploadImagePresenter;
import com.expressba.express.sorter.Expressupdate.UploadImagePresenterImpl;

/**
 * Created by 黎明 on 2016/5/3.
 */
public class ReceiverInfoFragment extends UIFragment implements ReceiverInfoFragmentView, View.OnClickListener, DeliverUpdateExpressFragmentView {
    private ImageButton back;
    private UploadImagePresenter presenter1;
    private TextView title;
    private TextView receiver_info_tel, receiver_info_name, receiver_info_add, receiver_info_addinfo;
    private Button receiver_info_send;
    private ReceiverInfoPresenter presenter;
    private static String ID;
    private ImageView startCamera, startCamera1;
    private static String packageID;
    private LoadPicPresenter picPresenter;
    @Override
    protected void onBack() {
       // MyFragmentManager.popFragment(ReceiverInfoFragment.class,null,null,getFragmentManager());
        getFragmentManager().popBackStack();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.receiver_info, container, false);
        back = (ImageButton) view.findViewById(R.id.top_bar_left_img);
        title = (TextView) view.findViewById(R.id.top_bar_center_text);
        title.setText("收件人地址");
        picPresenter = new LoadPicPresenterImpl(this, getActivity());
        presenter = new ReceiverInfoPresenterImpl(getActivity(), this);
        receiver_info_add = (TextView) view.findViewById(R.id.receiver_info_add);
        receiver_info_addinfo = (TextView) view.findViewById(R.id.receiver_info_addinfo);
        receiver_info_name = (TextView) view.findViewById(R.id.receiver_info_name);
        receiver_info_tel = (TextView) view.findViewById(R.id.receiver_info_tel);
        receiver_info_send = (Button) view.findViewById(R.id.receiver_info_send);
        back.setOnClickListener(this);
        startCamera = (ImageView) view.findViewById(R.id.startCamera);
        startCamera1 = (ImageView) view.findViewById(R.id.startCamera1);

        startCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startCamera();
            }
        });
        receiver_info_send.setOnClickListener(this);
        if (getArguments() != null) {
            packageID = getArguments().getString("packageID");
            ExpressInfo expressInfo = (ExpressInfo) getArguments().getParcelable("express");
            picPresenter.loadPicPresenter(expressInfo.getID(), 0);
            try {
                if (expressInfo.getOutTime() != null) {
                    receiver_info_send.setVisibility(View.INVISIBLE);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            receiver_info_add.setText(expressInfo.getRadd());
            receiver_info_addinfo.setText(expressInfo.getRaddinfo());
            receiver_info_name.setText(expressInfo.getRname());
            receiver_info_tel.setText(expressInfo.getRtel());
            ID = expressInfo.getID();
        } else
            getFragmentManager().popBackStack();
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.top_bar_left_img:
            //   MyFragmentManager.popFragment(ReceiverInfoFragment.class,null,null,getFragmentManager());
                // MyFragmentManager.turnFragment(ReceiverInfoFragment.class, SorterIndexFragment.class, null, getFragmentManager());
                getFragmentManager().popBackStack();
                break;
            case R.id.receiver_info_send:
                //拍照 完了签收成功会调用presenter
                presenter.ReceiveExpress(ID);
        }
    }

    @Override
    public void onFail(String errorMessage) {
        Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_LONG).show();
    }

    public void startCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 1);
    }

    @Override
    public void onSuccess() {
        Toast.makeText(getActivity(), "签收成功", Toast.LENGTH_LONG).show();
        receiver_info_send.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 1) {
                Bundle bundle = data.getExtras();
                //图片二进制流
                Bitmap bitmap = (Bitmap) bundle.get("data");
                presenter1 = new UploadImagePresenterImpl(this, getActivity());
                presenter1.uploadImage(ID, 1, bitmap);
                //type==1 paisong
                startCamera.setImageBitmap(bitmap);
            }
        }
    }

    @Override
    public void onSuccess(String picture) {
        Bitmap bitmap = null;
        try {
            byte[] bitmapArray;
            bitmapArray = Base64.decode(picture, Base64.DEFAULT);
            bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);
            startCamera1.setImageBitmap(bitmap);
        } catch (Exception e) {
            Toast.makeText(getActivity(), "图片加载错误", Toast.LENGTH_SHORT).show();
        }
    }
}
