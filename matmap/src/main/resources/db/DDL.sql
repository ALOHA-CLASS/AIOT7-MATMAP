-- Active: 1788828726528@@127.0.0.1@3306@project

CREATE DATABASE IF NOT EXISTS `project`
    DEFAULT CHARACTER SET = 'utf8mb4';

USE `project`;

-- 외래키 무시
SET FOREIGN_KEY_CHECKS = 0;


DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `no` bigint NOT NULL AUTO_INCREMENT COMMENT 'PK',
  `id` varchar(64) NOT NULL COMMENT 'UK',
  `username` varchar(100) NOT NULL COMMENT '아이디',
  `password` varchar(100) NOT NULL COMMENT '비밀번호',
  `name` varchar(100) NOT NULL COMMENT '이름',
  `tel` varchar(100) NOT NULL COMMENT '전화번호',
  `email` varchar(100) NOT NULL COMMENT '이메일',
  `enabled` tinyint(1) NOT NULL DEFAULT '1' COMMENT '사용여부',
  `note` text DEFAULT NULL COMMENT '비고',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '등록일자',
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일자',
  PRIMARY KEY (`no`),
  UNIQUE KEY `id` (`id`),
  UNIQUE KEY `username` (`username`)
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;



DROP TABLE IF EXISTS `user_auth`;
CREATE TABLE `user_auth` (
  `no` bigint NOT NULL AUTO_INCREMENT COMMENT 'PK',
  `id` varchar(64) NOT NULL COMMENT 'UK',
  `user_no` bigint NOT NULL COMMENT 'FK',
  `username` varchar(100) NOT NULL COMMENT '아이디',
  `auth` varchar(100) NOT NULL COMMENT '권한',
  `name` varchar(100) NOT NULL COMMENT '이름',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '등록일자',
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일자',
  PRIMARY KEY (`no`),
  UNIQUE KEY `id` (`id`),
  KEY `user_no` (`user_no`),
  CONSTRAINT `user_auth_ibfk_1` FOREIGN KEY (`user_no`) REFERENCES `users` (`no`) ON DELETE CASCADE ON UPDATE CASCADE
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

DROP TABLE IF EXISTS `board`;

CREATE TABLE `board` (
	`no` BIGINT NOT NULL AUTO_INCREMENT COMMENT 'PK',
	`id` VARCHAR(64) NOT NULL UNIQUE COMMENT 'UK',
	`type` VARCHAR(100) NULL DEFAULT '공지사항' COMMENT '타입',
	`title` VARCHAR(200) NULL COMMENT '제목',
	`content` TEXT NULL COMMENT '내용',
	`views` INT NULL DEFAULT 0 COMMENT '조회수',
	`seq` INT NULL DEFAULT 0 COMMENT '순서',
	`created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '등록일자',
	`updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일자',
	PRIMARY KEY (`no`)
);

DROP TABLE IF EXISTS `popups`;

CREATE TABLE `popups` (
	`no` BIGINT NOT NULL AUTO_INCREMENT COMMENT 'PK',
	`id` VARCHAR(64) NOT NULL UNIQUE COMMENT 'UK',
	`type` VARCHAR(100) NULL DEFAULT '메인' COMMENT '타입',
	`name` VARCHAR(100) NULL COMMENT '이름',
	`url` TEXT NULL COMMENT '이미지경로',
	`link` TEXT NULL COMMENT '링크',
	`seq` INT NULL DEFAULT 0 COMMENT '순서',
	`content` TEXT NULL COMMENT '내용',
	`started_at` TIMESTAMP NULL COMMENT '시작일',
	`ended_at` TIMESTAMP NULL COMMENT '종료일',
	`is_show` TINYiNT(1) NULL DEFAULT 1 COMMENT '공개여부',
	`created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '등록일자',
	`updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일자',
	PRIMARY KEY (`no`)
);



DROP TABLE IF EXISTS `media`;

CREATE TABLE `media` (
	`no` BIGINT NOT NULL AUTO_INCREMENT COMMENT 'PK',
	`id` VARCHAR(64) NOT NULL UNIQUE COMMENT 'UK',
	`parent_id` VARCHAR(64) NOT NULL COMMENT '부모 UK',
	`is_main` TINYiNT(1) NULL COMMENT '메인미디어',
	`is_thumb` TINYiNT(1) NULL COMMENT '썸네일',
	`thumb_seq` INT NULL COMMENT '썸네일순서',
	`type` ENUM('이미지','동영상','임베드') NULL COMMENT "타입 ('이미지','동영상','임베드')",
	`content` TEXT NULL COMMENT '컨텐츠( URL, img, video, iframe )',
	`path` TEXT NULL COMMENT '파일경로',
	`name` TEXT NULL COMMENT '파일명',
	`origin_name` TEXT NULL COMMENT '원본파일명',
	`created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '등록일자',
	`updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일자',
	PRIMARY KEY (`no`)
);




-- =========================================================
-- 맛집지도 프로젝트 추가 테이블
-- 기존 users, media 테이블을 참조합니다.
-- =========================================================

-- ---------------------------------------------------------
-- 1. 기존 추가 테이블 삭제 (자식 테이블부터 삭제)
-- ---------------------------------------------------------
DROP TABLE IF EXISTS `map_favorites`;
DROP TABLE IF EXISTS `restaurant_media`;
DROP TABLE IF EXISTS `map_shares`;
DROP TABLE IF EXISTS `share_group_members`;
DROP TABLE IF EXISTS `restaurants`;
DROP TABLE IF EXISTS `share_groups`;
DROP TABLE IF EXISTS `map_groups`;


-- ---------------------------------------------------------
-- 2. 맛맵 그룹
-- 한 회원이 여러 개의 맛맵을 생성할 수 있습니다.
-- ---------------------------------------------------------
CREATE TABLE `map_groups` (
    `no` BIGINT NOT NULL AUTO_INCREMENT COMMENT 'PK',
    `id` VARCHAR(64) NOT NULL COMMENT '고유 식별자',
    `owner_no` BIGINT NOT NULL COMMENT '맛맵 소유 회원번호',
    `name` VARCHAR(100) NOT NULL COMMENT '맛맵 이름',
    `description` TEXT DEFAULT NULL COMMENT '맛맵 설명',
    `visibility` ENUM('PRIVATE', 'SHARED', 'PUBLIC')
        NOT NULL DEFAULT 'PRIVATE' COMMENT '공개 범위',
    `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '등록일자',
    `updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
        ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일자',

    PRIMARY KEY (`no`),
    UNIQUE KEY `uk_map_groups_id` (`id`),
    KEY `idx_map_groups_owner` (`owner_no`),
    KEY `idx_map_groups_visibility` (`visibility`),

    CONSTRAINT `fk_map_groups_owner`
        FOREIGN KEY (`owner_no`) REFERENCES `users` (`no`)
        ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB
  DEFAULT CHARSET=utf8mb4
  COLLATE=utf8mb4_unicode_ci
  COMMENT='맛맵 그룹';


-- ---------------------------------------------------------
-- 3. 공유그룹
-- 회원들이 맛맵을 함께 공유하기 위한 그룹입니다.
-- ---------------------------------------------------------
CREATE TABLE `share_groups` (
    `no` BIGINT NOT NULL AUTO_INCREMENT COMMENT 'PK',
    `id` VARCHAR(64) NOT NULL COMMENT '고유 식별자',
    `owner_no` BIGINT NOT NULL COMMENT '그룹 소유 회원번호',
    `name` VARCHAR(100) NOT NULL COMMENT '공유그룹 이름',
    `description` TEXT DEFAULT NULL COMMENT '공유그룹 설명',
    `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '등록일자',
    `updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
        ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일자',

    PRIMARY KEY (`no`),
    UNIQUE KEY `uk_share_groups_id` (`id`),
    KEY `idx_share_groups_owner` (`owner_no`),

    CONSTRAINT `fk_share_groups_owner`
        FOREIGN KEY (`owner_no`) REFERENCES `users` (`no`)
        ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB
  DEFAULT CHARSET=utf8mb4
  COLLATE=utf8mb4_unicode_ci
  COMMENT='공유그룹';


-- ---------------------------------------------------------
-- 4. 공유그룹 회원
-- 공유그룹과 회원은 다대다 관계입니다.
-- ---------------------------------------------------------
CREATE TABLE `share_group_members` (
    `no` BIGINT NOT NULL AUTO_INCREMENT COMMENT 'PK',
    `share_group_no` BIGINT NOT NULL COMMENT '공유그룹 번호',
    `user_no` BIGINT NOT NULL COMMENT '회원번호',
    `role` ENUM('OWNER', 'MEMBER')
        NOT NULL DEFAULT 'MEMBER' COMMENT '그룹 내 역할',
    `status` ENUM('INVITED', 'ACTIVE', 'LEFT')
        NOT NULL DEFAULT 'ACTIVE' COMMENT '가입 상태',
    `joined_at` TIMESTAMP NULL DEFAULT NULL COMMENT '가입일자',
    `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '등록일자',
    `updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
        ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일자',

    PRIMARY KEY (`no`),
    UNIQUE KEY `uk_share_group_member`
        (`share_group_no`, `user_no`),
    KEY `idx_share_group_members_user` (`user_no`),
    KEY `idx_share_group_members_status` (`status`),

    CONSTRAINT `fk_share_group_members_group`
        FOREIGN KEY (`share_group_no`) REFERENCES `share_groups` (`no`)
        ON DELETE CASCADE ON UPDATE CASCADE,

    CONSTRAINT `fk_share_group_members_user`
        FOREIGN KEY (`user_no`) REFERENCES `users` (`no`)
        ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB
  DEFAULT CHARSET=utf8mb4
  COLLATE=utf8mb4_unicode_ci
  COMMENT='공유그룹 회원';


-- ---------------------------------------------------------
-- 5. 맛집
-- 맛맵에 등록하는 맛집 정보입니다.
-- ---------------------------------------------------------
CREATE TABLE `restaurants` (
    `no` BIGINT NOT NULL AUTO_INCREMENT COMMENT 'PK',
    `id` VARCHAR(64) NOT NULL COMMENT '고유 식별자',
    `map_no` BIGINT NOT NULL COMMENT '소속 맛맵 번호',
    `name` VARCHAR(200) NOT NULL COMMENT '맛집 이름',
    `nickname` VARCHAR(100) DEFAULT NULL COMMENT '맛집 별명',
    `memo` TEXT DEFAULT NULL COMMENT '개인 메모',
    `rating` DECIMAL(2,1) DEFAULT NULL COMMENT '별점 (0.0~5.0)',
    `address` VARCHAR(500) DEFAULT NULL COMMENT '주소',
    `latitude` DECIMAL(10,7) NOT NULL COMMENT '위도',
    `longitude` DECIMAL(10,7) NOT NULL COMMENT '경도',
    `visibility` ENUM('PRIVATE', 'MAP_ONLY', 'PUBLIC')
        NOT NULL DEFAULT 'MAP_ONLY' COMMENT '맛집 공개 범위',
    `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '등록일자',
    `updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
        ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일자',

    PRIMARY KEY (`no`),
    UNIQUE KEY `uk_restaurants_id` (`id`),
    KEY `idx_restaurants_map` (`map_no`),
    KEY `idx_restaurants_name` (`name`),
    KEY `idx_restaurants_rating` (`rating`),
    KEY `idx_restaurants_location` (`latitude`, `longitude`),
    KEY `idx_restaurants_visibility` (`visibility`),

    CONSTRAINT `fk_restaurants_map`
        FOREIGN KEY (`map_no`) REFERENCES `map_groups` (`no`)
        ON DELETE CASCADE ON UPDATE CASCADE,

    CONSTRAINT `chk_restaurants_rating`
        CHECK (`rating` IS NULL OR (`rating` >= 0 AND `rating` <= 5)),

    CONSTRAINT `chk_restaurants_latitude`
        CHECK (`latitude` >= -90 AND `latitude` <= 90),

    CONSTRAINT `chk_restaurants_longitude`
        CHECK (`longitude` >= -180 AND `longitude` <= 180)
) ENGINE=InnoDB
  DEFAULT CHARSET=utf8mb4
  COLLATE=utf8mb4_unicode_ci
  COMMENT='맛집';


-- ---------------------------------------------------------
-- 6. 맛맵 공유
-- 하나의 맛맵을 여러 공유그룹에 공유할 수 있습니다.
-- ---------------------------------------------------------
CREATE TABLE `map_shares` (
    `no` BIGINT NOT NULL AUTO_INCREMENT COMMENT 'PK',
    `map_no` BIGINT NOT NULL COMMENT '공유할 맛맵 번호',
    `share_group_no` BIGINT NOT NULL COMMENT '공유 대상 그룹 번호',
    `permission` ENUM('VIEW', 'EDIT')
        NOT NULL DEFAULT 'VIEW' COMMENT '공유 권한',
    `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '공유일자',
    `updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
        ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일자',

    PRIMARY KEY (`no`),
    UNIQUE KEY `uk_map_shares_map_group`
        (`map_no`, `share_group_no`),
    KEY `idx_map_shares_group` (`share_group_no`),

    CONSTRAINT `fk_map_shares_map`
        FOREIGN KEY (`map_no`) REFERENCES `map_groups` (`no`)
        ON DELETE CASCADE ON UPDATE CASCADE,

    CONSTRAINT `fk_map_shares_group`
        FOREIGN KEY (`share_group_no`) REFERENCES `share_groups` (`no`)
        ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB
  DEFAULT CHARSET=utf8mb4
  COLLATE=utf8mb4_unicode_ci
  COMMENT='맛맵 공유 권한';


-- ---------------------------------------------------------
-- 7. 맛집 미디어 연결
-- 기존 media 테이블을 재사용합니다.
-- 한 맛집에 여러 이미지 등을 연결할 수 있습니다.
-- ---------------------------------------------------------
CREATE TABLE `restaurant_media` (
    `no` BIGINT NOT NULL AUTO_INCREMENT COMMENT 'PK',
    `restaurant_no` BIGINT NOT NULL COMMENT '맛집 번호',
    `media_no` BIGINT NOT NULL COMMENT '미디어 번호',
    `seq` INT NOT NULL DEFAULT 0 COMMENT '정렬 순서',
    `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '등록일자',

    PRIMARY KEY (`no`),
    UNIQUE KEY `uk_restaurant_media`
        (`restaurant_no`, `media_no`),
    KEY `idx_restaurant_media_media` (`media_no`),

    CONSTRAINT `fk_restaurant_media_restaurant`
        FOREIGN KEY (`restaurant_no`) REFERENCES `restaurants` (`no`)
        ON DELETE CASCADE ON UPDATE CASCADE,

    CONSTRAINT `fk_restaurant_media_media`
        FOREIGN KEY (`media_no`) REFERENCES `media` (`no`)
        ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB
  DEFAULT CHARSET=utf8mb4
  COLLATE=utf8mb4_unicode_ci
  COMMENT='맛집 미디어 연결';


-- ---------------------------------------------------------
-- 8. 맛맵 즐겨찾기 (선택 기능)
-- 회원이 다른 맛맵을 즐겨찾기에 등록할 수 있습니다.
-- ---------------------------------------------------------
CREATE TABLE `map_favorites` (
    `no` BIGINT NOT NULL AUTO_INCREMENT COMMENT 'PK',
    `user_no` BIGINT NOT NULL COMMENT '회원번호',
    `map_no` BIGINT NOT NULL COMMENT '맛맵 번호',
    `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '등록일자',

    PRIMARY KEY (`no`),
    UNIQUE KEY `uk_map_favorites_user_map` (`user_no`, `map_no`),
    KEY `idx_map_favorites_map` (`map_no`),

    CONSTRAINT `fk_map_favorites_user`
        FOREIGN KEY (`user_no`) REFERENCES `users` (`no`)
        ON DELETE CASCADE ON UPDATE CASCADE,

    CONSTRAINT `fk_map_favorites_map`
        FOREIGN KEY (`map_no`) REFERENCES `map_groups` (`no`)
        ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB
  DEFAULT CHARSET=utf8mb4
  COLLATE=utf8mb4_unicode_ci
  COMMENT='맛맵 즐겨찾기';




-- 외래키 복원
SET FOREIGN_KEY_CHECKS = 1;