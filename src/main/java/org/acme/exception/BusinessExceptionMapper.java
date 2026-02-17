package org.acme.exception;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class BusinessExceptionMapper implements ExceptionMapper<BusinessException> {
    public record ErrorResponse(String error) {}

    @Override
    public Response toResponse(BusinessException e) {
        ErrorResponse body = new ErrorResponse(e.getMessage());
        int statusCode = e.getStatusCode();
        return Response.status(statusCode)
                .entity(body)
                .build();
    }
}