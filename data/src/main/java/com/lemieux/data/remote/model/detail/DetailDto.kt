package com.lemieux.data.remote.model.detail

import com.google.gson.annotations.SerializedName
import com.lemieux.domain.model.Detail

data class DetailDto(
    val adult: Boolean?,
    @SerializedName("backdrop_path")
    val backdropPath: String?,
    @SerializedName("belongs_to_collection")
    val belongsToCollection: BelongsToCollection?,
    val budget: Int?,
    val genres: List<Genre>?,
    val homepage: String?,
    val id: Int?,
    @SerializedName("imdb_id")
    val imdbId: String?,
    @SerializedName("origin_country")
    val originCountry: List<String>?,
    @SerializedName("original_language")
    val originalLanguage: String?,
    @SerializedName("original_title")
    val originalTitle: String?,
    val overview: String?,
    val popularity: Double?,
    @SerializedName("poster_path")
    val posterPath: String?,
    @SerializedName("production_companies")
    val productionCompanies: List<ProductionCompany>?,
    @SerializedName("production_countries")
    val productionCountries: List<ProductionCountry>?,
    @SerializedName("release_date")
    val releaseDate: String?,
    val revenue: Int?,
    val runtime: Int?,
    @SerializedName("spoken_languages")
    val spokenLanguages: List<SpokenLanguage>?,
    val status: String?,
    val tagline: String?,
    val title: String?,
    val video: Boolean?,
    @SerializedName("vote_average")
    val voteAverage: Double?,
    @SerializedName("vote_count")
    val voteCount: Int?,
)

fun DetailDto.toDetail(): Detail {
    return Detail(
        adult = this.adult ?: false, 
        backdropPath = this.backdropPath ?: "",
        belongsToCollection = this.belongsToCollection.let {
            com.lemieux.domain.model.BelongsToCollection(
                backdropPath = it?.backdropPath ?: "",
                id = it?.id ?: 0, 
                name = it?.name ?: "",
                posterPath = it?.posterPath ?: ""
            )
        },
        budget = this.budget ?: 0, 
        genres = this.genres?.map { genre ->
            com.lemieux.domain.model.Genre(
                id = genre.id ?: 0, 
                name = genre.name ?: ""
            )
        } ?: emptyList(), 
        homepage = this.homepage ?: "", 
        id = this.id ?: 0, 
        imdbId = this.imdbId ?: "",
        originCountry = this.originCountry ?: emptyList(),
        originalLanguage = this.originalLanguage ?: "",
        originalTitle = this.originalTitle ?: "",
        overview = this.overview ?: "", 
        popularity = this.popularity ?: 0.0, 
        posterPath = this.posterPath ?: "",
        productionCompanies = this.productionCompanies?.map { company ->
            com.lemieux.domain.model.ProductionCompany(
                id = company.id ?: 0, 
                logoPath = company.logoPath ?: "",
                name = company.name ?: "",
                originCountry = company.originCountry ?: ""
            )
        } ?: emptyList(), 
        productionCountries = this.productionCountries?.map { country ->
            com.lemieux.domain.model.ProductionCountry(
                iso31661 = country.iso31661 ?: "",
                name = country.name ?: ""
            )
        } ?: emptyList(), 
        releaseDate = this.releaseDate ?: "",
        revenue = this.revenue ?: 0, 
        runtime = this.runtime ?: 0, 
        spokenLanguages = this.spokenLanguages?.map { language ->
            com.lemieux.domain.model.SpokenLanguage(
                englishName = language.englishName ?: "",
                iso6391 = language.iso6391 ?: "",
                name = language.name ?: ""
            )
        } ?: emptyList(), 
        status = this.status ?: "", 
        tagline = this.tagline ?: "", 
        title = this.title ?: "", 
        video = this.video ?: false, 
        voteAverage = this.voteAverage ?: 0.0,
        voteCount = this.voteCount ?: 0
    )
}
