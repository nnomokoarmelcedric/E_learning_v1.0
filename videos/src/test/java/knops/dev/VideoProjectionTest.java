package knops.dev;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class VideoProjectionTest {

    @Test
    public void testGetId() {
        Long id = 1L;
        VideoProjection videoProjection = new VideoProjectionImpl(id, "Test Video", "Test Description", "video/mp4");

        assertEquals(id, videoProjection.getId());
    }

    @Test
    public void testGetName() {
        String name = "Test Video";
        VideoProjection videoProjection = new VideoProjectionImpl(1L, name, "Test Description", "video/mp4");

        assertEquals(name, videoProjection.getName());
    }

    @Test
    public void testGetDescription() {
        String description = "Test Description";
        VideoProjection videoProjection = new VideoProjectionImpl(1L, "Test Video", description, "video/mp4");

        assertEquals(description, videoProjection.getDescription());
    }

    @Test
    public void testGetContentType() {
        String contentType = "video/mp4";
        VideoProjection videoProjection = new VideoProjectionImpl(1L, "Test Video", "Test Description", contentType);

        assertEquals(contentType, videoProjection.getContentType());
    }

    private static class VideoProjectionImpl implements VideoProjection {
        private final Long id;
        private final String name;
        private final String description;
        private final String contentType;

        public VideoProjectionImpl(Long id, String name, String description, String contentType) {
            this.id = id;
            this.name = name;
            this.description = description;
            this.contentType = contentType;
        }

        @Override
        public Long getId() {
            return id;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public String getDescription() {
            return description;
        }

        @Override
        public String getContentType() {
            return contentType;
        }
    }
}
