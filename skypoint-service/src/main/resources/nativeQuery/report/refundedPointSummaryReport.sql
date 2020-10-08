SELECT
	IFNULL(SUM(skyUser), 0) AS skyUser,
	IFNULL(SUM(skyOwner), 0) AS skyOwner
FROM
(
    SELECT
        CASE WHEN type = 'SKYUSER' THEN amount END AS skyUser,
        CASE WHEN type = 'SKYOWNER' THEN amount END AS skyOwner
    FROM
    (
        SELECT
            a.type,
            COUNT(tv.id ) AS amount
        FROM
            skypoint_db.transaction_values tv
        INNER JOIN
            skypoint_db.transactions t ON t.id = tv.transaction_id
            AND t.status = 'SUCCESS'
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
            tv.transaction_type_code = 'REFUNDED'
        GROUP BY a.type
    ) AS result
) AS result_2