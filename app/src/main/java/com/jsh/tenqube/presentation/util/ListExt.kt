package com.jsh.tenqube.presentation.util

import com.jsh.tenqube.domain.entity.Label

//getLabelList 와 getShopList 에서 labelids
fun List<String>.toValue(labelMap: Map<String, String>): List<String> {
    val newList = mutableListOf<String>()

    this.map{
        labelMap[it]?.let { labelName -> newList.add(labelName) }
    }
    return newList
}

fun Map<String, String>.mapToList(): List<String> {
    val newList = mutableListOf<String>()

    this.forEach { (_, value) ->
        newList.add(value)
    }

    return newList
}

fun List<Label>.toLabelMap(): Map<String, String> {
    val newList = mutableMapOf<String, String>()
    this.map{
        newList[it.id] = it.name
    }

    return newList
}