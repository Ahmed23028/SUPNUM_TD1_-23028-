package com.soa.td1.endpoint;

import com.soa.td1.model.dto.CreateServerRequest;
import com.soa.td1.model.dto.CreateServerResponse;
import com.soa.td1.model.dto.DeleteServerRequest;
import com.soa.td1.model.dto.DeleteServerResponse;
import com.soa.td1.model.dto.GetAllServersRequest;
import com.soa.td1.model.dto.GetAllServersResponse;
import com.soa.td1.model.dto.GetServerStatusRequest;
import com.soa.td1.model.dto.GetServerStatusResponse;
import com.soa.td1.model.dto.RenameServerRequest;
import com.soa.td1.model.dto.RenameServerResponse;
import com.soa.td1.model.dto.ServerSoap;
import com.soa.td1.model.dto.StartServerRequest;
import com.soa.td1.model.dto.StartServerResponse;
import com.soa.td1.model.dto.StopServerRequest;
import com.soa.td1.model.dto.StopServerResponse;
import com.soa.td1.model.entity.Server;
import com.soa.td1.service.ServerSrervice;
import jakarta.xml.bind.JAXBElement;
import lombok.RequiredArgsConstructor;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import javax.xml.namespace.QName;
import java.util.List;

@Endpoint
@RequiredArgsConstructor
public class ServerEndpoint {
    
    private static final String NAMESPACE_URI = "http://soa.td1.com/server";
    
    private final ServerSrervice serverService;
    
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "CreateServerRequest")
    @ResponsePayload
    public JAXBElement<CreateServerResponse> createServer(@RequestPayload JAXBElement<CreateServerRequest> request) {
        CreateServerRequest req = request.getValue();
        
        // Validation
        if (req == null) {
            throw new IllegalArgumentException("CreateServerRequest cannot be null");
        }
        if (req.getName() == null || req.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Server name is required");
        }
        if (req.getIpAddress() == null || req.getIpAddress().trim().isEmpty()) {
            throw new IllegalArgumentException("Server ipAddress is required");
        }
        
        Server server = serverService.createServer(req.getName(), req.getIpAddress());
        
        CreateServerResponse response = new CreateServerResponse();
        response.setServer(convertToServerSoap(server));
        
        return new JAXBElement<>(new QName(NAMESPACE_URI, "CreateServerResponse"), 
                CreateServerResponse.class, response);
    }
    
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "GetAllServersRequest")
    @ResponsePayload
    public JAXBElement<GetAllServersResponse> getAllServers(@RequestPayload JAXBElement<GetAllServersRequest> request) {
        List<Server> servers = serverService.getAllServers();
        
        GetAllServersResponse response = new GetAllServersResponse();
        servers.forEach(s -> response.getServers().add(convertToServerSoap(s)));
        
        return new JAXBElement<>(new QName(NAMESPACE_URI, "GetAllServersResponse"), 
                GetAllServersResponse.class, response);
    }
    
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "RenameServerRequest")
    @ResponsePayload
    public JAXBElement<RenameServerResponse> renameServer(@RequestPayload JAXBElement<RenameServerRequest> request) {
        RenameServerRequest req = request.getValue();
        Server server = serverService.renameServer(req.getId(), req.getNewName());
        
        RenameServerResponse response = new RenameServerResponse();
        response.setServer(convertToServerSoap(server));
        
        return new JAXBElement<>(new QName(NAMESPACE_URI, "RenameServerResponse"), 
                RenameServerResponse.class, response);
    }
    
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "GetServerStatusRequest")
    @ResponsePayload
    public JAXBElement<GetServerStatusResponse> getServerStatus(@RequestPayload JAXBElement<GetServerStatusRequest> request) {
        GetServerStatusRequest req = request.getValue();
        Boolean status = serverService.getServerStatus(req.getId());
        
        GetServerStatusResponse response = new GetServerStatusResponse();
        response.setStatus(status);
        
        return new JAXBElement<>(new QName(NAMESPACE_URI, "GetServerStatusResponse"), 
                GetServerStatusResponse.class, response);
    }
    
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "StartServerRequest")
    @ResponsePayload
    public JAXBElement<StartServerResponse> startServer(@RequestPayload JAXBElement<StartServerRequest> request) {
        StartServerRequest req = request.getValue();
        Server server = serverService.startServer(req.getId());
        
        StartServerResponse response = new StartServerResponse();
        response.setServer(convertToServerSoap(server));
        
        return new JAXBElement<>(new QName(NAMESPACE_URI, "StartServerResponse"), 
                StartServerResponse.class, response);
    }
    
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "StopServerRequest")
    @ResponsePayload
    public JAXBElement<StopServerResponse> stopServer(@RequestPayload JAXBElement<StopServerRequest> request) {
        StopServerRequest req = request.getValue();
        Server server = serverService.stopServer(req.getId());
        
        StopServerResponse response = new StopServerResponse();
        response.setServer(convertToServerSoap(server));
        
        return new JAXBElement<>(new QName(NAMESPACE_URI, "StopServerResponse"), 
                StopServerResponse.class, response);
    }
    
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "DeleteServerRequest")
    @ResponsePayload
    public JAXBElement<DeleteServerResponse> deleteServer(@RequestPayload JAXBElement<DeleteServerRequest> request) {
        DeleteServerRequest req = request.getValue();
        
        DeleteServerResponse response = new DeleteServerResponse();
        try {
            serverService.deleteServer(req.getId());
            response.setSuccess(true);
            response.setMessage("Serveur supprimé avec succès");
        } catch (RuntimeException e) {
            response.setSuccess(false);
            response.setMessage(e.getMessage());
        }
        
        return new JAXBElement<>(new QName(NAMESPACE_URI, "DeleteServerResponse"), 
                DeleteServerResponse.class, response);
    }
    
    private ServerSoap convertToServerSoap(Server server) {
        ServerSoap serverSoap = new ServerSoap();
        serverSoap.setId(server.getId());
        serverSoap.setName(server.getName());
        serverSoap.setIpAddress(server.getIpAddress());
        serverSoap.setStatus(server.getStatus());
        return serverSoap;
    }
}

