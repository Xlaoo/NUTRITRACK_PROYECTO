CREATE DATABASE  IF NOT EXISTS `nutritrack` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `nutritrack`;
-- MySQL dump 10.13  Distrib 8.0.45, for macos15 (arm64)
--
-- Host: 127.0.0.1    Database: nutritrack
-- ------------------------------------------------------
-- Server version	8.4.11

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `alimentos`
--

DROP TABLE IF EXISTS `alimentos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `alimentos` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(150) COLLATE utf8mb4_general_ci NOT NULL,
  `calorias` decimal(6,2) DEFAULT '0.00',
  `proteinas` decimal(6,2) DEFAULT '0.00',
  `carbohidratos` decimal(6,2) DEFAULT '0.00',
  `grasas` decimal(6,2) DEFAULT '0.00',
  `categoria` varchar(100) COLLATE utf8mb4_general_ci DEFAULT '',
  `descripcion` text COLLATE utf8mb4_general_ci,
  `azucares` decimal(6,2) DEFAULT '0.00',
  `sodio` decimal(6,2) DEFAULT '0.00',
  `fecha_creacion` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `alimentos`
--

LOCK TABLES `alimentos` WRITE;
/*!40000 ALTER TABLE `alimentos` DISABLE KEYS */;
INSERT INTO `alimentos` VALUES (1,'Manzana',52.00,0.30,14.00,0.20,'Frutas','Manzana fresca rica en fibra y agua',10.40,1.00,'2026-10-10 18:49:48'),(2,'Plátano',89.00,1.10,22.80,0.30,'Frutas','Plátano maduro fuente natural de potasio',12.20,1.00,'2026-10-10 18:49:48'),(3,'Arroz blanco cocido',130.00,2.70,28.20,0.30,'Cereales','Arroz blanco cocido básico en la dieta',0.10,1.00,'2026-10-10 18:49:48'),(4,'Pechuga de pollo',165.00,31.00,0.00,3.60,'Carnes','Pechuga de pollo magra cocida a la plancha',0.00,74.00,'2026-10-10 18:49:48'),(5,'Huevo',155.00,13.00,1.10,11.00,'Proteínas','Huevo entero de gallina cocido',1.10,124.00,'2026-10-10 18:49:48'),(6,'Leche entera',61.00,3.20,4.80,3.30,'Lácteos','Leche entera pasteurizada líquida',5.10,43.00,'2026-10-10 18:49:48'),(7,'Avena',389.00,16.90,66.30,6.90,'Cereales','Avena en hojuelas integral rica en fibra',0.00,2.00,'2026-10-10 18:49:48'),(8,'Papa sancochada',87.00,1.90,20.10,0.10,'Tubérculos','Papa cocida o hervida sin piel',0.90,6.00,'2026-10-10 18:49:48'),(9,'Pan integral',247.00,13.00,41.30,3.40,'Panadería','Pan elaborado con harina de trigo integral',4.30,400.00,'2026-10-10 18:49:48'),(10,'Yogur natural',61.00,3.50,4.70,3.30,'Lácteos','Yogur natural sin azúcar añadida',4.70,46.00,'2026-10-10 18:49:48'),(11,'Pescado',105.00,22.00,0.00,1.20,'Pescados','Filete de pescado blanco cocido',0.00,68.00,'2026-10-10 18:49:48'),(12,'Zanahoria',41.00,0.90,9.60,0.20,'Verduras','Zanahoria fresca fuente de betacarotenos',4.70,69.00,'2026-10-10 18:49:48'),(13,'Brócoli',34.00,2.80,6.60,0.40,'Verduras','Brócoli fresco rico en antioxidantes y vitamina C',1.70,33.00,'2026-10-10 18:49:48'),(14,'Naranja',47.00,0.90,11.80,0.10,'Frutas','Naranja fresca rica en vitamina C',9.40,0.00,'2026-10-10 18:49:48'),(15,'Lentejas cocidas',116.00,9.00,20.10,0.40,'Legumbres','Lentejas cocidas ricas en hierro y proteína vegetal',1.80,2.00,'2026-10-10 18:49:48'),(16,'Carne cocida',250.00,26.00,0.00,15.00,'Proteínas','0',0.00,60.00,'2026-10-15 20:25:52'),(17,'Camote',86.00,1.60,20.00,0.10,'Carbohidratos','0',4.20,5.00,'2026-10-15 20:25:52'),(18,'Jamón de pavo',74.00,16.00,0.50,1.00,'Proteínas','0',0.50,800.00,'2026-10-15 20:25:52'),(19,'Queso light',180.00,25.00,1.00,8.00,'Lácteos','0',0.50,400.00,'2026-10-15 20:25:52');
/*!40000 ALTER TABLE `alimentos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `codigos_recuperacion`
--

DROP TABLE IF EXISTS `codigos_recuperacion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `codigos_recuperacion` (
  `id` int NOT NULL AUTO_INCREMENT,
  `usuario_id` int NOT NULL,
  `codigo` varchar(50) COLLATE utf8mb4_general_ci NOT NULL,
  `token` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `expiracion` datetime NOT NULL,
  `intentos` int DEFAULT '0',
  `utilizado` tinyint(1) DEFAULT '0',
  `fecha_creacion` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `usuario_id` (`usuario_id`),
  KEY `token` (`token`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `codigos_recuperacion`
--

LOCK TABLES `codigos_recuperacion` WRITE;
/*!40000 ALTER TABLE `codigos_recuperacion` DISABLE KEYS */;
INSERT INTO `codigos_recuperacion` VALUES (1,1,'629118',NULL,'2026-10-07 04:57:44',0,1,'2026-10-07 02:42:44'),(2,1,'308221',NULL,'2026-10-07 04:59:50',0,1,'2026-10-07 02:44:50'),(3,1,'724144','73772ecf2755370aa13243a7ac4f1a3682b5ba1779b63974','2026-10-07 05:02:13',1,1,'2026-10-07 02:47:13');
/*!40000 ALTER TABLE `codigos_recuperacion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `configuracion`
--

DROP TABLE IF EXISTS `configuracion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `configuracion` (
  `id` int NOT NULL AUTO_INCREMENT,
  `mantenimiento` tinyint(1) DEFAULT '0',
  `notificaciones` tinyint(1) DEFAULT '1',
  `registro_usuarios` tinyint(1) DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `configuracion`
--

LOCK TABLES `configuracion` WRITE;
/*!40000 ALTER TABLE `configuracion` DISABLE KEYS */;
INSERT INTO `configuracion` VALUES (1,0,1,1);
/*!40000 ALTER TABLE `configuracion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `consumos`
--

DROP TABLE IF EXISTS `consumos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `consumos` (
  `id` int NOT NULL AUTO_INCREMENT,
  `usuario_id` int NOT NULL,
  `alimento_id` int NOT NULL,
  `cantidad` decimal(6,2) NOT NULL,
  `unidad` varchar(50) COLLATE utf8mb4_general_ci DEFAULT 'g',
  `momento` varchar(50) COLLATE utf8mb4_general_ci DEFAULT '',
  `fecha_consumo` varchar(50) COLLATE utf8mb4_general_ci NOT NULL,
  `fecha_registro` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `nombre_alimento` varchar(150) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `calorias` decimal(8,2) DEFAULT '0.00',
  `proteinas` decimal(8,2) DEFAULT '0.00',
  `carbohidratos` decimal(8,2) DEFAULT '0.00',
  `grasas` decimal(8,2) DEFAULT '0.00',
  PRIMARY KEY (`id`),
  KEY `fk_consumos_usuario` (`usuario_id`),
  CONSTRAINT `fk_consumos_usuario` FOREIGN KEY (`usuario_id`) REFERENCES `usuarios` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `consumos`
--

LOCK TABLES `consumos` WRITE;
/*!40000 ALTER TABLE `consumos` DISABLE KEYS */;
INSERT INTO `consumos` VALUES (1,1,1,150.00,'gramos','Desayuno','2026-10-10','2026-10-10 18:54:19','Manzana',78.00,0.45,21.00,0.30),(2,1,2,200.00,'gramos','Desayuno','2026-10-10','2026-10-10 18:58:53','Plátano',178.00,2.20,45.60,0.60),(3,3,1,3.00,'unidad','Desayuno','2026-10-14','2026-10-15 00:36:23','Manzana',156.00,0.90,42.00,0.60),(4,3,7,300.00,'gramos','Desayuno','2026-10-14','2026-10-15 00:44:08','Avena',1167.00,50.70,198.90,20.70),(5,3,6,200.00,'mililitros','Desayuno','2026-10-14','2026-10-15 00:44:21','Leche entera',122.00,6.40,9.60,6.60),(6,4,13,200.00,'gramos','Almuerzo','2026-10-15','2026-10-15 20:01:05','Brócoli',68.00,5.60,13.20,0.80),(7,4,3,100.00,'gramos','Almuerzo','2026-10-15','2026-10-15 20:01:22','Arroz blanco cocido',130.00,2.70,28.20,0.30),(8,4,4,300.00,'gramos','Almuerzo','2026-10-15','2026-10-15 20:01:50','Pechuga de pollo',495.00,93.00,0.00,10.80),(9,4,3,300.00,'gramos','Almuerzo','2026-10-24','2026-10-24 18:29:03','Arroz blanco cocido',390.00,8.10,84.60,0.90);
/*!40000 ALTER TABLE `consumos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `contenidos_educativos`
--

DROP TABLE IF EXISTS `contenidos_educativos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `contenidos_educativos` (
  `id` int NOT NULL AUTO_INCREMENT,
  `titulo` varchar(200) NOT NULL,
  `categoria` varchar(100) DEFAULT NULL,
  `contenido` text NOT NULL,
  `fecha_publicacion` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contenidos_educativos`
--

LOCK TABLES `contenidos_educativos` WRITE;
/*!40000 ALTER TABLE `contenidos_educativos` DISABLE KEYS */;
/*!40000 ALTER TABLE `contenidos_educativos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `historial_alimentacion`
--

DROP TABLE IF EXISTS `historial_alimentacion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `historial_alimentacion` (
  `id` int NOT NULL AUTO_INCREMENT,
  `usuario_id` int NOT NULL,
  `nombre_alimento` varchar(150) NOT NULL,
  `cantidad` decimal(8,2) NOT NULL,
  `unidad` varchar(30) DEFAULT 'gramos',
  `calorias` decimal(8,2) DEFAULT '0.00',
  `proteinas` decimal(8,2) DEFAULT '0.00',
  `carbohidratos` decimal(8,2) DEFAULT '0.00',
  `grasas` decimal(8,2) DEFAULT '0.00',
  `tipo_comida` varchar(30) DEFAULT NULL,
  `fecha_consumo` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `fk_historial_usuario` (`usuario_id`),
  CONSTRAINT `fk_historial_usuario` FOREIGN KEY (`usuario_id`) REFERENCES `usuarios` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `historial_alimentacion`
--

LOCK TABLES `historial_alimentacion` WRITE;
/*!40000 ALTER TABLE `historial_alimentacion` DISABLE KEYS */;
/*!40000 ALTER TABLE `historial_alimentacion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mediciones`
--

DROP TABLE IF EXISTS `mediciones`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `mediciones` (
  `id` int NOT NULL AUTO_INCREMENT,
  `usuario_id` int NOT NULL,
  `peso` decimal(5,2) NOT NULL,
  `altura` decimal(5,2) NOT NULL,
  `fecha_medicion` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mediciones`
--

LOCK TABLES `mediciones` WRITE;
/*!40000 ALTER TABLE `mediciones` DISABLE KEYS */;
INSERT INTO `mediciones` VALUES (1,1,71.00,1.75,'2026-10-07 01:20:14'),(2,4,72.00,175.00,'2026-10-15 20:00:22');
/*!40000 ALTER TABLE `mediciones` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `recomendaciones`
--

DROP TABLE IF EXISTS `recomendaciones`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `recomendaciones` (
  `id` int NOT NULL AUTO_INCREMENT,
  `usuario_id` int NOT NULL,
  `titulo` varchar(150) DEFAULT NULL,
  `descripcion` text,
  `fecha_generada` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `usuario_id` (`usuario_id`),
  CONSTRAINT `recomendaciones_ibfk_1` FOREIGN KEY (`usuario_id`) REFERENCES `usuarios` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `recomendaciones`
--

LOCK TABLES `recomendaciones` WRITE;
/*!40000 ALTER TABLE `recomendaciones` DISABLE KEYS */;
/*!40000 ALTER TABLE `recomendaciones` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `registros_diarios`
--

DROP TABLE IF EXISTS `registros_diarios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `registros_diarios` (
  `id` int NOT NULL AUTO_INCREMENT,
  `usuario_id` int NOT NULL,
  `alimento_id` int NOT NULL,
  `cantidad` decimal(10,2) NOT NULL,
  `fecha_registro` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `usuario_id` (`usuario_id`),
  KEY `alimento_id` (`alimento_id`),
  CONSTRAINT `registros_diarios_ibfk_1` FOREIGN KEY (`usuario_id`) REFERENCES `usuarios` (`id`) ON DELETE CASCADE,
  CONSTRAINT `registros_diarios_ibfk_2` FOREIGN KEY (`alimento_id`) REFERENCES `alimentos` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `registros_diarios`
--

LOCK TABLES `registros_diarios` WRITE;
/*!40000 ALTER TABLE `registros_diarios` DISABLE KEYS */;
/*!40000 ALTER TABLE `registros_diarios` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuarios`
--

DROP TABLE IF EXISTS `usuarios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuarios` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(100) COLLATE utf8mb4_general_ci NOT NULL,
  `apellido` varchar(100) COLLATE utf8mb4_general_ci NOT NULL,
  `correo` varchar(150) COLLATE utf8mb4_general_ci NOT NULL,
  `password` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `edad` int DEFAULT NULL,
  `sexo` varchar(20) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `peso` decimal(5,2) DEFAULT NULL,
  `altura` decimal(5,2) DEFAULT NULL,
  `fecha_registro` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `rol` varchar(50) COLLATE utf8mb4_general_ci DEFAULT 'usuario',
  `nivel_actividad` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `objetivo_alimentacion` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `correo` (`correo`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuarios`
--

LOCK TABLES `usuarios` WRITE;
/*!40000 ALTER TABLE `usuarios` DISABLE KEYS */;
INSERT INTO `usuarios` VALUES (1,'Juan','Perez','juan.perez@example.com','$2y$10$FrVY43BNTKLHCjE2.QF5iuxDpLwOyYctDCkkskVYa6YjWpS3l5PWS',26,'Masculino',71.00,1.75,'2026-10-07 01:18:57','usuario','Moderado','Mantener peso'),(2,'Administrador','NutriTrack','admin@gmail.com','$2y$10$N7xuWYF6gdeTF.XJtWrKGe7alc8YLOw7CdN6Gciezv7jrcTY9OK9q',NULL,NULL,NULL,NULL,'2026-10-07 01:50:01','administrador',NULL,NULL),(3,'renato','susaya','renatosusaya@gmail.com','$2y$12$.NegaAKA/OqrZqe4ItxR9.gyj1tCtpESp2oTie.R4GG.p2kwIsOcW',21,'Hombre',100.00,1.75,'2026-10-15 00:24:49','usuario','Actividad física media','Perder peso'),(4,'ricardo','palma','ricardo@gmail.com','$2y$12$JvQY2YKTJZNgcWyUZ4NJ6u6jgRE4rVrrQm84Y7nM8x3JjupWqskp6',20,'Hombre',72.00,175.00,'2026-10-15 19:43:07','usuario','Físicamente activo','Mejorar masa muscular');
/*!40000 ALTER TABLE `usuarios` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'nutritrack'
--

--
-- Dumping routines for database 'nutritrack'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-10-31 11:49:15
