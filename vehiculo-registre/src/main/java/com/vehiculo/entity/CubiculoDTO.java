package com.vehiculo.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CubiculoDTO {

    private Long id;
    private String nombre;
    private boolean ocupado;
    private int precio;

}
