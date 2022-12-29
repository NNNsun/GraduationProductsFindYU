package com.example.navermapex_2;

import javax.xml.transform.Result;

public class LocationSearch {
    int[] location = new int[2];
    String userinput;

    public LocationSearch(String inputdata) {
        this.userinput = inputdata;
    }

    public int[] Location() {
        //중앙도서관
        if (userinput.equals("중도") || userinput.equals("중도") || userinput.equals("LIB")) {
            location[0] = 534;
            location[1] = 268;
            ResultActivity.titleData = "[B04] 중앙도서관";
        }

        //중앙도서관
        else if (userinput.equals("정행대") || userinput.equals("정치외교") || userinput.equals("JH")
                || userinput.equals("정치외교학과")) {
            location[0] = 463;
            location[1] = 241;
            ResultActivity.titleData = "[B05] 정치행정대학";
        } else if (userinput.equals("test1") || userinput.equals("정문") || userinput.equals("3번출구") || userinput.equals("영남대학교정문") || userinput.equals("학교정문")) {
            location[0] = 331;
            location[1] = 116;
        } else if (userinput.equals("test2") || userinput.equals("중도입구")) {
            location[0] = 468;
            location[1] = 243;
        }
        //A02
        else if (userinput.equals("국제교류센터") || userinput.equals("KFC") || userinput.equals("대구은행") || userinput.equals("문구점") || userinput.equals("국제교류팀") || userinput.equals("유학생지원팀") || userinput.equals("국제학부") || userinput.equals("국제학")) {
            location[0] = 0;
            location[1] = 0;
        }
        //A04
        else if (userinput.equals("박물관")) {
            location[0] = 0;
            location[1] = 0;
        }
        //A05
        else if (userinput.equals("학생지원센터") || userinput.equals("상담센터") || userinput.equals("취업지원센터") || userinput.equals("경력개발센터") || userinput.equals("건강관리센터")) {
            location[0] = 0;
            location[1] = 0;
        }
        //A06
        else if (userinput.equals("디자인미술대학디자인관") || userinput.equals("디자인관") || userinput.equals("시디과") || userinput.equals("시각디자인학과") || userinput.equals("산업디자인학과") || userinput.equals("산디과") || userinput.equals("산업디자인") || userinput.equals("디자인") || userinput.equals("시각디자인")) {
            location[0] = 0;
            location[1] = 0;
        }
        //A07
        else if (userinput.equals("디자인미술대학미술관") || userinput.equals("미술관") || userinput.equals("회화과") || userinput.equals("회화") || userinput.equals("트랜스아트") || userinput.equals("미술") || userinput.equals("미술대학") || userinput.equals("미대")) {
            location[0] = 0;
            location[1] = 0;
        }
        //A08
        else if (userinput.equals("사범대학") || userinput.equals("사범대") || userinput.equals("사대") || userinput.equals("교육대학원") || userinput.equals("국어교육과") || userinput.equals("영어교육과") || userinput.equals("한문교육과") || userinput.equals("수학교육과") || userinput.equals("유아교육과") || userinput.equals("특수체육교육과") || userinput.equals("교육연수원")) {
            location[0] = 0;
            location[1] = 0;
        }
        //A10
        else if (userinput.equals("음악대학") || userinput.equals("음대") || userinput.equals("성악과") || userinput.equals("피아노학과") || userinput.equals("관현악과") || userinput.equals("국악과")) {
            location[0] = 0;
            location[1] = 0;
        }
        //A11
        else if (userinput.equals("음악대학심포니홀") || userinput.equals("심포니홀")) {
            location[0] = 0;
            location[1] = 0;
        }
        //A12
        else if (userinput.equals("시설관리지원센터") || userinput.equals("건축팀") || userinput.equals("시설운영팀") || userinput.equals("안전관리팀")) {
            location[0] = 0;
            location[1] = 0;
        }
        //B01
        else if (userinput.equals("노천강당") || userinput.equals("총동아리") || userinput.equals("동아리") || userinput.equals("무대") || userinput.equals("중동") || userinput.equals("총동")) {
            location[0] = 0;
            location[1] = 0;
        }
        //B02
        else if (userinput.equals("상경관") || userinput.equals("상경대학") || userinput.equals("경제금융학부") || userinput.equals("글로벌차이나연합") || userinput.equals("무역학부") || userinput.equals("항공운송학과") || userinput.equals("항공운항") || userinput.equals("야간강좌학생회") || userinput.equals("야간강좌") || userinput.equals("야간") || userinput.equals("공인회계사") || userinput.equals("회계") || userinput.equals("경영대학원") || userinput.equals("무역대학원") || userinput.equals("경영학과") || userinput.equals("회계세무학과") || userinput.equals("산업경영학과") || userinput.equals("글로벌비즈니스학과")) {
            location[0] = 0;
            location[1] = 0;
        }
        //B03
        else if (userinput.equals("인문관") || userinput.equals("문과대학") || userinput.equals("문과대") || userinput.equals("국어국문학과") || userinput.equals("국어국문") || userinput.equals("중국언어문화학과") || userinput.equals("중국언어") || userinput.equals("일어일문학과") || userinput.equals("일어") || userinput.equals("영어영문학과") || userinput.equals("유럽언어문화학부") || userinput.equals("유럽언어") || userinput.equals("불어불문학과") || userinput.equals("불어") || userinput.equals("프랑스") || userinput.equals("독어독문학과") || userinput.equals("독어") || userinput.equals("철학과") || userinput.equals("철학") || userinput.equals("역사학과") || userinput.equals("문화인류학과") || userinput.equals("심리학과") || userinput.equals("심리학") || userinput.equals("사회학과") || userinput.equals("언론정보학과") || userinput.equals("언론") || userinput.equals("영어통번역") || userinput.equals("영어통번역전공") || userinput.equals("프랑스어") || userinput.equals("프랑스어문전공") || userinput.equals("독일어") || userinput.equals("독일언어") || userinput.equals("독일언어문화") || userinput.equals("독일언어문화전공")) {
            location[0] = 0;
            location[1] = 0;
        }
        //B04 중복되는 부분
        else if (userinput.equals("중앙도서관") || userinput.equals("중도") || userinput.equals("중도열람실") || userinput.equals("중도스타벅스") || userinput.equals("중도세미나실") || userinput.equals("중도그룹학습실")) {
            location[0] = 0;
            location[1] = 0;
        }
        //B05 중복되는 부분
        else if (userinput.equals("법정관") || userinput.equals("법과대학") || userinput.equals("법과대") || userinput.equals("정행대") || userinput.equals("정치행정대학") || userinput.equals("정치외교학과") || userinput.equals("정외과") || userinput.equals("행정학과") || userinput.equals("행정") || userinput.equals("새마을국제개발학과") || userinput.equals("새마을학과") || userinput.equals("새마을") || userinput.equals("경찰행정학과") || userinput.equals("경찰행정") || userinput.equals("경행과") || userinput.equals("군사학과") || userinput.equals("군사")) {
            location[0] = 0;
            location[1] = 0;
        }
        //B06
        else if (userinput.equals("학생회관") || userinput.equals("중도학식") || userinput.equals("중앙도서관학식") || userinput.equals("중도식당") || userinput.equals("중앙감사") || userinput.equals("학생회관식당") || userinput.equals("학생회관학식") || userinput.equals("총학생회") || userinput.equals("총학")) {
            location[0] = 0;
            location[1] = 0;
        }
        //B07
        else if (userinput.equals("이희건기념관") || userinput.equals("이희건") || userinput.equals("배드민턴장") || userinput.equals("체육관") || userinput.equals("검도관") || userinput.equals("검도") || userinput.equals("농구장")) {
            location[0] = 0;
            location[1] = 0;
        }
        //C01
        else if (userinput.equals("본부본관") || userinput.equals("본부") || userinput.equals("입학팀") || userinput.equals("입학처") || userinput.equals("입학처상임전문위원실") || userinput.equals("총무팀") || userinput.equals("인사관리팀") || userinput.equals("종합상황실") || userinput.equals("문서실") || userinput.equals("문서총무팀") || userinput.equals("종합상황총무팀") || userinput.equals("총무부장실") || userinput.equals("고충처리상담실") || userinput.equals("심리상담실") || userinput.equals("총무처장실") || userinput.equals("총무처") || userinput.equals("재무팀") || userinput.equals("대구은행영대본부점") || userinput.equals("대구은행영대본부") || userinput.equals("총장실") || userinput.equals("비서실") || userinput.equals("안내실") || userinput.equals("부속실") || userinput.equals("접견실") || userinput.equals("대외협력부총장실") || userinput.equals("기자실") || userinput.equals("비서홍보팀") || userinput.equals("입학처장실") || userinput.equals("글로컬사업추진팀") || userinput.equals("글로컬사업추진단장실") || userinput.equals("비서실장실") || userinput.equals("교학부총장실") || userinput.equals("기획팀") || userinput.equals("제도법무팀") || userinput.equals("기획부처장실") || userinput.equals("기획처장실") || userinput.equals("대학자원관리시스템설치추진단장실") || userinput.equals("대학자원관리시스템설치") || userinput.equals("대외협력관리팀") || userinput.equals("대외협력처장실") || userinput.equals("홍보매체제작실") || userinput.equals("대회의실") || userinput.equals("평가분석팀") || userinput.equals("예산팀") || userinput.equals("대기실홍보대사") || userinput.equals("홍보대사대기실")) {
            location[0] = 0;
            location[1] = 0;
        }
        //C02
        else if (userinput.equals("외국어교육원") || userinput.equals("외국어") || userinput.equals("외국어교육") || userinput.equals("외교원") || userinput.equals("글로벌라운지") || userinput.equals("외국어특강") || userinput.equals("외국어세미나") || userinput.equals("외국인교원수석연구실") || userinput.equals("외국인교원공동연구실") || userinput.equals("중어중문학전공사무실") || userinput.equals("응용중국어통번역") || userinput.equals("응용중국어통번역전공")) {
            location[0] = 0;
            location[1] = 0;
        }
        //C03
        else if (userinput.equals("종합강의동") || userinput.equals("종합강의") || userinput.equals("종합관") || userinput.equals("중국언어문화부행정실") || userinput.equals("방송스튜디오실") || userinput.equals("가상스튜디오") || userinput.equals("교육개발센터장실") || userinput.equals("교육개발센터행정실") || userinput.equals("영사실") || userinput.equals("상담심리") || userinput.equals("심리학부") || userinput.equals("이광오심리") || userinput.equals("천마학부") || userinput.equals("천마학부대학") || userinput.equals("전공자유") || userinput.equals("전공자유선택") || userinput.equals("전공자유선택학부")) {
            location[0] = 0;
            location[1] = 0;
        }
        //C04
        else if (userinput.equals("인문계식당") || userinput.equals("문과식당") || userinput.equals("문과대식당") || userinput.equals("문과학식") || userinput.equals("인문계학색싱당") || userinput.equals("문과학생식당") || userinput.equals("인문계교직원식당")) {
            location[0] = 0;
            location[1] = 0;
        }
        //C21
        else if (userinput.equals("학사민원실") || userinput.equals("학사민원") || userinput.equals("학사") || userinput.equals("교원인사팀") || userinput.equals("수업학적팀") || userinput.equals("교무처장") || userinput.equals("교무처장실") || userinput.equals("교원인사") || userinput.equals("수업학적") || userinput.equals("교무부장실") || userinput.equals("교무부장") || userinput.equals("캠퍼스관리팀") || userinput.equals("전기팀") || userinput.equals("환경설비팀") || userinput.equals("시설팀") || userinput.equals("시설관리처장실") || userinput.equals("재산관리팀")) {
            location[0] = 0;
            location[1] = 0;
        }
        //C25
        else if (userinput.equals("연대본부노동조합") || userinput.equals("연대본부") || userinput.equals("노동조합") || userinput.equals("연본")) {
            location[0] = 0;
            location[1] = 0;
        }
        //C28
        else if (userinput.equals("학군단") || userinput.equals("명예위원실")) {
            location[0] = 0;
            location[1] = 0;
        }
        //E02
        else if (userinput.equals("천마아트센터") || userinput.equals("천마아트") || userinput.equals("아트센터") || userinput.equals("아트")|| userinput.equals("그랜드홀") || userinput.equals("챔버홀")) {
            location[0] = 0;
            location[1] = 0;
        }
        //E05
        else if (userinput.equals("천마체육관") || userinput.equals("천마체육")) {
            location[0] = 0;
            location[1] = 0;
        }
        //E21
        else if (userinput.equals("IT관") || userinput.equals("아티관")|| userinput.equals("IT")|| userinput.equals("컴퓨터공학과")|| userinput.equals("정보통신공학과")|| userinput.equals("컴퓨터공학")|| userinput.equals("정보통신공학")|| userinput.equals("컴공")|| userinput.equals("정통")|| userinput.equals("컴퓨터")|| userinput.equals("컴퓨터공학과사무실")|| userinput.equals("정보통신공학과사무실")) {
            location[0] = 0;
            location[1] = 0;
        }
        //E22
        else if (userinput.equals("전기관") || userinput.equals("전기")|| userinput.equals("전기공학과")|| userinput.equals("전자공학과")|| userinput.equals("전기공학")|| userinput.equals("전자공학")|| userinput.equals("전기공")|| userinput.equals("전자공")|| userinput.equals("전기공학과사무실")|| userinput.equals("전자공학과사무실")|| userinput.equals("전자")) {
            location[0] = 0;
            location[1] = 0;
        }
        //E22
        else if (userinput.equals("섬유관") || userinput.equals("섬유")|| userinput.equals("의류패션학과")|| userinput.equals("의류패션")|| userinput.equals("의패")|| userinput.equals("파이버시스템공학과")|| userinput.equals("파이버시스템")|| userinput.equals("파시공")|| userinput.equals("고분자바이오소재")|| userinput.equals("고분자")|| userinput.equals("바이오")|| userinput.equals("고분자바이오")|| userinput.equals("파이버시스템공학과사무실")|| userinput.equals("파시공과사무실")|| userinput.equals("의류패션학과사무실")) {
            location[0] = 0;
            location[1] = 0;
        }
        //E24
        else if (userinput.equals("화공관") || userinput.equals("화공")|| userinput.equals("화학공학부")|| userinput.equals("화학공학")|| userinput.equals("화학공학부사무실")) {
            location[0] = 0;
            location[1] = 0;
        }
        //E28
        else if (userinput.equals("소재관") || userinput.equals("소재")|| userinput.equals("신소재공학부")|| userinput.equals("신소재공학")|| userinput.equals("신소재")|| userinput.equals("환경공학과")|| userinput.equals("환경공학")|| userinput.equals("환경")|| userinput.equals("환경공학과사무실")|| userinput.equals("신소재공학부사무실")|| userinput.equals("ABEEK사무실")) {
            location[0] = 0;
            location[1] = 0;
        }
        //E29
        else if (userinput.equals("기계관") || userinput.equals("기계")|| userinput.equals("기계IT대학")|| userinput.equals("기계공학부")|| userinput.equals("기계공학")|| userinput.equals("공학교육혁신센터")|| userinput.equals("기계공학부사무실")|| userinput.equals("소프트웨어융합학부")) {
            location[0] = 0;
            location[1] = 0;
        }
        //F03
        else if (userinput.equals("건축관") || userinput.equals("건축")|| userinput.equals("건축학부")|| userinput.equals("건축연구소")|| userinput.equals("건축학과사무실")|| userinput.equals("건축학")|| userinput.equals("건축공학")|| userinput.equals("건축디자인")) {
            location[0] = 0;
            location[1] = 0;
        }
        //F04
        else if (userinput.equals("정보전산원") || userinput.equals("전산원")|| userinput.equals("전산")|| userinput.equals("YES")|| userinput.equals("YU진로취업센터")) {
            location[0] = 0;
            location[1] = 0;
        }
        //F05
        else if (userinput.equals("공과대학강당") || userinput.equals("공대강당")) {
            location[0] = 0;
            location[1] = 0;
        }
        //F06
        else if (userinput.equals("정보통신연구소") || userinput.equals("ASIC연구센터")) {
            location[0] = 0;
            location[1] = 0;
        }

        //F07
        else if (userinput.equals("건설관") || userinput.equals("건설")|| userinput.equals("건시공")|| userinput.equals("건설시스템공학과")|| userinput.equals("건설시스템공학")|| userinput.equals("도시공학과")|| userinput.equals("도시공학")|| userinput.equals("도시")|| userinput.equals("건설시스템공학과사무실")|| userinput.equals("도시공학과사무실")) {
            location[0] = 0;
            location[1] = 0;
        }

        //F21
        else if (userinput.equals("제1과학관") || userinput.equals("제1과학")|| userinput.equals("수학과")|| userinput.equals("통계학과")|| userinput.equals("물리학과")|| userinput.equals("수학")|| userinput.equals("통계")|| userinput.equals("물리")|| userinput.equals("화학생화학부")|| userinput.equals("화학전공")|| userinput.equals("자연과학대학행정실")|| userinput.equals("자연과학대학")|| userinput.equals("자과대")|| userinput.equals("수학과사무실")|| userinput.equals("통계학과사무실")|| userinput.equals("물리학과사무실")|| userinput.equals("화학생화학과사무실")|| userinput.equals("생명과학과사무실")) {
            location[0] = 0;
            location[1] = 0;
        }
        //F22
        else if (userinput.equals("제2과학관") || userinput.equals("제2과학") || userinput.equals("생명과학과") || userinput.equals("생명과학")) {
            location[0] = 0;
            location[1] = 0;
        }
        //F22
        else if (userinput.equals("제3과학관") || userinput.equals("제3과학") || userinput.equals("생화학전공") || userinput.equals("생화학")) {
            location[0] = 0;
            location[1] = 0;
        }
        //F24
        else if (userinput.equals("과학도서관") || userinput.equals("이도") || userinput.equals("과도") || userinput.equals("이종우과학도서관")|| userinput.equals("이과도서관") || userinput.equals("공대도서관")|| userinput.equals("그룹스터디룸") || userinput.equals("그룹룸")|| userinput.equals("이도스터디룸")|| userinput.equals("셀프스튜디오")|| userinput.equals("취업스터디룸")|| userinput.equals("취업룸")) {
            location[0] = 0;
            location[1] = 0;
        }
        //F25
        else if (userinput.equals("자연계식당") || userinput.equals("공대식당") || userinput.equals("이과식당") || userinput.equals("자연대식당")) {
            location[0] = 0;
            location[1] = 0;
        }
        //F26
        else if (userinput.equals("생명응용과학대제1실험동") || userinput.equals("생명응용제1실험동") || userinput.equals("제1실험동") || userinput.equals("생명공학관")|| userinput.equals("식품공학과") || userinput.equals("생명공학과")|| userinput.equals("의생명공학과") || userinput.equals("의생명공학")|| userinput.equals("식품공학")|| userinput.equals("생명공학")|| userinput.equals("식품공학과사무실")|| userinput.equals("의생명공학과사무실")) {
            location[0] = 0;
            location[1] = 0;
        }
        //F27
        else if (userinput.equals("생명응용과학대본관") || userinput.equals("생명응용본관")|| userinput.equals("생응대본관")) {
            location[0] = 0;
            location[1] = 0;
        }
        //F28
        else if (userinput.equals("생명응용과학대제2실험동") || userinput.equals("생명응용제2실험동")|| userinput.equals("생응대")|| userinput.equals("생응대제2실험동")|| userinput.equals("제2실험동")|| userinput.equals("생명응용")|| userinput.equals("식품경제외식학과")|| userinput.equals("조경학과")|| userinput.equals("산림자원학과")|| userinput.equals("식품경제외식")|| userinput.equals("조경")|| userinput.equals("산림자원")|| userinput.equals("식품경제외식학과사무실")|| userinput.equals("조경학과사무실")|| userinput.equals("산림자원학과사무실")) {
            location[0] = 0;
            location[1] = 0;
        }
        //F29
        else if (userinput.equals("생명응용과학대제3실험동") || userinput.equals("생명응용제3실험동")|| userinput.equals("생응대제3실험동")|| userinput.equals("제3실험동")|| userinput.equals("원예생명과학과")|| userinput.equals("원예")|| userinput.equals("원예생명과학")|| userinput.equals("원예생명")|| userinput.equals("원예생명과학과사무실")) {
            location[0] = 0;
            location[1] = 0;
        }
        //조건에 걸리지 않는 것
        else {
            location[0] = 0;
            location[1] = 0;
        }
        return location;
    }
}
