package com.intento.tarea2;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class HorariosActivity extends Activity {

    private TableLayout tablaHorarios;
    private ProgressBar progressBar;

    private String username;
    private String rol;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(
                R.layout.activity_horarios
        );

        username =
                getIntent()
                        .getStringExtra(
                                "username"
                        );

        rol =
                getIntent()
                        .getStringExtra(
                                "rol"
                        );

        if (username == null) {
            username = "";
        }

        if (rol == null) {
            rol = "USER";
        }

        tablaHorarios =
                findViewById(
                        R.id.tablaHorarios
                );

        progressBar =
                findViewById(
                        R.id.progressBar
                );

        cargarHorarios();
    }

    /*
     * THREAD:
     * Los horarios se preparan fuera
     * del hilo principal.
     */
    private void cargarHorarios() {

        progressBar.setVisibility(
                View.VISIBLE
        );

        new Thread(() -> {

            ArrayList<Horario> copia =
                    DataStore
                            .obtenerHorarios();

            /*
             * Android solamente permite modificar
             * Views desde el hilo principal.
             */
            runOnUiThread(() -> {

                dibujarTabla(copia);

                progressBar.setVisibility(
                        View.GONE
                );
            });

        }).start();
    }

    private void dibujarTabla(
            ArrayList<Horario> horarios) {

        tablaHorarios.removeAllViews();

        crearEncabezado();

        for (Horario horario : horarios) {

            TableRow fila =
                    new TableRow(this);

            fila.addView(
                    crearTexto(
                            horario.getDia()
                    )
            );

            fila.addView(
                    crearTexto(
                            horario.getHoraInicio()
                                    + "\n"
                                    + horario.getHoraFin()
                    )
            );

            fila.addView(
                    crearTexto(
                            horario.getRamo()
                    )
            );

            fila.addView(
                    crearTexto(
                            ScheduleHelper
                                    .textoEstado(
                                            horario
                                    )
                    )
            );

            if ("ADMIN".equals(rol)) {

                fila.addView(
                        crearAccionesAdmin(
                                horario
                        )
                );

            } else {

                fila.addView(
                        crearAccionesUsuario(
                                horario
                        )
                );
            }

            tablaHorarios.addView(fila);
        }
    }

    private void crearEncabezado() {

        TableRow encabezado =
                new TableRow(this);

        encabezado.setBackgroundColor(
                Color.rgb(
                        52,
                        89,
                        149
                )
        );

        encabezado.addView(
                crearTitulo("Día")
        );

        encabezado.addView(
                crearTitulo("Horario")
        );

        encabezado.addView(
                crearTitulo("Ramo")
        );

        encabezado.addView(
                crearTitulo("Estado")
        );

        encabezado.addView(
                crearTitulo("Acción")
        );

        tablaHorarios.addView(encabezado);
    }

    private TextView crearTitulo(
            String texto) {

        TextView textView =
                crearTexto(texto);

        textView.setTextColor(
                Color.WHITE
        );

        return textView;
    }

    private TextView crearTexto(
            String texto) {

        TextView textView =
                new TextView(this);

        textView.setText(texto);

        textView.setPadding(
                18,
                20,
                18,
                20
        );

        textView.setGravity(
                Gravity.CENTER
        );

        return textView;
    }

    private View crearAccionesUsuario(
            Horario horario) {

        LinearLayout contenedor =
                new LinearLayout(this);

        contenedor.setOrientation(
                LinearLayout.VERTICAL
        );

        if (!horario.isHabilitado()) {

            TextView texto =
                    crearTexto(
                            "No disponible"
                    );

            contenedor.addView(texto);

            return contenedor;
        }

        if (!horario.estaReservado()) {

            Button btnReservar =
                    new Button(this);

            btnReservar.setText(
                    "Reservar"
            );

            btnReservar
                    .setOnClickListener(view -> {

                        String resultado =
                                DataStore.reservar(
                                        horario,
                                        username
                                );

                        if (resultado != null) {

                            Toast.makeText(
                                    this,
                                    resultado,
                                    Toast.LENGTH_LONG
                            ).show();

                        } else {

                            Toast.makeText(
                                    this,
                                    "✅ Reserva realizada",
                                    Toast.LENGTH_SHORT
                            ).show();

                            cargarHorarios();
                        }
                    });

            contenedor.addView(btnReservar);

            return contenedor;
        }

        if (username.equals(
                horario.getReservadoPor())) {

            Button btnCancelar =
                    new Button(this);

            btnCancelar.setText(
                    "Cancelar"
            );

            btnCancelar
                    .setOnClickListener(view -> {

                        DataStore
                                .cancelarReservaUsuario(
                                        horario,
                                        username
                                );

                        cargarHorarios();
                    });

            Button btnCalendario =
                    new Button(this);

            btnCalendario.setText(
                    "📅 Calendario"
            );

            btnCalendario
                    .setOnClickListener(view ->
                            agregarCalendario(
                                    horario
                            )
                    );

            contenedor.addView(
                    btnCancelar
            );

            contenedor.addView(
                    btnCalendario
            );

        } else {

            contenedor.addView(
                    crearTexto(
                            "Ocupado"
                    )
            );
        }

        return contenedor;
    }

    private View crearAccionesAdmin(
            Horario horario) {

        LinearLayout contenedor =
                new LinearLayout(this);

        contenedor.setOrientation(
                LinearLayout.VERTICAL
        );

        if (horario.estaReservado()) {

            TextView reservado =
                    crearTexto(
                            "👤 " +
                                    horario
                                            .getReservadoPor()
                    );

            Button cancelar =
                    new Button(this);

            cancelar.setText(
                    "Cancelar reserva"
            );

            cancelar
                    .setOnClickListener(view -> {

                        DataStore
                                .cancelarReservaAdmin(
                                        horario
                                );

                        cargarHorarios();
                    });

            contenedor.addView(
                    reservado
            );

            contenedor.addView(
                    cancelar
            );
        }

        Button disponibilidad =
                new Button(this);

        if (horario.isHabilitado()) {

            disponibilidad.setText(
                    "Deshabilitar"
            );

        } else {

            disponibilidad.setText(
                    "Habilitar"
            );
        }

        disponibilidad
                .setOnClickListener(view -> {

                    DataStore
                            .cambiarDisponibilidad(
                                    horario
                            );

                    cargarHorarios();
                });

        contenedor.addView(
                disponibilidad
        );

        return contenedor;
    }

    private void agregarCalendario(
            Horario horario) {

        /*
         * INTENT IMPLÍCITO 5
         * Agregar reserva al calendario.
         */

        Intent intent =
                new Intent(
                        Intent.ACTION_INSERT
                );

        intent.setData(
                CalendarContract
                        .Events
                        .CONTENT_URI
        );

        intent.putExtra(
                CalendarContract
                        .Events
                        .TITLE,
                "Clase de " +
                        horario.getRamo()
        );

        intent.putExtra(
                CalendarContract
                        .Events
                        .EVENT_LOCATION,
                "Sala de estudio"
        );

        intent.putExtra(
                CalendarContract
                        .Events
                        .DESCRIPTION,
                "Reserva realizada con StudyClass"
        );

        if (intent.resolveActivity(
                getPackageManager()
        ) != null) {

            startActivity(intent);

        } else {

            Toast.makeText(
                    this,
                    "No existe aplicación de calendario",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }
}