SELECT
	tv.code AS transactionCode,
	st.stakeholder_user_id AS stakeholderUserId,
	tt.name AS transactionTypeName,
	tv.amount AS totalPoint,
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
    AND a.user_code = :userCode
INNER JOIN
	skypoint_transaction st ON st.transaction_id = t.id
	AND st.stakeholder_user_id = :stakeholderUserId
WHERE tv.transaction_type_code != 'EARNED_EXTRA'
ORDER BY t.id DESC