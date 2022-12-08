package com.example.controllers;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.security.services.UserDetailsImpl;
import com.example.respositorys.UserRepository;
import com.example.exceptions.TokenRefreshException;
import com.example.model.ERole;
import com.example.model.RefreshToken;
import com.example.model.Role;
import com.example.model.User;
import com.example.request.LoginRequest;
import com.example.request.SignupRequest;
import com.example.request.TokenRefreshRequest;
import com.example.response.JwtResponse;
import com.example.response.MessageResponse;
import com.example.response.TokenRefreshResponse;
import com.example.respositorys.RoleRepository;
import com.example.security.jwt.JwtUtils;

import com.example.security.services.RefreshTokenService;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;

	@Autowired
	RefreshTokenService refreshTokenService;

	

	private Logger logger = Logger.getLogger(AuthController.class);

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		// SecurityContextHolder.getContext().setAuthentication(authentication); //this
		// seems missing
		logger.info("getContext " + SecurityContextHolder.getContext());

		String jwt = jwtUtils.generateJwtToken(authentication);
		logger.info("this is the creted jwt" + jwt);

		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		logger.info("user Details" + userDetails.getUsername());
		List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
				.collect(Collectors.toList());
		logger.info("roles " + roles);

		return ResponseEntity.ok(
				new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(), roles));
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
		System.out.println("All Role " + signUpRequest.getRole());

		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
			logger.warn("this user name already exists");
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
		}

		if (userRepository.existsByEmail(signUpRequest.getEmail())) {

			logger.warn("this email  already exists");

			return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
		}

		/*
		 * Create new user's account User user = new User(signUpRequest.getUsername(),
		 * signUpRequest.getEmail(), encoder.encode(signUpRequest.getPassword()));
		 */

		User user = new User(signUpRequest.getFirstName(), signUpRequest.getLastName(), signUpRequest.getPhoneNumber(),
				signUpRequest.getEmail(), signUpRequest.getUsername(), encoder.encode(signUpRequest.getPassword()),
				signUpRequest.getAddressline1(), signUpRequest.getAddressline2(), signUpRequest.getState(),
				signUpRequest.getZipcode());

		Set<String> strRoles = signUpRequest.getRole();

		Set<Role> roles = new HashSet<>();

		if (strRoles == null) {
			Role userRole = roleRepository.findByName(ERole.USER)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			roles.add(userRole);
		} else {
			strRoles.forEach(role -> {
				switch (role.toLowerCase()) {
				case "admin":
					Role adminRole = roleRepository.findByName(ERole.ADMIN)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(adminRole);

					// roles.add("ADMIN");)

					break;
				case "mod":
					Role modRole = roleRepository.findByName(ERole.MODERATOR)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(modRole);

					break;

				default:
					Role userRole = roleRepository.findByName(ERole.USER)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(userRole);
				}
			});
		}

		user.setRoles(roles);
		userRepository.save(user);
		// mail service to send plain text mail to user's email account about successful
		// registration
		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
	}

	@PostMapping("/refreshtoken")
	public ResponseEntity<?> refreshtoken(@Valid @RequestBody TokenRefreshRequest request) {
		String requestRefreshToken = request.getRefreshToken();
		 return refreshTokenService.findByToken(requestRefreshToken)
			        .map(refreshTokenService::verifyExpiration)
			        .map(RefreshToken::getUser)
			        .map(user -> {
			          String token = jwtUtils.generateTokenFromUsername(user.getUsername());
			          return ResponseEntity.ok(new TokenRefreshResponse(token, requestRefreshToken));
			        }).orElseThrow(() -> new TokenRefreshException(requestRefreshToken,
			            "Refresh token is not in database!"));
			  
	}

}
