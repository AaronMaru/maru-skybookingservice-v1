SELECT
	tv.code as transactionCode,
	a.user_code  as userCode,
	tv.transaction_type_code as transactionTypeCode,
	tt.name AS transactionTypeName,
	CASE
		WHEN tv.transaction_type_code IN ('EARNED_HOTEL', 'EARNED_FLIGHT') THEN tv.earning_amount
		WHEN tv.transaction_type_code IN ('REDEEMED_FLIGHT', 'REDEEMED_HOTEL') THEN tv.amount * (-1)
	    WHEN tv.transaction_type_code = 'TOP_UP' THEN tv.amount + (tv.amount * tv.extra_rate)
	    ELSE tv.amount
	END AS amount,
	t.reference_code as referenceCode,
	t.remark as remark,
	t.created_at AS transactionDate
FROM
	transaction_values tv
INNER JOIN
	transactions t ON t.id = tv.transaction_id
INNER JOIN
	account a ON a.id = t.account_id
INNER JOIN
    transaction_types tt ON tt.code = tv.transaction_type_code
WHERE
    tv.transaction_type_code != 'EARNED_EXTRA'
AND
    t.status = 'SUCCESS'
AND
    DATE(t.created_at ) >= DATE(:startDate)
AND
    DATE(t.created_at ) <= DATE(:endDate)
AND
    tt.language_code = :languageCode
ORDER BY t.id DESC