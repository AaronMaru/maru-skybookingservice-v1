<div class="booking-ref">
    <div class="pull-right" style="float:right;font-size:13.5px;font-weight:500;color:#606060;">
        <span>${booking_no??? then(booking_no, 'NO LABEL YET')}</span>
        <b style="font-weight: 600; color:#222;">${data.bookingInfo.bookingCode}</b>
    </div>
    <h6 style="font-size:15px;font-weight: 500;margin:10px 0;">${itinerary??? then(itinerary, 'NO LABEL YET')}</h6>
    <div class="pull-right" style="float:right;font-size:13.5px;font-weight:500;color:#606060;">
        <span>${booking_on??? then(booking_on, 'NO LABEL YET')}:</span>
        <b style="font-weight:500;">${data.bookingInfo.bookingDate?datetime("yyyy-MM-dd'T'HH:mm:ssXXX")?string["dd MMM YYYY"]}</b>
    </div>
    <h4 style="font-size:13.5px;margin:10px 0;font-weight:500;color:#606060;">${booking_reference??? then(booking_reference, 'NO LABEL YET')}:
        <b style="font-weight:600;color:#222;">${data.bookingInfo.pnrCode}</b>
    </h4>
</div>

<div class="fight-details line-head">
    <h6 style="font-size: 15px;font-weight: 500;margin-bottom:0;position:relative;">${flight_details??? then(flight_details, 'NO LABEL YET')}</h6>
        <#list data.itineraryInfo as itemItineraryInfo>
            <#assign itinerarySegmentFirst = itemItineraryInfo.itinerarySegment?first/>
            <#assign itinerarySegmentLast = itemItineraryInfo.itinerarySegment?last/>
            <ul style="width:100%;display:inline-block;float:left;padding:0;text-align:center;color: #606060;">
                <li style="width: 25%; display:inline-block;text-align: center;">
                    <small>${itinerarySegmentFirst.departureInfo.date?datetime("yyyy-MM-dd'T'HH:mm:ssXXX")?string["d MMM | HH:mm"]}</small>
                    <h2 style="margin: 5px 0;color: #707070;font-size: 20px;">${itinerarySegmentFirst.departureInfo.locationCode}</h2>
                    <span style="color:#707070;font-size: 13px;">${itinerarySegmentFirst.departureInfo.city}, ${itinerarySegmentFirst.departureInfo.country}</span>
                </li>
                <li style="display:inline-block;text-align: center;">
                    <img style="width:91px;text-align:center;display:block;margin:0 15px 10px;" src="https://skybooking.s3.amazonaws.com/uploads/mail/images/flight_itenary.png"/>
                    <small style="display:block; font-size: 12px;color: #606060;">${(itemItineraryInfo.stop != 0) ? then('${itemItineraryInfo.stop} Stop ${itemItineraryInfo.elapsedHourMinute}' , 'Non Stop')}</small>
                </li>
                <li style="width:25%;display:inline-block;text-align:center;color:#606060;">
                    <small>${itinerarySegmentLast.arrivalInfo.date?datetime("yyyy-MM-dd'T'HH:mm:ssXXX")?string["d MMM | HH:mm"]}</small>
                    <h2 style="margin: 5px 0;color: #707070;font-size: 20px;">${itinerarySegmentLast.arrivalInfo.locationCode}</h2>
                    <span style="color:#707070;font-size: 13px;">${itinerarySegmentLast.arrivalInfo.city}, ${itinerarySegmentLast.arrivalInfo.country}</span>
                </li>
            </ul>
            <#if itemItineraryInfo?index == 0 && data.bookingInfo.tripType == "ROUND">
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
            <#list data.ticketInfo as item>
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
                        ${data.bookingInfo.cabinName}
                    </td>
                </tr>
            </#list>
        </tbody>
    </table>
</div>