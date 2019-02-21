package com.ebrightmoon.main.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;


import com.ebrightmoon.main.gen.DaoMaster;
import com.ebrightmoon.main.gen.OrderFeedDao;
import com.ebrightmoon.main.gen.SearchHistoryDao;

import org.greenrobot.greendao.database.Database;

/**
 * 作者：create by  Administrator on 2019/2/20
 * 邮箱：2315813288@qq.com
 */
public class MyOpenHelper extends DaoMaster.DevOpenHelper {

    public MyOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }

    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {
        super.onUpgrade(db, oldVersion, newVersion);
        MigrationHelper.getInstance().migrate(db, OrderFeedDao.class, SearchHistoryDao.class);
    }
}
