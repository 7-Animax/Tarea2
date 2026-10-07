package com.intento.tarea2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class AdminHome extends Activity {

    private String username;
    private TextView txtEstado;
    private Button btnInscripciones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(
                R.layout.activity_admin_home
        );

        username =
                getIntent()
                        .getStringExtra(
                                "username"
                        );

        if (username == null) {
            username = "Administrador";
        }

        TextView txtAdmin =
                findViewById(
                        R.id.txtAdmin
                );

        txtEstado =
                findViewById(
                        R.id.txtEstado
                );

        btnInscripciones =
                findViewById(
                        R.id.btnInscripciones
                );

        Button btnGestionar =
                findViewById(
                        R.id.btnGestionar
                );

        txtAdmin.setText("👨‍💼 Bienvenido, " + username);

        actualizarEstado();

        btnInscripciones
                .setOnClickListener(view -> {

                    DataStore.inscripcionesAbiertas =
                            !DataStore
                                    .inscripcionesAbiertas;

                    actualizarEstado();
                });

        btnGestionar
                .setOnClickListener(view -> {

                    /*
                     * INTENT EXPLÍCITO 3
                     * Home -> HorariosActivity
                     */

                    Intent intent =
                            new Intent(
                                    this,
                                    HorariosActivity.class
                            );

                    intent.putExtra(
                            "username",
                            username
                    );

                    intent.putExtra(
                            "rol",
                            "ADMIN"
                    );

                    startActivity(intent);
                });
    }

    private void actualizarEstado() {

        if (DataStore.inscripcionesAbiertas) {

            txtEstado.setText(
                    "✅ Inscripciones abiertas"
            );

            btnInscripciones.setText(
                    "🔒 Cerrar inscripciones"
            );

        } else {

            txtEstado.setText(
                    "🔒 Inscripciones cerradas"
            );

            btnInscripciones.setText(
                    "✅ Abrir inscripciones"
            );
        }
    }
}