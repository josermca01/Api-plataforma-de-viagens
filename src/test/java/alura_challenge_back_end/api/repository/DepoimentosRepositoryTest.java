// package alura_challenge_back_end.api.repository;

// import static org.assertj.core.api.Assertions.assertThat;

// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
// import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
// import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
// import org.springframework.test.context.ActiveProfiles;

// import alura_challenge_back_end.api.entities.DadosDepoimentos;
// import alura_challenge_back_end.api.entities.Depoimentos;

// @DataJpaTest
// @AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
// @ActiveProfiles("test")
// public class DepoimentosRepositoryTest {
    
//     @Autowired
//     private TestEntityManager em;

//     @Autowired
//     private DepoimentosRepository repository;

//     @Test
//     void testFind() {
//         var depoimento = cadastrarDepoimentos("jose","lugar lindo");

//         var comentario = repository.find(null);

//     }
//     @Test
//     void testFind2() {
//         var depoimento = cadastrarDepoimentos("jose","lugar lindo");

//     }



// private Depoimentos cadastrarDepoimentos(String nome,String depoimento){
//     var comentario = new Depoimentos(dadosDepoimentos(nome,depoimento));
//     em.persist(comentario);
//     return comentario;
// }

// private DadosDepoimentos dadosDepoimentos(String nome , String depoimento){
//     return new DadosDepoimentos(nome,depoimento);
// }
// }
