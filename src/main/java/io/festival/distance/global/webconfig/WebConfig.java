/*
package io.festival.distance.global.webconfig;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

@WebFilter("/*")
@Component
public class WebConfig implements Filter {

    private static final String ALLOWED_ORIGIN = "https://alpha.dis-tance.com";
    private static final String ALLOWED_ORIGIN_LOCAL = "http://localhost:8080";
    private static final String ORIGIN = "Origin";

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
        FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
        String origin = httpRequest.getHeader(ORIGIN);
        if (origin == null && !httpRequest.getRequestURL().toString()
            .startsWith(ALLOWED_ORIGIN_LOCAL)) {
            httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "Unauthorized request.");
            return;
        }

        if (origin != null && !origin.equals(ALLOWED_ORIGIN)) {
            httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "Unauthorized request.");
            return;
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }
}
*/
