--
-- Table structure for table `config_upgrade_levels`
--

INSERT INTO config_upgrade_levels (code,`type`,status,created_by,modified_by,verified,rate,level_name,from_value,to_value,created_at,updated_at) VALUES
('E001','SKYUSER',1,'1',NULL,1,0.01,'Blue',0,500,now(),now()),
('E002','SKYUSER',1,'1',NULL,1,0.02,'Silver',500,1000,now(),now()),
('E003','SKYUSER',1,'1',NULL,1,0.03,'Gold',1000,1500,now(),now()),
('E004','SKYUSER',1,'1',NULL,1,0.04,'Premuim',1500,1000000,now(),now()),
('E005','SKYOWNER',1,'1',NULL,1,0.01,'Blue',0,500,now(),now()),
('E006','SKYOWNER',1,'1',NULL,1,0.02,'Silver',500,1000,now(),now()),
('E007','SKYOWNER',1,'1',NULL,1,0.03,'Gold',1000,1500,now(),now()),
('E008','SKYOWNER',1,'1',NULL,1,0.04,'Premuim',1500,1000000,now(),now());


--
-- Table structure for table `config_top_up`
--
INSERT INTO config_top_up (`type`,topup_key,value,status,created_by,modified_by,created_at,updated_at) VALUES
('SKYUSER', 'EXTRA-RATE', 0.01, 1, '1', NULL, now(), now()),
('SKYOWNER', 'LIMIT-AMOUNT', 500.00, 1, '1', NULL,now(), now()),
('SKYOWNER', 'EXTRA-RATE', 0.01, 1, '1', NULL, now(), now()),
('SKYUSER', 'LIMIT-AMOUNT', 500.00, 1, '1', NULL, now(), now());