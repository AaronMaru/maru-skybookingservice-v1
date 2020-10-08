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
            SUM(tv.earning_amount ) AS amount
        FROM
            transaction_values tv
        INNER JOIN
            transactions t ON t.id = tv.transaction_id
            and t.status = 'SUCCESS'
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
            tv.transaction_type_code IN ('EARNED_EXTRA' , 'EARNED_FLIGHT', 'EARNED_HOTEL')
        GROUP BY a.type
    ) AS result
) AS result_2