package knops.dev;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class VideoControllerTest {

    @Test
    public void testUploadVideo_Success() throws IOException {
        // Créez un mock du service VideoService
        VideoService videoService = Mockito.mock(VideoService.class);

        // Créez un instance de VideoController avec le mock du service
        VideoController videoController = new VideoController();
        videoController.videoService = videoService;

        // Définissez les données du test
        String videoName = "Test Video";
        String videoDescription = "Test Description";
        MultipartFile file = Mockito.mock(MultipartFile.class);

        // Configurez le comportement du mock du service pour simuler un succès
        doNothing().when(videoService).uploadVideo(videoName, videoDescription, file);

        // Appelez la méthode de contrôleur sous test
        ResponseEntity<String> response = videoController.uploadVideo(file, videoName, videoDescription);

        // Vérifiez que le service a été appelé avec les bons arguments
        verify(videoService).uploadVideo(videoName, videoDescription, file);

        // Vérifiez que la réponse HTTP est OK
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Video uploaded successfully!", response.getBody());
    }

    @Test
    public void testUploadVideo_Conflict() throws IOException {
        // Créez un mock du service VideoService
        VideoService videoService = Mockito.mock(VideoService.class);

        // Créez un instance de VideoController avec le mock du service
        VideoController videoController = new VideoController();
        videoController.videoService = videoService;

        // Définissez les données du test
        String videoName = "Test Video";
        String videoDescription = "Test Description";
        MultipartFile file = Mockito.mock(MultipartFile.class);

        // Configurez le comportement du mock du service pour simuler une exception d'argument
        doThrow(IllegalArgumentException.class).when(videoService).uploadVideo(videoName, videoDescription, file);

        // Appelez la méthode de contrôleur sous test
        ResponseEntity<String> response = videoController.uploadVideo(file, videoName, videoDescription);

        // Vérifiez que le service a été appelé avec les bons arguments
        verify(videoService).uploadVideo(videoName, videoDescription, file);

        // Vérifiez que la réponse HTTP est en conflit
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("A video with the same name already exists.", response.getBody());
    }

    @Test
    public void testUploadVideo_InternalServerError() throws IOException {
        // Créez un mock du service VideoService
        VideoService videoService = Mockito.mock(VideoService.class);

        // Créez un instance de VideoController avec le mock du service
        VideoController videoController = new VideoController();
        videoController.videoService = videoService;

        // Définissez les données du test
        String videoName = "Test Video";
        String videoDescription = "Test Description";
        MultipartFile file = Mockito.mock(MultipartFile.class);

        // Configurez le comportement du mock du service pour simuler une exception d'E/S
        doThrow(IOException.class).when(videoService).uploadVideo(videoName, videoDescription, file);

        // Appelez la méthode de contrôleur sous test
        ResponseEntity<String> response = videoController.uploadVideo(file, videoName, videoDescription);

        // Vérifiez que le service a été appelé avec les bons arguments
        verify(videoService).uploadVideo(videoName, videoDescription, file);

        // Vérifiez que la réponse HTTP est une erreur interne du serveur
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Error uploading video.", response.getBody());
    }

    // Ajoutez d'autres tests pour les autres méthodes de VideoController
}
