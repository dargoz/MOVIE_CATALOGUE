package com.dargoz.madesubmission;

public interface BaseView<T extends BasePresenter> {
    void setPresenter(T presenter);
}
