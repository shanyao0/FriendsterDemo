package shanyao.friendsterdemo;

import android.app.Application;
import android.content.Context;

/**
 * Created by zs on 2015/10/27.
 */


public class ShanYaoApplication extends Application {

	private static Context mContext;

	@Override
	public void onCreate() {
		super.onCreate();
		// 初始化mContext和mHandler
		mContext = getApplicationContext();
	}

	/**
	 * 获取全局的context
	 */
	public static Context getContext() {
		return mContext;
	}

}
