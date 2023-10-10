package knops.dev;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class VideoControllerTest {

    @Test
    public void testUploadVideo_Success() throws IOException {
        VideoService videoService = Mockito.mock(VideoService.class);

        VideoController videoController = new VideoController();
        videoController.videoService = videoService;

        String videoName = "Test Video";
        String videoDescription = "Test Description";
        MultipartFile file = Mockito.mock(MultipartFile.class);

        doNothing().when(videoService).uploadVideo(videoName, videoDescription, file);

        ResponseEntity<String> response = videoController.uploadVideo(file, videoName, videoDescription);

        verify(videoService).uploadVideo(videoName, videoDescription, file);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Video uploaded successfully!", response.getBody());
    }

    @Test
    public void testUploadVideo_Conflict() throws IOException {
        VideoService videoService = Mockito.mock(VideoService.class);

        VideoController videoController = new VideoController();
        videoController.videoService = videoService;

        String videoName = "Test Video";
        String videoDescription = "Test Description";
        MultipartFile file = Mockito.mock(MultipartFile.class);

        doThrow(IllegalArgumentException.class).when(videoService).uploadVideo(videoName, videoDescription, file);

        ResponseEntity<String> response = videoController.uploadVideo(file, videoName, videoDescription);

        verify(videoService).uploadVideo(videoName, videoDescription, file);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("A video with the same name already exists.", response.getBody());
    }

    @Test
    public void testUploadVideo_InternalServerError() throws IOException {
        VideoService videoService = Mockito.mock(VideoService.class);

        VideoController videoController = new VideoController();
        videoController.videoService = videoService;

        String videoName = "Test Video";
        String videoDescription = "Test Description";
        MultipartFile file = Mockito.mock(MultipartFile.class);

        doThrow(IOException.class).when(videoService).uploadVideo(videoName, videoDescription, file);

        ResponseEntity<String> response = videoController.uploadVideo(file, videoName, videoDescription);

        verify(videoService).uploadVideo(videoName, videoDescription, file);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Error uploading video.", response.getBody());
    }

}
