package com.example.administrator.ui_sdk.Other;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.ui_sdk.Applications;
import com.example.administrator.ui_sdk.DensityUtil;
import com.example.administrator.ui_sdk.R;
import com.example.administrator.ui_sdk.View.SystemManager;


/**
 * Created by Administrator on 2015/12/18.
 */
public class BaseActivity extends FragmentActivity implements
        OnClickListener {

    public LinearLayout titlebar, contentView, navbar, nav_main = null;
    public RelativeLayout base_top_relative = null;
    public TextView base_top_text1, base_top_title, base_top_text = null;
    public ImageView base_top_image, base_top_image1 = null;
    public View title, nav = null;
    private TextView nav_1_text, nav_2_text, nav_3_text, nav_4_text = null;
    private ImageView nav_1_image, nav_2_image, nav_3_image, nav_4_image = null;
    // 屏幕的宽高
    public static int width, height = 0;
    //判断是否拥有标题
    private boolean flag = true;
    private Activity activity = null;

    private TextView origin1, origin2, origin3, origin4 = null;
    private View nav_origin1, nav_origin2, nav_origin3, nav_origin4 = null;
    private LinearLayout nav_origin1_linear4, nav_origin1_linear3, nav_origin1_linear2, nav_origin1_linear1 = null;
    private ImageView base_image_add = null;


    // 添加布局文件
    public void setcontentView() {
    }

    // 设置自定义标题

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
     * @param resouse
     */
    public void Title(int resouse) {
        // 重写如果传参数为0则去掉标题
        if (resouse == 0) {
            titlebar.setVisibility(View.GONE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                flag = false;
                ViewColor(contentView);
            }
        } else {
            titlebar.removeAllViews();
            title = LayoutInflater.from(this).inflate(resouse, null);
            titlebar.addView(title);
            DensityUtil.setRelHeight(titlebar, BaseActivity.height / 12);

            if (resouse == R.layout.base_top) {
                base_top_relative = (RelativeLayout) title
                        .findViewById(R.id.base_top_relative);
                base_top_text1 = (TextView) title
                        .findViewById(R.id.base_top_text1);
                base_top_title = (TextView) title
                        .findViewById(R.id.base_top_title);
                base_top_text = (TextView) title
                        .findViewById(R.id.base_top_text);
                base_top_image = (ImageView) title
                        .findViewById(R.id.base_top_image);
                base_top_image1 = (ImageView) title.findViewById(R.id.base_top_image1);
                base_top_relative.setOnClickListener(this);
                base_top_text1.setOnClickListener(this);
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                ViewColor(titlebar);
            }
        }
    }

    /**
     * 自定义底部标题
     * 判断条件如果resouse为0则默认去掉系统默认的底部标题
     * 如果resouse不为0则分为两种情况
     * 1.如果resouse为系统默认的布局文件nav之后获取组件的ID之后进行事件的点击事件
     * 2.如果resouse不是系统默认的布局文件则是自定义标题只能将自定义的布局文件添加到系统默认的布局里面进行显示，之后的将重写点击事件进行实现点击
     *
     * @param resouse
     */
    public void Nav(int resouse) {
        if (resouse == 0) {
            navbar.setVisibility(View.GONE);
        } else {
            navbar.removeAllViews();
            nav = LayoutInflater.from(this).inflate(resouse, null);
            nav_1_text = (TextView) nav.findViewById(R.id.nav_1_text);
            nav_2_text = (TextView) nav.findViewById(R.id.nav_2_text);
            nav_3_text = (TextView) nav.findViewById(R.id.nav_3_text);
            nav_4_text = (TextView) nav.findViewById(R.id.nav_4_text);
            nav_1_image = (ImageView) nav.findViewById(R.id.nav_1_image);
            nav_2_image = (ImageView) nav.findViewById(R.id.nav_2_image);
            nav_3_image = (ImageView) nav.findViewById(R.id.nav_3_image);
            nav_4_image = (ImageView) nav.findViewById(R.id.nav_4_image);

            nav_origin1 = nav.findViewById(R.id.nav_origin1);
            nav_origin2 = nav.findViewById(R.id.nav_origin2);
            nav_origin3 = nav.findViewById(R.id.nav_origin3);
            nav_origin4 = nav.findViewById(R.id.nav_origin4);

            origin1 = (TextView) nav_origin1.findViewById(R.id.origin);
            origin2 = (TextView) nav_origin2.findViewById(R.id.origin);
            origin3 = (TextView) nav_origin3.findViewById(R.id.origin);
            origin4 = (TextView) nav_origin4.findViewById(R.id.origin);

            nav_origin1_linear4 = (LinearLayout) nav.findViewById(R.id.nav_origin1_linear4);
            nav_origin1_linear3 = (LinearLayout) nav.findViewById(R.id.nav_origin1_linear3);
            nav_origin1_linear2 = (LinearLayout) nav.findViewById(R.id.nav_origin1_linear2);
            nav_origin1_linear1 = (LinearLayout) nav.findViewById(R.id.nav_origin1_linear1);

            navbar.addView(nav);
            if (resouse == R.layout.nav) {
                nav_main = (LinearLayout) findViewById(R.id.nav_main);
                setNavSize(0);
                setNavSize_Main(BaseActivity.height / 10);
            }
        }
    }

    /**
     * 设置状态栏的颜色
     * 此方法是获取组件的背景颜色
     * 该方法为私有，现在仅提供内部使用
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void ViewColor(View view) {
        Drawable drawable = view.getBackground();
        ColorDrawable colorDrawable = (ColorDrawable) drawable;
        new SystemManager().setTitleBarColor(colorDrawable.getColor(), this);
    }

    /**
     * 初始化界面操作
     * 比如设置标题重写布局，进行布局操作，自己添加自定义布局等
     */
    public void init() {
    }

    /**
     * 按钮点击事件
     * 该方法可以在继承BaseActivity之后进行重写，以达到按钮点击事件
     *
     * @param view
     */
    public void Click(View view) {
//        switch (view.getId()) {
//            case R.id.base_top_relative:
//                finish();
//                break;
//            case R.id.base_top_text1:
//                break;
//        }
    }

    @Override
    public void onClick(View v) {
        Click(v);
    }

    /**
     * 创建界面
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.base_main);
        // 将每个activity存进application类
        Applications.getInstance().addActivity(this);
        //获取手机屏幕大小
        getPhone();

        titlebar = (LinearLayout) findViewById(R.id.titlebar);
        contentView = (LinearLayout) findViewById(R.id.contentView);
        navbar = (LinearLayout) findViewById(R.id.nav);
        base_image_add = (ImageView) findViewById(R.id.base_image_add);


        base_image_add.setOnClickListener(this);
        //添加默认的系统导航
        Nav(R.layout.nav);
        //添加布局文件
        setcontentView();
        //添加系统默认的标题
        Title(R.layout.base_top);
        //初始化界面
        init();
    }

    // 设置标题右边的文字是否可见
    public void TitleBarRight(boolean visible) {
        if (!visible) {
            base_top_text1.setVisibility(View.GONE);
        }
    }

    // 设置标题右边的图片
    public void RightIcon(int resid) {
        base_top_image1.setVisibility(View.VISIBLE);
        base_top_image1.setImageResource(resid);
    }

    // 设置标题
    public void setTitle(String title) {
        base_top_title.setText(title);
    }

    // 设置标题左边的文字
    public void setTitleLeft(String title) {
        base_top_text.setText(title);
    }

    // 设置标题右边的文字
    public void setTitleRight(String title) {
        base_top_text1.setText(title);
    }

    // 设置标题左边的图标
    public void setTitleIcon(int resid) {
        base_top_image.setImageResource(resid);
    }

    // 设置布局的背景颜色
    public void setTitleBackground(int resource) {
        titlebar.setBackgroundResource(resource);
        if (flag) {
            ViewColor(titlebar);
        }
    }

    //设置发表文章的按钮是否可见
    public void setVisiableAdd(boolean visiable) {
        if (visiable) {
            base_image_add.setVisibility(View.VISIBLE);
        } else {
            base_image_add.setVisibility(View.GONE);
        }
    }

    public void Destory() {
        Applications.getInstance().onTerminate();
    }

    public void setNav1(String msg) {
        nav_1_text.setText(msg);
    }

    public void setNav2(String msg) {
        nav_2_text.setText(msg);
    }

    public void setNav3(String msg) {
        nav_3_text.setText(msg);
    }

    public void setNav4(String msg) {
        nav_4_text.setText(msg);
    }

    public void setNav1TextColor(int resid) {
        nav_1_text.setTextColor(resid);
    }

    public void setNav2TextColor(int resid) {
        nav_2_text.setTextColor(resid);
    }

    public void setNav3TextColor(int resid) {
        nav_3_text.setTextColor(resid);
    }

    public void setNav4TextColor(int resid) {
        nav_4_text.setTextColor(resid);
    }

    public void setNav1Image(int resid) {
        nav_1_image.setImageResource(resid);
    }

    public void setNav2Image(int resid) {
        nav_2_image.setImageResource(resid);
    }

    public void setNav3Image(int resid) {
        nav_3_image.setImageResource(resid);
    }

    public void setNav4Image(int resid) {
        nav_4_image.setImageResource(resid);
    }

    public void setNavOrigin1(String msg) {
        if (msg.equals("0")) {
            nav_origin1_linear1.setVisibility(View.GONE);
        } else {
            nav_origin1_linear1.setVisibility(View.VISIBLE);
            origin1.setText(msg);
        }
    }

    public void setNavOrigin2(String msg) {
        if (msg.equals("0")) {
            nav_origin1_linear2.setVisibility(View.GONE);
        } else {
            nav_origin1_linear2.setVisibility(View.VISIBLE);
            origin2.setText(msg);
        }
    }

    public void setNavOrigin3(int msg) {
        if (msg != 0) {
            nav_origin1_linear3.setVisibility(View.VISIBLE);
            origin3.setText("" + msg);
        } else {
            nav_origin1_linear3.setVisibility(View.GONE);
        }
    }

    public void setNavOrigin4(String msg) {
        if (msg.equals("0")) {
            nav_origin1_linear4.setVisibility(View.GONE);
        } else {
            nav_origin1_linear4.setVisibility(View.VISIBLE);
            origin4.setText(msg);
        }
    }

    // 设置布局的背景颜色
    public void setBackground(int Resource) {
        contentView.setBackgroundResource(Resource);
        if (!flag) {
            ViewColor(contentView);
        }
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

    public void setNavSize(int height) {
        RelativeLayout.LayoutParams imagebtn_params = new RelativeLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        if (height == 0) {
            imagebtn_params.height = (int) (BaseActivity.height / 12);
        } else {
            imagebtn_params.height = height;
        }
        imagebtn_params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        navbar.setLayoutParams(imagebtn_params);
    }

    public void setNavSize_Main(int height) {
        LinearLayout.LayoutParams imagebtn_params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        imagebtn_params.height = height;
        imagebtn_params.width = BaseActivity.width;
        nav_main.setLayoutParams(imagebtn_params);
    }

    public void Back(Activity activity) {
        this.activity = activity;
    }

    /**
     * 点击系统退出按钮返回上一页
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Applications.getInstance().removeOneActivity(activity);
        }
        return false;
    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        Log.e("hello", "onStart 启动");
//    }
//
//    @Override
//    protected void onRestart() {
//        super.onRestart();
//        Log.e("hello", "onRestart 重启");
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        Log.e("hello", "onResume 运行");
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        Log.e("hello", "onPause 暂停");
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//        Log.e("hello", "onStop 停止");
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        Log.e("hello", "onDestroy 销毁");
//    }
}
