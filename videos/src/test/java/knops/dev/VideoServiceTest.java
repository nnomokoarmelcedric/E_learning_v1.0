package knops.dev;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class VideoServiceTest {

    @Test
    public void testUploadVideo_Success() throws IOException {
        VideoRepository videoRepository = Mockito.mock(VideoRepository.class);

        MultipartFile file = Mockito.mock(MultipartFile.class);

        VideoService videoService = new VideoService();
        videoService.videoRepository = videoRepository;

        when(videoRepository.findByName(anyString())).thenReturn(Optional.empty());

        String videoName = "Test Video";
        String videoDescription = "Test Description";

        videoService.uploadVideo(videoName, videoDescription, file);

        verify(videoRepository).findByName(videoName);
        verify(videoRepository).save(any(Video.class));
    }

    @Test
    public void testUploadVideo_Conflict() throws IOException {
        VideoRepository videoRepository = Mockito.mock(VideoRepository.class);

        MultipartFile file = Mockito.mock(MultipartFile.class);

        VideoService videoService = new VideoService();
        videoService.videoRepository = videoRepository;

        when(videoRepository.findByName(anyString())).thenReturn(Optional.of(new Video()));

        String videoName = "Test Video";
        String videoDescription = "Test Description";

        assertThrows(IllegalArgumentException.class, () -> videoService.uploadVideo(videoName, videoDescription, file));

        verify(videoRepository).findByName(videoName);
        verify(videoRepository, never()).save(any(Video.class));
    }



    @Test
    public void testUpdateVideo_Success() throws IOException {
        VideoRepository videoRepository = Mockito.mock(VideoRepository.class);

        VideoService videoService = new VideoService();
        videoService.videoRepository = videoRepository;

        Video existingVideo = new Video();
        existingVideo.setId(1L);
        when(videoRepository.findById(anyLong())).thenReturn(Optional.of(existingVideo));

        String newName = "New Video Name";
        String newDescription = "New Video Description";

        boolean updated = videoService.updateVideo(1L, newName, newDescription);

    }}