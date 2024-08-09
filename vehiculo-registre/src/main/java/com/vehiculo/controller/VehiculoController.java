package com.vehiculo.controller;


import com.vehiculo.client.ClientCubiculeVehicule;
import com.vehiculo.entity.CubiculoDTO;
import com.vehiculo.entity.UserVehicule;
import com.vehiculo.services.VehiculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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

    @GetMapping("/busyToFreeParking/{id}")
    public ResponseEntity<Map<String, Integer>> leaveTheParking(@PathVariable Long id){
        Map<String, Integer> response = vehiculoService.leaveTheParking(id);
        return ResponseEntity.ok(response);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public UserVehicule createVehicule(@RequestBody UserVehicule userVehicule){
        return vehiculoService.createUserVehicule(userVehicule);
    }




}
