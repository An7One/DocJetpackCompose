package com.an7one.officialDoc.jetpackCompose.codeLabNavigation.util

import java.text.DecimalFormat

fun <E> List<E>.extractProportions(selector: (E) -> Float): List<Float> {
    val total = this.sumOf { selector(it).toDouble() }
    return this.map { (selector(it) / total).toFloat() }
}

fun formatAmount(amount: Float): String = AmountDecimalFormat.format(amount)

val AccountDecimalFormat = DecimalFormat("####")
private val AmountDecimalFormat = DecimalFormat("#,###.##")