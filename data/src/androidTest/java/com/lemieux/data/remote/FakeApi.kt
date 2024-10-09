package com.lemieux.data.remote

import com.google.gson.Gson
import com.lemieux.data.remote.model.MovieResponse
import com.lemieux.data.remote.model.detail.DetailDto

class FakeApi : MovieApi {
    override suspend fun getPopularMovies(page: Int): MovieResponse =
        Gson().fromJson("{\n" +
                "  \"page\": 1,\n" +
                "  \"results\": [\n" +
                "    {\n" +
                "      \"adult\": false,\n" +
                "      \"backdrop_path\": \"/gMJngTNfaqCSCqGD4y8lVMZXKDn.jpg\",\n" +
                "      \"genre_ids\": [\n" +
                "        28,\n" +
                "        12,\n" +
                "        878\n" +
                "      ],\n" +
                "      \"id\": 640146,\n" +
                "      \"original_language\": \"en\",\n" +
                "      \"original_title\": \"Ant-Man and the Wasp: Quantumania\",\n" +
                "      \"overview\": \"Super-Hero partners Scott Lang and Hope van Dyne, along with with Hope's parents Janet van Dyne and Hank Pym, and Scott's daughter Cassie Lang, find themselves exploring the Quantum Realm, interacting with strange new creatures and embarking on an adventure that will push them beyond the limits of what they thought possible.\",\n" +
                "      \"popularity\": 8567.865,\n" +
                "      \"poster_path\": \"/ngl2FKBlU4fhbdsrtdom9LVLBXw.jpg\",\n" +
                "      \"release_date\": \"2023-02-15\",\n" +
                "      \"title\": \"Ant-Man and the Wasp: Quantumania\",\n" +
                "      \"video\": false,\n" +
                "      \"vote_average\": 6.5,\n" +
                "      \"vote_count\": 1886\n" +
                "    },\n" +
                "    {\n" +
                "      \"adult\": false,\n" +
                "      \"backdrop_path\": \"/iJQIbOPm81fPEGKt5BPuZmfnA54.jpg\",\n" +
                "      \"genre_ids\": [\n" +
                "        16,\n" +
                "        12,\n" +
                "        10751,\n" +
                "        14,\n" +
                "        35\n" +
                "      ],\n" +
                "      \"id\": 502356,\n" +
                "      \"original_language\": \"en\",\n" +
                "      \"original_title\": \"The Super Mario Bros. Movie\",\n" +
                "      \"overview\": \"While working underground to fix a water main, Brooklyn plumbers—and brothers—Mario and Luigi are transported down a mysterious pipe and wander into a magical new world. But when the brothers are separated, Mario embarks on an epic quest to find Luigi.\",\n" +
                "      \"popularity\": 6572.614,\n" +
                "      \"poster_path\": \"/qNBAXBIQlnOThrVvA6mA2B5ggV6.jpg\",\n" +
                "      \"release_date\": \"2023-04-05\",\n" +
                "      \"title\": \"The Super Mario Bros. Movie\",\n" +
                "      \"video\": false,\n" +
                "      \"vote_average\": 7.5,\n" +
                "      \"vote_count\": 1456\n" +
                "    }\n" +
                "  ],\n" +
                "  \"total_pages\": 38029,\n" +
                "  \"total_results\": 760569\n" +
                "}", MovieResponse::class.java)

    override suspend fun getMovieDetail(movieId: Int): DetailDto {
        return Gson().fromJson("{\n" +
                "  \"adult\": false,\n" +
                "  \"backdrop_path\": \"/hZkgoQYus5vegHoetLkCJzb17zJ.jpg\",\n" +
                "  \"belongs_to_collection\": null,\n" +
                "  \"budget\": 63000000,\n" +
                "  \"genres\": [\n" +
                "    {\n" +
                "      \"id\": 18,\n" +
                "      \"name\": \"Drama\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": 53,\n" +
                "      \"name\": \"Thriller\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": 35,\n" +
                "      \"name\": \"Comedy\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"homepage\": \"http://www.foxmovies.com/movies/fight-club\",\n" +
                "  \"id\": 550,\n" +
                "  \"imdb_id\": \"tt0137523\",\n" +
                "  \"original_language\": \"en\",\n" +
                "  \"original_title\": \"Fight Club\",\n" +
                "  \"overview\": \"A ticking-time-bomb insomniac and a slippery soap salesman channel primal male aggression into a shocking new form of therapy. Their concept catches on, with underground \\\"fight clubs\\\" forming in every town, until an eccentric gets in the way and ignites an out-of-control spiral toward oblivion.\",\n" +
                "  \"popularity\": 61.416,\n" +
                "  \"poster_path\": \"/pB8BM7pdSp6B6Ih7QZ4DrQ3PmJK.jpg\",\n" +
                "  \"production_companies\": [\n" +
                "    {\n" +
                "      \"id\": 508,\n" +
                "      \"logo_path\": \"/7cxRWzi4LsVm4Utfpr1hfARNurT.png\",\n" +
                "      \"name\": \"Regency Enterprises\",\n" +
                "      \"origin_country\": \"US\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": 711,\n" +
                "      \"logo_path\": \"/tEiIH5QesdheJmDAqQwvtN60727.png\",\n" +
                "      \"name\": \"Fox 2000 Pictures\",\n" +
                "      \"origin_country\": \"US\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": 20555,\n" +
                "      \"logo_path\": \"/hD8yEGUBlHOcfHYbujp71vD8gZp.png\",\n" +
                "      \"name\": \"Taurus Film\",\n" +
                "      \"origin_country\": \"DE\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": 54051,\n" +
                "      \"logo_path\": null,\n" +
                "      \"name\": \"Atman Entertainment\",\n" +
                "      \"origin_country\": \"\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": 54052,\n" +
                "      \"logo_path\": null,\n" +
                "      \"name\": \"Knickerbocker Films\",\n" +
                "      \"origin_country\": \"US\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": 4700,\n" +
                "      \"logo_path\": \"/A32wmjrs9Psf4zw0uaixF0GXfxq.png\",\n" +
                "      \"name\": \"The Linson Company\",\n" +
                "      \"origin_country\": \"US\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": 25,\n" +
                "      \"logo_path\": \"/qZCc1lty5FzX30aOCVRBLzaVmcp.png\",\n" +
                "      \"name\": \"20th Century Fox\",\n" +
                "      \"origin_country\": \"US\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"production_countries\": [\n" +
                "    {\n" +
                "      \"iso_3166_1\": \"US\",\n" +
                "      \"name\": \"United States of America\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"release_date\": \"1999-10-15\",\n" +
                "  \"revenue\": 100853753,\n" +
                "  \"runtime\": 139,\n" +
                "  \"spoken_languages\": [\n" +
                "    {\n" +
                "      \"english_name\": \"English\",\n" +
                "      \"iso_639_1\": \"en\",\n" +
                "      \"name\": \"English\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"status\": \"Released\",\n" +
                "  \"tagline\": \"Mischief. Mayhem. Soap.\",\n" +
                "  \"title\": \"Fight Club\",\n" +
                "  \"video\": false,\n" +
                "  \"vote_average\": 8.433,\n" +
                "  \"vote_count\": 26280\n" +
                "}", DetailDto::class.java)
    }
}
