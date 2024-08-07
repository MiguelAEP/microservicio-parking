package com.vehiculo.controller;


import com.vehiculo.client.ClientCubiculeVehicule;
import com.vehiculo.entity.CubiculoDTO;
import com.vehiculo.entity.UserVehicule;
import com.vehiculo.services.VehiculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vehicule")
public class VehiculoController {

    @Autowired
    private VehiculoService vehiculoService;

    @Autowired
    private ClientCubiculeVehicule clientCubiculeVehicule;

    @GetMapping
    public List<UserVehicule> listAllVehicule(){
        return vehiculoService.listAllUserVehicule();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public UserVehicule findVehiculeById(@PathVariable Long id){
        return vehiculoService.findByIdVehicule(id);
    }

    @GetMapping("/showFreeCubiculo")
    public List<CubiculoDTO> listAllCubiculeFree(){
        return vehiculoService.showFreeCubicule();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public UserVehicule createVehicule(@RequestBody UserVehicule userVehicule){
        return vehiculoService.createUserVehicule(userVehicule);
    }




}
