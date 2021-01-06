package com.supernover.agasobanuyestreamingappclient.details;

public class CastModel {
    private String cname;
    private String curl;

    public CastModel() {
    }

    public CastModel(String cname2, String curl2) {
        this.cname = cname2;
        this.curl = curl2;
    }

    public String getCname() {
        return this.cname;
    }

    public void setCname(String cname2) {
        this.cname = cname2;
    }

    public String getCurl() {
        return this.curl;
    }

    public void setCurl(String curl2) {
        this.curl = curl2;
    }
}
