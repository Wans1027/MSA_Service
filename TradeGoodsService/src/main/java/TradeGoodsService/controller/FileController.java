package TradeGoodsService.controller;

import TradeGoodsService.entity.trade.Result;
import TradeGoodsService.service.FileStoreService;
import TradeGoodsService.service.GoodsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
//@RequestMapping("/goods")
public class FileController {

    private final FileStoreService fileStoreService;
    private final GoodsService goodsService;

    @PostMapping("/images/new/{goodsId}")
    public void saveImage(@RequestParam("file") List<MultipartFile> form, @PathVariable("goodsId") Long goodsId) throws IOException {
        if(form.isEmpty()) return;
        fileStoreService.storeFileDistinct(form, goodsId);
    }
    @GetMapping("/images/{goodsId}")
    public ResponseEntity<Result<String>> loadImagesUrls(@PathVariable("goodsId") Long goodsId){
        try {
            List<String> imageUrls = fileStoreService.getImageUrls(goodsId);
            return new ResponseEntity<>(new Result<>(imageUrls, imageUrls.size()), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
