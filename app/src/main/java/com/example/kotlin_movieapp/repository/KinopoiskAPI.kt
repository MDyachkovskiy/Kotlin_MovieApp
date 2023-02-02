package com.example.kotlin_movieapp.repository

import com.example.kotlin_movieapp.BuildConfig
import com.example.kotlin_movieapp.model.collectionResponse.SearchResponse
import com.example.kotlin_movieapp.model.collectionResponse.Top250Response
import com.example.kotlin_movieapp.model.collectionResponse.TopTvShowsResponse
import com.example.kotlin_movieapp.model.collectionResponse.UpComingResponse
import com.example.kotlin_movieapp.model.movieDetailsResponse.MovieDTO
import com.example.kotlin_movieapp.model.personDetailsResponse.PersonDTO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

private const val KINOPOISK_TOKEN = BuildConfig.KINOPOISK_API_KEY

private const val MOVIE_END_POINT = "movie?$KINOPOISK_TOKEN&field=id"

private const val PERSON_END_POINT = "person?$KINOPOISK_TOKEN&field=id"

private const val TOP250_END_POINT =
    "movie/?$KINOPOISK_TOKEN" +
            "&field=top250" +
            "&search=!null" +
            "&sortField=top250" +
            "&field=type" +
            "&search=movie" +
            "&moviesLimit=20" +
            "&selectFields=id name top250 poster"

private const val TOP_TV_SHOWS_END_POINT =
    "movie/?$KINOPOISK_TOKEN" +
            "&field=top250" +
            "&search=!null" +
            "&sortField=top250" +
            "&field=type" +
            "&search=tv-series" +
            "&selectFields=id name top250 poster type"

private const val POPULAR_NOW_END_POINT =
    "collection/?$KINOPOISK_TOKEN" +
            "&search=top_items_all" +
            "&field=collectionId" +
            "&selectFields=id name top250 poster type"

private const val SEARCH_END_POINT =
    "movie/?$KINOPOISK_TOKEN"

interface KinopoiskAPI {
    @GET(MOVIE_END_POINT)
    fun getMovie(
        @Query("search") id : Int?
    ) : Call<MovieDTO>

    @GET(PERSON_END_POINT)
    fun getPerson (
        @Query("search") id : Int?
    ) : Call<PersonDTO>

    @GET(TOP250_END_POINT)
    fun getTop250Collection(
    ) : Call<Top250Response>

    @GET(TOP_TV_SHOWS_END_POINT)
    fun getTopTvShowsCollection(
    ) : Call<TopTvShowsResponse>

    @GET(POPULAR_NOW_END_POINT)
    fun getUpComingCollection(
    ) : Call<UpComingResponse>

    @GET(SEARCH_END_POINT)
    fun getAdultCyrillicSearchCollection(
        @Query ("field") ratingField : String,
        @Query ("search") rate: String,
        @Query ("field") nameField: String,
        @Query ("search") name: String,
        @Query ("sortField") sortField: String,
        @Query ("sortType") sortType: Int,
        @Query ("field") ageRating6: String,
        @Query ("search") age6: Int,
        @Query ("field") ageRating12: String,
        @Query ("search") age12: Int,
        @Query ("field") ageRating16: String,
        @Query ("search") age16: Int,
        @Query ("field") ageRating18: String,
        @Query ("search") age18: Int,
        @Query ("field") ageRatingNull: String,
        @Query ("selectFields") selectFields: String,
    ) : Call<SearchResponse>

    @GET(SEARCH_END_POINT)
    fun getCyrillicSearchCollection(
        @Query ("field") ratingField : String,
        @Query ("search") rate: String,
        @Query ("field") nameField: String,
        @Query ("search") name: String,
        @Query ("sortField") sortField: String,
        @Query ("sortType") sortType: Int,
        @Query ("field") ageRating6: String,
        @Query ("search") age6: Int,
        @Query ("field") ageRating12: String,
        @Query ("search") age12: Int,
        @Query ("field") ageRating16: String,
        @Query ("search") age16: Int,
        @Query ("field") ageRatingNull: String,
        @Query ("selectFields") selectFields: String,
    ) : Call<SearchResponse>

    @GET(SEARCH_END_POINT)
    fun getAdultLatinSearchCollection(
        @Query ("field") ratingField : String,
        @Query ("search") rate: String,
        @Query ("field") englishNameField: String,
        @Query ("search") name: String,
        @Query ("sortField") sortField: String,
        @Query ("sortType") sortType: Int,
        @Query ("field") ageRating6: String,
        @Query ("search") age6: Int,
        @Query ("field") ageRating12: String,
        @Query ("search") age12: Int,
        @Query ("field") ageRating16: String,
        @Query ("search") age16: Int,
        @Query ("field") ageRating18: String,
        @Query ("search") age18: Int,
        @Query ("field") ageRatingNull: String,
        @Query ("selectFields") selectFields: String,
    ) : Call<SearchResponse>

    @GET(SEARCH_END_POINT)
    fun getLatinSearchCollection(
        @Query ("field") ratingField : String,
        @Query ("search") rate: String,
        @Query ("field") englishNameField: String,
        @Query ("search") name: String,
        @Query ("sortField") sortField: String,
        @Query ("sortType") sortType: Int,
        @Query ("field") ageRating6: String,
        @Query ("search") age6: Int,
        @Query ("field") ageRating12: String,
        @Query ("search") age12: Int,
        @Query ("field") ageRating16: String,
        @Query ("search") age16: Int,
        @Query ("field") ageRatingNull: String,
        @Query ("selectFields") selectFields: String,
    ) : Call<SearchResponse>

}
