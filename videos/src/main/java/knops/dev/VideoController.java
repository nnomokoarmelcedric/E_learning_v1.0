package knops.dev;

import io.netty.handler.codec.http.HttpConstants;
import lombok.extern.slf4j.Slf4j;
//import org.hibernate.mapping.List;
import java.io.*;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static org.springframework.http.HttpHeaders.ACCEPT_RANGES;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.header;
import static org.springframework.http.HttpHeaders.*;

@Slf4j
@RestController
@RequestMapping("/videos")
public class VideoController {
    @Autowired
    private VideoService videoService;
    @Autowired
    private VideoRepository videoRepository;

//    @Value("${photon.streaming.default-chunk-size}")
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

//    @GetMapping("/{id}")
//    public ResponseEntity<byte[]> getVideo(@PathVariable Long id) {
//        byte[] videoData = videoService.getVideo(id).getBody(); // Remplacez par la méthode réelle pour obtenir les données de la vidéo
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.valueOf("video/mp4")); // Remplacez par le type de contenu approprié
//
//        // Convertir les données binaires en URL
//        String base64Video = Base64.getEncoder().encodeToString(videoData);
//        String videoUrl = "data:video/mp4;base64," + base64Video; // Remplacez le type de contenu par celui approprié
//
//        // Retourner l'URL dans la réponse
//        return ResponseEntity.ok()
//                .headers(headers)
//                .body(videoUrl.getBytes());
//    }
//@GetMapping("/{id}")
//public ResponseEntity<Resource> streamVideo(@PathVariable Long id) {
//    Optional<Video> videoOptional = videoService.getVideoById(id);
//
//    if (videoOptional.isPresent()) {
//        Video video = videoOptional.get();
//        Resource videoResource = new FileSystemResource(video.getVideoPath());
//
//        return ResponseEntity.ok()
//                .contentType(MediaType.APPLICATION_OCTET_STREAM)
//                .body(videoResource);
//    } else {
//        return ResponseEntity.notFound().build();
//    }
//}

//    @GetMapping("/{id}")
//    public ResponseEntity<InputStreamResource> retrieveResource(@PathVariable long id) throws Exception{
//        Optional<Video> videoOptional = videoRepository.findById(id);
//
//        if (!videoOptional.isPresent()) {
//            return ResponseEntity.notFound().build();
//        }
//
//        Video video = videoOptional.get();
//        byte[] videoData = video.getData(); // Assurez-vous que votre entité Video a une méthode pour obtenir les données de la vidéo
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.set("Accept-Ranges","bytes");
//        headers.set("Content-Type","video/mp4");
//        headers.set("Content-Range","bytes 50-1025/17839845");
//        headers.set("Content-Length",String.valueOf(videoData.length));
//        return new ResponseEntity<>(new InputStreamResource(new ByteArrayInputStream(videoData)),headers,HttpStatus.OK);
//    }
//@GetMapping(value = "/videos/{id}",consumes = MediaType.ALL_VALUE,produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
//public ResponseEntity<Flux<DataBuffer>> streamVideo(@PathVariable long id) {
//    Flux<DataBuffer> videoStream = videoService.streamVideo(id);
//
//    return (ResponseEntity<Flux<DataBuffer>>) ResponseEntity.ok()
//            .header("Content-Disposition", "inline")
//            .header("Accept-Ranges","bytes")
//            .header("Content-Type","video/mp4")
//            .header("Content-Range","bytes 50-1025/17839845")
//            .body(videoStream);
//}




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
