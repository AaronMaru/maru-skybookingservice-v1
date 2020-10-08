SELECT
    IFNULL(SUM(t.amount ), 0) AS amount
FROM
    skypoint_transaction st
INNER JOIN
    transactions t  ON t.id  = st.transaction_id
INNER JOIN
    transaction_values tv  ON tv.transaction_id  = t.id
WHERE
    st.stakeholder_user_id = :stakeholderUserId
AND
	st.stakeholder_company_id = :stakeholderCompanyId
AND
    DATE(st.created_at) = CURDATE()
AND
    tv.transaction_type_code  = 'REDEEMED_FLIGHT' || tv.transaction_type_code  = 'REDEEMED_HOTEL'
AND
	t.status = 'SUCCESS'