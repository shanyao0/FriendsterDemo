package shanyao.friendsterdemo.utils;

import android.content.Context;

public class CommonUtils {
	/**
	 * dip转化成px
	 */
	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}
}


