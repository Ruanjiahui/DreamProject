package com.example.administrator.dreamproject.Fragment;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.administrator.data_sdk.AcitivityData;
import com.example.administrator.data_sdk.Common;
import com.example.administrator.data_sdk.FileUtil.FileWrite;
import com.example.administrator.data_sdk.SystemData;
import com.example.administrator.data_sdk.Transformation;
import com.example.administrator.data_sdk.User;
import com.example.administrator.dreamproject.Activity.Load;
import com.example.administrator.dreamproject.Activity.Me;
import com.example.administrator.dreamproject.Activity.Personal;
import com.example.administrator.dreamproject.Activity.WebViewActivity;
import com.example.administrator.dreamproject.AnalysisJSON;
import com.example.administrator.dreamproject.CommenData;
import com.example.administrator.dreamproject.Identification;
import com.example.administrator.dreamproject.PATH;
import com.example.administrator.dreamproject.R;
import com.example.administrator.dreamproject.ReleaseData;
import com.example.administrator.dreamproject.SQLite_Table;
import com.example.administrator.dreamproject.SystemImage;
import com.example.administrator.http_sdk.HTTP;
import com.example.administrator.http_sdk.HttpInterface;
import com.example.administrator.ui_sdk.ChlidData;
import com.example.administrator.ui_sdk.DensityUtil;
import com.example.administrator.ui_sdk.ListView_Object;
import com.example.administrator.ui_sdk.MyOnClickInterface;
import com.example.administrator.ui_sdk.Other.BaseActivity;
import com.example.administrator.ui_sdk.Other.MyBaseAdapter;
import com.example.administrator.ui_sdk.Other.MyViewPagerAdapter;
import com.example.administrator.ui_sdk.View.MyDialog;
import com.example.administrator.ui_sdk.View.SystemManager;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/1/11.
 */
public class Fragment1 extends Fragment implements ViewPager.OnPageChangeListener, View.OnClickListener, AdapterView.OnItemClickListener, MyOnClickInterface, HttpInterface.HttpHandler {

    private View view = null;

    private ViewPager fragment1_viewpager = null;

    //储存Fragment的链表
    private ArrayList<Fragment> list = null;
    //Fragment适配器
    private MyViewPagerAdapter pagerAdapter = null;
    //储存ListViewObject链表
    private ArrayList<ListView_Object> objects = null;

    //网格布局
    private ListView fragment1_listview = null;

    private static Context context = null;

    private RelativeLayout person_linear = null;

    //记录Viewpager当前的第几个页面
    public static int position = 0;

    private ScrollView scrollView = null;

    private MyBaseAdapter adapter = null;
    //保存自己的用户信息
    private User user = null;
    //保存他人的用户信息
    private User other_user = null;
    private static ImageView person_image = null;
    private static TextView person_name = null;
    private static TextView person_subtitle = null;
    private Activity activity = null;
    private MyDialog dialog = null;
    private Cursor cursor = null;

    private ArrayList<ReleaseData> releaseDatas = null;

    private String name = "";
    private int tmp = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment1, null);

        context = getActivity();
        activity = (Activity) context;

        Bundle bundle = getArguments();
        user = bundle.getParcelable("data");

        id();
        instance();
        addItem();

        DensityUtil.setHeight(person_linear, BaseActivity.height / 8);
        //传参数第一个context , 第二个是对象 , 第三个则是显示类型
        adapter = new MyBaseAdapter(context, objects, 1);
        fragment1_listview.setAdapter(adapter);
        //设置沉寂式状态栏
        SystemManager.ViewColor(person_linear, getActivity());

        setTileTxt(user);

        person_linear.setOnClickListener(this);
        fragment1_listview.setOnItemClickListener(this);
        adapter.setIconClick(this);

//        fragment1_listview.setOnScrollListener(new AbsListView.OnScrollListener() {
//
//            @Override
//            public void onScrollStateChanged(AbsListView view, int scrollState) {
//                Log.e("hello" , fragment1_listview.getFirstVisiblePosition() + "");
//            }
//
//            @Override
//            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
//
//            }
//        });
        if (CommenData.Fragment_FLAG) {
            //获取文章
            new HTTP(this, PATH.information + "?pagination=1&pagesize=15");
            CommenData.Fragment_FLAG = false;
        }


        return view;
    }

    /**
     * 添加Gridview 的item数据
     */
    private void addItem() {
        objects = new ArrayList<>();

//        Cursor cursor = SQLite_Table.TableVisiable(context, PATH.ihuo, PATH.articleinfo);
        if (SQLite_Table.TableVisiable(context, PATH.ihuo, PATH.articleinfo) == null) {
            SQLite_Table.createArticle(context, PATH.ihuo);
        } else {
            Cursor cursor = SQLite_Table.queryData(context, PATH.ihuo, PATH.articleinfo, null, null, null, null, null, "articleId desc", "10");
            releaseDatas = SQLite_Table.getArticle(cursor);
            for (int i = 0; i < releaseDatas.size(); i++) {
                String images[] = null;
                if ("1".equals(releaseDatas.get(i).getArticle_type())) {
                    objects.add(i, addObject(SystemData.createRoundConerImage(Transformation.zoomImg(context, Transformation.Resource2Bitmap(activity, R.mipmap
                            .m_0), 50, 50)), releaseDatas.get(i).getArticle_content(), "", releaseDatas.get(i).getNickname(), null, null, false));
                }
                if ("2".equals(releaseDatas.get(i).getArticle_type())) {
                    //获取数组的图片名称
                    images = releaseDatas.get(i).getArticle_images().split("\\|");
                    ArrayList<ChlidData> datas = new ArrayList<>();
                    for (int r = 0; r < images.length; r++) {
                        tmp++;
                        ChlidData childdata = new ChlidData();
                        childdata.setBitmap(Transformation.Resource2Bitmap(activity, R.mipmap.ic_launcher));
                        datas.add(childdata);
                        Bitmap bitmap = FileWrite.readBitmapFile(PATH.article_Logo, images[r]);
                        if (bitmap == null) {
                            synchronized (this) {
                                name = images[r];
                                new HTTP(this, PATH.articleicon, SystemImage.Min2Native(images[r]), "1");
                            }
                        } else {
                            childdata.setBitmap(bitmap);
                        }
                    }
                    objects.add(i, addObject(SystemData.createRoundConerImage(Transformation.zoomImg(context, Transformation.Resource2Bitmap(activity, R.mipmap
                            .m_0), 50, 50)), releaseDatas.get(i).getArticle_content(), "", releaseDatas.get(i).getNickname(), null, datas, false));
                }
                if ("3".equals(releaseDatas.get(i).getArticle_type())) {
                    objects.add(i, addObject(SystemData.createRoundConerImage(Transformation.zoomImg(context, Transformation.Resource2Bitmap(activity, R.mipmap
                            .m_0), 50, 50)), "", releaseDatas.get(i).getArticle_content(), releaseDatas.get(i).getNickname(), Transformation.Resource2Bitmap(activity, R.mipmap.ic_launcher), null, true));
                }
            }
        }
    }

    private ListView_Object addObject(Bitmap resid, String content, String subtitle, String name, Bitmap icon, ArrayList<ChlidData> list, boolean Click) {
        ListView_Object object = new ListView_Object();

        object.setNewsContent(content);
        object.setNewsname(name);
        object.setNewsTitle(subtitle);
        object.setIcon(icon);
        object.setNewsIcon(resid);
        object.setClick(Click);
        object.setList(list);

        return object;
    }

    /**
     * 获取组件ID
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void id() {
        fragment1_listview = (ListView) view.findViewById(R.id.fragment1_listview);
        person_image = (ImageView) view.findViewById(R.id.person_image);
        person_linear = (RelativeLayout) view.findViewById(R.id.person_linear);
        person_name = (TextView) view.findViewById(R.id.person_name);
        person_subtitle = (TextView) view.findViewById(R.id.person_subtitle);
    }

    /**
     * 配置pager适配器
     */
    private void instance() {
        list = new ArrayList<Fragment>();
        list.add(new FragmentNews(R.mipmap.getimgdata, "伟大复兴梦想节”的标志"));
        list.add(new FragmentNews(R.mipmap.get1, "“伟大复兴梦想节”的来源"));
        list.add(new FragmentNews(R.mipmap.get2, "伟大复兴（北京）文化产业股份公司介绍"));
        //配置适配器
        pagerAdapter = new MyViewPagerAdapter(getChildFragmentManager(),
                list, true);
        View view = LayoutInflater.from(context).inflate(R.layout.viewpager_top, null);
        fragment1_viewpager = (ViewPager) view.findViewById(R.id.fragment1_viewpager);
        fragment1_viewpager.setAdapter(pagerAdapter);

        DensityUtil.setHeight(fragment1_viewpager, BaseActivity.height / 4);
        // ViewPager默认显示第一个Fragment
        fragment1_viewpager.setCurrentItem(0, true);
        //将Viewpager添加到listview头部
        fragment1_listview.addHeaderView(view);
        fragment1_viewpager.setOnPageChangeListener(this);
    }


    /**
     * Viewpager点击事件
     *
     * @param position
     * @param positionOffset
     * @param positionOffsetPixels
     */
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        Fragment1.position = position;
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.person_linear:
                //登录成功后才进入个人中心

                if (SQLite_Table.StateLogin(context)) {
                    Common.IntentActivity(activity, Me.class, user, Identification.Main_Logo);
                } else {
                    Common.IntentActivity(activity, Load.class, null, Identification.Load);
                }
                break;
            default:

                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if ("3".equals(releaseDatas.get(position - 1).getArticle_type())) {
            Common.IntentActivity(context, WebViewActivity.class, addParcelable(objects.get(position - 1).getContent(), PATH.article + releaseDatas.get(position - 1).getArticle_url(), Identification.Article_List));
        }
    }

    private Parcelable addParcelable(String title, String url, int Identification) {
        AcitivityData object = new AcitivityData();
        object.setTitle(title);
        object.setUrl(url);
        object.setIdentification(Identification);

        return object;
    }

    @Override
    public void ItemOnClick(int position) {
//        User user = new User();
//        user =
        //跳转到个人信息
        Common.IntentActivity(context, Personal.class, user);
    }

    @Override
    public void onStart() {
        super.onStart();
//        fragment1_listview.smoothScrollToPosition(0);
    }

    /**
     * 处理获取的文章信息
     *
     * @param result
     */
    @Override
    public void handler(String result) {
        if (result != null) {
            //重新格式化链表
            releaseDatas = AnalysisJSON.Release_Information(result);

            if (SQLite_Table.TableVisiable(context, PATH.ihuo, PATH.articleinfo) == null) {
                SQLite_Table.createArticle(context, PATH.ihuo);
            }
            //将数据写入数据库
            for (int i = 0; i < releaseDatas.size(); i++) {
                SQLite_Table.insert_Table3(context, PATH.ihuo, PATH.articleinfo, CommenData.getarticle(releaseDatas.get(i).getArticle_id(), releaseDatas.get(i).getArticle_content(), releaseDatas.get(i).getArticle_images(), releaseDatas.get(i).getArticle_url()
                        , releaseDatas.get(i).getArticle_time(), releaseDatas.get(i).getArticle_type(), releaseDatas.get(i).getUser_id(), releaseDatas.get(i).getArticle_release(), releaseDatas.get(i).getNickname()), "articleId", releaseDatas.get(i).getArticle_id());
            }
            //重写添加数据更新数据
            addItem();
            adapter.ChangeData(objects);
        }
    }

    private int tmp1 = 0;

    @Override
    public void handler(Bitmap bitmap) {
        tmp1++;
        FileWrite.saveBitmapFile(bitmap, name, PATH.article_Logo);
        if (tmp == tmp1) {
            addItem();
            adapter.ChangeData(objects);
        }
    }

    public static void setTileTxt(User user) {

        user.setLogo(user.getLogo());
        //将图片设置为圆形
        user.setLogo(SystemData.createRoundConerImage(Transformation.zoomImg(context, user.getLogo(), 90, 90)));
        person_name.setText(user.getUsername());
        person_image.setImageBitmap(SystemData.createRoundConerImage(user.getLogo()));
        person_subtitle.setText(user.getAutograph());
    }
}
