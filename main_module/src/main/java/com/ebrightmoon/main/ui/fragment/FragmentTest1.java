package com.ebrightmoon.main.ui.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.ebrightmoon.common.base.BaseFragment;
import com.ebrightmoon.commonlogic.widget.MultiStateView;
import com.ebrightmoon.main.R;

import java.util.ArrayList;

/**
 * Time: 2020-04-09
 * Author:wyy
 * Description:
 */
public class FragmentTest1 extends BaseFragment implements MultiStateView.StateListener {

    private ListView listView;

    @Override
    protected int getLayoutID() {
        return R.layout.fragment_test1;
    }

    @Override
    protected void initView(View view) {
        final MultiStateView multiStateView = view.findViewById(R.id.multiStateView);
        listView = view.findViewById(R.id.listView);
        multiStateView.setStateListener(this);
        multiStateView.getView(MultiStateView.ViewState.ERROR).findViewById(R.id.retry)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        multiStateView.setViewState(MultiStateView.ViewState.LOADING);
                        Toast.makeText(getContext(), "Fetching Data", Toast.LENGTH_SHORT).show();
                        multiStateView.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                multiStateView.setViewState(MultiStateView.ViewState.CONTENT);
                            }
                        }, 3000);
                    }
                });
        ArrayList<String> title = new ArrayList<>();
        int i = 0;
        while (i < 99) {
            i++;
            title.add("Row" + i);
        }
        listView.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, title));

        multiStateView.setViewState(MultiStateView.ViewState.ERROR);
        multiStateView.setAnimateLayoutChanges(false);


    }

    @Override
    protected void bindEvent() {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void processClick(View v) {

    }

    @Override
    public void onStateChanged(MultiStateView.ViewState viewState) {

    }
}
