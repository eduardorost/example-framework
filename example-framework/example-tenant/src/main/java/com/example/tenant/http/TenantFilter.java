package com.example.tenant.http;

import com.example.tenant.storage.TenantStorage;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class TenantFilter extends OncePerRequestFilter {

    private static final String TENANT_ID_PARAMETER_NAME = "tenantId";

    private static final String[] IGNORED_PATHS = {
            "/actuator/**",
            "/v3/api-docs/**",
            "/swagger-resources/**",
            "/swagger-ui/**",
            "/health"
    };

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String tenantId = request.getHeader(TENANT_ID_PARAMETER_NAME);

        if (tenantId == null || tenantId.trim().isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("missing mandatory tenantId header");
            return;
        }

        TenantStorage.setTenantId(tenantId.trim());
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        for (String ignoredPath : IGNORED_PATHS) {
            if (new AntPathRequestMatcher(ignoredPath, request.getMethod()).matches(request)) {
                return true;
            }
        }

        return false;
    }
}
