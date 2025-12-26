package com.flower.pojo;
import java.util.Date;
public class Announcement {
    private int annId;
    private String title;
    private String content;
    private Date publishTime;

    public Announcement() {}

    public int getAnnId() {return annId;}
    public void setAnnId(int annId) {this.annId = annId;}
    public String getTitle() {return title;}
    public void setTitle(String title) {this.title = title;}
    public String getContent() {return content;}
    public void setContent(String content) {this.content = content;}
    public Date getPublishTime() {return publishTime;}
    public void setPublishTime(Date publishTime) {this.publishTime = publishTime;}
}