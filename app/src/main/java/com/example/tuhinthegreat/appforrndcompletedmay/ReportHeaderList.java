package com.example.tuhinthegreat.appforrndcompletedmay;

/**
 * Created by TUHIN THE GREAT on 5/10/2017.
 */

public class ReportHeaderList {
    String date,particular,amount,type;

    public ReportHeaderList(String date, String particular, String amount, String type) {
        this.date = date;
        this.particular = particular;
        this.amount = amount;
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getParticular() {
        return particular;
    }

    public void setParticular(String particular) {
        this.particular = particular;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
