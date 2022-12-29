package com.example.navermapex_2;

import com.naver.maps.geometry.LatLng;

import java.util.ArrayList;

public class Chip_CafeList {
    private ArrayList<LatLng> list = new ArrayList<>();
    private Boolean check;

    public Chip_CafeList() {
        this.check = false;
        list.add(new LatLng(35.83185999589793, 128.75324217493795));//아트스벅
        list.add(new LatLng(35.83316274131151, 128.7580081620249));//중앙스벅
        list.add(new LatLng(35.832528154677604, 128.75751260325626));//지오카페
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
