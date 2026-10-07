package com.intento.tarea2

object ScheduleHelper {

    @JvmStatic
    fun textoEstado(horario: Horario): String {

        if (!horario.isHabilitado) {
            return "❌ No disponible"
        }

        if (horario.reservadoPor != null) {
            return "🔒 Reservado"
        }

        return "✅ Disponible"
    }
}