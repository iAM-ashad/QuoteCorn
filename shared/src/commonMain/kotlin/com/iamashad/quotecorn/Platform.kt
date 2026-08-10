package com.iamashad.quotecorn

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform