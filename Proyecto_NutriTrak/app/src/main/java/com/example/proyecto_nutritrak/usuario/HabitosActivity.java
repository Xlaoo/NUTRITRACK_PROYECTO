package com.example.proyecto_nutritrak.usuario;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.proyecto_nutritrak.R;

import org.json.JSONArray;
import org.json.JSONObject;

public class HabitosActivity extends AppCompatActivity {

    private LinearLayout navInicio;
    private LinearLayout navAlimentos;
    private LinearLayout navRegistro;
    private LinearLayout navHabitos;
    private LinearLayout navProgreso;
    private LinearLayout navPerfil;

    private Button btnVerRecomendaciones;

    private TextView txtFrutasPorcentaje;
    private TextView txtVerdurasPorcentaje;
    private TextView txtProteinasPorcentaje;
    private TextView txtCerealesPorcentaje;
    private TextView txtBebidasPorcentaje;

    private View barFrutas;
    private View barVerduras;
    private View barProteinas;
    private View barCereales;
    private View barBebidas;

    private LinearLayout contenedorPatrones;

    private RequestQueue requestQueue;
    private int usuarioId;

    private final String URL_HABITOS =
            "http://10.0.2.2:8001/habitos.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WindowCompat.setDecorFitsSystemWindows(
                getWindow(),
                false
        );

        getWindow().setStatusBarColor(
                Color.parseColor("#F2FBF7")
        );

        getWindow().setNavigationBarColor(
                Color.WHITE
        );

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR |
                        View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
        );

        setContentView(
                R.layout.activity_habitos
        );

        inicializarComponentes();
        configurarBordesSeguros();
        configurarNavegacion();

        requestQueue = Volley.newRequestQueue(getApplicationContext());

        usuarioId = getIntent().getIntExtra("idUsuario", -1);

        android.util.Log.d("HabitosActivity", "idUsuario recibido: " + usuarioId);

        if (usuarioId == -1) {
            Toast.makeText(
                    this,
                    "Sesión no válida. Vuelva a iniciar sesión.",
                    Toast.LENGTH_LONG
            ).show();
            return;
        }

        cargarHabitos();
    }

    private void inicializarComponentes() {
        navInicio = findViewById(R.id.navInicio);
        navAlimentos = findViewById(R.id.navAlimentos);
        navRegistro = findViewById(R.id.navRegistro);
        navHabitos = findViewById(R.id.navHabitos);
        navProgreso = findViewById(R.id.navProgreso);
        navPerfil = findViewById(R.id.navPerfil);

        btnVerRecomendaciones = findViewById(R.id.btnVerRecomendaciones);

        txtFrutasPorcentaje = findViewById(R.id.txtFrutasPorcentaje);
        txtVerdurasPorcentaje = findViewById(R.id.txtVerdurasPorcentaje);
        txtProteinasPorcentaje = findViewById(R.id.txtProteinasPorcentaje);
        txtCerealesPorcentaje = findViewById(R.id.txtCerealesPorcentaje);
        txtBebidasPorcentaje = findViewById(R.id.txtBebidasPorcentaje);

        barFrutas = findViewById(R.id.barFrutas);
        barVerduras = findViewById(R.id.barVerduras);
        barProteinas = findViewById(R.id.barProteinas);
        barCereales = findViewById(R.id.barCereales);
        barBebidas = findViewById(R.id.barBebidas);

        contenedorPatrones = findViewById(R.id.contenedorPatrones);
    }

    private void configurarBordesSeguros() {
        View root = findViewById(R.id.rootHabitos);
        if (root != null) {
            ViewCompat.setOnApplyWindowInsetsListener(
                    root,
                    (view, windowInsets) -> {
                        Insets systemBars =
                                windowInsets.getInsets(
                                        WindowInsetsCompat.Type.systemBars()
                                );

                        view.setPadding(
                                0,
                                systemBars.top,
                                0,
                                0
                        );

                        return windowInsets;
                    }
            );
            ViewCompat.requestApplyInsets(root);
        }
    }

    private void configurarNavegacion() {
        navInicio.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            pasarUsuario(intent);
            startActivity(intent);
        });

        navAlimentos.setOnClickListener(v -> {
            Intent intent = new Intent(this, AlimentosActivity.class);
            pasarUsuario(intent);
            startActivity(intent);
        });

        navRegistro.setOnClickListener(v -> {
            Intent intent = new Intent(this, RegistroConsumoActivity.class);
            pasarUsuario(intent);
            startActivity(intent);
        });

        navHabitos.setOnClickListener(v -> {
            Toast.makeText(
                    this,
                    "Ya estás en Hábitos",
                    Toast.LENGTH_SHORT
            ).show();
        });

        navProgreso.setOnClickListener(v -> {
            Intent intent = new Intent(this, ProgresoActivity.class);
            pasarUsuario(intent);
            startActivity(intent);
        });

        navPerfil.setOnClickListener(v -> {
            Intent intent = new Intent(this, PerfilActivity.class);
            pasarUsuario(intent);
            startActivity(intent);
        });

        btnVerRecomendaciones.setOnClickListener(v -> {
            Intent intent = new Intent(this, RecomendacionesActivity.class);
            pasarUsuario(intent);
            startActivity(intent);
        });
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

    private void cargarHabitos() {
        android.util.Log.d("HabitosActivity", "Cargando hábitos para usuario_id: " + usuarioId);
        StringRequest request = new StringRequest(
                Request.Method.POST,
                URL_HABITOS,
                response -> {
                    android.util.Log.d("HabitosActivity", "Respuesta hábitos: " + response);
                    try {
                        JSONObject json = new JSONObject(response);

                        if (!json.getBoolean("success")) {
                            Toast.makeText(
                                    this,
                                    json.optString("message", "No se pudieron cargar los hábitos"),
                                    Toast.LENGTH_LONG
                            ).show();
                            return;
                        }

                        JSONObject frecuencias = json.getJSONObject("frecuencias");

                        int frutas = frecuencias.optInt("frutas", 0);
                        int verduras = frecuencias.optInt("verduras", 0);
                        int proteinas = frecuencias.optInt("proteinas", 0);
                        int cereales = frecuencias.optInt("cereales", 0);
                        int bebidas = frecuencias.optInt("bebidas_azucaradas", 0);

                        txtFrutasPorcentaje.setText(frutas + "%");
                        txtVerdurasPorcentaje.setText(verduras + "%");
                        txtProteinasPorcentaje.setText(proteinas + "%");
                        txtCerealesPorcentaje.setText(cereales + "%");
                        txtBebidasPorcentaje.setText(bebidas + "%");

                        // Barras visuales opcionales - por ahora solo mostramos porcentajes

                        JSONArray patrones = json.optJSONArray("patrones");
                        contenedorPatrones.removeAllViews();

                        if (patrones != null) {
                            for (int i = 0; i < patrones.length(); i++) {
                                String patron = patrones.getString(i);
                                agregarPatron(patron);
                            }
                        }

                    } catch (Exception e) {
                        android.util.Log.e("HabitosActivity", "Error procesando hábitos: " + e.getMessage());
                        Toast.makeText(
                                this,
                                "Error al procesar hábitos",
                                Toast.LENGTH_LONG
                        ).show();
                    }
                },
                error -> {
                    android.util.Log.e("HabitosActivity", "Error conexión: " + error.getMessage());
                    Toast.makeText(
                            this,
                            "No se pudo conectar con el servidor",
                            Toast.LENGTH_LONG
                    ).show();
                }
        ) {
            @Override
            protected java.util.Map<String, String> getParams() {
                java.util.Map<String, String> params = new java.util.HashMap<>();
                params.put("usuario_id", String.valueOf(usuarioId));
                android.util.Log.d("HabitosActivity", "Enviando usuario_id: " + usuarioId);
                return params;
            }
        };

        requestQueue.add(request);
    }

    private void agregarPatron(String texto) {
        TextView textView = new TextView(this);
        textView.setText("• " + texto);
        textView.setTextSize(14);
        textView.setTextColor(Color.parseColor("#173B2D"));
        textView.setPadding(0, 8, 0, 8);
        contenedorPatrones.addView(textView);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (requestQueue != null) {
            requestQueue.cancelAll(request -> true);
        }
    }
}
