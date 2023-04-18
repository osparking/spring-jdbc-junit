-- PUBLIC.TBL_MOVIES definition

-- Drop table

-- DROP TABLE PUBLIC.TBL_MOVIES;

-- moviesdb.tbl_movies definition

-- 이 파일은 SQL CLI 에서 수작업으로 실행하여 테이블을 만들 것

CREATE TABLE `moviesdb`.`tbl_movies` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(100) DEFAULT NULL,
  `GENERA` varchar(100) DEFAULT NULL,
  `RELEASE_DATE` date DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;