package com.example.registropropiedades.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.registropropiedades.model.Propiedad
import java.text.NumberFormat
import java.util.Locale

@Composable
fun PropiedadesScreen(
    darkTheme: Boolean,
    onDarkThemeChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    var direccion by rememberSaveable {
        mutableStateOf("")
    }

    var tipo by rememberSaveable {
        mutableStateOf("")
    }

    var precio by rememberSaveable {
        mutableStateOf("")
    }

    var superficie by rememberSaveable {
        mutableStateOf("")
    }

    var habitaciones by rememberSaveable {
        mutableStateOf("")
    }

    var mensajeError by rememberSaveable {
        mutableStateOf("")
    }

    val propiedades = remember {
        mutableStateListOf<Propiedad>()
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .safeDrawingPadding(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        item {
            Encabezado(
                darkTheme = darkTheme,
                onDarkThemeChange = onDarkThemeChange
            )
        }

        item {
            FormularioPropiedad(
                direccion = direccion,
                onDireccionChange = {
                    direccion = it
                    mensajeError = ""
                },

                tipo = tipo,
                onTipoChange = {
                    tipo = it
                    mensajeError = ""
                },

                precio = precio,
                onPrecioChange = {
                    precio = it
                    mensajeError = ""
                },

                superficie = superficie,
                onSuperficieChange = {
                    superficie = it
                    mensajeError = ""
                },

                habitaciones = habitaciones,
                onHabitacionesChange = {
                    habitaciones = it
                    mensajeError = ""
                },

                mensajeError = mensajeError,

                onRegistrar = {
                    val precioNumero = precio
                        .replace(",", ".")
                        .toDoubleOrNull()

                    val superficieNumero = superficie
                        .replace(",", ".")
                        .toDoubleOrNull()

                    val habitacionesNumero =
                        habitaciones.toIntOrNull()

                    when {
                        direccion.isBlank() -> {
                            mensajeError =
                                "Debe ingresar la dirección."
                        }

                        tipo.isBlank() -> {
                            mensajeError =
                                "Debe ingresar el tipo de propiedad."
                        }

                        precioNumero == null ||
                                precioNumero <= 0 -> {
                            mensajeError =
                                "Debe ingresar un precio válido."
                        }

                        superficieNumero == null ||
                                superficieNumero <= 0 -> {
                            mensajeError =
                                "Debe ingresar una superficie válida."
                        }

                        habitacionesNumero == null ||
                                habitacionesNumero < 0 -> {
                            mensajeError =
                                "Debe ingresar una cantidad válida de habitaciones."
                        }

                        else -> {
                            propiedades.add(
                                Propiedad(
                                    direccion = direccion.trim(),
                                    tipo = tipo.trim(),
                                    precio = precioNumero,
                                    superficie = superficieNumero,
                                    habitaciones = habitacionesNumero
                                )
                            )

                            direccion = ""
                            tipo = ""
                            precio = ""
                            superficie = ""
                            habitaciones = ""
                            mensajeError = ""
                        }
                    }
                }
            )
        }

        item {
            Text(
                text = "Propiedades registradas",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
        }

        if (propiedades.isEmpty()) {
            item {
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.medium,
                    color = MaterialTheme.colorScheme.surfaceVariant
                ) {
                    Text(
                        text = "Todavía no hay propiedades registradas.",
                        modifier = Modifier.padding(16.dp),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        items(
            items = propiedades,
            key = { propiedad ->
                propiedad.id
            }
        ) { propiedad ->
            PropiedadItem(
                propiedad = propiedad,
                onEliminar = {
                    propiedades.remove(propiedad)
                }
            )
        }
    }
}

@Composable
private fun Encabezado(
    darkTheme: Boolean,
    onDarkThemeChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = "Registro de propiedades",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = if (darkTheme) {
                    "Tema oscuro activado"
                } else {
                    "Tema claro activado"
                },
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Switch(
            checked = darkTheme,
            onCheckedChange = onDarkThemeChange
        )
    }
}

@Composable
private fun FormularioPropiedad(
    direccion: String,
    onDireccionChange: (String) -> Unit,

    tipo: String,
    onTipoChange: (String) -> Unit,

    precio: String,
    onPrecioChange: (String) -> Unit,

    superficie: String,
    onSuperficieChange: (String) -> Unit,

    habitaciones: String,
    onHabitacionesChange: (String) -> Unit,

    mensajeError: String,
    onRegistrar: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Nueva propiedad",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            OutlinedTextField(
                value = direccion,
                onValueChange = onDireccionChange,
                label = {
                    Text("Dirección")
                },
                placeholder = {
                    Text("Ejemplo: Av. España 123")
                },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = tipo,
                onValueChange = onTipoChange,
                label = {
                    Text("Tipo de propiedad")
                },
                placeholder = {
                    Text("Casa, apartamento, terreno...")
                },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = precio,
                onValueChange = onPrecioChange,
                label = {
                    Text("Precio")
                },
                placeholder = {
                    Text("Ejemplo: 500000000")
                },
                prefix = {
                    Text("₲ ")
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Decimal
                ),
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = superficie,
                onValueChange = onSuperficieChange,
                label = {
                    Text("Superficie")
                },
                placeholder = {
                    Text("Ejemplo: 250")
                },
                suffix = {
                    Text("m²")
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Decimal
                ),
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = habitaciones,
                onValueChange = onHabitacionesChange,
                label = {
                    Text("Número de habitaciones")
                },
                placeholder = {
                    Text("Ejemplo: 3")
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            if (mensajeError.isNotEmpty()) {
                Text(
                    text = mensajeError,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Button(
                onClick = onRegistrar,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Registrar propiedad")
            }
        }
    }
}

@Composable
private fun PropiedadItem(
    propiedad: Propiedad,
    onEliminar: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = propiedad.direccion,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(
                    horizontal = 2.dp
                )
            ) {
                item {
                    DatoPropiedad(
                        titulo = "Tipo",
                        valor = propiedad.tipo
                    )
                }

                item {
                    DatoPropiedad(
                        titulo = "Precio",
                        valor = formatearPrecio(propiedad.precio)
                    )
                }

                item {
                    DatoPropiedad(
                        titulo = "Superficie",
                        valor = "${formatearNumero(propiedad.superficie)} m²"
                    )
                }

                item {
                    DatoPropiedad(
                        titulo = "Habitaciones",
                        valor = propiedad.habitaciones.toString()
                    )
                }
            }

            OutlinedButton(
                onClick = onEliminar,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Eliminar")
            }
        }
    }
}

@Composable
private fun DatoPropiedad(
    titulo: String,
    valor: String
) {
    Surface(
        shape = MaterialTheme.shapes.medium,
        color = MaterialTheme.colorScheme.secondaryContainer
    ) {
        Column(
            modifier = Modifier.padding(
                horizontal = 16.dp,
                vertical = 10.dp
            )
        ) {
            Text(
                text = titulo,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )

            Text(
                text = valor,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
        }
    }
}

private fun formatearPrecio(
    precio: Double
): String {
    val formato = NumberFormat.getNumberInstance(
        Locale("es", "PY")
    )

    formato.maximumFractionDigits = 0

    return "₲ ${formato.format(precio)}"
}

private fun formatearNumero(
    numero: Double
): String {
    return if (numero % 1.0 == 0.0) {
        numero.toLong().toString()
    } else {
        numero.toString()
    }
}