<!DOCTYPE html>
<html>

<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta charset="UTF-8">
    <title>Voucher Receipt PDF</title>
    <style>
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
                <img src="https://dev-hotels-v1.s3.amazonaws.com/uploads/mail/logo.png" style="height:40px; float:left" height="40" align="left">
                <h3 class="pull-right" style="font-family:Muli, Roboto, RobotoDraft, Helvetica, Arial, sans-serif; color:#000; float:right; font-weight:900; margin:0 0 5px; text-align:right; width:60%" align="right" width="60%">
                    <b style="font-size:20px; font-weight:900; margin:0; color:#067ebc">${label_voucher.bk??? then(label_voucher.bk, 'NO LABEL YET')} </b>${label_voucher.confirmation??? then(label_voucher.confirmation, 'NO LABEL YET')}
                    <p style="font-family:Muli, Roboto, RobotoDraft, Helvetica, Arial, sans-serif; font-size:8px; font-weight:500; color:#989898;margin: 0;text-transform:capitalize;" align="revert">${label_voucher.bk_check_in??? then(label_voucher.bk_check_in, 'NO LABEL YET')}.</p>
                </h3>
                <hr style="border:5px solid #067ebc; display:inline-block; width:98%" width="98%">
            </div>
            <div class="bookin-id" style="display:block; padding:15px; width:95%" width="95%">
                <div class="signature-stamp" style="float:right; text-align:center; width:50%" align="center" width="50%">
                        <span class="pull-right" style="border:1px solid #f5f5f5; border-radius:5px; float:right; padding:10px; width:250px" width="250">
                            <img src="https://dev-hotels-v1.s3.amazonaws.com/uploads/mail/stamp.png" alt="" style="width:150px" width="150">
                            <h4 style="font-family:Muli, Roboto, RobotoDraft, Helvetica, Arial, sans-serif; font-size:14px; margin:10px 0 0">${label_voucher.auth_signature??? then(label_voucher.auth_signature, 'NO LABEL YET')}</h4>
                        </span>
                    <p style="font-family:Muli, Roboto, RobotoDraft, Helvetica, Arial, sans-serif; font-size:9px; font-weight:500; text-align:right; float:right; width:100%" align="right" width="100%">${label_voucher.issue_qs??? then(label_voucher.issue_qs, 'NO LABEL YET')} www.skybooking.net/support.</p>
                </div>
                <ul style="margin:0; padding:0; width:50%" width="50%">
                    <li style="font-family:Muli, Roboto, RobotoDraft, Helvetica, Arial, sans-serif; color:rgb(112, 113, 114); font-size:15px; font-weight:500; list-style:none; padding:5px; display:inline-block; margin:5px 0; width:100%" width="100%">
                        <b style="font-size:12px; font-weight:100; margin:0; float:left; width:45%" width="45%">${label_voucher.bk_id??? then(label_voucher.bk_id, 'NO LABEL YET')} :</b>
                        <span style="color:#000; float:left; font-size:12px; font-weight:900; margin-left:10px; width:50%" width="50%">${data.bookingCode}</span>
                    </li>
                    <li style="font-family:Muli, Roboto, RobotoDraft, Helvetica, Arial, sans-serif; color:rgb(112, 113, 114); font-size:15px; font-weight:500; list-style:none; padding:5px; display:inline-block; margin:5px 0; width:100%" width="100%">
                        <b style="font-size:12px; font-weight:100; margin:0; float:left; width:45%" width="45%">${label_voucher.bk_ref_no??? then(label_voucher.bk_ref_no, 'NO LABEL YET')} :</b>
                        <span style="color:#000; float:left; font-size:12px; font-weight:900; margin-left:10px; width:50%" width="50%">${data.bookingReference}</span>
                    </li>
                    <li style="font-family:Muli, Roboto, RobotoDraft, Helvetica, Arial, sans-serif; color:rgb(112, 113, 114); font-size:15px; font-weight:500; list-style:none; padding:5px; display:inline-block; margin:5px 0; width:100%" width="100%">
                        <b style="font-size:12px; font-weight:100; margin:0; float:left; width:45%" width="45%">${label_voucher.client??? then(label_voucher.client, 'NO LABEL YET')} :</b>
                        <span style="color:#000; float:left; font-size:12px; font-weight:900; margin-left:10px; width:50%" width="50%">${data.clientFullname}</span>
                    </li>
                    <li style="font-family:Muli, Roboto, RobotoDraft, Helvetica, Arial, sans-serif; color:rgb(112, 113, 114); font-size:15px; font-weight:500; list-style:none; padding:5px; display:inline-block; margin:5px 0; width:100%" width="100%">
                        <b style="font-size:12px; font-weight:100; margin:0; float:left; width:45%" width="45%">${label_voucher.member_id??? then(label_voucher.member_id, 'NO LABEL YET')} :</b>
                        <span style="color:#000; float:left; font-size:12px; font-weight:900; margin-left:10px; width:50%" width="50%">${data.clientId}</span>
                    </li>
                    <li style="font-family:Muli, Roboto, RobotoDraft, Helvetica, Arial, sans-serif; color:rgb(112, 113, 114); font-size:15px; font-weight:500; list-style:none; padding:5px; display:inline-block; margin:5px 0; width:100%" width="100%">
                        <b style="font-size:12px; font-weight:100; margin:0; float:left; width:45%" width="45%">${label_voucher.country_residence??? then(label_voucher.country_residence, 'NO LABEL YET')} :</b>
                        <span style="color:#000; float:left; font-size:12px; font-weight:900; margin-left:10px; width:50%" width="50%">${data.countryResident}</span>
                    </li>
                    <li style="font-family:Muli, Roboto, RobotoDraft, Helvetica, Arial, sans-serif; color:rgb(112, 113, 114); font-size:15px; font-weight:500; list-style:none; padding:5px; display:inline-block; margin:5px 0; width:100%" width="100%">
                        <b style="font-size:12px; font-weight:100; margin:0; float:left; width:45%" width="45%">${label_voucher.property??? then(label_voucher.property, 'NO LABEL YET')} :</b>
                        <span style="color:#000; float:left; font-size:12px; font-weight:900; margin-left:10px; width:50%" width="50%">${data.property}</span>
                    </li>
                    <li style="font-family:Muli, Roboto, RobotoDraft, Helvetica, Arial, sans-serif; color:rgb(112, 113, 114); font-size:15px; font-weight:500; list-style:none; padding:5px; display:inline-block; margin:5px 0; width:100%" width="100%">
                        <b style="font-size:12px; font-weight:100; margin:0; float:left; width:45%" width="45%">${label_voucher.addr??? then(label_voucher.addr, 'NO LABEL YET')} :</b>
                        <span style="color:#000; float:left; font-size:12px; font-weight:900; margin-left:10px; width:50%" width="50%">${data.address}</span>
                    </li>
                    <li style="font-family:Muli, Roboto, RobotoDraft, Helvetica, Arial, sans-serif; color:rgb(112, 113, 114); font-size:15px; font-weight:500; list-style:none; padding:5px; display:inline-block; margin:5px 0; width:100%" width="100%">
                        <b style="font-size:12px; font-weight:100; margin:0; float:left; width:45%" width="45%">${label_voucher.contact_number??? then(label_voucher.contact_number, 'NO LABEL YET')} :</b>
                        <span style="color:#000; float:left; font-size:12px; font-weight:900; margin-left:10px; width:50%" width="50%">${data.propertyContactNumber}</span>
                    </li>
                </ul>
                <div class="cancel-policy">
                    <p style="font-family:Muli, Roboto, RobotoDraft, Helvetica, Arial, sans-serif; font-size:13px; font-weight:500; text-align:justify; background:#e5f2f8; border-radius:5px; color:rgb(112, 113, 114); margin:0; padding:10px" align="justify">${label_voucher.cancel_policy??? then(label_voucher.cancel_policy, 'NO LABEL YET')}.
                    </p>
                </div>
                <div class="benefits-added">
                    <h4 style="font-family:Muli, Roboto, RobotoDraft, Helvetica, Arial, sans-serif; background:#e5f2f8; border-radius:5px; font-size:13px; margin:10px 0; padding:10px">${label_voucher.benefit_included??? then(label_voucher.benefit_included, 'NO LABEL YET')}:</h4>
                    <div class="pay-details" style="float:left; width:50%" width="50%">
                        <ul class="arr-val" style="margin:10px 0 0; padding:0; width:100%; display:inline-block" width="100%">
                            <li style="font-family:Muli, Roboto, RobotoDraft, Helvetica, Arial, sans-serif; color:rgb(112, 113, 114); font-size:15px; font-weight:500; list-style:none; padding:0 0; display:inline-block; margin:0; width:45%; float:left" width="45%">
                                <b style="font-size:12px; font-weight:900; margin:0; float:left; width:35%; color:#000; padding:7px 0" width="35%">${label_voucher.arrival??? then(label_voucher.arrival, 'NO LABEL YET')}:</b>
                                <span style="color:#000; float:left; font-size:11px; font-weight:900; margin-left:10px; width:57%; background:#e5f2f8; border-radius:5px; padding:7px 0; text-align:center" width="57%" align="center">${data.arrival}</span>
                            </li>
                            <li style="font-family:Muli, Roboto, RobotoDraft, Helvetica, Arial, sans-serif; color:rgb(112, 113, 114); font-size:15px; font-weight:500; list-style:none; padding:0 0; display:inline-block; margin:0; width:45%; float:left" width="45%">
                                <b style="font-size:12px; font-weight:900; margin:0; float:left; width:35%; color:#000; padding:7px 0" width="35%">${label_voucher.departure??? then(label_voucher.departure, 'NO LABEL YET')}:</b>
                                <span style="color:#000; float:left; font-size:11px; font-weight:900; margin-left:10px; width:57%; background:#e5f2f8; border-radius:5px; padding:7px 0; text-align:center" width="57%" align="center">${data.departure}</span>
                            </li>
                        </ul>
                        <h4 style="font-family:Muli, Roboto, RobotoDraft, Helvetica, Arial, sans-serif; background:#e5f2f8; border-radius:5px; font-size:13px; margin:10px 0; padding:0; background-color:#fff" bgcolor="#ffffff">${label_voucher.payment_detail??? then(label_voucher.payment_detail, 'NO LABEL YET')}:</h4>
                        <ul class="payee-method" style="margin:0; padding:0; width:100%" width="100%">
                            <li style="font-family:Muli, Roboto, RobotoDraft, Helvetica, Arial, sans-serif; color:rgb(112, 113, 114); font-size:15px; font-weight:500; list-style:none; padding:0; display:inline-block; margin:0; width:100%; text-align:left" width="100%" align="left">
                                <b style="font-size:12px; font-weight:100; margin:0; float:left; width:45%; padding:5px 0" width="45%">${label_voucher.payment_method??? then(label_voucher.payment_method, 'NO LABEL YET')} : </b>
                                <span style="color:#000; float:left; font-size:12px; font-weight:900; margin-left:10px; width:45%; background:#e5f2f8; border-radius:5px; margin:0; padding:5px 10px" width="45%">${data.paymentMethod}</span>
                            </li>
                            <li style="font-family:Muli, Roboto, RobotoDraft, Helvetica, Arial, sans-serif; color:rgb(112, 113, 114); font-size:15px; font-weight:500; list-style:none; padding:0; display:inline-block; margin:0; width:100%; text-align:left" width="100%" align="left">
                                <b style="font-size:12px; font-weight:100; margin:0; float:left; width:45%; padding:5px 0" width="45%">${label_voucher.card_no??? then(label_voucher.card_no, 'NO LABEL YET')} : </b>
                                <span style="color:#000; float:left; font-size:12px; font-weight:900; margin-left:10px; width:45%; background:#e5f2f8; border-radius:5px; margin:0; padding:5px 10px" width="45%">${data.cardNo}</span>
                            </li>
                            <li style="font-family:Muli, Roboto, RobotoDraft, Helvetica, Arial, sans-serif; color:rgb(112, 113, 114); font-size:15px; font-weight:500; list-style:none; padding:0; display:inline-block; margin:0; width:100%; text-align:left" width="100%" align="left">
                                <b style="font-size:12px; font-weight:100; margin:0; float:left; width:45%; padding:5px 0" width="45%">${label_voucher.exp??? then(label_voucher.exp, 'NO LABEL YET')} : </b>
                                <span style="color:#000; float:left; font-size:12px; font-weight:900; margin-left:10px; width:45%; background:#e5f2f8; border-radius:5px; margin:0; padding:5px 10px" width="45%">${data.expired}</span>
                            </li>
                        </ul>
                        <h4 style="font-family:Muli, Roboto, RobotoDraft, Helvetica, Arial, sans-serif; background:#e5f2f8; border-radius:5px; font-size:13px; margin:10px 0; padding:0; background-color:#fff" bgcolor="#ffffff">${label_voucher.bk_pay??? then(label_voucher.bk_pay, 'NO LABEL YET')}:</h4>
                        <p style="font-family:Muli, Roboto, RobotoDraft, Helvetica, Arial, sans-serif; font-size:11px; font-weight:500; text-align:justify; background:#e5f2f8; border-radius:5px; color:#989898; line-height:18px; margin:8px 0; padding:7px" align="justify">${label_voucher.skb_addr??? then(label_voucher.skb_addr, 'NO LABEL YET')}</p>
                        <strong style="display:block; font-size:small; font-weight:600; margin-bottom:7px">${label_voucher.remark??? then(label_voucher.remark, 'NO LABEL YET')}:</strong>
                        <strong style="display:block; font-size:11px; font-weight:600; margin-bottom:7px; line-height:1.5em; text-align:justify" align="justify">${data.remark}</strong>
                    </div>
                    <div class="num-rooms" style="background:#e5f2f8; border-radius:5px; float:right; margin-left:1%; padding:0 10px 10px; width:45%" width="45%">
                        <div class="room-count" style="margin:10px 0; padding:6px 0">
                            <span style="color:rgb(112, 113, 114); display:inline-block; font-size:13px; width:52%" width="52%">${label_voucher.num_rm??? then(label_voucher.num_rm, 'NO LABEL YET')} :</span>
                            <b style="font-size:13px; font-weight:600; margin:0; background:white; border-radius:3px; display:inline-block; text-align:center; width:45%" align="center" width="45%">${data.numRooms}</b>
                        </div>
                        <div class="room-count" style="margin:10px 0; padding:6px 0">
                            <span style="color:rgb(112, 113, 114); display:inline-block; font-size:13px; width:52%" width="52%">${label_voucher.num_ext_bed??? then(label_voucher.num_ext_bed, 'NO LABEL YET')} :</span>
                            <b style="font-size:13px; font-weight:600; margin:0; background:white; border-radius:3px; display:inline-block; text-align:center; width:45%" align="center" width="45%">${data.numExtraBed}</b>
                        </div>
                        <div class="room-count" style="margin:10px 0; padding:6px 0">
                            <span style="color:rgb(112, 113, 114); display:inline-block; font-size:13px; width:52%" width="52%">${label_voucher.num_adult??? then(label_voucher.num_adult, 'NO LABEL YET')} :</span>
                            <b style="font-size:13px; font-weight:600; margin:0; background:white; border-radius:3px; display:inline-block; text-align:center; width:45%" align="center" width="45%">${data.numAdult}</b>
                        </div>
                        <div class="room-count" style="margin:10px 0; padding:6px 0">
                            <span style="color:rgb(112, 113, 114); display:inline-block; font-size:13px; width:52%" width="52%">${label_voucher.num_ch??? then(label_voucher.num_ch, 'NO LABEL YET')} :</span>
                            <b style="font-size:13px; font-weight:600; margin:0; background:white; border-radius:3px; display:inline-block; text-align:center; width:45%" align="center" width="45%">${data.numChildren}</b>
                        </div>
                        <div class="room-count" style="margin:10px 0; padding:6px 0">
                            <span style="color:rgb(112, 113, 114); display:inline-block; font-size:13px; width:52%" width="52%">${label_voucher.rm_type??? then(label_voucher.rm_type, 'NO LABEL YET')} :</span>
                            <b style="font-size:13px; font-weight:600; margin:0; background:white; border-radius:3px; display:inline-block; text-align:center; width:45%" align="center" width="45%">${data.roomType}</b>
                        </div>
                        <div class="room-count" style="margin:10px 0; padding:6px 0">
                            <span style="color:rgb(112, 113, 114); display:inline-block; font-size:13px; width:52%" width="52%">${label_voucher.promotion??? then(label_voucher.promotion, 'NO LABEL YET')} :</span>
                            <b style="font-size:13px; font-weight:600; margin:0; background:white; border-radius:3px; display:inline-block; text-align:center; width:45%" align="center" width="45%">${data.numPromotion}</b>
                        </div>
                        <small style="color:#989898; font-size:11px">${label_voucher.promotion_detail??? then(label_voucher.promotion_detail, 'NO LABEL YET')}</small>
                    </div>
                </div>
                <div class="note-help" style="background:#e5f2f8; border-radius:5px; display:inline-block; margin:10px 0; padding:10px">
                    <b style="font-size:15px; font-weight:600; margin:0">${label_voucher.note??? then(label_voucher.note, 'NO LABEL YET')}</b>
                    <ul style="margin:0; padding:0; width:100%" width="100%">
                        <li style="font-family:Muli, Roboto, RobotoDraft, Helvetica, Arial, sans-serif; color:rgb(112, 113, 114); font-size:13px; font-weight:500; list-style:disc; padding:5px; display:inline-block; margin:0; width:100%; line-height:20px; text-align:justify" width="100%" align="justify">
                            <strong style="color: #067ebc;">${label_voucher.important??? then(label_voucher.important, 'NO LABEL YET')}</strong> ${label_voucher.check_in_policy??? then(label_voucher.check_in_policy, 'NO LABEL YET')}.</li>
                        <li style="font-family:Muli, Roboto, RobotoDraft, Helvetica, Arial, sans-serif; color:rgb(112, 113, 114); font-size:13px; font-weight:500; list-style:disc; padding:5px; display:inline-block; margin:0; width:100%; line-height:20px; text-align:justify" width="100%" align="justify">${label_voucher.rm_policy??? then(label_voucher.rm_policy, 'NO LABEL YET')}.</li>
                        <li style="font-family:Muli, Roboto, RobotoDraft, Helvetica, Arial, sans-serif; color:rgb(112, 113, 114); font-size:13px; font-weight:500; list-style:disc; padding:5px; display:inline-block; margin:0; width:100%; line-height:20px; text-align:justify" width="100%" align="justify">${label_voucher.total_price_policy??? then(label_voucher.total_price_policy, 'NO LABEL YET')}.</li>
                        <li style="font-family:Muli, Roboto, RobotoDraft, Helvetica, Arial, sans-serif; color:rgb(112, 113, 114); font-size:13px; font-weight:500; list-style:disc; padding:5px; display:inline-block; margin:0; width:100%; line-height:20px; text-align:justify" width="100%" align="justify">${label_voucher.facility_detail??? then(label_voucher.facility_detail, 'NO LABEL YET')}.</li>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</div>
</body>

</html>