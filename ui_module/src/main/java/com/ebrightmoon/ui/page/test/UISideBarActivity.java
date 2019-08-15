package com.ebrightmoon.ui.page.test;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ebrightmoon.common.base.BaseActivity;
import com.ebrightmoon.common.widget.WaveSideBar;
import com.ebrightmoon.ui.R;
import com.ebrightmoon.ui.adapter.test.ContactsAdapter;
import com.ebrightmoon.ui.data.test.Contact;

import java.util.ArrayList;

/**
 * Time: 2019-08-12
 * Author:wyy
 * Description:
 */
public class UISideBarActivity extends BaseActivity {
    private RecyclerView rvContacts;
    private WaveSideBar sideBar;

    private ArrayList<Contact> contacts = new ArrayList<>();
    @Override
    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.ui_activity_sidebar);

    }

    @Override
    public void initData() {
        contacts.addAll(Contact.getEnglishContacts());
    }

    @Override
    public void initView() {
        rvContacts = (RecyclerView) findViewById(R.id.rv_contacts);
        rvContacts.setLayoutManager(new LinearLayoutManager(this));
        rvContacts.setAdapter(new ContactsAdapter(contacts, R.layout.item_contacts));
        sideBar = (WaveSideBar) findViewById(R.id.side_bar);
        sideBar.setLazyRespond(false);
        sideBar.setMaxOffset(100);
        sideBar.setTextAlign(WaveSideBar.TEXT_ALIGN_LEFT);

        sideBar.setOnSelectIndexItemListener(new WaveSideBar.OnSelectIndexItemListener() {
            @Override
            public void onSelectIndexItem(String index) {
                for (int i=0; i<contacts.size(); i++) {
                    if (contacts.get(i).getIndex().equals(index)) {
                        ((LinearLayoutManager) rvContacts.getLayoutManager()).scrollToPositionWithOffset(i, 0);
                        return;
                    }
                }
            }
        });
    }

    @Override
    protected void bindEvent() {

    }

    @Override
    public void processClick(View paramView) {

    }
}
