package com.soa.td1.model.dto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Server", propOrder = {
    "id",
    "name",
    "ipAddress",
    "status"
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServerSoap {
    
    @XmlElement(required = true)
    private Long id;
    
    @XmlElement(required = true)
    private String name;
    
    @XmlElement(name = "ipAddress", required = true)
    private String ipAddress;
    
    @XmlElement(required = true)
    private Boolean status;
}

