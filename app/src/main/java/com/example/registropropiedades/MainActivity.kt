package com.example.registropropiedades

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.registropropiedades.screens.PropiedadesScreen
import com.example.registropropiedades.ui.theme.RegistroPropiedadesTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            val temaOscuroSistema =
                isSystemInDarkTheme()

            var temaOscuro by rememberSaveable {
                mutableStateOf(temaOscuroSistema)
            }

            RegistroPropiedadesTheme(
                darkTheme = temaOscuro
            ) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    PropiedadesScreen(
                        darkTheme = temaOscuro,
                        onDarkThemeChange = { nuevoValor ->
                            temaOscuro = nuevoValor
                        }
                    )
                }
            }
        }
    }
}