package com.example.registropropiedades.model

data class Propiedad(
    val id: Long = System.nanoTime(),
    val direccion: String,
    val tipo: String,
    val precio: Double,
    val superficie: Double,
    val habitaciones: Int
)