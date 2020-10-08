SELECT
    IFNULL(COUNT(a.id), 0) AS account,
    IFNULL(SUM(a.balance), 0) AS balance,
    IFNULL(SUM(a.saved_point), 0) AS savedPoint
FROM
    account a
