SELECT
    tv.code AS transactionCode,
    a.user_code AS userCode,
    a.type AS userType,
    IFNULL(t.amount, 0) AS amount,
    t.created_at AS createdAt,
    t.created_by AS createdBy
FROM
    transactions t
INNER JOIN
    transaction_values tv ON tv.transaction_id  = t.id
INNER JOIN
    account a ON a.id = t.account_id
WHERE
    t.status  = 'SUCCESS'
AND
    DATE(t.created_at) = curdate()
AND
    tv.transaction_type_code = 'TOP_UP'
ORDER BY t.id DESC
LIMIT 5