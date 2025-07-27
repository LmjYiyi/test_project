-- 先删除旧表(如果存在)
DROP TABLE IF EXISTS credit_card_account;

-- 创建新表
CREATE TABLE credit_card_account (
                                     card_no VARCHAR(19) PRIMARY KEY,
                                     person_name VARCHAR(60),
                                     usable_amount DECIMAL(10, 2),
                                     over_amount DECIMAL(10, 2),
                                     card_pin VARCHAR(6),
                                     card_expired_date VARCHAR(6),
                                     cer_type VARCHAR(3),
                                     cer_no VARCHAR(20),
                                     trx_zoneno INT,
                                     card_status VARCHAR(1) DEFAULT '1',
                                     daily_limit DECIMAL(10, 2) DEFAULT 10000.00
);

-- 插入测试数据
INSERT INTO credit_card_account (card_no, person_name, usable_amount, over_amount, card_pin, card_expired_date, cer_type, cer_no, trx_zoneno, card_status) VALUES
-- 基础测试数据
('6226220000000000', 'John Zhang', 1000.00, 0.00, '123456', '202701', '0', '441323200001010098', 200, '1'),
('6226220000000001', 'Mike Li', 500.00, 0.00, '654321', '202802', '1', '12345678901234567890', 300, '1'),
('6226220000000002', 'David Wang', 10.00, 0.00, '111111', '202603', '0', '55555555555555555555', 200, '1'),

-- 特殊场景测试数据
('6226220000000003', 'Zhao Liu', 5.00, 0.00, '222222', '202412', '3', 'HK1234567890', 400, '1'), -- 不透支场景
('6226220000000004', 'Qian Qi', 2000.00, 0.00, '333333', '202512', '0', '441323199001010099', 500, '1'), -- 异地大额交易
('6226220000000005', 'Sun Ba', 100.00, 0.00, '444444', '202306', '1', 'P987654321098765432', 200, '1'), -- 外币交易
('6226220000000006', 'Zhou Jiu', 50.00, 0.00, '555555', '202309', '0', '441323198502020088', 200, '0'), -- 已销户卡
('6226220000000007', 'Wu Shi', 1000.00, 0.00, '666666', '202501', '3', 'TW9876543210', 600, '1'), -- 台湾地区卡
('6226220000000008', 'Zheng Shiyi', 10.00, 0.00, '777777', '202407', '1', 'P123456789012345678', 200, '1'), -- 低余额卡
('6226220000000009', 'Wang Shier', 5000.00, 0.00, '888888', '202612', '0', '441323197803030077', 700, '1'), -- 高余额卡
('6226220000000010', 'Feng Shisan', 100.00, 0.00, '999999', '202303', '0', '441323199504040066', 200, '1'); -- 即将过期卡



-- auto-generated definition
create table teller_info
(
    id          bigint auto_increment
        primary key,
    ssic_id     varchar(50)  not null comment '认证号',
    ssic_type   varchar(10)  not null comment '认证类型：1-统一认证号，2-身份证，3-香港身份证',
    username    varchar(100) null comment '用户名',
    email       varchar(100) null comment '邮箱',
    phone       varchar(20)  null comment '手机号',
    status      varchar(10)  null comment '状态',
    create_time datetime     null comment '创建时间',
    update_time datetime     null comment '更新时间',
    field1      varchar(255) null comment '字段1',
    sub_field1  int          null comment '子字段1',
    sub_field2  tinyint(1)   null comment '子字段2',
    field3      varchar(255) null comment '字段3',
    constraint uk_ssic_id_type
        unique (ssic_id, ssic_type)
);

INSERT INTO teller_info (id, ssic_id, ssic_type, username, email, phone, status, create_time, update_time, field1, sub_field1, sub_field2, field3) 
VALUES 
(1, '123456789', '1', 'zhangsan', 'zhangsan@example.com', '13800138001', '1', '2025-07-15 11:03:46', '2025-07-15 11:03:46', '统一认证用户-123456789', 101, 1, '服务站点: 总行'),

(2, '987654321', '1', 'lisi', 'lisi@example.com', '13800138002', '1', '2025-07-15 11:03:46', '2025-07-15 11:03:46', '统一认证用户-987654321', 102, 1, '服务站点: 分行'),

(4, '110101199002022345', '2', 'zhaoliu', 'zhaoliu@example.com', '13800138004', '1', '2025-07-15 11:03:46', '2025-07-15 11:03:46', '身份证用户-110101199002022345', 202, 0, '服务站点: 网点'),

(5, 'A123456(7)', '3', 'chenqi', 'chenqi@example.com', '13800138005', '1', '2025-07-15 11:03:46', '2025-07-15 11:03:46', '香港身份证用户-A123456(7)', 301, 1, '服务站点: 香港分行'),

(6, 'B234567(8)', '3', 'liuba', 'liuba@example.com', '13800138006', '1', '2025-07-15 11:03:46', '2025-07-15 11:03:46', '香港身份证用户-B234567(8)', 302, 1, '服务站点: 香港支行'),

(13, '110101199001011234', '2', 'wangwu', 'wangwu@example.com', '13800138003', '1', '2025-07-24 09:33:29', '2025-07-24 10:33:34', '身份证用户-110101199001011234', 0, 0, '服务站点: 支行');


