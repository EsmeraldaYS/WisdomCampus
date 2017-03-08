package demo.ysu.com.wisdomcampus;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import demo.ysu.com.wisdomcampus.DB.CourseDao;
import demo.ysu.com.wisdomcampus.utils.WeekUtils;


public class CourseFragment extends Fragment {

    @Bind(R.id.name)
    TextView name;
    @Bind(R.id.week)
    TextView week;
    @Bind(R.id.start)
    TextView start;
    @Bind(R.id.end)
    TextView end;
    @Bind(R.id.location)
    TextView location;
    @Bind(R.id.teacher)
    TextView teacher;
    private FragmentActivity activity;
    private CourseDao courseDao;
    private CourseBean courseBean;
    private String courseName;
    private String courseLocation;
    private String courseTime;
    private String courseTeacher;
    private String courstTimeDetail;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        activity = getActivity();
        initData();

    }

    private void initData() {
        String id = activity.getIntent().getStringExtra("id");
        courseDao = new CourseDao(activity);
        courseBean = courseDao.queryById(id);
        courseName = courseBean.getCourseName();
        courseLocation = courseBean.getCourseLocation();
        courseTime = courseBean.getCourseTime();
        courseTeacher = courseBean.getCourseTeacher();
        courstTimeDetail = courseBean.getCourstTimeDetail();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.course_info_framgment, container, false);
        ButterKnife.bind(this, view);
        initUI();
        return view;
    }


    private void initUI() {
        name.setText(courseName);
        week.setText(WeekUtils.getWeek(courseTime));
        location.setText(courseLocation);
        teacher.setText(courseTeacher);
        String[] times = courstTimeDetail.split("-");
        start.setText(times[0]);
        end.setText(times[1]);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);

    }
}
