package demo.ysu.com.wisdomcampus.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MySQLiteOpenHelper extends SQLiteOpenHelper {


    //数据库版本号
    private static Integer Version = 1;
    public MySQLiteOpenHelper(Context context) {
        //必须通过super调用父类当中的构造函数
        super(context, "PersonInformation.db", null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table PersonInformation(_id integer primary key autoincrement,key varchar(50),value varchar(200) UNIQUE)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


}
