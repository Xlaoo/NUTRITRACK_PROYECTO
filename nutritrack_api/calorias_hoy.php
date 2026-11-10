<?php
/**
 * Obtener calorías consumidas hoy - NutriTrack
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

// 1. Recibir parámetro usuario_id
$usuario_id = $_POST["usuario_id"] ?? "";

// 2. Validación básica
if (empty($usuario_id)) {
    http_response_code(400);
    echo json_encode(
        [
            "success" => false,
            "message" => "El ID de usuario es requerido"
        ],
        JSON_UNESCAPED_UNICODE
    );
    exit;
}

// 3. Validación de usuario_id
$usuario_id_val = filter_var($usuario_id, FILTER_VALIDATE_INT);
if ($usuario_id_val === false || $usuario_id_val <= 0) {
    http_response_code(400);
    echo json_encode(
        [
            "success" => false,
            "message" => "El ID de usuario no es válido"
        ],
        JSON_UNESCAPED_UNICODE
    );
    exit;
}

try {
    // 4. Obtener la fecha de hoy en formato YYYY-MM-DD
    $fecha_hoy = date("Y-m-d");

    // 5. Consultar suma de calorías consumidas hoy por el usuario
    $sql = "
        SELECT SUM(calorias) as total_calorias
        FROM consumos
        WHERE usuario_id = ?
        AND fecha_consumo = ?
    ";

    $stmt = $conexion->prepare($sql);
    if (!$stmt) {
        throw new Exception("Error al preparar la consulta de calorías");
    }

    $stmt->bind_param("is", $usuario_id_val, $fecha_hoy);
    $stmt->execute();
    $resultado = $stmt->get_result();

    $fila = $resultado->fetch_assoc();
    $stmt->close();

    // 6. Devolver el total (0 si no hay consumos)
    $total_calorias = $fila["total_calorias"] ?? 0;
    if ($total_calorias === null) {
        $total_calorias = 0;
    }

    echo json_encode(
        [
            "success" => true,
            "calorias" => $total_calorias,
            "fecha" => $fecha_hoy
        ],
        JSON_UNESCAPED_UNICODE
    );

    $conexion->close();

} catch (Throwable $e) {
    http_response_code(500);
    echo json_encode(
        [
            "success" => false,
            "message" => "Error del servidor: " . $e->getMessage()
        ],
        JSON_UNESCAPED_UNICODE
    );
}
?>
