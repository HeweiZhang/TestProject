package com.sikeda.mvpsample.app.main;

import java.util.List;

/**
 * Created by Administrator on 2016/11/14.
 */

public class MainPresenterImpl implements MainPresenter,FindItemsInteractor.OnFinishedListener {
    private MainView mainView;
    private FindItemsInteractor findItemsInteractor;

    public MainPresenterImpl(MainView mainView, FindItemsInteractor findItemsInteractor) {
        this.mainView = mainView;
        this.findItemsInteractor = findItemsInteractor;
        findItemsInteractor.findItems(this);
    }

    @Override
    public void onResume() {
        if (mainView != null) {
            mainView.showProgress();
        }

    }

    @Override
    public void onItemClicked(int position) {
        if (mainView != null) {
            mainView.showMessage(String.format("Position %d clicked",position ));
        }
        if(position == 0){
            mainView.navigateToImage();
        }
    }

    @Override
    public void onDestroy() {
        mainView = null;
    }

    @Override
    public void onFinished(List<String> items) {
        if (mainView != null) {
            mainView.setItemsList(items);
            mainView.hideProgress();
        }
    }

    public MainView getMainView(){
        return mainView;
    }
}
