package com.lxkj.xpp.legal.event;

import android.os.Bundle;

import com.lxkj.xpp.legal.base.BaseFragment;

/**
 * Created by 熊萍萍 on 2017/2/27/027.
 */
public class NavFragmentEvent {
    public BaseFragment fragment;
    public Bundle bundle;

    public NavFragmentEvent(BaseFragment fragment) {
        this.fragment = fragment;
    }

    public NavFragmentEvent(BaseFragment fragment, Bundle bundle) {
        this.fragment = fragment;
        this.bundle = bundle;
    }
}
