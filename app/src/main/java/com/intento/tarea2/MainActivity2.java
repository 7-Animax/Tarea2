package com.intento.tarea2;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity2 extends Activity {

    private EditText edtUsername;
    private EditText edtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        DataStore.inicializarDatos();

        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);

        Button btnLogin =
                findViewById(R.id.btnLogin);

        Button btnRegister =
                findViewById(R.id.btnRegister);

        btnLogin.setOnClickListener(view ->
                iniciarSesion());

        btnRegister.setOnClickListener(view ->
                mostrarRegistro());
    }

    private void iniciarSesion() {

        String username =
                edtUsername.getText()
                        .toString()
                        .trim();

        String password =
                edtPassword.getText()
                        .toString();

        if (username.isEmpty()) {

            edtUsername.setError(
                    "Ingresa tu usuario"
            );

            return;
        }

        if (password.isEmpty()) {

            edtPassword.setError(
                    "Ingresa tu contraseña"
            );

            return;
        }

        Usuario usuario =
                DataStore.autenticar(
                        username,
                        password
                );

        if (usuario == null) {

            Toast.makeText(
                    this,
                    "Usuario o contraseña incorrectos",
                    Toast.LENGTH_LONG
            ).show();

            return;
        }

        if (usuario.getRol()
                .equals("ADMIN")) {

            /*
             * INTENT EXPLÍCITO 2
             * MainActivity -> AdminHomeActivity
             */

            Intent intent =
                    new Intent(
                            MainActivity2.this,
                            AdminHome.class
                    );

            intent.putExtra(
                    "username",
                    usuario.getUsername()
            );

            intent.putExtra(
                    "rol",
                    "ADMIN"
            );

            startActivity(intent);

        } else {

            /*
             * INTENT EXPLÍCITO 1
             * MainActivity -> UserHomeActivity
             */

            Intent intent =
                    new Intent(
                            MainActivity2.this,
                            UserHome.class
                    );

            intent.putExtra(
                    "username",
                    usuario.getUsername()
            );

            intent.putExtra(
                    "rol",
                    "USER"
            );

            startActivity(intent);
        }
    }

    private void mostrarRegistro() {

        LinearLayout contenedor =
                new LinearLayout(this);

        contenedor.setOrientation(
                LinearLayout.VERTICAL
        );

        int padding = 50;

        contenedor.setPadding(
                padding,
                20,
                padding,
                10
        );

        EditText usuarioNuevo =
                new EditText(this);

        usuarioNuevo.setHint(
                "Nombre de usuario"
        );

        EditText passwordNuevo =
                new EditText(this);

        passwordNuevo.setHint(
                "Contraseña"
        );

        passwordNuevo.setInputType(
                InputType.TYPE_CLASS_TEXT |
                        InputType.TYPE_TEXT_VARIATION_PASSWORD
        );

        contenedor.addView(usuarioNuevo);
        contenedor.addView(passwordNuevo);

        AlertDialog dialog =
                new AlertDialog.Builder(this)
                        .setTitle(
                                "📚 Crear cuenta"
                        )
                        .setView(contenedor)
                        .setNegativeButton(
                                "Cancelar",
                                null
                        )
                        .setPositiveButton(
                                "Registrarse",
                                null
                        )
                        .create();

        dialog.setOnShowListener(listener -> {

            dialog.getButton(
                    AlertDialog.BUTTON_POSITIVE
            ).setOnClickListener(view -> {

                String nuevoUsername =
                        usuarioNuevo
                                .getText()
                                .toString()
                                .trim();

                String nuevaPassword =
                        passwordNuevo
                                .getText()
                                .toString();

                String resultado =
                        DataStore.registrarUsuario(
                                nuevoUsername,
                                nuevaPassword
                        );

                if (resultado != null) {

                    usuarioNuevo.setError(
                            resultado
                    );

                    return;
                }

                Toast.makeText(
                        MainActivity2.this,
                        "✅ Usuario registrado",
                        Toast.LENGTH_LONG
                ).show();

                edtUsername.setText(
                        nuevoUsername
                );

                dialog.dismiss();
            });
        });

        dialog.show();
    }
}