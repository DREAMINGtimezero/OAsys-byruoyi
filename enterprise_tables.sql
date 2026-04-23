-- 创建企业主表
CREATE TABLE `t_enterprise` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `delete_flag` int(1) DEFAULT 0 COMMENT '删除标记：0=未删除，1=已删除',
  `enterprise_name` varchar(255) DEFAULT NULL COMMENT '企业名称',
  `region` varchar(100) DEFAULT NULL COMMENT '地区',
  `establishment_date` varchar(50) DEFAULT NULL COMMENT '成立时间',
  `registered_capital` varchar(100) DEFAULT NULL COMMENT '注册资本',
  `paid_in_capital` varchar(100) DEFAULT NULL COMMENT '实缴资本',
  `employee_count` varchar(50) DEFAULT NULL COMMENT '人数',
  `product` varchar(255) DEFAULT NULL COMMENT '产品',
  `website` varchar(255) DEFAULT NULL COMMENT '网站地址',
  `contact_phone` varchar(20) DEFAULT NULL COMMENT '联系电话',
  `data_source` int(1) DEFAULT 2 COMMENT '来源：1=公司网页，2=系统新增',
  `status` varchar(50) DEFAULT NULL COMMENT '状态',
  `detail` varchar(1000) DEFAULT NULL COMMENT '详情',
  `dev_status` int(1) DEFAULT 1 COMMENT '开发状态：1=未开发，2=开发中，3=失败，4=成功',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='企业信息表';

-- 创建企业沟通子表
CREATE TABLE `t_enterprise_sub` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `delete_flag` int(1) DEFAULT 0 COMMENT '删除标记：0=未删除，1=已删除',
  `enterprise_id` bigint(20) DEFAULT NULL COMMENT '企业id',
  `way_of_vision` varchar(50) DEFAULT NULL COMMENT '访问方式',
  `progress` varchar(500) DEFAULT NULL COMMENT '进展情况',
  `communication_time` date DEFAULT NULL COMMENT '沟通时间',
  `note` varchar(1000) DEFAULT NULL COMMENT '备注信息',
  PRIMARY KEY (`id`),
  KEY `idx_enterprise_id` (`enterprise_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='企业沟通流程表';