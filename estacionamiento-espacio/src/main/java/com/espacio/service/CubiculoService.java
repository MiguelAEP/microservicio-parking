package com.espacio.service;

import com.espacio.entity.Cubiculo;

import java.util.List;

public interface CubiculoService {

    List<Cubiculo> findAllCubiculo();
    Cubiculo addCubiculo(Cubiculo cubiculo);
    Cubiculo findyCubiculoById(Long id);
    Boolean knowStatusOcupadoById(Long id);
    List<Cubiculo> showAllCubiculoFalse();
    void busyToFreeParking(Long id);
    void freeToBusyParking(Long id);
}
