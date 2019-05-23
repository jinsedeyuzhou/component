package com.ebrightmoon.data.router;

/**


// 注意事项！！！  /模块/页面名称   或  /模块/子模块../页面名称   至少两级  例子 /商城/商品详情 /shop/shopDetails
    //url 第一次相同会报错??
 */
public interface RouterURLS {
    //UI
    String UI_HOME="/ui/home";
    //用户系统 登陆
    String USER_LOGIN="/user/login";
    //用户系统 注册
    String USER_REGISTER="/user/register";
    //用户系统 设置
    String USER_SETTING="/user/setting";




}
