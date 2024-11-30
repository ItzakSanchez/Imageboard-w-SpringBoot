DROP DATABASE IF EXISTS imageboard;
CREATE DATABASE imageboard;
USE imageboard;

CREATE TABLE `tbl_post` (
  `id` int NOT NULL AUTO_INCREMENT,
  `id_poster` varchar(255) NOT NULL,
  `nickname` varchar(255),
  `title` varchar(255),
  `content` varchar(8000) NOT NULL,
  `image_path` varchar(255),
  `creation_date_time` timestamp NOT NULL,
  `parent_post_id` int NOT NULL,
  `is_pinned` bit,
  `updated_at` timestamp NOT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

