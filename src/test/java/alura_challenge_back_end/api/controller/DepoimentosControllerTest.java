package alura_challenge_back_end.api.controller;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ResponseStatusException;

import alura_challenge_back_end.api.entities.Depoimentos;
import alura_challenge_back_end.api.repository.DepoimentosRepository;
import alura_challenge_back_end.api.service.DepoimentosService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
public class DepoimentosControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<Depoimentos> dadosDepoimentos;
    @Autowired
    private JacksonTester<Page<Depoimentos>> paginaDepoimentos;
    @Autowired
    private JacksonTester<List<Depoimentos>> depoimenotsAleatorios;
    
    @MockBean
    private DepoimentosService service;
    @MockBean
    private DepoimentosRepository repository;
        
    private Long userId = 1L;

    @Test
    @DisplayName("Teste de requisicoes")
    void consultar_cenario1_get() throws Exception {
        PageRequest pageRequest = PageRequest.of(0, 3, Direction.valueOf("ASC"),"id");

        List<Depoimentos> allDepoimentos = insertDepoimentos();
        int start = (int) pageRequest.getOffset();
        int end = Math.min((start + pageRequest.getPageSize()), allDepoimentos.size());

        List<Depoimentos> pageContent = allDepoimentos.subList(start, end);

        var comentarios = new PageImpl<>(pageContent, pageRequest, allDepoimentos.size());

        when(service.getDepoimento(any(PageRequest.class))).thenReturn(comentarios);

        var json = paginaDepoimentos.write(comentarios).getJson();

        var response = mvc
                .perform(get("/depoimentos")
                .param("page", "0")
                .param("linesPerPage", "3")
                .param("direction", "ASC")
                .param("orderBy", "id"))
                .andReturn().getResponse();


        assertThat(response.getContentAsString()).isEqualTo(json);
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }
    @Test
    @DisplayName("Teste de requisicoes")
    void consultar_cenario2_get() throws Exception {

        List<Depoimentos> allDepoimentos = insertDepoimentos();

        when(service.getDepoimentoAleatorio()).thenReturn(allDepoimentos);

        var json = depoimenotsAleatorios.write(allDepoimentos).getJson();

        var response = mvc
                .perform(get("/depoimentos-home"))
                .andReturn().getResponse();


        assertThat(response.getContentAsString()).isEqualTo(json);
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }
    @Test
    @DisplayName("Teste de requisicoes")
    void consultar_cenario3_get() throws Exception {

        Depoimentos depoimento = new Depoimentos(null,"jose","lugar maravilhoso");

        when(service.getDepoimentosById(any())).thenReturn(depoimento);

        var json = dadosDepoimentos.write(depoimento).getJson();

        var response = mvc
                .perform(get("/depoimentos/{id}",userId))
                .andReturn().getResponse();


        assertThat(response.getContentAsString()).isEqualTo(json);
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }
    @Test
    @DisplayName("Teste de requisicoes")
    void deletar_cenario1_delete() throws Exception {

        doNothing().when(service).delete(userId);

        mvc.perform(delete("/depoimentos/"+userId.toString())
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());
    }
    @Test
    @DisplayName("Teste de requisicoes")
    void deletar_cenario2_delete() throws Exception {
        var dados = new Depoimentos(87L,"jose","lugar maravilhoso");


        Mockito.doThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Place not found")).when(service).delete(dados.getId());


        mvc.perform(delete("/depoimentos/" + dados.getId().toString())
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());

    }
    @Test
    @DisplayName("Deveria devolver codigo http 400 quando informacoes estao invalidas")
    void cadastrar_cenario1_post() throws Exception {
        var response = mvc
                .perform(post("/depoimentos"))
                .andReturn().getResponse();

        assertThat(response.getStatus())
                .isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Deveria devolver codigo http 200 quando informacoes estivrem validas")
    void cadastrar_cenario2_post() throws Exception {
        var dados = new Depoimentos(null,"jose","lugar maravilhoso");
        when(service.insert(any())).thenReturn(dados);

        var json = dadosDepoimentos.write(dados).getJson();

        var response = mvc
                .perform(post("/depoimentos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andReturn().getResponse();


        assertThat(response.getContentAsString()).isEqualTo(json);
        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
    }

    @Test
    @DisplayName("Deveria devolver codigo http 400 quando informacoes estao invalidas")
    void alterar_cenario1_put() throws Exception {
        var response = mvc
                .perform(put("/depoimentos/{id}",userId))
                .andReturn().getResponse();

        assertThat(response.getStatus())
                .isEqualTo(HttpStatus.BAD_REQUEST.value());
    }
    @Test
    @DisplayName("Deveria devolver codigo http 200 quando informacoes estivrem validas")
    void alterar_cenario2_put() throws Exception {
        var dados = new Depoimentos(userId,"novo nome","novo depoimento");
        
        when(service.update(eq(dados.getId()),any())).thenReturn(dados);

        var json = dadosDepoimentos.write(dados).getJson();

        var response = mvc
                .perform(put("/depoimentos/{id}",userId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andReturn().getResponse();
        
        assertThat(response.getContentAsString()).isEqualTo(json);
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }


    private List<Depoimentos> insertDepoimentos(){
        List<Depoimentos> allDepoimentos = new ArrayList<Depoimentos>();
        allDepoimentos.add(new Depoimentos(1l,"jose","teste"));
        allDepoimentos.add(new Depoimentos(2l,"jose","teste"));
        allDepoimentos.add(new Depoimentos(3l,"jose","teste"));
        return allDepoimentos;
    }
}
