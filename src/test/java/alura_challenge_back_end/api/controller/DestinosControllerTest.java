// package alura_challenge_back_end.api.controller;

// import org.apache.tomcat.util.http.parser.MediaType;
// import org.junit.jupiter.api.DisplayName;
// import org.junit.jupiter.api.Test;
// import org.mockito.Mockito;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
// import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.boot.test.json.JacksonTester;
// import org.springframework.boot.test.mock.mockito.MockBean;
// import org.springframework.http.HttpStatus;
// import org.springframework.mock.web.MockMultipartFile;
// import org.springframework.test.web.servlet.MockMvc;
// import alura_challenge_back_end.api.entities.Destinos;
// import alura_challenge_back_end.api.repository.DestinosRepository;
// import alura_challenge_back_end.api.service.DestinosService;

// import static org.assertj.core.api.Assertions.assertThat;
// import static org.mockito.ArgumentMatchers.*;
// import static org.mockito.Mockito.when;
// import static org.mockito.Mockito.doNothing;
// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// import java.util.ArrayList;
// import java.util.List;

// @SpringBootTest
// @AutoConfigureMockMvc
// @AutoConfigureJsonTesters
// public class DestinosControllerTest {

//     @Autowired
//     private MockMvc mvc;

//     @Autowired
//     private JacksonTester<Destinos> dadosDestinos;
//     @Autowired
//     private JacksonTester<List<Destinos>> paginaDestinos;
    
//     @MockBean
//     private DestinosService service;
//     @MockBean
//     private DestinosRepository repository;
        
//     private Long userId = 1L;

//     @Test
//     @DisplayName("Validar codigo 200")
//     void consultaCenarioGet1() throws Exception{
//         List<Destinos> lista = insertDestinos();
//         when(service.getDestino(anyString())).thenReturn(lista);

//         var json = paginaDestinos.write(lista).getJson();

//         var response = mvc
//                 .perform(get("/destinos"))
//                 .andReturn().getResponse();


//         assertThat(response.getContentAsString()).isEqualTo(json);
//         assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
//     }
//     @Test
//     @DisplayName("Validar codigo 200")
//     void cadastroCenarioPut1() throws Exception{
//         List<String> fotos = insertFotos();
//         fotos.add("src/main/resources/images/194.png");
//         fotos.add("src/main/resources/images/448.png");
//         Destinos d = new Destinos(1L,"aracoiaba",fotos,2000.00);
//         when(service.insert(any())).thenReturn(d);

//         MockMultipartFile file = new MockMultipartFile(
//                         "file",
//                         "test.png",
//                         org.springframework.http.MediaType.APPLICATION_,
//                         "<<pdf data>>".getBytes(StandardCharsets.UTF_8));

//         var json = dadosDestinos.write(d).getJson();

//         var response = mvc
//                 .perform(put("/destinos"))
//                 .andReturn().getResponse();


//         assertThat(response.getContentAsString()).isEqualTo(json);
//         assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
//     }


//     private List<Destinos> insertDestinos(){
//         List<Destinos> allDestinos = new ArrayList<Destinos>();
//         List<String> fotos = insertFotos();
//         allDestinos.add(new Destinos(1l,"aracoiaba",fotos,2000.00));
//         allDestinos.add(new Destinos(2l,"sorocaba",fotos,2000.00));
//         allDestinos.add(new Destinos(3l,"paris",fotos,2000.00));
//         return allDestinos;
//     }
//     private List<String> insertFotos(){
//         List<String> fotos = new ArrayList<String>();
//         fotos.add("src/main/resources/images/194.png");
//         fotos.add("src/main/resources/images/448.png");
//         return fotos;
//     }
// }
