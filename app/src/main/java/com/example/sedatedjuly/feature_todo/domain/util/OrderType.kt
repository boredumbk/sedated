package com.example.sedatedjuly.feature_todo.domain.util

sealed class OrderType {
    object Ascending: OrderType()
    object Descending: OrderType()
}