package com.ebrightmoon.main.adapter;

import android.content.Context;
import androidx.fragment.app.FragmentManager;


import com.ebrightmoon.common.base.mvc.BaseFragment;
import com.ebrightmoon.main.entity.Channel;

import java.util.ArrayList;

/**
 * 主页Fragment 适配,其他有关fragment也可以用
 *
 * @author wyy
 */
public class NewsChannelAdapter
        extends BaseFramentAdapter {
    private ArrayList<Channel> mChannels;

    public NewsChannelAdapter(Context paramContext, FragmentManager paramFragmentManager, ArrayList<BaseFragment> paramArrayList, ArrayList<Channel> mChannels) {
        super(paramContext, paramFragmentManager, paramArrayList);
        this.mChannels = mChannels;
    }

    public CharSequence getPageTitle(int position) {
        return (this.mChannels.get(position).getName());
    }

}
