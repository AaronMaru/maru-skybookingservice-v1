<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <style type="text/css">

        h1, h2, h3, h4, h5, h6, p, a, li {
            font-family: Muli,Roboto,RobotoDraft,Helvetica,Arial,sans-serif;
        }
        body, td, input, textarea, select {
            font-family: arial,sans-serif;
        }
        .logo-sec {
            width: auto;
            margin: 0 auto;
            max-width: 800px;
            background: #fff;
        }
        .logo-sec h2 {
            font-size: 17px;
            margin: 10px 0;
        }
        .back-logo {
            text-align: left;
        }
        .back-logo img {
            height: 55px;
            margin: 10px 0 15px;
        }
        .logo-sec hr {
            clear: both;
            margin: 5px 0px;
        }
        .billing-booking {
            padding: 10px 20px;
            display: inline-block;
            width: 95%;
        }
        .billing-details {
            width: 100%;
            display: inline-block;
        }
        .billing-details ul {
            padding: 0;
        }
        .billing-details ul li {
            list-style: none;
            margin: 5px 0;
            font-size: 14px;
            color: rgb(112, 113, 114);
            width: 100%;
            display: inline-block;
        }
        .billing-details ul li b {
            float: left;
            width: 30%;
        }
        .billing-details ul li span {
            float: left;
            width: 70%;
        }
        .billing-details h2 {
            font-size: 17px;
            margin: 10px 0;
        }
        .pull-right {
            float: right;
        }
        .billing-receipt h2 {
            float: left;
            font-size: 20px;
            color: #067ebc;
            margin: 0;
        }
        .billing-receipt h3 {
            margin: 0;
            text-align: right;
        }
        .billing-receipt p {
            margin: 5px 0 0 0;
            font-style: italic;
            color: #989898;
            font-size: 14px;
        }
        .line-head h3 {
            margin-bottom: 0;
            position: relative;
            font-size: 17px;
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
        .line-head table {
            width: 100%;
            text-align: left;
            border-collapse: collapse;
            margin: 20px 0;
        }
        .line-head table tr th, .line-head table tr td {
            border: 1px solid #ccc;
            padding: 13px;
            border-radius: 5px;
            font-size: 14px;
        }
        .line-head table tr td:last-child, .line-head table tr th:last-child {
            text-align: right;
        }
        .line-head table tr td {
            color: #989898;
        }
        .line-head table tr.total-points td {
            text-align: right;
            color: #000;
            font-weight: 600;
        }
        /* .pay-sum ul {
            margin-bottom: 0;
        }
        .pay-sum ul li {
            margin: 0;
        } */
        .ins-area ol {padding: 0 15px;}
        .ins-area ol li {
            margin: 10px 0;
            font-size: 12px;
            color: #989898;
        }
        .ins-area h2 {
            border-bottom: 1px solid #ccc;
            padding-bottom: 5px;
        }
        .footer-area {
            display: inline-block;
            width: 100%;
        }
        .your-records {
            width: 50%;
            float: left;
        }
        .cont-us {
            width: 50%;
            float: right;
            text-align: right;
            margin-top: 30px;
        }
        .cont-us img {
            width: 18px;
            margin: -4px 5px;
        }
        .cont-us p {
            margin: 10px 0 0;
            font-size: 13px;
            color: #989898;
        }
        .cont-us b {
            font-size: 14px;
        }
        .your-records h2 {
            margin: 10px 0 0;
            color: #067ebc;
        }
        .your-records p {
            font-size: 13px;
            color: #989898;
            width: 80%;
            margin: 10px 0 0 0;
        }

        span.copy-right {
            display: inline-block;
            margin: 20px 0 15px;
            font-style: italic;
            font-size: 13px;
            width: 100%;
            color: #989898;
        }
    </style>
</head>

<body>
<div class="container">
    <div class="logo-sec">
        <div class="back-logo">
            <img src="https://s3.amazonaws.com/skybooking/uploads/mail/images/logo.png"/>
        </div>
        <hr />
        <hr />

        <div class="billing-booking">
            <div class="billing-receipt">
                <h2>${label_topUp.e_receipt??? then(label_topUp.e_receipt, 'NO LABEL YET')}</h2>
                <div class="pull-right">
                    <h3>${label_topUp.top_up_confirmed??? then(label_topUp.top_up_confirmed, 'NO LABEL YET')}</h3>
                    <p>${label_topUp.top_up_thank??? then(label_topUp.top_up_thank, 'NO LABEL YET')}</p>
                </div>
            </div>
            <div class="billing-details">
                <h2>${label_topUp.issue_for??? then(label_topUp.issue_for, 'NO LABEL YET')} :</h2>
                <ul>
                    <li>
                        <b>${label_topUp.company_traveler_name??? then(label_topUp.company_traveler_name, 'NO LABEL YET')} :</b>
                        <span>${data.fullName}</span>
                    </li>
                    <li>
                        <b>${label_topUp.transaction_id??? then(label_topUp.transaction_id, 'NO LABEL YET')} :</b>
                        <span>${data.transactionId}</span>
                    </li>
                    <li>
                        <b>${label_topUp.email??? then(label_topUp.email, 'NO LABEL YET')} :</b>
                        <span>${data.email}</span>
                    </li>
                    <li>
                        <b>${label_topUp.transaction_date??? then(label_topUp.transaction_date, 'NO LABEL YET')} :</b>
                        <span>${data.transactionDate?datetime("yyyy-MM-dd'T'HH:mm:ssXXX")?string["EEE, dd MMM YYYY HH:mm"]} </span>
                    </li>
                </ul>
            </div>
        </div>
        <div class="line-head">
            <h3>${label_topUp.skypoint_detail??? then(label_topUp.skypoint_detail, 'NO LABEL YET')}</h3>
            <table>
                <tbody>
                <tr>
                    <th>${label_topUp.description??? then(label_topUp.description, 'NO LABEL YET')}</th>
                    <th>${label_topUp.amount??? then(label_topUp.amount, 'NO LABEL YET')}</th>
                </tr>
                <tr>
                    <td>${label_topUp.top_up_skypoint??? then(label_topUp.top_up_skypoint, 'NO LABEL YET')}</td>
                    <td>${data.amount}pts</td>
                </tr>
                <tr>
                    <td>${label_topUp.earn_extra_point??? then(label_topUp.earn_extra_point, 'NO LABEL YET')}</td>
                    <td>${data.earnAmount}pts</td>
                </tr>
                <tr class="total-points">
                    <td>${label_topUp.total_point??? then(label_topUp.total_point, 'NO LABEL YET')}</td>
                    <td>${data.amount + data.earnAmount}pts</td>
                </tr>
                </tbody>
            </table>
        </div>
        <div class="billing-details pay-sum">
            <h2>${label_topUp.payment_summary??? then(label_topUp.payment_summary, 'NO LABEL YET')}</h2>
            <ul>
                <li>
                    <b>${label_topUp.total_paid_amount??? then(label_topUp.total_paid_amount, 'NO LABEL YET')} :</b>
                    <span>USD ${data.amount}</span>
                </li>
            </ul>
        </div>
        <div class="ins-area">
            <h2>${label_topUp.instruction??? then(label_topUp.instruction, 'NO LABEL YET')}</h2>
            <ol className="ul-instuction">
                ${label_topUp.instruction_des??? then(label_topUp.instruction_des, 'NO LABEL YET')}
            </ol>
        </div>
        <hr>
        <hr>
        <div class="footer-area">
            <div class="your-records">
                <h2>${label_topUp.please_retain_record??? then(label_topUp.please_retain_record, 'NO LABEL YET')}</h2>
                <p>${label_topUp.address??? then(label_topUp.address, 'NO LABLE YET')}</p>
            </div>
            <div class="cont-us">
                <b> ${label_topUp.call_customer_service??? then(label_topUp.call_customer_service, 'NO LABEL YET')}</b>
                <p><img src="https://s3.amazonaws.com/skybooking/uploads/mail/images/phone.png">(+855) 98888100 / (+855) 98888200</p>
            </div>
            <span class="copy-right">${label_topUp.copy_right??? then(label_topUp.copy_right, 'NO LABEL YET')} &copy; <script>document.write(new Date().getFullYear())</script> ${label_topUp.skybooking_reserved??? then(label_topUp.skybooking_reserved, 'NO LABEL YET')}</span>
        </div>
    </div>
</div>
</body>

</html>