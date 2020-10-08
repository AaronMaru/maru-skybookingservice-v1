SELECT
	IFNULL(SUM(skyUser), 0) AS skyUser,
	IFNULL(SUM(skyOwnerOnline), 0) AS skyOwnerOnline,
	IFNULL(SUM(skyOwnerOffline), 0) AS skyOwnerOffline,
	IFNULL(SUM(earnedSkyUser), 0) AS earnedSkyUser,
	IFNULL(SUM(earnedSkyOwnerOnline), 0) AS earnedSkyOwnerOnline,
	IFNULL(SUM(earnedSkyOwnerOffline), 0) AS earnedSkyOwnerOffline
FROM(
	SELECT
		CASE WHEN type = 'SKYUSER' AND  proceedBy = 'ONLINE' THEN amount END AS skyUser,
		CASE WHEN type = 'SKYOWNER' AND  proceedBy = 'ONLINE' THEN amount END AS skyOwnerOnline,
		CASE WHEN type = 'SKYOWNER' AND  proceedBy = 'OFFLINE' THEN amount END AS skyOwnerOffline,
		CASE WHEN type = 'SKYUSER' AND  proceedBy = 'ONLINE' THEN earning END AS earnedSkyUser,
		CASE WHEN type = 'SKYOWNER' AND  proceedBy = 'ONLINE' THEN earning END AS earnedSkyOwnerOnline,
		CASE WHEN type = 'SKYOWNER' AND  proceedBy = 'OFFLINE' THEN earning END AS earnedSkyOwnerOffline
	FROM(
		SELECT
			a.type,
			SUM(tv.amount) AS amount,
			SUM(tv.amount * tv.extra_rate) AS earning,
			t.proceed_by AS proceedBy
		FROM
		    transaction_values tv
		INNER JOIN
		    transactions t ON t.id = tv.transaction_id
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
			tv.transaction_type_code = 'TOP_UP'
		GROUP BY
		    a.type, t.proceed_by
	) AS result_1
) AS result_2