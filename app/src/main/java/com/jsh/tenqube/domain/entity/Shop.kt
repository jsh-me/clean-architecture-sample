package com.jsh.tenqube.domain.entity

import com.jsh.tenqube.domain.entity.DomainLabel.Label


sealed class DomainShop {
    data class Shop(
        var id: String,
        var name: String,
        var imgUrl: String,
        var labels: List<Label>
    ): DomainShop()
}