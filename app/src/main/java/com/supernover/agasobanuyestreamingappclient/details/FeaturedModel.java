package com.supernover.agasobanuyestreamingappclient.details;

public class FeaturedModel {
    private String Fcast;
    private String Fcover;
    private String Fdesc;
    private String Flink;
    private String Frating;
    private String Fstudio;
    private String Fthumb;
    private String Ftitle;
    private String Tlink;

    public FeaturedModel() {
    }

    public FeaturedModel(String ftitle, String fdesc, String fthumb, String fstudio, String frating, String flink, String tlink, String fcover, String fcast) {
        this.Ftitle = ftitle;
        this.Fdesc = fdesc;
        this.Fthumb = fthumb;
        this.Fstudio = fstudio;
        this.Frating = frating;
        this.Flink = flink;
        this.Tlink = tlink;
        this.Fcover = fcover;
        this.Fcast = fcast;
    }

    public String getFtitle() {
        return this.Ftitle;
    }

    public void setFtitle(String ftitle) {
        this.Ftitle = ftitle;
    }

    public String getFdesc() {
        return this.Fdesc;
    }

    public void setFdesc(String fdesc) {
        this.Fdesc = fdesc;
    }

    public String getFthumb() {
        return this.Fthumb;
    }

    public void setFthumb(String fthumb) {
        this.Fthumb = fthumb;
    }

    public String getFstudio() {
        return this.Fstudio;
    }

    public void setFstudio(String fstudio) {
        this.Fstudio = fstudio;
    }

    public String getFrating() {
        return this.Frating;
    }

    public void setFrating(String frating) {
        this.Frating = frating;
    }

    public String getFlink() {
        return this.Flink;
    }

    public void setFlink(String flink) {
        this.Flink = flink;
    }

    public String getTlink() {
        return this.Tlink;
    }

    public void setTlink(String tlink) {
        this.Tlink = tlink;
    }

    public String getFcover() {
        return this.Fcover;
    }

    public void setFcover(String fcover) {
        this.Fcover = fcover;
    }

    public String getFcast() {
        return this.Fcast;
    }

    public void setFcast(String fcast) {
        this.Fcast = fcast;
    }
}
