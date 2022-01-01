package com.hamdy.showtime.ui.model

data class VideoResponse(
	val id: Int? = null,
	val results: List<ResultsVideoItem?>? = null
)

data class ResultsVideoItem(
	val site: String? = null,
	val size: Int? = null,
	val iso31661: String? = null,
	val name: String? = null,
	val official: Boolean? = null,
	val id: String? = null,
	val type: String? = null,
	val publishedAt: String? = null,
	val iso6391: String? = null,
	val key: String? = null
)

