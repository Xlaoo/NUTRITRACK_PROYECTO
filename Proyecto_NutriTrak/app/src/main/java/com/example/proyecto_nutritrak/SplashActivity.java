package com.example.proyecto_nutritrak;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.proyecto_nutritrak.admin.AdminActivity;

public class SplashActivity extends AppCompatActivity {
    private static final int SPLASH_TIME = 2500;

    private Handler handler;

    private boolean yaInicioLogin = false;

    private Runnable splashRunnable;

    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);

        sessionManager = new SessionManager(this);

        LinearLayout splashContent =
                findViewById(R.id.splashContent);

        Button btnComenzar =
                findViewById(R.id.btnComenzar);

        Animation fadeAnimation =
                AnimationUtils.loadAnimation(
                        this,
                        R.anim.fade_in
                );
        Animation slideAnimation =
                AnimationUtils.loadAnimation(
                        this,
                        R.anim.slide_up
                );
        splashContent.startAnimation(fadeAnimation);

        btnComenzar.startAnimation(slideAnimation);
        handler = new Handler();
        splashRunnable = new Runnable() {

            @Override
            public void run() {

                verificarSesion();

            }

        };

        handler.postDelayed(
                splashRunnable,
                SPLASH_TIME
        );
        btnComenzar.setOnClickListener(v -> {

            // Cancelamos la transición automática
            // para evitar abrir el Login dos veces.
            handler.removeCallbacks(
                    splashRunnable
            );

            verificarSesion();
        });
    }
    private void verificarSesion() {

        // Si ya inició alguna navegación, no hacemos nada
        if (yaInicioLogin) {
            return;
        }

        yaInicioLogin = true;

        android.util.Log.d("SplashActivity", "Verificando sesión...");

        // Verificar si hay sesión activa
        if (sessionManager.isLoggedIn()) {
            android.util.Log.d("SplashActivity", "Sesión activa encontrada");
            android.util.Log.d("SplashActivity", "Usuario ID: " + sessionManager.getUserId());
            android.util.Log.d("SplashActivity", "Nombre: " + sessionManager.getNombre());
            android.util.Log.d("SplashActivity", "Rol: " + sessionManager.getRol());

            // Si hay sesión, redirigir según el rol
            String rol = sessionManager.getRol();

            if ("administrador".equalsIgnoreCase(rol)) {
                Intent intent = new Intent(
                        SplashActivity.this,
                        com.example.proyecto_nutritrak.admin.AdminActivity.class
                );
                intent.putExtra("idUsuario", sessionManager.getUserId());
                intent.putExtra("nombre", sessionManager.getNombre());
                intent.putExtra("apellido", sessionManager.getApellido());
                intent.putExtra("correo", sessionManager.getCorreo());
                startActivity(intent);
            } else {
                Intent intent = new Intent(
                        SplashActivity.this,
                        com.example.proyecto_nutritrak.usuario.MainActivity.class
                );
                intent.putExtra("idUsuario", sessionManager.getUserId());
                intent.putExtra("nombre", sessionManager.getNombre());
                intent.putExtra("apellido", sessionManager.getApellido());
                intent.putExtra("correo", sessionManager.getCorreo());
                startActivity(intent);
            }
        } else {
            android.util.Log.d("SplashActivity", "No hay sesión activa, yendo a login");
            // Si no hay sesión, ir al login
            Intent intent = new Intent(
                    SplashActivity.this,
                    LoginActivity.class
            );
            startActivity(intent);
        }

        overridePendingTransition(
                android.R.anim.fade_in,
                android.R.anim.fade_out
        );

        finish();

    }
    @Override
    protected void onDestroy() {

        super.onDestroy();

        if (handler != null && splashRunnable != null) {

            handler.removeCallbacks(
                    splashRunnable
            );

        }
    }
}