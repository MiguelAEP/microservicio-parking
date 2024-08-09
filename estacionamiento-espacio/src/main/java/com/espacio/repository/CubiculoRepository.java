package com.espacio.repository;

import com.espacio.entity.Cubiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CubiculoRepository extends JpaRepository<Cubiculo,Long> {

    boolean existsByNombre(String nombre);

    @Query("SELECT c.ocupado FROM Cubiculo c WHERE c.id = :id")
    Boolean findOcupadoById(@Param("id") Long id);

    @Query("SELECT c FROM Cubiculo c WHERE c.ocupado = false")
    List<Cubiculo> findCubiculosNoOcupados();



}
