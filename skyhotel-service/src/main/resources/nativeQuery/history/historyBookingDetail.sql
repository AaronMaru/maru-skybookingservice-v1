SELECT
    hb.stakeholder_user_id AS stakeholderUserId,
	hb.stakeholder_company_id AS stakeholderCompanyId,
	hb.code AS bookingCode,
	hb.booking_reference AS bookingReference,
	hb.reference_code AS referenceCode,
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
	pt.card_holder_name AS cardHolderName,
	pt.card_type AS cardType,
	pt.payment_method AS paymentMethod,
	pt.card_number AS cardNumber,
	pt.transaction_id As paymentId,
	pt.amount AS paidAmount,
	pt.booking_type As paymentOf,
	hb.total_amount_before_discount AS totalAmountBeforeDiscount

FROM `hotel_booking` hb
LEFT JOIN payment_transaction AS pt on pt.booking_id = hb.id

WHERE hb.code = :bookingCode

AND CASE WHEN :skyType = "skyuser"
            THEN (hb.stakeholder_user_id = :skyuserId AND hb.stakeholder_company_id IS NULL)
         WHEN :skyType = "company"
            THEN hb.stakeholder_company_id = :companyId
         ELSE
            hb.code = :bookingCode
    END

AND hb.status IN ('CONFIRMED', 'CANCELLED', 'FAILED', 'PENDING')

