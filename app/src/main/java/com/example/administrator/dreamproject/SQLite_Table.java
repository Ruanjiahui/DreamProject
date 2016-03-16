package com.example.administrator.dreamproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.administrator.data_sdk.Database.DatabaseHelper;
import com.example.administrator.data_sdk.ServerData;
import com.example.administrator.data_sdk.User;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/1/26.
 */
public class SQLite_Table {

    private static DatabaseHelper databaseHelper = null;
    public synchronized static DatabaseHelper getInstance(Context context , String db_Name){
        if (databaseHelper == null) {
            databaseHelper = new DatabaseHelper(context  , db_Name);
        }
        return databaseHelper;
    }
    /**
     * 创建serverinfo表
     *
     * @param context
     * @param db_Name
     */
    public static void createserverinfoTable(Context context, String db_Name) {
        DatabaseHelper databaseHelper = SQLite_Table.getInstance(context , db_Name);
        sqLiteDatabase = databaseHelper.getWritableDatabase();
        sql = "create table serverinfo(name varchar(255) , password varchar(255) , phone varchar(11) , online integer)";
        sqLiteDatabase.execSQL(sql);

        sqLiteDatabase.close();
    }

    /**
     * 创建userinfo表
     *
     * @param context
     * @param db_Name
     */
    public static void createUserTable(Context context, String db_Name) {
        DatabaseHelper databaseHelper = SQLite_Table.getInstance(context , db_Name);
        sqLiteDatabase = databaseHelper.getWritableDatabase();

        sql = "CREATE TABLE userinfo(" +
                "userid varchar(12) NOT NULL," +
                "nickname varchar(36) NULL," +
                "truename varchar(8) NULL," +
                "password varchar(16) NULL," +
                "company varchar(32) NULL," +
                "viplev smallint NOT NULL default 0," +
                "sexy varchar(2) NULL," +
                "wbid varchar(16) NULL," +
                "qq varchar(12) NULL," +
                "wxid varchar(12) NULL," +
                "email varchar(32) NULL," +
                "icon varchar(50) NULL," +
                "picture varchar(50) NULL," +
                "d2code varchar(50) NULL," +
                "address varchar(50) NULL," +
                "postcode varchar(6) NULL," +
                "idcard varchar(18) NULL," +
                "note varchar(32) NULL," +
                "registerdate DATETIME NOT NULL DEFAULT (datetime('now', 'localtime'))," +
                "bankaccount varchar(50) NULL," +
                "bankname varchar(50) NULL," +
                "balance decimal(10, 2) NULL," +
                "available decimal(10, 2) NULL," +
                "state TINYINT DEFAULT 0, " +
                "appversion varchar(10) NULL," +
                "Integral int NOT NULL default 0," +
                "height varchar(10) NULL ," +
                "birthday varchar(20) NULL," +
                "occupation varchar(30) NULL , " +
                "education varchar(20) NULL ," +
                "autograph varchar(50) NULL , " +
                "phone varchar(11) NULL);";
        sqLiteDatabase.execSQL(sql);
    }

    public static void createMsgTable(Context context, String db_Name) {
        DatabaseHelper databaseHelper = SQLite_Table.getInstance(context , db_Name);
        sqLiteDatabase = databaseHelper.getWritableDatabase();

        sql = "CREATE TABLE chatmsg(channelid varchar(20) NOT NULL , sender varchar(20) , nickname varchar(20) , content varchar(255) NULL , memsg varchar(6) NOT NULL , read varchar(2) , time datetime)";

        sqLiteDatabase.execSQL(sql);
    }

    /**
     * 创建储存文章内容的表
     *
     * @param context
     * @param db_Name
     */


//    "articleId": "10",
//            "articleContent": "test",
//            "articleImages": "1641322656369801636.jpg",
//            "articleUrl": "1641322656369801636.html",
//            "articleTime": "2016-02-02 16:41:32.267",
//            "articleType": "3",
//            "userId": "15119481373",
//            "articleRelease": "1"
    public static void createArticle(Context context, String db_Name) {
        DatabaseHelper databaseHelper = SQLite_Table.getInstance(context , db_Name);
        sqLiteDatabase = databaseHelper.getWritableDatabase();
        sql = "CREATE TABLE article(" +
                "articleId varchar(20) NOT NULL , " +
                "articleContent varchar(255) , " +
                "articleImages varchar(50) , " +
                "articleUrl varchar(255) NULL , " +
                "articleTime varchar(50) NOT NULL , " +
                "articleType varchar(2) , " +
                "userId varchar(20) , " +
                "articleRelease varchar(3) , " +
                "nickname varchar(15))";

        sqLiteDatabase.execSQL(sql);

    }

    public static void createUserArticle(Context context, String db_Name) {
        DatabaseHelper databaseHelper = SQLite_Table.getInstance(context , db_Name);
        sqLiteDatabase = databaseHelper.getWritableDatabase();
        sql = "CREATE TABLE userarticle(" +
                "articleId varchar(20) NOT NULL , " +
                "articleContent varchar(255) , " +
                "articleImages varchar(50) , " +
                "articleUrl varchar(255) NULL , " +
                "articleTime varchar(50) NOT NULL , " +
                "articleType varchar(2) , " +
                "userId varchar(20) , " +
                "articleRelease varchar(3) , " +
                "nickname varchar(15))";

        sqLiteDatabase.execSQL(sql);

    }

    private static SQLiteDatabase sqLiteDatabase = null;
    private static String sql = "";
    private static Cursor cursor = null;

    /**
     * 判断数据库表是否存在
     *
     * @param context
     * @param TableName
     * @return
     */
    public static Cursor TableVisiable(Context context, String db_Name, String TableName) {
        DatabaseHelper databaseHelper = SQLite_Table.getInstance(context , db_Name);
        sqLiteDatabase = databaseHelper.getReadableDatabase();

        sql = "select * from " + TableName;
        try {
            cursor = sqLiteDatabase.rawQuery(sql, null);
            return cursor;
        } catch (Exception e) {
            Log.e("dreamproject", "no have table");
            return null;
        }
    }

    /**
     * 判断表是否拥有数据
     *
     * @param context
     * @param db_Name
     * @param TableName
     * @return
     */
    public static int TableDataVisiable(Context context, String db_Name, String TableName) {
        DatabaseHelper databaseHelper = SQLite_Table.getInstance(context , db_Name);
        sqLiteDatabase = databaseHelper.getReadableDatabase();

        sql = "select count(*) from " + TableName;
        try {
            cursor = sqLiteDatabase.rawQuery(sql, null);
            if (cursor.moveToNext()) {
                return cursor.getInt(0);
            }
            return 0;
        } catch (Exception e) {
            Log.e("dreamproject", "no have data");
            return 0;
        }
    }

    /**
     * 添加表
     *
     * @param db
     * @param Table_Name
     * @param contentValues
     */
    public static void insertTable(Context context, String db, String Table_Name, ContentValues contentValues) {
        DatabaseHelper databaseHelper = SQLite_Table.getInstance(context , db);
        sqLiteDatabase = databaseHelper.getWritableDatabase();
//        SQLite_Table.sqLiteDatabase = db.getWritableDatabase();

        sqLiteDatabase.insert(Table_Name, null, contentValues);
    }

    /**
     * 添加表的数据
     *
     * @param context
     * @param db
     * @param Table_Name
     * @param contentValues
     */
    public static void insertData(Context context, String db, String Table_Name, ContentValues contentValues) {
        DatabaseHelper databaseHelper = SQLite_Table.getInstance(context , db);
        sqLiteDatabase = databaseHelper.getWritableDatabase();

        sqLiteDatabase.insert(Table_Name, null, contentValues);
    }

    /**
     * 查询数据数据
     * @param context
     * @param db
     * @param Table_Name
     * @param colums        获取数据的列名称
     * @param selection     where后面的内容
     * @param selectionArgs where后面内容的参数
     * @param groupBy        GROUP BY后面的字符串
     * @param having         HAVING后面的字符串
     * @param orderBy        ORDER BY后面的字符串
     * @param limit          返回的数量
     * @return
     */
    public static Cursor queryData(Context context, String db, String Table_Name, String[] colums, String selection, String[] selectionArgs, String groupBy, String having, String orderBy, String limit) {
        DatabaseHelper databaseHelper = SQLite_Table.getInstance(context , db);
        sqLiteDatabase = databaseHelper.getReadableDatabase();

//        sql = "select (channelid , read) from chatmsg group by channelid having count(channelid) > 0";
//        sqLiteDatabase.rawQuery(sql , null);
//        Log.e("hello" , sqLiteDatabase.query(Table_Name, colums, selection, selectionArgs, groupBy, having, orderBy, limit) + "***");
        return sqLiteDatabase.query(Table_Name, colums, selection, selectionArgs, groupBy, having, orderBy, limit);
//        return sqLiteDatabase.rawQuery(sql, null);
    }

    /**
     * 修改表的数据
     *
     * @param context
     * @param db
     * @param Table_Name
     * @param contentValues
     */
    public static void updateData(Context context, String db, String Table_Name, ContentValues contentValues, String where, String[] args) {
        DatabaseHelper databaseHelper = SQLite_Table.getInstance(context , db);
        sqLiteDatabase = databaseHelper.getWritableDatabase();

        if (SQLite_Table.TableDataVisiable(context, db, Table_Name) != 0) {
            try {
                sqLiteDatabase.update(Table_Name, contentValues, where, args);
            } catch (Exception e) {
                Log.e("error", "update fair");
            }
        }
    }

    /**
     * 获取数据库用户表的信息
     *
     * @param cursor
     */
    public static User dbUser(Cursor cursor) {
        User user = new User();

        if (cursor.moveToNext()) {
            //暂时

            if (cursor.getString(11) != null) {
                user.setLogo_url(cursor.getString(11));
            }
            if (cursor.getString(27) != null) {
                user.setBirthday(cursor.getString(27));
            }
//            user.setIdentification(cursor.getInt(0));
            user.setUsername(cursor.getString(1));
            user.setHeight(cursor.getInt(26));
            if (!cursor.getString(26).equals("") || cursor.getString(26).length() > 0) {
                user.setHeight(Integer.parseInt(cursor.getString(26)));
            }
            if (cursor.getString(30) != null) {
                user.setAutograph(cursor.getString(30));
            }
            if (cursor.getString(29) != null) {
                user.setEducation(cursor.getString(29));
            }
            user.setGrade(cursor.getString(5));
            if (cursor.getString(31) != null) {
                user.setPhone(cursor.getString(31));
            }
            user.setLive(cursor.getString(14));
            if (cursor.getString(31) != null) {
                user.setPhone(cursor.getString(31));
            }
            user.setQrcode(cursor.getString(13));
            user.setUserid(cursor.getString(0));
            user.setSex(cursor.getInt(6));
            if (cursor.getString(28) != null) {
                user.setOccupation(cursor.getString(28));
            }
        }
        cursor.close();
        return user;
    }

    /**
     * 判断该表有没有这条数据
     *
     * @param context
     * @param db
     * @param Table_Name
     * @param key
     * @param id
     * @return
     */
    public static boolean visiableData(Context context, String db, String Table_Name, String key, String id) {
        DatabaseHelper databaseHelper = SQLite_Table.getInstance(context , db);
        sqLiteDatabase = databaseHelper.getWritableDatabase();

        sql = "select count(*) from " + Table_Name + " where " + key + " = ?";

        Cursor cursor = sqLiteDatabase.rawQuery(sql, new String[]{id});

        if (cursor.moveToNext()) {
            if (cursor.getInt(0) == 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * 解析serverinfo表的数据
     *
     * @param cursor
     * @return
     */
    public static ServerData dbServer(Cursor cursor) {
        ServerData serverData = new ServerData();

        if (cursor.moveToNext()) {
            serverData.setPhone(cursor.getString(2));
            serverData.setName(cursor.getString(0));
            serverData.setOnline(cursor.getInt(3));
            serverData.setPassword(cursor.getString(1));
        }
//        cursor.close();
        return serverData;
    }

    /**
     * @param context
     * @param db
     * @param Table_Name
     * @param key
     * @param value
     * @param value_1
     */
    public static void insertTable(Context context, String db, String Table_Name, String key, String value, int value_1) {
        Cursor cursor = SQLite_Table.TableVisiable(context, db, Table_Name);
        ContentValues contentValues = new ContentValues();
        if (value == null)
            contentValues.put(key, value_1);
        else
            contentValues.put(key, value);
        if (cursor != null) {
            if (SQLite_Table.TableDataVisiable(context, db, Table_Name) == 0) {
                SQLite_Table.insertData(context, db, Table_Name, contentValues);
            } else {
                SQLite_Table.updateData(context, db, Table_Name, contentValues, null, null);
            }
        } else {
            SQLite_Table.createUserTable(context, db);
        }
    }

    public static void insert_Table1(Context context, String db, String Table_Name, ContentValues contentValues) {
        //判断userinfo这个表有没有存在
//        if (SQLite_Table.TableVisiable(context, db, Table_Name) == null) {
//            SQLite_Table.createUserTable(context, db);
//        }
        //判断userinfo这个表有没有数据
        if (SQLite_Table.TableDataVisiable(context, db, Table_Name) != 0) {
//            Log.e("error" , "sfssgggf");
            SQLite_Table.updateData(context, db, Table_Name, contentValues, null, null);
        } else {
//            Log.e("error" , "fsds");
            SQLite_Table.insertData(context, db, Table_Name, contentValues);
        }
    }

    public static void insert_Table2(Context context, String db, String Table_Name, ContentValues contentValues) {
        //判断userinfo这个表有没有存在
//        if (SQLite_Table.TableVisiable(context, db, Table_Name) == null) {
//            SQLite_Table.createMsgTable(context, db);
//        }
        //判断userinfo这个表有没有数据
//        if (SQLite_Table.TableDataVisiable(context, db, Table_Name) != 0) {
//            Log.e("error" , "sfssgggf");
//            SQLite_Table.updateData(context, db, Table_Name, contentValues, null, null);
//        } else {
//            Log.e("error" , "fsds");
        SQLite_Table.insertData(context, db, Table_Name, contentValues);
//        }
    }

    /**
     * 如果表拥有该数据则不插入，否则则插入
     *
     * @param context
     * @param db
     * @param Table_Name
     * @param contentValues
     * @param key
     * @param cotent
     */
    public static void insert_Table3(Context context, String db, String Table_Name, ContentValues contentValues, String key, String cotent) {
        //判断userinfo这个表有没有存在
//        if (SQLite_Table.TableVisiable(context, db, Table_Name) == null) {
//            SQLite_Table.createMsgTable(context, db);
//        }
        //判断该表有没有这个数据，没有则插入，有则不插入
        if (!SQLite_Table.visiableData(context, db, Table_Name, key, cotent)) {
            SQLite_Table.insertData(context, db, Table_Name, contentValues);
        }
    }


    public static ArrayList<ReleaseData> getArticle(Cursor cursor) {
        ArrayList<ReleaseData> releaseDatas = new ArrayList<>();
        while (cursor.moveToNext()) {
            ReleaseData releaseData = new ReleaseData();
            releaseData.setArticle_id(cursor.getString(0));
            releaseData.setArticle_url(cursor.getString(3));
            releaseData.setArticle_type(cursor.getString(5));
            releaseData.setArticle_time(cursor.getString(4));
            releaseData.setArticle_content(cursor.getString(1));
            releaseData.setArticle_images(cursor.getString(2));
            releaseData.setUser_id(cursor.getString(6));
            releaseData.setArticle_release(cursor.getString(7));
            releaseData.setNickname(cursor.getString(8));

            releaseDatas.add(releaseData);
        }
        return releaseDatas;
    }


    /**
     * 判断用户是否登录
     * @param context
     * @return
     */
    public static boolean StateLogin(Context context) {
        //登录成功后才进入个人中心
        cursor = SQLite_Table.TableVisiable(context, PATH.ihuo, PATH.serverinfo);
        //如果没有这个表则视为未登录状态
        if (cursor == null) {
            return false;
        } else {
            ServerData serverData = SQLite_Table.dbServer(cursor);
            //如果表存在但是online状态为未登录也视为未登录状态
            if (serverData.getOnline() == 0)
                return false;
            else
                return true;
        }
    }
}
