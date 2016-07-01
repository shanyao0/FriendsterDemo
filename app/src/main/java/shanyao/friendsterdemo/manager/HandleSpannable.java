package shanyao.friendsterdemo.manager;

import android.content.Context;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import shanyao.friendsterdemo.ShanYaoApplication;
import shanyao.friendsterdemo.bean.DynamicComment;
import shanyao.friendsterdemo.interfaces.OnClickComment;
import shanyao.friendsterdemo.utils.ToastUtil;

/**
 * Created by zs on 2015/11/17.
 */
public class HandleSpannable {
    /**
     * 设置点赞的条目
     *
     * @param likeUser:需要设置的textView
     * @param likeList：点赞的人名的集合
     * @param isLiked：此人是否点赞
     */
    public static void setLikeUsers(TextView likeUser, List<String> likeList, int isLiked,int total) {
        // 构造多个超链接的html, 通过选中的位置来获取用户名
        if (likeList.size() > 0 && likeList != null
                && !likeList.isEmpty()) {
            likeUser.setVisibility(View.VISIBLE);
            likeUser.setMovementMethod(LinkMovementMethod.getInstance());
            likeUser.setFocusable(false);
            likeUser.setLongClickable(false);
            likeUser.setText(addClickablePart(ShanYaoApplication.getContext(), likeList, isLiked, 1,total),
                    TextView.BufferType.SPANNABLE);
        } else {
            likeUser.setVisibility(View.GONE);
            likeUser.setText("");
        }
    }

    /**
     * 设置分享
     *
     * @param shareUser:         分享的TextView
     * @param shareList：分享的人名的集合
     * @param isShared：此人是否分享
     */
    public static void setShareUsers(TextView shareUser, List<String> shareList, int isShared) {
        // 构造多个超链接的html, 通过选中的位置来获取用户名
        if (shareList.size() > 0 && shareList != null
                && !shareList.isEmpty()) {
            shareUser.setVisibility(View.VISIBLE);
            shareUser.setMovementMethod(LinkMovementMethod.getInstance());
            shareUser.setFocusable(false);
            shareUser.setLongClickable(false);
            shareUser.setText(addClickablePart(ShanYaoApplication.getContext(), shareList, isShared, 0,3),
                    TextView.BufferType.SPANNABLE);
        } else {
            shareUser.setVisibility(View.GONE);
            shareUser.setText("");
        }
    }

    /**
     * 处理一级评论
     *
     * @param comment
     */
    public static void setFirstComment(TextView textView, final DynamicComment comment, final OnClickComment onClickComment) {
        SpannableString spanStr = new SpannableString("");
        SpannableStringBuilder ssb = new SpannableStringBuilder(spanStr);
        /**
         *  添加评论人并设置点击事件
         */
        int start = 0;
        ssb.append(comment.name);
        ssb.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                ToastUtil.show(ShanYaoApplication.getContext(), comment.name);
                // 进入个人主页
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                // 设置颜色
                ds.setColor(Color.parseColor("#44b43a"));
                // 去掉下划线
                ds.setUnderlineText(false);
            }
        }, start, comment.name.length(), 0);
        /**
         *  一级评论直接添加评论内容
         */
        if (TextUtils.isEmpty(comment.toName)) {
            int start2 = comment.name.length() + 1;
            ssb.append(" " + comment.content);// 添加评论内容
            ssb.setSpan(new ClickableSpan() {
                @Override
                public void onClick(View widget) {
                    ToastUtil.show(ShanYaoApplication.getContext(), comment.content);
                    // 点击内容进行回复
                    onClickComment.onComment();
//                    handleComment(context);
                }

                @Override
                public void updateDrawState(TextPaint ds) {
                    super.updateDrawState(ds);
                    // 设置颜色
                    ds.setColor(Color.parseColor("#999999"));
                    // 去掉下划线
                    ds.setUnderlineText(false);
                }
            }, start2, start2 + comment.content.length(), 0);
            // 给TextView设置内容
            textView.setVisibility(View.VISIBLE);
            textView.setMovementMethod(LinkMovementMethod.getInstance());
            textView.setFocusable(false);
            textView.setLongClickable(false);
            textView.setText(ssb,
                    TextView.BufferType.SPANNABLE);
        }
        /**
         *  二级评轮添加"回复"，回复人，以及回复的内容
         */
        else {
            int start3 = comment.name.length()+3;
            ssb.append(" 回复 " + comment.toName + " " + comment.content);// 添加评论内容
            /*
             * toName的点击事件
             */
            ssb.setSpan(new ClickableSpan() {
                @Override
                public void onClick(View widget) {
                    ToastUtil.show(ShanYaoApplication.getContext(), comment.toName);
                    // 进入个人主页
                }

                @Override
                public void updateDrawState(TextPaint ds) {
                    super.updateDrawState(ds);
                    // 设置颜色
                    ds.setColor(Color.parseColor("#44b43a"));
                    // 去掉下划线
                    ds.setUnderlineText(false);
                }
            }, start3, start3 + comment.toName.length()+2, 0);
            /*
             * 评论内容的点击事件
             */
            int start4 = start3 + comment.toName.length() + 1;
            ssb.setSpan(new ClickableSpan() {
                @Override
                public void onClick(View widget) {
                    ToastUtil.show(ShanYaoApplication.getContext(), comment.content);
                    // 点击内容进行回复
//                    handleComment(context);
                    onClickComment.onComment();
                }

                @Override
                public void updateDrawState(TextPaint ds) {
                    super.updateDrawState(ds);
                    // 设置颜色
                    ds.setColor(Color.parseColor("#999999"));
                    // 去掉下划线
                    ds.setUnderlineText(false);
                }
            }, start4, start4 + comment.content.length(), 0);
            // 给TextView设置内容
            textView.setVisibility(View.VISIBLE);
            textView.setMovementMethod(LinkMovementMethod.getInstance());
            textView.setFocusable(false);
            textView.setLongClickable(false);
            textView.setText(ssb,
                    TextView.BufferType.SPANNABLE);
        }
    }


    private static SpannableStringBuilder addClickablePart(final Context context,
                                                           final List<String> likeList, int isLiked, int type,int total) {

        StringBuilder sbBuilder = new StringBuilder();
        int showCunt = likeList.size();
        // 控制显示几个人
        if (showCunt > total) {
            showCunt = total;
        }

        for (int i = 0; i < showCunt; i++) {
            sbBuilder.append(likeList.get(i) + "，");
        }
        // 去掉最后一个“，”号
        String likeUsersStr = sbBuilder
                .substring(0, sbBuilder.lastIndexOf("，")).toString();

        SpannableString spanStr = new SpannableString("");

        SpannableStringBuilder ssb = new SpannableStringBuilder(spanStr);
        ssb.append(likeUsersStr);

        String[] likeUsers = likeUsersStr.split("，");

        if (likeUsers.length > 0) {
            // 最后一个
            for (int i = 0; i < likeUsers.length; i++) {
                final String name = likeUsers[i];
                final int start = likeUsersStr.indexOf(name) + spanStr.length();
                final int index = i;
                ssb.setSpan(new ClickableSpan() {

                    @Override
                    public void onClick(View widget) {
                        String user = likeList.get(index);
                        // 进入用户个人中心
                        ToastUtil.show(context, user);
                    }

                    @Override
                    public void updateDrawState(TextPaint ds) {
                        super.updateDrawState(ds);
                        ds.setColor(Color.parseColor("#44b43a")); // 设置文本颜色
                        // 去掉下划线
                        ds.setUnderlineText(false);
                    }

                }, start, start + name.length(), 0);
            }
        }
        if (likeUsers.length < likeList.size()) {
            ssb.append("等");
            int start = ssb.length();
            String more = likeList.size() + "人";
            ssb.append(more);
            if (type == 1) {
                return ssb.append("点赞");
            } else {
                return ssb.append("分享");
            }
        } else {
            if (type == 1) {
                return ssb.append("点赞");
            } else {
                return ssb.append("分享");
            }
        }
    }
}
