package com.hari134.sunbase.controllers;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import java.util.ArrayList;
import java.util.List;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import com.hari134.sunbase.models.Customer;

import jakarta.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

@Controller
public class CustomerController {

    @GetMapping("/")
    public String redirectToLogin() {
        return "redirect:/login";
    }

    @GetMapping("/customer-list")
    public String getCustomerList(Model model, HttpSession session) {
        // Retrieve the authentication token from the session
        String token = (String) session.getAttribute("token");
        if (token != null) {
            // Call the method to get the customer list using the token
            List<Customer> customers = getCustomerListFromAPI(token);
            if (customers != null) {
                // Add the customer list to the model
                model.addAttribute("customers", customers);
                return "customer-list"; // Return the customer-list.jsp view
            } else {
                // Handle the case where customer retrieval fails
                model.addAttribute("error", "Failed to retrieve customer data.");
                return "error"; // You can create an error.jsp for error handling
            }
        } else {
            // Token is not available, redirect to the login page
            return "redirect:/login";
        }
    }

    @GetMapping("/create-customer")
    public String loginPage() {
        return "create-customer";
    }

    @PostMapping("/create-customer")
    public String createCustomer(
            @RequestParam("first_name") String firstName,
            @RequestParam("last_name") String lastName,
            @RequestParam("street") String street,
            @RequestParam("address") String address,
            @RequestParam("city") String city,
            @RequestParam("state") String state,
            @RequestParam("email") String email,
            @RequestParam("phone") String phone,
            Model model,
            HttpSession session) {
        // Retrieve the authentication token from the session
        String token = (String) session.getAttribute("token");
        if (token != null) {
            try {
                // Build the API URL
                String apiURL = "https://qa2.sunbasedata.com/sunbase/portal/api/assignment.jsp?cmd=create";

                // Create a RestTemplate instance
                RestTemplate restTemplate = new RestTemplate();

                // Set the request headers including Authorization
                HttpHeaders headers = new HttpHeaders();
                headers.set("Authorization", "Bearer " + token);

                // Create a JSON object representing the customer data
                JSONObject customerJson = new JSONObject();
                customerJson.put("first_name", firstName);
                customerJson.put("last_name", lastName);
                customerJson.put("street", street);
                customerJson.put("address", address);
                customerJson.put("city", city);
                customerJson.put("state", state);
                customerJson.put("email", email);
                customerJson.put("phone", phone);

                // Create the HTTP entity with the request body and headers
                HttpEntity<String> requestEntity = new HttpEntity<>(customerJson.toString(), headers);

                // Send the POST request
                ResponseEntity<String> responseEntity = restTemplate.exchange(apiURL, HttpMethod.POST, requestEntity,
                        String.class);

                // Check the response status code
                HttpStatusCode statusCode = responseEntity.getStatusCode();
                if (statusCode == HttpStatus.CREATED) {
                    // Customer created successfully
                    return "redirect:/customer-list"; // Redirect to the customer list page
                } else if (statusCode == HttpStatus.BAD_REQUEST) {
                    // Handle the case where First Name or Last Name is missing
                    model.addAttribute("error", "First Name or Last Name is missing.");
                    return "error"; // You can create an error.jsp for error handling
                } else {
                    // Handle other error cases
                    model.addAttribute("error", "Failed to create a new customer.");
                    return "error"; // You can create an error.jsp for error handling
                }
            } catch (Exception e) {
                e.printStackTrace();
                // Handle exceptions
                model.addAttribute("error", "Failed to create a new customer.");
                return "error"; // You can create an error.jsp for error handling
            }
        } else {
            // Token is not available, redirect to the login page
            return "redirect:/login";
        }
    }

    @PostMapping("/delete-customer")
    public String deleteCustomer(
            @RequestParam("uuid") String uuid,
            Model model,
            HttpSession session) {
        // Retrieve the authentication token from the session
        String token = (String) session.getAttribute("token");
        if (token != null) {
            try {
                // Build the API URL
                String apiURL = "https://qa2.sunbasedata.com/sunbase/portal/api/assignment.jsp?cmd=delete&uuid=" + uuid;

                // Create a RestTemplate instance
                RestTemplate restTemplate = new RestTemplate();

                // Set the request headers including Authorization
                HttpHeaders headers = new HttpHeaders();
                headers.set("Authorization", "Bearer " + token);

                // Create the HTTP entity with the headers
                HttpEntity<String> requestEntity = new HttpEntity<>(headers);

                // Send the POST request
                ResponseEntity<String> responseEntity = restTemplate.exchange(apiURL, HttpMethod.POST, requestEntity,
                        String.class);

                // Check the response status code and handle accordingly
                int statusCode = responseEntity.getStatusCodeValue();
                if (statusCode == 200) {
                    // Customer deleted successfully
                    return "redirect:/customer-list"; // Redirect to the customer list page
                } else if (statusCode == 400) {
                    // UUID not found
                    model.addAttribute("error", "UUID not found.");
                } else if (statusCode == 500) {
                    // Error during deletion
                    model.addAttribute("error", "Error during deletion. Customer not deleted.");
                } else {
                    // Handle other status codes as needed
                    model.addAttribute("error", "An error occurred. Customer not deleted.");
                }

                return "error"; // You can create an error.jsp for error handling
            } catch (Exception e) {
                e.printStackTrace();
                // Handle exceptions
                model.addAttribute("error", "Failed to delete the customer.");
                return "error"; // You can create an error.jsp for error handling
            }
        } else {
            // Token is not available, redirect to the login page
            return "redirect:/login";
        }
    }

    @GetMapping("/update-customer")
    public String getUpdateCustomerPage(
            @RequestParam("uuid") String uuid,
            Model model,
            HttpSession session) {
        // Retrieve the authentication token from the session
        String token = (String) session.getAttribute("token");

        if (token != null) {
            List<Customer> customers = getCustomerListFromAPI(token);

            if (customers != null) {
                // Find the customer in the list by UUID
                for (Customer customer : customers) {
                    if (customer.getUuid().equals(uuid)) {
                        // Pass the customer object to the update-customer.jsp page
                        model.addAttribute("customer", customer);
                        return "update-customer"; // Create an update-customer.jsp for updating customer details
                    }
                }
            }
        }

        // Handle errors or redirection if necessary
        model.addAttribute("error", "Customer not found for update.");
        return "error";
    }

    @PostMapping("/update-customer")
    public String updateCustomer(
            @RequestParam("uuid") String uuid,
            @RequestParam("first_name") String firstName,
            @RequestParam("last_name") String lastName,
            @RequestParam("street") String street,
            @RequestParam("address") String address,
            @RequestParam("city") String city,
            @RequestParam("state") String state,
            @RequestParam("email") String email,
            @RequestParam("phone") String phone,
            Model model,
            HttpSession session) {
        // Retrieve the authentication token from the session
        String token = (String) session.getAttribute("token");

        if (token != null) {
            try {
                // Build the API URL
                String apiURL = "https://qa2.sunbasedata.com/sunbase/portal/api/assignment.jsp?cmd=update&uuid=" + uuid;

                // Create a RestTemplate instance
                RestTemplate restTemplate = new RestTemplate();

                // Set the request headers, including Authorization
                HttpHeaders headers = new HttpHeaders();
                headers.set("Authorization", "Bearer " + token);

                // Create a JSON object representing the customer data
                JSONObject customerJson = new JSONObject();
                customerJson.put("first_name", firstName);
                customerJson.put("last_name", lastName);
                customerJson.put("street", street);
                customerJson.put("address", address);
                customerJson.put("city", city);
                customerJson.put("state", state);
                customerJson.put("email", email);
                customerJson.put("phone", phone);

                // Create the HTTP entity with the request body and headers
                HttpEntity<String> requestEntity = new HttpEntity<>(customerJson.toString(), headers);

                // Send the POST request
                ResponseEntity<String> responseEntity = restTemplate.exchange(
                        apiURL,
                        HttpMethod.POST,
                        requestEntity,
                        String.class);

                // Check the response status code
                HttpStatusCode statusCode = responseEntity.getStatusCode();
                if (statusCode == HttpStatus.OK) {
                    // Customer updated successfully
                    return "redirect:/customer-list"; // Redirect to the customer list page
                } else if (statusCode == HttpStatus.NOT_FOUND) {
                    // UUID not found
                    model.addAttribute("error", "UUID not found.");
                } else if (statusCode == HttpStatus.BAD_REQUEST) {
                    // Body is empty
                    model.addAttribute("error", "Body is empty.");
                } else {
                    // Handle other status codes if needed
                    model.addAttribute("error", "Failed to update the customer.");
                }
            } catch (Exception e) {
                e.printStackTrace();
                // Handle exceptions
                model.addAttribute("error", "Failed to update the customer.");
            }
        } else {
            // Token is not available, redirect to the login page
            return "redirect:/login";
        }

        // Handle errors or redirection if necessary
        return "error";
    }

    private List<Customer> getCustomerListFromAPI(String token) {
        try {
            // Build the API URL with the token
            String apiURL = "https://qa2.sunbasedata.com/sunbase/portal/api/assignment.jsp?cmd=get_customer_list";
            URL url = new URL(apiURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Authorization", "Bearer " + token);

            // Check for HTTP OK (200) response code
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Read and return the JSON response
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                return parseCustomerList(response.toString());
            } else {
                // Handle error cases
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private List<Customer> parseCustomerList(String json) {
        List<Customer> customers = new ArrayList<>();

        try {
            // Parse the JSON array
            JSONArray jsonArray = new JSONArray(json);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                // Extract customer data from the JSON object
                String uuid = jsonObject.getString("uuid");
                String firstName = jsonObject.getString("first_name");
                String lastName = jsonObject.getString("last_name");
                String street = jsonObject.getString("street");
                String address = jsonObject.getString("address");
                String city = jsonObject.getString("city");
                String state = jsonObject.getString("state");
                String email = jsonObject.getString("email");
                String phone = jsonObject.getString("phone");

                // Create a Customer object and add it to the list
                Customer customer = new Customer(uuid, firstName, lastName, street, address, city, state, email, phone);
                customers.add(customer);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            // Handle the JSON parsing error here (e.g., log it or return an empty list)
        }

        return customers;
    }

}
