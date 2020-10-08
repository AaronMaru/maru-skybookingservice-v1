SELECT
    tv.code AS transactionCode
FROM
    skypoint_db.transactions t
INNER JOIN
    skypoint_db.transaction_values tv ON tv.transaction_id  = t.id
INNER JOIN
	skypoint_db.account a ON a.id = t.account_id
WHERE
    t.status  = 'PENDING'
AND
    t.proceed_by = 'OFFLINE'
AND
    tv.transaction_type_code = 'TOP_UP'
ORDER BY t.id DESC