package demo.ysu.com.wisdomcampus;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.transformer.CubeOutTransformer;

import java.util.ArrayList;
import java.util.List;

import demo.ysu.com.wisdomcampus.Acitivity.CourseAcitivity;
import demo.ysu.com.wisdomcampus.utils.SpUtil;

public class TheFirstActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, OnBannerListener {
    public static List<Integer> images=new ArrayList<>();
    boolean isFirstIn;
    private static boolean isExit = false;
    private Context mContext=this;
    List<Class<? extends ViewPager.PageTransformer>> transformers=new ArrayList<>();
    private List<String> listlinks=new ArrayList<String>();
    private Integer[] imagesarray={R.mipmap.e,R.mipmap.b,R.mipmap.c,R.mipmap.d};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_the_first);
        initData();
        for(int i=0;i<imagesarray.length;i++){
            images.add(imagesarray[i]);
        }
        ListView listView = (ListView) findViewById(R.id.list);
        String[] data = getResources().getStringArray(R.array.anim);
        listView.setAdapter(new SampleAdapter(this, data));
        listView.setOnItemClickListener(this);
        Banner banner = (Banner) findViewById(R.id.banner);
        //设置banner样式
        banner.setBannerStyle(BannerConfig.CENTER);
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        banner.setImages(images);
        //设置banner动画效果
        banner.setBannerAnimation(Transformer.CubeOut);
        //设置标题集合（当banner样式有显示title时）
        //banner.setBannerTitles(titles);
        //设置自动轮播，默认为true
        banner.isAutoPlay(true);
        banner.setOnBannerListener(this);
        //设置轮播时间
        banner.setDelayTime(2000);
        //设置指示器位置（当banner模式中有指示器时）
        banner.setIndicatorGravity(BannerConfig.CIRCLE_INDICATOR);
        //banner设置方法全部调用完毕时最后调用
        banner.start();
        FloatingActionButton floatBtn = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        floatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isFirstIn){
                    Log.d("Float","点击了");
                    startActivity(new Intent(mContext,CourseAcitivity.class));
                }
            }
        });
    }
    @Override
    public void OnBannerClick(int position) {
        Toast.makeText(getApplicationContext(),"你点击了："+position,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent= new Intent();

                intent.setAction(Intent.ACTION_VIEW);
if(i<4) {
    if (listlinks.get(i) != null) {
        Uri content_url = Uri.parse(listlinks.get(i));

        intent.setData(content_url);

        startActivity(Intent.createChooser(intent, "请选择一款浏览器"));
    }
}
    }
    public void initData(){
        transformers.add(CubeOutTransformer.class);
        SharedPreferences sp = SpUtil.getSp(this, "config");
     isFirstIn = sp.getBoolean("isFirstIn", true);
        listlinks.add("http://news.ysu.edu.cn/info/1003/12910.htm");
        listlinks.add("http://www.ysu.edu.cn/info/1093/2098.htm");
        listlinks.add("http://www.ysu.edu.cn/info/1093/2106.htm");
        listlinks.add("http://www.ysu.edu.cn/info/1093/1951.htm");
    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            isExit = false;
        }
    };

    private void exit() {
        if (!isExit) {
            isExit = true;
            Toast.makeText(getApplicationContext(), "再按一次退出程序",
                    Toast.LENGTH_SHORT).show();
            // 利用handler延迟发送更改状态信息
            mHandler.sendEmptyMessageDelayed(0, 2000);
        } else {
            finish();
            System.exit(0);
        }
    }
}
