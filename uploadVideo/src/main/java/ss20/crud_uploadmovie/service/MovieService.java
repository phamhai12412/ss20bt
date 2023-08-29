package ss20.crud_uploadmovie.service;

import ss20.crud_uploadmovie.model.Movie;
import ss20.crud_uploadmovie.util.ConnectDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MovieService implements IMovieSerVice{
    @Override
    public List<Movie> findAll() {
        Connection con = null;
        List<Movie> movies = new ArrayList<>();
        try {
            con = ConnectDB.openConnection();
            CallableStatement callSt = con.prepareCall("{call findAllMovie}");
            ResultSet rs = callSt.executeQuery();
            while (rs.next()) {
                Movie movie = new Movie();
                movie.setId(rs.getLong("id"));
                movie.setTitle(rs.getString("title"));
                movie.setDescription(rs.getString("description"));
                movie.setImageURL(Collections.singletonList(rs.getString("imageURL")));
                movie.setVideoURL(rs.getString("videoURL"));
                movies.add(movie);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectDB.closeConnection(con);
        }
        return movies;
    }

    @Override
    public Movie findById(Long id) {
        Connection conn = ConnectDB.openConnection();
        Movie movie = null;
        try {
            CallableStatement callSt = conn.prepareCall("{call findMovieById(?)}");
            callSt.setLong(1,id);
            ResultSet rs = callSt.executeQuery();
            while (rs.next()) {
                movie = new Movie();
                movie.setId(rs.getLong("id"));
                movie.setTitle(rs.getString("title"));
                movie.setDescription(rs.getString("description"));
                movie.setImageURL(Collections.singletonList(rs.getString("imageURL")));
                movie.setVideoURL(rs.getString("videoURL"));
            }

            // lấy về list image
            callSt = conn.prepareCall("{call findImageByMovieId(?)}");
            callSt.setLong(1,id);
            ResultSet rs2 = callSt.executeQuery();
            while (rs2.next()){
                String url = rs2.getString("url");
                movie.getImageURL().add(url);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectDB.closeConnection(conn);
        }
        return movie;
    }

    @Override
    public void save(Movie movie) {
        Connection conn = ConnectDB.openConnection();

        try {
            if (movie.getId() == null) {
                // thêm moi
                CallableStatement callSt = conn.prepareCall("{call insertMovie(?,?,?,?,?)}");
                callSt.setString(1, movie.getTitle());
                callSt.setString(2, movie.getDescription());
                callSt.setString(3, movie.getImageURL().toString());
                callSt.setString(4, movie.getVideoURL());

                callSt.registerOutParameter(5, Types.INTEGER);
                callSt.execute();
                Long newMovieId = callSt.getLong(5);
                for (String URL:movie.getImageURL()) {
                    CallableStatement callSt1 = conn.prepareCall("{call insertImage(?,?)}");
                    callSt1.setString(1,URL);
                    callSt1.setLong(2,newMovieId);
                    callSt1.executeUpdate();
                }
                CallableStatement callSt1 = conn.prepareCall("{call insertVideo(?,?)}");
                callSt1.setString(1, movie.getVideoURL());
                callSt1.setLong(2,newMovieId);
                callSt1.executeUpdate();

            } else {
                // cap nhat
                CallableStatement callSt = conn.prepareCall("{call updateMovie(?,?,?,?,?)}");
                callSt.setLong(1, movie.getId());
                callSt.setString(2, movie.getTitle());
                callSt.setString(3, movie.getDescription());
                callSt.setString(4, movie.getImageURL().toString());
                callSt.setString(5, movie.getVideoURL());
                callSt.executeUpdate();
                for (String URL:movie.getImageURL()) {
                    CallableStatement callSt1 = conn.prepareCall("{call updateImage(?,?)}");
                    callSt1.setString(1,URL);
                    callSt1.setLong(2,movie.getId());
                    callSt1.executeUpdate();
                }
                CallableStatement callSt1 = conn.prepareCall("{call updateVideo(?,?)}");
                callSt1.setString(1, movie.getVideoURL());
                callSt1.setLong(2,movie.getId());
                callSt1.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectDB.closeConnection(conn);
        }
    }

    @Override
    public void delete(Long id) {
        Connection con = ConnectDB.openConnection();
        try {
            CallableStatement callSt = con.prepareCall("{call deleteMovie(?)}");
            callSt.setLong(1,id);
            callSt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectDB.closeConnection(con);
        }
    }
}
