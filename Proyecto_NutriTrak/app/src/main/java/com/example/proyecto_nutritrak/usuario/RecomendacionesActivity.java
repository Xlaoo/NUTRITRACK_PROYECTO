package com.example.proyecto_nutritrak.usuario;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.proyecto_nutritrak.R;

public class RecomendacionesActivity extends AppCompatActivity {

    private LinearLayout navInicio;
    private LinearLayout navAlimentos;
    private LinearLayout navRegistro;
    private LinearLayout navHabitos;
    private LinearLayout navProgreso;
    private LinearLayout navPerfil;

    private Button btnVolver;

    private LinearLayout tabParaTi;
    private LinearLayout tabAprende;

    private LinearLayout contenedorParaTi;
    private LinearLayout contenedorAprende;

    private boolean isParaTiActive = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WindowCompat.setDecorFitsSystemWindows(
                getWindow(),
                false
        );

        setContentView(R.layout.activity_recomendaciones);

        View root = findViewById(R.id.rootRecomendaciones);

        WindowCompat.getInsetsController(getWindow(), root)
                .setAppearanceLightStatusBars(true);

        WindowCompat.getInsetsController(getWindow(), root)
                .setAppearanceLightNavigationBars(true);

        if (savedInstanceState != null) {
            isParaTiActive = savedInstanceState.getBoolean(
                    "paraTiActivo",
                    true
            );
        }

        inicializarComponentes();
        configurarBordesSeguros();
        configurarNavegacion();
        configurarPestanas();

        // Conecta las tres tarjetas con la pantalla educativa.
        configurarEducacion();
    }

    private void inicializarComponentes() {
        navInicio = findViewById(R.id.navInicio);
        navAlimentos = findViewById(R.id.navAlimentos);
        navRegistro = findViewById(R.id.navRegistro);
        navHabitos = findViewById(R.id.navHabitos);
        navProgreso = findViewById(R.id.navProgreso);
        navPerfil = findViewById(R.id.navPerfil);

        btnVolver = findViewById(R.id.btnVolver);

        tabParaTi = findViewById(R.id.tabParaTi);
        tabAprende = findViewById(R.id.tabAprende);

        contenedorParaTi = findViewById(R.id.contenedorParaTi);
        contenedorAprende = findViewById(R.id.contenedorAprende);
    }

    private void configurarBordesSeguros() {
        View root = findViewById(R.id.rootRecomendaciones);

        ViewCompat.setOnApplyWindowInsetsListener(
                root,
                (view, windowInsets) -> {
                    Insets systemBars = windowInsets.getInsets(
                            WindowInsetsCompat.Type.systemBars()
                    );

                    view.setPadding(
                            systemBars.left,
                            systemBars.top,
                            systemBars.right,
                            systemBars.bottom
                    );

                    return windowInsets;
                }
        );

        ViewCompat.requestApplyInsets(root);
    }

    private void configurarNavegacion() {
        btnVolver.setOnClickListener(v -> finish());

        navInicio.setOnClickListener(
                v -> abrirPantalla(MainActivity.class)
        );

        navAlimentos.setOnClickListener(
                v -> abrirPantalla(AlimentosActivity.class)
        );

        navRegistro.setOnClickListener(
                v -> abrirPantalla(RegistroConsumoActivity.class)
        );

        navHabitos.setOnClickListener(
                v -> abrirPantalla(HabitosActivity.class)
        );

        navProgreso.setOnClickListener(
                v -> abrirPantalla(ProgresoActivity.class)
        );

        navPerfil.setOnClickListener(
                v -> abrirPantalla(PerfilActivity.class)
        );
    }

    private void abrirPantalla(Class<?> destino) {
        Intent intent = new Intent(this, destino);

        pasarUsuario(intent);

        startActivity(intent);
        finish();
    }

    private void configurarPestanas() {
        tabParaTi.setOnClickListener(v -> {
            isParaTiActive = true;
            android.util.Log.d("Recomendaciones", "Tocó Para Ti, isParaTiActive = " + isParaTiActive);
            actualizarPestanas();
        });

        tabAprende.setOnClickListener(v -> {
            isParaTiActive = false;
            android.util.Log.d("Recomendaciones", "Tocó Aprende, isParaTiActive = " + isParaTiActive);
            actualizarPestanas();
        });

        actualizarPestanas();
    }

    private void actualizarPestanas() {
        android.util.Log.d("Recomendaciones", "actualizarPestanas: isParaTiActive = " + isParaTiActive);
        android.util.Log.d("Recomendaciones", "tabParaTi seleccionada = " + isParaTiActive);
        android.util.Log.d("Recomendaciones", "tabAprende seleccionada = " + !isParaTiActive);
        
        aplicarEstiloPestana(tabParaTi, isParaTiActive);
        aplicarEstiloPestana(tabAprende, !isParaTiActive);

        contenedorParaTi.setVisibility(
                isParaTiActive ? View.VISIBLE : View.GONE
        );

        contenedorAprende.setVisibility(
                isParaTiActive ? View.GONE : View.VISIBLE
        );
    }

    private void aplicarEstiloPestana(
            LinearLayout pestana,
            boolean seleccionada
    ) {
        android.util.Log.d("Recomendaciones", "aplicarEstiloPestana: id = " + pestana.getId() + ", seleccionada = " + seleccionada);
        
        GradientDrawable fondo = new GradientDrawable();

        fondo.setCornerRadius(
                10 * getResources().getDisplayMetrics().density
        );

        fondo.setColor(
                seleccionada
                        ? Color.parseColor("#237448")
                        : Color.WHITE
        );

        pestana.setBackground(fondo);
        pestana.setSelected(seleccionada);

        TextView titulo = (TextView) pestana.getChildAt(0);

        titulo.setTextColor(
                seleccionada
                        ? Color.WHITE
                        : Color.parseColor("#237448")
        );

        titulo.setTypeface(
                null,
                seleccionada ? Typeface.BOLD : Typeface.NORMAL
        );

        ViewCompat.setStateDescription(
                pestana,
                seleccionada ? "Seleccionada" : "No seleccionada"
        );
    }

    // Conexión de las tarjetas de Aprende.
    private void configurarEducacion() {

        // Tema 0: etiquetas nutricionales.
        findViewById(R.id.cardEtiquetas).setOnClickListener(
                v -> mostrarContenidoEducativo(0)
        );

        // Tema 1: porciones.
        findViewById(R.id.cardPorciones).setOnClickListener(
                v -> mostrarContenidoEducativo(1)
        );

        // Tema 2: variedad de alimentos.
        findViewById(R.id.cardVariedad).setOnClickListener(
                v -> mostrarContenidoEducativo(2)
        );
    }

    private void mostrarContenidoEducativo(int tema) {
        Intent intent = new Intent(
                RecomendacionesActivity.this,
                DetalleEducacionActivity.class
        );

        intent.putExtra(
                DetalleEducacionActivity.EXTRA_TEMA,
                tema
        );

        startActivity(intent);

        // No cerramos Recomendaciones:
        // al volver desde el detalle seguirá abierta la pestaña Aprende.
    }

    private void pasarUsuario(Intent intent) {
        intent.putExtra(
                "idUsuario",
                getIntent().getIntExtra("idUsuario", -1)
        );

        intent.putExtra(
                "nombre",
                getIntent().getStringExtra("nombre")
        );

        intent.putExtra(
                "apellido",
                getIntent().getStringExtra("apellido")
        );

        intent.putExtra(
                "correo",
                getIntent().getStringExtra("correo")
        );
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean("paraTiActivo", isParaTiActive);
        super.onSaveInstanceState(outState);
    }
}