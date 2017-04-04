package demo.ysu.com.wisdomcampus.Acitivity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.PostFormBuilder;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import demo.ysu.com.wisdomcampus.DB.BaseInfoDao;
import demo.ysu.com.wisdomcampus.R;
import demo.ysu.com.wisdomcampus.ScoreBean;
import demo.ysu.com.wisdomcampus.utils.HtmlUtils;
import demo.ysu.com.wisdomcampus.utils.TextEncoderUtils;
import demo.ysu.com.wisdomcampus.utils.logd;
import okhttp3.Call;


/**
 * 成绩查询
 */
public class ScoreActivity extends AppCompatActivity {


    private String stuCenterUrl;
    private Context mContext = this;
    private List<String> yearList;
    private String ddlXN = "";
    private String ddlXQ = "";
    private String selectMode = null;
    private static String StuCenterUrl= "http://202.206.245.225/zjdxgc/xs_main.aspx?xh=stuxh";
    private static String VIEWSTATE = "dDwxODI2NTc3MzMwO3Q8cDxsPHhoOz47bDwxNTAxMDQwMTAxNDg7Pj47bDxpPDE+Oz47bDx0PDtsPGk8MT47aTwzPjtpPDU+O2k8Nz47aTw5PjtpPDExPjtpPDEzPjtpPDE2PjtpPDI2PjtpPDI3PjtpPDI4PjtpPDM1PjtpPDM3PjtpPDM5PjtpPDQxPjtpPDQ1Pjs+O2w8dDxwPHA8bDxUZXh0Oz47bDzlrablj7fvvJoxNTAxMDQwMTAxNDg7Pj47Pjs7Pjt0PHA8cDxsPFRleHQ7PjtsPOWnk+WQje+8muadqOaMr+aAnTs+Pjs+Ozs+O3Q8cDxwPGw8VGV4dDs+O2w85a2m6Zmi77ya5L+h5oGv56eR5a2m5LiO5bel56iL5a2m6ZmiOz4+Oz47Oz47dDxwPHA8bDxUZXh0Oz47bDzkuJPkuJrvvJo7Pj47Pjs7Pjt0PHA8cDxsPFRleHQ7PjtsPOiuoeeul+acuuenkeWtpuS4juaKgOacrzs+Pjs+Ozs+O3Q8cDxwPGw8VGV4dDs+O2w86KGM5pS/54+t77ya6K6h566X5py6MTUtNTs+Pjs+Ozs+O3Q8cDxwPGw8VGV4dDs+O2w8MjAxNTA0MDE7Pj47Pjs7Pjt0PHQ8cDxwPGw8RGF0YVRleHRGaWVsZDtEYXRhVmFsdWVGaWVsZDs+O2w8WE47WE47Pj47Pjt0PGk8Mz47QDxcZTsyMDE2LTIwMTc7MjAxNS0yMDE2Oz47QDxcZTsyMDE2LTIwMTc7MjAxNS0yMDE2Oz4+Oz47Oz47dDxwPDtwPGw8b25jbGljazs+O2w8d2luZG93LnByaW50KClcOzs+Pj47Oz47dDxwPDtwPGw8b25jbGljazs+O2w8d2luZG93LmNsb3NlKClcOzs+Pj47Oz47dDxwPHA8bDxWaXNpYmxlOz47bDxvPHQ+Oz4+Oz47Oz47dDxAMDw7QDA8Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7QDA8cDxsPFZpc2libGU7PjtsPG88dD47Pj47Ozs7Pjs7Pjs7Ozs7Ozs7Oz47Oz47dDxAMDw7Ozs7Ozs7Ozs7Pjs7Pjt0PEAwPDs7Ozs7Ozs7Ozs+Ozs+O3Q8O2w8aTwwPjtpPDE+O2k8Mj47aTw0Pjs+O2w8dDw7bDxpPDA+O2k8MT47PjtsPHQ8O2w8aTwwPjtpPDE+Oz47bDx0PEAwPDs7Ozs7Ozs7Ozs+Ozs+O3Q8QDA8Ozs7Ozs7Ozs7Oz47Oz47Pj47dDw7bDxpPDA+O2k8MT47PjtsPHQ8QDA8Ozs7Ozs7Ozs7Oz47Oz47dDxAMDw7Ozs7Ozs7Ozs7Pjs7Pjs+Pjs+Pjt0PDtsPGk8MD47PjtsPHQ8O2w8aTwwPjs+O2w8dDxAMDw7Ozs7Ozs7Ozs7Pjs7Pjs+Pjs+Pjt0PDtsPGk8MD47aTwxPjs+O2w8dDw7bDxpPDA+Oz47bDx0PEAwPHA8cDxsPFZpc2libGU7PjtsPG88Zj47Pj47Pjs7Ozs7Ozs7Ozs+Ozs+Oz4+O3Q8O2w8aTwwPjs+O2w8dDxAMDxwPHA8bDxWaXNpYmxlOz47bDxvPGY+Oz4+Oz47Ozs7Ozs7Ozs7Pjs7Pjs+Pjs+Pjt0PDtsPGk8MD47PjtsPHQ8O2w8aTwwPjs+O2w8dDxwPHA8bDxUZXh0Oz47bDxZU0RYOz4+Oz47Oz47Pj47Pj47Pj47dDxAMDw7Ozs7Ozs7Ozs7Pjs7Pjs+Pjs+Pjs+Z7BDgqrgO6kfLQONUgrLTOsiNS0=";
    private static String __VIEWSTATEGENERATOR="";
    private static String cjUrl="http://202.206.245.225/zjdxgc/xsgrxx.aspx?xh=stuxh&xm=stuname&gnmkdm=N121505";
    private List<ScoreBean> cjList;
    private Toolbar toolbar;
    private ProgressBar pbCjcx;
    private ListView listview;
    private String title = "";
    private TextView tvTitle;
    private String tempXQ;
    private String tempXN;
    private String password;
    private String stuXH;
    private String noCodeVIEWSTATE;
    private String noCodeLoginUrl;
    private static String cjcxUrl;
 private  String xm;
private  String stuNameEncoding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_score);
        setSupportActionBar(toolbar);
        initData();
        initView();
        initLisitener();
        checkoutFromWeb();
    }

    private void initView() {

       toolbar=(Toolbar) findViewById(R.id.toolbar);
        pbCjcx=(ProgressBar) findViewById(R.id.pb_cjcx);
        listview = (ListView) findViewById(R.id.list_cj);
        View view = View.inflate(mContext, R.layout.item_header, null);
        tvTitle =(TextView)findViewById(R.id.tv_title);
        listview.addHeaderView(view, null, false);
    }

    private void initData() {
        BaseInfoDao baseInfoDao = new BaseInfoDao(mContext);
        yearList = null;
        cjcxUrl = baseInfoDao.query("cjcxUrl").replace("xscj","xscj_gc");
        stuCenterUrl = baseInfoDao.query("StuCenterUrl");
        noCodeLoginUrl = baseInfoDao.query("noCodeLoginUrl");
         xm=baseInfoDao.query("stuName");
        //已保存的用户名和密码
        stuNameEncoding = TextEncoderUtils.encoding(xm);
        Log.d("WTY",stuNameEncoding);
        stuXH = baseInfoDao.query("stuXH");
        StuCenterUrl = StuCenterUrl.replace("stuxh", stuXH);
        password = baseInfoDao.query("password");
        Log.d("WTY",cjcxUrl);
        OkHttpUtils.get().url(cjcxUrl)
                .addParams("xh",stuXH)
                .addParams("xm",xm)
                .addParams("gnmkdm","N121501")
                .addHeader("Host","202.206.245.225")
                .addHeader("Referer",StuCenterUrl)
                .build()
                .connTimeOut(5000)
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e) {

                    }

                    @Override
                    public void onResponse(String response) {
                        logd logd=new logd();
                        System.out.println(response);
                        logd.showLog(response);
                        HtmlUtils utils = new HtmlUtils(response);
                        VIEWSTATE=utils.getVIEWSTATE2();

                    }
                });

    }

    private void initLisitener() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }




    /**
     * 查询成绩
     */
    private void checkoutFromWeb() {
        ddlXQ="";
        ddlXN="";
        if (!pbCjcx.isShown()) {
            pbCjcx.setVisibility(View.VISIBLE);
        }
        final PostFormBuilder post = OkHttpUtils.post();
        post.url(cjcxUrl)
                .addHeader("Host", "202.206.245.225")
                .addHeader("Referer", cjcxUrl)
                .addParams("__VIEWSTATE", VIEWSTATE)
                .addParams("ddlXN", ddlXN)
                .addParams("ddlXQ", ddlXQ)
                .addParams("Button1", "%B0%B4%D1%A7%C6%DA%B2%E9%D1%AF")
                //.addParams("__VIEWSTATEGENERATOR",__VIEWSTATEGENERATOR)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                Log.d("TAGcj", "查询失败");
            }
            @Override
            public void onResponse(String response) {
                System.out.println(response);
                Log.d("TAGcja", response);
                HtmlUtils cjUtils = new HtmlUtils(response);
                cjList = cjUtils.parseScore();

                //如果CJList的size == 1，表示没有成绩；
                if (cjList.size() == 1) {
                    title = "当前条件没有成绩哦";
                }
                initUI();
            }
        });
    }
    private void initUI() {
        if (pbCjcx.isShown()) {
            pbCjcx.setVisibility(View.INVISIBLE);
        }
        MyAdapter adapter = new MyAdapter();
        listview.setAdapter(adapter);
        if (pbCjcx.getVisibility() == View.VISIBLE)
            pbCjcx.setVisibility(View.INVISIBLE);
        startAnim();
    }

    private void startAnim() {
        Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.anim);
        listview.startAnimation(animation);
    }


    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return cjList.size();
        }

        @Override
        public Object getItem(int position) {
            return cjList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;
            ViewHolder holder;
            if (convertView == null) {
                view = View.inflate(mContext, R.layout.item_score, null);
                holder = new ViewHolder();
                holder.tvCourse =(TextView)view.findViewById(R.id.tv_course);
                holder.tvScore =(TextView) view.findViewById(R.id.tv_score);
                view.setTag(holder);
            } else {
                view = convertView;
                holder = (ViewHolder) view.getTag();
            }
            holder.tvCourse.setText(cjList.get(position).getCourseName());
            holder.tvScore.setText(String.valueOf(cjList.get(position).getCourseCj()));
            return view;
        }
    }

    public class ViewHolder {
        //课程
        TextView tvCourse;
        //成绩
        TextView tvScore;
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
