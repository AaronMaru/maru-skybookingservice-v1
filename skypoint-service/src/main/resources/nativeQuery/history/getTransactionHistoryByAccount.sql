SELECT
    tv.id as id,
	t.code AS transactionCode,
	CASE
		WHEN tv.transaction_type_code = 'TOP_UP' THEN 'Top-Up Skypoint'
		WHEN tv.transaction_type_code = 'EARNING_EXTRA' THEN 'Earning Skypoint from Top-Up'
		WHEN tv.transaction_type_code = 'EARNING' THEN 'Earning Skypoint'
		WHEN tv.transaction_type_code = 'WITHDRAWAL' AND t.transaction_for = 'FLIGHT' THEN 'Redeemed Flight Booking'
		WHEN tv.transaction_type_code = 'WITHDRAWAL' AND t.transaction_for = 'HOTEL' THEN 'Redeemed Hotel Booking'
		WHEN tv.transaction_type_code = 'EARNING' THEN 'Earning Skypoint from Booking'
	END AS transactionTypeName,
	CASE
		WHEN tv.transaction_type_code = 'EARNING_EXTRA' || tv.transaction_type_code = 'EARNING' THEN tv.earning_amount
		WHEN tv.transaction_type_code = 'WITHDRAWAL' THEN tv.amount * (-1)
	    ELSE tv.amount
	END AS amount,
	DATE_FORMAT(t.created_at, "%e %M %Y at %H:%m") as createdAt
FROM
	skypoint_db.transaction_values tv
INNER JOIN
	skypoint_db.transactions t ON t.id = tv.transaction_id
INNER JOIN
	skypoint_db.account a on a.id = t.account_id
WHERE
	a.user_code = :userCode
AND
    t.status = 'SUCCESS'
ORDER BY t.id DESC