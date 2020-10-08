--
-- Table structure for table `config_upgrade_levels`
--

INSERT INTO config_upgrade_levels (code, `type`, status, created_by, modified_by, verified,rate, level_name, from_value, to_value, language_code, created_at, updated_at) VALUES
('LEVEL1', 'SKYUSER', 1, '1', NULL, 1, 0.001,'Blue',0,500,'en',now(),now()),
('LEVEL2','SKYUSER',1,'1',NULL,1, 0.002,'Silver',500,1000,'en',now(),now()),
('LEVEL3','SKYUSER',1,'1',NULL,1, 0.003,'Gold',1000,1500,'en',now(),now()),
('LEVEL4','SKYUSER',1,'1',NULL,1, 0.004,'Premuim',1500,1000000,'en',now(),now()),
('LEVEL1','SKYOWNER',1,'1',NULL,1, 0.001,'Blue',0,500,'en',now(),now()),
('LEVEL2','SKYOWNER',1,'1',NULL,1, 0.002,'Silver',500,1000,'en',now(),now()),
('LEVEL3','SKYOWNER',1,'1',NULL,1, 0.003,'Gold',1000,1500,'en',now(),now()),
('LEVEL4','SKYOWNER',1,'1',NULL,1, 0.004,'Premuim',1500,1000000,'en',now(),now()),
('LEVEL1', 'SKYUSER', 1, '1', NULL, 1, 0.001, '蓝色', 0, 500, 'zh', now(), now()),
('LEVEL2', 'SKYUSER', 1, '1', NULL, 1, 0.002, '银', 500, 1000, 'zh', now(), now()),
('LEVEL3', 'SKYUSER', 1, '1', NULL, 1, 0.003, '金', 1000, 1500, 'zh', now(), now()),
('LEVEL4', 'SKYUSER', 1, '1', NULL, 1, 0.004, '保费', 1500, 1000000, 'zh', now(), now()),
('LEVEL1', 'SKYOWNER', 1, '1', NULL, 1, 0.001, '蓝色', 0, 500, 'zh', now(), now()),
('LEVEL2', 'SKYOWNER', 1, '1', NULL, 1, 0.002, '银', 500, 1000, 'zh', now(), now()),
('LEVEL3', 'SKYOWNER', 1, '1', NULL, 1, 0.003, '金', 1000, 1500, 'zh', now(), now()),
('LEVEL4', 'SKYOWNER', 1, '1', NULL, 1, 0.004, '保费', 1500, 1000000, 'zh', now(), now());

--
-- Table structure for table `config_top_up`
--
INSERT INTO config_top_up (`type`,topup_key,value,status,created_by,modified_by,created_at,updated_at) VALUES
('SKYUSER', 'EXTRA-RATE', 0.01, 1, '1', NULL, now(), now()),
('SKYOWNER', 'LIMIT-AMOUNT', 500.00, 1, '1', NULL,now(), now()),
('SKYOWNER', 'EXTRA-RATE', 0.01, 1, '1', NULL, now(), now()),
('SKYUSER', 'LIMIT-AMOUNT', 500.00, 1, '1', NULL, now(), now());

--
-- Table structure for table `transaction_types`
--
INSERT INTO transaction_types (code,name,language_code,created_at,updated_at) VALUES
('TOP_UP','Top-Up Skypoint','en',now(),now()),
('REFUNDED_FLIGHT','Refund Flight Booking','en',now(),now()),
('REFUNDED_HOTEL','Refund Hotel Booking','en',now(),now()),
('EARNED_HOTEL','Earning Skypoint from Hotel','en',now(),now()),
('EARNED_FLIGHT','Earning Skypoint from Fight','en',now(),now()),
('EARNED_EXTRA','Earning Extra Skypoint','en',now(),now()),
('REDEEMED_HOTEL','Redeemed Hotel Booking','en',now(),now()),
('REDEEMED_FLIGHT','Redeemed Flight Booking','en',now(),now()),
('TOP_UP', '充值Skypoint', 'zh', now(), now()),
('REFUNDED_FLIGHT', '退款机票预订', 'zh', now(), now()),
('REFUNDED_HOTEL', '退款酒店预订', 'zh', now(), now()),
('EARNED_HOTEL', '从酒店赚取Skypoint', 'zh', now(), now()),
('EARNED_FLIGHT', '从战斗中赚取Skypoint', 'zh', now(), now()),
('EARNED_EXTRA', '赚取额外的Skypoint', 'zh', now(), now()),
('REDEEMED_HOTEL', '兑换酒店预订', 'zh', now(), now()),
('REDEEMED_FLIGHT', '兑换的机票预订', 'zh', now(), now());








