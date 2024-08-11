package com.espacio.repository;

import com.espacio.entity.Cubiculo;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

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


}
