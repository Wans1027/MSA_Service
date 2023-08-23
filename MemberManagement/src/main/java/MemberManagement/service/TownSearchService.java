package MemberManagement.service;

import MemberManagement.repository.MemberRepository;
import MemberManagement.repository.TownRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.geotools.geojson.geom.GeometryJSON;
import org.geotools.geometry.jts.GeometryBuilder;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.locationtech.jts.geom.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


@Service
@Slf4j
@RequiredArgsConstructor
public class TownSearchService {

    private final MemberRepository memberRepository;
    private final TownRepository townRepository;

    public List<List<String>> geoJsonRead(double positionX, double positionY, double radius){
        // ex) `src/main/resource/providers.json`에 위치한 파일을 가져오려면 다음과 같이 코드를 작성한다.
        ClassPathResource resource = new ClassPathResource("HangJeongDong_ver20230701.geojson");
        List<List<String>> resultList = new ArrayList<>();
        try {
            Path path = Paths.get(resource.getURI());
            List<String> content = Files.readAllLines(path);
            int stop = content.size()-2;
            for (int i = 5; i < stop; i++) {
                String jsonData = content.get(i).substring(0, content.get(i).length() - 1);
                boolean polygon = polygon(positionX, positionY, jsonData, radius);
                if(polygon) {
                    boolean myLocation = isMyLocation(positionX, positionY, jsonData);
                    List<String> townInfo = jsonParse(jsonData);
                    townInfo.add(String.valueOf(myLocation));
                    resultList.add(townInfo);
                    System.out.println(townInfo.get(0));
                    System.out.println(townInfo.get(1));
                    System.out.println(townInfo.get(2));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultList;
    }

    public void findMyLocation(double positionX, double positionY){
        // ex) `src/main/resource/providers.json`에 위치한 파일을 가져오려면 다음과 같이 코드를 작성한다.
        ClassPathResource resource = new ClassPathResource("HangJeongDong_ver20230701.geojson");
        try {
            Path path = Paths.get(resource.getURI());
            List<String> content = Files.readAllLines(path);
            int stop = content.size()-2;
            for (int i = 5; i < stop; i++) {
                boolean polygon = isMyLocation(positionX,positionY,content.get(i).substring(0, content.get(i).length() - 1));
                if(polygon) {
                    List<String> townInfo = jsonParse(content.get(i).substring(0, content.get(i).length() - 1));
                    System.out.println(townInfo.get(0));
                    System.out.println(townInfo.get(1));
                    break;
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean polygon(double positionX, double positionY, String loc, double radius) throws IOException {
        //File initialFile = new File("src/main/resources/HangJeongDong_ver20230701.geojson");
        //InputStream targetStream = new FileInputStream(initialFile);
        GeometryJSON g = new GeometryJSON();
        MultiPolygon multiPolygon = null;
        try{
            multiPolygon = g.readMultiPolygon(loc);
        }catch (IOException ioException){
            return false;
        }

        GeometryFactory gf = new GeometryFactory();
        GeometryBuilder gb = new GeometryBuilder(gf);
        Polygon circle = gb.circle(positionX, positionY, radius/133.33d, 20);

        return circle.contains(multiPolygon);
    }

    private boolean isMyLocation(double positionX, double positionY, String loc) throws IOException {
        GeometryJSON g = new GeometryJSON();
        MultiPolygon multiPolygon = g.readMultiPolygon(loc);
        GeometryFactory gf = new GeometryFactory();
        return multiPolygon.contains(gf.createPoint(new Coordinate(positionX, positionY)));
    }

    private List<String> jsonParse(String loc) throws org.json.simple.parser.ParseException {
        JSONParser jsonParser = new JSONParser();
        Object obj = jsonParser.parse(loc);

        JSONObject jsonObj = (JSONObject) obj;
        jsonObj = (JSONObject)jsonParser.parse(jsonObj.get("properties").toString());
        List<String> propList = new ArrayList<>();
        propList.add(jsonObj.get("temp").toString());
        propList.add(jsonObj.get("adm_cd8").toString());
        return propList;

    }
    //https://github.com/vuski/admdongkor
    /* 상세
     * 통계청 통계지리정보서비스에서 제공하는 행정동 경계 파일을 바탕으로, 행정동 변경 이력을 수정하여 반영한 파일입니다.
     * geojson 형식이며, 좌표계는 WGS84 (EPSG:4326) 입니다.
     * geojson 및 csv 파일 인코딩 형식은 UTF-8입니다.
     * topology 정합성을 검토하였습니다.(폴리곤 경계끼리 잘 맞습니다)
     * 속성의 adm_cd 는 통계청에서 사용하는 7자리의 [한국행정구역분류코드]입니다.
     * 속성의 adm_cd2 는 행정안전부 사용하는 10자리의 [행정기관코드]입니다.(2018.07.24 업데이트 파일부터 적용)
     * 속성의 adm_cd8 는 통계청에서 사용하는 8자리의 새로운 [한국행정구역분류코드]입니다. (2022.01.01 업데이트 파일부터 적용)
     * 속성의 adm_nm 은 통계청에서 사용하는 전국 행정동 이름입니다.
     */
    //http://data.nsdi.go.kr/dataset?tags=%EA%B2%BD%EA%B3%84
}
