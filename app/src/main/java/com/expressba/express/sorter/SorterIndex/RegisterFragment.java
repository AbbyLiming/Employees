package com.expressba.express.sorter.SorterIndex;

import android.app.Activity;
import android.media.Image;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.trace.T;
import com.expressba.express.R;
import com.expressba.express.main.UIFragment;
import com.expressba.express.myelement.MyFragmentManager;
import com.expressba.express.user.login.LoginFragment;
import com.expressba.express.user.login.LoginFragmentView;
import com.expressba.express.user.login.LoginModel;
import com.expressba.express.user.login.LoginModelImpl;

/**
 * Created by 黎明 on 2016/5/23.
 */
public class RegisterFragment extends UIFragment implements RegisterFragmentView, LoginFragmentView {
    @Override
    public boolean checkInput() {
        return false;
    }

    @Override
    protected void onBack() {
        MyFragmentManager.popFragment(RegisterFragment.class, SorterIndexFragment.class, null, getFragmentManager());
        // getFragmentManager().popBackStack();
    }

    @Override
    public void onback() {
        MyFragmentManager.popFragment(LoginFragment.class, null, null, getFragmentManager());
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public Activity getTheActivity() {
        return getActivity();
    }

    @Override
    public void onError(int whereError, String errorInfo) {

    }

    private EditText name, password1, tel, status, outletsId, job, jobText, password2;
    private Button register;
    private LoginModel model;
    private ImageButton back;
    private TextView title;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.register_employee, container, false);
        name = (EditText) view.findViewById(R.id.register_employee_name);
        back = (ImageButton) view.findViewById(R.id.top_bar_left_img);
        title = (TextView) view.findViewById(R.id.top_bar_center_text);
        title.setText("注册");
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onback();
            }
        });
        password1 = (EditText) view.findViewById(R.id.register_employee_pass1);
        password2 = (EditText) view.findViewById(R.id.register_employee_pass2);
        tel = (EditText) view.findViewById(R.id.register_employee_tel);
        job = (EditText) view.findViewById(R.id.register_employee_job);
        jobText = (EditText) view.findViewById(R.id.register_employee_jobText);
        status = (EditText) view.findViewById(R.id.register_employee_status);
        outletsId = (EditText) view.findViewById(R.id.register_employee_outletsId);
        register = (Button) view.findViewById(R.id.register_employee_regist);
        model = new LoginModelImpl(getActivity(), this);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkInputText();
            }
        });
        return view;
    }

    private void checkInputText() {


            if (  TextUtils.isEmpty(password1.getText()) ||  TextUtils.isEmpty(tel.getText()) ||   TextUtils.isEmpty(status.getText()) ||   TextUtils.isEmpty(outletsId.getText()) ||
                    TextUtils.isEmpty(jobText.getText())||   TextUtils.isEmpty(job.getText()) ||TextUtils.isEmpty(password1.getText())||
                    TextUtils.isEmpty(password2.getText()))
                Toast.makeText(getActivity(), "请补全信息", Toast.LENGTH_SHORT).show();
            else if (password1.getText().toString().equals(password2.getText().toString())) {
                if(tel.getText().toString().length()!=11)
                    Toast.makeText(getActivity(),"手机号输入错误",Toast.LENGTH_SHORT).show();
                else if(password1.getText().length()<6||password1.getText().length()>20)
                    Toast.makeText(getActivity(),"密码应为6-20位",Toast.LENGTH_SHORT).show();
                else {
                    model.startRegister(tel.getText().toString(), password1.getText().toString(), name.getText().toString(), Integer.valueOf(job.getText().toString()),
                            jobText.getText().toString(), Integer.valueOf(status.getText().toString()), Integer.valueOf(outletsId.getText().toString()));
                }
        } else
            Toast.makeText(getActivity(), "密码输入不相符", Toast.LENGTH_SHORT).show();
    }
}
