package com.vehiculo.services;

import com.vehiculo.client.ClientCubiculeVehicule;
import com.vehiculo.entity.CubiculoDTO;
import com.vehiculo.entity.UserVehicule;
import com.vehiculo.repository.VehiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

        //HACER:
        //Cuando se crea el usuario se debe validar que el cubiculo debe estar desocupado(false)
        //consumimos el metodo de la otra api cubiculo
        if(clientCubiculeVehicule.knowStatusOcupadoById(userVehicule.getCubiculoId())){
            throw new RuntimeException("El cubiculo esta ocupado");
        }
        else{
            //ahora el estado del cubiculo cambia a ocupado
            clientCubiculeVehicule.freeToBusyParking(userVehicule.getCubiculoId());

            return vehiculoRepository.save(userVehicule);
        }

    }

    @Override
    public List<CubiculoDTO> showFreeCubicule() {
        return clientCubiculeVehicule.listCubiculeFalse();
    }

    @Override
    public Map<String,Integer> leaveTheParking(Long id) {

        Map<String,Integer> response = new HashMap<>();

        //obtenemos el vehiculo por el id
        UserVehicule userVehicule = vehiculoRepository.findById(id).orElseThrow();

        //tenemos que dejar vacio el espacio del parking ocupado
        clientCubiculeVehicule.busyToFreeParking(userVehicule.getCubiculoId());

        //tenemos que restar el tiempo actual - el tiempo que ingreso al parkeo
        LocalDateTime fechaHoraActual = LocalDateTime.now();
        LocalDateTime timeJoinParking = userVehicule.getIngreso();

        Duration duracion = Duration.between( timeJoinParking , fechaHoraActual);
        long hours = duracion.toHours();

        //tenemos que multiplicar el tiempo que se tuvo parkeado por el precio base
        //del cubiculo
        CubiculoDTO cubiculoDTO = clientCubiculeVehicule.findByIdCubiculo(userVehicule.getCubiculoId());
        int precioBase = cubiculoDTO.getPrecio();

        Integer amoutTotal = Math.toIntExact(precioBase * hours);

        System.out.println("amoutTotal" + amoutTotal);
        response.put("Amount to Pay " , amoutTotal);
        response.put("Horas acumuladas ", (int) hours);
        response.put("DNI " , userVehicule.getDocumentoIdentidad());
        return response;

    }



}
