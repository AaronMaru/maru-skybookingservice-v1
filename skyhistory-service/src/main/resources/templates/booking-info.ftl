<div class="sec-cont" style="width:100%;box-shadow: 0px 0px 15px #f5f5f5;margin: 0 auto;background:#fff;">
    <img style="text-align: center;margin: 0 auto;display: block;width: 150px;" src="${mailUrl}mail_pic.png"/>
    <h5 style="text-align: center;font-size: 23px;margin: 0px 0 10px 0;padding-bottom: 10px;font-weight: 500;">Flight Booking Confirmed</h5>
</div>
<h6 style="font-size: 15px;margin: 0px;font-weight: 500">Dear Customer,</h6>
<div class="cnt-outer" style="display:block;font-size: 14px;text-align: justify;">
    <p>Your flight booking has been confirmed and your tickets have been issued.</p>
    <p>If you'd like to change or cancel your booking, the Skybooking.net app makes it easy.</p>
    <p>You will find your itinerary and e-receipt attached. We advise you print out your itinerary and take it with you to ensure your trip goes as smoothly as possible.</p>
</div>
<div class="booking-ref">
    <div class="pull-right" style="float:right;font-size:13.5px;font-weight:500;color:#606060;">
        <span>Booking No.</span>
        <b style="font-weight: 600; color:#222;">{{ SBFT01001519 }}</b>
    </div>
    <h6 style="font-size:15px;font-weight: 500;margin:10px 0;">Itinerary</h6>
    <div class="pull-right" style="float:right;font-size:13.5px;font-weight:500;color:#606060;">
        <span>Booking On:</span>
        <b style="font-weight:500;">{{ 31 May 2019 08:57 }}</b>
    </div>
    <h4 style="font-size:13.5px;margin:10px 0;font-weight:500;color:#606060;">Booking Reference:
        <b style="font-weight:600;color:#222;">{{ FZPJFU }}</b>
    </h4>
</div>
<div class="fight-details line-head">
    <h6 style="font-size: 15px;font-weight: 500;margin-bottom:0;position:relative;">Flight Details</h6>

    <ul style="width:100%;display:inline-block;float:left;padding:0;text-align:center;color: #606060;">
        <li style="width: 25%; display:inline-block;text-align: center;">
            <small>{{ 31 May | 13:20 }}</small>
            <h2 style="margin: 5px 0;color: #707070;font-size: 20px;">{{ PNH }}</h2>
            <span style="color:#707070;font-size: 13px;">{{ Phnom Penh, Phnom }}</span>
        </li>
        <li style="display:inline-block;text-align: center;">
            <img style="width:91px;text-align:center;display:block;margin:0 15px 10px;" src="https://skybooking.s3.amazonaws.com/uploads/mail/images/flight_itenary.png"/>
            <small style="display:block; font-size: 12px;color: #606060;">{{ Non Stop }}</small>
        </li>
        <li style="width:25%;display:inline-block;text-align:center;color:#606060;">
            <small>{{ 31 May | 14:35 }}</small>
            <h2 style="margin: 5px 0;color: #707070;font-size: 20px;">{{ BKK }}</h2>
            <span style="color:#707070;font-size: 13px;">{{ Bangkok, BangKok }}</span>
        </li>
    </ul>

</div>

<div class="passenger-details line-head">
    <h6 style="font-size: 15px;font-weight: 500;">Passengers</h6>
    <table style="width:100%;text-align:left;border:1px solid #ccc;border-radius:5px;margin:15px 0;border-spacing:0;overflow:hidden;">
        <tbody>
            <tr>

                <th style="padding:11px;background-color:#EBEFF6;color:#000;font-size:14px;">Ticket Number</th>
                <th style="padding:11px;background-color:#EBEFF6;color:#000;font-size:14px;">Name</th>
                <th style="padding:11px;background-color:#EBEFF6;color:#000;font-size:14px;">PassType</th>
                <th style="padding:11px;background-color:#EBEFF6;color:#000;font-size:14px;">Class</th>
            </tr>
            <tr>
                <td style=" padding: 7px 11px;border: 0px solid;font-weight: 500;color: #606060;font-size: 14px;">
                    {{ 8293483378728 }}
                </td>
                <td style=" padding: 7px 11px;border: 0px solid;font-weight: 500;color: #606060;font-size: 14px; text-transform:uppercase;">
                    {{ EWRWEREWR/WQEQWEWQ }}
                </td>
                <td style=" padding: 7px 11px;border: 0px solid;font-weight: 500;color: #606060;font-size: 14px; text-transform:uppercase;">
                    {{ ADULT }}
                </td>
                <td style=" padding: 7px 11px;border: 0px solid;font-weight: 500;color: #606060;font-size: 14px;">
                    {{ Economy }}
                </td>
            </tr>
        </tbody>
    </table>
</div>