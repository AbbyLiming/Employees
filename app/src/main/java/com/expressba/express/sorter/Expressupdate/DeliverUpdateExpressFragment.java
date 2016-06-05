package com.expressba.express.sorter.Expressupdate;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.expressba.express.main.UIFragment;
import com.expressba.express.model.ExpressEntity;
import com.expressba.express.myelement.MyFragmentManager;
import com.expressba.express.sorter.SorterIndex.SorterIndexFragment;
import com.expressba.express.R;

/**
 * Created by 黎明 on 2016/5/3.
 */
public class DeliverUpdateExpressFragment extends UIFragment implements DeliverUpdateExpressFragmentView {
    private DeliverUpdateExpressPresenter presenter;
    private UploadImagePresenter presenter1;
    private EditText weight, insufee, transfee;
    private Button submit;
    private TextView ID;
    private TextView title;
    private ImageView startCamera;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        presenter = new DeliverUpdateExpressPresenterImpl(getActivity(), this);
        presenter1 = new UploadImagePresenterImpl(this, getActivity());
        View view = inflater.inflate(R.layout.deliver_update_express_first, container, false);
        title = (TextView) view.findViewById(R.id.top_bar_center_text);
        title.setText("快件信息");
        ID = (TextView) view.findViewById(R.id.deliver_update_express_first_ID);
        weight = (EditText) view.findViewById(R.id.deliver_update_express_first_weight);
        insufee = (EditText) view.findViewById(R.id.deliver_update_express_first_insufee);
        transfee = (EditText) view.findViewById(R.id.deliver_update_express_first_transfee);
        submit = (Button) view.findViewById(R.id.submit_deliver);
        startCamera = (ImageView) view.findViewById(R.id.startCamera);
        startCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startCamera();
            }
        });

        if (getArguments() != null) {
            ID.setText(getArguments().getString("ID").toString());
        } else {
            Toast.makeText(getActivity(), "请输入id", Toast.LENGTH_SHORT).show();
        }
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(weight.getText())|| TextUtils.isEmpty(insufee.getText()) || TextUtils.isEmpty(transfee.getText())) {
                    Toast.makeText(getActivity(), "请将信息补充完整", Toast.LENGTH_SHORT).show();

                } else {
                    ExpressEntity expressEntity = new ExpressEntity();
                    expressEntity.setId(ID.getText().toString());
                    expressEntity.setWeight(Float.parseFloat(weight.getText().toString()));
                    expressEntity.setTranFee(Float.parseFloat(transfee.getText().toString()));
                    expressEntity.setInsuFee(Float.parseFloat(insufee.getText().toString()));
                    presenter.updateExpress(expressEntity);
                }
            }
        });

        return view;
    }

    public void startCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 1);
    }
    @Override
    protected void onBack() {
        MyFragmentManager.popFragment(DeliverUpdateExpressFragment.class,SorterIndexFragment.class,null,getFragmentManager());
        // getFragmentManager().popBackStack();
    }

    @Override
    public void onFail(String errorMessage) {
        Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSuccess() {
        Dialog dialog1 = new AlertDialog.Builder(getActivity()).setIcon(
                android.R.drawable.btn_star).setTitle("确认").setMessage(
                "更新成功").setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SorterIndexFragment fragment = new SorterIndexFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                transaction.replace(R.id.fragment_container_layout, fragment);
                transaction.addToBackStack("DeliverUpdateExpressFragment");
                transaction.commit();
            }
        }).create();
        dialog1.show();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 1) {
                Bundle bundle = data.getExtras();
                //图片二进制流
                Bitmap bitmap = (Bitmap) bundle.get("data");
                presenter1.uploadImage(ID.getText().toString(), 0, bitmap);
                //type==0lanshou
                startCamera.setImageBitmap(bitmap);
            }
        }
    }
}
