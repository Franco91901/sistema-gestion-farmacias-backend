package com.projecto.soap.repository;
import com.example.anuncio.Anuncio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class AnuncioRepository {

    @Autowired
    JdbcTemplate template;


    public List<Anuncio> listProductos(){
        String sql = "select id, titulo, descripcion, imagen_url from anuncio";

        return template.query(sql, (result, rowNum) -> {
            Anuncio anuncio = new Anuncio();
            anuncio.setId(result.getLong("id"));
            anuncio.setTitulo(result.getString("titulo"));
            anuncio.setDescripcion(result.getString("descripcion"));
            anuncio.setImagenUrl(result.getString("imagen_url"));
            return anuncio;
        });
    }
}
