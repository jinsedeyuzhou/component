package com.ebrightmoon.ui.page.test;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ebrightmoon.common.base.BaseActivity;
import com.ebrightmoon.common.util.DensityUtils;
import com.ebrightmoon.common.widget.WaveSideBar;
import com.ebrightmoon.commonlogic.sidebar.StickyDecoration;
import com.ebrightmoon.commonlogic.sidebar.listener.GroupListener;
import com.ebrightmoon.commonlogic.sidebar.listener.OnGroupClickListener;
import com.ebrightmoon.ui.R;
import com.ebrightmoon.ui.adapter.contact.ContactsAdapter;
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
        sideBar = (WaveSideBar) findViewById(R.id.side_bar);
        sideBar.setLazyRespond(false);
        sideBar.setMaxOffset(100);
        sideBar.setTextAlign(WaveSideBar.TEXT_ALIGN_LEFT);
        StickyDecoration.Builder builder = StickyDecoration.Builder
                .init(new GroupListener() {
                    @Override
                    public String getGroupName(int position) {
                        //组名回调
                        if (contacts.size() > position && position > -1) {
                            //获取组名，用于判断是否是同一组
                            return contacts.get(position).getIndex();
                        }
                        return null;
                    }
                })
                //背景色
                .setGroupBackground(Color.parseColor("#48BDFF"))
                //高度
                .setGroupHeight(DensityUtils.dip2px( 35))
                //分割线颜色
                .setDivideColor(Color.parseColor("#EE96BC"))
                //分割线高度 (默认没有分割线)
//                .setDivideHeight(DensityUtil.dip2px(this, 2))
                //字体颜色 （默认）
                .setGroupTextColor(Color.BLACK)
                //字体大小
                .setGroupTextSize(DensityUtils.sp2px(this, 15))
                // 边距   靠左时为左边距  靠右时为右边距
                .setTextSideMargin(DensityUtils.dip2px( 10))
                // header数量（默认0）
                //.setHeaderCount(1)
                //Group点击事件
                .setOnClickListener(new OnGroupClickListener() {
                    @Override
                    public void onClick(int position, int id) {
                        //点击事件，返回当前分组下的第一个item的position
//                        String content = "onGroupClick --> " + position + " " +  contactList.get(position).getFirstName();
//                        Toast.makeText(MainActivity.this, content, Toast.LENGTH_SHORT).show();
                    }
                });

        StickyDecoration decoration = builder.build();
        rvContacts.addItemDecoration(decoration);
        rvContacts.setAdapter(new ContactsAdapter(contacts, R.layout.item_contacts));

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
