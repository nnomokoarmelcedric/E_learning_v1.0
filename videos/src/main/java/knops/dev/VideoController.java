package knops.dev;

import lombok.extern.slf4j.Slf4j;
//import org.hibernate.mapping.List;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/videos")
public class VideoController {
    @Autowired
    private VideoService videoService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadVideo(
            @RequestParam("file") MultipartFile file,
            @RequestParam("name") String videoName,
            @RequestParam("description") String videoDescription
    ) {
        try {
            videoService.uploadVideo(videoName,videoDescription, file);
            return ResponseEntity.ok("Video uploaded successfully!");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("A video with the same name already exists.");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading video.");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<byte[]> getVideo(@PathVariable Long id) {
        return videoService.getVideo(id);
    }

    @GetMapping
    public ResponseEntity< List<Video> > getAllVideos() {
        List <Video> videos = videoService.getAllVideos();
        return ResponseEntity.ok(videos);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteVideo(@PathVariable Long id) {
        boolean deleted = videoService.deleteVideoById(id);

        if (deleted) {
            return ResponseEntity.ok("Video deleted successfully!");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateVideo(
            @PathVariable Long id,
            @RequestParam("name") String newName,
            @RequestParam("file") MultipartFile newFile,
            @RequestParam("description") String newDescription

    ) {
        try {
            boolean updated = videoService.updateVideo(id, newName,newDescription, newFile);

            if (updated) {
                return ResponseEntity.ok("Video updated successfully!");
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating video.");
        }
    }

}
