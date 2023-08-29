package TradeGoodsService.file;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Base64;
import java.util.Objects;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

@Service
public class ImageResizeService {

    public String runImageSize(MultipartFile multipartFile){
        try {
            BufferedImage imageResized = imageResize(multipartFile);// 이미지 리사이즈 실행
            return encodeToString(imageResized, "jpg");
        } catch (IOException e) {
            e.printStackTrace();
            //e.getMessage();
        }
        return null;
    }

    /* 파일 정보와 리사이즈 값 정하는 메소드 */
    private BufferedImage imageResize(MultipartFile multipartFile) throws IOException {
        File file = multipartFileToFile(multipartFile);  //리사이즈할 파일 경로
        InputStream inputStream = new FileInputStream(file);
        int width = 480;//1280; // 리사이즈할 가로 길이
        int height = 480;//720; // 리사이즈할 세로 길이

        return resize(inputStream, width, height);
    }

    /* 리사이즈 실행 메소드 */
    private BufferedImage resize(InputStream inputStream, int width, int height)
            throws IOException {

        BufferedImage inputImage = ImageIO.read(inputStream);  // 받은 이미지 읽기

        BufferedImage outputImage = new BufferedImage(width, height, inputImage.getType());
        // 입력받은 리사이즈 길이와 높이

        Graphics2D graphics2D = outputImage.createGraphics();
        graphics2D.drawImage(inputImage, 0, 0, width, height, null); // 그리기
        graphics2D.dispose(); // 자원해제

        return outputImage;
    }

    private File multipartFileToFile(MultipartFile multipartFile) throws IOException {
        File file = new File(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        multipartFile.transferTo(file);
        return file;
    }

    private String encodeToString(BufferedImage image, String type) {
        String imageString = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        try {
            ImageIO.write(image, type, bos);
            byte[] imageBytes = bos.toByteArray();
            imageString = Base64.getEncoder().encodeToString(imageBytes);
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imageString;
    }
}
