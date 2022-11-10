package com.example.navermapex_2;

import java.lang.reflect.Array;
import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.PointF;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
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
import com.naver.maps.map.util.FusedLocationSource;
import com.naver.maps.map.widget.LocationButtonView;

public class MapFragment extends Fragment implements OnMapReadyCallback {

    private View view;
    private MapView mapView;
    private UiSettings uiSettings;

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1000;
    private FusedLocationSource locationSource;

    private double latitude = 35.83609; // 위도
    private double longitude = 128.75290; // 경도
    private int defaultZoom = 14; // 줌
    private int defaultTilt = 0; // 기울기
    private int defaultBearing = 150; // 베어링(회전)


    private MainActivity mactivity;
    private Button mainSearchBtn;
    private ImageButton imageBtn, roadBtn;
    private NaverMap naverMap;
    private CameraPosition cameraPosition;

    private Marker marker = new Marker();


    // Chip list
    private ArrayList<Marker> markerList = new ArrayList<>();
    private Chip_CafeList cafeList = new Chip_CafeList();
    private Chip_RestroomList restList = new Chip_RestroomList();
    private Chip_ConvList convList = new Chip_ConvList();
    private Chip_PressList pressList = new Chip_PressList();
    private Chip_LibraryList libraryList = new Chip_LibraryList();

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mactivity = (MainActivity) getActivity();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mactivity = null;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_map, container, false);

        // 네이버 지도 관련 설정
        mapView = view.findViewById(R.id.map_view);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        locationSource = new FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE);


        //장소 검색, 이미지 검색, 길찾기
        mainSearchBtn = view.findViewById(R.id.searchPlace);
        imageBtn = view.findViewById(R.id.searchImage);
        roadBtn = view.findViewById(R.id.searchRoad);


        // 검색창 클릭 리스너
        mainSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mactivity, SearchActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
            }
        });

        // 사진 검색 클릭 리스너
        imageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCameraSearch();

//                Intent intent = new Intent(mactivity, CameraSearchActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
//                startActivity(intent);
            }
        });

        // 길찾기 클릭 리스너
        roadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //현위치가 출발지로 설정된 검색페이지로 전환
                Intent intent = new Intent(mactivity, RoadActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
            }
        });

        return view;
    }

    // 사진 검색 기능
    public void openCameraSearch() {
        View camera_view = getLayoutInflater().inflate(R.layout.bottom_sheet_camera, null);
        BottomSheetDialog camera_dialog = new BottomSheetDialog(mactivity, R.style.BottomSheetDialogTheme);

        // 앨범 버튼 동작
        camera_view.findViewById(R.id.camera_library).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mactivity, "앨범", Toast.LENGTH_SHORT).show();
            }
        });

        // 촬영 버튼 동작
        camera_view.findViewById(R.id.camera_camera).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mactivity, "촬영", Toast.LENGTH_SHORT).show();
            }
        });

        camera_dialog.setContentView(camera_view);
        camera_dialog.show();
    }


    // NaverMap 객체가 준비되면 호출되는 메소드
    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {
        //상경대 이미지추가
        GroundOverlay groundOverlay = new GroundOverlay();
        groundOverlay.setBounds(new LatLngBounds(
                new LatLng(35.830330, 128.751790), new LatLng(35.838070, 128.760270)));
        groundOverlay.setImage(
                OverlayImage.fromResource(R.drawable.finmap));
        groundOverlay.setAlpha(1); //투명도
        groundOverlay.setMap(naverMap);

        // default 카메라 설정
        cameraPosition = new CameraPosition(
                new LatLng(latitude, longitude),
                defaultZoom,
                defaultTilt,
                defaultBearing
        );

        naverMap.setCameraPosition(cameraPosition);

        // 네이버 지도 관련 ui 설정
        uiSettings = naverMap.getUiSettings();
        uiSettings.setCompassEnabled(false); // 좌측 상단 나침반 제거
        uiSettings.setTiltGesturesEnabled(false); // 틸트 제스처 해제
        uiSettings.setRotateGesturesEnabled(true); // 회전 제스처 false -> true 변경
        uiSettings.setLocationButtonEnabled(false); // 기본 로케이션 버튼 해제

        // 로케이션 버튼 설정
        naverMap.setLocationSource(locationSource);
        LocationButtonView locationButtonView = getActivity().findViewById(R.id.location_button);
        locationButtonView.setMap(naverMap);

        //편의시설 메뉴 생성
        ChipGroup chipGroup = view.findViewById(R.id.chipGroup);
        int[] CafeIcon = {R.drawable.ic_chip_cafe, R.drawable.ic_chip_cafe_fill};
        int[] convenienceIcon = {R.drawable.ic_chip_convenience, R.drawable.ic_chip_convenience_fill};
        int[] restroomIcon = {R.drawable.ic_chip_restroom, R.drawable.ic_chip_restroom_fill};
        int[] pressroomIcon = {R.drawable.ic_chip_pressroom, R.drawable.ic_chip_pressroom_fill};
        int[] etcIcon = {R.drawable.ic_chip_etc, R.drawable.ic_chip_etc_fill};

        Chip cafeChip = setChip("카페", CafeIcon[0], CafeIcon[1]);
        Chip convenienceChip = setChip("편의점", convenienceIcon[0], convenienceIcon[1]);
        Chip restroomChip = setChip("화장실", restroomIcon[0], restroomIcon[1]);
        Chip pressroomChip = setChip("인쇄실", pressroomIcon[0], pressroomIcon[1]);
        Chip libraryChip = setChip("도서관", etcIcon[0], etcIcon[1]);

        chipGroup.addView(cafeChip);
        chipGroup.addView(convenienceChip);
        chipGroup.addView(restroomChip);
        chipGroup.addView(pressroomChip);
        chipGroup.addView(libraryChip);
        chipGroup.setSingleLine(true);
        // 지도 클릭 시 마커 찍기
        naverMap.setOnMapClickListener(new NaverMap.OnMapClickListener() {
            @Override
            public void onMapClick(@NonNull PointF pointF, @NonNull LatLng latLng) {
                //빈 곳 클릭시 다중마커 삭제 및 단일 마커 생성
                if (marker != null || !markerList.isEmpty()) {
                    marker.setMap(null);
                    for (Marker m : markerList) m.setMap(null);
                    markerList.removeAll(markerList);
                    cafeChip.setChecked(false);
                    convenienceChip.setChecked(false);
                    restroomChip.setChecked(false);
                    pressroomChip.setChecked(false);
                    libraryChip.setChecked(false);
                }
                marker = new Marker();
                marker.setPosition(latLng);
                // 지도 클릭시 해당 위치 좌표 출력
                Toast.makeText(view.getContext(), "위도: " + latLng.latitude + "\n" + "경도: " + latLng.longitude, Toast.LENGTH_SHORT).show();
                Log.d("클릭위치", latLng.latitude + "," + latLng.longitude);
                marker.setMap(naverMap);
            }
        });
        cafeChip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                restroomChip.setChecked(false);
                convenienceChip.setChecked(false);
                pressroomChip.setChecked(false);
                libraryChip.setChecked(false);
                if (restList.getCheck() || convList.getCheck() || pressList.getCheck()||libraryList.getCheck()) {
                    for (Marker m : markerList)
                        m.setMap(null);

                    markerList.removeAll(markerList);
                    restList.setCheck(false);
                    convList.setCheck(false);
                    pressList.setCheck(false);
                    libraryList.setCheck(false);
                }
                if (!cafeList.getCheck()) {
                    for (LatLng ll : cafeList.getList()) {
                        Marker marker = new Marker();
                        marker.setIcon(OverlayImage.fromResource(R.drawable.marker_cafe));
                        marker.setPosition(ll);
                        marker.setWidth(113);
                        marker.setHeight(150);
                        markerList.add(marker);
                    }
                    for (Marker m : markerList)
                        m.setMap(naverMap);
                    cafeList.setCheck(true);
                } else {
                    for (Marker m : markerList)
                        m.setMap(null);
                    markerList.removeAll(markerList);
                    cafeList.setCheck(false);
                }
            }
        });
        restroomChip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cafeChip.setChecked(false);
                convenienceChip.setChecked(false);
                pressroomChip.setChecked(false);
                libraryChip.setChecked(false);
                if (cafeList.getCheck() || convList.getCheck() || pressList.getCheck()||libraryList.getCheck()) {
                    for (Marker m : markerList) m.setMap(null);
                    markerList.removeAll(markerList);
                    cafeList.setCheck(false);
                    convList.setCheck(false);
                    pressList.setCheck(false);
                    libraryList.setCheck(false);
                }
                if (!restList.getCheck()) {
                    for (LatLng ll : restList.getList()) {
                        Marker marker = new Marker();
                        marker.setIcon(OverlayImage.fromResource(R.drawable.marker_restroom));
                        marker.setPosition(ll);
                        marker.setWidth(100);
                        marker.setHeight(130);
                        markerList.add(marker);
                    }
                    for (Marker m : markerList)
                        m.setMap(naverMap);
                    restList.setCheck(true);
                } else {
                    for (Marker m : markerList) m.setMap(null);
                    markerList.removeAll(markerList);
                    restList.setCheck(false);
                }
            }
        });
        convenienceChip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                restroomChip.setChecked(false);
                cafeChip.setChecked(false);
                pressroomChip.setChecked(false);
                libraryChip.setChecked(false);
                if (cafeList.getCheck() || restList.getCheck() || pressList.getCheck()||libraryList.getCheck()) {
                    for (Marker m : markerList) m.setMap(null);
                    markerList.removeAll(markerList);
                    cafeList.setCheck(false);
                    restList.setCheck(false);
                    pressList.setCheck(false);
                }

                if (!convList.getCheck()) {
                    for (LatLng ll : convList.getList()) {
                        Marker marker = new Marker();
                        marker.setIcon(OverlayImage.fromResource(R.drawable.marker_convenience));
                        marker.setWidth(113);
                        marker.setHeight(150);
                        marker.setPosition(ll);
                        markerList.add(marker);
                    }
                    for (Marker m : markerList) m.setMap(naverMap);
                    convList.setCheck(true);
                } else {
                    for (Marker m : markerList) m.setMap(null);
                    markerList.removeAll(markerList);
                    convList.setCheck(false);
                }
            }
        });
        pressroomChip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                restroomChip.setChecked(false);
                convenienceChip.setChecked(false);
                cafeChip.setChecked(false);
                libraryChip.setChecked(false);
                if (cafeList.getCheck() || restList.getCheck() || convList.getCheck()||libraryList.getCheck()) {
                    for (Marker m : markerList)
                        //마커 제거
                        m.setMap(null);
                    //마커 리스트 사라짐
                    markerList.removeAll(markerList);
                    cafeList.setCheck(false);
                    restList.setCheck(false);
                    convList.setCheck(false);
                    libraryList.setCheck(false);
                }

                if (!pressList.getCheck()) {
                    for (LatLng ll : pressList.getList()) {
                        Marker marker = new Marker();
                        marker.setIcon(OverlayImage.fromResource(R.drawable.marker_pressrom));
                        marker.setWidth(113);
                        marker.setHeight(150);
                        marker.setPosition(ll);
                        markerList.add(marker);
                    }
                    for (Marker m : markerList) m.setMap(naverMap);
                    pressList.setCheck(true);
                } else {
                    for (Marker m : markerList) m.setMap(null);
                    markerList.removeAll(markerList);
                    pressList.setCheck(false);
                }
            }
        });
        libraryChip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                restroomChip.setChecked(false);
                convenienceChip.setChecked(false);
                cafeChip.setChecked(false);
                pressroomChip.setChecked(false);
                if (convList.getCheck() || restList.getCheck() || pressList.getCheck()||cafeList.getCheck()) {
                    for (Marker m : markerList)
                        //마커 제거
                        m.setMap(null);
                    //마커 리스트 사라짐
                    markerList.removeAll(markerList);
                    cafeList.setCheck(false);
                    restList.setCheck(false);
                    convList.setCheck(false);
                    pressList.setCheck(false);
                }
                if (!libraryList.getCheck()) {
                    for (LatLng ll : libraryList.getList()) {
                        Marker marker = new Marker();
                        marker.setIcon(OverlayImage.fromResource(R.drawable.marker_college));
                        marker.setWidth(113);
                        marker.setHeight(150);
                        marker.setPosition(ll);
                        markerList.add(marker);
                    }
                    for (Marker m : markerList) m.setMap(naverMap);
                    libraryList.setCheck(true);
                } else {
                    for (Marker m : markerList) m.setMap(null);
                    markerList.removeAll(markerList);
                    libraryList.setCheck(false);
                }
            }
        });

    }

    // 현재 카메라가 보고있는 위치
    public LatLng getCurrentPosition(NaverMap naverMap) {
        CameraPosition cameraPosition = naverMap.getCameraPosition();
        return new LatLng(cameraPosition.target.latitude, cameraPosition.target.longitude);
    }

    public void getLS(FusedLocationSource ls) {
        locationSource = ls;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("Map onCreate : ", "Enter");

    }

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
        Log.e("Map onStart : ", "Enter");
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
        Log.e("Map onResume : ", "Enter");
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
        Log.e("Map onPause : ", "Enter");
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
        Log.e("Map onStop : ", "Enter");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mapView.onDestroy();
        Log.e("Map onDestroyView : ", "Enter");
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    //편의시설 메뉴 추가 함수
    @SuppressLint({"ResourceAsColor", "ResourceType"})
    private Chip setChip(String chip_name, int chipIcon, int chipIcon_fill) {
        Chip chip = new Chip(getContext());
        chip.setText(chip_name);
        chip.setChipBackgroundColorResource(R.drawable.chip_bg);
        chip.setChipIconResource(chipIcon);
        chip.setElevation(15);
        chip.setCheckedIconResource(chipIcon_fill);
        chip.setChipIconSize(0);
        chip.setCheckable(true);
        return chip;
    }
}
