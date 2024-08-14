package com.espacio.controller;

import com.espacio.entity.Cubiculo;
import com.espacio.service.CubiculoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.mockito.BDDMockito.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import com.fasterxml.jackson.databind.ObjectMapper;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


@WebMvcTest(CubiculoController.class)
public class CubiculoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CubiculoService cubiculoService;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    public void listAllCubicule() throws Exception {
        List<Cubiculo> listado = List.of( new Cubiculo(1L,"A001",true,10)
                , new Cubiculo(2L,"A002",false,50)
                , new Cubiculo(3L,"A003",true,10));

        given(cubiculoService.findAllCubiculo()).willReturn(listado);
        //when
        ResultActions response =mockMvc.perform(get("/api/cubiculo"));
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.size()",is(listado.size())));

    }

    @Test
    public void findByIdCubiculo() throws Exception {
        Cubiculo cubiculo = new Cubiculo(1L,"A001",true,10);

        given(cubiculoService.findyCubiculoById(1L)).willReturn(cubiculo);

        ResultActions response = mockMvc.perform(get("/api/cubiculo/"+1L));
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.nombre",is("A001")));
    }

    @Test
    public void createCubiculo() throws Exception {
        Cubiculo cubiculo = new Cubiculo(1L,"A001",true,10);
        given(cubiculoService.addCubiculo(any(Cubiculo.class))).willAnswer(invocation -> invocation.getArgument(0));

        ResultActions response = mockMvc.perform(post("/api/cubiculo")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cubiculo))
        );

        response.andExpect(status().isCreated())
                .andDo(print())
                .andExpect(jsonPath("$.nombre", is("A001")))
                .andExpect(jsonPath("$.precio", is(10)));
    }

    @Test
    public void knowStatusOcupadoById() throws Exception {

       Cubiculo cubiculo = new Cubiculo(1L,"A001",true,10);
        given(cubiculoService.knowStatusOcupadoById(1L)).willReturn(cubiculo.isOcupado());
        ResultActions response = mockMvc.perform(get("/api/cubiculo/validarOcupado/{id}",1L)
                .contentType(MediaType.APPLICATION_JSON) );

        response.andDo(print())
                .andExpect(content().string("true"));

    }

    @Test
    public void listCubiculeFalse() throws Exception {
        List<Cubiculo> listadoToTest = Stream.of( new Cubiculo(1L,"A001",true,10)
                        , new Cubiculo(2L,"A002",false,50)
                        , new Cubiculo(3L,"A003",true,10)
                        , new Cubiculo(4L,"A004",false,10))
                .filter(c-> !c.isOcupado()).toList();

        given(cubiculoService.showAllCubiculoFalse()).willReturn(listadoToTest);
        ResultActions response = mockMvc.perform(get("/api/cubiculo/freeCubicule"));

        response.andDo(print())
                .andExpect(jsonPath("$.size()",is(2)));

    }

}
