<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Topup Receipt PDF</title>
    <style>
        .container-fluid {
            background: #fff;
            padding-right: 0px;
            padding-left: 0px;
        }
        table.topup-table,
        th,
        td {
            width: 100%;
            border-radius: 5px;
            margin: 15px 0;
            overflow: hidden;
            border-spacing: 0;
            border: 1px solid #ccc;
            border-collapse: inherit;
            font-size: 13px;
        }

        table.topup-table p {
            margin: 6px 0px;
        }
        table.topup-table td .skybooking-logo {
            width: 200px;
        }

        table.topup-table .topup-title {
            text-align: center;
        }

        th,
        td {
            padding: 15px;
        }

        ul.list-topup-info {
            margin: 0px;
            padding: 0px;
            list-style-type: none;
        }

        ul.list-topup-info li {
            display: inline-block;
        }

        ul.list-topup-info li.list-value {
            margin-left: 25px;
        }
    </style>
</head>

<body>
<div class="container-fluid">
    <table class="topup-table">
        <tr>
            <td>
                <img src="https://s3.amazonaws.com/skybooking/uploads/mail/images/logo.png"/>
                <div class="topup-title">
                    <h2>${label_topUp.header??? then(label_topUp.header, 'NO LABEL YET')}</h2>
                    <p>${label_topUp.description??? then(label_topUp.description, 'NO LABEL YET')}</p>
                </div><br />
                <p><b>${label_topUp.issued_for??? then(label_topUp.issued_for, 'NO LABEL YET')}:</b></p>
                <p>${label_topUp.name??? then(label_topUp.name, 'NO LABEL YET')}: ${data.fullName}</p>
                <p>${label_topUp.contact??? then(label_topUp.contact, 'NO LABEL YET')}: ${data.email}/${data.phone}</p><br />

                <p><b>${label_topUp.transaction_detail??? then(label_topUp.transaction_detail, 'NO LABEL YET')}</b></p>
                <ul class="list-topup-info">
                    <li>
                        <p>${label_topUp.total_payment??? then(label_topUp.total_payment, 'NO LABEL YET')}:</p>
                        <p>${label_topUp.point_received??? then(label_topUp.point_received, 'NO LABEL YET')}:</p>
                        <p>${label_topUp.transaction_id??? then(label_topUp.transaction_id, 'NO LABEL YET')}:</p>
                        <p>${label_topUp.transaction_date??? then(label_topUp.transaction_date, 'NO LABEL YET')}:</p>
                    </li>
                    <li class="list-value">
                        <p>USD ${data.amount}</p>
                        <p>${data.amount + data.earnAmount}pts</p>
                        <p>${data.transactionId}</p>
                        <p>${data.transactionDate?datetime("yyyy-MM-dd'T'HH:mm:ssXXX")?string["EEE, dd MMM YYYY HH:mm"]}</p>
                    </li>
                </ul>
            </td>
        </tr>
        <tr>
            <td>
                <p>Please retain for your records.</p>
                <p>Office adress: Oval office Tower, Floor 11 #1, St. 360, BKK, Khan Chamkarmon</p>
                <p>Copyright @ 2020 Skybooking Co., Ltd All rights reserved.</p>
            </td>
        </tr>
    </table>
</div>
</body>

</html>