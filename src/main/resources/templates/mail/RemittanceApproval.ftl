<!DOCTYPE html>
<html lang = "en">

<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Amount Received Confirmation</title>
  <style>
    body {
        font-family: Arial, sans-serif;
        margin: 0;
        padding: 0;
        background-color: #f4f4f4;
        }

.email-container {
width: 100%;
max-width: 600px;
margin: 0 auto;
background-color: #ffffff;
border: 1px solid #dddddd;
padding: 20px;
}

.email-header {
background-color: #007bff;
color: #ffffff;
padding: 10px;
text-align: center;
}

.email-body {
padding: 20px;
line-height: 1.6;
color: #333333;
}

.email-footer {
margin-top: 20px;
padding: 10px;
text-align: center;
font-size: 12px;
color: #777777;
}

.amount {
font-size: 24px;
font-weight: bold;
color: #007bff;
}

.details-table {
width: 100%;
border-collapse: collapse;
margin: 20px 0;
}

.details-table th,
.details-table td {
border: 1px solid #dddddd;
padding: 8px;
text-align: left;
}

.details-table th {
background-color: #f8f8f8;
}

.button {
display: inline-block;
padding: 10px 20px;
margin-top: 20px;
color: #ffffff;
background-color: #007bff;
text-decoration: none;
border-radius: 5px;
}
</style>
</head>

<body>
<div class = "email-container">
  <div class="email-header">
    <h1>Amount Received</h1>
  </div>
  <div class="email-body">
    <p>Dear ${customer},</p>
    <p>We are pleased to inform you that we have received the following amount in bank:</p>
    <p class="amount">${amount} FRS</p>
    <p>Below are the details of the transaction:</p>
    <table class="details-table">
      <tr>
        <th>Transaction ID</th>
        <td>${transactionId}</td>
      </tr>
      <tr>
        <th>Date Received</th>
        <td>${dateTime}</td>
      </tr>
      <tr>
        <th>Agent's Name</th>
        <td>${agentName}</td>
      </tr>
    </table>
    <p>If you have any questions or need further assistance, please do not hesitate to contact us.</p>
    <p>Thank you for choosing our service.</p>
    <p>Best regards,</p>
    <p>${businessName}</p>
  </div>
  <div class="email-footer">
    <p>This is an automated message, please do not reply directly to this email.</p>
    <p>${businessName} | ${address} | ${telephone}</p>
  </div>
</div>
</body>

</html>
