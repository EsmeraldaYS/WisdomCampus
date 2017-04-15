package demo.ysu.com.wisdomcampus.Acitivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.PostFormBuilder;
import com.zhy.http.okhttp.callback.BitmapCallback;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zhy.http.okhttp.request.RequestCall;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import demo.ysu.com.wisdomcampus.CourseBean;
import demo.ysu.com.wisdomcampus.DB.BaseInfoDao;
import demo.ysu.com.wisdomcampus.DB.BaseInfoHelper;
import demo.ysu.com.wisdomcampus.DB.CourseDao;
import demo.ysu.com.wisdomcampus.DB.CourseHelper;
import demo.ysu.com.wisdomcampus.DB.MySQLiteOpenHelper;
import demo.ysu.com.wisdomcampus.DB.StudentDao;
import demo.ysu.com.wisdomcampus.InformationBean;
import demo.ysu.com.wisdomcampus.R;
import demo.ysu.com.wisdomcampus.TheFirstActivity;
import demo.ysu.com.wisdomcampus.utils.HtmlUtils;
import demo.ysu.com.wisdomcampus.utils.ParseCourses;
import demo.ysu.com.wisdomcampus.utils.PersonInformationUtil;
import demo.ysu.com.wisdomcampus.utils.SpUtil;
import demo.ysu.com.wisdomcampus.utils.TextEncoderUtils;
import okhttp3.Call;
import okhttp3.Response;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    InformationBean informationBean=new InformationBean();;
    EditText etCode;
    EditText etUsername;
    EditText etPassword;
    ImageView ivCodes;
    TextView tvChange;
    ProgressBar pbLogin;
    TextView tvError;
    LinearLayout rootView;
    Button btLogin ;
    private List<CourseBean> allCourseList;
    private ArrayList<String> personinformationlist;
    private String mainUrl = "http://202.206.245.231";
    //验证码
    private String codeUrl = "http://202.206.245.231/zjdxgc/CheckCode.aspx";
    //登录
    private String loginUrl = "http://202.206.245.231/zjdxgc/default2.aspx";
    //登出
    private String logoutUrl = "http://202.206.245.231/zjdxgc/logout.aspx";
    //无需验证码登陆的url
    private String noCodeLoginUrl = "http://202.206.245.231/zjdxgc/default6.aspx";
    //下面的url都需要进行替换修改
    private static String piUrl="http://202.206.245.231/zjdxgc/xsgrxx.aspx?xh=stuxh&xm=stuname&gnmkdm=N121501";
    //个人中心
    private static String StuCenterUrl = "http://202.206.245.231/zjdxgc/xs_main.aspx?xh=stuxh";
    //成绩查询
    private static String cjcxUrl = "http://202.206.245.231/zjdxgc/xscj.aspx?xh=stuxh&xm=stuname&gnmkdm=N121605";
    //考试查询
    private static String kscxUrl = "http://202.206.245.231/zjdxgc/xskscx.aspx?xh=stuxh&xm=stuname%90&gnmkdm=N121604";
    //课程表查询
    private static String kbcxUrl = "http://202.206.245.231/zjdxgc/xskbcx.aspx?xh=stuxh&xm=stuname&gnmkdm=N121603";
    private static String __VIEWSTATE;
    private String WWW="WWW";
    private String userId;
    private String password;
    private String code;
    private Context mContext = this;
    private String stuXH;
    private String stuName;
    private SharedPreferences sp;
    private String stuNameEncoding;
    //private ProgressDialog waitDialog;
    boolean Intenet;
    boolean isFirstIn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Bmob.initialize(this, "992eb924efef29ccc48535e294ead989");
        etCode=(EditText)findViewById(R.id.et_code);
        etUsername=(EditText) findViewById(R.id.et_username);
        etPassword=(EditText)findViewById(R.id.et_password);
        ivCodes=(ImageView) findViewById(R.id.iv_codes);
        tvChange=(TextView)findViewById(R.id.tv_change);
        pbLogin=(ProgressBar)findViewById(R.id.pb_login);
        tvError= (TextView) findViewById(R.id.tv_error);
        rootView= (LinearLayout) findViewById(R.id.rootView);
        btLogin= (Button) findViewById(R.id.bt_login);
        SharedPreferences sp = SpUtil.getSp(this, "config");
        isFirstIn = sp.getBoolean("isFirstIn", true);
        Intenet=sp.getBoolean("Intenet", true);

        initData();
        initListener();
        showCodeimage();
    }

    private void initData() {
        sp = SpUtil.getSp(mContext, "config");
        sp.edit().putBoolean("wherein", false).apply();
    }

         /*
          * 获取VIEWSTATE，网页如果没有最新的VIEWSTATE参数会重定向,(我也不知道是不是重定向)
          * */
    private void initListener() {
  OkHttpUtils.get().url("http://202.206.245.231/zjdxgc/default2.aspx").build() .connTimeOut(5000)
                .execute(new StringCallback() {
                             @Override
                             public void onError(Call call, Exception e) {

                             }

                             @Override
                             public void onResponse(String response) {
                                 HtmlUtils utils = new HtmlUtils(response);
                                 __VIEWSTATE=utils.getVIEWSTATE();
                                 changCodeImage();
                             }
                         });


        tvChange.setOnClickListener(this);
        ivCodes.setOnClickListener(this);
        btLogin.setOnClickListener(this);

        final InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        //监听软键盘回车键
        etCode.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    //关闭软键盘
                    imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                    attemptLogin();
                }
                return true;
            }
        });
    }

    //加载验证码
    public void showCodeimage() {
        OkHttpUtils.get().url(codeUrl).build()
                .connTimeOut(5000)
                .execute(new BitmapCallback() {
                    @Override
                    public void onError(okhttp3.Call call, Exception e) {
                    }

                    @Override
                    public void onResponse(Bitmap response) {
                        ivCodes.setImageBitmap(response);
                    }
                });
    }

    //登录前检查输入的数据
    private void attemptLogin() {
        View focusView = null;
        userId = etUsername.getText().toString().trim();
        password = etPassword.getText().toString().trim();
        code = etCode.getText().toString().trim();
        if (TextUtils.isEmpty(userId)) {
            etUsername.setError("学号不能为空");
            focusView = etUsername;
        } else if (TextUtils.isEmpty(password)) {
            etPassword.setError("密码不能为空");
            focusView = etPassword;
        } else if (TextUtils.isEmpty(code)) {
            etCode.setError("验证码不能为空");
            focusView = etCode;
        }
        if (focusView != null) {
            focusView.requestFocus();
        } else {
            //向服务器请求登录
            requestLogin();
        }
    }

    /**
     * 向服务器模拟登陆
     */
    private void requestLogin() {
        int visibility = tvError.getVisibility();
        if (visibility == View.VISIBLE) {
            tvError.setVisibility(View.INVISIBLE);
        }

        pbLogin.setVisibility(View.VISIBLE);
        final PostFormBuilder post = OkHttpUtils.post();
        post.url(loginUrl);
        post.tag(this);
        //下面数据抓包可以得到
        post.addParams("__VIEWSTATE", __VIEWSTATE);
        post.addParams("__VIEWSTATEGENERATOR", "F5DFF49B");
        post.addParams("txtUserName", userId);
        post.addParams("TextBox2", password);
        post.addParams("txtSecretCode", code);
        post.addParams("RadioButtonList1", "%D1%A7%C9%FA");
        post.addParams("Button1", "");
        post.addParams("lbLanguage", "");
        post.addHeader("Host", "202.206.245.231");
        post.addHeader("Referer", "http://202.206.245.231/zjdxgc/default2.aspx");
        post.addHeader("User-Agent", "Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0)");
        post.build()
                .connTimeOut(5000)
                .execute(new StringCallback() {
                    @Override
                    public void onError(okhttp3.Call call, Exception e) {
                        pbLogin.setVisibility(View.GONE);
                        tvError.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onResponse(String response) {
                        View focusView = null;
                        if (response.contains("验证码不正确")) {
                            etCode.setError("验证码错误");
                            focusView = etCode;
                        } else if (response.contains("密码错误")) {
                            etPassword.setError("密码错误");
                            focusView = etPassword;
                        } else if (response.contains("用户名不存在")) {
                            etUsername.setError("用户名不存在");
                            focusView = etUsername;
                        }
                        if (focusView != null) {
                            focusView.requestFocus();
                            pbLogin.setVisibility(View.INVISIBLE);
                            changCodeImage();
                        } else {
                            //登录成功
                            pbLogin.setVisibility(View.INVISIBLE);
                            showSaveDataDialog(response);
                        }
                    }
                });}



    private void showSaveDataDialog(String response) {

   /*     waitDialog = new ProgressDialog(mContext);
        waitDialog.setTitle("请稍后");
        waitDialog.setMessage("Loading...");
        waitDialog.show();*/
        initURL(response);
        enterTheSecond(StuCenterUrl);
        getThePersonalInformation(  piUrl);
        //saveDataToDB();

    }

    /**
     * 初始化用户数据
     *
     * @param response
     */
    private void initURL(String response) {
        HtmlUtils utils = new HtmlUtils(response);
        String xhandName = utils.getXhandName();
        String[] split = xhandName.split(" ");
        //用户的学号
        stuXH = etUsername.getText().toString().trim();;
        //用户的姓名
        stuName = split[0].replace("同学", "");

        stuNameEncoding = TextEncoderUtils.encoding(stuName);
        cjcxUrl = cjcxUrl.replace("stuxh", stuXH).replace("stuname", stuNameEncoding);
        kbcxUrl = kbcxUrl.replace("stuxh", stuXH).replace("stuname", stuNameEncoding);
        kscxUrl = kscxUrl.replace("stuxh", stuXH).replace("stuname", stuNameEncoding);
        piUrl=piUrl.replace("stuxh", stuXH).replace("stuname", stuNameEncoding);
        StuCenterUrl = StuCenterUrl.replace("stuxh", stuXH);
    }



    private void enterTheSecond(String stuCenterUrl) {

        OkHttpUtils.get().url(StuCenterUrl)
                .addHeader("Host",	"202.206.245.231")
                .addHeader("Referer",loginUrl)
                .addHeader("User-Agent","Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0)")
                .build()
                .connTimeOut(3000)
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e) {
                        Log.d(WWW,"enterTheSecond失败");
                    }

                    @Override
                    public void onResponse(String response) {
                        initCourseData(stuXH);
                    }
                });

    }
/*
*  获得课表
*
* */
    private void initCourseData(String stuXH) {
        Log.d("ceshi","sadjasdhgsa");
        kbcxUrl = kbcxUrl.replace("stuxh", stuXH).replace("stuname", stuNameEncoding);
        OkHttpUtils.post().url(kbcxUrl)
                .tag(this)
                .addHeader("gnmkdm", "N121603")
                .addParams("xm",stuNameEncoding)
                .addHeader("Referer", kbcxUrl)
                .addHeader("Host","202.206.245.231")
                .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/50.0.2661.75 Safari/537.36")
                .build()
                .connTimeOut(5000)
                .readTimeOut(5000)
                .execute(new StringCallback() {

                    @Override
                    public String parseNetworkResponse(Response response) throws IOException {
                        return super.parseNetworkResponse(response);
                    }

                    @Override
                    public void onError(Call call, Exception e) {
                        Toast.makeText(mContext, "课表获取失败", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response) {
                        Log.v("NO2",response);

                       try{
                           allCourseList = ParseCourses.getKB(response);
                       }
                       catch(NullPointerException e){
                           Log.v("NO2","同步失败");
                       }
                        if (allCourseList == null) {
                            Toast.makeText(mContext, "课表同步失败,可能需要评分", Toast.LENGTH_LONG).show();
                            Log.v("NO2","同步失败");}
                        else
                        {
                            CourseDao courseDao = new CourseDao(mContext);
                            courseDao.deleteAll();
                            boolean saveSucess = true;

                            if (allCourseList != null) {
                                for (CourseBean course : allCourseList) {
                                    String courseName = course.getCourseName();
                                    String courseTime = course.getCourseTime();
                                    String courstTimeDetail = course.getCourstTimeDetail();
                                    String courseTeacher = course.getCourseTeacher();
                                    String courseLocation = course.getCourseLocation();
                                    boolean isSucess = courseDao.add(courseName, courseTime, courstTimeDetail, courseTeacher, courseLocation);
                                    CourseHelper helper = new CourseHelper(mContext, 1);
                                    SQLiteDatabase db = helper.getWritableDatabase();
                                    db.close();
                                    db=null;
                                    helper=null;
                                    if (!isSucess) {
                                        saveSucess = false;
                                        Toast.makeText(mContext, "保存课表失败", Toast.LENGTH_SHORT).show();
                                        Log.v("NO1","保存课表失败");
                                        break;
                                    }
                                }
                            }
                            if (saveSucess) {

                                allCourseList = null;
                                sp.edit().putBoolean("isFirstIn",false).apply();
                            }
                            saveDataToDB();//保存数据
                        }
                    }
                });
    }




    /**
     * 获得个人信息
     */

    private void getThePersonalInformation(String piUrl) {

        OkHttpUtils.get().url(piUrl)
                .addHeader("Host",	"202.206.245.231")
                .addHeader("Referer",StuCenterUrl)
                .addHeader("User-Agent","Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0)")
                .build()
                .connTimeOut(3000)
                .execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                Log.d(WWW,"获得个人信息失败");
            }
            @Override
            public void onResponse(String response) {

                try{
                    PersonInformationUtil personInformationUtil = new PersonInformationUtil();
                    personinformationlist = personInformationUtil.get(response);
                }
                catch(NullPointerException e){
                    Log.v("NO3","同步失败");
                }
                if (personinformationlist == null) {
                    Toast.makeText(mContext, "个人信息同步失败,可能需要评分", Toast.LENGTH_LONG).show();
                    Log.v("NO3","个人同步失败");}
                else {
                    BaseInfoDao baseInfoDao = new BaseInfoDao(mContext);
                    StudentDao studentDao = new StudentDao(mContext);

                    String d=studentDao.query("stuXH");
                    Log.d("studentdao", d + "8");

                    if (d!=null) {
/*
   第二次进入
*/
                        if (!userId.equals(d)) {
                            /*
                            * 他人登录
                            * */
                            studentDao.deleteAll();
                            Log.d("studentdao", d + "他人登录");
                            studentDao.add("lbl_xb", personinformationlist.get(0));

                            studentDao.add("lbl_byzx", personinformationlist.get(2));
                            studentDao.add("lbl_mz", personinformationlist.get(3));
                            studentDao.add("lbl_zzmm", personinformationlist.get(4));
                            studentDao.add("lbl_lys", personinformationlist.get(5));
                            studentDao.add("lbl_sfzh", personinformationlist.get(6));
                            studentDao.add("lbl_xy", personinformationlist.get(7));
                            studentDao.add("lbl_zymc", personinformationlist.get(8));
                            studentDao.add("lbl_xzb", personinformationlist.get(9));
                            studentDao.add("lbl_dqszj", personinformationlist.get(10));
                            studentDao.add("lbl_xjzt", personinformationlist.get(11));
                            studentDao.add("zp", personinformationlist.get(12));
                            studentDao.add("stuXH",stuXH);

                            informationBean.setName(stuName.trim());
                            informationBean.setNumber(stuXH.trim());
                            informationBean.setGender(personinformationlist.get(0));
                            informationBean.setSchoolOfGraduation(personinformationlist.get(2));
                            informationBean.setNation(personinformationlist.get(3).trim());
                            informationBean.setPoliticsStatus(personinformationlist.get(4).trim());
                            informationBean.setHometown(personinformationlist.get(5).trim());
                            informationBean.setIdNumber(personinformationlist.get(6).trim());
                            informationBean.setCollege(personinformationlist.get(7).trim());
                            informationBean.setClasses(personinformationlist.get(9).trim());
                            informationBean.setEnrollmentYear(personinformationlist.get(10).trim());
                            informationBean.setZaiDu(personinformationlist.get(11).trim());
                            informationBean.setPicture(personinformationlist.get(12).trim());
                            BmobUser me = new BmobUser();
                            //此处应添加判断是否为HR
                  /*  BmobRole hr = new BmobRole("hr");
                    hr.getUsers().add(me);
                    hr.save();
                    BmobRole Normals=new BmobRole("Normals");
                    Normals.save();
                    BmobACL acl = new BmobACL();
                    acl.setRoleReadAccess(hr, true);
                    informationBean.setACL(acl);*/
                            informationBean.save(new SaveListener<String>() {
                                @Override
                                public void done(String s, BmobException e) {
                                    if (e == null) {
                                        Toast.makeText(mContext, "添加数据成功，返回objectId为：" + s, Toast.LENGTH_LONG).show();
                                        StudentDao studentDaos = new StudentDao(mContext);
                                        studentDaos.add("ID", s);
                                        MySQLiteOpenHelper helper = new MySQLiteOpenHelper(mContext);
                                        SQLiteDatabase db = helper.getWritableDatabase();
                                        db.close();
                                    } else {
                                        Toast.makeText(mContext, "创建数据失败：" + e.getMessage(), Toast.LENGTH_LONG).show();
                                        Log.d("qqqw", e.getMessage());
                                    }
                                }
                            });
                            BmobQuery<InformationBean> query = new BmobQuery<InformationBean>();
                            query.addWhereEqualTo("classes", personinformationlist.get(9).trim());
                            query.setLimit(50);
                            query.findObjects(new FindListener<InformationBean>() {
                                @Override
                                public void done(List<InformationBean> list, BmobException e) {
                                    if (e == null) {
                                        Log.d("ppp", "查询成功：共" + list.size() + "条数据。");
                                        for (InformationBean gameScore : list) {
                                            //获得playerName的信息
                                            gameScore.getName();
                                            //获得数据的objectId信息
                                            gameScore.getObjectId();
                                            //获得createdAt数据创建时间（注意是：createdAt，不是createAt）
                                            gameScore.getCreatedAt();
                                        }
                                    } else {
                                        Log.i("ppp", "失败：" + e.getMessage() + "," + e.getErrorCode());
                                    }
                                }
                            });
                        }else{
                            Log.d("studentdao", d + "这人第二次登录");
                        /*
                        * 这人第二次登录
                        * */
                        }

                    }else {
                        Log.d("studentdao", d + "第一次登录");
                        studentDao.deleteAll();
                        Log.d("studentdao", d + "8");
                        studentDao.add("lbl_xb", personinformationlist.get(0));

                        studentDao.add("lbl_byzx", personinformationlist.get(2));
                        studentDao.add("lbl_mz", personinformationlist.get(3));
                        studentDao.add("lbl_zzmm", personinformationlist.get(4));
                        studentDao.add("lbl_lys", personinformationlist.get(5));
                        studentDao.add("lbl_sfzh", personinformationlist.get(6));
                        studentDao.add("lbl_xy", personinformationlist.get(7));
                        studentDao.add("lbl_zymc", personinformationlist.get(8));
                        studentDao.add("lbl_xzb", personinformationlist.get(9));
                        studentDao.add("lbl_dqszj", personinformationlist.get(10));
                        studentDao.add("lbl_xjzt", personinformationlist.get(11));
                        studentDao.add("zp", personinformationlist.get(12));
                        studentDao.add("stuXH",stuXH);

                        informationBean.setName(stuName.trim());
                        informationBean.setNumber(stuXH.trim());
                        informationBean.setGender(personinformationlist.get(0));
                        informationBean.setSchoolOfGraduation(personinformationlist.get(2));
                        informationBean.setNation(personinformationlist.get(3).trim());
                        informationBean.setPoliticsStatus(personinformationlist.get(4).trim());
                        informationBean.setHometown(personinformationlist.get(5).trim());
                        informationBean.setIdNumber(personinformationlist.get(6).trim());
                        informationBean.setCollege(personinformationlist.get(7).trim());
                        informationBean.setClasses(personinformationlist.get(9).trim());
                        informationBean.setEnrollmentYear(personinformationlist.get(10).trim());
                        informationBean.setZaiDu(personinformationlist.get(11).trim());
                        informationBean.setPicture(personinformationlist.get(12).trim());
                        BmobUser me = new BmobUser();
                        //此处应添加判断是否为HR
                  /*  BmobRole hr = new BmobRole("hr");
                    hr.getUsers().add(me);
                    hr.save();
                    BmobRole Normals=new BmobRole("Normals");
                    Normals.save();
                    BmobACL acl = new BmobACL();
                    acl.setRoleReadAccess(hr, true);
                    informationBean.setACL(acl);*/
                        informationBean.save(new SaveListener<String>() {
                            @Override
                            public void done(String s, BmobException e) {
                                if (e == null) {
                                    Toast.makeText(mContext, "添加数据成功，返回objectId为：" + s, Toast.LENGTH_LONG).show();
                                    StudentDao studentDaos = new StudentDao(mContext);
                                    studentDaos.add("ID", s);
                                    MySQLiteOpenHelper helper = new MySQLiteOpenHelper(mContext);
                                    SQLiteDatabase db = helper.getWritableDatabase();
                                    db.close();
                                } else {
                                    Toast.makeText(mContext, "创建数据失败：" + e.getMessage(), Toast.LENGTH_LONG).show();
                                    Log.d("qqqw", e.getMessage());
                                }
                            }
                        });
                        BmobQuery<InformationBean> query = new BmobQuery<InformationBean>();
                        query.addWhereEqualTo("classes", personinformationlist.get(9).trim());
                        query.setLimit(50);
                        query.findObjects(new FindListener<InformationBean>() {
                            @Override
                            public void done(List<InformationBean> list, BmobException e) {
                                if (e == null) {
                                    Log.d("ppp", "查询成功：共" + list.size() + "条数据。");
                                    for (InformationBean gameScore : list) {
                                        //获得playerName的信息
                                        gameScore.getName();
                                        //获得数据的objectId信息
                                        gameScore.getObjectId();
                                        //获得createdAt数据创建时间（注意是：createdAt，不是createAt）
                                        gameScore.getCreatedAt();
                                    }
                                } else {
                                    Log.i("ppp", "失败：" + e.getMessage() + "," + e.getErrorCode());
                                }
                            }
                        });}
                }
            }
        });
    }



    /**
     * 保存数据数据库中
     */
    private void saveDataToDB() {
        //基本信息
        BaseInfoDao baseInfoDao = new BaseInfoDao(mContext);
        String stunmaefromdb=baseInfoDao.query("stuName");
        String stuxhfromdb=baseInfoDao.query("stuXH");
        if(stunmaefromdb!=null&&stuxhfromdb!=null){
            if (!stuName.equals(stunmaefromdb)&&!stuXH.equals(stuxhfromdb)){

                /*
                * 别人登录
                * */
                Log.d("WQE","别人登录");
                StudentDao studentDao=new StudentDao(mContext);
                studentDao.deleteAll();
                CourseDao courseDao=new CourseDao(mContext);
                courseDao.deleteAll();
                baseInfoDao.deleteAll();
        baseInfoDao.add("cjcxUrl", LoginActivity.cjcxUrl);
        baseInfoDao.add("kbcxUrl", LoginActivity.kbcxUrl);
        baseInfoDao.add("kscxUrl", LoginActivity.kscxUrl);
        baseInfoDao.add("StuCenterUrl", StuCenterUrl);
        baseInfoDao.add("stuName", stuName);
        baseInfoDao.add("stuXH", stuXH);
        baseInfoDao.add("noCodeLoginUrl", noCodeLoginUrl);
        baseInfoDao.add("mainUrl", mainUrl);
        baseInfoDao.add("logoutUrl", logoutUrl);
        baseInfoDao.add("password", password);
        }else {
                /*
                * 第二次登录
                * */ Log.d("WQE","第二次登录");


            }
        }else {

            /*
            * 第一次进入
            * */
            Log.d("WQE","第一次进入");
                baseInfoDao.add("cjcxUrl", LoginActivity.cjcxUrl);
                baseInfoDao.add("kbcxUrl", LoginActivity.kbcxUrl);
                baseInfoDao.add("kscxUrl", LoginActivity.kscxUrl);
                baseInfoDao.add("StuCenterUrl", StuCenterUrl);
                baseInfoDao.add("stuName", stuName);
                baseInfoDao.add("stuXH", stuXH);
                baseInfoDao.add("noCodeLoginUrl", noCodeLoginUrl);
                baseInfoDao.add("mainUrl", mainUrl);
                baseInfoDao.add("logoutUrl", logoutUrl);
                baseInfoDao.add("password", password);

        }

        BaseInfoHelper helper = new BaseInfoHelper(mContext);
        SQLiteDatabase db = helper.getWritableDatabase();
        stunmaefromdb=null;
        stuxhfromdb=null;
        db.close();
        //学生基本信息
        boolean saveSucess = true;
        //数据保存成功
        if (saveSucess) {
            sp.edit().putBoolean("isFirstIn", false).apply();
            //waitDialog.dismiss();
            Intent intent3=new Intent(mContext,TheFirstActivity.class);
            startActivity(intent3);
        }

    }

    //切换验证码
    public void changCodeImage() {
        codeUrl += '?';
        showCodeimage();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_login:
                if (password==null) {
                    Log.d("ceshia", String.valueOf(!Intenet));
                    if (!isFirstIn){
                        isFirstIn=false;
                    startActivity(new Intent(mContext,TheFirstActivity.class));
                }}
                attemptLogin();
               break;

            case R.id.tv_change:
                changCodeImage();
                break;
            case R.id.iv_codes:
                changCodeImage();break;

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RequestCall call = OkHttpUtils.get().url(kbcxUrl).build();
        call.cancel();
        call= OkHttpUtils.get().url(piUrl).build();
        call.cancel();
        call= OkHttpUtils.get().url("http://202.206.245.231/zjdxgc/default2.aspx").build();
        call.cancel();
        call= OkHttpUtils.get().url("loginUrl").build();
        call.cancel();
        call= OkHttpUtils.get().url("StuCenterUrl").build();
        call.cancel();
    }

}
