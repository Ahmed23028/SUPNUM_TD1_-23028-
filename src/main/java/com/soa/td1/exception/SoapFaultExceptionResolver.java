package com.soa.td1.exception;

import org.springframework.ws.soap.SoapFault;
import org.springframework.ws.soap.server.endpoint.SoapFaultMappingExceptionResolver;

public class SoapFaultExceptionResolver extends SoapFaultMappingExceptionResolver {
    
    @Override
    protected void customizeFault(Object endpoint, Exception ex, SoapFault fault) {
        logger.warn("Exception processed: " + ex.getMessage(), ex);
    }
}

