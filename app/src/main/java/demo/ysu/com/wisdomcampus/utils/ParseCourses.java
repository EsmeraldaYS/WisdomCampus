package demo.ysu.com.wisdomcampus.utils;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import demo.ysu.com.wisdomcampus.CourseBean;


/**
 * 解析课表，保存到List中
 *
 * @author Hugo
 * Created on 2016/4/23 8:56.
 */
public class ParseCourses {

    private static List<CourseBean> courseList = new ArrayList<>();

    public static List<CourseBean> getKB(String response) {
        response = response.replace("<br>", "hu");
        Document document = Jsoup.parse(response);
        Element table1 = document.getElementById("Table1");
        Log.v("TAG",table1.toString());
        /*table1.child(0).child(0).remove();
        table1.child(0).child(0).remove();
        table1.child(0).child(0).child(0).remove();
        table1.child(0).child(4).child(0).remove();
        table1.child(0).child(8).child(0).remove();*/


        //Element tbody=table1.select("tr").get(0);
        //拿到tbody
        Element tbody = table1.select("tbody").get(0);
        Log.v("TAG2",tbody.toString());
        //Elements trss=document.select("tr");

       // Element tbody=trss.get(0);
        //去除前面两行 ，时间和早晨
        tbody.child(0).remove();
        tbody.child(0).remove();
        //去除上午，下午，晚上
        tbody.child(0).child(0).remove();
        tbody.child(4).child(0).remove();
        tbody.child(8).child(0).remove();
        Log.v("TAG3",tbody.toString());
        //去除无用行之后，还剩余
        Elements trs = table1.select("tr");
        int rowNum = trs.size();
        //用map储存数据
        for (int i = 0; i < rowNum; i++) {
            if (i % 2 == 0) {
                //拿到每一行
                Element tr = trs.get(i);
                //一共有多少列 -2 晚自习有三节课，手动去掉最后一行，即第十一节课
                int columnNum = tr.childNodeSize() - 2;
                for (int j = 1; j < columnNum - 1; j++) {

                    String timeDetail = null;
                    switch (i) {
                        case 0:
                            timeDetail = "8:30-10:10";
                            break;
                        case 2:
                            timeDetail = "10:20-12:00";
                            break;
                        case 4:
                            timeDetail = "13:20-15:10";
                            break;
                        case 6:
                            timeDetail = "15:20-17:00";
                            break;
                        case 8:
                            timeDetail = "18:30-20:40";
                    }

                    Element colum = tr.child(j);
                    Log.v("TAG4",colum.toString());
                    if (colum.hasAttr("rowspan")) {
                        CourseBean course = new CourseBean();
                        String text = colum.text();
                        Log.v("TAG5",text);
                        //基于java的web开发(JSP/Sevlet) 周三第5,6节{第1-16周} 尹红丽 1号公教楼602
                        String[] strings = text.split("hu");
                        Log.v("TAG6",strings[0]+strings[4]);
                        String name = "";
                        try {
                            if (strings.length > 4) {
                                name = strings[0] + "-" + strings[2].substring(strings[2].indexOf("{") + 1, strings[2].indexOf("}"));

                            } else {
                                name = strings[0];
                              /*  if (strings[1].contains("单周")) {
                                    name = name + " -单周";
                                } else if (strings[1].contains("双周")) {
                                    name = name + " -双周";
                                }*/
                            }
                        } catch (ArrayIndexOutOfBoundsException e) {
                            course.setCourseName("暂无");
                        }
                        course.setCourseName(name);
                        course.setCourseTime(String.valueOf(j));
                        course.setCourstTimeDetail(timeDetail);
                        /**
                         * 有的课程不规范，缺少信息，导致解析错误，程序崩溃
                         */
                        String teacher = "";
                        try {
                            teacher = strings[3];
                        } catch (ArrayIndexOutOfBoundsException e) {
                            teacher = "暂无";
                        }
                        String location = "";
                        try {
                            location = strings[4];
                        } catch (ArrayIndexOutOfBoundsException e) {
                            location = "暂无";
                        }
                        course.setCourseTeacher(teacher);
                        course.setCourseLocation(location);
                        courseList.add(course);
                    }
                }
            }
        }
        for (CourseBean course : courseList) {
            Log.d("AAA",course.getCourseName());
            String courseTime = course.getCourseTime();
            String courstTimeDetail = course.getCourstTimeDetail();
            String courseTeacher = course.getCourseTeacher();
            String courseLocation = course.getCourseLocation();
           }
        return courseList;
    }

}