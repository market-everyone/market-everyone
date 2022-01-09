package web.common.util;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

public class ImageUtil {

    private String uploadPath = new File("module-web/src/main/resources/static/images").getAbsolutePath();


    public String uploadImage(HttpServletResponse res, @RequestParam MultipartFile file, Long id) throws IOException {

        OutputStream out = null;

        // 인코딩
        res.setCharacterEncoding("UTF-8");
        res.setContentType("text/html;charset=utf-8");

        try {

            String fileName = file.getOriginalFilename();
            byte[] bytes = file.getBytes();

            // 업로드 경로
            String imageUploadPath = uploadPath + File.separator + id + File.separator + fileName;

            String mkdirPath = uploadPath + File.separator + id;

            makeDir(mkdirPath);

            out = new FileOutputStream(imageUploadPath);
            out.write(bytes);
            out.flush();

            return fileName;

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

    public void makeDir(String path) {

        File folder = new File(path);

        if (!folder.exists()) {
            try {
                folder.mkdir();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
