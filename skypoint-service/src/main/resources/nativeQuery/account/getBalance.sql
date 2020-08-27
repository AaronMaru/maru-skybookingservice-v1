SELECT
	user_code AS userCode,
	`type`,
	topup ,
	withdrawal ,
	earning ,
	earning_extra AS earningExtra,
	refund ,
	balance ,
	level_name AS levelName,
	status
FROM
	account
WHERE
    `type` = :userType
AND
    user_code = :userCode
