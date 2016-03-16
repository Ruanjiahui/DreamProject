package com.example.administrator.dreamproject.Fragment;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.example.administrator.data_sdk.AcitivityData;
import com.example.administrator.data_sdk.Common;
import com.example.administrator.data_sdk.FileUtil.FileWrite;
import com.example.administrator.data_sdk.SystemData;
import com.example.administrator.data_sdk.Transformation;
import com.example.administrator.data_sdk.User;
import com.example.administrator.dreamproject.Activity.WebViewActivity;
import com.example.administrator.dreamproject.AnalysisJSON;
import com.example.administrator.dreamproject.CommenData;
import com.example.administrator.dreamproject.Identification;
import com.example.administrator.dreamproject.PATH;
import com.example.administrator.dreamproject.R;
import com.example.administrator.dreamproject.ReleaseData;
import com.example.administrator.dreamproject.SQLite_Table;
import com.example.administrator.http_sdk.HTTP;
import com.example.administrator.http_sdk.HttpInterface;
import com.example.administrator.ui_sdk.ChlidData;
import com.example.administrator.ui_sdk.ListView_Object;
import com.example.administrator.ui_sdk.Other.MyBaseAdapter;
import com.example.administrator.ui_sdk.View.MyListView;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/1/13.
 */
public class PersonalFragment1 extends Fragment implements AdapterView.OnItemClickListener, HttpInterface.HttpHandler {

    private View view = null;
    private ArrayList<ListView_Object> objects = null;
    private MyListView personalfragment1_listview = null;
    private Context context = null;
    private Activity activity = null;
    private User user = null;
    private int pagination = 1;

    private ArrayList<ReleaseData> releaseDatas = null;
    private MyBaseAdapter adapter = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.personalfragment1, null);
        context = getActivity();
        activity = (Activity) context;

        Bundle bundle = getArguments();
        user = bundle.getParcelable("data");

        personalfragment1_listview = (MyListView) view.findViewById(R.id.personalfragment1_listview);

        addItem();
        adapter = new MyBaseAdapter(context, objects, 1);
        personalfragment1_listview.setAdapter(adapter);

        personalfragment1_listview.setOnItemClickListener(this);

        //获取个人的文章
        //userID获取人的ID
        //pagination获取第几页
        //pagesize获取数目
        new HTTP(this, PATH.Personal_article + "?userId=" + user.getUserid() + "&pagination=" + pagination + "&pagesize=15");

        return view;
    }

    /**
     * 添加Gridview 的item数据
     */
    private void addItem() {
        objects = new ArrayList<>();

        if (SQLite_Table.TableVisiable(context, PATH.ihuo, PATH.userarticleinfo) == null) {
            SQLite_Table.createUserArticle(context, PATH.ihuo);
        } else {
            Cursor cursor = SQLite_Table.queryData(context, PATH.ihuo, PATH.userarticleinfo, null, "userId=?", new String[]{user.getUserid()}, null, null, "articleId desc", "10");
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
                        ChlidData childdata = new ChlidData();
                        childdata.setBitmap(Transformation.Resource2Bitmap(activity, R.mipmap.ic_launcher));
                        datas.add(childdata);
                        Bitmap bitmap = FileWrite.readBitmapFile(PATH.article_Logo, images[r]);
                        if (bitmap == null) {
                            synchronized (this) {
//                                name = images[r];
//                                new HTTP(this, PATH.articleicon, SystemImage.Min2Native(images[r]), "1");
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if ("3".equals(releaseDatas.get(position).getArticle_type())) {
            Common.IntentActivity(context, WebViewActivity.class, addParcelable(objects.get(position).getContent(), PATH.article + releaseDatas.get(position).getArticle_url(), Identification.Article_List));
        }
//        Common.IntentActivity(context, WebViewActivity.class, addParcelable(objects.get(position).getContent(), "http://mp.weixin.qq.com/s?__biz=MzIyNzA0NDkwOQ==&mid=401318416&idx=1&sn=0d3f474bd11da86ef962c6d409dcb6a3&scene=18#wechat_redirect", objects.get(position).getSubtitle()));
    }

    private Parcelable addParcelable(String title, String url, int Identification) {
        AcitivityData object = new AcitivityData();

        object.setUrl(url);
        object.setTitle(title);
        object.setIdentification(Identification);

        return object;
    }

    @Override
    public void handler(String result) {
        if (result != null) {
            //重新格式化链表
            releaseDatas = AnalysisJSON.Release_Information(result);

            if (SQLite_Table.TableVisiable(context, PATH.ihuo, PATH.userarticleinfo) == null) {
                SQLite_Table.createArticle(context, PATH.ihuo);
            }
            //将数据写入数据库
            for (int i = 0; i < releaseDatas.size(); i++) {
                SQLite_Table.insert_Table3(context, PATH.ihuo, PATH.userarticleinfo, CommenData.getarticle(releaseDatas.get(i).getArticle_id(), releaseDatas.get(i).getArticle_content(), releaseDatas.get(i).getArticle_images(), releaseDatas.get(i).getArticle_url()
                        , releaseDatas.get(i).getArticle_time(), releaseDatas.get(i).getArticle_type(), releaseDatas.get(i).getUser_id(), releaseDatas.get(i).getArticle_release(), releaseDatas.get(i).getNickname()), "articleId", releaseDatas.get(i).getArticle_id());
            }
            //重写添加数据更新数据
            addItem();
            adapter.ChangeData(objects);
        }
    }

    @Override
    public void handler(Bitmap bitmap) {

    }
}
