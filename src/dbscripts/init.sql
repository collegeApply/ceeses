-- 历年院校录取情况表
CREATE TABLE `t_lnyxlqqk`(
  `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `year` SMALLINT(4) NOT NULL COMMENT '年份',
  `volunteer_no` TINYINT(2) NOT NULL COMMENT '志愿号',
  `batch_code` CHAR(1) NOT NULL COMMENT '批次代码（提前批，本科一批等）',
  `student_gender` ENUM('男', '女', '未知') NOT NULL COMMENT '性别',
  `category_name` VARCHAR(64) NOT NULL COMMENT '科别名称（文史，理工等）',
  `area_name` VARCHAR(32) NOT NULL COMMENT '地区名称',
  `area_code` CHAR(2) NULL COMMENT '地区编码',
  `school_name` VARCHAR(512) NOT NULL COMMENT '录取院校名称',
  `major` VARCHAR(512) NOT NULL COMMENT '录取专业',
  `score` SMALLINT(3) NOT NULL COMMENT '分数',
  `ranking` INT(11) NOT NULL COMMENT '当前分数排名',
  `cast_archive_unit_name` VARCHAR(256) NOT NULL COMMENT '投档单位名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='历年院校录取情况，所有招录信息汇总';
