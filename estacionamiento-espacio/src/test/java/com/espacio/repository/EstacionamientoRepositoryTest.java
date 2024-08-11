package com.espacio.repository;

import com.espacio.entity.Cubiculo;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class EstacionamientoRepositoryTest {

    @Autowired
    private  CubiculoRepository cubiculoRepository;

    @BeforeAll
    public static void initEjecution(@Autowired CubiculoRepository cubiculoRepository) {
        Cubiculo cubiculo = new Cubiculo(1L,"A001",true,10);
        cubiculoRepository.save(cubiculo);
        System.out.println(cubiculo);
    }

    @Test
    @DisplayName("test para validar que existe cubiculo")
    public void guardarCubiculoExistente(){

        Cubiculo cubiculo = new Cubiculo(3L,"A001",true,50);
        boolean existe = cubiculoRepository.existsByNombre(cubiculo.getNombre());
        assertThat(existe).isTrue();
    }

    @Test
    @DisplayName("test para guardar un cubiculo - OK")
    public void guardarCubiculo(){

        Cubiculo cubiculo = new Cubiculo(2L,"A002",true,10);

        Cubiculo cubiculoGuardado = cubiculoRepository.save(cubiculo);
        assertThat(cubiculoGuardado).isNotNull();
        assertThat(cubiculoGuardado.getNombre()).isEqualTo("A002");
    }

    @Test
    @DisplayName("Test para validar si esta ocupado por el id de cubiculo")
    public void findOcupadoById(){

        Long id = 1L;
        boolean ocupado = cubiculoRepository.findOcupadoById(id);
        assertThat(ocupado).isTrue();
    }

    @Test
    @DisplayName("Test para mostrar los cubiculos libres")
    public void findCubiculosNoOcupados(){

        List<Cubiculo> cubiculoList = List.of(
                new Cubiculo(3L,"A003",true,50),
                new Cubiculo(4L,"A004",false,10),
                new Cubiculo(5L,"A005",false,10),
                new Cubiculo(6L,"A006",false,30));

        cubiculoList.forEach(cubiculo -> cubiculoRepository.save(cubiculo));

        List<Cubiculo> cubiculosLibres = cubiculoRepository.findCubiculosNoOcupados();
        assertThat(cubiculosLibres).isNotNull();
        assertThat(cubiculosLibres.size()).isEqualTo(3);
    }

    @Test
    @DisplayName("Test para buscar cubiculo por id")
    public void buscarCubiculoPorId(){
        Long id = 1L;
        Cubiculo cubiculo = cubiculoRepository.findById(id).orElseThrow();
        assertThat(cubiculo.getPrecio()).isEqualTo(10);
        assertThat(cubiculo.getNombre()).isEqualTo("A001");
        assertThat(cubiculo.isOcupado()).isEqualTo(true);
    }

}
