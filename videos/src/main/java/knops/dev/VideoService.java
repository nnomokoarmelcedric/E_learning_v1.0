package knops.dev;

import jakarta.ws.rs.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
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

    @Autowired
    private DataBufferFactory dataBufferFactory; // Injectez la factory DataBuffer
//
//    public ResponseEntity<Flux<DataBuffer>> streamVideo(Long id) {
//        Optional<Video> videoOptional = videoRepository.findById(id);
//        if (videoOptional.isPresent()) {
//            Video video = videoOptional.get();
//            byte[] videoData = video.getData();
//
//            // Ici, vous devrez mettre en œuvre la logique de segmentation de la vidéo en chunks
//            // en utilisant dataBufferFactory pour créer des DataBuffer
//
//            Flux<DataBuffer> videoDataStream = Flux.fromStream(() -> {
//                // Ici, vous devrez itérer sur les chunks de la vidéo et les émettre en tant que DataBuffer
//                // Utilisez dataBufferFactory pour créer des DataBuffer à partir de chaque chunk
//            });
//
//            HttpHeaders headers = new HttpHeaders();
//            headers.setContentType(MediaType.valueOf(video.getContentType()));
//
//            return new ResponseEntity<>(videoDataStream, headers, HttpStatus.OK);
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }
public Optional<Video> getVideoById(Long id) {
    return videoRepository.findById(id);
}
//    public ResponseEntity<byte[]> getVideo(Long id) {
//        Optional<Video> videoOptional = videoRepository.findById(id);
//        if (videoOptional.isPresent()) {
//            Video video = videoOptional.get();
//            return ResponseEntity.ok()
//                    .contentType(MediaType.parseMediaType(video.getContentType()))
//                    .body(video.getData());
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }
    public List<VideoProjection> getAllVideos() {
        return videoRepository.findAllBy();
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
//    public Flux<DataBuffer> streamVideo(long id) {
//        // Récupérez la vidéo depuis le repository par son ID
//        Video video = videoRepository.findById(id).orElseThrow(() -> new NotFoundException("Video not found"));
//
//        // Convertissez les données de la vidéo en flux de DataBuffer
//        return Flux.just(dataBufferFactory.wrap(video.getData()));
//    }

public ChunkWithMetadata fetchChunk(long id, Range range) throws IOException {
    Optional <Video> video = Optional.of(videoRepository.findById(id).orElseThrow());
    Video projection = video.get();
    return new ChunkWithMetadata(projection.getId(), readChunk(id, range, projection.getSize()));
}

    private byte[] readChunk(long id, Range range,long fileSize) throws IOException {
        Optional <Video> videoOption = videoRepository.findById(id);
        Video video = videoOption.get();
        int startPosition = (int)range.getRangeStart();
        long endPosition = range.getRangeEnd(fileSize);
        int chunkSize = (int) (endPosition - startPosition + 1);
        try(InputStream inputStream = new ByteArrayInputStream(video.getData(), startPosition, chunkSize)) {
            return inputStream.readAllBytes();
        } catch (IOException ioException) {
            System.out.println("an error occured");
            throw ioException; // Vous pouvez décider de la gérer ici ou la relancer
        }
    }
    public record ChunkWithMetadata(long id,
            byte[] chunk
    ) {}
}