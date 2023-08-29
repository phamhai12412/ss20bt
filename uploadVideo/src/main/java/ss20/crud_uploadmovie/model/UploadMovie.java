package ss20.crud_uploadmovie.model;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

public class UploadMovie {
    private Long id;
    private String title;
    private String description;
    private List<MultipartFile> image;
    private MultipartFile video;

    public UploadMovie() {
    }

    public UploadMovie(Long id, String title, String description, List<MultipartFile> image, MultipartFile video) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.image = image;
        this.video = video;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<MultipartFile> getImage() {
        return image;
    }

    public void setImage(List<MultipartFile> image) {
        this.image = image;
    }

    public MultipartFile getVideo() {
        return video;
    }

    public void setVideo(MultipartFile video) {
        this.video = video;
    }
}
