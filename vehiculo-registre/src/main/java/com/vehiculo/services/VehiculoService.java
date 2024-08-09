package com.vehiculo.services;

import com.vehiculo.entity.CubiculoDTO;
import com.vehiculo.entity.UserVehicule;

import java.util.List;
import java.util.Map;

public interface VehiculoService {

    List<UserVehicule> listAllUserVehicule();
    UserVehicule findByIdVehicule(Long id);
    UserVehicule createUserVehicule(UserVehicule userVehicule);
    List<CubiculoDTO> showFreeCubicule();

    Map<String,Integer> leaveTheParking(Long id);



}
