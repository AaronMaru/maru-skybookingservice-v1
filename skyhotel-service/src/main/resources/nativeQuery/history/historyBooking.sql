SELECT
    hb.stakeholder_user_id AS stakeholderUserId,
	hb.stakeholder_company_id AS stakeholderCompanyId,
	hb.code AS bookingCode,
	hb.booking_reference AS bookingReference,
	hb.reference,
	hb.contact_fullname AS contactName,
	CONCAT(hb.contact_phone_code,hb.contact_phone) AS contactPhone,
	hb.contact_email AS contactEmail,
	hb.cost,
	hb.status,
	hb.markup_amount AS markupAmount,
	hb.payment_fee_amount AS paymentFeeAmount,
	hb.markup_pay_amount AS markupPayAmount,
	hb.discount_payment_amount AS discountPaymentAmount,
	hb.commision_amount AS commissionAmount,
	hb.refund_fee_amount AS refundFeeAmount,
	hb.total_amount AS totalAmount,
	hb.currency_code AS currencyCode,
	hb.check_in AS checkIn,
	hb.check_out AS checkOut,
	hb.total_amount_before_discount AS totalAmountBeforeDiscount

FROM `hotel_booking` hb

WHERE hb.status IN :defaultStatus

AND CASE WHEN :skyType = "skyuser"
    THEN
        (hb.stakeholder_user_id = :skyuserId AND hb.stakeholder_company_id IS NULL)
    ELSE
        -- Condition for Skyowner
        hb.stakeholder_company_id = :companyId
    END

AND CASE WHEN
        :action = 'FILTER'
    THEN

        -- By price rang
        -- Param :priceRangeStart, :priceRangeEnd
        ( hb.total_amount - hb.discount_payment_amount ) >= :priceRangeStart AND ( hb.total_amount - hb.discount_payment_amount ) <= :priceRangeEnd

        -- Book by
        -- Param :bookedBy
        AND CASE WHEN
                :bookedBy <> ''
            THEN
                hb.contact_fullname LIKE :bookedBy '%'
            ELSE
                hb.status IN :defaultStatus
            END

        -- Paymnet Type
        -- Param :paymentType
        AND CASE WHEN
                :paymentType <> 'ALL'
            THEN
                (SELECT COUNT(pt.id) FROM payment_transaction pt WHERE pt.payment_method = :paymentType AND pt.booking_id = hb.id) > 0
            ELSE
                hb.status IN :defaultStatus
            END

        -- By booking status
        -- Param :bookingStatus
        AND CASE WHEN
                :bookingStatus <> 'ALL'
            THEN
                hb.status = :bookingStatus
            ELSE
                hb.status IN :defaultStatus
            END

        -- Between check in check ut date
        -- Param :checkIn, :checkOut
        AND CASE WHEN
                :checkIn <> '' AND :checkOut <> ''
            THEN
                hb.check_in >= :checkIn AND hb.check_out <= :checkOut
            ELSE
                hb.status IN :defaultStatus
            END

        -- By total room
        -- Param :roomNumber
        AND CASE WHEN
                :roomNumber > 0
            THEN
                (SELECT SUM(hbr.total_room) FROM hotel_booking_rate hbr WHERE hbr.booking_id = hb.id) = :roomNumber
        ELSE
            hb.status IN :defaultStatus
        END

    ELSE
        hb.status IN :defaultStatus
    END

AND CASE WHEN
        :action = 'SEARCH'
    THEN
        hb.status LIKE :keywordSearch '%'

    OR hb.contact_fullname LIKE :keywordSearch '%'
    OR hb.code LIKE :keywordSearch '%'
    OR Date(hb.created_at) LIKE :keywordSearch '%'

    OR (SELECT a.name
        FROM attachment a
        WHERE a.type = 'BOOKING_HOTEL_VOUCHER'
        AND a.reference = hb.code LIMIT 1)
        LIKE :keywordSearch '%'

    ELSE
        hb.status IN :defaultStatus
    END
