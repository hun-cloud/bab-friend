package babfriend.api.common.service;

import babfriend.api.user.dto.UserUpdateDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Slf4j
@Service
public class FileUtils {

    public static final String url = "https://bab-friend-back.store/image/";
    private static final String PROFILE_PICTURE_PATH = "/home/uploadedImage/";
    // 이미지 url : /image/**
    // 이미지 업데이트
    public String updateImage(UserUpdateDto userUpdateDto) {

        MultipartFile profileImage = userUpdateDto.getProfileImageUrl();

        if(!profileImage.isEmpty()) {
            String originalFileName = profileImage.getOriginalFilename();
            String originalFIleExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
            String storedFileName = UUID.randomUUID() + originalFIleExtension;
            // File file = new File(PROFILE_PICTURE_PATH + storedFileName);
            Path path = Paths.get(PROFILE_PICTURE_PATH + storedFileName).toAbsolutePath();

            try {
                profileImage.transferTo(path);
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e.getMessage());
            }

            return storedFileName;
        }
        return null;
    }

    // UUID로 사진 파일 가져오기
    public static ByteArrayResource getImage(String fileName) {
        try {
            Path filePath = Paths.get(PROFILE_PICTURE_PATH).resolve(fileName);

            if (Files.exists(filePath)) {
                byte[] fileContent = Files.readAllBytes(filePath);

                return new ByteArrayResource(fileContent);
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }

        return null;
    }

}
