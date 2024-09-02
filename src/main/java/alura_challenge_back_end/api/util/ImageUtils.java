package alura_challenge_back_end.api.util;

import java.io.ByteArrayOutputStream;
import java.util.zip.Inflater;
import javax.imageio.ImageIO;

import org.springframework.web.multipart.MultipartFile;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ImageUtils {


    
    public static byte[] compressImage(MultipartFile file) throws IOException {
        BufferedImage originalImage = ImageIO.read(file.getInputStream());

        // Reduz a qualidade ou tamanho da imagem
        BufferedImage compressedImage = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB); 
        Graphics2D g2d = compressedImage.createGraphics();
        g2d.drawImage(originalImage, 0, 0, 100, 100, null);
        g2d.dispose();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(compressedImage, file.getContentType().replace("image/", ""), baos);
        return baos.toByteArray();
    }



    public static byte[] decompressImage(byte[] data) {
        Inflater inflater = new Inflater();
        inflater.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] tmp = new byte[4*1024];
        try {
            while (!inflater.finished()) {
                int count = inflater.inflate(tmp);
                outputStream.write(tmp, 0, count);
            }
            outputStream.close();
        } catch (Exception ignored) {
        }
        return outputStream.toByteArray();
    }

}