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

        // Usuarios predefinidos
        usuarios.add(new Usuario("usuario", "1234", "USER"));
        usuarios.add(new Usuario("useradmin", "admin123", "ADMIN"));

        // Población inicial de horarios
        agregarHorario("Lunes", "08:00", "10:00", "Matemáticas");
        agregarHorario("Lunes", "10:00", "12:00", "Inglés");
        agregarHorario("Lunes", "14:00", "16:00", "Programación Android");

        agregarHorario("Martes", "08:00", "10:00", "Historia");
        agregarHorario("Martes", "10:00", "12:00", "Japonés");
        agregarHorario("Martes", "16:00", "18:00", "Ciencias Naturales");

        agregarHorario("Miércoles", "08:00", "10:00", "Lenguaje");
        agregarHorario("Miércoles", "12:00", "14:00", "Matemáticas");
        agregarHorario("Miércoles", "16:00", "18:00", "Programación Android");

        agregarHorario("Jueves", "10:00", "12:00", "Inglés");
        agregarHorario("Jueves", "14:00", "16:00", "Historia");
        agregarHorario("Jueves", "16:00", "18:00", "Lenguaje");

        agregarHorario("Viernes", "08:00", "10:00", "Japonés");
        agregarHorario("Viernes", "12:00", "14:00", "Ciencias Naturales");
        agregarHorario("Viernes", "14:00", "16:00", "Programación Android");

        datosInicializados = true;
    }

    private static void agregarHorario(String dia, String inicio, String fin, String ramo) {
        horarios.add(new Horario(dia, inicio, fin, ramo));
    }

    public static synchronized Usuario autenticar(String username, String password) {
        for (Usuario u : usuarios) {
            if (u.getUsername().equalsIgnoreCase(username) && u.getPassword().equals(password)) {
                return u;
            }
        }
        return null;
    }

    public static synchronized String registrarUsuario(String username, String password) {
        if (username == null || username.trim().isEmpty()) return "Usuario vacío";
        if (password == null || password.trim().isEmpty()) return "Contraseña vacía";
        if (username.equalsIgnoreCase("useradmin")) return "Nombre reservado";
        
        for (Usuario u : usuarios) {
            if (u.getUsername().equalsIgnoreCase(username)) return "Usuario ya existe";
        }
        
        usuarios.add(new Usuario(username, password, "USER"));
        return null;
    }

    public static synchronized String reservar(Horario horario, String username) {
        if (!inscripcionesAbiertas) return "Inscripciones cerradas";
        if (!horario.isHabilitado()) return "Horario deshabilitado";
        if (horario.estaReservado()) return "Ya reservado";

        // Mismo día y bloque
        for (Horario h : horarios) {
            if (username.equals(h.getReservadoPor()) && 
                h.getDia().equals(horario.getDia()) && 
                h.getHoraInicio().equals(horario.getHoraInicio())) {
                return "Ya tienes una clase en este bloque";
            }
        }

        horario.setReservadoPor(username);
        return null;
    }

    public static synchronized void cancelarReservaUsuario(Horario horario, String username) {
        if (username.equals(horario.getReservadoPor())) {
            horario.setReservadoPor(null);
        }
    }

    public static synchronized void cancelarReservaAdmin(Horario horario) {
        horario.setReservadoPor(null);
    }

    public static synchronized void cambiarDisponibilidad(Horario horario) {
        horario.setHabilitado(!horario.isHabilitado());
    }

    public static synchronized ArrayList<Horario> obtenerHorarios() {
        return new ArrayList<>(horarios);
    }
}