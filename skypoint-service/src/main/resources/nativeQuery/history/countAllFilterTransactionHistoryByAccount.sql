SELECT
    tv.amount AS total
FROM
	transaction_values tv
INNER JOIN
	transactions t ON t.id = tv.transaction_id
INNER JOIN
	account a ON a.id = t.account_id
WHERE
	a.user_code = :userCode
AND
    t.status = 'SUCCESS'
AND
    CASE
        WHEN :transactionTypeCode != 'all' THEN tv.transaction_type_code = :transactionTypeCode
	    ELSE tv.transaction_type_code IN (SELECT code FROM transaction_types WHERE language_code = 'en' AND code != 'EARNED_EXTRA')
	END
AND
	DATE(t.created_at) >= DATE(:startDate)
AND
	DATE(t.created_at) <= DATE(:endDate)