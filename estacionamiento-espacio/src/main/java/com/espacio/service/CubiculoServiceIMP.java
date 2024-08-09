package com.espacio.service;

import com.espacio.entity.Cubiculo;
import com.espacio.repository.CubiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CubiculoServiceIMP implements CubiculoService{

    @Autowired
    private CubiculoRepository cubiculoRepository;

    @Override
    public List<Cubiculo> findAllCubiculo() {
        return cubiculoRepository.findAll();
    }

    @Override
    public Cubiculo addCubiculo(Cubiculo cubiculo) {

        if(cubiculoRepository.existsByNombre(cubiculo.getNombre())){
            throw new RuntimeException("Ya existe el cubiculo");
        }else {
            return cubiculoRepository.save(cubiculo);
        }
    }

    @Override
    public Cubiculo findyCubiculoById(Long id) {
        return cubiculoRepository.findById(id).orElseThrow(()-> new RuntimeException("no existe el cubiculo buscado"));
    }

    @Override
    public Boolean knowStatusOcupadoById(Long id) {
        return cubiculoRepository.findOcupadoById(id);
    }

    @Override
    public List<Cubiculo> showAllCubiculoFalse() {
        return cubiculoRepository.findCubiculosNoOcupados();
    }

    @Override
    public void busyToFreeParking(Long id) {
        //CAMBIA EL ESTADO DE OCUPADO A DESOCUPADO
        Cubiculo cubiculo = findyCubiculoById(id);
        cubiculo.setOcupado(false);
        cubiculoRepository.save(cubiculo);
    }

    @Override
    public void freeToBusyParking(Long id) {
        //CAMBIA EL ESTADO DE LIBRE A OCUPADO
        Cubiculo cubiculo = findyCubiculoById(id);
        cubiculo.setOcupado(true);
        cubiculoRepository.save(cubiculo);
    }
}
