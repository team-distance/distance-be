package io.festival.distance.authuniversity.domain;


import static io.festival.distance.global.exception.ErrorCode.NOT_EXIST_SCHOOL;

import io.festival.distance.domain.studentcouncil.dto.response.SchoolLocation;
import io.festival.distance.global.exception.DistanceException;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.val;

@Getter
public enum University {
    GACHON("가천대학교", "gachon", false, 37.448213, 127.127850),
    KANGWON("강원대학교", "kangwon", false, 37.868406, 127.743567),
    KONKUK("건국대학교", "konkuk", false, 37.540641, 127.073775),
    KKU("건국대학교(글로컬)", "kku", false, 36.991532, 127.925885),
    GTEC("경기과학기술대학교", "gtec", false, 37.285548, 126.838979),
    KYONGGI("경기대학교", "kyonggi", false, 37.300731, 126.970667),
    KNU("경북대학교", "knu", false, 35.888000, 128.611741),
    GINUE("경인교육대학교", "ginue", false, 37.400372, 126.637125),
    KHU("경희대학교", "khu", false, 37.596205, 127.052017),
    KAYWON("계원예술대학교", "kaywon", false, 37.393560, 127.129527),
    KOREA("고려대학교", "korea", false, 37.589034, 127.033542),
    KW("광운대학교", "kw", false, 37.619521, 127.058034),
    DONGGUKK("동국대학교(경주)", "dongguk.ac", false, 35.845442, 129.212345),
    DONGDUK("동덕여자대학교", "dongduk", true, 37.606103, 127.038244),
    MJU("명지대학교", "mju", false, 37.225404, 127.187376),
    MJC("명지전문대학교", "mjc", false, 37.580029, 127.013038),
    KOOKMIN("국민대학교", "kookmin", false, 37.610545, 126.997503),
    DANKOOK("단국대학교", "dankook", false, 37.321649, 127.126409),
    DUKSUNG("덕성여자대학교", "duksung", true, 37.648657, 127.014509),
    DONGGUK("동국대학교", "dongguk.edu,dgu", false, 37.558524, 126.998027),
    SOGANG("서강대학교", "sogang", false, 37.550357, 126.940695),
    SKUNIV("서경대학교", "skuniv", false, 37.614149, 127.010769),
    SEOULTECH("서울과학기술대학교", "seoultech", false, 37.631409, 127.077004),
    SNUE("서울교육대학교", "snue", false, 37.485364, 127.014400),
    SNU("서울대학교", "snu", false, 37.459912, 126.951917),
    UOS("서울시립대학교", "uos", false, 37.583538, 127.058112),
    SWU("서울여자대학교", "swu", true, 37.629552, 127.090104),
    SKKU("성균관대학교", "skku", false, 37.587304, 126.994067),
    SUNGSHIN("성신여자대학교", "sungshin", true, 37.592846, 127.016291),
    SJU("세종대학교", "sju", false, 37.550325, 127.073807),
    SOOK("숙명여자대학교", "sookmyung", true, 37.543985, 126.964601),
    SOONG("숭실대학교", "soongsil", false, 37.496327, 126.957248),
    AJOU("아주대학교", "ajou", false, 37.282233, 127.046950),
    YONSEI("연세대학교", "yonsei", false, 37.565848, 126.938574),
    YNU("영남대학교", "ynu", false, 35.841196, 128.683669),
    EWHA("이화여자대학교", "ewhain", true, 37.562108, 126.946951),
    INU("인천대학교", "inu", false, 37.375548, 126.632638),
    ITC("인하공전대학교", "itc", false, 37.449996, 126.649918),
    INHA("인하대학교", "inha", false, 37.450000, 126.653050),
    JNU("전남대학교", "jnu", false, 35.176923, 126.906936),
    JBNU("전북대학교", "jbnu", false, 35.846119, 127.129106),
    CAU("중앙대학교", "cau", false, 37.505601, 126.957017),
    CHUNGBUK("충북대학교", "chungbuk", false, 36.629699, 127.456003),
    KNOU("한국방송통신대학교", "knou", false, 37.637309, 127.025990),
    KPU("한국산업기술대학교", "kpu", false, 37.340065, 126.731691),
    KARTS("한국예술종합학교", "karts", false, 37.612339, 126.997729),
    HUFS("한국외국어대학교", "hufs", false, 37.597846, 127.058197),
    KNSU("한국체육대학교", "knsu", false, 37.520552, 127.122090),
    HANYANG("한양대학교", "hanyang", false, 37.558537, 127.040837),
    ERICA("한양대학교(ERICA)", "hanyang", false, 37.295972, 126.835305),
    HONGIK("홍익대학교", "hongik", false, 37.551239, 126.924586),
    DGIST("dgist", "dgist", false, 35.774083, 128.548812),
    GIST("gist", "gist", false, 35.227643, 126.839401),
    KAIST("카이스트", "kaist", false, 36.372731, 127.362733),
    POSTECH("포항공과대학교", "postech", false, 36.011408, 129.323407),
    UNIST("unist", "unist", false, 35.583333, 129.187569),
    KMU("계명대학교", "kmu", false, 35.861203, 128.495026),
    CHOSUN("조선대학교", "chosun", false, 35.139179, 126.933446),
    GNU("경상대학교", "gnu", false, 35.153070, 128.098001),
    DONGA("동아대학교", "donga", false, 35.115406, 129.034149),
    DAEGU("대구대학교", "daegu", false, 35.874700, 128.748329),
    DEU("동의대학교", "deu", false, 35.131384, 129.085198),
    CNU("충남대학교", "cnu", false, 36.370239, 127.362529),
    BUKYONG("부경대학교", "bukyong", false, 35.138765, 129.100630),
    ISCU("서울사이버대학교", "iscu", false, 37.583092, 126.971396),
    HYCU("한양사이버대학교", "hycu", false, 37.558537, 127.040837),
    WONKWANG("원광대학교", "wonkwang", false, 35.973403, 126.957433),
    KHCU("경희사이버대학교", "khcu", false, 37.596205, 127.052017),
    SDU("서울디지털대학교", "sdu", false, 37.537889, 126.987115),
    BU("백석대학교", "bu", false, 36.801208, 127.075727),
    BC("부천대학교", "bc", false, 37.504128, 126.773210),
    CU("대구가톨릭대학교", "cu", false, 35.771579, 128.457456),
    KYWOMAN("한양여자대학교", "kywoman", true, 37.567635, 127.033765),
    HOSEO("호서대학교", "hoseo", false, 36.780265, 127.075676),
    YJC("영진전문대학교", "yjc", false, 35.938264, 128.562203),
    KONGJU("공주대학교", "kongju", false, 36.469109, 127.124643),
    KS("경성대학교", "ks", false, 35.140497, 129.102482),
    SHINGU("신구대학교", "shingu", false, 37.391699, 127.129413),
    HANNAM("한남대학교", "hannam", false, 36.354017, 127.457496),
    ULSAN("울산대학교", "ulsan", false, 35.543301, 129.256269),
    DAELIM("대림대학교", "daelim", false, 37.407327, 126.905442),
    DSC("동서울대학교", "dsc", false, 37.475058, 127.126267),
    CJU("청주대학교", "cju", false, 36.637826, 127.456116),
    KIT("경남정보대학교", "kit", false, 35.193400, 129.102093),
    DONGYANG("동양미래대학교", "dongyang", false, 37.492546, 126.957211),
    DHC("대구보건대학교", "dhc", false, 35.871804, 128.633482),
    YEONSUNG("연성대학교", "yeonsung", false, 37.274329, 126.869296),
    JJ("전주대학교", "jj", false, 35.846556, 127.129235),
    SEOIL("서일대학교", "seoil", false, 37.604598, 127.094725),
    INDUK("인덕대학교", "induk", false, 37.634024, 127.067024),
    CUK("고려사이버대학교", "cuk", false, 37.589034, 127.033542),
    YNK("영남이공대학교", "ync", false, 35.938362, 128.562269),
    JANGAN("장안대학교", "jangan", false, 37.240769, 127.080971),
    SCH("순천향대학교", "sch", false, 36.760494, 126.933211),
    BSCU("백석문화대학교", "bscu", false, 36.630749, 127.493227),
    KMCU("계명문화대학교", "kmcu", false, 35.858012, 128.494675),
    HANMA("경남대학교", "hanma", false, 35.183923, 128.090843),
    NSU("남서울대학교", "nsu", false, 36.769728, 127.211198),
    OSAN("오산대학교", "osan", false, 37.149194, 127.077124),
    SJCU("세종사이버대학교", "sjcu", false, 37.550325, 127.073807),
    JEJUNU("제주대학교", "jejunu", false, 33.455765, 126.561492),
    KBU("경복대학교", "kbu", false, 37.731275, 127.047380),
    MASAN("마산대학교", "masan", false, 35.213820, 128.583642),
    SUWON("수원대학교", "suwon", false, 37.222338, 126.976694),
    SANGJI("상지대학교", "sangji", false, 37.345129, 127.945432),
    SSC("수원과학대학교", "ssc", false, 37.281547, 127.026168),
    DONGSEO("동서대학교", "dongseo", false, 35.213107, 129.079659),
    HIT("대전보건대학교", "hit", false, 36.354528, 127.378021),
    SUNMOON("선문대학교", "sunmoon", false, 36.784418, 127.070973),
    YUHAN("유한대학교", "yuhan", false, 37.481797, 126.899699),
    KIC("경인여자대학교", "kic", true, 37.402018, 126.737034),
    PCU("배재대학교", "pcu", false, 36.329567, 127.388584),
    SEOYOUNG("서영대학교", "seoyoung", false, 35.148249, 126.915332),
    WSU("우송대학교", "wsu", false, 36.332087, 127.454297),
    DJU("대전대학교", "dju", false, 36.369443, 127.434541),
    JMAIL("중부대학교", "jmail", false, 36.960000, 127.574739),
    UT("한국교통대학교", "ut", false, 36.968588, 127.904366),
    INJE("인제대학교", "inje", false, 35.317997, 129.186509),
    DIT("동의과학대학교", "dit", false, 35.147874, 129.014791),
    HANBAT("한밭대학교", "hanbat", false, 36.355470, 127.380832),
    HANSUNG("한성대학교", "hansung", false, 37.582139, 127.010940),
    SYUIN("삼육대학교", "syuin", false, 37.642958, 127.102424),
    KAU("한국항공대학교", "kau", false, 37.596421, 126.864506),
    SEOULARTS("서울예술대학교", "seoularts", false, 37.452160, 126.792400),
    PUSAN("부산대학교", "pusan", false, 35.232075, 129.079593),
    SANGMYUNG("상명대학교", "sangmyung", false, 37.582095, 126.995864);

    private final String name;
    private final String domain;
    private final boolean isWomen;
    private final Double latitude;
    private final Double longitude;

    University(String name, String domain, boolean isWomen, Double latitude, Double longitude) {
        this.name = name;
        this.domain = domain;
        this.isWomen = isWomen;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public static Map<University, University> createUnmodifiableMap() {
        EnumMap<University, University> enumMap = new EnumMap<>(University.class);
        for (University univ : University.values()) {
            enumMap.put(univ, univ);
        }
        return Collections.unmodifiableMap(enumMap);
    }

    public final static Map<University, University> UNIV_MAP = createUnmodifiableMap();

    public static String getDomainByName(String name) { //순천향대학교 -> sch 반환
        for (Map.Entry<University, University> entry : UNIV_MAP.entrySet()) {
            if (entry.getKey().getName().equals(name)) {
                return entry.getKey().getDomain();
            }
        }
        throw new DistanceException(NOT_EXIST_SCHOOL);
    }

    public static boolean getIsWomen(String name) {
        for (Map.Entry<University, University> entry : UNIV_MAP.entrySet()) {
            if (entry.getKey().getName().equals(name)) {
                return entry.getKey().isWomen;
            }
        }
        throw new DistanceException(NOT_EXIST_SCHOOL);
    }

    public static List<University> getUniversity(String school) {
        return Arrays.stream(University.values())
            .filter(university -> university.getName().startsWith(school))
            .collect(Collectors.toList());
    }

    public static SchoolLocation getSchoolLocation(String school){
        for(Map.Entry<University,University> entry : UNIV_MAP.entrySet()){
            if(entry.getKey().getName().equals(school)){
                return SchoolLocation.builder()
                    .latitude(entry.getKey().latitude)
                    .longitude(entry.getKey().longitude)
                    .build();
            }
        }
        throw new DistanceException(NOT_EXIST_SCHOOL);
    }
}