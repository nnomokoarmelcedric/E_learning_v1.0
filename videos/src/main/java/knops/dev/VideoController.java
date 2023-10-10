package knops.dev;

import lombok.extern.slf4j.Slf4j;
import java.io.*;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static org.springframework.http.HttpHeaders.ACCEPT_RANGES;
import static org.springframework.http.HttpHeaders.*;

@Slf4j
@RestController
@RequestMapping("/videos")
public class VideoController {
    @Autowired
    VideoService videoService;
    @Autowired
    private VideoRepository videoRepository;

    public Integer defaultChunkSize = 3145728;

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





    @GetMapping
    public ResponseEntity<List<VideoProjection>> getAllVideos() {
        List<VideoProjection> videos = videoService.getAllVideos();
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
            @RequestParam("description") String newDescription

    ) {
        try {
            boolean updated = videoService.updateVideo(id, newName,newDescription);

            if (updated) {
                return ResponseEntity.ok("Video updated successfully!");
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating video.");
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<byte[]> readChunk(
            @RequestHeader(value = RANGE, required = false) String range,
            @PathVariable int id
    ) throws IOException {
        Range parsedRange = Range.parseHttpRangeString(range, defaultChunkSize);
        VideoService.ChunkWithMetadata chunkWithMetadata = videoService.fetchChunk(id, parsedRange);
        Optional<Video> videoTest = videoRepository.findById(chunkWithMetadata.id());
        Video video = videoTest.get();
        return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
                .header(CONTENT_TYPE, video.getContentType())
                .header(ACCEPT_RANGES, "bytes")
                .header(CONTENT_LENGTH, calculateContentLengthHeader(parsedRange, video.getSize()))
                .header(CONTENT_RANGE, constructContentRangeHeader(parsedRange, video.getSize()))
                .body(chunkWithMetadata.chunk());
    }
    private String calculateContentLengthHeader(knops.dev.Range range, long fileSize) {
        return String.valueOf(range.getRangeEnd(fileSize) - range.getRangeStart() + 1);
    }

    private String constructContentRangeHeader(knops.dev.Range range, long fileSize) {
        return  "bytes " + range.getRangeStart() + "-" + range.getRangeEnd(fileSize) + "/" + fileSize;
    }
}
