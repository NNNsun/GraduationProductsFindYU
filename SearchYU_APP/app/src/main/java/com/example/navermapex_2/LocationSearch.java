package com.example.navermapex_2;

import javax.xml.transform.Result;

public class LocationSearch {
    int[] location = new int[2];
    String userinput;
    public LocationSearch(String inputdata){
        this.userinput = inputdata;
    }

    public int[] Location(){
        //중앙도서관
        if(userinput.equals("중도") || userinput.equals("중도") || userinput.equals("LIB")){
            location[0] = 534;
            location[1] = 268;
            ResultActivity.titleData = "[B04] 중앙도서관";
        }

        //중앙도서관
        else if(userinput.equals("정행대") || userinput.equals("정치외교") || userinput.equals("JH")
        || userinput.equals("정치외교학과")){
            location[0] = 463;
            location[1] = 241;
            ResultActivity.titleData = "[B05] 정치행정대학";
        }

        else if(userinput.equals("test1") || userinput.equals("정문") || userinput.equals("3번출구")){
            location[0] = 331;
            location[1] = 116;
        }

        else if(userinput.equals("test2") || userinput.equals("중도입구")){
            location[0] = 468;
            location[1] = 243;
        }

        //조건에 걸리지 않는 것
        else{
            location[0] = 0;
            location[1] = 0;
        }
        return location;
    }
}
