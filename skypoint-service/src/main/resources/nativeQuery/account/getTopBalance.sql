SELECT
	a.user_code AS userCode,
	a.balance AS balance,
	a.level_name AS levelName
FROM
	account a
WHERE a.`type` = 'SKYOWNER'
ORDER BY a.balance DESC
LIMIT 5