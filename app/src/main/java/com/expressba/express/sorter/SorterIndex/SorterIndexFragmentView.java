package com.expressba.express.sorter.SorterIndex;

import com.expressba.express.model.PackageInfo;

/**
 * Created by 黎明 on 2016/5/4.
 */
public interface SorterIndexFragmentView {
    void onSuccess(int type,PackageInfo packageInfo);

    void onError(String errorMessage);

    void onSuccess();
}
