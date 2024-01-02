package com.slayer.domain

interface DtoToDomain<From,To> {
    fun map(from: From) : To
}