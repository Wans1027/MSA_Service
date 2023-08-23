package MemberManagement.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
class GeoJsonParseTest {
    @Autowired
    TownSearchService geoJsonParse;

    @Test
    void geoJsonRead() {
        geoJsonParse.geoJsonRead(127.068934, 37.201136,8d);
    }

    @Test
    void isPoint() throws IOException {
       geoJsonParse.findMyLocation(127.068934, 37.201136);
    }
}