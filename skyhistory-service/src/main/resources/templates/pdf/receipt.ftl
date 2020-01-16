<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <style type="text/css">
        h1, h2, h3, h4, h5, h6, p, a, li {
            font-family: Muli,Roboto,RobotoDraft,Helvetica,Arial,sans-serif;
        }
        body, td, input, textarea, select {
            font-family: arial,sans-serif;
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
            border-width: 1.4px;
        }
        h3 {
            font-size: 20px;
        }
        .logo-sec {
            width: auto;
            margin: 0 auto;
            max-width: 950px;
            background: #fff;
            padding: 10px 0;
        }
        .back-logo img {
            height: 45px;
        }
        .back-logo img.pull-right {
            float: right;
        }
        .booking-ref {
            padding: 0px 0;
            display: inline-block;
            width: 100%;
        }
        .billing-details {
            width: 70%;
            float: left;
        }
        .billing-details ul li {
            list-style: none;
            margin: 0;
            font-size: 13px;
            color: rgb(112, 113, 114);
            width: 100%;
            display: inline-block;
        }
        .billing-details ul li b {
            float: left;
            width: 37%;
            color: #000;
            font-weight: 500;
        }
        .billing-details ul li span {
            float: left;
            width: 62%;
        }
        .billing-details ul {
            padding: 0;
            margin: 0;
        }
        .billing-details h2 {
            font-size: 22px;
            font-weight: 600;
            margin: 15px 0;
        }
        .billing-details ul li a {
            color: rgb(112, 113, 114);
            text-decoration: none;
        }
        .booking-ref .pull-right {
            padding: 50px 0 0;
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
        .fight-details table {
            width: 100%;
            border-radius: 5px;
            margin: 15px 0;
            overflow: hidden;
            border-spacing: 0;
            border: 1px solid #ccc;
            border-collapse: inherit;
            font-size: 13px;
        }
        .fight-details table th {
            padding: 11px;
            background-color: #EBEFF6;
            color: #000;
            text-align: left;
        }
        .fight-details tbody tr td {
            padding: 7px 11px;
            border: 0px solid;
            font-weight: 500;
            color: #606060;
        }
        .pay-summary, .rec-date, .term-info {
            padding: 10px 0px 0;
            display: inline-block;
            width: 100%;
        }
        .pay-summary table {
            width: 80%;
            font-size: 13px;
        }
        .pay-summary table th {
            padding: 10px 0 5px;
            text-align: left;
        }
        .pay-summary table td {
            padding: 5px 0;
            color: #606060;
        }
        tr.tot-sec {
            border-top: 1px solid #ccc;
            font-size: 14px;
        }
        tr.tot-sec td {
            padding: 10px 0 0;
        }
        .rec-date span {
            margin: 10px 0;
            display: block;
            font-size: 13px;
        }
        .rec-date p {
            color: #606060;
            font-size: 13px;
        }
        .term-info p, .term-info b {
            color: #606060;
            font-size: 13px;
            margin: 0;
        }
        .call-service {
            padding: 0;
            width: 55%;
            float: left;
        }
        .call-service b {
            margin: 5px 0;
            display: block;
        }
        .call-service.col-md-4.col-md-offset-1 {
            padding: 30px 0 0;
            width: 28%;
            float: right;
        }
        .call-service.col-md-4.col-md-offset-1 img {
            width: 17px;
            margin-right: 5px;
        }

        @media print {
            .fight-details table th {
                background: #EBEFF6 !important;
                -webkit-print-color-adjust: exact;
            }
        }

    </style>
</head>

<body>
<div class="container-fluid">
    <div class="logo-sec">
        <div class="back-logo">
            <img src="https://s3.amazonaws.com/skybooking/uploads/mail/images/iata.png" class="pull-right"/>
            <img src="https://s3.amazonaws.com/skybooking/uploads/mail/images/logo.png"/>
        </div>
        <hr>
        <hr>
        <div class="booking-ref">
            <div class="pull-right">
                <img src="https://s3.amazonaws.com/skybooking/uploads/mail/images/stamp.png">
            </div>
            <div class="billing-details">
                <h2>${e_receipt}</h2>
                <ul>
                    <li>
                        <b>${booking_reference}</b>
                        <span>:&nbsp; ${data.pnrCode}</span>
                    </li>
                    <li>
                        <b>${transaction_id}</b>
                        <span>:&nbsp; ${data.transId}</span>
                    </li>
                    <li>
                        <b>${booking_no}</b>
                        <span>:&nbsp; ${data.bookingCode}</span>
                    </li>
                    <li>
                        <b>${payment_method}</b>
                        <span>:&nbsp; ${data.payMethod}</span>
                    </li>
                    <li>
                        <b>${booking_date}</b>
                        <span>:&nbsp; ${data.bookDate}</span>
                    </li>
                </ul>
            </div>
        </div>
        <div class="fight-details line-head">
            <h3>${flight_details}</h3>
            <table>
                <tbody>
                <tr>
                    <th>${flight_no}</th>
                    <th>${flight_date}</th>
                    <th>${route}</th>
                </tr>
                <#list data.bookingOd as item>
                    <tr>
                        <td>${item.fSegs.flightNumber}</td>
                        <td>${item.fSegs.depDateTime?string["EEE, dd MMM YYYY"]}</td>
                        <td>${item.fSegs.depCity} --- ${item.fSegs.arrCity}</td>
                    </tr>
                </#list>
                </tbody>
            </table>
        </div>
        <div class="fight-details line-head">
            <h3>${passenger}</h3>
            <table>
                <tbody>
                <tr>
                    <th>${ticket_number}</th>
                    <th>${name}</th>
                    <th>${pass_type}</th>
                    <th>${class}</th>
                </tr>
                <#list data.airTickets as item>
                    <tr>
                        <td>${item.ticketNumber}</td>
                        <td>${item.lastName} / ${item.firstName}</td>
                        <td>${item.passType}</td>
                        <td>${data.cabinName}</td>
                    </tr>
                </#list>
                </tbody>
            </table>
        </div>
        <div class="pay-summary">
            <h3>${payment_summary}</h3>
            <table>
                <tbody>
                <tr>
                    <th>${pass_type}</th>
                    <th>${fare}</th>
                    <th>${tax_fee}</th>
                    <th style="text-align:right;">${total}</th>
                </tr>

                <#list data.airItinPrices as item>
                    <tr>
                        <#if item.passType == "ADT">
                            <td>ADULT</td>
                        <#elseif item.passType == "CNN">
                            <td>CHILD</td>
                        <#else>
                            <td>INFANT</td>
                        </#if>
                        <td>USD ${item.baseFare + data.markupAmount}</td>
                        <td>USD ${item.totalTax}</td>
                        <td style="text-align:right;"><b>USD ${item.totalAmount} X ${item.passQty}</b></td>
                    </tr>
                </#list>
                <tr>
                    <td>${discount_payment}</td>
                    <td></td>
                    <td></td>
                    <td style="text-align:right;">${data.disPayment}</td>
                </tr>
                <tr class="tot-sec">
                    <td><b>${total}</b></td>
                    <td></td>
                    <td></td>
                    <td style="text-align:right;"><b>${data.totalAmount}</b></td>
                </tr>
                </tbody>
            </table>
        </div>
        <div class="rec-date">
            <span><b>${e_receipt_issued_date} : </b> ${data.bookDate}</span>
            <p>${e_receipt_issued_date_desc}</p>
        </div>
        <div class="term-info">
            <div class="call-service col-md-7">
                <b>${terms_and_condition}</b>
                <p>${terms_and_condition_desc}</p>
            </div>
            <div class="call-service col-md-4 col-md-offset-1">
                <b> ${call_service_center}</b>
                <p><img src="https://skybooking.s3.amazonaws.com/uploads/mail/images/phone.png">(+855) 98888100 / (+855) 98888200</p>
            </div>
        </div>
    </div>
</div>
</body>
</html>