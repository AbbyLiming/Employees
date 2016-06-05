package com.expressba.express.user.login;
import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.expressba.express.main.UIFragment;
import com.expressba.express.myelement.MyFragmentManager;
import com.expressba.express.sorter.SorterIndex.RegisterFragment;
import com.expressba.express.sorter.SorterIndex.SorterIndexFragment;
import com.expressba.express.R;

/**
 * Created by chao on 2016/4/16.
 */
public class LoginFragment extends UIFragment implements LoginFragmentView,View.OnClickListener{

    public static final int TEL_ERROR = 0;
    public static final int PASSWORD_ERROR = 1;
    public static final int NAME_ERROT = 2;
    public static final int VERIFY_ERROR = 3;
    private LoginModel loginModel;
    private EditText telEdit;
    private EditText passwordEdit;
    private String tel;
    private String password;
    private String name;
    private String verifyCode;
    private boolean hasUserNameEdit = false;//用户名输入框是否存在
    private boolean isLogin;
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_login,container,false);
        TextView topText = (TextView) view.findViewById(R.id.top_bar_center_text);
        topText.setText("登陆");
        loginModel = new LoginModelImpl(getActivity(),this);
        telEdit = (EditText) view.findViewById(R.id.login_tel_edit);
        passwordEdit = (EditText) view.findViewById(R.id.login_password_edit);
        view.findViewById(R.id.login_button).setOnClickListener(this);
        view.findViewById(R.id.top_bar_left_img).setOnClickListener(this);
        view.findViewById(R.id.register_button).setOnClickListener(this);
        telEdit.setText(R.string.telEdit);
        passwordEdit.setText(R.string.passwordEdit);
        return view;
    }
    @Override
    protected void onBack() {
        MyFragmentManager.popFragment(LoginFragment.class,SorterIndexFragment.class,null,getFragmentManager());
        // getFragmentManager().popBackStack();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.top_bar_left_img :
                //点击后退按钮
                onback();
                break;
            case R.id.login_button:
                isLogin = true;
                    if(checkInput()) {
                        loginModel.startLogin(tel,password);
                    }
                break;
            case R.id.register_button:
                toRegister();
                break;
            default:
                break;
        }
    }

    private void toRegister() {
        MyFragmentManager.turnFragment(LoginFragment.class,RegisterFragment.class,null,getFragmentManager());
    }


    @Override
    public void onback() {
        /*FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.hide(this);
        ft.commitAllowingStateLoss();*/
        MyFragmentManager.popFragment(LoginFragment.class,null,null,getFragmentManager());
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(getActivity(),message,Toast.LENGTH_SHORT).show();
    }

    @Override
    public Activity getTheActivity() {
        return getActivity();
    }

    @Override
    public boolean checkInput() {
        tel = telEdit.getText().toString();
        password = passwordEdit.getText().toString();
        Pattern pattern = Pattern.compile("^[0-9]+$");
        Matcher matcher = pattern.matcher(tel);
        if(tel.length()!=11 || !matcher.matches()) {
            telEdit.setError("必须是电话号");
            return false;
        }
        if(password.length()>20 || password.length()<6){
            passwordEdit.setError("6-20个字符");
            return false;
        }
        return true;
    }

    @Override
    public void onError(int whereError, String errorInfo) {
        switch (whereError){
            case TEL_ERROR:
                telEdit.setError(errorInfo);
                break;
            case PASSWORD_ERROR:
                passwordEdit.setError(errorInfo);
                break;
            default:
                break;
        }
    }
}
