package com.jsh.practice.presentation.entity

import java.io.Serializable

data class PresenterShop(
    val shopId: String,
    val shopName: String,
    val shopUrl: String,
    val shopLabel: List<PresenterLabel>
): Serializable