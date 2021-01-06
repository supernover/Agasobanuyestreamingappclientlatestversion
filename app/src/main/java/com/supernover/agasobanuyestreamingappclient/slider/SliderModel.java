package com.supernover.agasobanuyestreamingappclient.slider;

public class SliderModel {
    private String Ttitle;
    private String Turl;
    private String Tvid;

    public SliderModel(String ttitle, String turl, String tvid) {
        this.Ttitle = ttitle;
        this.Turl = turl;
        this.Tvid = tvid;
    }

    public SliderModel() {
    }

    public String getTtitle() {
        return this.Ttitle;
    }

    public void setTtitle(String ttitle) {
        this.Ttitle = ttitle;
    }

    public String getTurl() {
        return this.Turl;
    }

    public void setTurl(String turl) {
        this.Turl = turl;
    }

    public String getTvid() {
        return this.Tvid;
    }

    public void setTvid(String tvid) {
        this.Tvid = tvid;
    }
}
