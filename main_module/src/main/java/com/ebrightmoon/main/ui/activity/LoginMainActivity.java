package com.ebrightmoon.main.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ebrightmoon.common.base.BaseActivity;
import com.ebrightmoon.main.R;
import com.ebrightmoon.main.entity.UserInfo;
import com.ebrightmoon.retrofitrx.callback.ACallback;
import com.ebrightmoon.retrofitrx.common.AppConfig;
import com.ebrightmoon.retrofitrx.util.MD5;
import com.ebrightmoon.retrofitrx.response.ResponseResult;
import com.ebrightmoon.retrofitrx.retrofit.AppClient;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 作者：create by  Administrator on 2018/9/30
 * 邮箱：2315813288@qq.com
 */
public class LoginMainActivity extends BaseActivity {
    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9#_~!$&'()*+,;=:.\"(),:;<>@\\[\\]\\\\]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*$";
    private static final String PHONE_PATTERN ="^1\\d{10}$";
    private static final String PWD_PATTERN ="^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,15}$";
    private Pattern pattern = Pattern.compile(PHONE_PATTERN);
    private Pattern pwdPattern = Pattern.compile(PWD_PATTERN);
    private Matcher matcher;
    private TextInputLayout usernameWrapper;
    private TextInputLayout passwordWrapper;
    private Button mBtnLogin;
    private TextView mTvRegister;
    private TextView mTvForgetPwd;


    @Override
    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_main_login);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {
        usernameWrapper = (TextInputLayout) findViewById(R.id.usernameWrapper);
        passwordWrapper = (TextInputLayout) findViewById(R.id.passwordWrapper);
        mBtnLogin = (Button) findViewById(R.id.btn_login);

        mTvRegister = findViewById(R.id.tv_register);
        mTvForgetPwd = findViewById(R.id.tv_forget_pwd);

        mTvRegister.setOnClickListener(this);
        mTvForgetPwd.setOnClickListener(this);
        mBtnLogin.setOnClickListener(this);
        usernameWrapper.setHint("手机号码");
        passwordWrapper.setHint("密码");
    }

    @Override
    protected void bindEvent() {

    }

    @Override
    public void processClick(View paramView) {
        int id =paramView.getId();
        if (id== R.id.btn_login) {
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
                doLogin(username, password);
            }
        }else if (id==R.id.tv_register) {
            toOtherActivity(RegisterMainActivity.class, null, false);
        }else if (id==R.id.tv_forget_pwd) {
            toOtherActivity(LoginMainActivity.class, null, false);
        }
    }


        private void doLogin (String mobile,String password) {

            Map<String, String> registers = new HashMap<>();
            registers.put("mobile",mobile);
            registers.put("userPwd", MD5.encode(password));
            AppClient.getInstance().postResponseResult(AppConfig.URL_LOGIN, registers, new ACallback<ResponseResult<UserInfo>>() {
                @Override
                public void onSuccess(ResponseResult<UserInfo> data) {
                    finish();
                    Toast.makeText(getApplicationContext(),data.getMsg(),Toast.LENGTH_LONG).show();
                }

                @Override
                public void onFail(int errCode, String errMsg) {
                    Toast.makeText(getApplicationContext(),errMsg,Toast.LENGTH_LONG).show();
                }
            });
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
