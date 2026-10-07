package com.intento.tarea2;

public class Horario {

    private String dia;
    private String horaInicio;
    private String horaFin;
    private String ramo;

    private boolean habilitado;
    private String reservadoPor;

    public Horario(String dia,
                   String horaInicio,
                   String horaFin,
                   String ramo) {

        this.dia = dia;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.ramo = ramo;

        this.habilitado = true;
        this.reservadoPor = null;
    }

    public String getDia() {
        return dia;
    }

    public String getHoraInicio() {
        return horaInicio;
    }

    public String getHoraFin() {
        return horaFin;
    }

    public String getRamo() {
        return ramo;
    }

    public boolean isHabilitado() {
        return habilitado;
    }

    public void setHabilitado(boolean habilitado) {
        this.habilitado = habilitado;
    }

    public String getReservadoPor() {
        return reservadoPor;
    }

    public void setReservadoPor(String reservadoPor) {
        this.reservadoPor = reservadoPor;
    }

    public boolean estaReservado() {
        return reservadoPor != null;
    }
}