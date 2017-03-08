package demo.ysu.com.wisdomcampus.utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;


/**
 * Created by Administrator on 2017/2/11.
 */

public class PersonInformationUtil {

    private  String response;



    public ArrayList<String> get(String response){

        this.response=response;
        ArrayList<String> PersonInformationList=new ArrayList();
        Document document= Jsoup.parse(response);
        PersonInformationList.add(document.getElementById("lbl_xb").text());
        PersonInformationList.add(document.getElementById("lbl_TELNUMBER").text());
        PersonInformationList.add(document.getElementById("lbl_byzx").text());
        PersonInformationList.add(document.getElementById("lbl_mz").text());
        PersonInformationList.add(document.getElementById("lbl_zzmm").text());
        PersonInformationList.add(document.getElementById("lbl_lys").text());
        PersonInformationList.add(document.getElementById("lbl_sfzh").text());
        PersonInformationList.add(document.getElementById("lbl_xy").text());
        PersonInformationList.add(document.getElementById("lbl_zymc").text());
        PersonInformationList.add(document.getElementById("lbl_xzb").text());
        PersonInformationList.add(document.getElementById("lbl_dqszj").text());
        PersonInformationList.add(document.getElementById("lbl_xjzt").text());
        Elements media= document.select("[src]");
        PersonInformationList.add(media.last().attr("src"));


        return PersonInformationList;
    }


}
