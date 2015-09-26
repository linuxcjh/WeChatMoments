package com.alex.wechatmoments;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * ActivityBase
 */
public abstract class ActivityBase extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(setContentViewResId());
		findWigetAndListener();
		initData();
	}

	/**
	 *
	 * @param id
	 * @param <T>
	 * @return
	 */
	public <T extends View> T getViewById(int id) {
		return (T) findViewById(id);
	}

	public abstract int setContentViewResId();

	public abstract void findWigetAndListener();

	public abstract void initData();


}
