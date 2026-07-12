package com.projecto.soap.services;

import com.example.anuncio.ObtenerAnunciosResponse;
import com.projecto.soap.repository.AnuncioRepository;
import org.springframework.stereotype.Service;

@Service
public class AnuncioService1 {

    private final AnuncioRepository repo;
    
    public AnuncioService1(AnuncioRepository repo) {
        this.repo = repo;
    }

    public ObtenerAnunciosResponse findAll() {
        ObtenerAnunciosResponse response = new ObtenerAnunciosResponse();
        response.getAnuncio().addAll(repo.listProductos());
        return response;
    }

}
