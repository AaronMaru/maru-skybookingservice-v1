<!DOCTYPE html>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <style>
        body {
            margin: 0;
        }

        .container {
            background: #F5FCFF;
            height: 100vh;
        }

        .logo-sec {
            width: auto;
            margin: 0 auto;
            max-width: 700px;
        }

        .back-logo {
            text-align: center;
        }

        .back-logo img {
            height: 60px;
        }

        .sec-cont {
            width: 90%;
            box-shadow: 0px 0px 15px #f5f5f5;
            margin: 0 auto;
            background: #fff;
        }

        .sec-cont img {
            text-align: center;
            margin: 0 auto;
            display: block;
            width: 150px;
        }

        .sec-cont h5 {
            text-align: center;
            font-size: 23px;
            margin: 0px 0 10px 0;
            padding-bottom: 10px;
            font-weight: 600;
        }

        .sec-cont div.cnt-outer {
            padding: 0 20px;
            display: block;
        }

        .cnt-outer hr {
            border: 1px solid rgba(174, 174, 175, 0.1);
        }

        .sec-cont ul {
            margin: 0;
            padding: 0;
        }

        .sec-cont b, .sec-cont h6 {
            font-weight: 600;
            font-size: 15px;
            margin: 0px;
        }

        .sec-cont p {
            font-weight: 500;
            font-size: 14px;
            text-align: justify;
        }

        .sec-cont ul li {
            list-style: none;
            padding: 5px;
            color: #FF5722;
            font-weight: 500;
            font-size: 15px;
        }

        .sec-cont ul li.go-to {
            margin: 10px 0;
            text-align: center;
        }

        .sec-cont ul li.go-to a {
            text-decoration: none;
            color: #fff;
            background: #037ebc;
            padding: 9px 20px;
            border-radius: 5px;
            font-size: 14px;
        }

        .sec-cont i {
            margin: 20px 0;
            font-size: 13px;
            line-height: 1.5;
            display: block;
            color: #848383;
        }

        .sec-cont i b {
            font-size: 13px;
        }

        .sec-cont ul.scl-icn li {
            display: inline-block;
            text-align: center;
        }

        .sec-cont ul.scl-icn li img {
            width: 30px;
            margin: 6px;
        }

        .sec-cont ul.scl-icn, .sec-cont ul.scl-login {
            text-align: center;
            margin: 0;
            padding: 0;
        }

        .sec-cont ul.scl-icn p, .sec-cont ul.scl-login p {
            color: #00458c;
            font-weight: 600;
            font-size: 16px;
            margin: 20px 0 0px 0;
            text-align: center;
        }

        p.cop-rit {
            text-align: center;
            font-size: 14px;
            color: #848383;
            margin-top: 0;
            padding: 10px;
        }

        img.foot-imf {
            width: 100%;
            margin: 0 auto;
            text-align: center;
            display: block;
            border-bottom-left-radius: 6px;
            border-bottom-right-radius: 6px;
        }

        h1, h2, h3, h4, h5, h6, p, a, li {
            font-family: Muli, Roboto, RobotoDraft, Helvetica, Arial, sans-serif;
        }

        body, td, input, textarea, select {
            font-family: arial, sans-serif;
        }

        ul.scl-login li {
            display: inline-block;
            margin-top: 10px;
        }

        ul.scl-login li img {
            width: 100px;
            height: 35px;
        }

        p.reset {
            overflow: hidden;
        }

        div ul li.middle-order {
            text-align: center;
            color: #217ccf;
            position: relative;
            width: 70%;
            margin: 0 auto;
        }

        li.middle-order:after {
            content: "";
            border-bottom: 1px solid rgba(204, 204, 204, 0.50);
            width: 150px;
            height: 2px;
            display: block;
            position: absolute;
            right: 0px;
            top: 12px;
        }

        li.middle-order:before {
            content: "";
            border-bottom: 1px solid rgba(204, 204, 204, 0.50);
            width: 150px;
            height: 2px;
            display: block;
            position: absolute;
            left: 0px;
            top: 12px;
        }

        .mail-info h3 {
            font-weight: 500;
            font-size: 14px;
            text-align: justify;
        }

        /* shared email template css */
        .mail-info h3 b {
            font-weight: 700;
        }

        .tab-div img {
            width: 25px;
            display: inline-block;
            margin: 10px 5px -5px 0;
        }

        .tab-div table {
            width: 100%;
            margin: 10px 0;
        }

        .tab-div table tr td {
            padding: 10px;
            font-size: 15px;
        }

        .price-raise {
            width: 57%;
            display: inline-block;
        }

        .price-raise button,.price-raise a{
            float: right;
            padding: 10px;
            border-radius: 5px;
            background: #0c81be;
            color: #000;
        }

        .price-raise a {
            color: #000;
            text-decoration: none;
        }

        .price-raise b {
            display: block;
            font-size: 27px;
        }

        .price-raise span {
            color: #999;
            font-size: 14px;
            margin-bottom: 10px;
            display: block;
        }

        /* shared email template css */
    </style>
</head>
<body style="margin: 0;font-family: Muli,Roboto,RobotoDraft,Helvetica,Arial,sans-serif;">
<div class="container">
    <div class="logo-sec" style="width:auto;margin:0 auto;max-width:700px;background:#fff;padding:20px;">
        <div class="back-logo" style="text-align: center;">
            <img style="height: 60px;" src="${mailUrl}logo.png">
        </div>

        <div class="cnt-outer" style="display:block;font-size: 14px;text-align: justify;">
            <div class="det-number">
                <span>Hello!</span>
                <p>${description}</p>
                <p>Price may rise soon, please check it out: </p>
                <h1>Best flight for $${totalPrice}</h1>
                <#assign firstLeg = legs?first/>
                <#if legs?size gt 0 >
                    <#assign lastLeg = legs?last/>
                </#if>
                <b>${firstLeg.departureCode} -> ${firstLeg.arrivalCode} | ${firstLeg.departureDate?date?string["d MMM"]}  ${lastLeg??? then(" - " + lastLeg.departureDate?date?string["d MMM"],"")}</b>
                <p style="color: grey;">flight found on ${.now} UTC</p>


            </div>
            <hr>
            <div class="tab-div">
                <#if legs?size gt 0 &&  firstLeg.airLineName != lastLeg.airLineName>
                    <img src="${multiAirlineLogo}" alt=""> Multiple Airline
                <#else>
                    <img src="${firstLeg.airLineLogo}" alt=""> ${firstLeg.airLineName}
                </#if>
                <table>
                    <#list legs as leg>
                    <tr>
                        <td>${leg.departureDate?time?string["HH:mm"]}</td>
                        <td>${leg.departureCode}</td>
                        <td>${leg.arrivalDate?time?string["HH:mm"]}</td>
                        <td>${leg.arrivalCode}</td>
                        <td>${leg.duration}</td>
                        <td>${(leg.stop != 0) ? then('${leg.stop} Stop', 'Non Stop')}</td>
                    </tr>
                    </#list>
                </table>
            </div>
            <hr>
            <div class="price-raise">
                <a href="${link}" class="pull-right">Continue to Booking</a>
            </div>
            <hr>
        </div>


        <div class="logo-sec"
             style="background:#fff; clear:both; display:block;width: auto;margin: 0 auto;max-width:700px;padding:10px 20px;">
            <div class="sec-cont" style="width:100%;box-shadow:0px 0px 15px #f5f5f5;margin:0 auto;background:#fff;">
                <div class="cnt-outer" style="padding: 0 20px;display:  block;">

                    <ul class="scl-login" style="margin:0;padding:0;text-align: center;margin: 0;padding: 0;">
                        <p style="font-weight:500;font-size:14px;text-align:justify;color: #00458c;font-weight: 300;font-size: 16px;margin: 20px 0 0px 0;text-align: center;">
                            Get the latest Skybooking App for your phone</p>
                        <li style="list-style:none;padding:5px;color:#FF5722;font-weight:500;font-size:15px;display:inline-block;margin-top:10px;">
                            <a href="http://skybooking.app.link/IXBskEzxv0"><img style="width:100px;height:35px;"
                                                                                 src="${mailUrl}app_store_link.png"></a>
                        </li>
                        <li style="list-style:none;padding:5px;color:#FF5722;font-weight:500;font-size:15px;display:inline-block;margin-top:10px;">
                            <a href="http://skybooking.app.link/IXBskEzxv0"><img style="width:100px;height:35px;"
                                                                                 src="${mailUrl}play_store_link.png"></a>
                        </li>
                    </ul>
                    <ul class="scl-icn" style="text-align:center;margin: 0;padding: 0;">
                        <p style="font-weight:500;font-size:14px;text-align:justify;color: #00458c;font-weight: 300;font-size: 16px;margin: 20px 0 0px 0;text-align: center;">
                            Stay Connect With Us</p>
                        <li style="list-style:none;padding:5px;color:#FF5722;font-weight:500;font-size:15px; display:inline-block;text-align:center;">
                            <a href="https://www.facebook.com/skybooking.net/" target="_blank"><img
                                        style=" width:30px;margin:6px;" src="${mailUrl}facebook_link_img.png"></a>
                        </li>
                        <li style="list-style:none;padding:5px;color:#FF5722;font-weight:500;font-size:15px; display:inline-block;text-align:center;">
                            <a href="https://www.instagram.com/sky_booking_cambodia/" target="_blank"><img
                                        style=" width:30px;margin:6px;" src="${mailUrl}instagram_link_img.png"></a>
                        </li>
                        <li style="list-style:none;padding:5px;color:#FF5722;font-weight:500;font-size:15px; display:inline-block;text-align:center;">
                            <a href="https://twitter.com/Skybooking" target="_blank"><img
                                        style=" width:30px;margin:6px;" src="${mailUrl}twitter_link_img.png"></a>
                        </li>
                        <li style="list-style:none;padding:5px;color:#FF5722;font-weight:500;font-size:15px; display:inline-block;text-align:center;">
                            <a href="https://www.youtube.com/channel/UCE-5IeBNYa866AQARgk9Iow" target="_blank"><img
                                        style=" width:30px;margin:6px;" src="${mailUrl}youtube_link_img.png"></a>
                        </li>
                        <li style="list-style:none;padding:5px;color:#FF5722;font-weight:500;font-size:15px; display:inline-block;text-align:center;">
                            <a href="https://www.linkedin.com/in/sky-booking-b79582186/" target="_blank"><img
                                        style=" width:30px;margin:6px;" src="${mailUrl}linked_in_link_img.png"></a>
                        </li>
                    </ul>
                </div>
                <p style="font-weight:300;font-size:14px;text-align:justify;text-align:center;font-size:14px;color:#848383;margin-top:0;padding:10px;"
                   class="cop-rit">@ ${.now?string('yyyy')} Skybooking. All Right Reserved | By : Skybooking Co.Ltd</p>
            </div>
        </div>
    </div>
</div>
</body>
</html>