SELECT
	tv.code AS transactionCode,
	st.stakeholder_user_id AS stakeholderUserId,
	tt.name AS transactionTypeName,
	CASE
		WHEN tv.transaction_type_code IN ('EARNED_HOTEL', 'EARNED_FLIGHT') THEN tv.earning_amount
		WHEN tv.transaction_type_code IN ('REDEEMED_FLIGHT', 'REDEEMED_HOTEL') THEN tv.amount * (-1)
		WHEN tv.transaction_type_code = 'TOP_UP' THEN (tv.amount + (tv.amount * tv.extra_rate))
	    ELSE tv.amount
	END AS totalPoint,
	t.created_at AS createdAt
FROM
    transaction_values tv
INNER JOIN
    transactions t ON t.id = tv.transaction_id
    AND t.status = 'SUCCESS'
INNER JOIN
	transaction_types tt ON tt.code = tv.transaction_type_code
	AND tt.language_code = :languageCode
INNER JOIN
    account a ON a.id = t.account_id
    AND a.user_code = :userCode
INNER JOIN
	skypoint_transaction st ON st.transaction_id = t.id
	AND st.stakeholder_user_id = :stakeholderUserId
WHERE tv.transaction_type_code != 'EARNED_EXTRA'
ORDER BY t.id DESC
LIMIT :limit
OFFSET :offset