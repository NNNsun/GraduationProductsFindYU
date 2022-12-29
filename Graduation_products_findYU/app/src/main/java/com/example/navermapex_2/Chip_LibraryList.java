package com.example.navermapex_2;

import com.naver.maps.geometry.LatLng;

import java.util.ArrayList;

public class Chip_LibraryList {
    private ArrayList<LatLng> list = new ArrayList<>();
    private Boolean check;

    public Chip_LibraryList() {
        this.check = false;
        list.add(new LatLng(35.83318121315972,128.75760899341367));//중도
        list.add(new LatLng(35.82900673171905,128.75695020880596));//이도
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
