SELECT percentage
FROM hotel_markups hm
WHERE hm.type = :skyType
AND CASE WHEN :hotelPrice > 500 THEN
			hm.`from` >= 500
		ELSE
			hm.`from` < :hotelPrice AND :hotelPrice <= hm.`to`
		END