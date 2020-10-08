SELECT
    tv.id AS id,
	tv.code AS transactionCode,
	tt.code AS transactionTypeCode,
	tt.name as transactionTypeName,
	CASE
		WHEN tv.transaction_type_code IN ('REDEEMED_FLIGHT', 'REDEEMED_HOTEL', 'EARNED_FLIGHT', 'EARNED_HOTEL') THEN t.reference_code
	    ELSE ''
	END AS bookingId,
	CASE
		WHEN tv.transaction_type_code = 'TOP_UP' THEN (tv.amount)
	    ELSE 0.00
	END AS topUpPoint,
	CASE
		WHEN tv.transaction_type_code IN ('EARNED_HOTEL', 'EARNED_FLIGHT') THEN tv.earning_amount
		WHEN tv.transaction_type_code = 'TOP_UP' THEN (tv.amount * tv.extra_rate)
		ELSE 0.00
	END as earnedPoint,
	CASE
		WHEN tv.transaction_type_code = 'TOP_UP' THEN (tv.amount + (tv.amount * tv.extra_rate))
		WHEN tv.transaction_type_code IN ('EARNED_HOTEL', 'EARNED_FLIGHT') THEN tv.earning_amount
	    ELSE tv.amount
	END AS totalPoint,
	CASE
		WHEN tv.transaction_type_code IN ('REDEEMED_FLIGHT', 'REDEEMED_HOTEL') THEN tv.amount
		ELSE 0.00
	END as redeemedPoint,
	t.amount AS totalPrice,
	t.paid_amount AS paidPrice,
	DATE_FORMAT(t.created_at, "%e-%M-%Y") AS createdAt
FROM
	transaction_values tv
INNER JOIN
	transactions t ON t.id = tv.transaction_id
INNER JOIN
	transaction_types tt ON tt.code = tv.transaction_type_code AND tt.language_code = :languageCode
INNER JOIN
	account a ON a.id = t.account_id
WHERE
	tv.code = :transactionCode