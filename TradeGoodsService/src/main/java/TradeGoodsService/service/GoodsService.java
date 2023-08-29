package TradeGoodsService.service;

import TradeGoodsService.entity.trade.Goods;
import TradeGoodsService.entity.trade.GoodsImage;
import TradeGoodsService.file.ImageResizeService;
import TradeGoodsService.repository.trade.GoodsImageRepository;
import TradeGoodsService.repository.trade.GoodsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GoodsService {
    private final GoodsRepository goodsRepository;
    private final GoodsImageRepository goodsImageRepository;
    private final ImageResizeService imageResizeService;
    private final FileStoreService fileStoreService;

    @Transactional
    public Long saveGoods(Long sellerId, Integer areaId, Integer sellPrice, Integer categoryId, String title, String description, Goods.Status status){
        Goods goods = Goods.builder()
                .title(title)
                .status(status)
                .categoryId(categoryId)
                .sellerId(sellerId)
                .sellingAreaId(areaId)
                .sellPrice(sellPrice)
                .description(textToBase64Encode(description)).build();

        Goods savedGoods = goodsRepository.save(goods);
        return savedGoods.getId();
    }

    @Transactional
    public Long updateGoods(Long goodsId, Integer sellPrice, Integer categoryId, String title, String description, Goods.Status status) {
        Goods goods = goodsRepository.findById(goodsId).orElseThrow();
        goods.setCategoryId(categoryId);
        goods.setTitle(title);
        goods.setSellPrice(sellPrice);
        goods.setDescription(textToBase64Encode(description));
        goods.setStatus(status);
        return goods.getId();
    }

    @Transactional
    public List<Goods> loadGoodsByAreaIds(List<Integer> areaIds) {
        return goodsRepository.findGoodsByAreaIdsOrderByDate(areaIds);
    }

    @Transactional
    public void deleteGoods(Long goodsId) {
        List<GoodsImage> goodsImages = goodsImageRepository.findByGoodsId(goodsId).orElseThrow();
        goodsImages.forEach(goodsImage -> fileStoreService.deleteImage(goodsImage.getImageName()));
        goodsRepository.deleteById(goodsId);
    }

    public void setThumbnail(Long goodsId, MultipartFile multipartFile) {
        Goods goods = goodsRepository.findById(goodsId).orElseThrow();
        goods.setGoodsThumbnail(imageResizeService.runImageSize(multipartFile));

    }

    private String textToBase64Encode(String text) {
        return Base64.getEncoder().encodeToString(text.getBytes());
    }

    private String decodeText(String encodedText) {
        byte[] decodedBytes = Base64.getDecoder().decode(encodedText);
        return new String(decodedBytes);
    }

    private String imageToBase64Encode(MultipartFile multipartFile) throws IOException {
        byte[] imageBytes = multipartFile.getBytes();
        return Base64.getEncoder().encodeToString(imageBytes);
    }
}
