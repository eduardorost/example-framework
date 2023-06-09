package com.example.logging.filter;

import com.example.tenant.storage.TenantStorage;
import io.opentracing.util.GlobalTracer;
import org.slf4j.MDC;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class MDCFilter extends OncePerRequestFilter {

    private static final String TENANT = "tenant_id";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            try {
                String tenantId = TenantStorage.getTenantId();
                MDC.put(TENANT, tenantId);
                Optional.ofNullable(GlobalTracer.get().activeSpan()).ifPresent(span -> span.setTag(TENANT, tenantId));
            } catch (IllegalArgumentException ignored) {
                // no companyId specified
            }

            filterChain.doFilter(request, response);
        } finally {
            MDC.remove(TENANT);
        }
    }
}
