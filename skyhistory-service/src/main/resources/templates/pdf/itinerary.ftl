<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <style type="text/css">
        h1, h2, h3, h4, h5, h6, p, a, li {
            font-family: Muli,Roboto,RobotoDraft, Helvetica,Arial,sans-serif;
        }
        body, td, input, textarea, select {
            font-family: arial,sans-serif;
        }
        .container {
            background: #fff;
        }
        .pull-right {
            float: right;
        }
        hr {
            clear: both;
            margin: 5px 0px;
            border-color: #ccc;
            border-width: 0.4px;
        }
        h3 {
            font-size: 20px;
        }
        .logo-sec {
            width: auto;
            margin: 0 auto;
            max-width: 950px;
            background: #fff;
            padding: 20px 0;
        }
        .back-logo img {
            height: 60px;
        }
        .back-logo img.pull-right {
            float: right;
        }
        .booking-ref h3 {
            font-size: 20px;
            margin: 10px 0;
        }
        .booking-ref h4 {
            font-size: 14px;
            margin: 10px 0;
            font-weight: 500;
            color: #606060;
        }
        .booking-ref .pull-right {
            font-size: 14px;
            font-weight: 500;
            color: #606060;
        }
        .booking-ref .pull-right b {
            font-size: 14px;
            font-weight: 500;
            color: #067ebc;
        }
        .fight-details ul {
            text-align: center;
        }
        .fight-details ul li {
            display: inline-block;
            width: 32%;
        }
        .line-head h3 {
            margin-bottom: 0;
            position: relative;
        }
        .line-head h3:after {
            content: "";
            border-top: 1px solid #ccc;
            width: 81%;
            height: 1px;
            display: block;
            position: absolute;
            right: 0;
            top: 14px;
        }

        .fight-details ul li h2 {
            margin: 5px 0;
            color: #707070;
            font-size: 20px;
        }
        .fight-details ul li:first-child {
            text-align: right;
            color: #606060;
        }
        .fight-details ul li:last-child {
            text-align: left;
            color: #606060;
        }
        .fight-details ul li:nth-child(2) img {
            display: block;
            width: 120px;
            text-align: center;
            margin-left: auto;
            margin-right: auto;
        }
        .fight-details ul li:nth-child(2) small {
            font-size: 11px;
            color: #606060;
        }
        .fight-details ul li small, .fight-details ul li span {
            color: #707070;
            font-size: 12px;
        }
        .round-icon {
            text-align: center !important;
        }
        .dep-arrive {
            border: 1px solid #ccc;
            border-radius: 5px;
        }
        .dep-arrive h4 {
            background: #EBEFF6;
            margin: 0 0 7px;
            padding: 9px;
            padding-left: 57px;
        }
        .dep-arrive p {
            background: #EBEFF6;
            display: block;
            width: 50%;
            padding: 10px;
            margin: 0 0 0 27%;
            border-radius: 5px;
            color: #606060;
            font-size: 14px;
            text-align: center;
            clear: both;
        }
        .dep-arrive h4 span {
            display: inline-block;
            width: 34%;
            font-size: 14px;
        }
        .time-airport {
            padding: 0px 15px;
            margin-left: 44px;
            display: inline-block;
            width: 100%;
            clear: both;
        }
        .dep-arrive h4 span:first-child {
            width: 15%;
        }
        .time-airport span:first-child {
            width: 15% !important;
        }
        .time-airport span {
            width: 35%;
            display: inline-block;
            color: #606060;
            font-size: 14px;
            float: left;
            line-height: 25px;
        }
        .time-airport span:last-child {
            width: 50%;
            float: left;
        }
        .passenger-details table {
            width: 100%;
            text-align: left;
            border: 1px solid #ccc;
            border-radius: 5px;
            margin: 15px 0;
            border-spacing: 0;
            overflow: hidden;
        }
        .passenger-details table th {
            padding: 11px;
            background-color: #EBEFF6;
            color: #000;
            font-size: 14px;
        }
        .passenger-details table td {
            padding: 7px 11px;
            border: 0px solid;
            font-weight: 500;
            color: #606060;
            font-size: 14px;
        }
        .baggage-info {
            display: inline-block;
            width: 100%;
        }
        .baggage-info strong {
            margin: 15px 0;
            display: block;
        }
        .each-person {
            margin: 10px 0;
            font-size: 14px;
            color: #606060;
        }
        .impo-info span {
            margin: 10px 0 0 25px;
            display: block;
            line-height: 26px;
            font-size: 14px;
            color: #606060;
            text-align: justify;
        }
        .impo-info.line-head h3:after {
            width: 67%;
        }
        .passenger-details.line-head h3:after {
            width: 82%;
        }
        @page {
            margin:25px;
            margin-top:0px;
        }
        .passenger-details table th, .passenger-details table td {
            padding-left: 55px;
        }

        header { position: fixed; left: 0px; right: 0px; height: 50px; }
        @media print {
            .receip-deta h3 {
                background: #cccccc61;
                -webkit-print-color-adjust: exact;
            }
            .dep-arrive h4 {
                background: #EBEFF6;
                -webkit-print-color-adjust: exact;
            }
        }
    </style>
</head>

<body>
<div class="container">
    <div class="logo-sec">
        <div class="back-logo">
            <img src="https://s3.amazonaws.com/skybooking/uploads/mail/images/iata.png" class="pull-right">
            <img src=${logoPdf} alt="No Logo Yet">
        </div>
        <hr>
        <hr>

        <div class="booking-ref">
            <div class="pull-right">
                <span>${booking_no??? then(booking_no, 'NO LABEL YET')}.</span>
                <b>${data.bookingCode}</b>
            </div>
            <h3>${itinerary??? then(itinerary, 'NO LABEL YET')}</h3>
            <div class="pull-right">
                <span>${booking_on??? then(booking_on, 'NO LABEL YET')}:</span>
                <b>${data.bookDate}</b>
            </div>
            <h4>${booking_reference??? then(booking_reference, 'NO LABEL YET')}: <b> ${data.pnrCode} </b> </h4>
        </div>

        <div class="fight-details line-head">
            <h3>${flight_details??? then(flight_details, 'NO LABEL YET')}</h3>
            <#list data.bookingOd as item>
                <ul>
                    <li>
                        <small>${item.fSegs.depDateTime?datetime("yyyy-MM-dd HH:mm:ss")?string["d MMM | hh:mm"]}</small>
                        <h2>${item.fSegs.depLocation}</h2>
                        <span>${item.fSegs.depCity}, ${item.fSegs.depCountry}</span>
                    </li>
                    <li>
                        <img src="https://s3.amazonaws.com/skybooking/uploads/mail/images/flight_itenary.png"><br>
                        <small>${(item.stop != 0) ? then('Stop ${item.stop}', 'Non Stop')}</small>
                    </li>
                    <li>
                        <small>${item.fSegs.arrDateTime?datetime("yyyy-MM-dd HH:mm:ss")?string["d MMM | hh:mm"]}</small>
                        <h2>${item.fSegs.arrLocation}</h2>
                        <span>${item.fSegs.arrCity}, ${item.fSegs.arrCountry}</span>
                    </li>
                </ul>
                <#if item?index == 0 && data.tripType == "Return">
                    <ul>
                        <li class="round-icon">
                            <img src="https://skybooking.s3.amazonaws.com/uploads/mail/images/round.png">
                        </li>
                    </ul>
                </#if>
            </#list>
        </div>

        <div class="dep-arrive">
            <h4>
                <span>${flight_no??? then(flight_no, 'NO LABEL YET')}</span>
                <span>${depart_arrive_time??? then(depart_arrive_time, 'NO LABEL YET')}</span>
                <span>${depart_arrive_Airport??? then(depart_arrive_Airport, 'NO LABEL YET')}</span>
            </h4>
            <#list data.bookingOd as item>
                <div class="time-airport">
                    <span>${item.fSegs.flightNumber}</span>
                    <span>${item.fSegs.depDateTime?datetime("yyyy-MM-dd HH:mm:ss")?string["hh:mm EEE, dd MMM YYYY"]}</span>
                    <span>${item.fSegs.depLocationName} </span>
                </div>
                <div class="time-airport">
                    <span>&nbsp;</span>
                    <span>${item.fSegs.arrDateTime?datetime("yyyy-MM-dd HH:mm:ss")?string["hh:mm EEE, dd MMM YYYY"]}</span>
                    <span>${item.fSegs.arrLocationName}</span>
                </div>
            </#list>

            <br/><br/>

        </div>
        <div class="passenger-details line-head">
            <h3>${passenger??? then(passenger, 'NO LABEL YET')}</h3>
            <table>
                <thead>
                    <tr>
                        <th>${ticket_number??? then(ticket_number, 'NO LABEL YET')}</th>
                        <th>${name??? then(name, 'NO LABEL YET')}</th>
                        <th>${pass_type??? then(pass_type, 'NO LABEL YET')}</th>
                        <th>${class??? then(class, 'NO LABEL YET')}</th>
                    </tr>
                </thead>
                <tbody>
                    <#list data.airTickets as item>
                        <tr>
                            <td>${item.ticketNumber}</td>
                            <td style="text-transform: uppercase;">${item.lastName} / ${item.firstName}</td>
                            <td style="text-transform: uppercase;">${item.passType}</td>
                            <td>${data.cabinName}</td>
                        </tr>
                    </#list>
                </tbody>
            </table>
        </div>

        <div class="baggage-info line-head">
            <strong>${baggage_allowance}</strong>
            <#list data.airTickets as item>
                <#if item.bookingBaggageInfoRS.passType == "ADT">
                    <td>ADULT</td>
                <#elseif item.bookingBaggageInfoRS.passType == "CNN">
                    <td>CHILD</td>
                <#elseif item.bookingBaggageInfoRS.passType == "INF">
                    <td>INFANT</td>
                </#if>

                <div class="each-person">
                    <b>[${checked_baggage??? then(checked_baggage, 'NO LABEL YET')}]</b>
                    <#if item.bookingBaggageInfoRS.pieceStatus == 0>
                        ${item.bookingBaggageInfoRS.bagWeight}${item.bookingBaggageInfoRS.bagUnit}&nbsp;${per_person??? then(per_person, 'NO LABEL YET')}
                    <#else>
                        ${item.bookingBaggageInfoRS.bagPiece} ${each_piece??? then(each_piece, 'NO LABEL YET')}, ${item.bookingBaggageInfoRS.bagWeight}${item.bookingBaggageInfoRS.bagUnit}&nbsp;${each_piece??? then(each_piece, 'NO LABEL YET')}
                    </#if>
                    ${baggage_more_info??? then(baggage_more_info, 'NO LABEL YET')}
                </div>

                <div class="each-person" style="margin-bottom:15px;">
                    <b> [${carry_on_baggage??? then(carry_on_baggage, 'NO LABEL YET')}] </b>
                    <#if item.bookingBaggageInfoRS.passType == "INF">
                        ${carry_on_inf??? then(carry_on_inf, 'NO LABEL YET')}
                    <#else>
                        ${carry_on??? then(carry_on, 'NO LABEL YET')}
                    </#if>
                    ${baggage_more_info??? then(baggage_more_info, 'NO LABEL YET')}
                </div>
            </#list>

            <hr/>

        </div>
        <div class="impo-info line-head">
            <h3>${important_information??? then(important_information, 'NO LABEL YET')}</h3>
            <span>${important_information_1??? then(important_information_1, 'NO LABEL YET')}</span>
            <span>${important_information_2??? then(important_information_2, 'NO LABEL YET')}</span>
        </div>

    </div>
</div>
</body>
</html>
