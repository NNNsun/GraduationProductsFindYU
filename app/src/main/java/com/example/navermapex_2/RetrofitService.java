package com.example.navermapex_2;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface RetrofitService {
    // 형식입력부분 (POST로 하려했으나 사용자가 데이터를 건들일 방법이 없으며 임의로 브라우저에 접속하여 데이터를 건들인다고 해도
    // 보안상의 문제가 없기에 POST보다 전송 속도가 빠른 GET으로 설정함
    @GET("mazeget")
    Call<MazeResult> getPosts(@QueryMap Map<String,String> querys);
}
