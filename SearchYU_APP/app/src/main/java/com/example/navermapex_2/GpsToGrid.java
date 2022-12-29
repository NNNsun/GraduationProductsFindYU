package com.example.navermapex_2;

import com.naver.maps.geometry.LatLng;

public class GpsToGrid {
    double longitude; //경도
    double latitude; //위도
    MainActivity mainactivity;

    public GpsToGrid(double longitude, double latitude){
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public void transformGps(){
        int div_ns = 850; //영남대 세로칸 수
        int div_ew = 850; //영남대 가로칸 수
        //points.add(new LatLng(35.838300, 128.746500));
        double i;
        double j;
        i = -(latitude + (0.016300 / div_ns / 2) - 35.838300)/(0.016300 / 850) ;
        j  = (longitude - (0.018300 / div_ew / 2) - 128.746500)/(0.018300 / 850);

        mainactivity.start_x = String.valueOf((int)i);
        mainactivity.start_y = String.valueOf((int)j);
        mainactivity.check_toast = 0;

        System.out.println(";;;;;;;;;;;;;;;;;;;;;;;;;");
        System.out.println(mainactivity.start_x);
        System.out.println(mainactivity.start_y);
        System.out.println(";;;;;;;;;;;;;;;;;;;;;;;;;");


        //테스트에서는 정문으로 설정
        //범위밖
        if(i<0||i>850||j<0||j>850) {
            mainactivity.start_x = String.valueOf(116);
            mainactivity.start_y = String.valueOf(331);
            mainactivity.check_toast = 1;

        }

        System.out.println(";;;;;;;;;;;;;;;;;;;;;;;;;");
        System.out.println(mainactivity.start_x);
        System.out.println(mainactivity.start_y);
        System.out.println(";;;;;;;;;;;;;;;;;;;;;;;;;");
    }
}
