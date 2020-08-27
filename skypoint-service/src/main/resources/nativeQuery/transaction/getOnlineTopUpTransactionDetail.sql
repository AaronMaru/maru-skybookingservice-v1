SELECT
	t.code  AS transactionCode,
	DATE_FORMAT(t.created_at, "%e-%M-%Y") as transactionDate,
	tv.earning_amount AS earningExtra,
	(t.amount + tv.earning_amount) AS pointAmount,
	t.paid_amount  AS paidPrice,
	t.amount AS totalPrice,
	CASE
    	WHEN t.status = 'SUCCESS' THEN 'Successfully'
    	WHEN t.status = 'FAILED' THEN 'Failed'
    END AS status
FROM
	skypoint_db.transactions t
INNER JOIN
	skypoint_db.transaction_values tv ON tv.transaction_id  = t.id
WHERE
	t.code = :transactionCode
AND
	tv.transaction_type_code = 'EARNING_EXTRA'