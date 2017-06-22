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
  `school_name` VARCHAR(100) NOT NULL COMMENT '录取院校名称',
  `major` VARCHAR(100) NOT NULL COMMENT '录取专业',
  `score` SMALLINT(3) NOT NULL COMMENT '分数',
  `ranking` INT(11) NOT NULL COMMENT '当前分数排名',
  `cast_archive_unit_name` VARCHAR(256) NOT NULL COMMENT '投档单位名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='历年院校录取情况，所有招录信息汇总';

ALTER TABLE `t_lnyxlqqk` MODIFY `major` VARCHAR(100) NOT NULL COMMENT '录取专业';
ALTER TABLE `t_lnyxlqqk` MODIFY `school_name` VARCHAR(100) NOT NULL COMMENT '录取院校名称';
ALTER TABLE `t_lnyxlqqk` ADD INDEX `idx_lnyxlqqk_year_school_major`(`year`, `school_name`, `major`);

-- 历年分数段信息统计表
CREATE TABLE `t_lnfsdxq` (
  `year` smallint(4) NOT NULL COMMENT '年份',
  `total_grade` smallint(3) NOT NULL COMMENT '总分',
  `student_count` smallint(4) NOT NULL COMMENT '学生统计',
  `category` varchar(10) COLLATE utf8_unicode_ci NOT NULL COMMENT '科别-文科理科',
  `ranking` int(8) NOT NULL COMMENT '排名',
  `rural_area_count` smallint(4) NOT NULL COMMENT '农区统计',
  `pasturing_area_count` smallint(4) DEFAULT NULL COMMENT '牧区统计',
  `pre_reguar_count` smallint(4) NOT NULL COMMENT '预科统计',
  `rural_ten_add` smallint(4) NOT NULL COMMENT '农区+10分统计',
  `rural_twen_add` smallint(4) NOT NULL COMMENT '农区+20统计',
  `han_ten_add` smallint(4) NOT NULL COMMENT '汉族+10分统计',
  `han_twen_add` smallint(4) NOT NULL COMMENT '汉族+20统计',
  `pasturing_ten_add` smallint(4) NOT NULL COMMENT '牧区+10分统计',
  `pasturing_twen_add` smallint(4) NOT NULL COMMENT '牧区+20统计'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='历年分数段信息统计表';

-- 历年省控分数线
CREATE TABLE `t_lnskfxs` (
  `year` smallint(4) NOT NULL COMMENT '年份',
  `batch` smallint(3) NOT NULL COMMENT '批次，只分本科一批，二批，三批，专科',
  `category` varchar(10) COLLATE utf8_unicode_ci NOT NULL COMMENT '科类',
  `grade` smallint(4) NOT NULL COMMENT '分数'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='历年省控线表';

-- 历年院校录取统计表
CREATE TABLE `t_lnyxlqtj` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `year` smallint(4) NOT NULL COMMENT '年份',
  `college_code` varchar(20) COLLATE utf8_unicode_ci NOT NULL COMMENT '院校编码，统一代码',
  `college_name` varchar(100) COLLATE utf8_unicode_ci NOT NULL COMMENT '院校名称',
  `batch_code` char(1) COLLATE utf8_unicode_ci NOT NULL COMMENT '批次编号',
  `category` char(10) COLLATE utf8_unicode_ci NOT NULL COMMENT '科类编号',
  `enroll_count` smallint(4) NOT NULL COMMENT '录取人数统计',
  `high_grade` float(9,3) NOT NULL COMMENT '最高分',
  `low_grade` float(9,3) NOT NULL COMMENT '最低分',
  `avg_grade` float(6,2) DEFAULT NULL COMMENT '平均分，需计算出来',
  `high_ranking` smallint(4) NOT NULL COMMENT '最高名次，数字越小名次越高',
  `low_ranking` smallint(4) NOT NULL COMMENT '最低名次',
  `avg_ranking` float(9,3) DEFAULT NULL COMMENT '平均名次需计算出来',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='历年院校录取统计，针对某个院校';

-- 历年专业录取统计表
CREATE TABLE `t_lnzylqtj` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `year` smallint(4) NOT NULL COMMENT '年份',
  `college_enroll_id` int(11) COMMENT '对应历年院校录取表中的id，外键',
  `college_name` varchar(200) COLLATE utf8_unicode_ci NOT NULL COMMENT '院校名称',
  `major_name` varchar(200) COLLATE utf8_unicode_ci NOT NULL COMMENT '专业名称，专业只按名称模糊筛选',
  `batch_code` char(1) COLLATE utf8_unicode_ci NOT NULL COMMENT '批次编号',
  `category` char(10) COLLATE utf8_unicode_ci NOT NULL COMMENT '科类编号',
  `enroll_count` smallint(4) NOT NULL COMMENT '录取人数统计',
  `high_grade` smallint(4) NOT NULL COMMENT '最高分',
  `low_grade` smallint(4) NOT NULL COMMENT '最低分',
  `avg_grade` float(6,2) DEFAULT NULL COMMENT '平均分，需计算出来',
  `high_ranking` smallint(4) NOT NULL COMMENT '最高名次，数字越小名次越高',
  `low_ranking` smallint(4) NOT NULL COMMENT '最低名次',
  `avg_ranking` float(9,3) DEFAULT NULL COMMENT '平均名次需计算出来',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='历年专业录取统计，针对某个院校';

-- 院校基础信息表
DROP TABLE IF EXISTS `t_college_info`;
CREATE TABLE `t_college_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `code` varchar(20) COLLATE utf8_unicode_ci NOT NULL COMMENT '院校编码，统一代码',
  `name` varchar(100) COLLATE utf8_unicode_ci NOT NULL COMMENT '院校名称',
  `email` varchar(100) COLLATE utf8_unicode_ci NOT NULL COMMENT '院校电子邮箱',
  `area` varchar(10) COLLATE utf8_unicode_ci COMMENT '区域编码，建议用01-30代码各省',
  `type` varchar(8)  COMMENT '院校类型，直接用211，958表示, 逗号分隔',
  `ranking` smallint(4) COMMENT '学校排名',
  `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` TIMESTAMP NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `unq_college_code`(`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='院校基础信息，可能扩展';

-- 新增索引字段
ALTER TABLE t_lnyxlqtj ADD INDEX yx_low_rank_index (`low_ranking`);
ALTER TABLE t_lnzylqtj ADD INDEX zy_low_rank_index (`low_ranking`);
ALTER TABLE t_lnyxlqtj ADD INDEX yx_year_index (`year`);
ALTER TABLE t_lnzylqtj ADD INDEX zy_year_index (`year`);

-- 省控线直接初始化进去
INSERT INTO `t_lnskfxs` (`year`, `batch`, `category`, `grade`)
VALUES
	(2016,1,'1',457),
	(2016,2,'1',415),
	(2016,3,'1',379),
	(2016,4,'1',200),
	(2016,1,'5',416),
	(2016,2,'5',380),
	(2016,3,'5',353),
	(2016,4,'5',200),
	(2015,1,'1',466),
	(2015,2,'1',420),
	(2015,3,'1',382),
	(2015,4,'1',200),
	(2015,1,'5',400),
	(2015,2,'5',363),
	(2015,3,'5',339),
	(2015,4,'5',200),
	(2014,1,'1',473),
	(2014,2,'1',426),
	(2014,3,'1',386),
	(2014,4,'1',200),
	(2014,1,'5',406),
	(2014,2,'5',362),
	(2014,3,'5',330),
	(2014,4,'5',200),
	(2013,1,'1',435),
	(2013,2,'1',382),
	(2013,3,'1',344),
	(2013,4,'1',200),
	(2013,1,'5',383),
	(2013,2,'5',340),
	(2013,3,'5',310),
	(2013,4,'5',200),
	(2012,1,'1',433),
	(2012,2,'1',373),
	(2012,3,'1',320),
	(2012,4,'1',316),
	(2012,5,'1',220),
	(2012,1,'5',401),
	(2012,2,'5',355),
	(2012,3,'5',318),
	(2012,4,'5',315),
	(2012,5,'5',228),
	(2011,1,'1',430),
	(2011,2,'1',380),
	(2011,3,'1',342),
	(2011,4,'1',340),
	(2011,5,'1',240),
	(2011,1,'5',380),
	(2011,2,'5',331),
	(2011,3,'5',300),
	(2011,4,'5',295),
	(2011,5,'5',226),
	(2010,1,'1',430),
	(2010,2,'1',386),
	(2010,3,'1',350),
	(2010,4,'1',346),
	(2010,5,'1',295),
	(2010,1,'5',405),
	(2010,2,'5',363),
	(2010,3,'5',328),
	(2010,4,'5',323),
	(2010,5,'5',220);

