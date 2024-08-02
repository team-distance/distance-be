package io.festival.distance.authuniversity.domain;


import static io.festival.distance.global.exception.ErrorCode.NOT_EXIST_SCHOOL;

import io.festival.distance.global.exception.DistanceException;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.Getter;

@Getter
public enum University {
    GACHON("가천대학교","gachon",false), KANGWON("강원대학교","kangwon",false), KONKUK("건국대학교","konkuk",false), KKU("건국대학교(글로컬)","kku",false),
    GTEC("경기과학기술대학교","gtec",false),KYONGGI("경기대학교","kyonggi",false), KNU("경북대학교","knu",false), GINUE("경인교육대학교","ginue",false),
    KHU("경희대학교","khu",false), KAYWON("계원예술대학교","kaywon",false), KOREA("고려대학교","korea",false), KW("광운대학교","kw",false),
    DONGGUKK("동국대학교(경주)","dongguk.ac",false), DONGDUK("동덕여자대학교","dongduk",true),MJU("명지대학교","mju",false), MJC("명지전문대학교","mjc",false),
    KOOKMIN("국민대학교","kookmin",false),DANKOOK("단국대학교","dankook",false), DUKSUNG("덕성여자대학교","duksung",true), DONGGUK("동국대학교","dongguk.edu,dgu",false),
    SOGANG("서강대학교","sogang",false),SKUNIV("서경대학교","skuniv",false), SEOULTECH("서울과학기술대학교","seoultech",false), SNUE("서울교육대학교","snue",false),
    SNU("서울대학교","snu",false), UOS("서울시립대학교","uos",false), SWU("서울여자대학교","swu",true), SKKU("성균관대학교","skku",false),
    SUNGSHIN("성신여자대학교","sungshin",true),SJU("세종대학교","sju",false), SOOK("숙명여자대학교","sookmyung",true), SOONG("숭실대학교","soongsil",false),
    AJOU("아주대학교","ajou",false), YONSEI("연세대학교","yonsei",false), YNU("영남대학교","ynu",false), EWHA("이화여자대학교","ewhain",true),
    INU("인천대학교","inu",false),ITC("인하공전대학교","itc",false), INHA("인하대학교","itc",false), JNU("전남대학교","jnu",false),
    JBNU("전북대학교","jbnu",false), CAU("중앙대학교","cau",false), CHUNGBUK("충북대학교","chungbuk",false), KNOU("한국방송통신대학교","knou",false),
    KPU("한국산업기술대학교","kpu",false),KARTS("한국예술종합대학교","karts",false), HUFS("한국외국어대학교","hufs",false), KNSU("한국체육대학교","knsu",false),
    HANYANG("한양대학교","hanyang",false), ERICA("한양대학교(ERICA)","hanyang",false), HONGIK("홍익대학교","hongik",false), DGIST("dgist","dgist",false),
    GIST("gist","gist",false),KAIST("카이스트","kaist",false), POSTECH("포항공과대학교","postech",false), UNIST("unist","unist",false),
    KMU("계명대학교","kmu",false), CHOSUN("조선대학교","chosun",false), GNU("경상대학교","GNU",false),DONGA("동아대학교","donga",false),
    DAEGU("대구대학교","daegu",false), DEU("동의대학교","deu",false), CNU("충남대학교","cnu",false),BUKYONG("부경대학교","bukyong",false),
    ISCU("서울사이버대학교","iscu",false), HYCU("한양사이버대학교","hycu",false), WONKWANG("원광대학교","wonkwang",false),KHCU("경희사이버대학교","khcu",false),
    SDU("서울디지털대학교","sdu",false), BU("백석대학교","bu",false), BC("부천대학교","bc",false),CU("대구가톨릭대학교","donga",false),
    KYWOMAN("한양여자대학교","kywoman",true), HOSEO("호서대학교","hoseo",false), YJC("영진전문대학교","yjc",false),KONGJU("공주대학교","kongju",false),
    KS("경성대학교","ks",false), SHINGU("신구대학교","shingu",false), HANNAM("한남대학교","hannam",false),ULSAN("울산대학교","ulsan",false),
    DAELIM("대림대학교","daelim",false), DSC("동서울대학교","dsc",false), CJU("청주대학교","cju",false),KIT("경남정보대학교","kit",false),
    DONGYANG("동양미래대학교","dongyang",false), DHC("대구보건대학교","dhc",false), YEONSUNG("연성대학교","yeonsung",false),JJ("전주대학교","jj",false),
    SEOIL("서일대학교","seoil",false), INDUK("인덕대학교","induk",false), CUK("고려사이버대학교","cuk",false),YNK("영남이공대학교","ync",false),
    JANGAN("장안대학교","jangan",false), SCH("순천향대학교","sch",false), BSCU("백석문화대학교","bscu",false),KMCU("계명문화대학교","kmcu",false),
    HANMA("경남대학교","hanma",false), NSU("남서울대학교","nsu",false), OSAN("오산대학교","osan",false),SJCU("세종사이버대학교","sjcu",false),
    JEJUNU("제주대학교","jejunu",false), KBU("경복대학교","kbu",false), MASAN("마산대학교","masan",false),SUWON("수원대학교","suwon",false),
    SANGJI("상지대학교","sangji",false), SSC("수원과학대학교","ssc",false), DONGSEO("동서대학교","dongseo",false),HIT("대전보건대학교","hit",false),
    SUNMOON("선문대학교","sunmoon",false), YUHAN("유한대학교","yuhan",false), KIC("경인여자대학교","kic",true),PCU("배재대학교","pcu",false),
    SEOYOUNG("서영대학교","seoyoung",false), WSU("우송대학교","wsu",false), DJU("대전대학교","dju",false),JMAIL("중부대학교","jmail",false),
    UT("한국교통대학교","UT",false), INJE("인제대학교","inje",false), DIT("동의과학대학교","dit",false),HANBAT("한밭대학교","hanbat",false),
    HANSUNG("한성대학교","hansung",false),SYUIN("삼육대학교","syuin",false),KAU("한국항공대학교","kau",false), SEOULARTS("서울예술대학교", "seoularts",false),
    PUSAN("부산대학교","pusan",false), SANGMYUNG("상명대학교","sangmyung",false);
    private final String name;
    private final String domain;
    private final boolean isWomen;

    University(String name, String domain, boolean isWomen) {
        this.name = name;
        this.domain = domain;
        this.isWomen = isWomen;
    }

    public static Map<University, University> createUnmodifiableMap() {
        EnumMap<University, University> enumMap = new EnumMap<>(University.class);
        for (University univ : University.values()) {
            enumMap.put(univ, univ);
        }
        return Collections.unmodifiableMap(enumMap);
    }

    public final static Map<University,University> UNIV_MAP=createUnmodifiableMap();

    public static String getDomainByName(String name) { //순천향대학교 -> sch 반환
        for (Map.Entry<University, University> entry : UNIV_MAP.entrySet()) {
            if (entry.getKey().getName().equals(name)) {
                return entry.getKey().getDomain();
            }
        }
        throw new DistanceException(NOT_EXIST_SCHOOL);
    }

    public static boolean getIsWomen(String name){
        for(Map.Entry<University,University> entry : UNIV_MAP.entrySet()){
            if(entry.getKey().getName().equals(name)){
                return entry.getKey().isWomen;
            }
        }
        throw new DistanceException(NOT_EXIST_SCHOOL);
    }

    public static List<University> getUniversity(String school){
        return Arrays.stream(University.values())
            .filter(university -> university.getName().startsWith(school))
            .collect(Collectors.toList());
    }
}