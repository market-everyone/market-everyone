package web.common.util;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.UUID;

public class ImageUtil {

    private String uploadPath = new File("module-web/src/main/resources/static/images").getAbsolutePath();


    public String uploadImage(HttpServletRequest req, HttpServletResponse res, @RequestParam MultipartFile upload) throws IOException {

        UUID uuid = UUID.randomUUID();

        OutputStream out = null;

        // 인코딩
        res.setCharacterEncoding("UTF-8");
        res.setContentType("text/html;charset=utf-8");

        try {

            String fileName = upload.getOriginalFilename();
            byte[] bytes = upload.getBytes();

            if ((fileName.equals("")) || (fileName == null)) {
                return "";
            }

            // 업로드 경로
            String imageUploadPath = uploadPath + File.separator + fileName;

            String dbFilePath = "/images/" + fileName;

            out = new FileOutputStream(new File(imageUploadPath));
            out.write(bytes);
            out.flush();

            return dbFilePath;

        } catch (IOException e) {
            e.printStackTrace();
            return "error";
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public void deleteImage(String path) {

        File file = new File(path);
        if (file.exists()) {
            file.delete();
        }
    }
}
