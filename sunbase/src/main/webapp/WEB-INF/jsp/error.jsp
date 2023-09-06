<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>Error</title>
    <!-- Add your CSS styles here -->
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f5f5f5;
            text-align: center;
        }

        .error-container {
            max-width: 400px;
            margin: 0 auto;
            padding: 20px;
            background-color: #fff;
            border: 1px solid #ddd;
            border-radius: 5px;
            box-shadow: 0px 0px 5px #aaa;
        }

        .error-heading {
            font-size: 24px;
            color: #e74c3c;
        }

        .error-message {
            font-size: 16px;
            margin-top: 20px;
        }

        .back-button {
            margin-top: 20px;
        }
    </style>
</head>

<body>
    <div class="error-container">
        <h2 class="error-heading">Error</h2>
        <p class="error-message">${error}</p>
        <a href="/" class="back-button">Back to Home</a>
    </div>
</body>

</html>
