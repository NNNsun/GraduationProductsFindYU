package com.example.navermapex_2;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;



public class JsonConverter {
    private static final String TAG = "JC_TEST";
    //json -> str, str-> int 배열로 변환하여 좌표배열 리턴 클래스
    private String path; //json 파일 경로
    private final Context mContext;
    private AssetManager am;
    private String strConvert;
    private String startX;
    private String startY;
    private String endX;
    private String endY;
    private EditText start_x;
    private EditText start_y;
    private EditText end_x;
    private EditText end_y;
    private Button btnSend;
    public String ServerPath; //서버에서부터 받은경로

    private MainActivity mainactivity; // static변수 접근

    public JsonConverter(Context context, String path) {

        this.mContext = context;
        this.am = context.getResources().getAssets();
        this.path = path;
        jsonToString();
    }
    //json을 string으로 변환
    public void jsonToString() {

        //------------------------------ 서버 경로받기시작 부분 ------------------------------
        Map<String, String> querys = new HashMap<>();
        querys.clear();
        // 2**************** 이 부분 임시로 테스트를 위해 만들었던 입력부. String으로 주시면 됩니다.
        startX = String.valueOf(mainactivity.start_x);
        startY = String.valueOf(mainactivity.start_y);
        endX = String.valueOf(mainactivity.dest_x);
        endY = String.valueOf(mainactivity.dest_y);
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        System.out.println(mainactivity.dest_x);
        System.out.println(mainactivity.dest_y);
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        //input값 Map에 삽입
        querys.put("startx",startX);
        querys.put("starty",startY);
        querys.put("endx",endX);
        querys.put("endy",endY);

        Log.d(TAG,startX +" " + startY+" "+endX+" "+endY);

        //Url 접속
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://13.209.134.147:5000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitService servicel = retrofit.create(RetrofitService.class);
        Call<MazeResult> call = servicel.getPosts(querys);

        call.enqueue(new Callback<MazeResult>() {
            @Override
            public void onResponse(Call<MazeResult> call, Response<MazeResult> response) {

                if(response.isSuccessful()){
                    MazeResult result = response.body();
                    Log.d(TAG,"onResponse: 성공, 결과\n"+ result.toString());
                    // 3**************** 결과값 입력부분. result.toString() 이 변수입니다. result만 쓰실경우 오류가 나기에 꼭 붙여주세요. 다른 변수에 집어넣어서 쓰시면 됩니다.
                    ServerPath = result.toString();
                    System.out.println("ServerPath : ");
                    System.out.println(ServerPath);
                }else{
                    Log.d(TAG,"onResponse: 실패");
                }
            }

            @Override
            public void onFailure(Call<MazeResult> call, Throwable t) {
                Log.d(TAG,"onFailure: "+ t.getMessage());
            }
        });
        // ------------------------------ 끝 부분 ------------------------------

        InputStream is = null;
        String str = "";
        try {
            is = am.open(path);
            int fileSize = is.available();
            byte buf[] = new byte[fileSize];
            if (is.read(buf) > 0) {
                str = new String(buf);
            }
            is.close();
            strConvert = str;
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (is != null) {
            try {
                is.close();
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        strConvert = str;
    }

    // 2차원 좌표 배열(경로) 리턴
    public int[][] strTo2DArray() {
        String data = ServerPath;
//        String data = "[[114, 330], [115, 331], [116, 332], [117, 333], [118, 334], [119, 335], [120, 336], [121, 337], [122, 338], [123, 339], [124, 340], [125, 341], [126, 342], [127, 342], [128, 342], [129, 343], [130, 343], [131, 343], [132, 344], [133, 344], [134, 344], [135, 344], [136, 345], [137, 345], [138, 345], [139, 345], [140, 346], [141, 346], [142, 346], [143, 347], [144, 347], [145, 347], [146, 347], [147, 348], [148, 348], [149, 348], [150, 349], [151, 349], [152, 349], [153, 349], [154, 350], [155, 350], [156, 350], [157, 351], [158, 351], [159, 351], [160, 351], [161, 352], [162, 352], [163, 352], [164, 353], [165, 352], [166, 351], [167, 350], [168, 351], [169, 352], [170, 353], [171, 354], [172, 355], [173, 356], [174, 357], [175, 358], [176, 359], [177, 360], [178, 361], [179, 362], [180, 363], [181, 364], [182, 365], [183, 366], [184, 367], [185, 368], [186, 369], [187, 370], [188, 371], [189, 372], [190, 373], [190, 374], [190, 375], [190, 376], [190, 377], [190, 378], [190, 379], [190, 380], [190, 381], [190, 382], [190, 383], [190, 384], [190, 385], [190, 386], [191, 387], [192, 388], [193, 389], [194, 390], [195, 391], [194, 392], [194, 393], [194, 394], [194, 395], [194, 396], [194, 397], [194, 398], [194, 399], [194, 400], [194, 401], [194, 402], [194, 403], [194, 404], [194, 405], [194, 406], [195, 407], [196, 408], [196, 409], [196, 410], [197, 411], [198, 412], [199, 413], [199, 414], [199, 415], [199, 416], [200, 417], [200, 418], [200, 419], [200, 420], [200, 421], [200, 422], [201, 423], [201, 424], [201, 425], [201, 426], [201, 427], [201, 428], [202, 429], [203, 430], [204, 430], [205, 430], [206, 430], [207, 430], [208, 430], [209, 429], [210, 429], [211, 429], [212, 429], [213, 428], [214, 428], [215, 429], [216, 430], [217, 431], [218, 432], [219, 433], [219, 434], [220, 435], [220, 436], [220, 437], [221, 438], [221, 439], [222, 440], [222, 441], [222, 442], [222, 443], [223, 444], [224, 445], [225, 446], [226, 447], [227, 448], [228, 449], [229, 450], [230, 449], [231, 449], [232, 448], [233, 448], [234, 449], [235, 450], [236, 451], [237, 452], [237, 453], [237, 454], [237, 455], [238, 456], [239, 457], [239, 458], [239, 459], [239, 460], [239, 461], [239, 462]]";
        int datalen = data.length();
        data = data.substring(1, datalen - 2);
        int idx = 0;
        int i = 0;

        String result[] = data.split("], ");

        for (String r : result) {
            result[i] = r.substring(1, r.length());
            i++;
        }
        int[][] intresult = new int[result.length][2];
        String[][] tmp = new String[1][];
        for (String r : result) {
//            System.out.println();
            tmp[0] = r.split(", ");
            intresult[idx][0] = Integer.parseInt(tmp[0][0]);
            intresult[idx][1] = Integer.parseInt(tmp[0][1]);
//         System.out.println(result2[idx][0] +" "+ result2[idx][1]);
//         System.out.println();
            idx++;
        }
        return intresult;
    }

    //1차원 좌표 배열 리턴
    public int[] strToArray() {
        String data = strConvert;
        int datalen = data.length();
        data = data.substring(1,datalen-1); //배열에서 [ ] 제거
        int i=0;
        String tmp[] = data.split(", ");
        int result[] = new int[2];
        for(String t : tmp) {
            result[i] = Integer.parseInt(t);
            //System.out.println(result[i]);
            i++;
        }
        return result;
    }

}
