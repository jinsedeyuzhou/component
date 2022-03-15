package com.ebrightmoon.user.page;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.ebrightmoon.common.base.mvc.BaseActivity;
import com.ebrightmoon.data.router.RouterURLS;
import com.ebrightmoon.user.R;
import com.google.android.material.textfield.TextInputLayout;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 作者：create by  Administrator on 2018/9/30
 * 邮箱：
 */
@Route(path = RouterURLS.USER_REGISTER)
public class RegisterUserActivity extends BaseActivity {
    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9#_~!$&'()*+,;=:.\"(),:;<>@\\[\\]\\\\]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*$";
    private static final String PHONE_PATTERN ="^1\\d{10}$";
    private static final String PWD_PATTERN ="^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,15}$";
    private Pattern pattern = Pattern.compile(PHONE_PATTERN);
    private Pattern pwdPattern = Pattern.compile(PWD_PATTERN);
    private Matcher matcher;
    private TextInputLayout usernameWrapper;
    private TextInputLayout passwordWrapper;
    private Button mBtnLogin;
    @Override
    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_user_register);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {
        usernameWrapper = (TextInputLayout) findViewById(R.id.usernameWrapper);
        passwordWrapper = (TextInputLayout) findViewById(R.id.passwordWrapper);
        mBtnLogin = (Button) findViewById(R.id.btn_login);
        mBtnLogin.setOnClickListener(this);
        usernameWrapper.setHint("手机号码");
        passwordWrapper.setHint("密码");
    }

    @Override
    protected void bindEvent() {

    }

    @Override
    public void processClick(View paramView) {
        int id = paramView.getId();
        if (id==R.id.btn_login) {
            hideKeyboard();
            String username = usernameWrapper.getEditText().getText().toString();
            String password = passwordWrapper.getEditText().getText().toString();
            if (!validateEmail(username)) {
                usernameWrapper.setError("Not a valid phone!");
            } else if (!validatePassword(password)) {
                passwordWrapper.setError("Not a valid password!");
            } else {
                usernameWrapper.setErrorEnabled(false);
                passwordWrapper.setErrorEnabled(false);
                doRegister(username, password);
            }
        }
    }


    /**
     * 注册方法
     */
        private void doRegister (String mobile,String password) {
        }


        /**
         * 隐藏系统键盘
         */
        private void hideKeyboard () {
            View view = getCurrentFocus();
            if (view != null) {
                ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).
                        hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }

        /**
         * 验证密码是否正确
         * @param password
         * @return
         */
        public boolean validatePassword (String password){
            matcher = pwdPattern.matcher(password);
            return matcher.matches();
        }

        /**
         * 验证手机号和邮箱
         * @param email
         * @return
         */
        public boolean validateEmail (String email){
            matcher = pattern.matcher(email);
            return matcher.matches();
        }

    }
