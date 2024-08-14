package com.espacio.service;

import com.espacio.entity.Cubiculo;
import com.espacio.repository.CubiculoRepository;
import com.mysql.cj.xdevapi.Collection;
import org.hibernate.sql.ast.tree.expression.Collation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
@ExtendWith(MockitoExtension.class)
public class CubiculoServiceTest {

    @Mock
    private CubiculoRepository cubiculoRepository;

    @InjectMocks
    private CubiculoServiceIMP cubiculoServiceIMP;

    private Cubiculo cubiculo;

    @BeforeEach
    public void initial(){
      cubiculo = new Cubiculo(1L,"A001",false,10);
    }

    @Test
    @DisplayName("test para guardar un cubiculo")
    public void guardarCubiculo(){

        Cubiculo cubiculo = new Cubiculo(1L,"A001",true,10);
        when(cubiculoRepository.save(cubiculo)).thenReturn(cubiculo);

        Cubiculo resultado =cubiculoServiceIMP.addCubiculo(cubiculo);
        assertThat(resultado).isNotNull();
        assertEquals(cubiculo,resultado);
        assertThat(resultado.getNombre()).isEqualTo(cubiculo.getNombre());
        verify(cubiculoRepository).save(argThat(c -> c.getNombre().equalsIgnoreCase("A001")));

    }

    @Test
    @DisplayName("test para validar el listado de cubiculo")
    public void listadoCubiculoTest(){

        List<Cubiculo> listado = List.of( new Cubiculo(1L,"A001",true,10)
                 , new Cubiculo(2L,"A002",false,50)
                , new Cubiculo(3L,"A003",true,10));

        given(cubiculoRepository.findAll()).willReturn(listado);

        List<Cubiculo> cubiculos = cubiculoServiceIMP.findAllCubiculo();
        assertThat(cubiculos).isNotNull();
        assertThat(cubiculos.size()).isEqualTo(3);

    }

    @Test
    @DisplayName("test para buscar cubiculo por id")
    public void buscarCubiculoPorIdTest(){

        Cubiculo cubiculo =  new Cubiculo(1L,"A001",true,10);
        given(cubiculoRepository.findById(1L)).willReturn(Optional.of(cubiculo));

        Cubiculo cubiculoPorId = cubiculoServiceIMP.findyCubiculoById(1L);
        assertThat(cubiculoPorId.getNombre()).isEqualTo("A001");

    }

    @Test
    @DisplayName("test saber si un cubiculo esta ocupado")
    public void knowStatusOcupadoByIdTest(){
        Cubiculo cubiculo =  new Cubiculo(1L,"A001",true,10);

        given(cubiculoRepository.findOcupadoById(1L)).willReturn(cubiculo.isOcupado());

        boolean isOcupado = cubiculoServiceIMP.knowStatusOcupadoById(1L);

        assertThat(isOcupado).isTrue();
        verify(cubiculoRepository).findOcupadoById(1L);
    }

    @Test
    @DisplayName("Mostrar todos los cubiculos que esten libres")
    public void showAllCubiculoFalse(){
        List<Cubiculo> listadoToTest = Stream.of( new Cubiculo(1L,"A001",true,10)
                , new Cubiculo(2L,"A002",false,50)
                , new Cubiculo(3L,"A003",true,10)
                , new Cubiculo(4L,"A004",false,10))
                .filter(c-> !c.isOcupado()).toList();

        given(cubiculoRepository.findCubiculosNoOcupados()).willReturn(listadoToTest);
        List<Cubiculo> cubiculosNoOcupados = cubiculoServiceIMP.showAllCubiculoFalse();

        assertThat(cubiculosNoOcupados.size()).isEqualTo(2);
        assertThat(cubiculosNoOcupados).extracting(Cubiculo::getNombre).containsExactlyInAnyOrder("A002","A004");
        verify(cubiculoRepository).findCubiculosNoOcupados();
    }


    @Test
    @DisplayName("test cubiculos llenos")
    public void shouldReturnEmptyListWhenNoCubiculosAreFree(){

        given(cubiculoRepository.findCubiculosNoOcupados()).willReturn(Collections.emptyList());

        List<Cubiculo> cubiculosNoOcupados = cubiculoServiceIMP.showAllCubiculoFalse();

        assertThat(cubiculosNoOcupados.size()).isEqualTo(0);

        verify(cubiculoRepository).findCubiculosNoOcupados();
    }

    @Test
    @DisplayName("test cambiar de ocupado a libre cubiculo")
    public void busyToFreeParking(){

        Cubiculo cubiculo =  new Cubiculo(1L,"A001",true,10);
        cubiculo.setOcupado(false);
        given(cubiculoRepository.findById(1L)).willReturn(Optional.of(cubiculo));

         cubiculoServiceIMP.busyToFreeParking(1L);

        assertThat(cubiculo.isOcupado()).isFalse();

    }

    @Test
    @DisplayName("test cambiar de libre a ocupado cubiculo")
    public void freeToBusyParking(){


        given(cubiculoRepository.findById(1L)).willReturn(Optional.of(cubiculo));
        cubiculoServiceIMP.busyToFreeParking(1L);
        cubiculo.setOcupado(true);
        System.out.println(cubiculo.isOcupado());
        assertThat(cubiculo.isOcupado()).isTrue();

    }


}
