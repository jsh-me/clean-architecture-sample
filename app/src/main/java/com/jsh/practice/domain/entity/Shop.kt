package com.jsh.practice.domain.entity

data class Shop(
    val id: String,
    val name: String,
    val imgUrl: String,
    val labels: List<Label>
)