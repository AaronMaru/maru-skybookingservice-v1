<!DOCTYPE html>
<html>

<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta charset="UTF-8">
    <title>Voucher Receipt PDF</title>
    <style type="text/css">
        @media print {
            .rec-name h5, table.des-area, table.amount-pay, .pay-info h5, .left-de h6, .right-de h6 {
                background: #e5f2f8;
                -webkit-print-color-adjust: exact
            }
        }
        /* vietnamese */
        @font-face {
            font-family: 'Muli';
            font-style: normal;
            font-weight: 400;
            src: url(https://fonts.gstatic.com/s/muli/v22/7Auwp_0qiz-afT3GLRrX.woff2) format('woff2');
            unicode-range: U+0102-0103, U+0110-0111, U+0128-0129, U+0168-0169, U+01A0-01A1, U+01AF-01B0, U+1EA0-1EF9, U+20AB;
        }
        /* latin-ext */
        @font-face {
            font-family: 'Muli';
            font-style: normal;
            font-weight: 400;
            src: url(https://fonts.gstatic.com/s/muli/v22/7Auwp_0qiz-afTzGLRrX.woff2) format('woff2');
            unicode-range: U+0100-024F, U+0259, U+1E00-1EFF, U+2020, U+20A0-20AB, U+20AD-20CF, U+2113, U+2C60-2C7F, U+A720-A7FF;
        }
        /* latin */
        @font-face {
            font-family: 'Muli';
            font-style: normal;
            font-weight: 400;
            src: url(https://fonts.gstatic.com/s/muli/v22/7Auwp_0qiz-afTLGLQ.woff2) format('woff2');
            unicode-range: U+0000-00FF, U+0131, U+0152-0153, U+02BB-02BC, U+02C6, U+02DA, U+02DC, U+2000-206F, U+2074, U+20AC, U+2122, U+2191, U+2193, U+2212, U+2215, U+FEFF, U+FFFD;
        }
        /* vietnamese */
        @font-face {
            font-family: 'Muli';
            font-style: normal;
            font-weight: 600;
            src: url(https://fonts.gstatic.com/s/muli/v22/7Auwp_0qiz-afT3GLRrX.woff2) format('woff2');
            unicode-range: U+0102-0103, U+0110-0111, U+0128-0129, U+0168-0169, U+01A0-01A1, U+01AF-01B0, U+1EA0-1EF9, U+20AB;
        }
        /* latin-ext */
        @font-face {
            font-family: 'Muli';
            font-style: normal;
            font-weight: 600;
            src: url(https://fonts.gstatic.com/s/muli/v22/7Auwp_0qiz-afTzGLRrX.woff2) format('woff2');
            unicode-range: U+0100-024F, U+0259, U+1E00-1EFF, U+2020, U+20A0-20AB, U+20AD-20CF, U+2113, U+2C60-2C7F, U+A720-A7FF;
        }
        /* latin */
        @font-face {
            font-family: 'Muli';
            font-style: normal;
            font-weight: 600;
            src: url(https://fonts.gstatic.com/s/muli/v22/7Auwp_0qiz-afTLGLQ.woff2) format('woff2');
            unicode-range: U+0000-00FF, U+0131, U+0152-0153, U+02BB-02BC, U+02C6, U+02DA, U+02DC, U+2000-206F, U+2074, U+20AC, U+2122, U+2191, U+2193, U+2212, U+2215, U+FEFF, U+FFFD;
        }
        /* vietnamese */
        @font-face {
            font-family: 'Muli';
            font-style: normal;
            font-weight: 700;
            src: url(https://fonts.gstatic.com/s/muli/v22/7Auwp_0qiz-afT3GLRrX.woff2) format('woff2');
            unicode-range: U+0102-0103, U+0110-0111, U+0128-0129, U+0168-0169, U+01A0-01A1, U+01AF-01B0, U+1EA0-1EF9, U+20AB;
        }
        /* latin-ext */
        @font-face {
            font-family: 'Muli';
            font-style: normal;
            font-weight: 700;
            src: url(https://fonts.gstatic.com/s/muli/v22/7Auwp_0qiz-afTzGLRrX.woff2) format('woff2');
            unicode-range: U+0100-024F, U+0259, U+1E00-1EFF, U+2020, U+20A0-20AB, U+20AD-20CF, U+2113, U+2C60-2C7F, U+A720-A7FF;
        }
        /* latin */
        @font-face {
            font-family: 'Muli';
            font-style: normal;
            font-weight: 700;
            src: url(https://fonts.gstatic.com/s/muli/v22/7Auwp_0qiz-afTLGLQ.woff2) format('woff2');
            unicode-range: U+0000-00FF, U+0131, U+0152-0153, U+02BB-02BC, U+02C6, U+02DA, U+02DC, U+2000-206F, U+2074, U+20AC, U+2122, U+2191, U+2193, U+2212, U+2215, U+FEFF, U+FFFD;
        }
        /* vietnamese */
        @font-face {
            font-family: 'Quicksand';
            font-style: normal;
            font-weight: 300;
            src: url(https://fonts.gstatic.com/s/quicksand/v21/6xKtdSZaM9iE8KbpRA_hJFQNcOM.woff2) format('woff2');
            unicode-range: U+0102-0103, U+0110-0111, U+0128-0129, U+0168-0169, U+01A0-01A1, U+01AF-01B0, U+1EA0-1EF9, U+20AB;
        }
        /* latin-ext */
        @font-face {
            font-family: 'Quicksand';
            font-style: normal;
            font-weight: 300;
            src: url(https://fonts.gstatic.com/s/quicksand/v21/6xKtdSZaM9iE8KbpRA_hJVQNcOM.woff2) format('woff2');
            unicode-range: U+0100-024F, U+0259, U+1E00-1EFF, U+2020, U+20A0-20AB, U+20AD-20CF, U+2113, U+2C60-2C7F, U+A720-A7FF;
        }
        /* latin */
        @font-face {
            font-family: 'Quicksand';
            font-style: normal;
            font-weight: 300;
            src: url(https://fonts.gstatic.com/s/quicksand/v21/6xKtdSZaM9iE8KbpRA_hK1QN.woff2) format('woff2');
            unicode-range: U+0000-00FF, U+0131, U+0152-0153, U+02BB-02BC, U+02C6, U+02DA, U+02DC, U+2000-206F, U+2074, U+20AC, U+2122, U+2191, U+2193, U+2212, U+2215, U+FEFF, U+FFFD;
        }
        /* vietnamese */
        @font-face {
            font-family: 'Quicksand';
            font-style: normal;
            font-weight: 400;
            src: url(https://fonts.gstatic.com/s/quicksand/v21/6xKtdSZaM9iE8KbpRA_hJFQNcOM.woff2) format('woff2');
            unicode-range: U+0102-0103, U+0110-0111, U+0128-0129, U+0168-0169, U+01A0-01A1, U+01AF-01B0, U+1EA0-1EF9, U+20AB;
        }
        /* latin-ext */
        @font-face {
            font-family: 'Quicksand';
            font-style: normal;
            font-weight: 400;
            src: url(https://fonts.gstatic.com/s/quicksand/v21/6xKtdSZaM9iE8KbpRA_hJVQNcOM.woff2) format('woff2');
            unicode-range: U+0100-024F, U+0259, U+1E00-1EFF, U+2020, U+20A0-20AB, U+20AD-20CF, U+2113, U+2C60-2C7F, U+A720-A7FF;
        }
        /* latin */
        @font-face {
            font-family: 'Quicksand';
            font-style: normal;
            font-weight: 400;
            src: url(https://fonts.gstatic.com/s/quicksand/v21/6xKtdSZaM9iE8KbpRA_hK1QN.woff2) format('woff2');
            unicode-range: U+0000-00FF, U+0131, U+0152-0153, U+02BB-02BC, U+02C6, U+02DA, U+02DC, U+2000-206F, U+2074, U+20AC, U+2122, U+2191, U+2193, U+2212, U+2215, U+FEFF, U+FFFD;
        }
        /* vietnamese */
        @font-face {
            font-family: 'Quicksand';
            font-style: normal;
            font-weight: 500;
            src: url(https://fonts.gstatic.com/s/quicksand/v21/6xKtdSZaM9iE8KbpRA_hJFQNcOM.woff2) format('woff2');
            unicode-range: U+0102-0103, U+0110-0111, U+0128-0129, U+0168-0169, U+01A0-01A1, U+01AF-01B0, U+1EA0-1EF9, U+20AB;
        }
        /* latin-ext */
        @font-face {
            font-family: 'Quicksand';
            font-style: normal;
            font-weight: 500;
            src: url(https://fonts.gstatic.com/s/quicksand/v21/6xKtdSZaM9iE8KbpRA_hJVQNcOM.woff2) format('woff2');
            unicode-range: U+0100-024F, U+0259, U+1E00-1EFF, U+2020, U+20A0-20AB, U+20AD-20CF, U+2113, U+2C60-2C7F, U+A720-A7FF;
        }
        /* latin */
        @font-face {
            font-family: 'Quicksand';
            font-style: normal;
            font-weight: 500;
            src: url(https://fonts.gstatic.com/s/quicksand/v21/6xKtdSZaM9iE8KbpRA_hK1QN.woff2) format('woff2');
            unicode-range: U+0000-00FF, U+0131, U+0152-0153, U+02BB-02BC, U+02C6, U+02DA, U+02DC, U+2000-206F, U+2074, U+20AC, U+2122, U+2191, U+2193, U+2212, U+2215, U+FEFF, U+FFFD;
        }
        /* vietnamese */
        @font-face {
            font-family: 'Quicksand';
            font-style: normal;
            font-weight: 700;
            src: url(https://fonts.gstatic.com/s/quicksand/v21/6xKtdSZaM9iE8KbpRA_hJFQNcOM.woff2) format('woff2');
            unicode-range: U+0102-0103, U+0110-0111, U+0128-0129, U+0168-0169, U+01A0-01A1, U+01AF-01B0, U+1EA0-1EF9, U+20AB;
        }
        /* latin-ext */
        @font-face {
            font-family: 'Quicksand';
            font-style: normal;
            font-weight: 700;
            src: url(https://fonts.gstatic.com/s/quicksand/v21/6xKtdSZaM9iE8KbpRA_hJVQNcOM.woff2) format('woff2');
            unicode-range: U+0100-024F, U+0259, U+1E00-1EFF, U+2020, U+20A0-20AB, U+20AD-20CF, U+2113, U+2C60-2C7F, U+A720-A7FF;
        }
        /* latin */
        @font-face {
            font-family: 'Quicksand';
            font-style: normal;
            font-weight: 700;
            src: url(https://fonts.gstatic.com/s/quicksand/v21/6xKtdSZaM9iE8KbpRA_hK1QN.woff2) format('woff2');
            unicode-range: U+0000-00FF, U+0131, U+0152-0153, U+02BB-02BC, U+02C6, U+02DA, U+02DC, U+2000-206F, U+2074, U+20AC, U+2122, U+2191, U+2193, U+2212, U+2215, U+FEFF, U+FFFD;
        }
    </style>
</head>

<body style="margin:0; font-family:arial, sans-serif">
<div class="container" style="background:#F5FCFF; height:100vh" height="100vh">
    <div class="logo-sec" style="margin:0 auto; max-width:700px; padding:15px 0; width:auto" width="auto">
        <div class="sec-cont" style="background:#fff; box-shadow:0 0 15px #f5f5f5; margin:0 auto; width:90%" width="90%">
            <div class="back-logo" style="padding:15px; text-align:left" align="left">
                <img src="https://dev-hotels-v1.s3.amazonaws.com/uploads/mail/logo.png" style="height:40px" height="40">
                <hr style="border:5px solid #067ebc">
            </div>
            <div class="cnt-outer" style="display:block; padding:0 20px">
                <h6 style="font-family:Muli, Roboto, RobotoDraft, Helvetica, Arial, sans-serif; font-size:15px; font-weight:600; margin:0"><b style="font-size:15px; font-weight:600; margin:0">${label_receipt.addr??? then(label_receipt.addr, 'NO LABEL YET')}:</b></h6>
                <p style="font-family:Muli, Roboto, RobotoDraft, Helvetica, Arial, sans-serif; font-size:12px; font-weight:500; text-align:justify; color:#989898" align="justify">${label_receipt.skb_addr1??? then(label_receipt.skb_addr1, 'NO LABEL YET')}</p>
                <p style="font-family:Muli, Roboto, RobotoDraft, Helvetica, Arial, sans-serif; font-size:12px; font-weight:500; text-align:justify; color:#989898" align="justify">${label_receipt.skb_addr2??? then(label_receipt.skb_addr2, 'NO LABEL YET')}</p>
                <p style="font-family:Muli, Roboto, RobotoDraft, Helvetica, Arial, sans-serif; font-size:12px; font-weight:500; text-align:justify; color:#989898" align="justify">${label_receipt.skb_addr3??? then(label_receipt.skb_addr3, 'NO LABEL YET')}</p>
                <p style="font-family:Muli, Roboto, RobotoDraft, Helvetica, Arial, sans-serif; font-size:12px; font-weight:500; text-align:justify; color:#989898" align="justify"><b style="font-size:12px; font-weight:900; margin:0; color:#000">${label_receipt.tel??? then(label_receipt.tel, 'NO LABEL YET')}: <a href="#" style="font-family:Muli, Roboto, RobotoDraft, Helvetica, Arial, sans-serif; color:#989898; text-decoration:none">(+855) 098 666 931</a> / <a href="#" style="font-family:Muli, Roboto, RobotoDraft, Helvetica, Arial, sans-serif; color:#989898; text-decoration:none">(+855) 098 666 559</a></b></p>
                <p style="font-family:Muli, Roboto, RobotoDraft, Helvetica, Arial, sans-serif; font-size:12px; font-weight:500; text-align:justify; color:#989898" align="justify"><b style="font-size:12px; font-weight:900; margin:0; color:#000">${label_receipt.bk_no??? then(label_receipt.bk_no, 'NO LABEL YET')}:</b> ${data.bookingCode}</p>
                <p style="font-family:Muli, Roboto, RobotoDraft, Helvetica, Arial, sans-serif; font-size:12px; font-weight:500; text-align:justify; color:#989898" align="justify"><b style="font-size:12px; font-weight:900; margin:0; color:#000">${label_receipt.charge_date??? then(label_receipt.charge_date, 'NO LABEL YET')}:</b> ${data.chargeDate}</p>

                <div class="rec-name" style="margin-bottom:20px">
                    <h4 style="font-family:Muli, Roboto, RobotoDraft, Helvetica, Arial, sans-serif; margin-top:0; font-size:23px; margin:10px 0; text-align:center" align="center">${label_receipt.receipt??? then(label_receipt.receipt, 'NO LABEL YET')}</h4>
                    <h5 style="font-family:Muli, Roboto, RobotoDraft, Helvetica, Arial, sans-serif; font-size:15px; font-weight:700; margin:0 0 10px 0; padding-bottom:10px; text-align:left; background:#e5f2f8; border-radius:5px; padding:10px 0" align="center">${label_receipt.cus_name_addr??? then(label_receipt.cus_name_addr, 'NO LABEL YET')}</h5>
                    <table class="des-area" style="border-radius:5px; padding:10px; width:100%; background: rgba(3, 126, 188, 0.1)" width="100%">
                        <tr>
                            <td style="font-family:arial, sans-serif; color:#000; font-size:13px; padding:9px; font-weight:600">${label_receipt.name??? then(label_receipt.name, 'NO LABEL YET')}</td>
                            <td style="font-family:arial, sans-serif; color:#848383; font-size:13px; padding:9px">${data.customerName}</td>
                        </tr>
                        <tr>
                            <td style="font-family:arial, sans-serif; color:#000; font-size:13px; padding:9px; font-weight:600">${label_receipt.bll_addr??? then(label_receipt.bll_addr, 'NO LABEL YET')}</td>
                            <td style="font-family:arial, sans-serif; color:#848383; font-size:13px; padding:9px">${data.billingAddress}</td>
                        </tr>
                        <tr>
                            <td style="font-family:arial, sans-serif; color:#000; font-size:13px; padding:9px; font-weight:600">${label_receipt.email_addr??? then(label_receipt.email_addr, 'NO LABEL YET')}</td>
                            <td style="font-family:arial, sans-serif; color:#848383; font-size:13px; padding:9px">${data.emailAddress}</td>
                        </tr>
                    </table>
                </div>
                <div class="pay-info" style="display:inline-block; width:100%" width="100%">
                    <div class="left-de" style="float:left; margin-right:5%; width:75%" width="75%">
                        <h5 style="font-family:Muli, Roboto, RobotoDraft, Helvetica, Arial, sans-serif; font-size:14px; font-weight:700; margin:0 0 10px 0; padding-bottom:10px; text-align:left; border-radius:5px; padding:7px; background: rgba(3, 126, 188, 0.1)" align="center">${label_receipt.desc??? then(label_receipt.desc, 'NO LABEL YET')}</h5>
                        <table class="des-area" style="border-radius:5px; padding:10px; width:100%; background: rgba(3, 126, 188, 0.1)" width="100%">
                            <tr>
                                <td style="font-family:arial, sans-serif; color:#000; font-size:13px; padding:9px; font-weight:600">${label_receipt.hotel_name??? then(label_receipt.hotel_name, 'NO LABEL YET')}</td>
                                <td style="font-family:arial, sans-serif; color:#848383; font-size:13px; padding:9px">${data.hotelName}</td>
                            </tr>
                            <tr>
                                <td style="font-family:arial, sans-serif; color:#000; font-size:13px; padding:9px; font-weight:600">${label_receipt.period??? then(label_receipt.period, 'NO LABEL YET')}</td>
                                <td style="font-family:arial, sans-serif; color:#848383; font-size:13px; padding:9px">${data.period} Days</td>
                            </tr>
                            <tr>
                                <td style="font-family:arial, sans-serif; color:#000; font-size:13px; padding:9px; font-weight:600">${label_receipt.rm_type??? then(label_receipt.rm_type, 'NO LABEL YET')}</td>
                                <td style="font-family:arial, sans-serif; color:#848383; font-size:13px; padding:9px">${data.roomType}</td>
                            </tr>
                            <tr>
                                <td style="font-family:arial, sans-serif; color:#000; font-size:13px; padding:9px; font-weight:600"># ${label_receipt.of_rm??? then(label_receipt.of_rm, 'NO LABEL YET')}</td>
                                <td style="font-family:arial, sans-serif; color:#848383; font-size:13px; padding:9px">${data.numRooms}</td>
                            </tr>
                            <tr>
                                <td style="font-family:arial, sans-serif; color:#000; font-size:13px; padding:9px; font-weight:600"># ${label_receipt.of_ext_bed??? then(label_receipt.of_ext_bed, 'NO LABEL YET')}</td>
                                <td style="font-family:arial, sans-serif; color:#848383; font-size:13px; padding:9px">${data.numExtraBed}</td>
                            </tr>
                            <tr>
                                <td style="font-family:arial, sans-serif; color:#000; font-size:13px; padding:9px; font-weight:600">${label_receipt.total_room_charge??? then(label_receipt.total_room_charge, 'NO LABEL YET')}</td>
                            </tr>
                            <tr>
                                <td style="font-family:arial, sans-serif; color:#000; font-size:13px; padding:9px; font-weight:600">${label_receipt.total_ext_bed_charge??? then(label_receipt.total_ext_bed_charge, 'NO LABEL YET')}</td>
                            </tr>
                        </table>
                        <h6 style="font-family:Muli, Roboto, RobotoDraft, Helvetica, Arial, sans-serif; font-size:13px; font-weight:700; margin:5px 0; border-radius:5px; padding:10px 15px; text-align:right; background: rgba(3, 126, 188, 0.1)" align="right">${label_receipt.grand_total??? then(label_receipt.grand_total, 'NO LABEL YET')}</h6>
                        <h6 style="font-family:Muli, Roboto, RobotoDraft, Helvetica, Arial, sans-serif; font-size:13px; font-weight:700; margin:5px 0; border-radius:5px; padding:10px 15px; text-align:right; background: rgba(3, 126, 188, 0.1)" align="right">${label_receipt.total_charge_cc??? then(label_receipt.total_charge_cc, 'NO LABEL YET')}</h6>
                    </div>

                    <div class="right-de" style="float:right; text-align:center; width:20%" align="center" width="20%">
                        <h5 style="font-family:Muli, Roboto, RobotoDraft, Helvetica, Arial, sans-serif; font-size:14px; font-weight:700; margin:0 0 10px 0; padding-bottom:10px; text-align:center; border-radius:5px; padding:7px; background: rgba(3, 126, 188, 0.1)" align="center">${label_receipt.amount??? then(label_receipt.amount, 'NO LABEL YET')}</h5>
                        <table class="amount-pay" style="border-radius:5px; padding:10px 0 10px; width:100%; background: rgba(3, 126, 188, 0.1)" width="100%">
                            <tr>
                                <td style="font-family:arial, sans-serif; color:#848383; font-size:13px; padding:9px">&nbsp;</td>
                            </tr>
                            <tr>
                                <td style="font-family:arial, sans-serif; color:#848383; font-size:13px; padding:9px">&nbsp;</td>
                            </tr>
                            <tr>
                                <td style="font-family:arial, sans-serif; color:#848383; font-size:13px; padding:9px">&nbsp;</td>
                            </tr>
                            <tr>
                                <td style="font-family:arial, sans-serif; color:#848383; font-size:13px; padding:9px">&nbsp;</td>
                            </tr>
                            <tr>
                                <td style="font-family:arial, sans-serif; color:#848383; font-size:13px; padding:9px">&nbsp;</td>
                            </tr>
                            <tr class="room-charge">
                                <td style="font-family:arial, sans-serif; color:#676767; font-size:13px; padding:9px; border-radius:5px; background: rgba(3, 126, 188, 0.3)">${label_receipt.usd??? then(label_receipt.usd, 'NO LABEL YET')} ${data.totalRoomCharges}</td>
                            </tr>
                            <tr class="room-charge">
                                <td style="font-family:arial, sans-serif; color:#676767; font-size:13px; padding:9px; border-radius:5px; background: rgba(3, 126, 188, 0.3)">${label_receipt.usd??? then(label_receipt.usd, 'NO LABEL YET')} ${data.totalExtraBedCharges}</td>
                            </tr>
                        </table>
                        <h6 style="font-family:Muli, Roboto, RobotoDraft, Helvetica, Arial, sans-serif; font-size:13px; font-weight:700; margin:5px 0; border-radius:5px; padding:10px 15px; text-align:center;background: rgba(3, 126, 188, 0.3)" align="center">${label_receipt.usd??? then(label_receipt.usd, 'NO LABEL YET')} ${data.grandTotal}</h6>
                        <h6 style="font-family:Muli, Roboto, RobotoDraft, Helvetica, Arial, sans-serif; font-size:13px; font-weight:700; margin:5px 0; border-radius:5px; padding:10px 15px; text-align:center;background: rgba(3, 126, 188, 0.3)" align="center">${label_receipt.usd??? then(label_receipt.usd, 'NO LABEL YET')} ${data.totalPaidToCreditCard}</h6>
                    </div>
                </div>
                <div class="signature-stamp" style="display:inline-block; margin:30px 0 0 0; text-align:center; width:100%" align="center" width="100%">
                        <span class="pull-right" style="border:1px solid #f5f5f5; border-radius:5px; float:right; padding:10px; width:250px" width="250">
                            <img src="https://dev-hotels-v1.s3.amazonaws.com/uploads/mail/stamp.png" alt="" style="width:150px" width="150">
                            <h4 style="font-family:Muli, Roboto, RobotoDraft, Helvetica, Arial, sans-serif; margin-top:0; font-size:14px; margin:10px 0 0">${label_receipt.auth_signature??? then(label_receipt.auth_signature, 'NO LABEL YET')}</h4>
                        </span>
                    <p style="font-family:Muli, Roboto, RobotoDraft, Helvetica, Arial, sans-serif; font-size:12px; font-weight:500; text-align:justify; color:#989898; float:right; margin-right:10px" align="justify">${label_receipt.receipt_generate??? then(label_receipt.receipt_generate, 'NO LABEL YET')}</p>
                    <hr style="border:5px solid #067ebc; display:inline-block; margin:20px 0; width:100%" width="100%">
                </div>
            </div>

        </div>
    </div>
</div>
</body>

</html>