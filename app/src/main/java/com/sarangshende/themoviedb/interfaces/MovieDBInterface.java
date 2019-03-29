package com.sarangshende.themoviedb.interfaces;
import com.sarangshende.themoviedb.MainActivity;
import com.sarangshende.themoviedb.MovieDetailsActivity;
import com.sarangshende.themoviedb.models.AllMovies;
import com.sarangshende.themoviedb.models.Credits;
import com.sarangshende.themoviedb.models.MovieDetails;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface MovieDBInterface
{
    @GET("list/19476?api_key=1a6a79594cd3bc582e5eb595cdf691d2&language=en-US")
    Call<AllMovies> getAllMovies();

    @GET("movie/{movie_id}?api_key=1a6a79594cd3bc582e5eb595cdf691d2&language=en-US")
    Call<MovieDetails> getMovieDetails(@Path("movie_id") String movie_id);

    @GET("movie/{movie_id}?credits?api_key=1a6a79594cd3bc582e5eb595cdf691d2")
    Call<Credits> getCredits(@Path("movie_id") String movie_id);



}