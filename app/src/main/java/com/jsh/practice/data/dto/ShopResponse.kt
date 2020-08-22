package com.jsh.practice.data.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class ShopResponse(
    @JsonProperty("results") val results: List<ShopModel>
)