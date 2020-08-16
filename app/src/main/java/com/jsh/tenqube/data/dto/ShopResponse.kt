package com.jsh.tenqube.data.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class ShopResponse(
    @JsonProperty("results") val results: List<ShopModel>
)