package com.supernover.agasobanuyestreamingappclient.details;

public class PartModel {
    String part;
    String url;
    String vidUrl;

    public PartModel() {
    }

    public PartModel(String part2, String url2, String vidUrl2) {
        this.part = part2;
        this.url = url2;
        this.vidUrl = vidUrl2;
    }

    public String getPart() {
        return this.part;
    }

    public void setPart(String part2) {
        this.part = part2;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url2) {
        this.url = url2;
    }

    public String getVidUrl() {
        return this.vidUrl;
    }

    public void setVidUrl(String vidUrl2) {
        this.vidUrl = vidUrl2;
    }
}
