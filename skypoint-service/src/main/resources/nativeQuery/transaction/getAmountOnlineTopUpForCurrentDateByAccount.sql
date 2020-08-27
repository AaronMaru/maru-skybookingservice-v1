SELECT
	SUM(t.amount) AS amount,
	t.account_id AS accountId
FROM
	transactions t
INNER JOIN
	transaction_values tv ON tv.transaction_id = t.id
WHERE
	t.status = 'SUCCESS'
AND
	t.proceed_by = 'ONLINE'
AND
	DATE(t.created_at) = curdate()
AND
	t.account_id = :accountId
AND
	tv.transaction_type_code = 'TOP_UP'
