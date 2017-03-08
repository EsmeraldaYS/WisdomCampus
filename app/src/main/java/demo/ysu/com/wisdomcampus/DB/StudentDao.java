package demo.ysu.com.wisdomcampus.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/2/4.
 */

public class StudentDao {


    private Context mContext;

    public StudentDao(Context context) {
        mContext = context;
    }

    //添加url到数据库
    public boolean add(String key, String value) {
        MySQLiteOpenHelper helper = new MySQLiteOpenHelper(mContext);
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("key", key);
        values.put("value", value);
        long id = db.insert("PersonInformation", null, values);
        return id != -1;
    }

    public String query(String key) {
        MySQLiteOpenHelper helper = new MySQLiteOpenHelper(mContext);
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.query("PersonInformation", new String[]{"value"}, "key=?", new String[]{key}, null, null, null);
        String value = null;
        if (cursor.moveToNext()) {
            value = cursor.getString(0);
        }
        cursor.close();
        db.close();
        return value;
    }

    public List<String> queryAll() {
        MySQLiteOpenHelper helper = new MySQLiteOpenHelper(mContext);
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.query("PersonInformation", new String[]{"key", "value"}, null, null, null, null, null, null);
        List<String> list = new ArrayList<String>();
        while (cursor.moveToNext()) {
            String key = cursor.getString(0);
            String value = cursor.getString(1);
            Log.d("zzz",value);
           list.add(value);
        }
        cursor.close();
        db.close();
        return list;
    }

    public boolean delete(String key) {
        MySQLiteOpenHelper helper = new MySQLiteOpenHelper(mContext);
        SQLiteDatabase db = helper.getWritableDatabase();
        int delete = db.delete("PersonInformation", "key=?", new String[]{key});
        return delete != 0;
    }

    public boolean deleteAll() {
        MySQLiteOpenHelper helper = new MySQLiteOpenHelper(mContext);
        SQLiteDatabase db = helper.getWritableDatabase();
        int delete = db.delete("PersonInformation", null, null);
        return delete != 0;
    }

}
