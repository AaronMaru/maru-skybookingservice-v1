<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
<#--    <script src="/static/js/jquery-3.5.1.min.js"></script>-->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.27.0/moment.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.27.0/moment-with-locales.min.js"></script>
    <style type="text/css">
        h1, h2, h3, h4, h5, h6, p, a, li {
            font-family: Muli, Roboto, RobotoDraft, Helvetica, Arial, sans-serif;
        }

        body, td, input, textarea, select {
            font-family: arial, sans-serif;
        }

        .container {
            background: #ddd;
        }

        .container-fluid {
            background: #fff;
            padding-right: 0px;
            padding-left: 0px;
        }

        .pull-right {
            float: right;
        }

        hr {
            clear: both;
            margin: 5px 0px;
            border-color: #ccc;
            /*border-width: 0.4px;*/
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
            width: 20%;
        }

        .time-airport span:first-child {
            width: 19% !important;
        }

        .time-airport span {
            width: 32%;
            display: inline-block;
            color: #606060;
            font-size: 14px;
            float: left;
            line-height: 25px;
        }

        .time-airport span:last-child {
            width: 31%;
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

        .baggage_infor span:first-child {
            width: 10% !important;
            /*padding-left: 15px;*/
        }

        .baggage_infor span {
            width: 100%;
            display: inline-block;
            color: #606060;
            font-size: 14px;
            float: left;
            line-height: 25px;
        }

        .text-height p {
            line-height: 5px;
        }

        .bag_anounce {
            line-height: 15px;
            color: #868686;
        }

        .c-black {
            color: #1f1c1c !important;
        }

        .round-icon {
            text-align: center !important;
        }

        @page {
            margin: 25px;
            margin-top: 0px;
        }

        .passenger-details table th, .passenger-details table td {
            padding-left: 55px;
        }

        header {
            position: fixed;
            left: 0px;
            right: 0px;
            height: 50px;
        }

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
<input type="hidden" id="pdfLang" name="pdfLang" value="${pdfLang}">
<div class="container-fluid ">
    <div class="logo-sec">
        <div class="back-logo">
            <img src="https://s3.amazonaws.com/skybooking/uploads/mail/images/iata.png" class="pull-right"/>
            <img src="${logoPdf}" alt="No Logo Yet"/>
        </div>
        <hr>
        <hr>

        <div class="booking-ref">
            <div class="pull-right">
                <span>${booking_no??? then(booking_no, 'NO LABEL YET')}.</span>
                <b>${data.bookingInfo.bookingCode}</b>
            </div>
            <h3>${itinerary??? then(itinerary, 'NO LABEL YET')}</h3>
            <div class="pull-right">
                <span>${booking_on??? then(booking_on, 'NO LABEL YET')}:</span>
                <#if pdfLang == "zh">
                    <b id="bookingOn" class="bookingOn">${data.bookingInfo.bookingDate?datetime("yyyy-MM-dd'T'HH:mm:ssXXX")?string["YYYY-MM-dd"]}</b>
                <#else>
                    <b id="bookingOn" class="bookingOn">${data.bookingInfo.bookingDate?datetime("yyyy-MM-dd'T'HH:mm:ssXXX")?string["dd MMM YYYY"]}</b>
                </#if>
                <#--                <b id="bookingOn"></b>-->
            </div>
            <h4>${booking_reference??? then(booking_reference, 'NO LABEL YET')}: <b> ${data.bookingInfo.pnrCode} </b>
            </h4>
        </div>

        <div class="fight-details line-head">
            <h3>${flight_details??? then(flight_details, 'NO LABEL YET')}</h3>
            <#list data.itineraryInfo as itemItineraryInfo>
                <#assign itinerarySegmentFirst = itemItineraryInfo.itinerarySegment?first/>
                <#assign itinerarySegmentLast = itemItineraryInfo.itinerarySegment?last/>
                <ul>
                    <li>
                        <#if pdfLang == "zh">
                            <small id="departOn" class="departOn">${itinerarySegmentFirst.departureInfo.date?datetime("yyyy-MM-dd'T'HH:mm:ssXXX")?string["yyyy-MM-dd HH:mm:ss"]}</small>
                        <#else>
                            <small id="departOn" class="departOn">${itinerarySegmentFirst.departureInfo.date?datetime("yyyy-MM-dd'T'HH:mm:ssXXX")?string["d MMM | HH:mm"]}</small>
                        </#if>
                        <h2>${itinerarySegmentFirst.departureInfo.locationCode}</h2>
                        <span>${itinerarySegmentFirst.departureInfo.city}, ${itinerarySegmentFirst.departureInfo.country}</span>
                    </li>
                    <li>
                        <img src="https://s3.amazonaws.com/skybooking/uploads/mail/images/flight_itenary.png"><br>
                        <#if itemItineraryInfo.stop != 0>
                            <small>${itemItineraryInfo.stop} ${stop??? then(stop, 'NO LABEL YET')} ${itemItineraryInfo.elapsedHourMinute}</small>
                        <#else>
                            <small>${non_stop??? then(non_stop, 'NO LABEL YET')}</small>
                        </#if>
                        <#--                        <small>${(itemItineraryInfo.stop != 0) ? then('${itemItineraryInfo.stop} Stop ${itemItineraryInfo.elapsedHourMinute}', 'Non Stop')}</small>-->
                    </li>
                    <li>
                        <#if pdfLang == "zh">
                            <small id="arriveOn" class="arriveOn">${itinerarySegmentLast.arrivalInfo.date?datetime("yyyy-MM-dd'T'HH:mm:ssXXX")?string["yyyy-MM-dd HH:mm:ss"]}</small>
                        <#else>
                            <small id="arriveOn" class="arriveOn">${itinerarySegmentLast.arrivalInfo.date?datetime("yyyy-MM-dd'T'HH:mm:ssXXX")?string["d MMM | HH:mm"]}</small>
                        </#if>
                        <h2>${itinerarySegmentLast.arrivalInfo.locationCode}</h2>
                        <span>${itinerarySegmentLast.arrivalInfo.city}, ${itinerarySegmentLast.arrivalInfo.country}</span>
                    </li>
                </ul>
                <#if itemItineraryInfo?index == 0 && data.bookingInfo.tripType == "ROUND">
                    <ul>
                        <li class="round-icon">
                            <img src="https://skybooking.s3.amazonaws.com/uploads/mail/images/round.png">
                        </li>
                    </ul>
                </#if>
            </#list>
        </div>

        <div class="dep-arrive">
            <#list data.itineraryInfo as itemItineraryInfo>
                <h4>
                    <span>${flight_no??? then(flight_no, 'NO LABEL YET')}/${airline_ref??? then(airline_ref, 'NO LABEL YET')}</span>
                    <span>${depart_arrive_time??? then(depart_arrive_time, 'NO LABEL YET')}</span>
                    <span>${depart_arrive_Airport??? then(depart_arrive_Airport, 'NO LABEL YET')}</span>
                </h4>
                <#list itemItineraryInfo.itinerarySegment as itemItinerarySegment>
                    <#if itemItinerarySegment?index != 0 && itemItineraryInfo.itinerarySegment?size gt 1>
                        <p>${transfer_in??? then(transfer_in, 'NO LABEL YET')} ${itemItinerarySegment.departureInfo.city}<#if itemItinerarySegment.departureInfo.terminal != ""> T${itemItinerarySegment.departureInfo.terminal}</#if> ${itemItinerarySegment.layoverHourMinute}</p>
                    </#if>
                    <div class="time-airport">
                        <span>${itemItinerarySegment.airlineCode}${itemItinerarySegment.flightNumber}${itemItinerarySegment.airlineRef??? then(' / ' + itemItinerarySegment.airlineRef, '')}</span>
                        <#if pdfLang == "zh">
                            <span id="flightDetailDepartOn" class="flightDetailDepartOn">${itemItinerarySegment.departureInfo.date?datetime("yyyy-MM-dd'T'HH:mm:ssXXX")?string["yyyy-MM-dd HH:mm:ss"]}</span>
                        <#else>
                            <span id="flightDetailDepartOn" class="flightDetailDepartOn">${itemItinerarySegment.departureInfo.date?datetime("yyyy-MM-dd'T'HH:mm:ssXXX")?string["HH:mm EEE, dd MMM YYYY"]}</span>
                        </#if>
                        <span>${itemItinerarySegment.departureInfo.airportName}<#if itemItinerarySegment.departureInfo.terminal != ""> T${itemItinerarySegment.departureInfo.terminal}</#if> ${itemItinerarySegment.departureInfo.locationCode} </span>
                    </div>

                    <#if itemItinerarySegment.stopQty gt 0>
                        <#list itemItinerarySegment.stopInfo as itemStopInfo>
                            <p>${stop_in??? then(stop_in, 'NO LABEL YET')} ${itemStopInfo.city} ${itemStopInfo.durationHourMinute}</p>
                        </#list>
                    </#if>

                    <div class="time-airport">
                        <span>&nbsp;</span>
                        <#if pdfLang == "zh">
                            <span id="flightDetailArriveOn" class="flightDetailArriveOn">${itemItinerarySegment.arrivalInfo.date?datetime("yyyy-MM-dd'T'HH:mm:ssXXX")?string["yyyy-MM-dd HH:mm:ss"]}</span>
                        <#else>
                            <span id="flightDetailArriveOn" class="flightDetailArriveOn">${itemItinerarySegment.arrivalInfo.date?datetime("yyyy-MM-dd'T'HH:mm:ssXXX")?string["HH:mm EEE, dd MMM YYYY"]}</span>
                        </#if>
                        <span>${itemItinerarySegment.arrivalInfo.airportName}<#if itemItinerarySegment.arrivalInfo.terminal != ""> T${itemItinerarySegment.arrivalInfo.terminal}</#if> ${itemItinerarySegment.arrivalInfo.locationCode}</span>
                    </div>

                </#list>
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
                <#list data.ticketInfo as item>
                    <tr>
                        <td>${item.ticketNumber}</td>
                        <td style="text-transform: uppercase;">${item.lastName} / ${item.firstName}</td>
                        <#if item.passType == "ADT">
                            <td style="text-transform: uppercase;">${adult??? then(adult, 'NO LABEL YET')}</td>
                        <#elseif item.passType == "CNN">
                            <td style="text-transform: uppercase;">${child??? then(child, 'NO LABEL YET')}</td>
                        <#elseif item.passType == "INF">
                            <td style="text-transform: uppercase;">${infant??? then(infant, 'NO LABEL YET')}</td>
                        <#else>
                            <td style="text-transform: uppercase;"></td>
                        </#if>

                        <#if data.bookingInfo.cabinName == "Economy">
                            <td>${economy??? then(economy, 'NO LABEL YET')}</td>
                        <#elseif data.bookingInfo.cabinName == "Business">
                            <td>${business??? then(business, 'NO LABEL YET')}</td>
                        <#elseif data.bookingInfo.cabinName == "First">
                            <td>${first??? then(first, 'NO LABEL YET')}</td>
                        <#else>
                            <td></td>
                        </#if>
                    </tr>
                </#list>
                </tbody>
            </table>
        </div>

        <div class="baggage-info line-head">
            <strong>${baggage_allowance??? then(baggage_allowance, 'NO LABEL YET')}</strong>
            <#list data.baggageInfo as itemBaggageInfo>
                <div class="each-person">
                    <p><b>${itemBaggageInfo.segment}</b></p>
                </div>
                <hr class="c-black">
                <#list itemBaggageInfo.baggageAllowance as itemBaggageAllowance>
                    <div class="baggage_infor">
                        <#if itemBaggageAllowance.type == "ADT">
                            <span><b>${adult??? then(adult, 'NO LABEL YET')}</b></span>
                        <#elseif itemBaggageAllowance.type == "CNN">
                            <span><b>${child??? then(child, 'NO LABEL YET')}</b></span>
                        <#elseif itemBaggageAllowance.type == "INF">
                            <span><b>${infant??? then(infant, 'NO LABEL YET')}</b></span>
                        </#if>
                        <span class="text-height">
                            <!-- Calculate By weight -->
                            <#if itemBaggageAllowance.piece == false>
                                <!-- If have weight -->
                                <#if itemBaggageAllowance.weights gt 0>
                                    <p>${checked_baggage??? then(checked_baggage, 'NO LABEL YET')}: ${itemBaggageAllowance.weights} Kg</p>
                                <#else>
                                    <p>${checked_baggage??? then(checked_baggage, 'NO LABEL YET')}: ${no_package??? then(no_package, 'NO LABEL YET')}</p>
                                </#if>

                                <p> <i style="color: #868686;">${baggage_more_info??? then(baggage_more_info, 'NO LABEL YET')}</i> </p>
                            <#else>
                                <!-- If have piece -->
                                <p>${checked_baggage??? then(checked_baggage, 'NO LABEL YET')}: ${itemBaggageAllowance.pieces} ${piece_per_person??? then(piece_per_person, 'NO LABEL YET')} </p>
                                <p> <i class="bag_anounce">${important_information_3??? then(important_information_3, 'NO LABEL YET')}</i> </p>
                                <p>${itemBaggageAllowance.weights} ${itemBaggageAllowance.unit} x ${itemBaggageAllowance.pieces} ${pieces??? then(pieces, 'NO LABEL YET')}</p>
                            </#if>
                        </span>
                    </div>
                    <hr style="border-top: 1px dotted #cecbcb;">
                </#list>
            </#list>
        </div>
        <div class="impo-info line-head">
            <h3>${important_information??? then(important_information, 'NO LABEL YET')}</h3>
            <span>${important_information_1??? then(important_information_1, 'NO LABEL YET')}</span>
            <span>${important_information_2??? then(important_information_2, 'NO LABEL YET')}</span>
        </div>

    </div>
</div>
<script>
    $(document).ready(function () {

        if ($('#pdfLang').val() === "zh") {
            moment.locale(moment.locale("zh-cn"));
            $('.bookingOn').text(moment($('.bookingOn').text()).format('LL'));

            $(".departOn").each(function() {
                $(this).text(moment($(this).text()).format('MMM Do | HH:MM'));
            });

            $(".arriveOn").each(function() {
                $(this).text(moment($(this).text()).format('MMM Do | HH:MM'));
            });

            $(".flightDetailDepartOn").each(function() {
                $(this).text(moment($(this).text()).format('lll'));
            });

            $(".flightDetailArriveOn").each(function() {
                $(this).text(moment($(this).text()).format('lll'));
            });

        }

    });

</script>
</body>
</html>
