package demo.ysu.com.wisdomcampus.utils;

/**
 * @author Hugo
 * Created on 2016/5/19 19:02.
 */
public class UpdateDataEvent {
    private String msg;

    public UpdateDataEvent(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}
