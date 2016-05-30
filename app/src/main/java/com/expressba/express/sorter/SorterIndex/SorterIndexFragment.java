package com.expressba.express.sorter.SorterIndex;

import android.app.Activity;
import android.app.AlertDialog;

import com.expressba.express.map.MyHistoryTrace;
import com.expressba.express.model.EmployeesEntity;
import com.expressba.express.myelement.MyFragmentManager;

import android.app.Dialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.expressba.express.main.UIFragment;
import com.expressba.express.zxing.activity.CaptureActivity;

import com.expressba.express.model.PackageInfo;
import com.expressba.express.sorter.Expressupdate.ExpressUpdateFragment;
import com.expressba.express.sorter.SorterMe;
import com.expressba.express.sorter.open.ep_search.package_list.PackageListFragment;
import com.expressba.express.sorter.open.package_search.PackageSearchFragment;
import com.expressba.express.main.MyApplication;
import com.expressba.express.sorter.close.add_package_list.AddPackageListFragment;
import com.expressba.express.sorter.close.new_package_info.NewPackageInfoFragment;
import com.expressba.express.R;
import com.expressba.express.user.login.LoginFragment;

/**
 * Created by 黎明 on 2016/4/25.
 */
public class SorterIndexFragment extends UIFragment implements SorterIndexFragmentView {
    private Button meButton;
    private FragmentTransaction transaction;
    private ImageButton cameraButton;
    private ImageButton messageButton;
    private MyApplication myApplication;
    private SorterIndexPresenter presenter;
    private int EmployeesID;
    private static String result;
    private Button open, close;
    private EditText input;
    FragmentManager fragmentManager;
    private final static int SORTER = 2, DELIVER = 1, DRIVER = 3;
    private static int TYPE;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sorter_index_fragment, container, false);
        myApplication = (MyApplication) getActivity().getApplication();
        final EmployeesEntity employeesEntity = myApplication.getEmployeesInfo();
        transaction = getFragmentManager().beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        cameraButton = (ImageButton) view.findViewById(R.id.index_top_bar_camera);
        messageButton = (ImageButton) view.findViewById(R.id.index_top_bar_message);
        messageButton.setImageResource(R.mipmap.search);
        open = (Button) view.findViewById(R.id.open);
        input = (EditText) view.findViewById(R.id.index_top_bar_input);
        input.setHint("请输入快件单号");
        close = (Button) view.findViewById(R.id.close);
        EmployeesID = employeesEntity.getId();

        fragmentManager = getFragmentManager();
        transaction = fragmentManager.beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startCamera();
                //1为拆包动作 此时需调用zxing执行扫码 扫码完毕后会返回一个packageID
                //拆包就是扫码-显示包裹信息-查看快件信息-确认拆包
            }
        });
        close = (Button) view.findViewById(R.id.close);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                init();
                if (employeesEntity.getJob() == null) {
                    Toast.makeText(getActivity(), "请登录", Toast.LENGTH_SHORT).show();
                } else {
                    TYPE = employeesEntity.getJob();
                    if (TYPE == SORTER)
                        MyFragmentManager.turnFragment(SorterIndexFragment.class, NewPackageInfoFragment.class, null, getFragmentManager());
                    else if (TYPE == DELIVER) {
                        Dialog dialog = new AlertDialog.Builder(getActivity()).setIcon(
                                android.R.drawable.btn_star).setTitle("包裹类型").setMessage(
                                "请选择包裹类型").setPositiveButton("揽收包",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        presenter.CreatPackage(0, 1, EmployeesID, 0);
                                        //揽收包fromID设为0 isSorter为0
                                    }
                                }).setNegativeButton("派送包", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                presenter.CreatPackage(1, 1, EmployeesID, 0);
                                //派送包fromID设为1 isSorter为0
                            }
                        }).create();
                        dialog.show();
                    }
                }
            }
        });

        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startCamera();

            }
        });
        messageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!input.getText().toString().isEmpty())
                    sendToPackageFragment(input.getText().toString());
            }
        });
        meButton = (Button) view.findViewById(R.id.me_button);
        meButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!myApplication.getEmployeesInfo().getLoginState()) {//没有登陆跳转登陆界面
                    toLoginFragment();
                } else {
                    //登陆后跳转"我"界面
                    toMeFragment();
                }
            }
        });
        return view;
    }

    private void toMeFragment() {
        MyFragmentManager.turnFragment(SorterIndexFragment.class, SorterMe.class, null, getFragmentManager());
    }

    private void toLoginFragment() {
        MyFragmentManager.turnFragment(SorterIndexFragment.class, LoginFragment.class, null, getFragmentManager());
    }

    private void startCamera() {
        startActivityForResult(new Intent(getActivity(), CaptureActivity.class), 0);
    }

    @Override
    public void onActivityResult(final int requestCode, int resultCode, Intent data) {

        initResult();
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 0) {
                final Bundle bundle = data.getExtras();
                result = bundle.getString("result");

                if (TYPE == SORTER) {
                    Bundle bundle1 = new Bundle();
                    bundle1.putString("ID", result);
                    MyFragmentManager.turnFragment(SorterIndexFragment.class, PackageSearchFragment.class, bundle1, getFragmentManager());
                } else if (TYPE == DELIVER) {
                    Bundle bundle2 = new Bundle();
                    bundle.putString("packageID", result);

                    MyFragmentManager.turnFragment(SorterIndexFragment.class, PackageListFragment.class, bundle2, getFragmentManager());
                } else if (TYPE == DRIVER) {
                    LoadPackageIntoDriverPresenter presenter1 = new LoadPackageDriverPresenterImpl(getActivity(), this);
                    presenter1.loadPackageIntoDriver(result);
                }
            }
        }
    }

    private void initResult() {
        TYPE = ((MyApplication) getActivity().getApplication()).getEmployeesInfo().getJob();
    }

    @Override
    public void onSuccess(int type,final PackageInfo packageInfo) {
        //快递员打包成功，将application中加入packageID
        if(type==0)
        ((MyApplication)getActivity().getApplication()).getEmployeesInfo().setRecvPackageId(packageInfo.getId());
        else if(type==1)
            ((MyApplication)getActivity().getApplication()).getEmployeesInfo().setSendPackageId(packageInfo.getId());
        MyHistoryTrace MyTrace = new MyHistoryTrace();
        MyTrace.startTraceClient(getActivity(), String.valueOf(((MyApplication) getActivity().getApplication()).getEmployeesInfo().getId()), new MyHistoryTrace.StartTraceInterface() {
            @Override
            public void startTraceCallBack(int stateCode, String message) {
                if(stateCode==0)
                    Toast.makeText(getActivity(), "success", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void startTracePush(byte arg0, String arg1) {

            }
        });

        Dialog dialog = new AlertDialog.Builder(getActivity()).setIcon(
                android.R.drawable.btn_star).setTitle("通知").setMessage(
                "创建包裹成功，包裹ID为" + packageInfo.getId() + "是否向包裹中添加快件？").setPositiveButton("是",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // AddPackageListFragment fragment = new AddPackageListFragment();
                        Bundle bundle = new Bundle();
                        bundle.putString("packageID", packageInfo.getId());
                        MyFragmentManager.turnFragment(SorterIndexFragment.class, AddPackageListFragment.class, bundle, getFragmentManager());
                    }
                }).setNegativeButton("否", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getFragmentManager().popBackStack();
            }
        }).create();
        dialog.show();
    }

    @Override
    public void onError(String errorMessage) {
        Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_SHORT).show();
    }

    public void sendToPackageFragment(String input) {
        Bundle bundle = new Bundle();
        bundle.putString("ID", input);
        MyFragmentManager.turnFragment(SorterIndexFragment.class, ExpressUpdateFragment.class, bundle, getFragmentManager());
    }

    @Override
    public void onSuccess() {
        //司机上传轨迹
       MyHistoryTrace MyTrace = new MyHistoryTrace();
        MyTrace.startTraceClient(getActivity(), String.valueOf(((MyApplication) getActivity().getApplication()).getEmployeesInfo().getId()), new MyHistoryTrace.StartTraceInterface() {
            @Override
            public void startTraceCallBack(int stateCode, String message) {
                if(stateCode==0)
                    Toast.makeText(getActivity(), "success", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void startTracePush(byte arg0, String arg1) {

            }
        });


    }

    public void init() {
        if (((MyApplication) getActivity().getApplication()).getEmployeesInfo().getLoginState() == true) {
            presenter = new SorterIndexPresenterImpl(getActivity(), this);
            EmployeesEntity employeesEntity=((MyApplication) getActivity().getApplication()).getEmployeesInfo();
            TYPE = employeesEntity.getJob();
            EmployeesID = employeesEntity.getId();
        }
    }
}

