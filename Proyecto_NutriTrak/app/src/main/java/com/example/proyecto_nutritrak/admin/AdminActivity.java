package com.example.proyecto_nutritrak.admin;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.proyecto_nutritrak.LoginActivity;
import com.example.proyecto_nutritrak.R;
import com.example.proyecto_nutritrak.SessionManager;

public class AdminActivity extends AppCompatActivity {

    private Button btnUsuarios;
    private Button btnAlimentos;
    private Button btnReportes;
    private Button btnCerrarSesion;

    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_admin);

        sessionManager = new SessionManager(this);

        // Conectar botones
        btnUsuarios = findViewById(R.id.btnUsuarios);
        btnAlimentos = findViewById(R.id.btnAlimentos);
        btnReportes = findViewById(R.id.btnReportes);
        btnCerrarSesion = findViewById(R.id.btnCerrarSesion);

        // Gestionar usuarios
        btnUsuarios.setOnClickListener(v -> {

            Intent intent = new Intent(
                    AdminActivity.this,
                    UsuariosAdminActivity.class
            );

            startActivity(intent);
        });

        // Gestionar alimentos
        btnAlimentos.setOnClickListener(v -> {

            Intent intent = new Intent(
                    AdminActivity.this,
                    AlimentosAdminActivity.class
            );

            startActivity(intent);
        });

        // Ver reportes
        btnReportes.setOnClickListener(v -> {

            Intent intent = new Intent(
                    AdminActivity.this,
                    ReportesAdminActivity.class
            );

            startActivity(intent);
        });

        // Cerrar sesión
        btnCerrarSesion.setOnClickListener(v -> {

            // Limpiar la sesión
            sessionManager.logout();

            Intent intent = new Intent(
                    AdminActivity.this,
                    LoginActivity.class
            );

            intent.setFlags(
                    Intent.FLAG_ACTIVITY_NEW_TASK |
                            Intent.FLAG_ACTIVITY_CLEAR_TASK
            );

            startActivity(intent);

            finish();
        });
    }
}