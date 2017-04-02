package demo.ysu.com.wisdomcampus.utils;

/**
 * Created by Administrator on 2017/3/6.
 */

public class linksbean {
    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    private String link;
    private String src;

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    private String head;

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    @Override
    public String toString() {
        return "linksbean{" +
                "src='" + src + '\'' +
                ", head='" + head + '\'' +
                '}';
    }
}
