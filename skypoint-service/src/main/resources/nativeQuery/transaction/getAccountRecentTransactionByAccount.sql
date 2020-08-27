SELECT
	CASE
		WHEN tv.transaction_type_code = 'TOP_UP' THEN 'Top-Up Skypoint'
		WHEN tv.transaction_type_code = 'EARNING_EXTRA' THEN 'Earning Skypoint from Top-Up'
		WHEN tv.transaction_type_code = 'EARNING' THEN 'Earning Skypoint'
		WHEN tv.transaction_type_code = 'WITHDRAWAL' AND t.transaction_for = 'FLIGHT' THEN 'Redeemed Flight Booking'
		WHEN tv.transaction_type_code = 'WITHDRAWAL' AND t.transaction_for = 'HOTEL' THEN 'Redeemed Hotel Booking'
		WHEN tv.transaction_type_code = 'EARNING' THEN 'Earning Skypoint from Booking'
	END AS transactionTypeName,
	CASE
		WHEN tv.transaction_type_code = 'EARNING' || tv.transaction_type_code = 'EARNING_EXTRA' THEN tv.earning_amount
		ELSE tv.amount
	END AS amount,
	tv.created_at AS createdAt
FROM
	skypoint_db.transaction_values tv
INNER JOIN
	skypoint_db.transactions t ON t.id = tv.transaction_id
INNER JOIN
	skypoint_db.account a ON a.id = t.account_id
WHERE
	a.user_code = :userCode
ORDER BY tv.id DESC
LIMIT 5
