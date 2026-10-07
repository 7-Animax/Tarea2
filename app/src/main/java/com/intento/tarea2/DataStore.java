package com.intento.tarea2;

import java.util.ArrayList;

public class DataStore {

    public static final ArrayList<Usuario> usuarios = new ArrayList<>();
    public static final ArrayList<Horario> horarios = new ArrayList<>();

    public static boolean inscripcionesAbiertas = true;

    private static boolean datosInicializados = false;

    public static synchronized void inicializarDatos() {

        if (datosInicializados) {
            return;
        }

        // Usuario normal.
        usuarios.add(
                new Usuario(
                        "usuario",
                        "1234",
                        "USER"
                )
        );

        // Administrador.
        usuarios.add(
                new Usuario(
                        "useradmin",
                        "admin123",
                        "ADMIN"
                )
        );

        // HORARIOS DE EJEMPLO.

        horarios.add(new Horario(
                "Lunes",
                "08:00",
                "10:00",
                "Matemáticas"
        ));

        horarios.add(new Horario(
                "Lunes",
                "10:00",
                "12:00",
                "Inglés"
        ));

        horarios.add(new Horario(
                "Lunes",
                "14:00",
                "16:00",
                "Programación Android"
        ));

        horarios.add(new Horario(
                "Martes",
                "08:00",
                "10:00",
                "Historia"
        ));

        horarios.add(new Horario(
                "Martes",
                "10:00",
                "12:00",
                "Japonés"
        ));

        horarios.add(new Horario(
                "Martes",
                "16:00",
                "18:00",
                "Ciencias Naturales"
        ));

        horarios.add(new Horario(
                "Miércoles",
                "08:00",
                "10:00",
                "Lenguaje"
        ));

        horarios.add(new Horario(
                "Miércoles",
                "12:00",
                "14:00",
                "Matemáticas"
        ));

        horarios.add(new Horario(
                "Miércoles",
                "16:00",
                "18:00",
                "Programación Android"
        ));

        horarios.add(new Horario(
                "Jueves",
                "08:00",
                "10:00",
                "Inglés"
        ));

        horarios.add(new Horario(
                "Jueves",
                "14:00",
                "16:00",
                "Historia"
        ));

        horarios.add(new Horario(
                "Viernes",
                "08:00",
                "10:00",
                "Japonés"
        ));

        horarios.add(new Horario(
                "Viernes",
                "12:00",
                "14:00",
                "Ciencias Naturales"
        ));

        horarios.add(new Horario(
                "Viernes",
                "16:00",
                "18:00",
                "Programación Android"
        ));

        datosInicializados = true;
    }

    public static synchronized Usuario autenticar(
            String username,
            String password) {

        for (Usuario usuario : usuarios) {

            if (usuario.getUsername().equalsIgnoreCase(username)
                    && usuario.getPassword().equals(password)) {

                return usuario;
            }
        }

        return null;
    }

    public static synchronized String registrarUsuario(
            String username,
            String password) {

        if (username == null || username.trim().isEmpty()) {
            return "Debes ingresar un usuario";
        }

        if (password == null || password.trim().isEmpty()) {
            return "Debes ingresar una contraseña";
        }

        if (username.equalsIgnoreCase("useradmin")) {
            return "Ese nombre está reservado";
        }

        for (Usuario usuario : usuarios) {

            if (usuario.getUsername()
                    .equalsIgnoreCase(username)) {

                return "El usuario ya existe";
            }
        }

        usuarios.add(
                new Usuario(
                        username,
                        password,
                        "USER"
                )
        );

        return null;
    }

    public static synchronized String reservar(
            Horario horario,
            String username) {

        if (!inscripcionesAbiertas) {
            return "Las inscripciones están cerradas";
        }

        if (!horario.isHabilitado()) {
            return "Este horario no está disponible";
        }

        if (horario.estaReservado()) {
            return "Este horario ya está reservado";
        }

        // Evita dos reservas en el mismo bloque horario.
        for (Horario h : horarios) {

            if (username.equals(h.getReservadoPor())
                    && h.getDia().equals(horario.getDia())
                    && h.getHoraInicio().equals(
                    horario.getHoraInicio())) {

                return "Ya tienes otra clase en ese horario";
            }
        }

        horario.setReservadoPor(username);

        return null;
    }

    public static synchronized boolean cancelarReservaUsuario(
            Horario horario,
            String username) {

        if (horario.getReservadoPor() != null
                && horario.getReservadoPor()
                .equals(username)) {

            horario.setReservadoPor(null);

            return true;
        }

        return false;
    }

    public static synchronized void cancelarReservaAdmin(
            Horario horario) {

        horario.setReservadoPor(null);
    }

    public static synchronized void cambiarDisponibilidad(
            Horario horario) {

        horario.setHabilitado(
                !horario.isHabilitado()
        );
    }

    public static synchronized ArrayList<Horario>
    obtenerHorarios() {

        return new ArrayList<>(horarios);
    }
}