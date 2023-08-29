package TradeGoodsService.service;

import TradeGoodsService.repository.trade.GoodsImageRepository;
import TradeGoodsService.repository.trade.GoodsRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class FileStoreServiceTest {

    @Autowired
    FileStoreService fileStoreService;
    @Autowired
    GoodsImageRepository goodsImageRepository;
    @Autowired
    GoodsRepository goodsRepository;

    @Test
    void storeFileDistinct() {
    }

    @Test
    void getImageUrls() {
    }
}