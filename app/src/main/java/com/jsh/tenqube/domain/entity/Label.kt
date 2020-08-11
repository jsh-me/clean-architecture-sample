package com.jsh.tenqube.domain.entity


sealed class DomainLabel {
    data class Label(
        var id: String,
        var name: String
    ) : DomainLabel()
}