package demo.ysu.com.wisdomcampus.utils;

import android.util.Log;

/**
 * Created by Administrator on 2017/2/18.
 */

public class logd {

    public static void showLog(String responseInfo) {

        if (responseInfo.length() > 4000) {
                                 Log.v("str", "sb.length = " + responseInfo.length());
                                 int chunkCount = responseInfo.length() / 4000;     // integer division
                               for (int i = 0; i <= chunkCount; i++) {
                                         int max = 4000 * (i + 1);
                                   if (max >= responseInfo.length()) {
                                             Log.v("str", "chunk " + i + " of " + chunkCount + ":" + responseInfo.substring(4000 * i));
                                   } else {
                                         Log.v("str", "chunk " + i + " of " + chunkCount + ":" + responseInfo.substring(4000 * i, max));
                                }
                                  }
                    } else {
                          Log.v("str", responseInfo.toString());
                      }
}}
