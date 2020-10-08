SELECT
    tv.code AS transactionCode,
    IFNULL(t.amount, 0) AS amount,
    t.created_at AS createdAt,
    t.created_by AS createdBy
FROM
    transactions t
INNER JOIN
    transaction_values tv ON tv.transaction_id  = t.id
WHERE
    t.status  = 'SUCCESS'
AND
    t.proceed_by = 'OFFLINE'
AND
    DATE(t.created_at) = curdate()
AND
    tv.transaction_type_code = 'TOP_UP'
ORDER BY t.id DESC
LIMIT 5