package com.daivers.covinapp.model

import com.daivers.covinapp.data.CovidCase
import com.squareup.moshi.Json

data class NationCase(
    val name: String,
    val positif: String,
    val sembuh: String,
    val meninggal: String
)

data class KawalCoronaNetwork(val attributes: ProvinceCase)

data class ProvinceCase(
    @Json(name = "Provinsi")
    val provinsi: String,
    @Json(name = "Kasus_Posi")
    val positif: Int,
    @Json(name = "Kasus_Semb")
    val sembuh: Int,
    @Json(name = "Kasus_Meni")
    val meninggal: Int
)

fun List<NationCase>.asDataModel(): List<CovidCase> {
    return map {
        CovidCase(
            locationName = it.name,
            casePositive = it.positif,
            caseRevived = it.sembuh,
            caseDeath = it.meninggal
        )
    }
}
