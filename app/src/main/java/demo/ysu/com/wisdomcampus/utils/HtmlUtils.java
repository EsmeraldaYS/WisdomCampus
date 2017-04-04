package demo.ysu.com.wisdomcampus.utils;


import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import demo.ysu.com.wisdomcampus.ScoreBean;

import static android.content.ContentValues.TAG;

/**
 * 对url中的中文编码
 *
 * @author Hugo
 * Created on 2016/4/21 12:01.
 */


public class HtmlUtils {
    private String response;

    public HtmlUtils(String response) {
        this.response = response;
    }


    public String getXhandName() {
        Document document = Jsoup.parse(response);
        Element xhxm = document.getElementById("xhxm");
        if (xhxm == null) {
            Log.d(TAG,"xhxm为空");
        }
        String text = xhxm.text();
        return text;
    }

    public String getVIEWSTATE2(){
        Log.d("TAG100","WWW"+response);
        String text=response.substring(response.indexOf("__VIEWSTATE")+20,response.indexOf("__VIEWSTATE")+1776+20);
        Log.d("TAG100","WWW"+text);
        return  text;

    }



    public String getVIEWSTATE(){
        String text=response.substring(response.indexOf("__VIEWSTATE")+20,response.indexOf("__VIEWSTATE")+20+48);
        Log.d(TAG,text);
        return  text;
    }
    public String getVIEWSTATEGENERATOR(){
        String text=response.substring(response.indexOf("__VIEWSTATEGENERATOR")+30,response.indexOf("__VIEWSTATEGENERATOR")+30+15);
        Log.d("__VIEWSTATEGENERATOR",text);
        return  text;
    }
   public List<ScoreBean> parseScore() {
        List<ScoreBean> scoreList = new ArrayList<>();
        Document document = Jsoup.parse(response);
        Element dataGrid1 = document.getElementById("Datagrid1");
        Elements trs = dataGrid1.select("tr");
        for (int i = 1; i < trs.size(); i++) {
            ScoreBean bean = new ScoreBean();
            Elements tds = trs.get(i).select("td");
            for (int j = 0; j < tds.size(); j++) {
                switch (j) {
                    case 2:
                        bean.setCourseId(tds.get(j).text());
                        break;
                    case 3:
                        bean.setCourseName(tds.get(j).text());
                        break;
                    case 4:
                        bean.setCourseXz(tds.get(j).text());
                        break;
                    case 7:
                        bean.setCourseCj(tds.get(j).text());
                        break;
                    case 5:
                        bean.setCourseGs(tds.get(j).text());
                        break;
                    case 9:
                        bean.setCourseBk(tds.get(j).text());
                        break;
                    case 10:
                        bean.setCourseCx(tds.get(j).text());
                        break;
                    case 6:
                        bean.setCourseXf(tds.get(j).text());
                        break;
                    case 8:
                        bean.setCourseBj(tds.get(j).text());
                        break;
                    default:break;
                }
            }
            scoreList.add(bean);
        }
        return scoreList;
    }

    /**
     * 返回成绩查询页面的年份集合
     *
     * @return
     */
    public List<String> parseSelectYearList() {
        Document document = Jsoup.parse(response);
        Element select = document.getElementById("ddlXN");
        Elements options = select.select("option");
        List<String> tempList = new ArrayList<>();
        List<String> yearList = new ArrayList<>();
        for (Element option : options) {
            tempList.add(option.text());
        }
        for (int j = tempList.size() - 1; j > 0; j--) {
            yearList.add(tempList.get(j));
        }
        return yearList;
    }

}
