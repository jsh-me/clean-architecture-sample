package com.jsh.tenqube.presentation.util

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