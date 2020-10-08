SELECT
    tv.code AS transactionCode,
	a.user_code AS userCode,
	a.type,
	tt.name AS transactionTypeName,
	tv.amount AS amount,
	t.created_at AS createdAt
FROM
    transaction_values tv
INNER JOIN
    transactions t ON t.id = tv.transaction_id
    AND t.status = 'SUCCESS'
INNER JOIN
	transaction_types tt ON tt.code = tv.transaction_type_code
	AND tt.language_code = :languageCode
INNER JOIN
    account a ON a.id = t.account_id
    AND
        CASE
            WHEN :userCode != 'all' THEN a.user_code = :userCode
            ELSE a.user_code IN (SELECT user_code FROM account WHERE status = 'active')
        END
WHERE
    DATE(t.created_at) >= DATE(:startDate)
AND
    DATE(t.created_at) <= DATE(:endDate)
AND
    tv.transaction_type_code IN ('REDEEMED_FLIGHT' , 'REDEEMED_HOTEL')