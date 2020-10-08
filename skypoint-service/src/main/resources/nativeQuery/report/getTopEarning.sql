SELECT
	a.user_code AS userCode,
	a.saved_point AS savedPoint,
	a.level_code AS levelCode,
	cul.level_name AS levelName
FROM
	account a
INNER JOIN
	config_upgrade_levels cul ON cul.code = a.level_code
	AND cul.language_code = 'en'
	AND a.`type` = cul.`type`
ORDER BY a.saved_point DESC
LIMIT 5