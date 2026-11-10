package com.example.proyecto_nutritrak.usuario;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;

public class DetalleEducacionActivity extends AppCompatActivity {

    public static final String EXTRA_TEMA = "temaEducativo";

    private static final int VERDE = Color.rgb(39, 139, 88);
    private static final int TEXTO = Color.rgb(35, 59, 49);
    private static final int GRIS = Color.rgb(95, 111, 102);
    private static final int FONDO = Color.rgb(245, 248, 246);

    private LinearLayout contenido;
    private TextView resultado;

    private int tema;
    private int respuestaSeleccionada = -1;
    private int respuestaCorrecta;

    private String explicacion;
    private String fuenteUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        tema = getIntent().getIntExtra(EXTRA_TEMA, 0);

        if (tema < 0 || tema > 2) {
            tema = 0;
        }

        if (savedInstanceState != null) {
            respuestaSeleccionada =
                    savedInstanceState.getInt("respuesta", -1);
        }

        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);

        construirPantalla();

        if (respuestaSeleccionada >= 0
                && respuestaSeleccionada < 3) {
            mostrarResultado(respuestaSeleccionada);
        }
    }

    private void construirPantalla() {
        LinearLayout raiz = new LinearLayout(this);
        raiz.setOrientation(LinearLayout.VERTICAL);
        raiz.setBackgroundColor(FONDO);

        setContentView(raiz);

        WindowCompat.getInsetsController(getWindow(), raiz)
                .setAppearanceLightStatusBars(true);

        WindowCompat.getInsetsController(getWindow(), raiz)
                .setAppearanceLightNavigationBars(true);

        ViewCompat.setOnApplyWindowInsetsListener(
                raiz,
                (view, insets) -> {
                    Insets barras = insets.getInsets(
                            WindowInsetsCompat.Type.systemBars()
                    );

                    view.setPadding(
                            barras.left,
                            barras.top,
                            barras.right,
                            barras.bottom
                    );

                    return insets;
                }
        );

        ViewCompat.requestApplyInsets(raiz);

        // Cabecera
        LinearLayout cabecera = new LinearLayout(this);
        cabecera.setOrientation(LinearLayout.HORIZONTAL);
        cabecera.setGravity(Gravity.CENTER_VERTICAL);
        cabecera.setPadding(dp(8), dp(8), dp(16), dp(8));
        cabecera.setBackgroundColor(Color.WHITE);

        Button volver = new Button(this);
        volver.setText("‹");
        volver.setTextSize(30);
        volver.setTextColor(VERDE);
        volver.setContentDescription("Volver a Aprende");
        volver.setBackground(fondo(Color.WHITE, 12));
        volver.setOnClickListener(v -> finish());

        cabecera.addView(
                volver,
                new LinearLayout.LayoutParams(dp(48), dp(48))
        );

        TextView tituloCabecera = texto(
                "Educación nutricional",
                19,
                TEXTO,
                true
        );

        LinearLayout.LayoutParams tituloParams =
                new LinearLayout.LayoutParams(
                        0,
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        1
                );

        tituloParams.setMarginStart(dp(8));
        cabecera.addView(tituloCabecera, tituloParams);

        raiz.addView(cabecera);

        // Contenido desplazable
        ScrollView scroll = new ScrollView(this);
        scroll.setFillViewport(true);

        raiz.addView(
                scroll,
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        0,
                        1
                )
        );

        contenido = new LinearLayout(this);
        contenido.setOrientation(LinearLayout.VERTICAL);
        contenido.setPadding(dp(20), dp(20), dp(20), dp(24));

        scroll.addView(contenido);

        TextView categoria = texto(
                "APRENDE · NUTRITRACK",
                12,
                VERDE,
                true
        );
        agregar(contenido, categoria, 0);

        switch (tema) {
            case 1:
                crearTemaPorciones();
                break;

            case 2:
                crearTemaVariedad();
                break;

            default:
                crearTemaEtiquetas();
                break;
        }

        crearPregunta();
        crearFuentes();

        Button finalizar = boton("Volver a Aprende");
        finalizar.setOnClickListener(v -> finish());
        agregar(contenido, finalizar, 24);
    }

    private void crearTemaEtiquetas() {
        agregar(
                contenido,
                texto("Cómo leer una etiqueta nutricional",
                        27, TEXTO, true),
                10
        );

        agregar(
                contenido,
                texto(
                        "Aprende a identificar la cantidad de referencia "
                                + "y a interpretar la información del envase.",
                        16, GRIS, false
                ),
                12
        );

        LinearLayout ejemplo = tarjeta(
                Color.rgb(234, 243, 255)
        );

        agregar(
                ejemplo,
                texto("▤  Información nutricional",
                        20, TEXTO, true),
                0
        );

        agregar(
                ejemplo,
                texto("Producto ficticio · Ejemplo educativo",
                        12, GRIS, false),
                6
        );

        fila(ejemplo, "Tamaño de porción", "30 g");
        fila(ejemplo, "Porciones por envase", "4");
        fila(ejemplo, "Energía por porción", "120 kcal");

        bloque(
                "01",
                "Empieza por la porción",
                "Los valores de este ejemplo corresponden a 30 g. "
                        + "Antes de interpretar una etiqueta, revisa si "
                        + "informa por porción o por 100 g o 100 ml."
        );

        bloque(
                "02",
                "Revisa cuánto contiene el envase",
                "Un paquete puede contener varias porciones. "
                        + "En este ejemplo contiene cuatro, "
                        + "por lo que el paquete completo pesa 120 g."
        );

        bloque(
                "03",
                "Relaciona los datos con la cantidad",
                "Si consumes dos porciones del ejemplo, multiplicas "
                        + "la energía por dos: 120 × 2 = 240 kcal. "
                        + "Para comparar productos, utiliza la misma "
                        + "cantidad de referencia."
        );

        respuestaCorrecta = 1;
        explicacion = "Dos porciones equivalen a 240 kcal: "
                + "120 kcal × 2 = 240 kcal.";

        fuenteUrl =
                "https://www.fda.gov/food/nutrition-facts-label/"
                        + "como-entender-y-utilizar-la-etiqueta-de-informacion-nutricional";
    }

    private void crearTemaPorciones() {
        agregar(
                contenido,
                texto("Comprende las porciones",
                        27, TEXTO, true),
                10
        );

        agregar(
                contenido,
                texto(
                        "Distingue el contenido del envase de la cantidad "
                                + "a la que se refiere su etiqueta.",
                        16, GRIS, false
                ),
                12
        );

        LinearLayout ejemplo = tarjeta(
                Color.rgb(255, 246, 229)
        );

        agregar(
                ejemplo,
                texto("Un envase, cuatro porciones",
                        20, TEXTO, true),
                0
        );

        TextView dibujo = texto("◼   ◼   ◼   ◼", 34, VERDE, true);
        dibujo.setGravity(Gravity.CENTER);
        dibujo.setContentDescription(
                "Cuatro bloques que representan cuatro porciones iguales"
        );
        agregar(ejemplo, dibujo, 18);

        agregar(
                ejemplo,
                texto("Cada bloque representa 50 g.",
                        14, GRIS, false),
                8
        );

        fila(ejemplo, "Contenido del envase", "200 g");
        fila(ejemplo, "Una porción", "50 g");
        fila(ejemplo, "Porciones por envase", "4");

        agregar(
                ejemplo,
                texto("Cantidades ficticias para practicar.",
                        12, GRIS, false),
                12
        );

        bloque(
                "01",
                "¿Qué significa una porción?",
                "En una etiqueta, es la cantidad de alimento a la que "
                        + "corresponden los valores nutricionales indicados."
        );

        bloque(
                "02",
                "El envase no siempre es una porción",
                "Para calcular cuántas porciones contiene este envase, "
                        + "divide 200 g entre 50 g. El resultado es cuatro."
        );

        bloque(
                "03",
                "Una referencia para interpretar",
                "La porción de la etiqueta no indica necesariamente "
                        + "cuánto debes comer. Sirve para entender "
                        + "la información nutricional presentada."
        );

        respuestaCorrecta = 1;
        explicacion = "Son dos porciones: "
                + "100 g ÷ 50 g por porción = 2.";

        fuenteUrl =
                "https://www.fda.gov/food/nutrition-facts-label/"
                        + "obtenga-informacion-sobre-el-tamano-de-la-porcion";
    }

    private void crearTemaVariedad() {
        agregar(
                contenido,
                texto("Variedad en la alimentación",
                        27, TEXTO, true),
                10
        );

        agregar(
                contenido,
                texto(
                        "Conoce distintos alimentos y las posibilidades "
                                + "para combinarlos en tus comidas.",
                        16, GRIS, false
                ),
                12
        );

        LinearLayout ejemplo = tarjeta(
                Color.rgb(232, 245, 236)
        );

        agregar(
                ejemplo,
                texto("Explora distintos grupos",
                        20, TEXTO, true),
                0
        );

        agregar(
                ejemplo,
                texto("🥕  Frutas y verduras",
                        18, TEXTO, true),
                18
        );

        agregar(
                ejemplo,
                texto("🌾  Cereales integrales",
                        18, TEXTO, true),
                14
        );

        agregar(
                ejemplo,
                texto("🫘  Legumbres",
                        18, TEXTO, true),
                14
        );

        agregar(
                ejemplo,
                texto("🥚  Otras fuentes de proteína",
                        18, TEXTO, true),
                14
        );

        agregar(
                ejemplo,
                texto(
                        "Ejemplos de grupos de alimentos; "
                                + "no representan un menú ni cantidades indicadas.",
                        12, GRIS, false
                ),
                16
        );

        bloque(
                "01",
                "Combina diferentes alimentos",
                "La variedad incluye alimentos de distintos grupos "
                        + "y también opciones diferentes dentro de cada grupo."
        );

        bloque(
                "02",
                "Alterna tus elecciones",
                "Por ejemplo, puedes alternar lentejas y frejoles, "
                        + "o elegir distintas frutas y verduras disponibles."
        );

        bloque(
                "03",
                "Adapta los ejemplos a tu contexto",
                "Considera la disponibilidad, tus costumbres y "
                        + "tus necesidades personales al elegir alimentos."
        );

        respuestaCorrecta = 0;
        explicacion = "Alternar distintos alimentos es un ejemplo "
                + "de variedad dentro de la alimentación.";

        fuenteUrl =
                "https://www.who.int/news-room/fact-sheets/detail/healthy-diet";
    }

    private void crearPregunta() {
        LinearLayout tarjetaPregunta = tarjeta(Color.WHITE);

        agregar(
                tarjetaPregunta,
                texto("Comprueba lo aprendido",
                        20, TEXTO, true),
                0
        );

        agregar(
                tarjetaPregunta,
                texto("Práctica opcional · Puedes volver a intentar",
                        12, GRIS, false),
                6
        );

        String pregunta;
        String[] opciones;

        if (tema == 0) {
            pregunta = "Según el ejemplo, ¿cuánta energía "
                    + "aportan dos porciones?";

            opciones = new String[]{
                    "120 kcal",
                    "240 kcal",
                    "480 kcal"
            };

        } else if (tema == 1) {
            pregunta = "Si una porción equivale a 50 g, "
                    + "¿cuántas porciones hay en 100 g?";

            opciones = new String[]{
                    "Una porción",
                    "Dos porciones",
                    "Cuatro porciones"
            };

        } else {
            pregunta = "¿Cuál es un ejemplo de variedad?";

            opciones = new String[]{
                    "Alternar distintos alimentos",
                    "Elegir siempre un único alimento",
                    "Repetir exactamente todas las comidas"
            };
        }

        agregar(
                tarjetaPregunta,
                texto(pregunta, 16, TEXTO, true),
                18
        );

        for (int i = 0; i < opciones.length; i++) {
            final int indice = i;

            Button opcion = boton(opciones[i]);

            opcion.setOnClickListener(v -> {
                respuestaSeleccionada = indice;
                mostrarResultado(indice);
            });

            agregar(tarjetaPregunta, opcion, 10);
        }

        resultado = texto("", 15, TEXTO, false);
        resultado.setPadding(dp(14), dp(14), dp(14), dp(14));
        resultado.setVisibility(View.GONE);

        ViewCompat.setAccessibilityLiveRegion(
                resultado,
                ViewCompat.ACCESSIBILITY_LIVE_REGION_POLITE
        );

        agregar(tarjetaPregunta, resultado, 14);
    }

    private void mostrarResultado(int seleccion) {
        resultado.setVisibility(View.VISIBLE);

        if (seleccion == respuestaCorrecta) {
            resultado.setBackground(
                    fondo(Color.rgb(232, 245, 236), 12)
            );
            resultado.setTextColor(Color.rgb(28, 100, 59));
            resultado.setText("Correcto\n\n" + explicacion);

        } else {
            resultado.setBackground(
                    fondo(Color.rgb(255, 246, 229), 12)
            );
            resultado.setTextColor(Color.rgb(116, 77, 19));
            resultado.setText(
                    "Revisa el ejemplo\n\n"
                            + explicacion
                            + "\n\nPuedes seleccionar otra respuesta."
            );
        }
    }

    private void crearFuentes() {
        LinearLayout fuentes = tarjeta(Color.WHITE);

        agregar(
                fuentes,
                texto("Fuente de consulta", 17, TEXTO, true),
                0
        );

        String nombreFuente;

        if (tema == 0) {
            nombreFuente =
                    "FDA · Cómo entender y utilizar la etiqueta de información nutricional";

        } else if (tema == 1) {
            nombreFuente =
                    "FDA · Información sobre el tamaño de la porción";

        } else {
            nombreFuente =
                    "Organización Mundial de la Salud · Alimentación saludable";
        }

        agregar(
                fuentes,
                texto(nombreFuente, 14, GRIS, false),
                8
        );

        Button consultar = boton("Consultar fuente");
        consultar.setOnClickListener(v -> abrirFuente());
        agregar(fuentes, consultar, 12);

        agregar(
                contenido,
                texto(
                        "Contenido educativo general. "
                                + "No sustituye la asesoría nutricional individual.",
                        12, GRIS, false
                ),
                20
        );
    }

    private void abrirFuente() {
        try {
            Intent intent = new Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(fuenteUrl)
            );
            startActivity(intent);

        } catch (ActivityNotFoundException e) {
            Toast.makeText(
                    this,
                    "No hay un navegador disponible para abrir el enlace.",
                    Toast.LENGTH_LONG
            ).show();
        }
    }

    private void bloque(String numero, String titulo, String descripcion) {
        LinearLayout tarjeta = tarjeta(Color.WHITE);

        agregar(
                tarjeta,
                texto(numero + "  " + titulo, 18, TEXTO, true),
                0
        );

        agregar(
                tarjeta,
                texto(descripcion, 16, GRIS, false),
                10
        );
    }

    private void fila(LinearLayout padre, String etiqueta, String valor) {
        View linea = new View(this);
        linea.setBackgroundColor(Color.rgb(211, 222, 215));

        LinearLayout.LayoutParams lineaParams =
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        dp(1)
                );

        lineaParams.topMargin = dp(14);
        padre.addView(linea, lineaParams);

        LinearLayout fila = new LinearLayout(this);
        fila.setOrientation(LinearLayout.HORIZONTAL);
        fila.setGravity(Gravity.CENTER_VERTICAL);

        TextView izquierda = texto(etiqueta, 14, TEXTO, false);

        fila.addView(
                izquierda,
                new LinearLayout.LayoutParams(
                        0,
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        1
                )
        );

        TextView derecha = texto(valor, 16, TEXTO, true);
        derecha.setPadding(dp(12), 0, 0, 0);
        fila.addView(derecha);

        agregar(padre, fila, 12);
    }

    private LinearLayout tarjeta(int color) {
        LinearLayout tarjeta = new LinearLayout(this);
        tarjeta.setOrientation(LinearLayout.VERTICAL);
        tarjeta.setPadding(dp(18), dp(18), dp(18), dp(18));
        tarjeta.setBackground(fondo(color, 18));

        agregar(contenido, tarjeta, 18);

        return tarjeta;
    }

    private TextView texto(
            String contenidoTexto,
            int tamano,
            int color,
            boolean negrita
    ) {
        TextView vista = new TextView(this);
        vista.setText(contenidoTexto);
        vista.setTextSize(tamano);
        vista.setTextColor(color);
        vista.setLineSpacing(dp(3), 1.08f);

        vista.setTypeface(
                Typeface.SANS_SERIF,
                negrita ? Typeface.BOLD : Typeface.NORMAL
        );

        return vista;
    }

    private Button boton(String etiqueta) {
        Button boton = new Button(this);
        boton.setText(etiqueta);
        boton.setTextSize(14);
        boton.setAllCaps(false);
        boton.setTextColor(Color.WHITE);
        boton.setTypeface(Typeface.SANS_SERIF, Typeface.BOLD);
        boton.setMinHeight(dp(52));
        boton.setPadding(dp(16), dp(12), dp(16), dp(12));
        boton.setBackgroundTintList(
                android.content.res.ColorStateList.valueOf(VERDE)
        );

        return boton;
    }

    private GradientDrawable fondo(int color, int radio) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setColor(color);
        drawable.setCornerRadius(dp(radio));
        return drawable;
    }

    private void agregar(
            LinearLayout padre,
            View vista,
            int margenSuperior
    ) {
        LinearLayout.LayoutParams parametros =
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );

        parametros.topMargin = dp(margenSuperior);
        padre.addView(vista, parametros);
    }

    private int dp(int valor) {
        return Math.round(
                valor * getResources().getDisplayMetrics().density
        );
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt("respuesta", respuestaSeleccionada);
        super.onSaveInstanceState(outState);
    }
}