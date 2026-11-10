package com.example.proyecto_nutritrak.usuario;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.proyecto_nutritrak.R;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ProgresoActivity extends AppCompatActivity {

    private TextView txtPesoActual;
    private TextView txtAltura;
    private TextView txtIMC;
    private TextView txtExplicacionIMC;
    private LinearLayout contenedorMediciones;

    private TextInputEditText etNuevoPeso;
    private TextInputEditText etNuevaAltura;

    private LinearLayout navInicio;
    private LinearLayout navAlimentos;
    private LinearLayout navRegistro;
    private LinearLayout navHabitos;
    private LinearLayout navProgreso;
    private LinearLayout navPerfil;

    private final String URL_PROGRESO =
            "http://10.0.2.2:8001/progreso.php";

    private final String URL_MEDICION =
            "http://10.0.2.2:8001/registrar_medicion.php";

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
                R.layout.activity_progreso
        );

        inicializarComponentes();
        configurarBordesSeguros();
        configurarNavegacion();

        cargarProgreso();
    }

    private void inicializarComponentes() {

        txtPesoActual =
                findViewById(R.id.txtPesoActual);

        txtAltura =
                findViewById(R.id.txtAltura);

        txtIMC =
                findViewById(R.id.txtIMC);

        txtExplicacionIMC =
                findViewById(R.id.txtExplicacionIMC);

        contenedorMediciones =
                findViewById(R.id.contenedorMediciones);

        etNuevoPeso =
                findViewById(R.id.etNuevoPeso);

        etNuevaAltura =
                findViewById(R.id.etNuevaAltura);

        navInicio =
                findViewById(R.id.navInicio);

        navAlimentos =
                findViewById(R.id.navAlimentos);

        navRegistro =
                findViewById(R.id.navRegistro);

        navHabitos =
                findViewById(R.id.navHabitos);

        navProgreso =
                findViewById(R.id.navProgreso);

        navPerfil =
                findViewById(R.id.navPerfil);
    }

    private void configurarBordesSeguros() {

        View root =
                findViewById(R.id.rootProgreso);

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

    private void configurarNavegacion() {

        navInicio.setOnClickListener(v -> {

            Intent intent =
                    new Intent(
                            this,
                            MainActivity.class
                    );

            pasarUsuario(intent);

            startActivity(intent);
        });

        navAlimentos.setOnClickListener(v -> {

            Intent intent =
                    new Intent(
                            this,
                            AlimentosActivity.class
                    );

            pasarUsuario(intent);

            startActivity(intent);
        });

        navRegistro.setOnClickListener(v -> {

            Intent intent =
                    new Intent(
                            this,
                            RegistroConsumoActivity.class
                    );

            pasarUsuario(intent);

            startActivity(intent);
        });

        navHabitos.setOnClickListener(v -> {

            Intent intent =
                    new Intent(
                            this,
                            HabitosActivity.class
                    );

            pasarUsuario(intent);

            startActivity(intent);
        });

        navProgreso.setOnClickListener(v -> {

            Toast.makeText(
                    this,
                    "Ya estás en Progreso",
                    Toast.LENGTH_SHORT
            ).show();
        });

        navPerfil.setOnClickListener(v -> {

            Intent intent =
                    new Intent(
                            this,
                            PerfilActivity.class
                    );

            pasarUsuario(intent);

            startActivity(intent);
        });
    }

    private void pasarUsuario(Intent intent) {

        intent.putExtra(
                "idUsuario",
                getIntent().getIntExtra(
                        "idUsuario",
                        -1
                )
        );

        intent.putExtra(
                "nombre",
                getIntent().getStringExtra(
                        "nombre"
                )
        );

        intent.putExtra(
                "apellido",
                getIntent().getStringExtra(
                        "apellido"
                )
        );

        intent.putExtra(
                "correo",
                getIntent().getStringExtra(
                        "correo"
                )
        );
    }

    private void cargarProgreso() {

        int idUsuario =
                getIntent().getIntExtra(
                        "idUsuario",
                        -1
                );

        if (idUsuario == -1) {

            Toast.makeText(
                    this,
                    "No se encontró el usuario",
                    Toast.LENGTH_LONG
            ).show();

            return;
        }

        StringRequest request =
                new StringRequest(
                        Request.Method.POST,
                        URL_PROGRESO,

                        response -> {

                            try {

                                JSONObject json =
                                        new JSONObject(response);

                                if (!json.getBoolean(
                                        "success"
                                )) {

                                    Toast.makeText(
                                            this,
                                            json.optString(
                                                    "message"
                                            ),
                                            Toast.LENGTH_LONG
                                    ).show();

                                    return;
                                }

                                JSONObject usuario =
                                        json.getJSONObject(
                                                "usuario"
                                        );

                                String peso =
                                        usuario.optString(
                                                "peso",
                                                "0"
                                        );

                                String altura =
                                        usuario.optString(
                                                "altura",
                                                "0"
                                        );

                                txtPesoActual.setText(
                                        peso + " kg"
                                );

                                // Detectar si la altura está en metros o centímetros para mostrar correctamente
                                double alturaValor = Double.parseDouble(altura);
                                if (alturaValor < 3.0) {
                                    txtAltura.setText(altura + " m");
                                } else {
                                    txtAltura.setText(altura + " cm");
                                }

                                calcularIMC(
                                        peso,
                                        altura
                                );

                                JSONArray mediciones =
                                        json.getJSONArray(
                                                "mediciones"
                                        );

                                contenedorMediciones
                                        .removeAllViews();

                                for (
                                        int i = 0;
                                        i < mediciones.length();
                                        i++
                                ) {

                                    JSONObject medicion =
                                            mediciones.getJSONObject(
                                                    i
                                            );

                                    agregarMedicion(
                                            medicion
                                    );
                                }

                            } catch (JSONException e) {

                                Toast.makeText(
                                        this,
                                        "Error procesando progreso",
                                        Toast.LENGTH_LONG
                                ).show();
                            }
                        },

                        error -> Toast.makeText(
                                this,
                                "No se pudo conectar con el servidor",
                                Toast.LENGTH_LONG
                        ).show()
                ) {

                    @Override
                    protected Map<String, String>
                    getParams() {

                        Map<String, String> params =
                                new HashMap<>();

                        params.put(
                                "idUsuario",
                                String.valueOf(
                                        idUsuario
                                )
                        );

                        return params;
                    }
                };

        Volley.newRequestQueue(this)
                .add(request);
    }

    private void calcularIMC(
            String pesoTexto,
            String alturaTexto
    ) {

        try {

            double peso =
                    Double.parseDouble(
                            pesoTexto
                    );

            double altura =
                    Double.parseDouble(
                            alturaTexto
                    );

            if (
                    peso <= 0 ||
                            altura <= 0
            ) {

                txtIMC.setText(
                        "No disponible"
                );

                txtExplicacionIMC.setText("");

                return;
            }

            // Detectar si la altura está en metros o centímetros
            // Si es menor a 3, asumir que está en metros, si no, en centímetros
            double alturaMetros;
            if (altura < 3.0) {
                alturaMetros = altura;
                txtAltura.setText(altura + " m");
            } else {
                alturaMetros = altura / 100.0;
                txtAltura.setText(altura + " cm");
            }

            double imc =
                    peso /
                            (
                                    alturaMetros *
                                            alturaMetros
                            );

            String categoria;
            String explicacion;

            if (imc < 18.5) {
                categoria = "Bajo peso";
                explicacion = "Debes aumentar tu peso";
            } else if (imc < 25) {
                categoria = "Peso normal";
                explicacion = "¡Excelente! Mantén tus hábitos";
            } else if (imc < 30) {
                categoria = "Sobrepeso";
                explicacion = "Considera mejorar tu alimentación";
            } else {
                categoria = "Obesidad";
                explicacion = "Consulta a un especialista";
            }

            txtIMC.setText(
                    String.format(
                            "%.1f\n%s",
                            imc,
                            categoria
                    )
            );

            txtExplicacionIMC.setText(explicacion);

        } catch (Exception e) {

            txtIMC.setText(
                    "No disponible"
            );

            txtExplicacionIMC.setText("");
        }
    }

    private void agregarMedicion(
            JSONObject medicion
    ) {

        TextView texto =
                new TextView(this);

        String peso =
                medicion.optString(
                        "peso",
                        "-"
                );

        String altura =
                medicion.optString(
                        "altura",
                        "-"
                );

        String fecha =
                medicion.optString(
                        "fecha_medicion",
                        "-"
                );

        // Detectar si la altura está en metros o centímetros
        String alturaConUnidad;
        try {
            double alturaValor = Double.parseDouble(altura);
            if (alturaValor < 3.0) {
                alturaConUnidad = altura + " m";
            } else {
                alturaConUnidad = altura + " cm";
            }
        } catch (Exception e) {
            alturaConUnidad = altura + " cm";
        }

        texto.setText(
                "📅 " + fecha +
                        "\n⚖ Peso: " + peso + " kg" +
                        "\n📏 Altura: " + alturaConUnidad
        );

        texto.setTextSize(15);
        texto.setTextColor(
                Color.parseColor("#34443D")
        );

        texto.setPadding(
                0,
                15,
                0,
                15
        );

        contenedorMediciones.addView(
                texto
        );
    }

    public void guardarMedicion(View view) {

        int idUsuario =
                getIntent().getIntExtra(
                        "idUsuario",
                        -1
                );

        String peso =
                etNuevoPeso.getText()
                        .toString()
                        .trim();

        String altura =
                etNuevaAltura.getText()
                        .toString()
                        .trim();

        if (idUsuario == -1) {

            Toast.makeText(
                    this,
                    "Usuario no válido",
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }

        if (peso.isEmpty()) {

            etNuevoPeso.setError(
                    "Ingresa tu peso"
            );

            return;
        }

        if (altura.isEmpty()) {

            etNuevaAltura.setError(
                    "Ingresa tu altura"
            );

            return;
        }

        StringRequest request =
                new StringRequest(
                        Request.Method.POST,
                        URL_MEDICION,

                        response -> {

                            try {

                                JSONObject json =
                                        new JSONObject(response);

                                Toast.makeText(
                                        this,
                                        json.optString(
                                                "message"
                                        ),
                                        Toast.LENGTH_LONG
                                ).show();

                                if (json.getBoolean(
                                        "success"
                                )) {

                                    etNuevoPeso.setText("");
                                    etNuevaAltura.setText("");

                                    cargarProgreso();
                                }

                            } catch (JSONException e) {

                                Toast.makeText(
                                        this,
                                        "Respuesta inválida",
                                        Toast.LENGTH_LONG
                                ).show();
                            }
                        },

                        error -> Toast.makeText(
                                this,
                                "No se pudo guardar la medición",
                                Toast.LENGTH_LONG
                        ).show()
                ) {

                    @Override
                    protected Map<String, String>
                    getParams() {

                        Map<String, String> params =
                                new HashMap<>();

                        params.put(
                                "usuario_id",
                                String.valueOf(
                                        idUsuario
                                )
                        );

                        params.put(
                                "peso",
                                peso
                        );

                        params.put(
                                "altura",
                                altura
                        );

                        return params;
                    }
                };

        Volley.newRequestQueue(this)
                .add(request);
    }
}