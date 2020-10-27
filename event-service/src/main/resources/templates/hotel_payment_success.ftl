<div class="cnt-outer">
    <hr>
    <div class="pay-info" style="display:inline-block; width:100%" width="100%">
        <h4 style="margin:10px 0">${payment_information??? then(payment_information, 'NO LABEL YET')}</h4>
        <div class="left-de" style="float:left; margin-right:0%; width:78%" width="78%">
            <h5 style="border-radius:5px; font-size:14px; font-weight:700; padding:7px; background: rgba(3, 126, 188, 0.1)">${description??? then(description, 'NO LABEL YET')}</h5>
            <table class="des-area" style="border-radius:5px; padding:10px; width:100%; background: rgba(3, 126, 188, 0.1)" width="100%">
                <tr>
                    <td style="color:#000; font-size:13px; padding:9px; font-weight:600">${hotel_name??? then(hotel_name, 'NO LABEL YET')}</td>
                    <td style="color:#848383; font-size:13px; padding:9px">${data.hotelName}</td>
                </tr>
                <tr>
                    <td style="color:#000; font-size:13px; padding:9px; font-weight:600">${period??? then(period, 'NO LABEL YET')}</td>
                    <td style="color:#848383; font-size:13px; padding:9px">${data.period} Days</td>
                </tr>
                <tr>
                    <td style="color:#000; font-size:13px; padding:9px; font-weight:600">${room_type??? then(room_type, 'NO LABEL YET')}</td>
                    <td style="color:#848383; font-size:13px; padding:9px">${data.roomType}</td>
                </tr>
                <tr>
                    <td style="color:#000; font-size:13px; padding:9px; font-weight:600">${no_of_rooms??? then(no_of_rooms, 'NO LABEL YET')}</td>
                    <td style="color:#848383; font-size:13px; padding:9px">${data.numRooms}</td>
                </tr>
                <tr>
                    <td style="color:#000; font-size:13px; padding:9px; font-weight:600">${no_of_extra_beds??? then(no_of_extra_beds, 'NO LABEL YET')}</td>
                    <td style="color:#848383; font-size:13px; padding:9px">${data.numExtraBed}</td>
                </tr>
                <tr>
                    <td style="color:#000; font-size:13px; padding:9px; font-weight:600">${total_room_charges??? then(total_room_charges, 'NO LABEL YET')}</td>
                </tr>
                <tr>
                    <td style="color:#000; font-size:13px; padding:9px; font-weight:600">${total_extra_bed_charges??? then(total_extra_bed_charges, 'NO LABEL YET')}</td>
                </tr>
            </table>
            <h6 style="border-radius:5px; font-size:14px; font-weight:700; margin:5px 0; padding:10px 15px; text-align:right; background: rgba(3, 126, 188, 0.1)" align="right">${grand_total??? then(grand_total, 'NO LABEL YET')}</h6>
            <h6 style="border-radius:5px; font-size:14px; font-weight:700; margin:5px 0; padding:10px 15px; text-align:right; background: rgba(3, 126, 188, 0.1)" align="right">${total_charges_to_credit_card??? then(total_charges_to_credit_card, 'NO LABEL YET')}</h6>
        </div>

        <div class="right-de" style="float:right; text-align:center; width:20%" align="center" width="20%">
            <h5 style="border-radius:5px; font-size:14px; font-weight:700; padding:7px; background: rgba(3, 126, 188, 0.1)">${amount??? then(amount, 'NO LABEL YET')}</h5>
            <table class="amount-pay" style="border-radius:5px; padding:10px; width:100%; background: rgba(3, 126, 188, 0.1)" width="100%">
                <tr>
                    <td style="color:#848383; font-size:13px; padding:9px">&nbsp;</td>
                </tr>
                <tr>
                    <td style="color:#848383; font-size:13px; padding:9px">&nbsp;</td>
                </tr>
                <tr>
                    <td style="color:#848383; font-size:13px; padding:9px">&nbsp;</td>
                </tr>
                <tr>
                    <td style="color:#848383; font-size:13px; padding:9px">&nbsp;</td>
                </tr>
                <tr>
                    <td style="color:#848383; font-size:13px; padding:9px">&nbsp;</td>
                </tr>
                <tr class="room-charge">
                    <td style="color:#676767; font-size:13px; padding:9px; border-radius:5px; background: rgba(3, 126, 188, 0.3)">${usd??? then(usd, 'NO LABEL YET')} ${data.totalRoomCharges}</td>
                </tr>
                <tr class="room-charge">
                    <td style="color:#676767; font-size:13px; padding:9px; border-radius:5px; background: rgba(3, 126, 188, 0.3)">${usd??? then(usd, 'NO LABEL YET')} ${data.totalExtraBedCharges}</td>
                </tr>
            </table>
            <h6 style="border-radius:5px; font-size:14px; font-weight:700; margin:5px 0; padding:10px 15px; text-align:center; background: rgba(3, 126, 188, 0.3)" align="center">${usd??? then(usd, 'NO LABEL YET')} ${data.grandTotal}</h6>
            <h6 style="border-radius:5px; font-size:14px; font-weight:700; margin:5px 0; padding:10px 15px; text-align:center; background: rgba(3, 126, 188, 0.3)" align="center">${usd??? then(usd, 'NO LABEL YET')} ${data.totalPaidToCreditCard}</h6>
        </div>
    </div>


</div>