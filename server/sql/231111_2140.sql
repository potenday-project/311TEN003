-- bside.attach definition

CREATE TABLE `attach`
(
    `attach_no`          bigint NOT NULL AUTO_INCREMENT,
    `created_by`         bigint                            DEFAULT NULL,
    `created_date`       datetime(6)                       DEFAULT NULL,
    `last_modified_by`   bigint                            DEFAULT NULL,
    `last_modified_date` datetime(6)                       DEFAULT NULL,
    `ref_no`             bigint                            DEFAULT NULL,
    `attach_type`        enum ('ALCOHOL','POST','PROFILE') DEFAULT NULL,
    `attach_url`         varchar(255)                      DEFAULT NULL,
    `del_yn`             enum ('N','Y')                    DEFAULT 'N',
    `description`        varchar(255)                      DEFAULT NULL,
    `origin_filename`    varchar(255)                      DEFAULT NULL,
    `public_id`          varchar(255)                      DEFAULT NULL,
    `ref_table`          varchar(255)                      DEFAULT NULL,
    `save_file_name`     varchar(255)                      DEFAULT NULL,
    `save_location`      varchar(255)                      DEFAULT NULL,
    PRIMARY KEY (`attach_no`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;