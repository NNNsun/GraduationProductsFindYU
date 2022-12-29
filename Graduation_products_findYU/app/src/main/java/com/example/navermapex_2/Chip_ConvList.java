package com.example.navermapex_2;

import com.naver.maps.geometry.LatLng;

import java.util.ArrayList;

public class Chip_ConvList {
    private ArrayList<LatLng> list = new ArrayList<>();
    private Boolean check;

    public Chip_ConvList() {
        this.check = false;
        list.add(new LatLng(35.83413562696157, 128.75700655566453));//학생회관 gs
        list.add(new LatLng(35.83267452612796, 128.7557005108235));//상경세븐
        list.add(new LatLng(35.82833133102383,128.756483550288));//이도세븐
        list.add(new LatLng(35.83008635397924,128.7578140316764));//과대세븐
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
