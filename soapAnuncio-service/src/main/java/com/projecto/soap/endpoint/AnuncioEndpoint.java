package com.projecto.soap.endpoint;

import com.example.anuncio.ObtenerAnunciosResponse;
import com.projecto.soap.services.AnuncioService1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class AnuncioEndpoint {

    @Autowired
    private AnuncioService1 servicio;

    private static final String NAMESPACE = "http://example.com/anuncio";

    @PayloadRoot(
            namespace = "http://example.com/anuncio",
            localPart = "ObtenerAnunciosRequest"
    )
    @ResponsePayload
    public ObtenerAnunciosResponse lista() {
        return servicio.findAll();
    }


}
