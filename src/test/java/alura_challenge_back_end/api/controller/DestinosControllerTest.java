package alura_challenge_back_end.api.controller;

import org.springframework.http.MediaType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.server.ResponseStatusException;

import alura_challenge_back_end.api.entities.Destinos;
import alura_challenge_back_end.api.repository.DestinosRepository;
import alura_challenge_back_end.api.service.DestinosService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
public class DestinosControllerTest {
    public static final int BITE_SIZE = 4 * 1024;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<Destinos> dadosDestinos;
    @Autowired
    private JacksonTester<List<Destinos>> paginaDestinos;
    
    @MockBean
    private DestinosService service;
    @MockBean
    private DestinosRepository repository;
        
    private Long userId = 1L;

    @Test
    @DisplayName("Validar codigo 200 da maneira que os dados sao enviados quando solicitados pela requisisao get")
    void consultaCenarioGet1() throws Exception{
        List<Destinos> lista = insertDestinos();
        when(service.getDestino(anyString())).thenReturn(lista);

        var json = paginaDestinos.write(lista).getJson();

        var response = mvc
                .perform(get("/destinos"))
                .andReturn().getResponse();


        assertThat(response.getContentAsString()).isEqualTo(json);
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }
    @Test
    @DisplayName("Validar codigo 200 quando todos os campos sao preechidos")
    void cadastroCenarioPost1() throws Exception{
        MockMultipartFile file = new MockMultipartFile(
                        "image",
                        "test.png",
                        MediaType.IMAGE_PNG_VALUE, new byte[BITE_SIZE]);
        Destinos d = new Destinos(1L,"aracoiaba",file.getOriginalFilename(),null,2000.00);
        when(service.insert(any())).thenReturn(d);

        var json = dadosDestinos.write(d).getJson();

        var response = mvc
                .perform(MockMvcRequestBuilders.multipart("/destinos")
                .file(file)
                .param("nome", "aracoiaba")
                .param("preco", "2000.00"))
                .andReturn().getResponse();

        assertThat(response.getContentAsString()).isEqualTo(json);
        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
    }
    @Test
    @DisplayName("Validar codigo 400 quando nenhum campo e enviado")
    void cadastroCenarioPost2() throws Exception{
        var response = mvc
                .perform(MockMvcRequestBuilders.multipart("/destinos"))
                .andReturn().getResponse();

                assertThat(response.getStatus())
                .isEqualTo(HttpStatus.BAD_REQUEST.value());
    }
    @Test
    @DisplayName("Validar codigo 400 quando o campo do arquivo de imagem nao e enviado")
    void cadastroCenarioPost3() throws Exception{

        var response = mvc
                .perform(MockMvcRequestBuilders.multipart("/destinos")
                .param("nome", "aracoiaba")
                .param("preco", "2000.00"))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }
    @Test
    @DisplayName("Validar codigo 400 quando campo do nome e do preco nao sao enviados")
    void cadastroCenarioPost4() throws Exception{

        MockMultipartFile file = new MockMultipartFile(
                        "image",
                        "test.png",
                        MediaType.IMAGE_PNG_VALUE, new byte[BITE_SIZE]);
        var response = mvc
                .perform(MockMvcRequestBuilders.multipart("/destinos")
                .file(file))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }
    @Test
    @DisplayName("Teste de requisicoes")
    void deletarCenarioDelete1() throws Exception {

        doNothing().when(service).delete(userId);

        mvc.perform(delete("/destinos/"+userId.toString())
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());
    }
    @Test
    @DisplayName("Teste de requisicoes")
    void deletarCenarioDelete2() throws Exception {
        var dados = new Destinos(87L,"aracoiaba","teste.png",null,2000.00);


        Mockito.doThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Place not found")).when(service).delete(dados.getId());


        mvc.perform(delete("/destinos/" + dados.getId().toString())
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());

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
        var dados = new Destinos(userId,"novo nome","test.png",null,900.00);
        
        when(service.update(eq(dados.getId()),any())).thenReturn(dados);

        var json = dadosDestinos.write(dados).getJson();

        var response = mvc
                .perform(put("/destinos/{id}",userId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andReturn().getResponse();
        
        assertThat(response.getContentAsString()).isEqualTo(json);
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }
    private List<Destinos> insertDestinos(){
        List<Destinos> allDestinos = new ArrayList<Destinos>();
        List<String> fotos = insertFotos();
        allDestinos.add(new Destinos(1l,"aracoiaba",fotos.getFirst(),fotos.getLast(),2000.00));
        allDestinos.add(new Destinos(2l,"sorocaba",fotos.getFirst(),fotos.getLast(),2000.00));
        allDestinos.add(new Destinos(3l,"paris",fotos.getFirst(),fotos.getLast(),2000.00));
        return allDestinos;
    }
    private List<String> insertFotos(){
        List<String> fotos = new ArrayList<String>();
        fotos.add("194.png");
        fotos.add("448.png");
        return fotos;
    }
}
