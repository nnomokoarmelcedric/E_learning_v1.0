package knops.dev;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@AllArgsConstructor
@Data
@NoArgsConstructor

public class Video {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private String contentType;

    @Lob
    private byte[] data;

    public Video(String name, String description, String contentType, byte[] data) {
        this.name = name;
        this.description = description;
        this.contentType = contentType;
        this.data = data;
    }

    // Getters and setters
}

