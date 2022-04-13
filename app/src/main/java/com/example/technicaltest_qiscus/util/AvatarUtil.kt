package com.example.technicaltest_qiscus.util

object AvatarUtil {
    fun generateAvatar(s: String): String {
        var s = s
        s = s.replace(" ".toRegex(), "")
        return "https://robohash.org/$s/bgset_bg2/3.14160?set=set4"
    }
}