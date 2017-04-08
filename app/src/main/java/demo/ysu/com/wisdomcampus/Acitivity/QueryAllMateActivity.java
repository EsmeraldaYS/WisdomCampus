package demo.ysu.com.wisdomcampus.Acitivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import demo.ysu.com.wisdomcampus.DB.StudentDao;
import demo.ysu.com.wisdomcampus.InformationBean;
import demo.ysu.com.wisdomcampus.MyRecyclerViewAdapter;
import demo.ysu.com.wisdomcampus.R;
import demo.ysu.com.wisdomcampus.utils.DividerItemDecoration;


public class QueryAllMateActivity extends AppCompatActivity {
    private RecyclerView mrecyclerView;
    private SharedPreferences sp;
    private Context mContext = this;
    private MyRecyclerViewAdapter recycleAdapter;
    private LinearLayoutManager mlinearLayoutManager;
    private Context context = this;
    private InformationBean informationBean;
    private Handler hander;
    private EditText editText;
 private DividerItemDecoration dividerItemDecoration;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query_all_mate);


        StudentDao studentDao = new StudentDao(mContext);
        mrecyclerView = (RecyclerView) findViewById(R.id.query_name);
        mlinearLayoutManager = new LinearLayoutManager(this);
        mrecyclerView.setLayoutManager(mlinearLayoutManager);
        mlinearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        String classes = studentDao.query("lbl_xzb");
        final EditText et = new EditText(this);

        hander = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                List<String> name=new ArrayList<>();
              final List<String> obj=new ArrayList<>();
               List<InformationBean> list = (List<InformationBean>) msg.obj;
                for (InformationBean gameScore : list) {
                    //获得playerName的信息
                    gameScore.getName();
                    //获得数据的objectId信息
                    gameScore.getObjectId();
                    //获得createdAt数据创建时间（注意是：createdAt，不是createAt）
                    gameScore.getCreatedAt();
                    //设置Adapter
                    name.add(  gameScore.getName());
                    obj.add( gameScore.getObjectId());
                    Log.d("owo",gameScore.getObjectId());

                }

                mrecyclerView.addItemDecoration(new DividerItemDecoration(context, dividerItemDecoration.VERTICAL_LIST));
                recycleAdapter = new MyRecyclerViewAdapter(context, name);
                mrecyclerView.setAdapter(recycleAdapter);
                recycleAdapter.setOnItemClickLitener(new MyRecyclerViewAdapter.OnItemClickLitener()
                {
                    @Override
                    public void onItemClick(View view, final int position)
                    {
                      final  LinearLayout dialogLayout=(LinearLayout) getLayoutInflater().inflate(R.layout.dialogitem,null);
                        new AlertDialog.Builder(context)
                                .setIcon(R.mipmap.splash_background480)
                                .setTitle("密码")
                                .setView(dialogLayout)
                                .setPositiveButton("登录", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                et.setText(obj.get(position));
                                Intent intent=new Intent(context,StudentInformationActivity.class);
                                intent.putExtra("ed",et.getText().toString().trim());
                                startActivity(intent);
                             }
                        }).show();
                    }

                    @Override
                    public void onItemLongClick(View view, int position)
                    {
                        Toast.makeText(context, position + " long click",
                                Toast.LENGTH_SHORT).show();

                    }
                });
            }
        };
        BmobQuery<InformationBean> query = new BmobQuery<InformationBean>();
        query.addWhereEqualTo("classes", classes.trim());
        query.setLimit(50);
        query.findObjects(new FindListener<InformationBean>() {
            @Override
            public void done(List<InformationBean> list, BmobException e) {

                if (e == null) {
                    Log.d("ppp", "查询成功：共" + list.size() + "条数据。");
                    Message message = new Message();
                    message.obj = list;
                    hander.sendMessage(message);
                    for (InformationBean gameScore : list) {
                        gameScore.getName();
                        gameScore.getObjectId();
                        gameScore.getCreatedAt();
                    }
                } else {
                    Log.i("ppp", "失败：" + e.getMessage() + "," + e.getErrorCode());
                }
            }
        });
    }
}

