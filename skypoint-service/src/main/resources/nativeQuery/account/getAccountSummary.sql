SELECT
    COUNT(a.id) AS account,
    SUM(a.balance) AS balance,
    SUM(a.saved_point) AS earning
FROM
    skypoint_db.account a
WHERE a.`type` = 'SKYOWNER'
