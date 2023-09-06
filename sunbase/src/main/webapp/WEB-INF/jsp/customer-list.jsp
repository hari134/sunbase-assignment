<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>Customer List</title>

    <!-- Add Font Awesome stylesheet for icons -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">

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

        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }

        table, th, td {
            border: 1px solid #ddd;
        }

        th, td {
            padding: 10px;
            text-align: left;
        }

        th {
            background-color: #333;
            color: #fff;
        }

        a.btn {
            display: inline-block;
            padding: 10px 20px;
            background-color: #007bff;
            color: #fff;
            text-decoration: none;
            border-radius: 4px;
        }

        a.btn:hover {
            background-color: #0056b3;
        }

        form button {
            background-color: #dc3545;
            color: #fff;
            border: none;
            padding: 5px 10px;
            cursor: pointer;
            margin-right: 10px;
            border-radius: 4px;
        }

        form button.update-button {
            background-color: #28a745; /* Green color for update */
        }

        form button.delete-button {
            background-color: #dc3545; /* Red color for delete */
        }

        form button:hover {
            background-color: #c82333;
        }

        /* Add CSS styles for the Font Awesome icons */
        .fa-trash-alt {
            color: #fff;
        }
    </style>
</head>

<body>
    <h1>Customer List</h1>

    <a href="/create-customer" class="btn">Add Customer</a>

    <table>
        <tr>
            <th>First Name</th>
            <th>Last Name</th>
            <th>Street</th>
            <th>Address</th>
            <th>City</th>
            <th>State</th>
            <th>Email</th>
            <th>Phone</th>
            <th>Action</th>
        </tr>
        <c:forEach items="${customers}" var="customer">
            <tr>
                <td>${customer.firstName}</td>
                <td>${customer.lastName}</td>
                <td>${customer.street}</td>
                <td>${customer.address}</td>
                <td>${customer.city}</td>
                <td>${customer.state}</td>
                <td>${customer.email}</td>
                <td>${customer.phone}</td>
                <td>
                    <form action="/delete-customer" method="post">
                        <input type="hidden" name="uuid" value="${customer.uuid}">
                        <!-- Use Font Awesome icon for delete -->
                        <button type="submit" class="delete-button"><i class="far fa-trash-alt"></i></button>
                    </form>
                    <form action="/update-customer" method="get">
                        <input type="hidden" name="uuid" value="${customer.uuid}">
                        <!-- Use Font Awesome icon for update -->
                        <button type="submit" class="update-button"><i class="fas fa-edit"></i></button>
                    </form>
                </td>
            </tr>
        </c:forEach>
    </table>

</body>

</html>
