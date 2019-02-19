package com.jasoncareter.onetick.mPresenter;

import android.support.v4.app.Fragment;

public interface NavigationHost {
     void navigateTo(Fragment fragment , boolean addToBackStack) ;
}
