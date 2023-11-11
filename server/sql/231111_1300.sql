-- bside.post_quote definition

-- bside.alcohol_type definition

CREATE TABLE `alcohol_type`
(
    `alcohol_type_no`    bigint NOT NULL AUTO_INCREMENT,
    `created_by`         bigint         DEFAULT NULL,
    `created_date`       datetime(6)    DEFAULT NULL,
    `last_modified_by`   bigint         DEFAULT NULL,
    `last_modified_date` datetime(6)    DEFAULT NULL,
    `del_yn`             enum ('N','Y') DEFAULT 'N',
    `description`        varchar(255)   DEFAULT NULL,
    `name`               varchar(255)   DEFAULT NULL,
    PRIMARY KEY (`alcohol_type_no`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 5
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE `post_quote`
(
    `created_by`         bigint         DEFAULT NULL,
    `created_date`       datetime(6)    DEFAULT NULL,
    `last_modified_by`   bigint         DEFAULT NULL,
    `last_modified_date` datetime(6)    DEFAULT NULL,
    `post_no`            bigint         DEFAULT NULL,
    `post_quote_no`      bigint NOT NULL AUTO_INCREMENT,
    `quote_no`           bigint         DEFAULT NULL,
    `del_yn`             enum ('N','Y') DEFAULT 'N',
    PRIMARY KEY (`post_quote_no`),
    KEY `FK6ay3nwfna4k4mqq99g3cvfwpx` (`post_no`),
    KEY `FKsahkvutk2rot924eigkmie3kh` (`quote_no`),
    CONSTRAINT `FK6ay3nwfna4k4mqq99g3cvfwpx` FOREIGN KEY (`post_no`) REFERENCES `post` (`post_no`),
    CONSTRAINT `FKsahkvutk2rot924eigkmie3kh` FOREIGN KEY (`quote_no`) REFERENCES `post` (`post_no`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;


ALTER TABLE bside.alcohol
    ADD alcohol_type_no bigint NULL;
ALTER TABLE bside.alcohol
    CHANGE alcohol_type_no alcohol_type_no bigint NULL AFTER alcohol_no;

ALTER TABLE bside.alcohol
    ADD CONSTRAINT FK_alcohol_alcohol_type
        FOREIGN KEY (alcohol_type_no) REFERENCES alcohol_type (alcohol_type_no);
