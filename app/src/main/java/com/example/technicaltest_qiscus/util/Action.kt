package com.example.technicaltest_qiscus.util

interface Action<T> {
    fun call(t: T)
}