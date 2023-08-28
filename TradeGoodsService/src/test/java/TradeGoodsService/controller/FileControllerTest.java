package TradeGoodsService.controller;

import TradeGoodsService.service.FileStoreService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

/**
 * @WebMvcTest
 * 여러 스프링 테스트 어노테이션 중, Web(Spring MVC)에 집중할 수 있는 어노테이션이다.
 * @SpringBootTest는 모든 빈을 로드한다. 따라서 Controller 레이어만 테스트 하고 싶다면 @WebMvcTest를 사용하는 것이 좋다.
 */
@WebMvcTest(FileController.class)
@ExtendWith(MockitoExtension.class)
class FileControllerTest {
    @Autowired
    MockMvc mvc;

    @MockBean
    FileStoreService fileStoreService;

    @Test
    void saveImage() {
    }

    @Test
    void loadImagesUrls() {
        //BDDMockito.given();
    }
}