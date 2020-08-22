package com.jsh.practice.data.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class LabelResponse(
    @JsonProperty("results") val results: List<LabelModel>
)