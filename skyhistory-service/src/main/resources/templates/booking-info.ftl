<div class="booking-ref">
    <div class="pull-right" style="float:right;font-size:13.5px;font-weight:500;color:#606060;">
        <span>${booking_no??? then(booking_no, 'NO LABEL YET')}</span>
        <b style="font-weight: 600; color:#222;">${data.bookingCode}</b>
    </div>
    <h6 style="font-size:15px;font-weight: 500;margin:10px 0;">${itinerary??? then(itinerary, 'NO LABEL YET')}</h6>
    <div class="pull-right" style="float:right;font-size:13.5px;font-weight:500;color:#606060;">
        <span>${booking_on??? then(booking_on, 'NO LABEL YET')}:</span>
        <b style="font-weight:500;">${data.bookDate}</b>
    </div>
    <h4 style="font-size:13.5px;margin:10px 0;font-weight:500;color:#606060;">${booking_reference??? then(booking_reference, 'NO LABEL YET')}:
        <b style="font-weight:600;color:#222;">${data.pnrCode}</b>
    </h4>
</div>

<div class="fight-details line-head">
    <h6 style="font-size: 15px;font-weight: 500;margin-bottom:0;position:relative;">${flight_details??? then(flight_details, 'NO LABEL YET')}</h6>
        <#list data.bookingOd as item>
            <ul style="width:100%;display:inline-block;float:left;padding:0;text-align:center;color: #606060;">
                <li style="width: 25%; display:inline-block;text-align: center;">
                    <small>${item.fSegs.depDateTime?datetime("yyyy-MM-dd HH:mm:ss")?string["d MMM | hh:mm"]}</small>
                    <h2 style="margin: 5px 0;color: #707070;font-size: 20px;">${item.fSegs.depLocation}</h2>
                    <span style="color:#707070;font-size: 13px;">${item.fSegs.depCity}, ${item.fSegs.depCountry}</span>
                </li>
                <li style="display:inline-block;text-align: center;">
                    <img style="width:91px;text-align:center;display:block;margin:0 15px 10px;" src="https://skybooking.s3.amazonaws.com/uploads/mail/images/flight_itenary.png"/>
                    <small style="display:block; font-size: 12px;color: #606060;">${(item.stop != 0) ? then('Stop ${item.stop}' , 'Non Stop')}</small>
                </li>
                <li style="width:25%;display:inline-block;text-align:center;color:#606060;">
                    <small>${item.fSegs.arrDateTime?datetime("yyyy-MM-dd HH:mm:ss")?string["d MMM | hh:mm"]}</small>
                    <h2 style="margin: 5px 0;color: #707070;font-size: 20px;">${item.fSegs.arrLocation}</h2>
                    <span style="color:#707070;font-size: 13px;">${item.fSegs.arrCity}, ${item.fSegs.arrCountry}</span>
                </li>
            </ul>
            <#if item?index == 0 && data.tripType == "Return">
                <ul style="width: 100%;display:inline-block;float:left;text-align:center;padding:0;margin-bottom:0;">
                    <li style="display:inline-block;text-align: center;">
                        <img src="https://skybooking.s3.amazonaws.com/uploads/mail/images/round.png">
                    </li>
                </ul>
            </#if>

        </#list>
</div>

<div class="passenger-details line-head">
    <h6 style="font-size: 15px;font-weight: 500;">${passengers??? then(passengers, 'NO LABEL YET')}</h6>
    <table style="width:100%;text-align:left;border:1px solid #ccc;border-radius:5px;margin:15px 0;border-spacing:0;overflow:hidden;">
        <tbody>
            <tr>

                <th style="padding:11px;background-color:#EBEFF6;color:#000;font-size:14px;">${ticket_number??? then(ticket_number, 'NO LABEL YET')}</th>
                <th style="padding:11px;background-color:#EBEFF6;color:#000;font-size:14px;">${name??? then(name, 'NO LABEL YET')}</th>
                <th style="padding:11px;background-color:#EBEFF6;color:#000;font-size:14px;">${pass_type??? then(pass_type, 'NO LABEL YET')}</th>
                <th style="padding:11px;background-color:#EBEFF6;color:#000;font-size:14px;">${class??? then(class, 'NO LABEL YET')}</th>
            </tr>
            <#list data.airTickets as item>
                <tr>
                    <td style="padding: 7px 11px;border: 0px solid;font-weight: 500;color: #606060;font-size: 14px;">
                        ${item.ticketNumber}
                    </td>
                    <td style="padding: 7px 11px;border: 0px solid;font-weight: 500;color: #606060;font-size: 14px; text-transform:uppercase;">
                        ${item.lastName} / ${item.firstName}
                    </td>
                    <td style=" padding: 7px 11px;border: 0px solid;font-weight: 500;color: #606060;font-size: 14px; text-transform:uppercase;">
                        ${item.passType}
                    </td>
                    <td style=" padding: 7px 11px;border: 0px solid;font-weight: 500;color: #606060;font-size: 14px;">
                        ${data.cabinName}
                    </td>
                </tr>
            </#list>
        </tbody>
    </table>
</div>