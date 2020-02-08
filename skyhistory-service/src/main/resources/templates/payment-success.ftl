<div class="booking-ref">
    <h3 style="font-size:20px;margin: 10px 0;">${payment_information??? then(payment_information, 'NO LABEL YET')}</h3>
    <div style="font-size:14px;font-weight:500;color:#606060;padding:0px 10px;">
        <h4 style="color: black; margin: 20px 0px 13px 0px">${flight_info??? then(flight_info, 'NO LABEL YET')}</h4>
        <div style="padding: 0px 10px;">
            <table cellpadding="5">
                <thead style="text-align: left;">
                <tr>
                    <td style="width:20%;">${flight_date??? then(flight_date, 'NO LABEL YET')}</td>
                    <td style="width:20%;">${route??? then(route, 'NO LABEL YET')}</td>
                </tr>
                </thead>
                <tbody style="width: 25%;">
                <#list data.bookingOd as item>
                    <tr style="font-weight:300">
                        <td>${item.fSegs.depDateTime?datetime("yyyy-MM-dd HH:mm:ss")?string["d MMM | hh:mm"]}</td>
                        <td style="color: black;">${item.fSegs.depCity} (${item.fSegs.depLocation})&nbsp;${item.fSegs.arrCity} (${item.fSegs.arrLocation})</td>
                    </tr>
                </#list>
                </tbody>
            </table>
        </div>
    </div>

    <div style="font-size:14px;font-weight:500;color:#606060;padding:0px 10px;">
        <h4 style="color: black; margin: 20px 0px 13px 0px">${passenger??? then(passenger, 'NO LABEL YET')}</h4>
        <div style="padding: 0px 10px;">
            <table cellpadding="5">
                <thead style="text-align: left;">
                <tr>
                    <td style="width:20%;">${ticket_type??? then(ticket_type, 'NO LABEL YET')}</td>
                    <td style="width:20%;">${name??? then(name, 'NO LABEL YET')}</td>
                </tr>
                </thead>
                <tbody style="width: 25%;">
                <#list data.airTickets as item>
                    <tr style="font-weight:300">
                        <td>${item.passType}</td>
                        <td style="color: black; text-transform:uppercase;">${item.lastName} / ${item.firstName}</td>
                    </tr>
                </#list>
                </tbody>
            </table>
        </div>
    </div>

    <div style="font-size:14px;font-weight:500;color:#606060;padding:0px 10px;">
        <h4 style="color: black; margin: 20px 0px 13px 0px">${payment_summary??? then(payment_summary, 'NO LABEL YET')}</h4>
        <div>

            <table cellpadding="5" style="background:#ebeff6;padding:0 10px;width:100%;">
                <thead style="text-align: left;">
                <tr>
                    <th style="width:25%;padding-left: 0px;">${passtype??? then(passtype, 'NO LABEL YET')}</th>
                    <th style="width:25%;">${fare??? then(fare, 'NO LABEL YET')}</th>
                    <th style="width:25%;">${taxes_fees??? then(taxes_fees, 'NO LABEL YET')}</th>
                    <th style="width:25%;text-align: right;">${total??? then(total, 'NO LABEL YET')}</th>
                </tr>
                </thead>
                <tbody>
                <#list data.airItinPrices as item>
                    <tr style="font-weight: 300;">
                        <#if item.passType == "ADT">
                            <td style="padding-left: 0px;">ADULT</td>
                        <#elseif item.passType == "CNN">
                            <td style="padding-left: 0px;">CHILD</td>
                        <#else>
                            <td style="padding-left: 0px;">INFANT</td>
                        </#if>
                        <td>USD ${item.baseFare + data.markupAmount}</td>
                        <td>USD ${item.totalTax}</td>
                        <td style="color: black; text-align: right;">USD ${item.totalAmount} X ${item.passQty}</td>
                    </tr>
                </#list>
                <tr>
                    <td style="padding-left: 0px;">${discount_payment??? then(discount_payment, 'NO LABEL YET')}</td>
                    <td></td>
                    <td></td>
                    <td style="padding-left: 0px;text-align: right;">USD ${data.disPayment}</td>
                </tr>
                <tr>
                    <td style="padding-top: 0px;"></td>
                </tr>
                <tr>
                    <td style="border-top:2px solid #ccc;padding-bottom: 0px;" colspan="5"></td>
                </tr>

                <tr style="color:black;font-weight:600;font-size:17px;">
                    <td style="padding-left:0px;">
                        ${total??? then(total, 'NO LABEL YET')} :
                    </td>
                    <td></td>
                    <td></td>
                    <td style="text-align:right;">USD ${data.totalAmount}</td>
                </tr>
                </tbody>
            </table>
        </div>
    </div>

<#--    <p style="margin: 23px 0 23px 0;">If you'd like to check your booking status in real time, please go your booking.</p>-->
<#--    <div style="text-align: center;">-->
<#--        <a style="background:#037ebc;color:#fff;padding: 10px 0px;text-decoration:none;width:30%;display:block;margin:auto;" href="{{ config('app.url').'/skyuser/booking-detail' }}">My Booking</a>-->
<#--    </div>-->
</div>

