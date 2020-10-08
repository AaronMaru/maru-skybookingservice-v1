SELECT
	a.user_code AS userCode,
	IFNULL(a.balance, 0) AS balance,
	a.level_code AS levelCode,
	cul.level_name AS levelName

FROM
	skypoint_db.account a
INNER JOIN
	skypoint_db.config_upgrade_levels cul ON cul.code = a.level_code
	AND cul.language_code = 'en'
	AND a.`type` = cul.`type`
ORDER BY a.balance DESC
LIMIT 5