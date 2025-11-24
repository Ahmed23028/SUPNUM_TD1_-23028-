package com.soa.td1.model.dto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", namespace = "http://soa.td1.com/server", propOrder = {
    "name",
    "ipAddress"
})
@XmlRootElement(name = "CreateServerRequest", namespace = "http://soa.td1.com/server")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateServerRequest {
    
    @XmlElement(name = "name", namespace = "http://soa.td1.com/server", required = true, nillable = false)
    private String name;
    
    @XmlElement(name = "ipAddress", namespace = "http://soa.td1.com/server", required = true, nillable = false)
    private String ipAddress;
}

