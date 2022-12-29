package com.example.navermapex_2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.PendingDynamicLinkData;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.geometry.LatLngBounds;
import com.naver.maps.map.CameraPosition;
import com.naver.maps.map.MapView;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.UiSettings;
import com.naver.maps.map.overlay.GroundOverlay;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.overlay.OverlayImage;
import com.naver.maps.map.overlay.PathOverlay;

import java.util.ArrayList;
import java.util.List;

public class ResultActivity extends AppCompatActivity implements OnMapReadyCallback {

    private MapView mapView;
    private ImageButton prev_btn, exit_btn, dst_btn;
    private Button search;
    private UiSettings uiSettings;
    private String user_input; //searchFragment에서 사용자가 입력한 값
    private int[] place; // json에서 읽어온 좌표
    private JsonConverter jc;
    private double[] cord_deeplink = new double[2]; //딥링크로 받은 현재위치 위도,경도

    private double latitude = 35.83609; // 위도
    private double longitude = 128.75290; // 경도
    private int defaultZoom = 16; // 줌
    private int defaultTilt = 0; // 기울기
    private int defaultBearing = 0; // 베어링(회전)

    private CameraPosition cameraPosition;
    private Marker marker;
    private String path; //json 파일 경로
    private boolean isDeepLink = false; // deeplink인지 아닌지 판별
    private static final String TAG = "ResultActivity"; //Logcat Tag
    private String strlat, strlon; //test
    private TextView title;
    public static String titleData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //액션바 제거
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        setContentView(R.layout.activity_result);

        search = findViewById(R.id.search_bar);
        prev_btn = findViewById(R.id.prev_button);
        exit_btn = findViewById(R.id.exit_button);
        dst_btn = findViewById(R.id.dst_button);
        title = findViewById(R.id.title);

        // 네이버 지도 관련 설정
        mapView = findViewById(R.id.map_view);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        Intent connect = getIntent();

        handleDeepLink(); // 딥링크 수신

        if (isDeepLink == false) {
            //이전화면(SearchActivity)에서 유저입력값 받아오기
            user_input = connect.getStringExtra("user_input");
            if (user_input != null) {search.setText(user_input);} //유저 입력결과 위에 표시

            // json에서 검색지 좌표 읽어오기
//            path = "local/test.json";
//            jc = new JsonConverter(ResultActivity.this, path);
//            place = jc.strToArray();
        }
        else {
            search.setText("공유위치");
            title.setText("상대방의 위치");
        }

        // 유저가 검색결과창에서 다시 검색한 경우
        if(connect.getStringExtra("new_user_input") != null) {
            String new_user_input = connect.getStringExtra("new_user_input");
            search.setText(new_user_input); //유저가 다시 검색한 결과를 검색창에 표시
            user_input = new_user_input; //새로검색되도 올바른작동
            //새로 검색한 위치에 대한 좌표 받아오기
//            path = "local/test3.json";
//            jc = new JsonConverter(ResultActivity.this, path);
//            place = jc.strToArray();
        }

        // 뒤로가기 버튼 눌렀을 때
        prev_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 이전화면으로 이동
                finish(); //디바이스 뒤로가기 버튼으로 못돌아오게하기
            }
        });

        // X버튼 눌렀을 때
        exit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 홈화면으로 이동
                Intent intent = new Intent(ResultActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); //Main액티비티위에 있던 액티비티들 모두 삭제
                startActivity(intent);
                finish();
            }
        });
        // 검색바 눌렀을 때
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 간단한 검색화면으로 이동
                Intent intent = new Intent(ResultActivity.this, ModifyActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                finish(); //새로운 검색결과창 띄워주기위해 현재 액티비티 종료

            }
        });
        // 목적지 설정 버튼 눌렀을 때
        dst_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //경로탐색 결과화면으로 이동
                //검색한 장소의 좌표 넘겨야함.(place 배열 전달할 것)
                Intent intent = new Intent(ResultActivity.this, RoadResultActivity.class);
                intent.putExtra("result_coordinate", place);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
            }
        });


    }

    // 딥링크 수신
    private void handleDeepLink() {
        FirebaseDynamicLinks.getInstance()
                .getDynamicLink(getIntent())
                .addOnSuccessListener(this, new OnSuccessListener<PendingDynamicLinkData>() {
                    @Override
                    public void onSuccess(PendingDynamicLinkData pendingDynamicLinkData) {
                        // Get deep link from result (may be null if no link is found)
                        Uri deepLink = null;
                        try {
                            if (pendingDynamicLinkData != null) {
                                deepLink = pendingDynamicLinkData.getLink();
                                Log.d(TAG, deepLink.toString()); // debug
                                isDeepLink = true;

                                strlat = deepLink.getQueryParameter("lat");
                                strlon = deepLink.getQueryParameter("lon");

                                double tmp = Double.parseDouble(strlat);
                                //cord_deeplink = new double[2];
                                cord_deeplink[0] = tmp;
                                tmp = Double.parseDouble(strlon);
                                cord_deeplink[1] = tmp;
                            }
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "getDynamicLink:onFailure", e);
                    }
                });
    }

    // NaverMap 객체가 준비되면 호출되는 메소드
    @Override
    public void onMapReady (@NonNull NaverMap naverMap){
        //상경대 이미지추가
        GroundOverlay groundOverlay = new GroundOverlay();
        groundOverlay.setBounds(new LatLngBounds(
                new LatLng(35.830330, 128.751790), new LatLng(35.838070, 128.760270)));
        groundOverlay.setImage(
                OverlayImage.fromResource(R.drawable.finmap));
        groundOverlay.setAlpha(1); //투명도
        groundOverlay.setZIndex(1);
        groundOverlay.setMap(naverMap);

        // 네이버 지도 관련 ui 설정
        uiSettings = naverMap.getUiSettings();
        uiSettings.setCompassEnabled(false); // 좌측 상단 나침반 제거
        uiSettings.setTiltGesturesEnabled(false); // 틸트 제스처 해제
        uiSettings.setRotateGesturesEnabled(true); // 회전 제스처 false -> true 변경
        uiSettings.setLocationButtonEnabled(false); // 기본 로케이션 버튼 해제

        // 카메라 영역 범위 제한 : 영남대에서 테스트할때 주석 해제
        //LatLng southWest = new LatLng(35.822000, 128.746500); //서남단
        //LatLng northEast = new LatLng(35.838300, 128.764800); //동북단
        //naverMap.setExtent(new LatLngBounds(southWest, northEast));

        //zoom 범위 제한
        naverMap.setMinZoom(14.0); //최소
        naverMap.setMaxZoom(19.0); //최대

        //격자 그리기
        int div_ns = 850; //영남대 세로칸 수
        int div_ew = 850; //영남대 가로칸 수

        if(isDeepLink) { //좌표를 딥링크로 받은 경우
            LatLng yuCord = (new LatLng(cord_deeplink[0], cord_deeplink[1]));
            marker = new Marker();
            marker.setPosition(yuCord);
            marker.setIcon(OverlayImage.fromResource(R.drawable.marker_college));
            marker.setMap(naverMap); // 좌표 상 장소를 마커로 표시

            // 마커가 중앙에 오게 카메라 위치 조절
            cameraPosition = new CameraPosition(
                    yuCord,
                    16,
                    defaultTilt,
                    defaultBearing
            );
            naverMap.setCameraPosition(cameraPosition);

        }
        else { // 좌표를 검색해서 받은 경우
            //(0,0)좌표의 위도경도
            double startfindy = 35.822000 + 0.016300 - (0.016300/div_ns/2);
            double startfindx = 128.746500 + (0.018300/div_ew/2);

            //검색어에 해당하는 좌표 탐색
            LocationSearch LS = new LocationSearch(user_input);
            place = LS.Location();

            if (place[0]!=0 && place[1]!=0){
                LatLng yuCord = (new LatLng(startfindy - place[1]*0.016300/div_ns, startfindx + place[0]*0.018300/div_ew));
                marker = new Marker();
                marker.setPosition(yuCord);
                marker.setIcon(OverlayImage.fromResource(R.drawable.marker_college));
                marker.setMap(naverMap); // 좌표 상 장소를 마커로 표시

                title.setText(titleData);
                System.out.println(titleData);

                // 마커가 중앙에 오게 카메라 위치 조절
                cameraPosition = new CameraPosition(
                        yuCord,
                        defaultZoom,
                        defaultTilt,
                        defaultBearing
                );
                naverMap.setCameraPosition(cameraPosition);
                dst_btn.setEnabled(true); //목적지 검색 활성화
            }

            else{
                dst_btn.setEnabled(false); //목적지 못찾으면 버튼 비활성화
                //에러 토스트메시지
                Toast.makeText(ResultActivity.this, "목적지 재검색 요구", Toast.LENGTH_SHORT).show();
                //영남대 한가운대
                LatLng default_location = (new LatLng(startfindy - 425*0.016300/div_ns, startfindx + 425*0.018300/div_ew));

                // 마커가 중앙에 오게 카메라 위치 조절
                cameraPosition = new CameraPosition(
                        default_location,
                        defaultZoom,
                        defaultTilt,
                        defaultBearing
                );
                naverMap.setCameraPosition(cameraPosition);
            }
        }

    }

    //MapView 라이프사이클 메서드
    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

}