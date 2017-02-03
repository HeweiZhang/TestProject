package com.sikeda.mvpsample.app.main;

import java.util.List;

/**
 * Created by Administrator on 2016/11/14.
 */

public interface MainView {
    void showProgress();
    void hideProgress();
    void setItemsList(List<String> itemsList);
    void showMessage(String message);

    void navigateToImage();
}
