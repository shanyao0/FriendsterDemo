package shanyao.friendsterdemo.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import shanyao.friendsterdemo.utils.CommonUtils;
import shanyao.friendsterdemo.ShanYaoApplication;
import shanyao.friendsterdemo.widgets.HackyViewPager;
import shanyao.friendsterdemo.R;
import shanyao.friendsterdemo.utils.ToastUtil;
import uk.co.senab.photoview.PhotoViewAttacher;

public class ActivityImagePager extends FragmentActivity {
    private static final String STATE_POSITION = "STATE_POSITION";
    public static final String EXTRA_IMAGE_INDEX = "image_index";
    public static final String EXTRA_IMAGE_URLS = "image_urls";
    @Bind(R.id.hacky_viewpager)
    HackyViewPager hackyViewpager;
    @Bind(R.id.ll_pager_points)
    LinearLayout llPagerPoints;
    @Bind(R.id.iv_guide_red)
    ImageView ivGuideRed;

    private int pagerPosition;
    private TextView indicator;
    private PhotoViewAttacher attacher;
    int[] urls;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_image_pager);
        ButterKnife.bind(this);
        // 获取intent传递过来的值
        pagerPosition = getIntent().getIntExtra(EXTRA_IMAGE_INDEX, 0);
        urls = getIntent().getIntArrayExtra(EXTRA_IMAGE_URLS);
        /**
         * 动态的添加点
         */
        if (urls.length > 1) {
            ivGuideRed.setVisibility(View.VISIBLE);
            for (int i = 0; i < urls.length; i++) {
                View view = new View(this);
                view.setBackgroundResource(R.drawable.shape_point_nomal);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        CommonUtils.dip2px(ShanYaoApplication.getContext(), 5), CommonUtils.dip2px(ShanYaoApplication.getContext(), 5));
                if (i != 0) {
                    params.leftMargin = CommonUtils.dip2px(ShanYaoApplication.getContext(), 5);
                }
                view.setLayoutParams(params);
                llPagerPoints.addView(view);
            }
        }else{//就1张图片时不显示点
            ivGuideRed.setVisibility(View.INVISIBLE);
        }
        /**
         * 设置ViewPager
         */
        hackyViewpager.setAdapter(new hackyPagerAdapter());
        // 设置显示默认的图片
        hackyViewpager.setCurrentItem(pagerPosition);
        hackyViewpager.setOnPageChangeListener(listener);

    }

    class hackyPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return urls.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            /**
             * 使用photoView设置图片
             */
            ImageView view = new ImageView(ShanYaoApplication.getContext());
            view.setImageResource(urls[position]);
            attacher = new PhotoViewAttacher(view);
            attacher.update();
            /**
             * 设置长按事件
             */
            attacher.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    ToastUtil.show(ShanYaoApplication.getContext(), "这是第" + position + "张图片");
                    return true;
                }
            });
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    ViewPager.OnPageChangeListener listener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) ivGuideRed
                    .getLayoutParams();
            // 获取红点移动的距离
            int redPoint = (int) (CommonUtils.dip2px(ShanYaoApplication.getContext(), 10) * (positionOffset + position));
            // 设置margin值
            params.leftMargin = redPoint;
            // 重新绘制图片控件
            ivGuideRed.setLayoutParams(params);
        }

        @Override
        public void onPageSelected(int position) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
}