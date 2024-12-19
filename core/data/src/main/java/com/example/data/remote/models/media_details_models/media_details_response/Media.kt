package com.example.data.remote.models.media_details_models.media_details_response

data class Media(
    val popularity: Int,
    val averageScore: Int,
    val characters: Characters,
    val coverImage: CoverImage,
    val description: String,
    val duration: Int,
    val endDate: EndDate,
    val episodes: Int,
    val chapters: Int?,
    val externalLinks: List<ExternalLink>,
    val favourites: Int,
    val format: String,
    val genres: List<String>,
    val nextAiringEpisode: NextAiringEpisode,
    val recommendations: Recommendations,
    val season: String?,
    val seasonYear: Int,
    val bannerImage: String?,
    val source: String?,
    val startDate: StartDate,
    val status: String,
    val studios: Studios,
    val tags: List<Tag>,
    val title: TitleX,
    val type: String
)