package com.jsh.tenqube.data.dto

import com.fasterxml.jackson.annotation.JsonProperty


data class LabelModel(
        @JsonProperty("id") val id: String,
        @JsonProperty("name") val name: String,
        @JsonProperty("createAt") val createAt: String,
        @JsonProperty("deleteAt") val deleteAt: String
    )