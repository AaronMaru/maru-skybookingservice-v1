SELECT
	a.user_code AS userCode,
	a.saved_point AS earning,
	a.level_name AS levelName
FROM
	account a
WHERE a.`type` = 'SKYOWNER'
ORDER BY a.saved_point DESC
LIMIT 5