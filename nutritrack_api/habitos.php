<?php
/**
 * API para obtener hábitos alimenticios del usuario
 *
 * PHP version 8.0+
 *
 * @category HR
 * @package  NutriTrack
 * @author   Cognition <devin@cognition.com>
 * @license  http://opensource.org/licenses/MIT MIT License
 * @link     http://nutritrack.com
 */

header("Content-Type: application/json; charset=UTF-8");

require_once "conexion.php";

// Solo permitir solicitudes POST
if ($_SERVER["REQUEST_METHOD"] !== "POST") {
    echo json_encode(
        [
            "success" => false,
            "message" => "Método no permitido"
        ]
    );
    exit;
}

$usuario_id = $_POST["usuario_id"] ?? 0;

if ($usuario_id <= 0) {
    echo json_encode(
        [
            "success" => false,
            "message" => "ID de usuario no válido"
        ]
    );
    exit;
}

// Obtener registros de los últimos 7 días
$sql = "SELECT c.*, a.categoria, a.nombre
        FROM consumos c
        JOIN alimentos a ON c.alimento_id = a.id
        WHERE c.usuario_id = ?
        AND c.fecha_consumo >= DATE_SUB(CURDATE(), INTERVAL 7 DAY)
        ORDER BY c.fecha_consumo DESC";

$stmt = $conn->prepare($sql);
$stmt->bind_param("i", $usuario_id);
$stmt->execute();
$resultado = $stmt->get_result();

$registros = [];
$dias_con_registros = [];

while ($row = $resultado->fetch_assoc()) {
    $registros[] = $row;
    $fecha = $row["fecha_consumo"];
    if (!in_array($fecha, $dias_con_registros)) {
        $dias_con_registros[] = $fecha;
    }
}

// Inicializar contadores
$frecuencias = [
    "frutas" => 0,
    "verduras" => 0,
    "proteinas" => 0,
    "cereales" => 0,
    "bebidas_azucaradas" => 0
];

$frutas_categorias = ["Frutas", "Frutas frescas"];
$verduras_categorias = ["Verduras", "Hortalizas", "Vegetales"];
$proteinas_categorias = ["Carnes", "Pescados", "Huevos", "Lácteos", "Legumbres"];
$cereales_categorias = ["Cereales", "Granos", "Pan", "Pasta", "Arroz"];
$bebidas_categorias = ["Bebidas azucaradas", "Refrescos", "Jugos procesados"];

$dias_con_frutas = [];
$dias_con_verduras = [];
$dias_con_proteinas = [];
$dias_con_cereales = [];
$dias_con_bebidas = [];

foreach ($registros as $registro) {
    $categoria = $registro["categoria"];
    $fecha = $registro["fecha_consumo"];

    if (in_array($categoria, $frutas_categorias) && !in_array($fecha, $dias_con_frutas)) {
        $dias_con_frutas[] = $fecha;
    }

    if (in_array($categoria, $verduras_categorias) && !in_array($fecha, $dias_con_verduras)) {
        $dias_con_verduras[] = $fecha;
    }

    if (in_array($categoria, $proteinas_categorias) && !in_array($fecha, $dias_con_proteinas)) {
        $dias_con_proteinas[] = $fecha;
    }

    if (in_array($categoria, $cereales_categorias) && !in_array($fecha, $dias_con_cereales)) {
        $dias_con_cereales[] = $fecha;
    }

    if (in_array($categoria, $bebidas_categorias) && !in_array($fecha, $dias_con_bebidas)) {
        $dias_con_bebidas[] = $fecha;
    }
}

// Calcular porcentajes (de los últimos 7 días)
$dias_semana = 7;
$frecuencias["frutas"] = round((count($dias_con_frutas) / $dias_semana) * 100);
$frecuencias["verduras"] = round((count($dias_con_verduras) / $dias_semana) * 100);
$frecuencias["proteinas"] = round((count($dias_con_proteinas) / $dias_semana) * 100);
$frecuencias["cereales"] = round((count($dias_con_cereales) / $dias_semana) * 100);
$frecuencias["bebidas_azucaradas"] = round((count($dias_con_bebidas) / $dias_semana) * 100);

// Generar patrones
$patrones = [];

if (count($dias_con_frutas) >= 5) {
    $patrones[] = "Registraste frutas " . count($dias_con_frutas) . " de 7 días esta semana. ¡Excelente!";
} elseif (count($dias_con_frutas) >= 3) {
    $patrones[] = "Registraste frutas " . count($dias_con_frutas) . " días esta semana. Intenta aumentar un poco más.";
} else {
    $patrones[] = "Solo registraste frutas " . count($dias_con_frutas) . " días. Intenta incluir más frutas en tu dieta.";
}

if (count($dias_con_verduras) >= 5) {
    $patrones[] = "Registraste verduras " . count($dias_con_verduras) . " de 7 días esta semana. ¡Muy bien!";
} elseif (count($dias_con_verduras) >= 3) {
    $patrones[] = "Registraste verduras " . count($dias_con_verduras) . " días esta semana. Puedes mejorar.";
} else {
    $patrones[] = "Solo registraste verduras " . count($dias_con_verduras) . " días. Las verduras son importantes.";
}

if (count($dias_con_bebidas) >= 3) {
    $patrones[] = "Consumiste bebidas azucaradas " . count($dias_con_bebidas) . " días. Intenta reducirlas.";
}

if (count($dias_con_registros) >= 6) {
    $patrones[] = "Registraste tu alimentación " . count($dias_con_registros) . " días. ¡Gran constancia!";
} elseif (count($dias_con_registros) >= 4) {
    $patrones[] = "Registraste tu alimentación " . count($dias_con_registros) . " días. Sigue así.";
} else {
    $patrones[] = "Solo registraste " . count($dias_con_registros) . " días. Intenta registrar más días.";
}

echo json_encode(
    [
        "success" => true,
        "frecuencias" => $frecuencias,
        "patrones" => $patrones,
        "dias_registrados" => count($dias_con_registros)
    ],
    JSON_UNESCAPED_UNICODE
);

$stmt->close();
$conn->close();

?>
