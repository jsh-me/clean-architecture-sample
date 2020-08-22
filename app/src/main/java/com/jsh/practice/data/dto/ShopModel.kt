package com.jsh.practice.data.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class ShopModel(
        @JsonProperty("id") val id: String,
        @JsonProperty("name") val name: String,
        @JsonProperty("quality") val quality: Int,
        @JsonProperty("imgUrl") val imgUrl: String,
        @JsonProperty("linkUrl") val linkUrl: String,
        @JsonProperty("labelIds") val labelIds: List<String>,
        @JsonProperty("isDeleted") val isDeleted: Int,
        @JsonProperty("createAt") val createAt: String,
        @JsonProperty("deleteAt") val deleteAt: String
)