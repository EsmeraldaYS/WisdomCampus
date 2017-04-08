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
        String text=response.substring(response.indexOf("__VIEWSTATE")+20,response.indexOf("__VIEWSTATE")+1776+20);
        Log.d("VIEWSTATE2","WWW"+text);
        return  text;

    }



    public String getVIEWSTATE(){
        String text=response.substring(response.indexOf("__VIEWSTATE")+20,response.indexOf("__VIEWSTATE")+20+48);
        Log.d(TAG,text);
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
                    case 1:
                        bean.setCourseXQ(tds.get(j).text());
                        break;
                    case 2:
                        bean.setCourseId(tds.get(j).text());
                        break;
                    case 3:
                        bean.setCourseName(tds.get(j).text());
                        break;
                    case 4:
                        bean.setCourseXz(tds.get(j).text());
                        break;
                    case 5:
                        bean.setCourseGs(tds.get(j).text()+"");
                        break;
                    case 6:
                        bean.setCourseXf(tds.get(j).text());
                        break;
                    case 7:
                        bean.setCourseCj(tds.get(j).text());
                        break;
                    case 8:
                        bean.setCourseBj(tds.get(j).text());
                        break;
                    case 9:
                        bean.setCourseBk(tds.get(j).text());
                        break;
                    case 10:
                        bean.setCourseCx(tds.get(j).text());
                        break;
                    case 11:
                        bean.setCourseXY(tds.get(j).text());
                        break;
                    case 12:
                        bean.setCourseBZ(tds.get(j).text());
                        break;
                    case 13:
                        bean.setCourseXW(tds.get(j).text());
                        break;
                    case 14:
                        bean.setCourseBJ(tds.get(j).text());
                        break;
                    default:break;
                }
            }
            scoreList.add(bean);
        }
        return scoreList;
    }

}
