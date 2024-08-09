package com.espacio.controller;

import com.espacio.entity.Cubiculo;
import com.espacio.service.CubiculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cubiculo")
public class CubiculoController {

    @Autowired
    private CubiculoService cubiculoService;

    @GetMapping
    public List<Cubiculo> listAllCubicule(){
        return cubiculoService.findAllCubiculo();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public Cubiculo findByIdCubiculo(@PathVariable Long id){
        return cubiculoService.findyCubiculoById(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Cubiculo createCubiculo(@RequestBody Cubiculo cubiculo){
        return cubiculoService.addCubiculo(cubiculo);
    }

    @GetMapping("/validarOcupado/{id}")
    public Boolean knowStatusOcupadoById(@PathVariable Long id){
        return cubiculoService.knowStatusOcupadoById(id);
    }

    @GetMapping("/freeCubicule")
    public List<Cubiculo> listCubiculeFalse(){
        return cubiculoService.showAllCubiculoFalse();
    }

    @GetMapping("/busyToFreeParking/{id}")
    public void busyToFreeParking(@PathVariable Long id){
        cubiculoService.busyToFreeParking(id);
    }

    @GetMapping("/freeToBusyParking/{id}")
    public void freeToBusyParking(@PathVariable Long id){
        cubiculoService.freeToBusyParking(id);
    }


}
