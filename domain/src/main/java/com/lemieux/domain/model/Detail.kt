package com.lemieux.domain.model

data class Detail(
    val adult: Boolean? = null,
    val backdropPath: String? = null,
    val belongsToCollection: BelongsToCollection? = null,
    val budget: Long? = null,
    val genres: List<Genre>? = null,
    val homepage: String? = null,
    val id: Int? = null,
    val imdbId: String? = null,
    val originCountry: List<String>? = null,
    val originalLanguage: String? = null,
    val originalTitle: String? = null,
    val overview: String? = null,
    val popularity: Double? = null,
    val posterPath: String? = null,
    val productionCompanies: List<ProductionCompany>? = null,
    val productionCountries: List<ProductionCountry>? = null,
    val releaseDate: String? = null,
    val revenue: Long? = null,
    val runtime: Int? = null,
    val spokenLanguages: List<SpokenLanguage>? = null,
    val status: String? = null,
    val tagline: String? = null,
    val title: String? = null,
    val video: Boolean? = null,
    val voteAverage: Double? = null,
    val voteCount: Int? = null,
)

data class Genre(
    val id: Int? = null,
    val name: String? = null,
)

data class ProductionCompany(
    val id: Int? = null,
    val logoPath: String? = null,
    val name: String? = null,
    val originCountry: String? = null,
)

data class ProductionCountry(
    val iso31661: String? = null,
    val name: String? = null,
)

data class SpokenLanguage(
    val englishName: String? = null,
    val iso6391: String? = null,
    val name: String? = null,
)

data class BelongsToCollection(
    val backdropPath: String? = null,
    val id: Int? = null,
    val name: String? = null,
    val posterPath: String? = null,
)
