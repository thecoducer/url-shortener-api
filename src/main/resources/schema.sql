DROP TABLE IF EXISTS url_info;

CREATE TABLE IF NOT EXISTS `url_info` (
  `id` int PRIMARY KEY NOT NULL AUTO_INCREMENT,
  `url` varchar(255) NOT NULL,
  `short_key` varchar(255) UNIQUE NOT NULL,
  `usage_count` int DEFAULT 0,
  `created_at` bigint NOT NULL
);