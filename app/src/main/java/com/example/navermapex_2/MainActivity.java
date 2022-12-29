package com.example.navermapex_2;

import static com.example.navermapex_2.R.style.BottomSheetDialogTheme;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.location.LocationManager;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;

import android.os.Environment;
import android.os.PersistableBundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.naver.maps.map.LocationTrackingMode;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.util.FusedLocationSource;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends AppCompatActivity {
    public static String dest_x = "-99";
    public static String dest_y = "-98";
    public static String start_x = "-99";
    public static String start_y = "-98";
    public static int check_toast = 0;
    public static boolean check_src = false;
    public static String src_input = "error";

    private CoordinatorLayout coordinatorLayout;
    private BottomNavigationView bottomNavigationView;
    private FragmentManager fragmentManager = getSupportFragmentManager();
    private FragmentTransaction fragmentTransaction;

    private MapFragment mapFragment;
    private ShareFragment shareFragment;
    private EventFragment eventFragment;

    private int g_frag_pos = 0;

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1000;
    private FusedLocationSource locationSource;
    private NaverMap naverMap;

    private long backKeyPressedTime = 0;

    private static final int GPS_ENABLE_REQUEST_CODE = 2001;
    private static final int PERMISSIONS_REQUEST_CODE = 100;
    String[] REQUIRED_PERMISSIONS = {Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION};

    private final int REQUEST_IMAGE_CAPTURE = 672;
    private final int REQUEST_IMAGE_GALLERY = 1;

    private Uri photoUri;
    private String imageFilePath;

    private String[] tags = {"중앙도서관", "", "과학도서관", "", "법정관", "", "IT관", "", "상경관", "", "천마아트센터", ""};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("onCreate", "ENTER");

        //액션바 제거
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        setContentView(R.layout.activity_main);

        locationSource = new FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE);

        bottomNavigationView = findViewById(R.id.bottomNavi);
        bottomNavigationView.setItemIconTintList(null);

        // 하단 네비게이션 뷰 아이템 선택 리스너
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_bar_map: // 지도
                        setFrag(0);
                        break;
                    case R.id.action_bar_share: // 위치 공유
                        setFrag(1);
                        break;
                    case R.id.action_bar_concert: // 행사
                        setFrag(2);
                        break;
                }
                return true;
            }
        });

        setFrag(0); // 기본 화면 지정

        if (mapFragment != null)
            mapFragment.getLS(locationSource);

        // 현재 위치 받아오기
        if (!checkLocationServicesStatus()) {
            showDialogForLocationServiceSetting();
        } else {
            checkRunTimePermission();
        }
    }

    // 사진 검색 기능
    public void openCameraSearch() {
        View camera_view = getLayoutInflater().inflate(R.layout.bottom_sheet_camera, null);
        BottomSheetDialog camera_dialog = new BottomSheetDialog(this, R.style.BottomSheetDialogTheme);

        askPermisson();

        // 앨범 버튼 동작
        camera_view.findViewById(R.id.camera_library).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
//                Toast.makeText(mActivity, "앨범", Toast.LENGTH_SHORT).show();
            }
        });

        // 촬영 버튼 동작
        camera_view.findViewById(R.id.camera_camera).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                mActivity.cameraResultLauncher.launch(intent);


                openCamera();


            }
        });

        camera_dialog.setContentView(camera_view);
        camera_dialog.show();
    }

    private void openCamera() {
        if (TedPermission.isDenied(this, Manifest.permission.CAMERA)) {
            Toast.makeText(this, getResources().getString(R.string.permission_r), Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (intent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException e) {

            }
            if (photoFile != null) {
                photoUri = FileProvider.getUriForFile(this, getPackageName(), photoFile);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
            }
        }


    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "TEST_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        File image = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );

        imageFilePath = image.getAbsolutePath();

        return image;
    }

    public void openGallery() {
        if (TedPermission.isDenied(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            Toast.makeText(this, getResources().getString(R.string.permission_r), Toast.LENGTH_SHORT).show();

            return;
        }

        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent, REQUEST_IMAGE_GALLERY);
    }

    ///////////////////////////////////////////////////////////////////////////////
    // 카메라, 갤러리 접근 권한 요청
    private void askPermisson() {
        TedPermission.with(this)
                .setPermissionListener(permissionListener)
                .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE)
                .setDeniedMessage(getResources().getString(R.string.permission_d))
                .check();
    }

    PermissionListener permissionListener = new PermissionListener() {
        @Override
        public void onPermissionGranted() { // 승인
        }

        @Override
        public void onPermissionDenied(List<String> deniedPermissions) { // 거절
        }
    };

    @Nullable
    @Override
    public View onCreateView(@Nullable View parent, @NonNull String name, @NonNull Context context, @NonNull AttributeSet attrs) {
        return super.onCreateView(parent, name, context, attrs);
    }

    // 하단 네비게이션에서 아이템 선택시 해당하는 프래그먼트로 이동
    public void setFrag(int num) {
        fragmentTransaction = fragmentManager.beginTransaction();

        switch (num) {
            case 0:
                if (mapFragment != null)
                    fragmentTransaction.show(mapFragment);
                if (shareFragment != null)
                    fragmentTransaction.detach(shareFragment);
                if (eventFragment != null)
                    fragmentTransaction.hide(eventFragment);

                if (mapFragment == null) {
                    mapFragment = new MapFragment();
                    fragmentTransaction.add(R.id.main_frame, mapFragment);
                }

                break;
            case 1:
                if (mapFragment != null)
                    fragmentTransaction.hide(mapFragment);
                if (shareFragment != null)
                    fragmentTransaction.attach(shareFragment);
                if (eventFragment != null)
                    fragmentTransaction.hide(eventFragment);

                if (shareFragment == null) {
                    shareFragment = new ShareFragment();
                    fragmentTransaction.add(R.id.main_frame, shareFragment);
                }

                break;

            case 2:
                if (mapFragment != null)
                    fragmentTransaction.hide(mapFragment);
                if (shareFragment != null)
                    fragmentTransaction.detach(shareFragment);
                if (eventFragment != null)
                    fragmentTransaction.show(eventFragment);

                if (eventFragment == null) {
                    eventFragment = new EventFragment();
                    fragmentTransaction.add(R.id.main_frame, eventFragment);
                }
                break;
        }
        fragmentTransaction.commit();
    }

    /*
     * ActivityCompat.requestPermissions를 사용한 퍼미션 요청의 결과를 리턴받는 메소드
     */
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSIONS_REQUEST_CODE && grantResults.length == REQUIRED_PERMISSIONS.length) {

            // 요청 코드가 PERMISSIONS_REQUEST_CODE 이고, 요청한 퍼미션 개수만큼 수신되었다면
            boolean check_result = true;

            // 모든 퍼미션을 허용했는지 체크

            for (int result : grantResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    check_result = false;
                    break;
                }
            }

            if (check_result) {

                //위치 값을 가져올 수 있음
                ;
            } else {
                // 거부한 퍼미션이 있다면 앱을 사용할 수 없는 이유를 설명해주고 앱을 종료함.2가지 경우가 있음

                if (ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[0])
                        || ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[1])) {

                    Toast.makeText(MainActivity.this, "퍼미션이 거부되었습니다. 앱을 다시 실행하여 퍼미션을 허용해주세요.", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(MainActivity.this, "퍼미션이 거부되었습니다. 설정(앱 정보)에서 퍼미션을 허용해야 합니다. ", Toast.LENGTH_LONG).show();

                }
            }

        }
        // 네이버 위치 버튼 관련 인증
        if (locationSource.onRequestPermissionsResult(
                requestCode, permissions, grantResults)) {
            if (!locationSource.isActivated()) { // 권한 거부됨
                naverMap.setLocationTrackingMode(LocationTrackingMode.None);
            }
            return;
        }
        super.onRequestPermissionsResult(
                requestCode, permissions, grantResults);
    }

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
            backKeyPressedTime = System.currentTimeMillis();
            Toast.makeText(this, "뒤로가기 버튼을 한 번 더 눌러 종료", Toast.LENGTH_SHORT).show();
            return;
        }

        if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
            finish();
        }
    }

    void checkRunTimePermission() {

        //런타임 퍼미션 처리
        // 1. 위치 퍼미션을 가지고 있는지 체크함
        int hasFineLocationPermission = ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        int hasCoarseLocationPermission = ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.ACCESS_COARSE_LOCATION);


        if (hasFineLocationPermission == PackageManager.PERMISSION_GRANTED &&
                hasCoarseLocationPermission == PackageManager.PERMISSION_GRANTED) {

            // 2. 이미 퍼미션을 가지고 있다면
            // ( 안드로이드 6.0 이하 버전은 런타임 퍼미션이 필요없기 때문에 이미 허용된 걸로 인식함)


            // 3.  위치 값을 가져올 수 있음


        } else {  //2. 퍼미션 요청을 허용한 적이 없다면 퍼미션 요청이 필요함. 2가지 경우(3-1, 4-1)가 있음

            // 3-1. 사용자가 퍼미션 거부를 한 적이 있는 경우에는
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, REQUIRED_PERMISSIONS[0])) {

                // 3-2. 요청을 진행하기 전에 사용자가에게 퍼미션이 필요한 이유를 설명해줄 필요가 있습니다.
                Toast.makeText(MainActivity.this, "이 앱을 실행하려면 위치 접근 권한이 필요합니다.", Toast.LENGTH_LONG).show();
                // 3-3. 사용자게에 퍼미션 요청을 합니다. 요청 결과는 onRequestPermissionResult에서 수신됩니다.
                ActivityCompat.requestPermissions(MainActivity.this, REQUIRED_PERMISSIONS,
                        PERMISSIONS_REQUEST_CODE);
            } else {
                // 4-1. 사용자가 퍼미션 거부를 한 적이 없는 경우에는 퍼미션 요청을 바로 합니다.
                // 요청 결과는 onRequestPermissionResult에서 수신됩니다.
                ActivityCompat.requestPermissions(MainActivity.this, REQUIRED_PERMISSIONS,
                        PERMISSIONS_REQUEST_CODE);
            }
        }
    }

    //GPS 활성화를 위한 메소드들
    private void showDialogForLocationServiceSetting() {

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("위치 서비스 비활성화");
        builder.setMessage("앱을 사용하기 위해서는 위치 서비스가 필요합니다.\n"
                + "위치 설정을 수정하실래요?");
        builder.setCancelable(true);
        builder.setPositiveButton("설정", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                Intent callGPSSettingIntent
                        = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivityForResult(callGPSSettingIntent, GPS_ENABLE_REQUEST_CODE);
            }
        });
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        builder.create().show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {

            case GPS_ENABLE_REQUEST_CODE:

                //사용자가 GPS 활성 시켰는지 검사
                if (checkLocationServicesStatus()) {
                    if (checkLocationServicesStatus()) {

                        Log.d("@@@", "onActivityResult : GPS 활성화 되있음");
                        checkRunTimePermission();
                        return;
                    }
                }

                break;

            case REQUEST_IMAGE_CAPTURE:
                if (resultCode == Activity.RESULT_OK) {
                    Bitmap bitmap = BitmapFactory.decodeFile(imageFilePath);
                    ExifInterface exif = null;

                    try {
                        exif = new ExifInterface(imageFilePath);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    int exifOrientation;
                    int exifDegree;

                    if (exif != null) {
                        exifOrientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
                        exifDegree = exifOrientationToDegreess(exifOrientation);
                    } else {
                        exifDegree = 0;
                    }

                    searchByImage();
                }
            case REQUEST_IMAGE_GALLERY:
                if (data == null) {
                    break;
                }

                searchByImage();
                break;
        }
    }

    // 화면 돌려서 촬영 시 rotate 시켜주기
    private int exifOrientationToDegreess(int exifOrientation) {
        if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_90) {
            return 90;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_180) {
            return 180;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_270) {
            return 270;
        } else {
            return 0;
        }
    }

    private Bitmap rotate(Bitmap bitmap, float degree) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);

        return Bitmap.createBitmap(bitmap,
                0, 0,
                bitmap.getWidth(),
                bitmap.getHeight(),
                matrix,
                true);
    }

    private void searchByImage() {
        showDialog(1);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                TimerTask task = new TimerTask() {
                    @Override
                    public void run() {
                        removeDialog(1);

                        int idx = (int) (Math.random() * tags.length);
                        String tag = tags[idx];

                        Intent intent = new Intent(MainActivity.this, ResultActivity.class);
                        intent.putExtra("user_input", tag);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(intent);
                    }
                };

                Timer timer = new Timer();
                timer.schedule(task, 3000);
            }
        });
        thread.start();
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("찾는 중...");

        return dialog;
    }

    public boolean checkLocationServicesStatus() {
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }


}