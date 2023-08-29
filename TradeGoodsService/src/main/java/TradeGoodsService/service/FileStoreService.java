package TradeGoodsService.service;

import TradeGoodsService.entity.trade.Goods;
import TradeGoodsService.entity.trade.GoodsImage;
import TradeGoodsService.file.awsS3.S3UploadService;
import TradeGoodsService.repository.trade.GoodsImageRepository;
import TradeGoodsService.repository.trade.GoodsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FileStoreService {

    @Value("${file.dir}")
    private String fileDir; //설정해둔 경로

    private final S3UploadService s3UploadService;
    private final GoodsImageRepository goodsImageRepository;
    private final GoodsRepository goodsRepository;

    //상품에 이미 사진이 첨부되어 있다면 지우고 다시 저장
    @Transactional
    public void storeFileDistinct(List<MultipartFile> multipartFiles, Long goodsId) {
        Optional<List<GoodsImage>> goodsImages = goodsImageRepository.findByGoodsId(goodsId);
        if(goodsImages.isPresent()){
            List<GoodsImage> images = goodsImages.orElseThrow();
            for (GoodsImage image : images) {
                String imageName = image.getImageName();
                s3UploadService.deleteImage(imageName);
            }
            goodsImageRepository.deleteByGoodsId(goodsId);
        }
        Goods goods = goodsRepository.findById(goodsId).orElseThrow();
        multipartFiles.forEach(multipartFile -> {
            try {
                goodsImageRepository.save(new GoodsImage(storeFile(multipartFile), goods));
            } catch (IOException e) {
                throw new IllegalArgumentException();
            }
        });

    }
    @Transactional
    public List<String> getImageUrls(Long goodsId) throws Exception {
        Optional<List<GoodsImage>> byGoodsId = goodsImageRepository.findByGoodsId(goodsId);
        if(byGoodsId.isPresent()){
            List<GoodsImage> goodsImages = byGoodsId.orElseThrow();
            return goodsImages.stream().map(goodsImage -> s3UploadService.getImageUrl(goodsImage.getImageName())).collect(Collectors.toList());
        }
        throw new Exception();
    }

    public void deleteImage(String filename){
        s3UploadService.deleteImage(filename);
    }

    private String storeFile(MultipartFile multipartFile) throws IOException {
        if (!multipartFile.isEmpty()) { //파일이 존재한다면
            //원본파일 이름을 가져와
            String originalFilename = multipartFile.getOriginalFilename();

            String uuid = UUID.randomUUID().toString();
            String ext = extracted(originalFilename);
            //UUID와 확장명을 합친다
            String storeFileName = uuid + "." + ext;
            //설정해둔 저장소에 저장
            s3UploadService.saveFile(multipartFile, storeFileName);

            return storeFileName;//변경된 이름을 반환
        }
        throw new MultipartException("multipartFile is Null");
    }



    private String extracted(String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        return originalFilename.substring(pos + 1);
    }

}
