package knops.dev;

import lombok.Data;

@Data
public class VideoDTO {
    private Long id;
    private String name;
    private String description;
    private String contentType;
    private byte[] data; // Les données binaires de la vidéo
}
