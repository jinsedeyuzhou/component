package com.ebrightmoon.data.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.ebrightmoon.data.gen.DaoMaster;
import com.ebrightmoon.data.gen.NewsFeedDao;
import com.ebrightmoon.data.pojo.NewsFeed;

import org.greenrobot.greendao.database.Database;

/**
 * 作者：create by  Administrator on 2019/2/20
 * 邮箱：
 */
public class MyOpenHelper extends DaoMaster.DevOpenHelper {

    public MyOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }

    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {
        super.onUpgrade(db, oldVersion, newVersion);
        MigrationHelper.getInstance().migrate(db, NewsFeedDao.class);
    }
}
