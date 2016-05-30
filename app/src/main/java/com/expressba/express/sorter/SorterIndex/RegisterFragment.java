package com.expressba.express.sorter.SorterIndex;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
public class RegisterFragment extends UIFragment  implements RegisterFragmentView,LoginFragmentView
{
    @Override
    public boolean checkInput() {
     return false;
    }

    @Override
    public void addUserNameEdit() {

    }

    @Override
    public void onback() {
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
    public void onError(int whereError, String errorInfo) {

    }

    private EditText name,password1,tel,status,outletsId,job,jobText,password2;
    private Button register;
    private LoginModel model;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.register_employee,container,false);
        name=(EditText)view.findViewById(R.id.register_employee_name);
        password1=(EditText)view.findViewById(R.id.register_employee_pass1);
        password2=(EditText)view.findViewById(R.id.register_employee_pass2);
        tel=(EditText)view.findViewById(R.id.register_employee_tel);
        job=(EditText)view.findViewById(R.id.register_employee_job);
        jobText=(EditText)view.findViewById(R.id.register_employee_jobText);
        status=(EditText)view.findViewById(R.id.register_employee_status);
        outletsId=(EditText)view.findViewById(R.id.register_employee_outletsId);
        register=(Button)view.findViewById(R.id.register_employee_regist);
        model=new LoginModelImpl(getActivity(),this);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkInputText();
            }
        });
        return view;
    }

    private void checkInputText() {
        if(password1.getText().toString().equals(password2.getText().toString())&&password1.getText()!=null)
        {
            if(name.getText()==null||tel.getText()==null||status.getText()==null||outletsId.getText()==null||
                    jobText.getText()==null||job.getText()==null)
                Toast.makeText(getActivity(),"请补全信息",Toast.LENGTH_SHORT).show();
            else
                model.startRegister(tel.getText().toString(),password1.getText().toString(),name.getText().toString(),Integer.valueOf(job.getText().toString()),
                        jobText.getText().toString(),Integer.valueOf(status.getText().toString()),Integer.valueOf(outletsId.getText().toString()));

        }
        else
            Toast.makeText(getActivity(),"密码输入不相符",Toast.LENGTH_SHORT).show();
    }
}
