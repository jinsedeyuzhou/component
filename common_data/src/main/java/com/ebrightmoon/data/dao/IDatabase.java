package com.ebrightmoon.data.dao;


import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.query.Query;
import org.greenrobot.greendao.query.QueryBuilder;

import java.util.Collection;
import java.util.List;

/**
 * @Description: 数据操库作接口
 */
public interface IDatabase<M, K> {
    boolean insert(M m);

    boolean insertOrReplace(M m);

    boolean insertInTx(List<M> list);

    boolean insertOrReplaceInTx(List<M> list);

    boolean delete(M m);

    boolean deleteByKey(K key);

    boolean deleteInTx(List<M> list);

    boolean deleteByKeyInTx(K... key);

    boolean deleteAll();

    boolean update(M m);

    boolean updateInTx(M... m);

    boolean updateInTx(List<M> list);

    M load(K key);

    List<M> loadAll();

    boolean refresh(M m);

    void runInTx(Runnable runnable);

    AbstractDao<M, K> getAbstractDao();

    QueryBuilder<M> queryBuilder();

    List<M> queryRaw(String where, String... selectionArg);

    Query<M> queryRawCreate(String where, Object... selectionArg);

    Query<M> queryRawCreateListArgs(String where, Collection<Object> selectionArg);

}
