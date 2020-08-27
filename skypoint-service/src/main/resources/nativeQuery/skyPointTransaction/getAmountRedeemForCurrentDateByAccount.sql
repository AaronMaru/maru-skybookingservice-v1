SELECT
    SUM(t.amount ) AS amount
FROM
    skypoint_db.skypoint_transaction st
INNER JOIN
    skypoint_db.transactions t  ON t.code  = st.transaction_code
INNER JOIN
    skypoint_db.transaction_values tv  ON tv.transaction_id  = t.id
WHERE
    st.stakeholder_user_id = :stakeholderUserId
AND
	st.stakeholder_company_id = :stakeholderCompanyId
AND
    DATE(st.created_at) = CURDATE()
AND
    tv.transaction_type_code  = 'WITHDRAWAL'
AND
	t.status = 'SUCCESS'