package com.hzdawoud.fetchlt.data.model

import kotlinx.serialization.Serializable

@Serializable
data class EodResponseDto(
    val data: List<EodEntryDto>
)