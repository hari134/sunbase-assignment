package com.hari134.sunbase.controllers;

import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String loginPage() {
        return "login"; // Return the login page (login.jsp)
    }



    @PostMapping("/login")
    public void authenticateUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String loginId = request.getParameter("login_id");
        String password = request.getParameter("password");
        String apiUrl = "https://qa2.sunbasedata.com/sunbase/portal/api/assignment_auth.jsp";
        JSONObject requestBody = new JSONObject();
        requestBody.put("login_id", loginId);
        requestBody.put("password", password);

        // Create a RestTemplate to make the API call
        RestTemplate restTemplate = new RestTemplate();

        // Make the POST request to authenticate the user
        ResponseEntity<String> apiResponse = restTemplate.postForEntity(apiUrl, requestBody.toString(), String.class);

        // Check the API response
        if (apiResponse.getStatusCode() == HttpStatus.OK) {
            // Authentication successful, store the token in local storage
            JSONObject jsonResponse = new JSONObject(apiResponse.getBody());
            String token = jsonResponse.getString("access_token");
            request.getSession().setAttribute("token",token);
            response.sendRedirect("/customer-list");
        } else {
            // Authentication failed, redirect back to login page with an error message
            response.sendRedirect("/login?error=1");
        }
    }
}
