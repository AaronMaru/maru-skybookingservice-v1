<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1"/>
<style type="text/css">
/*@import url('https://fonts.googleapis.com/css?family=Quicksand:300,400,500,700|Muli:400,600,700|Montserrat:400,600,700');*/
h1, h2, h3, h4, h5, h6, p, a, li {
  font-family: Muli,Roboto,RobotoDraft, Helvetica,Arial,sans-serif;
}
body, td, input, textarea, select {
  font-family: arial,sans-serif;
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
.gray-text {
    color:#666 !important;
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
                <img src="https://s3.amazonaws.com/skybooking/uploads/mail/images/iata.png" class="pull-right"/>
                <img src="https://s3.amazonaws.com/skybooking/uploads/mail/images/logo.png"/>
            </div>
            <hr/>
            <hr/>

            <div class="booking-ref">
                <div class="pull-right">
                    <span>Booking No.</span>
                    <b>SBFT01001519</b>
                </div>
                <h3>Itinerary</h3>
                <div class="pull-right">
                    <span>Booking On:</span>
                    <b>31 May 2019 08:57</b>
                </div>
                <h4>Booking Reference: <b> FZPJFU </b> </h4>
            </div>

            <div class="fight-details line-head">
                <h3>Flight Details</h3>
                <ul style="margin-top:25px;">
                    <li>
                        <small>31 May | 13:20</small>
                        <h2>PNH</h2>
                        <span>Phnom Penh, Phnom Penh</span>
                    </li>
                    <li>
                        <img  src="https://s3.amazonaws.com/skybooking/uploads/mail/images/flight_itenary.png"/><br/>
                            <small>Non Stop</small>
                    </li>
                    <li>
                        <small>31 May | 14:35</small>
                        <h2>BKK</h2>
                        <span>Bangkok, Bangkok</span>
                    </li>
                </ul>
            </div>

            <div class="dep-arrive">

                <h4>
                    <span>Flight No</span>
                    <span>Depart/Arrive Time</span>
                    <span>Departure/Arrival Airport</span>
                </h4>
                <p>PG930</p>

                <div class="time-airport">
                    <span>13:20 on Fri, 31 May 2019</span>
                    <span>14:35 on Fri, 31 May 2019</span>
                </div>

                <p>gg</p>

                <div class="time-airport">
                    <span></span>
                    <span>Phnom Penh International Airport PNH</span>
                    <span>Suvarnabhumi Airport BKK</span>
                </div>
                <br/><br/>
            </div>
            <div class="passenger-details line-head">
                <h3>Passengers</h3>
                <table>
                    <tbody>
                        <tr>
                            <th> Ticket Number </th>
                            <th> Name </th>
                            <th> PassType </th>
                            <th> Class </th>
                        </tr>
                        <tr>
                            <td>8293483378728</td>
                            <td style="text-transform: uppercase;">EWRWEREWR/WQEQWEWQ</td>
                            <td style="text-transform: uppercase;">ADULT</td>
                            <td>Economy</td>
                        </tr>
                    </tbody>
                </table>
            </div>
            <div class="baggage-info line-head">
                <strong>Baggage Allowance</strong>

                    <p><b>Adult</b></p>

                    <div class="each-person">
                        <b>[Checked Baggage] </b>
                        20kg per person. Please contact airline for detailed baggage regulations.

                    </div>

                        <div class="each-person" style="margin-bottom:15px;">
                            <b> [Carry-on Baggage] </b> 1 piece(s) per person, 7kg each piece. Please contact airline for detailed baggage regulations.
                        </div>

                    <hr/>

            </div>
            <div class="impo-info line-head">
                <h3>Important Information</h3>
                <span>1. Passengers should arrive at the airport at least 2 hours before departure to ensure they have enough time to check in. During various procedures in the airport, passengers must provide the valid ID used to purchase their ticket. Their boarding pass or itinerary may also be required.</span>
                <span>2. Please note that tickets must be used in the sequence set out in the itinerary, otherwise airlines reserve the right to refusearriage. Skybooking.net bears no responsibility if passengers are unable to board a plane due to not complying with airline policies and regulations.</span>
            </div>

        </div>
    </div>
</body>
</html>
