package ss20.crud_uploadmovie.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import ss20.crud_uploadmovie.model.Movie;
import ss20.crud_uploadmovie.model.UploadMovie;
import ss20.crud_uploadmovie.service.MovieService;

import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
public class MovieController {
    private final MovieService movieService = new MovieService();
    @GetMapping(value = {"/","/movies"})
    public String index(Model model) {
        List<Movie> movies = movieService.findAll();
        model.addAttribute("movies", movies);
        return "/index";
    }

    @GetMapping("/upload")
    public ModelAndView showCreateForm() {
        ModelAndView modelAndView = new ModelAndView("/upload");
        modelAndView.addObject("uploadMovie", new UploadMovie());
        return modelAndView;
    }
    @Value("${uploadVideo}")
    private String uploadVideo;
    @Value("${uploadImage}")
    private String uploadImage;
    @PostMapping("/save")
    public ModelAndView saveProduct(@ModelAttribute UploadMovie uploadMovie) {
        List<String> imageList = new ArrayList<>();

        for (MultipartFile image : uploadMovie.getImage()) {
            String imageStr = image.getOriginalFilename();

            imageList.add(imageStr);
            try {
                FileCopyUtils.copy(image.getBytes(), new File(uploadImage + imageStr));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        MultipartFile videoFile = uploadMovie.getVideo();
        String video = videoFile.getOriginalFilename();
        try {
            FileCopyUtils.copy(videoFile.getBytes(), new File(uploadVideo + video));
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        Movie movie = new Movie(uploadMovie.getId(), uploadMovie.getTitle(), uploadMovie.getDescription(), imageList, video);
        System.out.println("movie->"+movie);
        movieService.save(movie);
        ModelAndView modelAndView = new ModelAndView("/upload");
        modelAndView.addObject("uploadMovie", uploadMovie);
        modelAndView.addObject("message", "Upload new movie successfully !");
        return modelAndView;
    }


}
