package com.isabella.dechat.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.isabella.dechat.R;

import java.io.ByteArrayOutputStream;

/**
 * description 图片工具类
 * Created by 张芸艳 on 2017/4/28.
 */

public class GlideUtils {
    private volatile  static GlideUtils glideUtils;



    public static GlideUtils getInstance() {
        if (glideUtils == null) {
            synchronized (GlideUtils.class) {
                if (glideUtils == null) {
                    glideUtils = new GlideUtils();
                }
            }
        }
        return glideUtils;
    }

    private GlideUtils() {}
    public void haveCache(String url, ImageView view, Context context){
        Glide.with(context).load(url)
                .placeholder(R.drawable.ic_album_default)
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .error(R.drawable.ic_album_default)
                .into(view);

    }
    public void photo(String url, ImageView view, Context context){
        Glide.with(context).load(url)
                .placeholder(R.mipmap.ic_friend_background_default)
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .error(R.mipmap.ic_friend_background_default)
                .into(view);

    }

    public void haveCacheLarger(String url, ImageView view, Context context){
        Glide.with(context).load(url)
                .placeholder(R.drawable.ic_album_default)
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .error(R.drawable.ic_album_default)
                .centerCrop()
                .into(view);

    }
    //小圆角
    public void havaRoundLetter(String url, ImageView view, Context context){
        Glide.with(context).load(url)
                .placeholder(R.drawable.woman_user_round_icon_default)
                .error(R.drawable.woman_user_round_icon_default)
                .transform(new CenterCrop(context),new GlideRound(context,10))
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .into(view);
    }
    //把网络请求图片设置成圆角
    public void havaRound(String url, ImageView view, Context context){
        Glide.with(context).load(url)
                .placeholder(R.drawable.woman_user_round_icon_default)
                .error(R.drawable.woman_user_round_icon_default)
                .transform(new CenterCrop(context),new GlideRound(context,50))
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .into(view);
    }
    //把本地bitmap设置成圆角
    public void bitmapRound(Bitmap bitmap,Context context,ImageView imageView){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] bytes=baos.toByteArray();

        Glide.with(context)
                .load(bytes)
                .transform(new CenterCrop(context),new GlideRound(context,80))
                .into(imageView);
    }
    public void bitmapSet(Bitmap bitmap,Context context,ImageView imageView){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] bytes=baos.toByteArray();

        Glide.with(context)
                .load(bytes)
                .centerCrop()
                .into(imageView);
    }

}
