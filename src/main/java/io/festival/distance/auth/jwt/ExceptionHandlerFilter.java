package io.festival.distance.auth.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.festival.distance.exception.DistanceException;
import io.festival.distance.exception.ErrorCode;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.Data;
import org.springframework.http.MediaType;
import org.springframework.web.filter.OncePerRequestFilter;

public class ExceptionHandlerFilter extends OncePerRequestFilter {
    private static final String IN_CODING_TYPE = "UTF-8";
    @Override
    protected void doFilterInternal(
        HttpServletRequest request,
        HttpServletResponse response,
        FilterChain filterChain
    ) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (DistanceException e) {
            setErrorResponse(response, e.getErrorCode());
        }
    }

    private void setErrorResponse(
        HttpServletResponse response,
        ErrorCode errorCode
    ) {
        ObjectMapper objectMapper = new ObjectMapper();
        response.setStatus(errorCode.getStatus());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(IN_CODING_TYPE);
        ErrorResponse errorResponse = new ErrorResponse(errorCode.getStatus(),
            errorCode.getMessage());
        try {
            response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Data
    public static class ErrorResponse {

        private final Integer code;
        private final String message;
    }
}
