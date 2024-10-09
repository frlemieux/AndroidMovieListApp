package com.lemieux.data.remote.model.detail

import com.google.gson.annotations.SerializedName
import com.lemieux.domain.model.Detail

data class DetailDto(
    val adult: Boolean? = null ,
    @SerializedName("backdrop_path")
    val backdropPath: String? = null,
    @SerializedName("belongs_to_collection")
    val belongsToCollection: BelongsToCollection? = null,
    val budget: Long? = null,
    val genres: List<Genre>? = null,
    val homepage: String? = null,
    val id: Int? = null,
    @SerializedName("imdb_id")
    val imdbId: String? = null,
    @SerializedName("origin_country")
    val originCountry: List<String>? = null,
    @SerializedName("original_language")
    val originalLanguage: String? = null,
    @SerializedName("original_title")
    val originalTitle: String? = null,
    val overview: String? = null,
    val popularity: Double? = null,
    @SerializedName("poster_path")
    val posterPath: String? = null,
    @SerializedName("production_companies")
    val productionCompanies: List<ProductionCompany>? = null,
    @SerializedName("production_countries")
    val productionCountries: List<ProductionCountry>? = null,
    @SerializedName("release_date")
    val releaseDate: String? = null,
    val revenue: Long? = null,
    val runtime: Int? = null,
    @SerializedName("spoken_languages")
    val spokenLanguages: List<SpokenLanguage>? = null,
    val status: String? = null,
    val tagline: String? = null,
    val title: String? = null,
    val video: Boolean? = null,
    @SerializedName("vote_average")
    val voteAverage: Double? = null,
    @SerializedName("vote_count")
    val voteCount: Int? = null,
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
