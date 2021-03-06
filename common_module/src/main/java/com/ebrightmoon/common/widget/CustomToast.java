package com.ebrightmoon.common.widget;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ebrightmoon.common.R;


/**
 * 自定义弹出对话框
 * @author wyy
 * 系统自定义Toast
 */
public class CustomToast extends Toast {

	private TextView textView;
	private String text;
	private View view;
	private int time;
	private Context context;

	public CustomToast(Context context, String text, int time) {
		super(context);
		this.context = context;
		this.text = text;
		this.time = time;
		init();
	}

	private void init() {
		view = View.inflate(context, R.layout.custom_toast, null);
		setView(view);
		textView = (TextView) view.findViewById(R.id.textView);
		textView.setText(text);
		setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);
		setDuration(time);
	}


	public static CustomToast makeText(Context context, CharSequence text,  int duration) {
		CustomToast result = new CustomToast(context,text.toString(),duration);
		return result;
	}

}
