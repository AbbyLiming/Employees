package com.expressba.express.user.telephone;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.expressba.express.R;
import com.expressba.express.main.UIFragment;
import com.expressba.express.myelement.MyFragmentManager;
import com.expressba.express.sorter.SorterIndex.SorterIndexFragment;

/**
 * Created by chao on 2016/4/28.
 */
public class ChangeTelFragment extends UIFragment implements View.OnClickListener,ChangeTelView{
    private EditText telEdit;
    private Button verifySubmitButton;
    ChangeTelPresenter presenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_change_telephone,container,false);

       presenter = new ChangeTelPresenterImpl(getActivity(),this);

        ((TextView)view.findViewById(R.id.top_bar_center_text)).setText("手机号修改");
        telEdit = (EditText) view.findViewById(R.id.user_change_tel);
        verifySubmitButton = (Button) view.findViewById(R.id.user_change_tel_submit);

        view.findViewById(R.id.top_bar_left_img).setOnClickListener(this);
        verifySubmitButton.setOnClickListener(this);


        return view;
    }
    @Override
    protected void onBack() {
        MyFragmentManager.popFragment(ChangeTelFragment.class,SorterIndexFragment.class,null,getFragmentManager());
        // getFragmentManager().popBackStack();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.top_bar_left_img:
                getFragmentManager().popBackStack();
                break;
            case R.id.user_change_tel_submit:
                presenter.onSubmit(telEdit.getText().toString());
                break;
            default:
                break;
        }
    }


    private void showToast(String message){
        Toast.makeText(getActivity(),message,Toast.LENGTH_SHORT).show();
    }


    /**
     * 提交成功回调
     */
    @Override
    public void onSubmitSuccess() {
        showToast("电话号码修改成功");
        getFragmentManager().popBackStack();
    }

    /**
     * 错误处理
     * @param errormessage
     */
    @Override
    public void onError(String errormessage) {
        showToast(errormessage);
    }
}
