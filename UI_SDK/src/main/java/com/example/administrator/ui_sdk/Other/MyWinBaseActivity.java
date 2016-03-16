package com.example.administrator.ui_sdk.Other;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.ui_sdk.BaseInterface;
import com.example.administrator.ui_sdk.DensityUtil;
import com.example.administrator.ui_sdk.R;

/**
 * Created by Administrator on 2016/2/25.
 */
public class MyWinBaseActivity extends FragmentActivity implements View.OnClickListener {

    private LinearLayout base_linear, base_linear1, base_linear2, base_linear3, base_linear4, base_linear6, base_linear7, base_linear8, base_linear9, base_linear10, base_linear11, base_linear12, base_linear13, base_linear14, base_linear15, base_linear16 = null;
    private Context context = null;
    private Activity activity = null;
    private LinearLayout base_linear5 = null;

    public static int width, height = 0;
    //记录标题栏的高度
    private int w, h = 0;

    //标题的布局
    private LinearLayout titlebar = null;
    //内容的布局
    private LinearLayout contentView = null;


    private RelativeLayout base_top_relative = null;
    private ImageView base_top_image = null;
    private TextView base_top_text = null;
    private TextView base_top_title = null;
    private TextView base_top_text1 = null;
    private ImageView base_top_image1 = null;

    private BaseInterface.topinterface topinterface = null;

    //进行初始化的操作方法
    public void inti() {
    }

    //自定义标题

    /**
     * 判断条件如果resouse为0则默认去掉系统默认的标题
     * 如果resouse不为0则分为两种情况
     * 1.如果resouse为系统默认的布局文件base_top之后获取组件的ID之后进行事件的点击事件
     * 2.如果resouse不是系统默认的布局文件则是自定义标题只能将自定义的布局文件添加到系统默认的布局里面进行显示，之后的将重写点击事件进行实现点击
     * <p/>
     * 还有就是获取沉寂式状态栏分两种情况
     * 1.如果去掉标题栏，则沉寂式状态栏则自动获取contentView的背景颜色
     * 2.如果用有标题栏，则沉寂式状态栏则自动获取titlebar的背景颜色
     *
     * @param resid
     */
    public void setTileBar(int resid) {
        if (resid != 0) {
            //将标题显示出来
            titlebar.setVisibility(View.VISIBLE);
            //移除titlebar的所有组件
            titlebar.removeAllViews();
            View view = LayoutInflater.from(context).inflate(resid, null);
            //设置默认高度
            setTopHeight(MyWinBaseActivity.height / 12);
            //设置默认的颜色
            setTopColor(R.color.Blue);
            //设置沉寂式状态栏
            setTopWindowColor(titlebar, activity);

            if (resid == R.layout.base_top) {
                base_top_relative = (RelativeLayout) view.findViewById(R.id.base_top_relative);
                base_top_image = (ImageView) view.findViewById(R.id.base_top_image);
                base_top_image1 = (ImageView) view.findViewById(R.id.base_top_image1);
                base_top_text = (TextView) view.findViewById(R.id.base_top_text);
                base_top_title = (TextView) view.findViewById(R.id.base_top_title);
                base_top_text1 = (TextView) view.findViewById(R.id.base_top_text1);


                base_top_relative.setOnClickListener(this);
                base_top_text1.setOnClickListener(this);
                base_top_image1.setOnClickListener(this);
            }
            //将布局文件添加进标题文件里
            titlebar.addView(view);
        } else {
            //将标题隐藏出来
            titlebar.setVisibility(View.GONE);
            //如果没有标题则获取主布局的颜色作为颜色设置沉寂式状态栏
            //设置沉寂式状态栏
            setTopWindowColor(contentView, activity);
        }
    }


    //自定义内容

    /**
     * 判断条件如果resouse为0则默认去掉系统默认的标题
     * 如果resid不为0则分为两种情况
     * 1.如果resid为系统默认的布局文件win之后获取组件的ID之后进行事件的点击事件
     * 2.如果resid不是系统默认的布局文件则是自定义标题只能将自定义的布局文件添加到系统默认的布局里面进行显示，之后的将重写点击事件进行实现点击
     * <p/>
     * 还有就是获取沉寂式状态栏分两种情况
     * 1.如果去掉标题栏，则沉寂式状态栏则自动获取自定义加上去的背景颜色
     * 2.如果用有标题栏，则沉寂式状态栏则自动获取win的背景颜色
     *
     * @param resid
     */
    private void ContentView(int resid) {

        if (resid != 0) {
            //移除contentView的所有组件
            contentView.removeAllViews();
            View view = LayoutInflater.from(context).inflate(resid, null);
            //设置默认高度
            setTopHeight(MyWinBaseActivity.height / 12);
            //设置默认的颜色
            setContentColor(R.color.White);

            setContentState(view);

            //将布局文件添加进标题文件里
            contentView.addView(view);
            contentView.setPadding(5, 5, 5, 5);
        } else {
            Log.e("Activity", "请设置主布局的布局文件");
        }
    }

    /**
     * 自定义点击事件的接口
     *
     * @param topinterface
     */
    public void TopClick(BaseInterface.topinterface topinterface) {
        this.topinterface = topinterface;
    }

    /**
     * 将自定义的布局文件添加到主布局当中
     *
     * @param resid
     */
    public void setContent(int resid) {
        contentView.setVisibility(View.VISIBLE);
        contentView.addView(LayoutInflater.from(context).inflate(resid, null));
    }

    /**
     * 设置win布局是否显示
     *
     * @param visiable
     */
    public void setContentViewVisiable(boolean visiable) {
        setVisiable(contentView, visiable);
    }

    /**
     * 设置沉寂式状态栏
     *
     * @param view
     * @param activity
     */
    public void setTopWindowColor(View view, Activity activity) {
        //判断android版本大于4.4才能执行沉寂式状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            try {
                DensityUtil.ViewColor(view, activity);
            } catch (Exception e) {
                Log.e("Activity" + activity, "设置的沉寂式状态栏没有背景颜色，请设置背景颜色");
            }
        }
    }

    /**
     * 设置默认标题的高度
     *
     * @param height
     */
    public void setTopHeight(int height) {
        h = height;
        DensityUtil.setHeight(titlebar, height);
    }

    /**
     * 设置默认标题的颜色
     *
     * @param resid
     */
    public void setTopColor(int resid) {
        titlebar.setBackgroundResource(resid);
    }

    /**
     * 设置主内容的颜色
     *
     * @param resid
     */
    public void setContentColor(int resid) {
        contentView.setBackgroundResource(resid);
    }


    /**
     * 设置右边的文字
     *
     * @param msg
     */
    public void setRightTile(String msg) {
        base_top_text1.setVisibility(View.VISIBLE);
        base_top_text1.setText(msg);
    }

    /**
     * 设置标题
     *
     * @param msg
     */
    public void setTile(String msg) {
        base_top_title.setText(msg);
    }

    /**
     * 设置右边标题的文字是否隐藏
     *
     * @param visiable
     */
    public void setRightTileVisiable(boolean visiable) {
        setVisiable(base_top_text1, visiable);
    }

    /**
     * 设置标题是否隐藏
     *
     * @param visiable
     */
    public void setTileVisiable(boolean visiable) {
        setVisiable(base_top_title, visiable);
    }

    /**
     * 设置右边图片
     *
     * @param resid
     */
    public void setRightImage(int resid) {
        base_top_image1.setVisibility(View.VISIBLE);
        base_top_image1.setImageResource(resid);
    }

    /**
     * 设置右边图片是否显示
     *
     * @param visiable
     */
    public void setRightImageVisiable(boolean visiable) {
        setVisiable(base_top_image1, visiable);
    }

    /**
     * 设置左边文字
     *
     * @param msg
     */
    public void setLeftTile(String msg) {
        base_top_text.setVisibility(View.VISIBLE);
        base_top_text.setText(msg);
    }

    /**
     * 设置左边的文字是否隐藏
     *
     * @param visiable
     */
    public void setLeftTileVisiable(boolean visiable) {
        setVisiable(base_top_text, visiable);
    }

    /**
     * 设置左边的图片
     *
     * @param resid
     */
    public void setLeftImage(int resid) {
        base_top_image.setVisibility(View.VISIBLE);
        base_top_image.setImageResource(resid);
    }

    /**
     * 设置左边的图片是否显示
     *
     * @param visiable
     */
    public void setLeftImageVisiable(boolean visiable) {
        setVisiable(base_top_image, visiable);
    }


    private void setVisiable(View v, boolean visiable) {
        if (visiable)
            v.setVisibility(View.GONE);
        else
            v.setVisibility(View.VISIBLE);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.winbasemain);

        context = this;
        activity = (Activity) context;

        getPhone();


        titlebar = (LinearLayout) findViewById(R.id.titlebar);
        contentView = (LinearLayout) findViewById(R.id.contentView);


        //设置系统默认标题
        setTileBar(R.layout.base_top);
        //设置win布局
        ContentView(R.layout.win);
        //调用初始化界面
        inti();

    }

    /**
     * 获取手机屏幕大小
     */
    private void getPhone() {
        WindowManager wm = (WindowManager) this
                .getSystemService(Context.WINDOW_SERVICE);
        width = wm.getDefaultDisplay().getWidth();
        height = wm.getDefaultDisplay().getHeight();
    }

    @Override
    public void onClick(View v) {
        topinterface.onClick(v);
    }


    /**
     * 设置win布局
     *
     * @param view
     */
    private void setContentState(View view) {
        base_linear = (LinearLayout) view.findViewById(R.id.base_linear);
        base_linear1 = (LinearLayout) view.findViewById(R.id.base_linear1);
        base_linear2 = (LinearLayout) view.findViewById(R.id.base_linear2);
        base_linear3 = (LinearLayout) view.findViewById(R.id.base_linear3);
        base_linear4 = (LinearLayout) view.findViewById(R.id.base_linear4);
        base_linear5 = (LinearLayout) view.findViewById(R.id.base_linear5);
        base_linear6 = (LinearLayout) view.findViewById(R.id.base_linear6);
        base_linear7 = (LinearLayout) view.findViewById(R.id.base_linear7);
        base_linear8 = (LinearLayout) view.findViewById(R.id.base_linear8);
        base_linear9 = (LinearLayout) view.findViewById(R.id.base_linear9);
        base_linear10 = (LinearLayout) view.findViewById(R.id.base_linear10);
        base_linear11 = (LinearLayout) view.findViewById(R.id.base_linear11);
        base_linear12 = (LinearLayout) view.findViewById(R.id.base_linear12);
        base_linear13 = (LinearLayout) view.findViewById(R.id.base_linear13);
        base_linear14 = (LinearLayout) view.findViewById(R.id.base_linear14);
        base_linear15 = (LinearLayout) view.findViewById(R.id.base_linear15);
        base_linear16 = (LinearLayout) view.findViewById(R.id.base_linear16);

        int height = MyWinBaseActivity.height - DensityUtil.dip2px(context, 5) * 3, width = MyWinBaseActivity.width;
        //设置win8界面
        DensityUtil.setLinearSize(base_linear, width, height / 3);
        DensityUtil.setLinearSize(base_linear3, width / 3 * 2, height / 6 - DensityUtil.dip2px(context, 5), 0, DensityUtil.dip2px(context, 5), 0, 0);
        DensityUtil.setLinearSize(base_linear4, width / 3 * 2, height / 6);
        DensityUtil.setLinearSize(base_linear6, width / 3, height / 3);

        DensityUtil.setLinearSize(base_linear9, width / 3 - DensityUtil.dip2px(context, 5), height / 3);
        DensityUtil.setLinearSize(base_linear12, width / 3 - DensityUtil.dip2px(context, 5), height / 6 - DensityUtil.dip2px(context, 5), 0, DensityUtil.dip2px(context, 5), 0, 0);
        DensityUtil.setLinearSize(base_linear13, width / 3 - DensityUtil.dip2px(context, 5), height / 6);
        DensityUtil.setLinearSize(base_linear15, width / 3, height / 6 - DensityUtil.dip2px(context, 5), 0, DensityUtil.dip2px(context, 5), 0, 0);
        DensityUtil.setLinearSize(base_linear16, width / 3, height / 6);
    }

    //添加布局文件
    public void addLinear(View view) {
        base_linear.addView(view);
    }

}
