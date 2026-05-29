package com.example.HospitalManaSystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HospitalManaSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(HospitalManaSystemApplication.class, args);
	}

}

//UsernamePasswordAuthenticationToken is an implementation of Spring
// Security’s Authentication interface in Spring Security.
//
//It represents the authenticated (or unauthenticated) user.
//
//Simple Meaning
//
//It is an object that stores:
//
//user/principal
//password/credentials
//roles/authorities
//authentication status

//currently, when you signup, your credential save to DB & again with
//same credential you need to login
//overall, jwt is generate at the time of login, not the time of signUp

// /logout pr by default logout page by spring security
// JSESSIONID is a unique session identifier to track a user’s session.
// It is stored as a cookie in the browser.

//Spring Security Flow
//Client → Login → Spring Security authenticates → Session created
//       ← JSESSIONID cookie returned
//
//Client → Sends JSESSIONID → Server fetches session → User is already authenticated
//
//👉 So user doesn’t need to login again for every request

//"JSESSIONID is a session identifier stored in a cookie that allows the server to recognize" +
//        " and authenticate a user across multiple requests in a stateful manner."

/*
Servlet filters are not specifically about security, they are general-purpose generic request/response interceptors
It’s a chain of filters that every HTTP request passes through before reaching your controller.
✅ Common use cases:

1. Logging

👉 Purpose: Track what’s happening in your application

What you log:
Request URL
HTTP method (GET, POST)
Headers
Response time

Example:
Request: GET /admin/patients
Time taken: 120ms

2. Request/Response Modification

👉 You can intercept and change data

Examples:
Modify request body
Change response before sending

👉 Use case:

Mask sensitive data
Add extra info

3. Compression (GZIP)

👉 Reduce response size

Without compression:
Response size = 500KB
With GZIP:
Response size = 50KB

👉 How?
Filter compresses response before sending

👉 Benefit:

Faster APIs
Less bandwidth usage

4. IP Filtering / Rate Limiting
👉 IP Filtering:
Allow/block specific IPs

Example:

Allow: 192.168.1.1
Block: 123.45.67.89
👉 Rate Limiting:
Limit number of requests

Example:

Max 100 requests per minute

👉 Why?

Prevent abuse
Protect APIs from overload

🔹 5. Audit Tracking

👉 Record who did what

Example:
User: admin
Action: Deleted patient
Time: 10:30 AM

👉 Used in:

Banking apps
Admin panels
*/

/*
Spring Security’s specialized chain built on top of servlet filters.
✅ Handles:
Authentication (Who are you?)
Authorization (What can you access?)
Session management
CSRF protection
Login/logout handling

Real responsibilities:
Validate credentials (login)
Extract token / JSESSIONID
Store user in SecurityContext
Check roles (ADMIN, USER)
Block unauthorized requests
 */

/*
👉 Servlet Filter Chain

Managed by Tomcat / Servlet container

👉 Security Filter Chain

Managed by Spring Security

Servlet filter chain is managed by the servlet container, while Spring Security manages
 its own security filter chain via DelegatingFilterProxy within the servlet chain."

 "A servlet is a Java class that handles HTTP requests and responses on the server side,
  forming the foundation of Java web applications."

 */