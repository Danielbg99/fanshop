package com.example.fanshop.web.filters;

import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

import javax.servlet.http.HttpServletRequest;

public class PreAuthenticationFilter extends AbstractPreAuthenticatedProcessingFilter {
    @Override
    protected Object getPreAuthenticatedPrincipal(HttpServletRequest httpServletRequest) {
        Object principal = httpServletRequest.getUserPrincipal();

        return principal;
    }

    @Override
    protected Object getPreAuthenticatedCredentials(HttpServletRequest httpServletRequest) {
        return "N/A";
    }
}
