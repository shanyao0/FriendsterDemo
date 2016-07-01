package shanyao.friendsterdemo.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;
import shanyao.friendsterdemo.bean.DynamicComment;
import shanyao.friendsterdemo.widgets.DynamicGridView;
import shanyao.friendsterdemo.ShanYaoApplication;
import shanyao.friendsterdemo.manager.HandleSpannable;
import shanyao.friendsterdemo.interfaces.OnClickComment;
import shanyao.friendsterdemo.R;
import shanyao.friendsterdemo.activity.ActivityImagePager;
import shanyao.friendsterdemo.utils.ConstantUtils;
import shanyao.friendsterdemo.utils.ToastUtil;

/**
 * Created by zs on 2015/11/6.
 */
public class FriendsterAdapter extends BaseAdapter {
    Context context;
    boolean isZan;// 是否点赞：从服务器获取
    boolean isAnimtion = false;// 判断当前是否有动画
    private ArrayList<String> list;
    ArrayList<String> likeList;// 点赞人的集合

    public FriendsterAdapter(ArrayList list, Context context) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_friendster, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        /**
         * 随机图片数量，处理GridView的图片的加载
         */
        Random random = new Random();
        int i = random.nextInt(10);
        final int[] urls = Arrays.copyOfRange(ConstantUtils.images, 0, i);
        if (urls != null && urls.length > 0) {
            viewHolder.healthDynamicGridView.setVisibility(View.VISIBLE);
            viewHolder.healthDynamicGridView.setAdapter(new DynamicGridAdapter(urls, context));
            viewHolder.healthDynamicGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    imageBrower(position, urls);
                }
            });
        } else {
            viewHolder.healthDynamicGridView.setVisibility(View.GONE);
        }
        // 是否点赞：从服务器获取
        isZan = false;
        // 设置点击事件
        MyClickListenr listener = new MyClickListenr(viewHolder);
        viewHolder.tvZan.setOnClickListener(listener);
        viewHolder.tvComment.setOnClickListener(listener);
        viewHolder.tvShare.setOnClickListener(listener);
        // 设置点赞和分享
        likeList = new ArrayList<>();
        likeList.add("土豆");
        likeList.add("白菜");
        likeList.add("海带");
        likeList.add("土豆");
        likeList.add("白菜");
        likeList.add("海带");
        likeList.add("土豆");
        likeList.add("白菜");
        likeList.add("海带");
        HandleSpannable.setLikeUsers(viewHolder.healthyDynamicZan, likeList, 1, 3);
        HandleSpannable.setShareUsers(viewHolder.healthyDynamicShare, likeList, 0);
        // 设置三条评论
        ArrayList<DynamicComment> commentList;
        commentList = getCommentList();
        HandleSpannable.setFirstComment(viewHolder.commentFirst, commentList.get(0), onClickComment);
        HandleSpannable.setFirstComment(viewHolder.commentSecond, commentList.get(1),onClickComment);
        HandleSpannable.setFirstComment(viewHolder.commentThird, commentList.get(2), onClickComment);
        return convertView;
    }

    /**
     * 处理评论条目中评论的逻辑
     */
    OnClickComment onClickComment = new OnClickComment() {
        @Override
        public void onComment() {
            handleComment();
        }
    };

    private ArrayList<DynamicComment> getCommentList() {
        ArrayList<DynamicComment> list = new ArrayList<>();
        DynamicComment comment = new DynamicComment();
        comment.name = "土豆";
        comment.content = "说的好";
        list.add(comment);
        DynamicComment comment1 = new DynamicComment();
        comment1.name = "白菜";
        comment1.content = "说的太好了";
        list.add(comment1);
        DynamicComment comment2 = new DynamicComment();
        comment2.name = "海带";
        comment2.content = "楼上说的很对哈哈哈啊哈啊哈啊哈啊哈啊啊哈哈哈啊哈啊哈啊哈啊哈哈哈哈哈哈哈哈哈";
        comment2.toName = "白菜";
        list.add(comment2);
        return list;
    }

    class ViewHolder {
        @Bind(R.id.health_dynamic_portrait)
        ImageView healthDynamicPortrait;
        @Bind(R.id.user_name)
        TextView userName;
        @Bind(R.id.tv_content)
        TextView tvContent;
        @Bind(R.id.health_dynamic_gridView)
        DynamicGridView healthDynamicGridView;
        @Bind(R.id.tv_share)
        TextView tvShare;
        @Bind(R.id.tv_comment)
        TextView tvComment;
        @Bind(R.id.tv_zan)
        TextView tvZan;
        @Bind(R.id.tv_share1)
        TextView tvShare1;
        @Bind(R.id.tv_comment1)
        TextView tvComment1;
        @Bind(R.id.tv_view_zan)
        TextView tvViewZan;
        @Bind(R.id.healthy_dynamic_zan)
        TextView healthyDynamicZan;
        @Bind(R.id.healthy_dynamic_share)
        TextView healthyDynamicShare;
        @Bind(R.id.comment_first)
        TextView commentFirst;
        @Bind(R.id.comment_second)
        TextView commentSecond;
        @Bind(R.id.comment_third)
        TextView commentThird;
        @Bind(R.id.tv_more_comment)
        TextView tvMoreComment;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    class MyClickListenr implements View.OnClickListener {
        ViewHolder viewHolder;

        public MyClickListenr(ViewHolder viewHolder) {
            this.viewHolder = viewHolder;
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tv_zan://点赞
                    handleZan();
                    break;
                case R.id.tv_comment://评论
                    handleComment();
                    break;
                case R.id.tv_share://分享
                    handleShare();
                    break;
            }

        }

        /**
         * 处理分享的逻辑
         */
        private void handleShare() {

        }

        /**
         * 处理点赞的逻辑
         */
        private void handleZan() {
            if (!isZan) {
                // 1.点赞人数加1,显示为点赞
                String text = (String) viewHolder.tvZan.getText();
                int i = Integer.parseInt(text) + 1;
                viewHolder.tvZan.setText(i + "");
                viewHolder.tvZan.setSelected(true);
                // 2.将本用户加入点赞集合，并显示
                likeList.add(0, "山药");
                HandleSpannable.setLikeUsers(viewHolder.healthyDynamicZan, likeList, 1, 4);
                // 3.点赞动画
                showAnima(viewHolder);
                // 4.设置isZan，并请求服务器设置为点赞
                isZan = true;
            } else {
                // 1.点赞人数减一
                String text = (String) viewHolder.tvZan.getText();
                int i = Integer.parseInt(text) - 1;
                viewHolder.tvZan.setText(i + "");
                viewHolder.tvZan.setSelected(false);
                // 2.将本用户移除点赞集合，并隐藏
                likeList.remove(0);
                HandleSpannable.setLikeUsers(viewHolder.healthyDynamicZan, likeList, 1, 3);
                // 3.取消赞动画
                ToastUtil.show(ShanYaoApplication.getContext(), "已取消赞");
                // 4.设置isZan，并请求服务器设置为未点赞
                isZan = false;
            }
        }
    }
    /**
     * 处理评论的逻辑
     */
    private void handleComment() {
        ToastUtil.show(context,"评论");
    }
    private void showAnima(final ViewHolder viewHolder) {
        // 向上平移
        TranslateAnimation ta = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, -2.0f);
        // 透明消失
        AlphaAnimation aa = new AlphaAnimation(1.0f, 0f);
        // 一起飞
        AnimationSet set = new AnimationSet(true);
        set.setDuration(1500);
        set.setFillAfter(true);
        set.addAnimation(ta);
        set.addAnimation(aa);
        if (!isAnimtion) {
            viewHolder.tvViewZan.startAnimation(set);
        } else {
            return;
        }
        set.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                viewHolder.tvViewZan.setVisibility(View.VISIBLE);
                isAnimtion = true;
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                isAnimtion = false;
                viewHolder.tvViewZan.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    /**
     * 获取当前用户的用户名
     */
    private String getUserName() {
        return "山药";
    }

    private void imageBrower(int position, int[] urls) {
        Intent intent = new Intent(context, ActivityImagePager.class);
        // 图片url,为了演示这里使用常量，一般从数据库中或网络中获取
        intent.putExtra(ActivityImagePager.EXTRA_IMAGE_URLS, urls);
        intent.putExtra(ActivityImagePager.EXTRA_IMAGE_INDEX, position);
        context.startActivity(intent);
    }
}
