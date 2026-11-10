<?php
/**
 * Obtener datos de usuario para MainActivity
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

$idUsuario = $_POST["idUsuario"] ?? "";

if (empty($idUsuario)) {
    echo json_encode(
        [
            "success" => false,
            "message" => "ID de usuario requerido"
        ],
        JSON_UNESCAPED_UNICODE
    );
    exit;
}

$sql = "SELECT
            id,
            nombre,
            apellido,
            correo,
            edad,
            sexo,
            peso,
            altura,
            nivel_actividad,
            objetivo_alimentacion
        FROM usuarios
        WHERE id = ?";

$stmt = $conexion->prepare($sql);

$stmt->bind_param(
    "i",
    $idUsuario
);

$stmt->execute();

$resultado = $stmt->get_result();

if ($resultado->num_rows > 0) {
    $usuario = $resultado->fetch_assoc();

    echo json_encode(
        [
            "success" => true,
            "usuario" => $usuario
        ],
        JSON_UNESCAPED_UNICODE
    );
} else {
    echo json_encode(
        [
            "success" => false,
            "message" => "Usuario no encontrado"
        ],
        JSON_UNESCAPED_UNICODE
    );
}

$stmt->close();
$conexion->close();
?>
