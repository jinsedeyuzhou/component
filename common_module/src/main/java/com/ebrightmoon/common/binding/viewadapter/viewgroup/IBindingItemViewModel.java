package com.ebrightmoon.common.binding.viewadapter.viewgroup;

import androidx.databinding.ViewDataBinding;

/**
 *   on 2017/6/15.
 */


public interface IBindingItemViewModel<V extends ViewDataBinding> {
    void injecDataBinding(V binding);
}
