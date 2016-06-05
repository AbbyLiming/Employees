package com.expressba.express.sorter;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.trace.V;
import com.expressba.express.map.MyHistoryTrace;
import com.expressba.express.model.EmployeesEntity;
import com.expressba.express.myelement.MyFragmentManager;
import com.expressba.express.main.MyApplication;
import com.expressba.express.main.UIFragment;
import com.expressba.express.sorter.close.add_package_list.AddPackageListFragment;
import com.expressba.express.sorter.work.SearchWorkFragment;
import com.expressba.express.R;
import com.expressba.express.user.password.ChangePasswordFragment;
import com.expressba.express.user.telephone.ChangeTelFragment;
import com.expressba.express.zxing.activity.CaptureActivity;

/**
 * Created by 黎明 on 2016/5/4.
 */
public class SorterMe extends UIFragment implements SorterMeFragmentView, View.OnClickListener {

    @Override
    protected void onBack() {
        getFragmentManager().popBackStack();
    }

    FragmentManager fragmentManager;
    FragmentTransaction transaction;
    private int job;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sorter_me_fragment, container, false);
        TextView textView = (TextView) view.findViewById(R.id.top_bar_center_text);
        textView.setText("我");
        fragmentManager = getFragmentManager();
        transaction = fragmentManager.beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        EmployeesEntity employeesEntity=((MyApplication)getActivity().getApplication()).getEmployeesInfo();
        job=employeesEntity.getJob();
        if(job==1) {
            view.findViewById(R.id.send_record).setVisibility(View.VISIBLE);
            view.findViewById(R.id.receive_record).setVisibility(View.VISIBLE);
        }
        else {
            view.findViewById(R.id.send_record).setVisibility(View.INVISIBLE);
            view.findViewById(R.id.receive_record).setVisibility(View.INVISIBLE);
        }
        view.findViewById(R.id.top_bar_left_img).setOnClickListener(this);
        view.findViewById(R.id.user_tel).setOnClickListener(this);
        view.findViewById(R.id.user_password).setOnClickListener(this);
        view.findViewById(R.id.send_record).setOnClickListener(this);
        view.findViewById(R.id.receive_record).setOnClickListener(this);
        view.findViewById(R.id.my_complaint).setOnClickListener(this);
        view.findViewById(R.id.user_me_login_out).setOnClickListener(this);
        view.findViewById(R.id.user_work_log).setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.top_bar_left_img:
                getFragmentManager().popBackStack();
                break;
            case R.id.user_tel:
                toTelChange();
                break;
            case R.id.user_password:
                toPasswordChange();
                break;
            case R.id.send_record:
                toSendRecordFragment();
                break;
            case R.id.receive_record:
                toReceiveRecordFragment();
                break;
            case R.id.my_complaint:
                toAboutSoftFragment();
                break;
            case R.id.user_me_login_out:
                loginOut();
                break;
            case R.id.user_work_log:
                toWorklog();
        }
    }

    /**
     * 修改手机号
     */
    @Override
    public void toTelChange() {
      /*  ChangeTelFragment changeTelFragment = new ChangeTelFragment();

        transaction.replace(R.id.fragment_container_layout, changeTelFragment);
        transaction.addToBackStack("mefragment");
        transaction.commit();*/
        MyFragmentManager.turnFragment(SorterMe.class, ChangeTelFragment.class, null, getFragmentManager());
    }

    /**
     * 修改密码
     */
    @Override
    public void toPasswordChange() {
        /*ChangePasswordFragment changePasswordFragment = new ChangePasswordFragment();

        transaction.replace(R.id.fragment_container_layout, changePasswordFragment);
        transaction.addToBackStack("mefragment");
        transaction.commit();*/

        MyFragmentManager.turnFragment(SorterMe.class, ChangePasswordFragment.class, null, getFragmentManager());
    }

    /**
     * 注销登陆
     */
    @Override
    public void loginOut() {
        ((MyApplication) getActivity().getApplication()).getEmployeesInfo().setLoginState(false);
        MyHistoryTrace MyTrace = new MyHistoryTrace();
          MyTrace.stopTraceClient(new MyHistoryTrace.StopTraceInterface() {
            @Override
            public void stopTraceSuccess() {

            }

            @Override
            public void stopTraceFail(int arg0, String arg1) {

            }
        });
        getFragmentManager().popBackStack();

    }

    @Override
    public void toSendRecordFragment() {
       // AddPackageListFragment fragment = new AddPackageListFragment();
        Bundle bundle = new Bundle();
        // bundle.putString("packageID", "69026465584374");
        bundle.putString("packageID", ((MyApplication) getActivity().getApplication()).getEmployeesInfo().getSendPackageId());
      /*  fragment.setArguments(bundle);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container_layout, fragment);
        transaction.addToBackStack("SorterMe");
        transaction.commit();*/
        MyFragmentManager.turnFragment(SorterMe.class, AddPackageListFragment.class, bundle, getFragmentManager());
    }

    /**
     * 关于软件
     */
    @Override
    public void toAboutSoftFragment() {
        //startActivityForResult(new Intent(getActivity(), CaptureActivity.class), 0);
        MyHistoryTrace MyTrace = new MyHistoryTrace();
        MyTrace.startTraceClient(getActivity(), String.valueOf(((MyApplication) getActivity().getApplication()).getEmployeesInfo().getId()), new MyHistoryTrace.StartTraceInterface() {
            @Override
            public void startTraceCallBack(int stateCode, String message) {
                if (stateCode == 0)
                    Toast.makeText(getActivity(), "success", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void startTracePush(byte arg0, String arg1) {

            }
        });
    }

    /**
     * 我的投诉
     */
    @Override
    public void toMyComplaint() {

    }

    //工作量查询
    @Override
    public void toWorklog() {
        /*SearchWorkFragment fragment = new SearchWorkFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.replace(R.id.fragment_container_layout, fragment);
        transaction.addToBackStack("SorterMe");
        transaction.commit();*/
        MyFragmentManager.turnFragment(SorterMe.class, SearchWorkFragment.class, null, getFragmentManager());
    }

    @Override
    public void toReceiveRecordFragment() {
       // AddPackageListFragment fragment = new AddPackageListFragment();
        Bundle bundle = new Bundle();
        //  bundle.putString("packageID", "69026465584374");
        bundle.putString("packageID", ((MyApplication) getActivity().getApplication()).getEmployeesInfo().getRecvPackageId());
        /*fragment.setArguments(bundle);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container_layout, fragment);
        transaction.addToBackStack("SorterMe");
        transaction.commit();*/
        MyFragmentManager.turnFragment(SorterMe.class,AddPackageListFragment.class,bundle, getFragmentManager());
    }
}