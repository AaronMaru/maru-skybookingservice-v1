SELECT
    tv.id as id,
	t.code AS transactionId,
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
	    ELSE tv.amount
	END AS pointAmount,
	CASE
		WHEN tv.transaction_type_code = 'EARNING_EXTRA' || tv.transaction_type_code = 'EARNING' THEN tv.earning_amount
	    ELSE tv.amount
	END AS amount,
	CASE
		WHEN tv.transaction_type_code = 'TOP_UP' THEN tv.amount
	    ELSE 0.00
	END AS totalPrice,
	tv.earning_amount AS earning,
	CASE
		WHEN tv.transaction_type_code = 'WITHDRAWAL' || tv.transaction_type_code = 'EARNING' THEN t.reference_code
	    ELSE ''
	END AS bookingId,
    concat('Successfully') AS status,
	DATE_FORMAT(t.created_at, "%e-%M-%Y") AS createdAt
FROM
	skypoint_db.transaction_values tv
INNER JOIN
	skypoint_db.transactions t ON t.id = tv.transaction_id
INNER JOIN
	skypoint_db.account a ON a.id = t.account_id
WHERE
	tv.id = :transactionValueId