package com.LMSAssginment.Code.AuthenticationLayer.Security;


//package com.LMSAssginment.Code.AuthenticationLayer.Security;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//
//import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//@Configuration
//@EnableWebSecurity
//
//public class SecurityConfiguration {
//    private  AuthenticationProvider authenticationProvider;
//
//    private JWTAuthenticationFilter jwtAuthFilter;
//
//    public SecurityConfiguration(AuthenticationProvider authenticationProvider, JWTAuthenticationFilter jwtAuthFilter) {
//        this.authenticationProvider = authenticationProvider;
//        this.jwtAuthFilter = jwtAuthFilter;
//    }
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf(AbstractHttpConfigurer::disable)
//                .authorizeHttpRequests(authorize -> authorize
//                        .requestMatchers("/login").permitAll()
//                        .requestMatchers("/signup").permitAll()
//                        .requestMatchers("/createUser/**").hasRole("ADMIN")
//                        .anyRequest().authenticated()
//                )
//                .sessionManagement(session -> session
//                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                )
//                .authenticationProvider(authenticationProvider)
//                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
//
//        return http.build();
//    }
//
//}
//package com.LMSAssginment.Code.AuthenticationLayer.Security;
//


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity

public class SecurityConfiguration {
    private  AuthenticationProvider authenticationProvider;

    private JWTAuthenticationFilter jwtAuthFilter;

    public SecurityConfiguration(AuthenticationProvider authenticationProvider, JWTAuthenticationFilter jwtAuthFilter) {
        this.authenticationProvider = authenticationProvider;
        this.jwtAuthFilter = jwtAuthFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/login").permitAll()
                        .requestMatchers("/signup").permitAll()
                        .requestMatchers("/performanceTracking/**").hasRole("INSTRUCTOR")
                        .requestMatchers("/createUser/**").hasRole("ADMIN")
                        .requestMatchers("/{course_id}/createAssessment").hasRole("INSTRUCTOR")
                        .requestMatchers("/{Course_id}/{assessment_id}/displayAssessment").permitAll()
                        .requestMatchers("/{course_id}/createQuestions/mcq").hasRole("INSTRUCTOR")
                        .requestMatchers("/{course_id}/createQuestions/tf").hasRole("INSTRUCTOR")
                        .requestMatchers("/{course_id}/createQuestions/sa").hasRole("INSTRUCTOR")
                        .requestMatchers("{user_id}/{course_id}/{assessment_id}/displayAssessment/submitQuiz").hasRole("STUDENT")
                        .requestMatchers("{user_id}/{course_id}/{assessment_id}/displayAssessment/submitAssignment").hasRole("STUDENT")
                        .requestMatchers("{user_id}/{course_id}/{assessment_id}/displayAssessment/giveFeedback").hasRole("INSTRUCTOR")
                        .requestMatchers("/{userID}/notifications/all").permitAll()
                        .requestMatchers("/{userID}/notifications/unread").permitAll()
                        .requestMatchers("/{userID}/notifications/{notification_id}").permitAll()
                        .requestMatchers("/{course_id}/createNotification/allEnrolled").hasRole("INSTRUCTOR")
                        .requestMatchers("/{course_id}/createNotification/specific").hasRole("INSTRUCTOR")
                        .requestMatchers("/course/create/**").hasRole("INSTRUCTOR")
                        .requestMatchers("/course/update").hasRole("INSTRUCTOR")
                        .requestMatchers("/course/delete/**").hasRole("INSTRUCTOR")
                        .requestMatchers("/course/addLesson").hasRole("INSTRUCTOR")
                        .requestMatchers("/drop/**").hasRole("INSTRUCTOR")
                        .requestMatchers("enroll/view/**").hasRole("INSTRUCTOR")
                        .requestMatchers("/enroll/**").hasRole("STUDENT")
                        .requestMatchers("/attend").hasRole("STUDENT")
                        .requestMatchers("/attend/course/{courseId}/lesson/{lessonId}").hasRole("INSTRUCTOR")
                        .requestMatchers("/profile/{id}").permitAll()
                        .requestMatchers("/profile/updateStudent/{id}").hasRole("STUDENT")
                        .requestMatchers("/profile/updateAdmin/{id}").hasRole("ADMIN")
                        .requestMatchers("/profile/updateInstructor/{id}").hasRole("INSTRUCTOR")
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

}


