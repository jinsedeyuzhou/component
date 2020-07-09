package com.ebrightmoon.common.http;

/**
 * Time: 2020/6/15
 * Author:wyy
 * Description:
 */
public class ApiConfig {

    public static final String Base_URL = "https://t4.fsyuncai.com/";

    // 轨迹点上传
    public static final String URL_MOBILE_UPLOAD = "tmsmobile/route/upload";

    //查询最新任务
    public static final String URL_HOME_ONGOING_TASK = "dispatch/newest";
    // 首页未度数上报异常
    public static final String URL_UNREAD_COUNT = "user/unreadCount";
    // 查询任务列表
    public static final String URL_DISPATCH_TASKLIST = "dispatch/taskList";
    // 查询运单列表
    public static final String URL_TRANSPORT_BILLLIST="transport/transportBillList";
    // 查询派车单装车点位信息
    public static final String URL_LOADING_POINT="loading/loadingPoint";
    // 查询月台拣货进度 loading/picking
    public static final String URL_LOADING_PICKING="loading/picking";
    //查询运单详情
    public static final String URL_TRANSPORT_DETAIL="transport/detail";


}
