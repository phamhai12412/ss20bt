package ss20.crud_uploadmovie.service;

import ss20.crud_uploadmovie.model.Movie;

import java.util.List;

public interface IMovieSerVice {
    List<Movie> findAll();
    Movie findById(Long id);
    void save (Movie movie);
    void delete(Long id);
}
