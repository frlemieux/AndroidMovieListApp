package com.lemieux.detail

import androidx.compose.runtime.Stable
import com.lemieux.domain.model.Detail

@Stable
data class MovieDetails(
    val title: String? = null,
    val tagline: String? = null,
    val posterPath: String? = null,
    val genres: List<Genre>? = null,
    val overview: String? = null,
    val budget: Long? = null,
    val revenue: Long? = null,
    val runtime: Int? = null,
    val releaseDate: String? = null,
    val productionCompanies: List<ProductionCompany>? = null,
)

data class Genre(val id: Int? = null, val name: String)
data class ProductionCompany(val id: Int? = null, val name: String? = null, val logoPath: String?)

fun Detail.toMovieDetails(): MovieDetails {
    return MovieDetails(
        title = this.title,
        tagline = this.tagline,
        posterPath = this.posterPath,
        genres = this.genres?.map { genre ->
            Genre(id = genre.id, name = genre.name.orEmpty())
        },
        overview = this.overview,
        budget = this.budget,
        revenue = this.revenue,
        runtime = this.runtime,
        releaseDate = this.releaseDate,
        productionCompanies = this.productionCompanies?.map { company ->
            ProductionCompany(
                id = company.id,
                name = company.name,
                logoPath = company.logoPath
            )
        }
    )
}