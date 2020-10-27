SELECT 
	createdAt,
	IFNULL(SUM(topUp), 0) AS topUp,
	IFNULL(SUM(earnedExtra),0) AS earnedExtra,
	IFNULL(SUM(earnedFlight), 0) AS earnedFlight,
	IFNULL(SUM(earnedHotel), 0) AS earnedHotel,
	IFNULL(SUM(redeemedFlight), 0) AS redeemedFlight,
	IFNULL(SUM(redeemedHotel), 0) AS redeemedHotel,
	IFNULL(SUM(refunded), 0) AS refunded
FROM
(
    SELECT
        createdAt,
        CASE WHEN transactoinTypeCode = 'TOP_UP' THEN amount END AS topUp,
        CASE WHEN transactoinTypeCode = 'EARNED_EXTRA' THEN amount END AS earnedExtra,
        CASE WHEN transactoinTypeCode = 'EARNED_FLIGHT' THEN amount END AS earnedFlight,
        CASE WHEN transactoinTypeCode = 'EARNED_HOTEL' THEN amount END AS earnedHotel,
        CASE WHEN transactoinTypeCode = 'REDEEMED_FLIGHT' THEN amount END AS redeemedFlight,
        CASE WHEN transactoinTypeCode = 'REDEEMED_HOTEL' THEN amount END AS redeemedHotel,
        CASE WHEN transactoinTypeCode = 'REFUNDED' THEN amount END AS refunded
    FROM
    (
        SELECT
            DATE(tv.created_at) AS createdAt,
            tv.transactiON_type_code  AS transactoinTypeCode,
            CASE
                WHEN tv.transaction_type_code IN ('EARNED_EXTRA', 'EARNED_HOTEL', 'EARNED_FLIGHT') THEN SUM(tv.earning_amount )
                ELSE SUM(tv.amount)
            END AS amount
        FROM
            transaction_values tv
        INNER JOIN
            transactions t ON t.id = tv.transaction_id
            AND t.status = 'SUCCESS'
        INNER JOIN account a ON a.id = t.account_id
            AND a.user_code = :userCode
        GROUP BY tv.transactiON_type_code, tv.created_at
    ) AS result1
) AS result2
GROUP BY createdAt 