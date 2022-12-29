package com.example.navermapex_2;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class RoadSrcSearchActivity extends AppCompatActivity {

    private EditText search; // 검색바
    private ImageButton search_btn, prev_btn; // 검색버튼, 뒤로가기 버튼
    private String user_input; // 유저입력내용
    public MainActivity mainactivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //액션바 제거
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        setContentView(R.layout.activity_road_src_search);

        search = findViewById(R.id.search_bar);
        search_btn = findViewById(R.id.search_button);
        prev_btn = findViewById(R.id.prev_button);

        search.setFocusable(true);

        if(mainactivity.check_src == true){
            // 길찾기 시작 화면으로 이동
            user_input = mainactivity.src_input; //검색창에 입력한 내용
            if(!(user_input == null || user_input.isEmpty())) { //입력값이 공백일 때 화면전환x
                // 서버에 유저가 입력한 출발지의 좌표 요청 -> json 파일 다운/갱신
                //검색어에 해당하는 좌표 탐색
                user_input = user_input.replaceAll("\\s", "");
                LocationSearch LS = new LocationSearch(user_input);
                int[] place = LS.Location();
                if (place[0] == 0 && place[1] == 0) {
                    Toast.makeText(RoadSrcSearchActivity.this, "검색결과를 찾을 수 없습니다.", Toast.LENGTH_SHORT).show();
                } else {
                    mainactivity.start_x = String.valueOf(place[0]);
                    mainactivity.start_y = String.valueOf(place[1]);

                    // 길찾기 시작 화면으로 이동
                    Intent intent = new Intent();
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    intent.putExtra("src_userInput", user_input);
                    setResult(RESULT_OK, intent);
                    //mainactivity.check_src = false;
                    finish();
                }
            }
        }

        // 뒤로가기 버튼 눌렀을 때
        prev_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // 검색버튼 눌렀을 때
        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user_input = String.valueOf(search.getText()); //검색창에 입력한 내용
                if(!(user_input == null || user_input.isEmpty())) { //입력값이 공백일 때 화면전환x
                    // 서버에 유저가 입력한 출발지의 좌표 요청 -> json 파일 다운/갱신
                    //검색어에 해당하는 좌표 탐색
                    LocationSearch LS = new LocationSearch(user_input);
                    int[] place = LS.Location();
                    if(place[0] == 0 && place[1] == 0) {
                        Toast.makeText(RoadSrcSearchActivity.this, "검색결과를 찾을 수 없습니다.", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        mainactivity.start_x = String.valueOf(place[0]);
                        mainactivity.start_y = String.valueOf(place[1]);

                        // 길찾기 시작 화면으로 이동
                        Intent intent = new Intent();
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        intent.putExtra("src_userInput",user_input);
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                }
            }
        });

    }
}