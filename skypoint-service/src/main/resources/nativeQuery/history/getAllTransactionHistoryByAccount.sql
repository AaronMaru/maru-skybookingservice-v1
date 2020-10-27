SELECT
    tv.id AS id,
	tv.code AS transactionCode,
	tv.transaction_type_code AS transactionTypeCode,
	tt.name AS transactionTypeName,
	CASE
		WHEN tv.transaction_type_code IN ('EARNED_HOTEL', 'EARNED_FLIGHT') THEN tv.earning_amount
		WHEN tv.transaction_type_code IN ('REDEEMED_FLIGHT', 'REDEEMED_HOTEL') THEN tv.amount * (-1)
	    WHEN tv.transaction_type_code = 'TOP_UP' THEN (tv.amount + (tv.amount * tv.extra_rate))
	    ELSE tv.amount
	END AS totalPoint,
	t.created_at as createdAt
FROM
	transaction_values tv
INNER JOIN
	transactions t ON t.id = tv.transaction_id
INNER JOIN
	account a ON a.id = t.account_id
INNER JOIN
    transaction_types tt ON tt.code = tv.transaction_type_code
WHERE
	a.user_code = :userCode
AND
    t.status = 'SUCCESS'
AND
    tv.transaction_type_code != 'EARNED_EXTRA'
AND
    tt.language_code = :languageCode
AND
	DATE(t.created_at) >= DATE(:startDate)
AND
	DATE(t.created_at) <= DATE(:endDate)
ORDER BY t.id DESC