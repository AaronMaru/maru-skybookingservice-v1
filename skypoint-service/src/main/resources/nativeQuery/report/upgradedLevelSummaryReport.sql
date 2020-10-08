SELECT
    IFNULL(SUM(level1), 0) AS level1,
	IFNULL(SUM(level2), 0) AS level2,
	IFNULL(SUM(level3), 0) AS level3,
	IFNULL(SUM(level4), 0) AS level4
FROM
(
    SELECT
        CASE WHEN levelCode = 'LEVEL1' THEN total END AS level1,
        CASE WHEN levelCode = 'LEVEL2' THEN total END AS level2,
        CASE WHEN levelCode = 'LEVEL3' THEN total END AS level3,
        CASE WHEN levelCode = 'LEVEL4' THEN total END AS level4
    FROM
    (
        SELECT
            ulh.level_code AS levelCode,
            COUNT(ulh.id) AS total
        FROM
            skypoint_db.upgrade_level_history ulh
        WHERE
            DATE(ulh.created_at) >= DATE(:startDate)
        AND
            DATE(ulh.created_at) <= DATE(:endDate)
        GROUP BY
            ulh.level_code
    ) AS result1
)AS result2