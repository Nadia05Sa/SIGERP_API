package com.utez.api_sigerp.service;

import com.utez.api_sigerp.model.Categoria;
import com.utez.api_sigerp.repository.CategoriaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {
    private final CategoriaRepository categoriaRepository;

    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    // Guardar una nueva categoría
    public Categoria save(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    // Obtener todas las categorías
    public List<Categoria> getCategorias() {
        return categoriaRepository.findAll();
    }

    // Buscar una categoría por nombre
    public Optional<Categoria> getCategoriaByNombre(String nombre) {
        return categoriaRepository.findByNombreIgnoreCase(nombre);
    }

    // Buscar una categoría por ID
    public Optional<Categoria> getCategoriaById(String id) {
        return categoriaRepository.findById(id);
    }

    //Obtener una imagen por una categoria
    public Optional<String> getCategoriaImageById(String id) {
        return categoriaRepository.findById(id)
                .map(Categoria::getImagen);
    }
}
