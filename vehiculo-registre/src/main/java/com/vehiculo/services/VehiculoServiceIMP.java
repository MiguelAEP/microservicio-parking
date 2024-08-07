package com.vehiculo.services;

import com.vehiculo.client.ClientCubiculeVehicule;
import com.vehiculo.entity.CubiculoDTO;
import com.vehiculo.entity.UserVehicule;
import com.vehiculo.repository.VehiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehiculoServiceIMP implements VehiculoService{

    @Autowired
    private VehiculoRepository vehiculoRepository;

    @Autowired
    private ClientCubiculeVehicule clientCubiculeVehicule;

    @Override
    public List<UserVehicule> listAllUserVehicule() {
        return vehiculoRepository.findAll();
    }

    @Override
    public UserVehicule findByIdVehicule(Long id) {
        return vehiculoRepository.findById(id).orElseThrow(()-> new RuntimeException("no existe el id del vehiculo"));
    }

    @Override
    public UserVehicule createUserVehicule(UserVehicule userVehicule) {
        System.out.println("ocuparon el estacionamiento=? " + clientCubiculeVehicule.knowStatusOcupadoById(userVehicule.getCubiculoId()));
        //HACER:
        //Cuando se crea el usuario se debe validar que el cubiculo debe estar desocupado(false)
        //consumimos el metodo de la otra api cubiculo
        if(clientCubiculeVehicule.knowStatusOcupadoById(userVehicule.getCubiculoId())){
            throw new RuntimeException("El cubiculo esta ocupado");
        }

        return vehiculoRepository.save(userVehicule);
    }

    @Override
    public List<CubiculoDTO> showFreeCubicule() {
        return clientCubiculeVehicule.listCubiculeFalse();
    }
}
