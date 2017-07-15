package com.isabella.dechat.util;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LayoutAnimationController;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.github.chrisbanes.photoview.OnPhotoTapListener;
import com.github.chrisbanes.photoview.PhotoView;
import com.isabella.dechat.R;
import com.isabella.dechat.adapter.MyViewPager;
import com.isabella.dechat.bean.PhotolistBean;
import com.isabella.dechat.widget.MyToast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 主界面点击发布，弹出半透明对话框
 * Created by xiaoke on 2016/4/28.
 */
public class PicShowDialog extends Dialog {
    private Context context;
    private View view;
    private List<PhotolistBean> imageInfos;
    private MyViewPager vp;
    private List<View> views = new ArrayList<View>();
    private LayoutAnimationController lac;
    private LinearLayout ll_point;
    private ViewPagerAdapter pageAdapter;
    private int position;
    private AlertDialog.Builder builder;
    private LinearLayout.LayoutParams paramsL = new LinearLayout.LayoutParams(10, 10);
    private String imagePath;

    public PicShowDialog(Context context, int themeResId) {
        super(context, themeResId);
        this.context = context;
    }


    public PicShowDialog(Context context, List<PhotolistBean> imageInfos, int position) {
        this(context, R.style.Pic_Dialog);
        this.imageInfos = imageInfos;
        this.position = position;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_dialog_pic);
        getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//        getWindow().setLayout(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        vp = (MyViewPager) findViewById(R.id.vp);
        ll_point = (LinearLayout) findViewById(R.id.ll_point);
//        init();
        initMyPageAdapter();
        setDialog();
//        vp.setAdapter(new ViewPagerAdapter());
        vp.setCurrentItem(position);
        vp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int position) {
                if (views.size() != 0 && views.get(position) != null) {

                    for (int i = 0; i < views.size(); i++) {
                        if (i == position) {
                            views.get(i).setBackgroundResource(R.drawable.point_focus2);
                        } else {
                            views.get(i).setBackgroundResource(R.drawable.point_normal2);
                        }
                    }

                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

    }
    /***
     * 初始化viewpager适配器
     */

    private void initMyPageAdapter() {
        initPoint();
        if (pageAdapter == null) {
            pageAdapter = new ViewPagerAdapter();
            if (vp != null) {
                vp.setAdapter(pageAdapter);
            }

        } else {
            pageAdapter.notifyDataSetChanged();
        }
    }

    private void initPoint() {
        views.clear();
        ll_point.removeAllViews();
        if (imageInfos.size()==1){
            ll_point.setVisibility(View.GONE);
        }else {
            for (int i = 0; i < imageInfos.size(); i++) {
                View view = new View(context);
                paramsL.setMargins(dip2px(context, 5), dip2px(context, 2), 0, dip2px(context, 5));
                view.setLayoutParams(paramsL);
                if (i == position) {
                    view.setBackgroundResource(R.drawable.point_focus2);
                } else {
                    view.setBackgroundResource(R.drawable.point_normal2);
                }

                views.add(view);
                ll_point.addView(view);
            }
        }

    }

    private class ViewPagerAdapter extends PagerAdapter {




        @Override
        public int getCount() {
            return imageInfos.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view =View.inflate(context, R.layout.item_pic_show, null);
            PhotoView photoView = (PhotoView) view.findViewById(R.id.pic_pv);
            imagePath = imageInfos.get(position).getImagePath();

            GlideUtils.getInstance().haveCache(imagePath,photoView,context);
            photoView.setOnPhotoTapListener(new OnPhotoTapListener() {
                @Override
                public void onPhotoTap(ImageView view, float x, float y) {
                    dismiss();
                }
            });
            photoView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    builder.show();
                    return false;
                }
            });
                    ((ViewPager) container).addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager) container).removeView((View) object);
        }
    }
        private   int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
    public void saveImageToGallery(Context context, Bitmap bmp) {

        // 首先保存图片
        File file = Environment.getExternalStorageDirectory();
        File appDir = new File(file, "DeChat");
        if (!appDir.exists()) {
            appDir.mkdirs();
        }
        String fileName = System.currentTimeMillis() + ".jpg";
        File currentFile = new File(appDir, fileName);

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(currentFile);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

//        // 其次把文件插入到系统图库
//        try {
//            MediaStore.Images.Media.insertImage(context.getContentResolver(),
//                    currentFile.getAbsolutePath(), fileName, null);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }

        // 最后通知图库更新
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                Uri.fromFile(new File(currentFile.getPath()))));
    }
    private void setDialog() {
        builder = new AlertDialog.Builder(context);
        //设置对话框的图片
        builder.setIcon(R.drawable.ic_dialog);
        builder.setTitle("保存图片");
        builder.setMessage("(๑´ㅂ`๑)亲,是否要保存图片到本地相册?");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Glide.with(context)
                        .load(imagePath)
                        .asBitmap()
                        .into(new SimpleTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                                //image.setImageBitmap(resource);
                                MyToast.makeText(context, "保存成功", Toast.LENGTH_SHORT);
                                saveImageToGallery(context, resource);
                            }
                        });


            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
    }
}

