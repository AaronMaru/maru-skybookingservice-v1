SELECT
		b.booking_code AS code,
		b.itinerary_ref AS reference,
		b.cont_fullname AS fullName,
		b.cont_email AS email,
		b.cont_phonecode AS phoneCode,
		b.cont_phone AS phone,
		'flight' AS type,
		b.created_at AS createdAt
	FROM bookings b
	WHERE b.booking_code LIKE CONCAT('%', :keyword, '%')
		OR b.cont_fullname  LIKE CONCAT('%', :keyword, '%')
		OR b.cont_email LIKE CONCAT('%', :keyword, '%')
		OR b.cont_phone LIKE CONCAT('%', :keyword, '%')
		OR b.itinerary_ref LIKE CONCAT('%', :keyword, '%')

UNION ALL

SELECT     hb.code AS code,
		   hb.reference_code AS reference,
		   hb.contact_fullname AS fullName ,
		   hb.contact_email AS email ,
		   hb.contact_phone_code AS phoneCode ,
		   hb.contact_phone AS phone,
		   'hotel' AS type,
		   hb.created_at AS createAt
	FROM hotel_booking hb
	WHERE hb.code LIKE CONCAT('%', :keyword, '%')
		OR hb.contact_fullname  LIKE CONCAT('%', :keyword, '%')
		OR hb.contact_email LIKE CONCAT('%', :keyword, '%')
		OR hb.contact_phone LIKE CONCAT('%', :keyword, '%')
		OR hb.reference_code LIKE CONCAT('%', :keyword, '%')

UNION ALL

SELECT
		su.user_code AS code,
		'' AS reference,
		CONCAT(su.first_name ,' ', su.lASt_name ) AS fullName,
		us.email AS email ,
		us.code AS phoneCode,
		us.phone AS phone,
		CASE WHEN cm.stakeholder_company_id is null
			THEN 'skyuser'
			ELSE 'skyowner'
		END AS type,
		su.created_at AS createAt
	FROM users us JOIN stakeholder_users su
		ON us.id = su.user_id
	LEFT JOIN stakeholder_user_hAS_companies cm
		ON su.id = cm.stakeholder_user_id
	WHERE su.user_code LIKE CONCAT('%', :keyword, '%')
		OR CONCAT(su.first_name ,' ', su.lASt_name) LIKE CONCAT('%', :keyword, '%')
		OR us.email LIKE CONCAT('%', :keyword, '%')
		OR us.phone LIKE CONCAT('%', :keyword, '%')
