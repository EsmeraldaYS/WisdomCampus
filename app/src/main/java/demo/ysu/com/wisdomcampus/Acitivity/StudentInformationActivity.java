package demo.ysu.com.wisdomcampus.Acitivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.BitmapCallback;

import org.apache.commons.codec.binary.Base64;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import demo.ysu.com.wisdomcampus.DB.StudentDao;
import demo.ysu.com.wisdomcampus.InformationBean;
import demo.ysu.com.wisdomcampus.MyRecyclerViewAdapter;
import demo.ysu.com.wisdomcampus.R;

public class StudentInformationActivity extends AppCompatActivity {
    private RecyclerView mrecyclerView;
    private String Url = "http://202.206.245.225/zjdxgc/C";
    private Context mContext = this;
    private List<String> mDatas;
    private MyRecyclerViewAdapter recycleAdapter;
    private LinearLayoutManager mlinearLayoutManager;
    private Context context=this;
    private ImageView zhaopian;
    private Handler handler;
    private SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        zhaopian=(ImageView) findViewById(R.id.zhaopian);
        if (getIntent().getStringExtra("ed")!=null) {
            initIntenet(getIntent().getStringExtra("ed"));
        }else {
            initData();
        }

    }

    private void initIntenet(String ed ) {
        BmobQuery<InformationBean> bmob2Query = new BmobQuery<InformationBean>();
        bmob2Query.getObject(ed,new QueryListener<InformationBean>() {
            @Override
            public void done(InformationBean informationBean, BmobException e) {
                if(e==null){
                    mDatas.clear();
                    mDatas.add(informationBean.getName());
                    mDatas.add(informationBean.getGender());
                    mDatas.add(informationBean.getSchoolOfGraduation());
                    mDatas.add(informationBean.getNation());
                    mDatas.add(informationBean.getPoliticsStatus());
                    mDatas.add(informationBean.getHometown());
                    mDatas.add(informationBean.getIdNumber());
                    mDatas.add(informationBean.getClasses());
                    mrecyclerView=(RecyclerView)findViewById(R.id.recye);
                    mlinearLayoutManager=new LinearLayoutManager(mContext);
                    mrecyclerView.setLayoutManager(mlinearLayoutManager);
                    mlinearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
                    //设置Adapter
                    recycleAdapter=new MyRecyclerViewAdapter(context,mDatas);
                    mrecyclerView.setAdapter( recycleAdapter);
                }else{
                    Log.d("wtd","查询失败：" + e.getMessage());
                }

            }});
    }

    private void initData() {
        StudentDao studentDao=new StudentDao(mContext);
        mDatas=studentDao.queryAll();
        OkHttpUtils.get().url(Url.replace("C",mDatas.get(mDatas.size()-1))).build()
                .connTimeOut(5000)
                .execute(new BitmapCallback() {
                    @Override
                    public void onError(okhttp3.Call call, Exception e) {
                        Log.d("DDD","dd错误");
                    }

                    @Override
                    public void onResponse(Bitmap response) {
                        Log.d("DDD","正确");
                        zhaopian.setImageBitmap(response);
                    }
                });
        mrecyclerView=(RecyclerView)findViewById(R.id.recye);
        mlinearLayoutManager=new LinearLayoutManager(mContext);
        mrecyclerView.setLayoutManager(mlinearLayoutManager);
        mlinearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        //设置Adapter
        recycleAdapter=new MyRecyclerViewAdapter(context,mDatas);
        mrecyclerView.setAdapter( recycleAdapter);

}}