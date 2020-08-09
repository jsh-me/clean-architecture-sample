package com.jsh.tenqube.data.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class LabelResponse(
    @JsonProperty("results") var results: List<LabelModel>
)