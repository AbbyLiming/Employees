package com.expressba.express.main;

import android.app.Activity;
import android.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;

import com.expressba.express.R;
import com.expressba.express.myelement.MyFragmentManager;
import com.expressba.express.sorter.SorterIndex.SorterIndexFragment;


public class MainActivity extends Activity implements MainView {

    FragmentManager fm;
    MainPresenter mainPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainPresenter = new MainPresenterImpl(this);
        if(savedInstanceState == null) {//保存状态，防止重建
            fm = getFragmentManager();
            setDefaultFragment();//设置第一个主布局的fragment
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void setDefaultFragment() {
        MyFragmentManager.turnFragment(null,SorterIndexFragment.class,null,getFragmentManager(),false);
        /*Bundle bundle=new Bundle();
        bundle.putString("ID","28314616734325");
        MyFragmentManager.turnFragment(null,DeliverUpdateExpressFragment.class,bundle,getFragmentManager(),false);
    */
    }

}
