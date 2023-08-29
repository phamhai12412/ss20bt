package ss20.crud_uploadmovie.model;

import java.util.ArrayList;
import java.util.List;

public class Movie {
    private Long id;
    private String title;
    private String description;
    private String imageURL;
    private List<String> imageURLs= new ArrayList<>();
    private String videoURL;

    public Movie() {
    }

    public Movie(String title, String description, String imageURL, List<String> imageURLs, String videoURL) {
        this.title = title;
        this.description = description;
        this.imageURL = imageURL;
        this.imageURLs = imageURLs;
        this.videoURL = videoURL;
    }

    public Movie(Long id, String title, String description, String imageURL, List<String> imageURLs, String videoURL) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.imageURL = imageURL;
        this.imageURLs = imageURLs;
        this.videoURL = videoURL;
    }

    public Movie(Long id, String title, String description, List<String> imageList, String video) {
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

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public List<String> getImageURLs() {
        return imageURLs;
    }

    public void setImageURLs(List<String> imageURLs) {
        this.imageURLs = imageURLs;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public void setVideoURL(String videoURL) {
        this.videoURL = videoURL;
    }
}
