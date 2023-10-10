package knops.dev;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class VideoServiceTest {

    @Test
    public void testUploadVideo_Success() throws IOException {
        // Créez un mock du repository VideoRepository
        VideoRepository videoRepository = Mockito.mock(VideoRepository.class);

        // Créez un mock de MultipartFile
        MultipartFile file = Mockito.mock(MultipartFile.class);

        // Créez un instance de VideoService avec le mock du repository
        VideoService videoService = new VideoService();
        videoService.videoRepository = videoRepository;

        // Configurez le mock du repository pour simuler qu'aucune vidéo avec le même nom n'existe
        when(videoRepository.findByName(anyString())).thenReturn(Optional.empty());

        // Définissez les données du test
        String videoName = "Test Video";
        String videoDescription = "Test Description";

        // Appelez la méthode de service sous test
        videoService.uploadVideo(videoName, videoDescription, file);

        // Vérifiez que le mock du repository a été appelé avec les bonnes données
        verify(videoRepository).findByName(videoName);
        verify(videoRepository).save(any(Video.class));
    }

    @Test
    public void testUploadVideo_Conflict() throws IOException {
        // Créez un mock du repository VideoRepository
        VideoRepository videoRepository = Mockito.mock(VideoRepository.class);

        // Créez un mock de MultipartFile
        MultipartFile file = Mockito.mock(MultipartFile.class);

        // Créez un instance de VideoService avec le mock du repository
        VideoService videoService = new VideoService();
        videoService.videoRepository = videoRepository;

        // Configurez le mock du repository pour simuler qu'une vidéo avec le même nom existe
        when(videoRepository.findByName(anyString())).thenReturn(Optional.of(new Video()));

        // Définissez les données du test
        String videoName = "Test Video";
        String videoDescription = "Test Description";

        // Appelez la méthode de service sous test et vérifiez qu'elle lance une exception
        assertThrows(IllegalArgumentException.class, () -> videoService.uploadVideo(videoName, videoDescription, file));

        // Vérifiez que le mock du repository a été appelé avec les bonnes données
        verify(videoRepository).findByName(videoName);
        verify(videoRepository, never()).save(any(Video.class));
    }



    @Test
    public void testUpdateVideo_Success() throws IOException {
        // Créez un mock du repository VideoRepository
        VideoRepository videoRepository = Mockito.mock(VideoRepository.class);

        // Créez un instance de VideoService avec le mock du repository
        VideoService videoService = new VideoService();
        videoService.videoRepository = videoRepository;

        // Configurez le mock du repository pour simuler qu'une vidéo avec l'ID existe
        Video existingVideo = new Video();
        existingVideo.setId(1L);
        when(videoRepository.findById(anyLong())).thenReturn(Optional.of(existingVideo));

        // Définissez les données du test
        String newName = "New Video Name";
        String newDescription = "New Video Description";

        // Appelez la méthode de service sous test
        boolean updated = videoService.updateVideo(1L, newName, newDescription);

        // V
    }}