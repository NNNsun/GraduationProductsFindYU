package com.example.navermapex_2;

import com.naver.maps.geometry.LatLng;

import java.util.ArrayList;

public class Chip_RestroomList {
    private ArrayList<LatLng> list = new ArrayList<>();
    private Boolean check;

    public Chip_RestroomList() {
        this.check = false;
        list.add(new LatLng(35.83685735886198, 128.7545924428146));//대구은행
        list.add(new LatLng(35.83651397060139, 128.75646778964966));//박물관
        list.add(new LatLng(35.83550827772912, 128.75633529441535));//학생지원
        list.add(new LatLng(35.8342579866046, 128.75546845718878));//노천강당
        list.add(new LatLng(35.83437600263862, 128.75604766479086));//이건희
        list.add(new LatLng(35.83506935519952, 128.7573960765435));//디자인관
        list.add(new LatLng(35.83482867712222, 128.7584893819586));//미술관
        list.add(new LatLng(35.83440161690518, 128.75854651526822));//사범대
        list.add(new LatLng(35.83429543964252, 128.7565245168085));//학생화관
        list.add(new LatLng(35.83350571838494, 128.7566692521698));//정치행정
        list.add(new LatLng(35.83300703162817, 128.75848932984286));//중도
        list.add(new LatLng(35.83283868825299, 128.75540906177542));//상경관
        list.add(new LatLng(35.83157307221238, 128.75818523461908));//인문관
        list.add(new LatLng(35.83195172876246, 128.7529116589227));//천마아트
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
