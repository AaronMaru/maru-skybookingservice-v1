SELECT
	tv.code  AS transactionCode,
	t.created_at AS transactionDate,
	tv.amount AS topUpPoint,
	tv.extra_rate * tv.amount AS earnedPoint,
	(t.amount + (tv.extra_rate * tv.amount)) AS totalPoint,
	t.amount AS totalPrice,
	t.paid_amount  AS paidPrice,
	t.status AS status
FROM
	transaction_values tv
INNER JOIN
	transactions t ON t.id = tv.transaction_id
WHERE
	tv.code = :transactionCode
AND
	t.proceed_by = 'ONLINE'