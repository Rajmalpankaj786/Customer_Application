package com.springsecurity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringSecurityDbApp9RoleWithCsrfTokenApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringSecurityDbApp9RoleWithCsrfTokenApplication.class, args);
	}

}

/*
 * Advantages of Using Tokens: Tokens can be invalidated during any suspicious
 * activities without invalidating the user credentials. Tokens can be created
 * with a short life span. Tokens can be used to store the user related
 * information like roles and authorities, etc.
 * 
 * Each JWT contains encoded JSON objects. The token is mainly composed of a
 * header, payload, and signature. These three parts are separated by period(.)
 * 
 * 
 * How JWT Token is used----
 * 
 * User sign-in using username and password. Backend server verifies the
 * credentials and issues a JWT token signed using a private key. Client uses
 * the JWT to access protected resources by passing the JWT in HTTP
 * Authorization header. Backend server then verifies the authenticity of the
 * token using the secret key and grant permission to the protected API.
 * 
 * In order to use the JWT token inside our application, we need to follow the
 * following steps: Step1: Inside the pom.xml we need to add some JWT related
 * dependencies which will going to help in generating and validating the JWT
 * tokens. <dependency> <groupId>io.jsonwebtoken</groupId>
 * <artifactId>jjwt-api</artifactId> <version>0.11.1</version> </dependency>
 * <dependency> <groupId>io.jsonwebtoken</groupId>
 * <artifactId>jjwt-impl</artifactId> <version>0.11.1</version>
 * <scope>runtime</scope> </dependency> <dependency>
 * <groupId>io.jsonwebtoken</groupId> <artifactId>jjwt-jackson</artifactId>
 * <version>0.11.1</version> <scope>runtime</scope> </dependency>
 * 
 * 
 * Step2 : Perform some changes inside our Spring Security configuration: As of
 * now inside our application, by default Spring Security framework is
 * generating a JSessionId and it uses that JSsesionId for all the subsequent
 * request that our user is going to make post successful authentication. So we
 * need to disable that default behavior, post that only we can generate our own
 * JWT token.
 * 
 * http.sessionManagement(sessionManagement ->
 * sessionManagement.sessionCreationPolicy(Sessio nCreationPolicy.STATELESS))
 * 
 * With the above configuration, we are telling the Spring Security framework to
 * not generate any JSessionId, we are going to take care of our own session
 * management or token management inside our application.
 * 
 * 
 * 
 * Step3: Expose the Authorization header to the client with the response: When
 * we generate the JWT token inside our application after successful
 * authentication, we are going to send that token to the client application
 * inside the response as an header with name Authorization. So inside the Cors
 * configuration, we need to define those details telling to the Spring Security
 * framework, please allow to send this header information as part of the
 * response that we are going to send from the backend application to the client
 * application. Then only the client browser is going to accept that new header
 * which we are going to send in as a part of the response.
 * 
 * cfg.setExposedHeaders(Arrays.asList("Authorization"));
 * 
 * Using this setExposedHeader method we can expose the headers that we are
 * sending inside the response to the client application. Otherwise our browser
 * is not going to accept these headers, because there are 2 different origins
 * are trying to communicate. So, that's why all these details we need to define
 * as part of CorsConfiguration.
 * 
 * 
 * Step4: Create a JWT token generator filter.
 * 
 * SecurityConstants.java (Interface) JwtTokenGeneratorFilter.java (class)
 * creating jwt token JwtTokenValidatorFilter.java (class) checking the jwt
 * token and that same user is asking
 */
