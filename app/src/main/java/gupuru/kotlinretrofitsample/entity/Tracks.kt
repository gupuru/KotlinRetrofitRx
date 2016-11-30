package gupuru.kotlinretrofitsample.entity

import java.util.*

data class Tracks(
        var href: String,
        var limit: Int,
        var next: String,
        var previous: String,
        var offset: Int,
        var total: Int,
        var items: ArrayList<Items>
)