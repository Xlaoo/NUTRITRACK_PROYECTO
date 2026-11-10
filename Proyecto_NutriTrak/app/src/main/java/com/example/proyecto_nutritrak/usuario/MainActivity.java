package com.example.proyecto_nutritrak.usuario;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private TextView txtHola;
    private TextView txtCaloriasConsumidas;
    private TextView txtMetaDiaria;
    private TextView txtCaloriasRestantes;
    private TextView txtProteinas;
    private TextView txtCarbos;
    private TextView txtGrasas;
    private TextView txtPorcentajeProteinas;
    private TextView txtPorcentajeCarbos;
    private TextView txtPorcentajeGrasas;
    private TextView txtTituloRecomendacion;
    private TextView txtTextoRecomendacion;
    private TextView iconoRecomendacion;

    private LinearLayout navInicio;
    private LinearLayout navAlimentos;
    private LinearLayout navRegistro;
    private LinearLayout navHabitos;
    private LinearLayout navProgreso;
    private LinearLayout navPerfil;
    private LinearLayout btnHistorialAlimentacion;

    private int idUsuario;

    private static final String URL_USUARIO =
            "http://10.0.2.2:8001/obtener_usuario.php";
    private static final String URL_CALORIAS =
            "http://10.0.2.2:8001/calorias_hoy.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        Window window = getWindow();

        window.setStatusBarColor(
                Color.parseColor("#F2FBF7")
        );

        window.setNavigationBarColor(
                Color.parseColor("#FFFFFF")
        );

        window.getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                        | View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
        );

        WindowCompat.setDecorFitsSystemWindows(
                window,
                false
        );


        setContentView(R.layout.activity_main);

        idUsuario = getIntent().getIntExtra("idUsuario", -1);

        android.util.Log.d("MainActivity", "onCreate - idUsuario recibido: " + idUsuario);

        inicializarComponentes();

        configurarBordesSeguros();

        mostrarNombreUsuario();

        cargarDatosUsuario();

        configurarNavegacion();
    }

    @Override
    protected void onResume() {
        super.onResume();
        android.util.Log.d("MainActivity", "onResume - idUsuario: " + idUsuario);
        // Recargar datos cada vez que se vuelve a la actividad
        cargarDatosUsuario();
    }

    private void configurarBordesSeguros() {

        View root =
                findViewById(R.id.mainRoot);

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

    private void inicializarComponentes() {

        txtHola =
                findViewById(R.id.txtHola);

        txtCaloriasConsumidas =
                findViewById(R.id.txtCaloriasConsumidas);

        txtMetaDiaria =
                findViewById(R.id.txtMetaDiaria);

        txtCaloriasRestantes =
                findViewById(R.id.txtCaloriasRestantes);

        txtProteinas =
                findViewById(R.id.txtProteinas);

        txtCarbos =
                findViewById(R.id.txtCarbos);

        txtGrasas =
                findViewById(R.id.txtGrasas);

        txtPorcentajeProteinas =
                findViewById(R.id.txtPorcentajeProteinas);

        txtPorcentajeCarbos =
                findViewById(R.id.txtPorcentajeCarbos);

        txtPorcentajeGrasas =
                findViewById(R.id.txtPorcentajeGrasas);

        txtTituloRecomendacion =
                findViewById(R.id.tituloRecomendacion);

        txtTextoRecomendacion =
                findViewById(R.id.textoRecomendacion);

        iconoRecomendacion =
                findViewById(R.id.iconoRecomendacion);

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

        btnHistorialAlimentacion =
                findViewById(R.id.btnHistorialAlimentacion);
    }


    private void mostrarNombreUsuario() {

        String nombre =
                getIntent().getStringExtra(
                        "nombre"
                );

        if (nombre != null
                && !nombre.isEmpty()) {

            txtHola.setText(
                    "Hola, " + nombre + " 👋"
            );

        } else {

            txtHola.setText(
                    "Hola 👋"
            );
        }
    }

    private void cargarDatosUsuario() {
        android.util.Log.d("MainActivity", "Cargando datos usuario, idUsuario: " + idUsuario);
        if (idUsuario == -1) {
            android.util.Log.e("MainActivity", "idUsuario es -1, no se puede cargar");
            return;
        }

        // Cargar datos del usuario y calcular meta calórica
        StringRequest requestUsuario = new StringRequest(
                Request.Method.POST,
                URL_USUARIO,
                response -> {
                    android.util.Log.d("MainActivity", "Respuesta usuario: " + response);
                    try {
                        JSONObject json = new JSONObject(response);
                        if (json.getBoolean("success")) {
                            JSONObject usuario = json.getJSONObject("usuario");
                            double peso = usuario.optDouble("peso", 0);
                            double altura = usuario.optDouble("altura", 0);
                            String sexo = usuario.optString("sexo", "");
                            String actividad = usuario.optString("nivel_actividad", "");
                            String objetivo = usuario.optString("objetivo_alimentacion", "");

                            android.util.Log.d("MainActivity", "Peso: " + peso + ", Altura: " + altura + ", Sexo: " + sexo);

                            int metaCalorias = calcularMetaCalorica(peso, altura, sexo, actividad, objetivo);
                            txtMetaDiaria.setText(metaCalorias + " kcal");

                            // Calcular y mostrar macronutrientes
                            calcularMacronutrientes(metaCalorias);

                            // Mostrar recomendación personalizada según objetivo
                            mostrarRecomendacionPersonalizada(objetivo);

                            // Cargar calorías consumidas hoy
                            cargarCaloriasConsumidas(metaCalorias);
                        } else {
                            android.util.Log.e("MainActivity", "Error en respuesta usuario: " + json.optString("message"));
                        }
                    } catch (JSONException e) {
                        android.util.Log.e("MainActivity", "Error JSON usuario: " + e.getMessage());
                        e.printStackTrace();
                    }
                },
                error -> {
                    android.util.Log.e("MainActivity", "Error al cargar usuario: " + error.getMessage());
                    Toast.makeText(this, "Error al cargar datos del usuario", Toast.LENGTH_SHORT).show();
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("idUsuario", String.valueOf(idUsuario));
                return params;
            }
        };

        Volley.newRequestQueue(this).add(requestUsuario);
    }

    private int calcularMetaCalorica(double peso, double altura, String sexo, String actividad, String objetivo) {
        // Fórmula de Harris-Benedict
        double tmb;

        // Detectar si la altura está en metros o centímetros
        double alturaM;
        if (altura < 3.0) {
            alturaM = altura; // Ya está en metros
        } else {
            alturaM = altura / 100.0; // Convertir de cm a metros
        }

        if (sexo.equalsIgnoreCase("Hombre")) {
            tmb = 88.362 + (13.397 * peso) + (4.799 * alturaM) - (5.677 * 25); // Asumiendo edad 25
        } else {
            tmb = 447.593 + (9.247 * peso) + (3.098 * alturaM) - (4.330 * 25);
        }

        // Factor de actividad
        double factorActividad = 1.2; // Sedentario por defecto
        if (actividad.contains("media")) {
            factorActividad = 1.55;
        } else if (actividad.contains("activo")) {
            factorActividad = 1.725;
        }

        double mantenimiento = tmb * factorActividad;

        // Ajuste por objetivo
        if (objetivo.contains("Perder")) {
            mantenimiento -= 500; // Déficit de 500 kcal
        } else if (objetivo.contains("muscular")) {
            mantenimiento += 300; // Superávit de 300 kcal
        }

        return (int) Math.round(mantenimiento);
    }

    private void calcularMacronutrientes(int metaCalorias) {
        // Distribución estándar: 30% proteínas, 50% carbohidratos, 20% grasas
        double proteinasGramos = (metaCalorias * 0.30) / 4; // 4 kcal por gramo de proteína
        double carbosGramos = (metaCalorias * 0.50) / 4; // 4 kcal por gramo de carbohidrato
        double grasasGramos = (metaCalorias * 0.20) / 9; // 9 kcal por gramo de grasa

        txtProteinas.setText((int) Math.round(proteinasGramos) + " g");
        txtCarbos.setText((int) Math.round(carbosGramos) + " g");
        txtGrasas.setText((int) Math.round(grasasGramos) + " g");

        txtPorcentajeProteinas.setText("30%");
        txtPorcentajeCarbos.setText("50%");
        txtPorcentajeGrasas.setText("20%");
    }

    private void mostrarRecomendacionPersonalizada(String objetivo) {
        String titulo = "Consejo del día";
        String texto = "";
        String icono = "💡";

        if (objetivo == null || objetivo.isEmpty()) {
            objetivo = "Mantener";
        }

        if (objetivo.contains("Perder")) {
            icono = "🔥";
            String[] consejos = {
                "Prioriza proteínas en cada comida para mantener la masa muscular",
                "Aumenta el consumo de vegetales para sentirte lleno con menos calorías",
                "Bebe agua antes de cada comida para controlar el apetito",
                "Reduce alimentos procesados y azúcares añadidos",
                "Incluye actividad física moderada al menos 30 minutos diarios"
            };
            texto = consejos[(int) (Math.random() * consejos.length)];
        } else if (objetivo.contains("muscular") || objetivo.contains("Ganar")) {
            icono = "💪";
            String[] consejos = {
                "Consume proteínas después del entrenamiento para la recuperación",
                "Aumenta gradualmente tu ingesta calórica con alimentos nutritivos",
                "Incluye carbohidratos complejos antes del entrenamiento",
                "No saltes comidas: come 5-6 veces al día",
                "Prioriza el descanso y sueño para el crecimiento muscular"
            };
            texto = consejos[(int) (Math.random() * consejos.length)];
        } else {
            icono = "⚖️";
            String[] consejos = {
                "Mantén un balance equilibrado entre proteínas, carbohidratos y grasas",
                "Varía tu dieta para obtener todos los nutrientes necesarios",
                "Mantente hidratado durante todo el día",
                "Escucha a tu cuerpo: come cuando tengas hambre real",
                "Practica alimentación consciente sin distracciones"
            };
            texto = consejos[(int) (Math.random() * consejos.length)];
        }

        txtTituloRecomendacion.setText(titulo);
        txtTextoRecomendacion.setText(texto);
        iconoRecomendacion.setText(icono);
    }

    private void cargarCaloriasConsumidas(int metaCalorias) {
        StringRequest request = new StringRequest(
                Request.Method.POST,
                URL_CALORIAS,
                response -> {
                    android.util.Log.d("MainActivity", "Respuesta calorias: " + response);
                    try {
                        JSONObject json = new JSONObject(response);
                        int caloriasConsumidas = json.optInt("calorias", 0);
                        android.util.Log.d("MainActivity", "Calorias consumidas: " + caloriasConsumidas);
                        txtCaloriasConsumidas.setText(String.valueOf(caloriasConsumidas));

                        int restantes = metaCalorias - caloriasConsumidas;
                        if (restantes < 0) {
                            txtCaloriasRestantes.setText(Math.abs(restantes) + " kcal excedentes");
                            txtCaloriasRestantes.setTextColor(Color.parseColor("#FF6B6B"));
                        } else {
                            txtCaloriasRestantes.setText(restantes + " kcal restantes");
                            txtCaloriasRestantes.setTextColor(Color.parseColor("#4CAF50"));
                        }
                    } catch (JSONException e) {
                        android.util.Log.e("MainActivity", "Error JSON: " + e.getMessage());
                        txtCaloriasConsumidas.setText("0");
                        txtCaloriasRestantes.setText(metaCalorias + " kcal restantes");
                    }
                },
                error -> {
                    android.util.Log.e("MainActivity", "Error calorias: " + error.getMessage());
                    Toast.makeText(this, "Error al cargar calorías", Toast.LENGTH_SHORT).show();
                    txtCaloriasConsumidas.setText("0");
                    txtCaloriasRestantes.setText(metaCalorias + " kcal restantes");
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("usuario_id", String.valueOf(idUsuario));
                android.util.Log.d("MainActivity", "Enviando usuario_id: " + idUsuario);
                return params;
            }
        };

        Volley.newRequestQueue(this).add(request);
    }

    private void configurarNavegacion() {


        navInicio.setOnClickListener(v -> {
            Toast.makeText(
                    this,
                    "Ya estás en Inicio",
                    Toast.LENGTH_SHORT
            ).show();
        });

        if (btnHistorialAlimentacion != null) {
            btnHistorialAlimentacion.setOnClickListener(v -> {
                Intent intent =
                        new Intent(
                                MainActivity.this,
                                HistorialActivity.class
                        );
                enviarDatosUsuario(intent);
                startActivity(intent);
            });
        }

        navAlimentos.setOnClickListener(v -> {

            Intent intent =
                    new Intent(
                            MainActivity.this,
                            AlimentosActivity.class
                    );

            enviarDatosUsuario(intent);

            startActivity(intent);
        });

        navRegistro.setOnClickListener(v -> {

            Intent intent =
                    new Intent(
                            MainActivity.this,
                            RegistroConsumoActivity.class
                    );

            enviarDatosUsuario(intent);

            startActivity(intent);
        });

        navHabitos.setOnClickListener(v -> {

            Intent intent =
                    new Intent(
                            MainActivity.this,
                            HabitosActivity.class
                    );

            enviarDatosUsuario(intent);

            startActivity(intent);
        });

        navProgreso.setOnClickListener(v -> {

            Intent intent =
                    new Intent(
                            MainActivity.this,
                            ProgresoActivity.class
                    );

            enviarDatosUsuario(intent);

            startActivity(intent);
        });

        navPerfil.setOnClickListener(v -> {

            Intent intent =
                    new Intent(
                            MainActivity.this,
                            PerfilActivity.class
                    );

            enviarDatosUsuario(intent);

            startActivity(intent);
        });
    }

    private void enviarDatosUsuario(Intent intent) {

        intent.putExtra(
                "idUsuario",
                idUsuario
        );

        String nombre =
                getIntent().getStringExtra(
                        "nombre"
                );

        String apellido =
                getIntent().getStringExtra(
                        "apellido"
                );

        String correo =
                getIntent().getStringExtra(
                        "correo"
                );

        intent.putExtra(
                "nombre",
                nombre
        );

        intent.putExtra(
                "apellido",
                apellido
        );

        intent.putExtra(
                "correo",
                correo
        );
    }
}