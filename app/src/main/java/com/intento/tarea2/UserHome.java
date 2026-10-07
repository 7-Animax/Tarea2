package com.intento.tarea2;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class UserHome extends Activity {

    private String username;
    private String rol;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(
                R.layout.activity_user_home
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

        TextView txtBienvenida =
                findViewById(
                        R.id.txtBienvenida
                );

        txtBienvenida.setText(
                "👋 Bienvenido, " +
                        username
        );

        Button btnHorarios =
                findViewById(
                        R.id.btnHorarios
                );

        Button btnMaps =
                findViewById(
                        R.id.btnMaps
                );

        Button btnWeb =
                findViewById(
                        R.id.btnWeb
                );

        Button btnTelefono =
                findViewById(
                        R.id.btnTelefono
                );

        Button btnCorreo =
                findViewById(
                        R.id.btnCorreo
                );

        btnHorarios.setOnClickListener(
                view -> abrirHorarios()
        );

        btnMaps.setOnClickListener(
                view -> abrirMaps()
        );

        btnWeb.setOnClickListener(
                view -> abrirPaginaWeb()
        );

        btnTelefono.setOnClickListener(
                view -> abrirTelefono()
        );

        btnCorreo.setOnClickListener(
                view -> enviarCorreo()
        );
    }

    private void abrirHorarios() {

        /*
         * INTENT EXPLÍCITO 3
         * UserHome -> HorariosActivity
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
                rol
        );

        startActivity(intent);
    }

    private void abrirMaps() {

        /*
         * INTENT IMPLÍCITO 1
         * Abrir ubicación.
         */

        Uri ubicacion =
                Uri.parse(
                        "geo:0,0?q=Santo Tomas Santiago Centro"
                );

        Intent intent =
                new Intent(
                        Intent.ACTION_VIEW,
                        ubicacion
                );

        ejecutarIntent(intent);
    }

    private void abrirPaginaWeb() {

        /*
         * INTENT IMPLÍCITO 2
         * Abrir página web.
         */

        Intent intent =
                new Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse(
                                "https://www.santotomas.cl"
                        )
                );

        ejecutarIntent(intent);
    }

    private void abrirTelefono() {

        /*
         * INTENT IMPLÍCITO 3
         * Abre el marcador telefónico.
         */

        Intent intent =
                new Intent(
                        Intent.ACTION_DIAL,
                        Uri.parse(
                                "tel:+56220000000"
                        )
                );

        ejecutarIntent(intent);
    }

    private void enviarCorreo() {

        /*
         * INTENT IMPLÍCITO 4
         * Abre aplicación de correo.
         */

        Intent intent =
                new Intent(
                        Intent.ACTION_SENDTO
                );

        intent.setData(
                Uri.parse(
                        "mailto:consultas@studyclass.cl"
                )
        );

        intent.putExtra(
                Intent.EXTRA_SUBJECT,
                "Consulta de horario StudyClass"
        );

        intent.putExtra(
                Intent.EXTRA_TEXT,
                "Hola, quisiera consultar sobre los horarios disponibles."
        );

        ejecutarIntent(intent);
    }

    private void ejecutarIntent(
            Intent intent) {

        if (intent.resolveActivity(
                getPackageManager()
        ) != null) {

            startActivity(intent);

        } else {

            Toast.makeText(
                    this,
                    "No existe una aplicación compatible",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }
}