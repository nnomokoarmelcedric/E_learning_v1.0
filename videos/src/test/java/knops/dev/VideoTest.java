package knops.dev;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class VideoTest {

    @Test
    public void testGetSizeWithData() {
        byte[] testData = {1, 2, 3, 4, 5};
        Video video = new Video("Test Video", "Test Description", "video/mp4", testData);
        assertEquals(5, video.getSize());
    }

    @Test
    public void testGetSizeWithNullData() {
        Video video = new Video("Test Video", "Test Description", "video/mp4", null);
        assertEquals(0, video.getSize());
    }

    @Test
    public void testConstructorWithAllFields() {
        Long id = 1L;
        String name = "Test Video";
        String description = "Test Description";
        String contentType = "video/mp4";
        Video video = new Video(id, name, description, contentType);
        assertEquals(id, video.getId());
        assertEquals(name, video.getName());
        assertEquals(description, video.getDescription());
        assertEquals(contentType, video.getContentType());
    }

    @Test
    public void testConstructorWithNoId() {
        String name = "Test Video";
        String description = "Test Description";
        String contentType = "video/mp4";
        Video video = new Video(name, description, contentType, null);
        assertNull(video.getId());
        assertEquals(name, video.getName());
        assertEquals(description, video.getDescription());
        assertEquals(contentType, video.getContentType());
    }
}
