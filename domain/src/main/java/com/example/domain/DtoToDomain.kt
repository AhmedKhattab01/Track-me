package com.example.domain

interface DtoToDomain<From,To> {
    fun map(from: From) : To
}