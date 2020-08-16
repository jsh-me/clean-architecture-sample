package com.jsh.tenqube.domain.entity

data class Shop(
    val id: String,
    val name: String,
    val imgUrl: String,
    val labels: List<Label>
)