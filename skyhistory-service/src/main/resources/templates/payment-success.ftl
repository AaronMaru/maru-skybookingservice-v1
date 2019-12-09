 <div class="sec-cont" style="width:100%;box-shadow: 0px 0px 15px #f5f5f5;margin: 0 auto;background:#fff;">
     <img style="text-align: center;margin: 0 auto;display: block;width: 150px;" src="${mailUrl}mail_pic.png"/>
    <h5 style="text-align: center;font-size: 23px;margin: 0px 0 10px 0;padding-bottom: 10px;font-weight: 500;">Flight Payment Successful</h5>
</div>
<h6 style="font-size: 15px;margin: 0px;font-weight: 500">Dear Customer,</h6>
<div class="cnt-outer" style="display:block;font-size: 14px;text-align: justify;">
    <p>Thank you for choosing Skybooking.net. Payment for your flight has been received.</p>
    <p>Ticket(s) will be issued within 2 hr 0 mins.</p>
    <p>The confirmation, itinerary, and e-receipt will be emailed to you once the tickets have
        been issued. Skybooking.net guarantees your tickets will be issued.
    </p>
</div>
<div class="booking-ref">
    <h3 style="font-size:20px;margin: 10px 0;">Payment Information</h3>
    <div style="font-size:14px;font-weight:500;color:#606060;padding:0px 10px;">
        <h4 style="color: black; margin: 20px 0px 13px 0px">Flight Info</h4>
        <div style="padding: 0px 10px;">
            <table cellpadding="5">
                <thead style="text-align: left;">
                <tr>
                    <td style="width:20%;">Flight Date</td>
                    <td style="width:20%;">Route</td>
                </tr>
                </thead>
                <tbody style="width: 25%;">
                @foreach ($user['booking']->ordes as $k => $item)
                @foreach ($item['segninfo'] as $segninfo)
                <tr style="font-weight:300">
                    <td>{{date('d M | H:i A', strtotime($segninfo['dep_date']))}}</td>
                    <td style="color: black;">{{$segninfo['dep_city']. " (".$segninfo['dep_location_code']." ) " . $segninfo['arr_city']. " (".$segninfo['arr_location_code'].") "}}</td>
                </tr>
                @endforeach
                @endforeach
                </tbody>
            </table>
        </div>
    </div>

    <div style="font-size:14px;font-weight:500;color:#606060;padding:0px 10px;">
        <h4 style="color: black; margin: 20px 0px 13px 0px">Passenger</h4>
        <div style="padding: 0px 10px;">
            <table cellpadding="5">
                <thead style="text-align: left;">
                <tr>
                    <td style="width:20%;">Ticket type</td>
                    <td style="width:20%;">Name</td>
                </tr>
                </thead>
                <tbody style="width: 25%;">
                @foreach ($user['booking']->tickets as $item)
                <tr style="font-weight:300">
                    <td>{{$item['pass_type']}}</td>
                    <td style="color: black; text-transform:uppercase;"> {{$item['last_name'] .'/'. $item['first_name']}}</td>
                </tr>
                @endforeach
                </tbody>
            </table>
        </div>
    </div>

    <div style="font-size:14px;font-weight:500;color:#606060;padding:0px 10px;">
        <h4 style="color: black; margin: 20px 0px 13px 0px">Payment Summary</h4>
        <div>
            @php

            $FlightTotal = trimAmount($user['booking']->total_amount + $user['booking']->markup_amount + $user['booking']->markup_pay_amount - $user['booking']->dis_pay_met_amount);

            $TotalAmount = (float) number_format( $FlightTotal, 2, '.', '' );

            @endphp
            <table cellpadding="5" style="background:#ebeff6;padding:0 10px;width:100%;">
                <thead style="text-align: left;">
                <tr>
                    <th style="width:25%;padding-left: 0px;">PassType</th>
                    <th style="width:25%;">Fare</th>
                    <th style="width:25%;">Taxes & Fees</th>
                    <th style="width:25%;text-align: right;">Total</th>
                </tr>
                </thead>
                <tbody>
                @foreach ($user['booking']->itinerary as $item)
                @php
                $baseFare = getFinalPrice( $item['base_fare'] + (float) number_format($item['base_fare'] * $user['booking']->markup_percentage, 2, ".", ""), getMPMPercentage());
                $tax = getFinalPrice( $item['total_tax'] + (float) number_format($item['total_tax'] * $user['booking']->markup_percentage, 2, ".", ""), getMPMPercentage());
                @endphp
                <tr style="font-weight: 300;">
                    <td style="padding-left: 0px;">{{ $item['pass_type']  }}</td>
                    <td>USD {{ $baseFare }}</td>
                    <td>USD {{ $tax }}</td>
                    <td style="color: black; text-align: right;">USD {{ $baseFare + $tax . " X " . $item['pass_qty'] }}</td>
                </tr>
                @endforeach
                <tr>
                    <td style="padding-left: 0px;">Discount Payment</td>
                    <td></td>
                    <td></td>
                    <td style="padding-left: 0px;text-align: right;">USD {{ $user['booking']->dis_pay_met_amount  }}</td>
                </tr>
                <tr>
                    <td style="padding-top: 0px;"></td>
                </tr>
                <tr>
                    <td style="border-top:2px solid #ccc;padding-bottom: 0px;" colspan="5"></td>
                </tr>

                <tr style="color:black;font-weight:600;font-size:17px;">
                    <td style="padding-left:0px;">
                        Total :
                    </td>
                    <td></td>
                    <td></td>
                    <td style="text-align:right;">USD {{ $FlightTotal }}</td>
                </tr>
                </tbody>
            </table>
        </div>
    </div>

    <p style="margin: 23px 0 23px 0;">If you'd like to check your booking status in real time, please go your booking.</p>
    <div style="text-align: center;">
        <a style="background:#037ebc;color:#fff;padding: 10px 0px;text-decoration:none;width:30%;display:block;margin:auto;" href="{{ config('app.url').'/skyuser/booking-detail' }}">My Booking</a>
    </div>
</div>

