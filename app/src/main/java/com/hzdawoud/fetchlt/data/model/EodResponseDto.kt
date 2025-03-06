package com.hzdawoud.fetchlt.data.model

import com.hzdawoud.fetchlt.domain.model.EodResponse
import kotlinx.serialization.Serializable

@Serializable
data class EodResponseDto(
    val data: List<EodEntryDto>
) {
    fun toDomain() = EodResponse(data.map { it.toDomain() })
}