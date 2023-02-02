package com.example.kotlin_movieapp.repository

import com.example.kotlin_movieapp.model.collectionResponse.SearchResponse
import com.example.kotlin_movieapp.model.collectionResponse.Top250Response
import com.example.kotlin_movieapp.model.collectionResponse.TopTvShowsResponse
import com.example.kotlin_movieapp.model.collectionResponse.UpComingResponse
import com.example.kotlin_movieapp.model.movieDetailsResponse.MovieDTO
import com.example.kotlin_movieapp.model.personDetailsResponse.PersonDTO
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val KINOPOISK_DOMAIN = "https://api.kinopoisk.dev/"

private const val RATING_FIELD = "rating.kp"

private const val NAME_FIELD = "name"

private const val ENLISH_NAME_FIELD = "alternativeName"

private const val AGE_RATING = "ageRating"

private const val SORT_FIELD = "id name top250 poster type rating.kp description"

class RemoteDataSource {

    private val kinopoiskAPI = Retrofit.Builder()
        .baseUrl(KINOPOISK_DOMAIN)
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
        .client(createOkHttpClient())
        .build().create(KinopoiskAPI::class.java)

   fun getMovieDetails(movieId: Int?, callback: Callback<MovieDTO>) {
        kinopoiskAPI.getMovie(movieId).enqueue(callback)
    }

    fun getPersonDetails(personId: Int?, callback: Callback<PersonDTO>) {
        kinopoiskAPI.getPerson(personId).enqueue(callback)
    }

    fun getTop250Collection(callback: Callback<Top250Response>){
        kinopoiskAPI.getTop250Collection().enqueue(callback)
    }

    fun getTopTvShowsCollection(callback: Callback<TopTvShowsResponse>){
        kinopoiskAPI.getTopTvShowsCollection().enqueue(callback)
    }

    fun getUpComingCollection(callback: Callback<UpComingResponse>){
        kinopoiskAPI.getUpComingCollection().enqueue(callback)
    }

    fun getAdultCyrillicSearchCollection(rating: Int?, name: String,
                                         callback: Callback<SearchResponse>){
        kinopoiskAPI.getAdultCyrillicSearchCollection(
            RATING_FIELD,
            "$rating-10",
            NAME_FIELD,
            name,
            RATING_FIELD,
            -1,
            AGE_RATING,
            6,
            AGE_RATING,
            12,
            AGE_RATING,
            16,
            AGE_RATING,
            18,
            AGE_RATING,
            SORT_FIELD
        ).enqueue(callback)
    }

    fun getCyrillicSearchCollection(rating: Int?, name: String,
                                    callback: Callback<SearchResponse>){
        kinopoiskAPI.getCyrillicSearchCollection(
            RATING_FIELD,
            "$rating-10",
            NAME_FIELD,
            name,
            RATING_FIELD,
            -1,
            AGE_RATING,
            6,
            AGE_RATING,
            12,
            AGE_RATING,
            16,
            AGE_RATING,
            SORT_FIELD
        ).enqueue(callback)
    }

    fun getAdultLatinSearchCollection(rating: Int?, name: String,
                                      callback: Callback<SearchResponse>){
        kinopoiskAPI.getAdultLatinSearchCollection(
            RATING_FIELD,
            "$rating-10",
            ENLISH_NAME_FIELD,
            name,
            RATING_FIELD,
            -1,
            AGE_RATING,
            6,
            AGE_RATING,
            12,
            AGE_RATING,
            16,
            AGE_RATING,
            18,
            AGE_RATING,
            SORT_FIELD
        ).enqueue(callback)
    }

    fun getLatinSearchCollection(rating: Int?, name: String,
                                 callback: Callback<SearchResponse>){
        kinopoiskAPI.getLatinSearchCollection(
            RATING_FIELD,
            "$rating-10",
            ENLISH_NAME_FIELD,
            name,
            RATING_FIELD,
            -1,
            AGE_RATING,
            6,
            AGE_RATING,
            12,
            AGE_RATING,
            16,
            AGE_RATING,
            SORT_FIELD
        ).enqueue(callback)
    }

    private fun createOkHttpClient (): OkHttpClient {
        val httpClient = OkHttpClient.Builder()

        httpClient.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))

        return httpClient.build()
    }
}