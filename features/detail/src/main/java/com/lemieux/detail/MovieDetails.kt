package com.lemieux.detail

import com.lemieux.domain.model.Detail


data class MovieDetails(
    val title: String,
    val tagline: String,
    val posterPath: String,
    val genres: List<Genre>,
    val overview: String,
    val budget: Long,
    val revenue: Long,
    val runtime: Int,
    val releaseDate: String,
    val productionCompanies: List<ProductionCompany>
)

data class Genre(val id: Int, val name: String)
data class ProductionCompany(val id: Int, val name: String, val logoPath: String?)

fun Detail.toMovieDetails(): MovieDetails {
    return MovieDetails(
        title = this.title,
        tagline = this.tagline,
        posterPath = this.posterPath,
        genres = this.genres.map { genre ->
            Genre(id = genre.id, name = genre.name)
        },
        overview = this.overview,
        budget = this.budget,
        revenue = this.revenue,
        runtime = this.runtime,
        releaseDate = this.releaseDate,
        productionCompanies = this.productionCompanies.map { company ->
            ProductionCompany(
                id = company.id,
                name = company.name,
                logoPath = company.logoPath
            )
        }
    )
}