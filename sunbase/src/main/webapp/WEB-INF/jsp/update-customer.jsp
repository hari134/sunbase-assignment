<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>Update Customer</title>

    <style>
        /* CSS styles for beautifying the page */
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 0;
        }

        h1 {
            background-color: #333;
            color: #fff;
            padding: 20px;
            text-align: center;
        }

        form {
            background-color: #fff;
            max-width: 400px;
            margin: 20px auto;
            padding: 20px;
            border-radius: 4px;
            box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.2);
        }

        label {
            display: block;
            margin-bottom: 10px;
        }

        input[type="text"],
        input[type="email"] {
            width: 100%;
            padding: 10px;
            margin-bottom: 20px;
            border: 1px solid #ccc;
            border-radius: 4px;
        }

        input[type="submit"] {
            background-color: #007bff;
            color: #fff;
            padding: 10px 20px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }

        input[type="submit"]:hover {
            background-color: #0056b3;
        }
    </style>
</head>

<body>
    <h1>Update Customer</h1>

    <form action="/update-customer" method="POST">
        <input type="hidden" name="uuid" value="${customer.uuid}" /> <!-- Include the UUID as a hidden field -->
        <label for="first_name">First Name:</label>
        <input type="text" id="first_name" name="first_name" value="${customer.firstName}" required />

        <label for="last_name">Last Name:</label>
        <input type="text" id="last_name" name="last_name" value="${customer.lastName}" required />

        <label for="street">Street:</label>
        <input type="text" id="street" name="street" value="${customer.street}" required />

        <label for="address">Address:</label>
        <input type="text" id="address" name="address" value="${customer.address}" required />

        <label for="city">City:</label>
        <input type="text" id="city" name="city" value="${customer.city}" required />

        <label for="state">State:</label>
        <input type="text" id="state" name="state" value="${customer.state}" required />

        <label for="email">Email:</label>
        <input type="email" id="email" name="email" value="${customer.email}" required />

        <label for="phone">Phone:</label>
        <input type="text" id="phone" name="phone" value="${customer.phone}" required />

        <input type="submit" value="Update Customer">
    </form>
</body>

</html>
