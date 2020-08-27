SELECT
    t.code AS transactionCode,
    t.amount,
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
    tv.transaction_type_code = 'TOP_UP'
AND
    t.code LIKE '%' :valueSearch '%'
ORDER BY t.id DESC