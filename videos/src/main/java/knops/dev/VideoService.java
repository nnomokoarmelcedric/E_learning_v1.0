package knops.dev;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class VideoService {
    @Autowired
    private VideoRepository videoRepository;

    public void uploadVideo(String videoName,String videoDescription, MultipartFile file) throws IOException {
        Optional<Video> existingVideo = videoRepository.findByName(videoName);

        if (existingVideo.isPresent()) {
            throw new IllegalArgumentException("A video with the same name already exists.");
        }

        Video video = new Video();
        video.setName(videoName);
        video.setDescription(videoDescription);
        video.setContentType(file.getContentType());
        video.setData(file.getBytes());
        videoRepository.save(video);
    }

    public boolean deleteVideoById(Long id) {
        Optional<Video> videoOptional = videoRepository.findById(id);
        if (videoOptional.isPresent()) {
            videoRepository.delete(videoOptional.get());
            return true;
        } else {
            return false;
        }
    }
    public ResponseEntity<byte[]> getVideo(Long id) {
        Optional<Video> videoOptional = videoRepository.findById(id);
        if (videoOptional.isPresent()) {
            Video video = videoOptional.get();
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(video.getContentType()))
                    .body(video.getData());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    public List<Video> getAllVideos() {
        return videoRepository.findAll();
    }


    public boolean updateVideo(Long id, String newName,String newDescription, MultipartFile newFile) throws IOException {
        Optional<Video> videoOptional = videoRepository.findById(id);

        if (videoOptional.isPresent()) {
            Video video = videoOptional.get();
            video.setName(newName);
            video.setDescription(newDescription);
            video.setContentType(newFile.getContentType());
            video.setData(newFile.getBytes());
            videoRepository.save(video);
            return true;
        } else {
            return false;
        }
    }
}