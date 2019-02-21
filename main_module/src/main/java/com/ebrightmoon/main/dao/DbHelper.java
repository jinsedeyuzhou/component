package com.ebrightmoon.main.dao;

import android.content.Context;

import com.ebrightmoon.main.entity.OrderFeed;
import com.ebrightmoon.main.entity.SearchHistory;
import com.ebrightmoon.main.gen.DaoMaster;
import com.ebrightmoon.main.gen.DaoSession;

import org.greenrobot.greendao.AbstractDao;


/**
 * @Description: 数据库操作类，由于greenDao的特殊性，不能在框架中搭建，
 * 所有数据库操作都可以参考该类实现自己的数据库操作管理类，不同的Dao实现
 * 对应的getAbstractDao方法就行。
 */
public class DbHelper {
    private static final String DB_NAME = "news.db";//数据库名称
    private static DbHelper instance;
    private static DBManager<OrderFeed, Long> author;
    private static DBManager<SearchHistory, Long> history;
    private DaoMaster.DevOpenHelper mHelper;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;
    private DBManager product;

    private DbHelper() {

    }

    public static DbHelper getInstance() {
        if (instance == null) {
            synchronized (DbHelper.class) {
                if (instance == null) {
                    instance = new DbHelper();
                }
            }
        }
        return instance;
    }

    public void init(Context context) {
        mHelper = new MyOpenHelper(context, DB_NAME, null);
        mDaoMaster = new DaoMaster(mHelper.getWritableDatabase());
        mDaoSession = mDaoMaster.newSession();
    }

    public void init(Context context, String dbName) {
        mHelper = new MyOpenHelper(context, dbName, null);
        mDaoMaster = new DaoMaster(mHelper.getWritableDatabase());
        mDaoSession = mDaoMaster.newSession();
    }

    /**
     * @return
     */
    public DBManager<OrderFeed, Long> author() {
        if (author == null) {
            author = new DBManager<OrderFeed, Long>() {
                @Override
                public AbstractDao<OrderFeed, Long> getAbstractDao() {
                    return mDaoSession.getOrderFeedDao();
                }
            };
        }
        return author;
    }

  public DBManager<SearchHistory, Long> history() {
        if (history == null) {
            history = new DBManager<SearchHistory, Long>() {
                @Override
                public AbstractDao<SearchHistory, Long> getAbstractDao() {
                    return mDaoSession.getSearchHistoryDao();
                }
            };
        }
        return history;
    }


    public DaoMaster getDaoMaster() {
        return mDaoMaster;
    }

    public DaoSession getDaoSession() {
        return mDaoSession;
    }

    public void clear() {
        if (mDaoSession != null) {
            mDaoSession.clear();
            mDaoSession = null;
        }
    }

    public void close() {
        clear();
        if (mHelper != null) {
            mHelper.close();
            mHelper = null;
        }
    }
}
