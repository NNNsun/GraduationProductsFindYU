package com.example.navermapex_2;

import com.naver.maps.geometry.LatLng;

import java.util.ArrayList;

public class Chip_PressList {
    private ArrayList<LatLng> list = new ArrayList<>();
    private Boolean check;
    public Chip_PressList() {
        this.check = false;
        list.add(new LatLng(35.83288399033157, 128.7553896426345));//상경인쇄
        list.add(new LatLng(35.832170823087054, 128.75873551415077));//인문인쇄
        list.add(new LatLng(35.83315460691409, 128.75757731282533));//중도인쇄
        list.add(new LatLng(35.83418078195311, 128.7591042490459));//사범인쇄
        list.add(new LatLng(35.82993639458982, 128.7543306042308));//전기인쇄
        list.add(new LatLng(35.82626745069656,128.75397628628974));//기계인쇄
        list.add(new LatLng(35.828992014150074, 128.75692951408342));//이도인쇄
    }
    public void setCheck(Boolean check) {
        this.check = check;
    }
    public Boolean getCheck() {
        return check;
    }
    public ArrayList<LatLng> getList() {
        return list;
    }
}
