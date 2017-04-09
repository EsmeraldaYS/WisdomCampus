package demo.ysu.com.wisdomcampus.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class BaseInfoHelper extends SQLiteOpenHelper {
    public BaseInfoHelper(Context context) {
        super(context, "baseinfo.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table baseinfo(_id integer primary key autoincrement,key varchar(50),value varchar(200)UNIQUE)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
