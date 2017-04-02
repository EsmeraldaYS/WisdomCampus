package demo.ysu.com.wisdomcampus.Acitivity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

import demo.ysu.com.wisdomcampus.MyRecyclerAdapter;
import demo.ysu.com.wisdomcampus.R;
import demo.ysu.com.wisdomcampus.utils.linksbean;
import okhttp3.Call;

public class NewActivity extends AppCompatActivity {

    private Context context=this;
    public ArrayList<linksbean> listlinksbean= new ArrayList<>();
    private  RecyclerView recyclerView;
    private MyRecyclerAdapter recyclerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);
        OkHttpUtils.get()
                .url("http://news.ysu.edu.cn/")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e) {
                        Log.d("TAG","失败");
                    }

                    @Override
                    public void onResponse(String response) {
                        Document document = Jsoup.parse(response);
                        Element body=document.body();
                        Elements tables= body.select("table");
                        // Logd.showLog("PPP" + tables.html());
                        Element content= body.getElementById("content_level1_right_content");
                        Elements maincontent = body.getElementsByAttributeValue("id", "u_u4_ifocus");
                        Element u_u4_ifocus_pic=body.getElementById("u_u4_ifocus_piclist");
                        Element piccontents=body.getElementById("u_u4_ifocus_tx_ul");
                        Elements contentli =piccontents.select("li");

                        Elements li = u_u4_ifocus_pic.select("li");
                        for (int i = 0; i <li.size() ; i++) {
                            Element li2=li.get(i);
                            Element contentli2=contentli.get(i);
                            linksbean linksbean=new linksbean();
                            linksbean.setLink("http://news.ysu.edu.cn/"+li2.select("a[href]").attr("href"));
                            linksbean.setHead(contentli2.text());
                            linksbean.setSrc("http://news.ysu.edu.cn/"+li2.getElementsByTag("img").attr("src"));;
                            listlinksbean.add(linksbean);
                        }

                        Elements contents=content.children();
                        recyclerView = (RecyclerView) findViewById(R.id.recycler);
                        LinearLayoutManager layoutManager = new LinearLayoutManager(context );
                        recyclerView.setLayoutManager(layoutManager);
                        layoutManager.setOrientation(OrientationHelper. VERTICAL);
                        recyclerAdapter=new MyRecyclerAdapter(context,listlinksbean);
                        recyclerView.setAdapter( recyclerAdapter);
                    }



                });


    }
}
