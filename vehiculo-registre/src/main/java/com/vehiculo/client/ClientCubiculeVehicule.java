package com.vehiculo.client;

import com.vehiculo.entity.CubiculoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "estacionamiento-espacio", url = "http://localhost:8082/api/cubiculo")
public interface ClientCubiculeVehicule {

    @GetMapping("/validarOcupado/{id}")
     Boolean knowStatusOcupadoById(@PathVariable Long id);

    @GetMapping("/freeCubicule")
    public List<CubiculoDTO> listCubiculeFalse();

}
