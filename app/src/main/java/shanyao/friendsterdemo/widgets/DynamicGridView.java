package shanyao.friendsterdemo.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.GridView;

public class DynamicGridView extends GridView {
	public Context mContext;
	public DynamicGridView(Context context) {
		super(context);
		this.mContext = context;
	}

	public DynamicGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.mContext = context;
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int  expandSpec = 0;
	    int  size = getAdapter().getCount();
	    
		if (size == 1) {
			setNumColumns(1);
		} 
		if ( size==2 || size == 4  ) {
			setNumColumns(2);
			ViewGroup.LayoutParams params = this.getLayoutParams();
			params.width = dip2px(136);
			setLayoutParams(params);
		}
		else {
			setNumColumns(3);
			ViewGroup.LayoutParams params = this.getLayoutParams();
			params.width = dip2px(207);
			setLayoutParams(params);
		}
		expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec,expandSpec );
	}
	/**
	 * dip转化成px
	 */
	public int dip2px(float dpValue) {
		final float scale = mContext.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}
}
