<!DOCTYPE html>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <link href="https://fonts.googleapis.com/css?family=Quicksand:300,400,500,700|Muli:400,600,700|Montserrat:400,600,700"
          rel='stylesheet' type='text/css'/>
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

        span b {
            line-height: 18px;
        }

        .logo-sec {
            width: auto;
            margin: 0 auto;
            max-width: 793px;
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

        .fight-details {
            margin-bottom: 15px;
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
            width: 33%;
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
            margin: 0 50px 10px;
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

        .time-airport span {
            width: 35%;
            display: inline-block;
            color: #606060;
            font-size: 14px;
            float: left;
            line-height: 25px;
        }

        .time-airport span:last-child {
            width: 64%;
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

        .head-booking {
            text-align: right;
        }

        .impo-info span {
            margin: 10px 0 0 0px;
            display: block;
            line-height: 23px;
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

        .passenger-details table th, .passenger-details table td {
            padding-left: 55px;
        }

        .footer-area {
            padding: 50px 0px 0px;
            position: relative;
            z-index: 1000;
            clear: both;
            margin: 0px 0;
        }

        .footer-area img {
            float: left;
            width: 25%;
        }

        img.stamp-sign {
            position: absolute;
            right: 0px;
            top: -65px;
        }

        .logo-footer {
            position: absolute;
            left: 0px;
            bottom: 0px;
        }

        #footer {
            min-width: 793px;
            text-align: center;
            position: fixed;
            bottom: 125px;
        }

        @page {
            margin: 25px;
            margin-top: 0px;
        }

        .footer-area p {
            width: 50%;
            margin: 0;
            font-size: 13px;
            margin-left: 0px;
            text-align: left;
            color: #067ebc;
        }

        .foot-contact {
            width: 100%;
            margin-top: 15px;
            font-size: 13px;
            margin-left: 0px;
            color: #067ebc;
            text-align: left;
        }

        .foot-contact span {
            display: block;
        }

        .footer-text {
            color: #606060;
        }

        .baggage-info table {
            border: 0px solid #ccc;
        }

        .passenger-details.baggage-info table th, .passenger-details.baggage-info table td {
            background: transparent;
            padding: 5px;
            font-size: 13px;
        }

        .page-break {
            page-break-after: always;
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

        .head-tbl-text th {
            color: #666 !important;
        }

        .head-tbl-text td {
            color: #666 !important;
        }

        .gray-text {
            color: #666 !important;
        }
    </style>
</head>

<body>
<div class="container">
    <div class="logo-sec">
        <div class="back-logo">
            <img src="https://s3.amazonaws.com/skybooking/uploads/mail/images/iata.png" class="pull-right"/>
            <img src="https://s3.amazonaws.com/skybooking/uploads/mail/images/logo.png"/>
        </div>
        <hr/>
        <hr/>
        <div class="booking-ref">
            <div class="pull-right" style="text-align: right;">
                <span>Booking No. <b> SBFT01001519 </b></span><br/>
                <span>Transaction ID. <b> FBTP0100719 </b></span><br/>
                <span>Booking On: <b>1 May 2019 08:57</b></span><br/>
                <span>Payment Method: <b>Pi Pay</b></span><br/>
            </div>
            <h3>E-Receipt</h3>
            <div  style="text-align: right;">
                <span>Booking On:</span>
                <b> 1 May 2019 08:57 </b>
                <br/>
                <span>Payment Method: <b> Pi Pay </b></span>
            </div>
            <h4>Booking Reference: <b>FZPJFU</b> </h4>
        </div>

        <div class="fight-details line-head"><h3>Flight Details</h3></div>
        <div class="dep-arrive">
            <h4>
                <span style="width: 15%;">Flight No</span>
                <span style="width: 24%;">Flight Date</span>
                <span>Route</span>
            </h4>
            <div class="time-airport">
                <span style="width: 15%;">{{ PG930 }}</span>
                <span style="width: 25%;"> {{ Fri, 31 May 2019 }}</span>
                <span> {{ Phnom Penh PNH }}
                     <img src="https://s3.amazonaws.com/skybooking/uploads/mail/images/flight_itenary_line.png"
                          style="width:30px; margin-top:5px; padding:2px; 0px; 0px;"/>
                   {{ Bangkok BKK }}
                </span>
            </div>
            <br/><br/>
        </div>
        <div class="passenger-details line-head">
            <h3>Passengers</h3>
            <table>
                <tbody>
                <tr>
                    <th> Ticket Number</th>
                    <th> Name</th>
                    <th> PassType</th>
                    <th> Class</th>
                </tr>

                <tr>
                    <td>{{ 1321654135 }}</td>
                    <td style="text-transform: uppercase;">{{ WERWFSDFWERFSDFS }}</td>
                    <td style="text-transform: uppercase;">{{ ADULT }}</td>
                    <td>{{ Economy }}</td>
                </tr>
                </tbody>
            </table>
        </div>
        <br/>

        <div class="passenger-details baggage-info" style="width: 65%; margin-top:5px;">
            <strong>Payment Summary</strong>
            <table style="margin-top:-5px;">
                <tbody>
                <tr class="head-tbl-text">
                    <th>PassType</th>
                    <th>Fare</th>
                    <th>Taxes &amp; Fees</th>
                    <th style="text-align:right;">Total</th>
                </tr>
                <tr>
                    <td style="color: red !important;">ADULTssssssss</td>
                    <td>{{ USD 69.3 }}</td>
                    <td>{{ USD 71.15 }}</td>
                    <td style="text-align:right;"><b>{{ 140.45 x 1 }}</b></td>
                </tr>
                </tbody>
            </table>

            <span class="gray-text">Discount Payment:</span>
            <span class="pull-right gray-text">{{ USD 0 }}</span>
            <hr/>

            <span class="gray-text"><b>Total:</b></span>
            <span class="pull-right gray-text"><b>{{ USD 133.76 }}</b></span>
        </div>
        <div class="impo-info ">
            <span>There may be a slight discrepancy between the sum of the individual itemsand the total amount due toconversions at the exchange rate. The total amount will always prevail. </span>
        </div>

    </div>
</div>
<div id="footer">
    <div class="footer-area">
        <img src="https://s3.amazonaws.com/skybooking/uploads/mail/images/stamp.png" class="stamp-sign"
             style="float: right; padding-right:55px;"/>
        <img src="https://s3.amazonaws.com/skybooking/uploads/mail/images/logo.png" class="logo-footer"/>
        <p class="address-footer">
            <span class="footer-text">Phnom Penh, Oval Office Tower, 13th Floor St.360,Boeng Keng Kang, Chamkarmon, Cambodia 23000</span>
        </p>
        <div class="foot-contact">
            <span><b>Call our Customer Service Center 24/7:</b></span>
            <span class="footer-text">Customer Support: (+855) 98 666 931 / (+855) 098 888 559</span>
        </div>
    </div>
    <div style="position: relative;">
        <img src="https://s3.amazonaws.com/skybooking/uploads/mail/images/bg.png"
             style="width: 95%;position: absolute;left: 0;top: -100px;"/>
    </div>
</div>
</body>
</html>