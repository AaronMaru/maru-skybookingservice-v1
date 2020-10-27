SELECT
    tv.code AS transactionCode,
    IFNULL(t.amount, 0) AS amount,
    t.created_at AS createdAt,
    t.created_by AS createdBy
FROM
    transaction_values tv
INNER JOIN
    transactions t ON t.id = tv.transaction_id
WHERE
    t.proceed_by = 'OFFLINE'
AND
    tv.transaction_type_code = 'TOP_UP'
AND
    tv.code LIKE '%' :valueSearch '%'
ORDER BY t.id DESC