package com.ebrightmoon.main.dao;

import android.database.sqlite.SQLiteException;
import android.util.Log;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.query.Query;
import org.greenrobot.greendao.query.QueryBuilder;

import java.util.Collection;
import java.util.List;

/**
 * @Description: 数据库操作管理
 */
public abstract class DBManager<M, K> implements IDatabase<M, K> {


    @Override
    public boolean insert( M m) {
        try {
            getAbstractDao().insert(m);
        } catch (SQLiteException e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean insertOrReplace( M m) {
        try {
            getAbstractDao().insertOrReplace(m);
        } catch (SQLiteException e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean insertInTx( List<M> list) {
        try {
            getAbstractDao().insertInTx(list);
        } catch (SQLiteException e) {
            
            return false;
        }
        return true;
    }

    @Override
    public boolean insertOrReplaceInTx( List<M> list) {
        try {
            getAbstractDao().insertOrReplaceInTx(list);
        } catch (SQLiteException e) {
            
            return false;
        }
        return true;
    }

    @Override
    public boolean delete( M m) {
        try {
            getAbstractDao().delete(m);
        } catch (SQLiteException e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean deleteByKey( K key) {
        try {
            getAbstractDao().deleteByKey(key);
        } catch (SQLiteException e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean deleteInTx( List<M> list) {
        try {
            getAbstractDao().deleteInTx(list);
        } catch (SQLiteException e) {
            return false;
        }
        return true;
    }

    @SafeVarargs
    @Override
    public final boolean deleteByKeyInTx( K... key) {
        try {
            getAbstractDao().deleteByKeyInTx(key);
        } catch (SQLiteException e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean deleteAll() {
        try {
            getAbstractDao().deleteAll();
        } catch (SQLiteException e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean update( M m) {
        try {
            getAbstractDao().update(m);
        } catch (SQLiteException e) {
            return false;
        }
        return true;
    }

    @SafeVarargs
    @Override
    public final boolean updateInTx( M... m) {
        try {
            getAbstractDao().updateInTx(m);
        } catch (SQLiteException e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean updateInTx( List<M> list) {
        try {
            getAbstractDao().updateInTx(list);
        } catch (SQLiteException e) {
            return false;
        }
        return true;
    }

    @Override
    public M load( K key) {
        try {
            return getAbstractDao().load(key);
        } catch (SQLiteException e) {
            return null;
        }
    }

    @Override
    public List<M> loadAll() {
        return getAbstractDao().loadAll();
    }

    @Override
    public boolean refresh( M m) {
        try {
            getAbstractDao().refresh(m);
        } catch (SQLiteException e) {
            return false;
        }
        return true;
    }

    @Override
    public void runInTx( Runnable runnable) {
        try {
            getAbstractDao().getSession().runInTx(runnable);
        } catch (SQLiteException e) {
            Log.v("DBManager",e.getMessage());
        }

    }

    @Override
    public QueryBuilder<M> queryBuilder() {
        return getAbstractDao().queryBuilder();
    }

    @Override
    public List<M> queryRaw(String where, String... selectionArg) {
        return getAbstractDao().queryRaw(where, selectionArg);
    }

    @Override
    public Query<M> queryRawCreate(String where, Object... selectionArg) {
        return getAbstractDao().queryRawCreate(where, selectionArg);
    }

    @Override
    public Query<M> queryRawCreateListArgs(String where, Collection<Object> selectionArg) {
        return getAbstractDao().queryRawCreateListArgs(where, selectionArg);
    }

    @Override
    public abstract AbstractDao<M, K> getAbstractDao();

}
