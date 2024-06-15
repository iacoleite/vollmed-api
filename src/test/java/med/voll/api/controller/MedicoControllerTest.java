package med.voll.api.controller;


import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import med.voll.api.domain.consulta.DadosDetalhamentoConsulta;
import med.voll.api.domain.endereco.DadosEndereco;
import med.voll.api.domain.endereco.Endereco;
import med.voll.api.domain.medico.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
public class MedicoControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private MedicoRepository medicoRepository;

    @Autowired
    private JacksonTester<DadosCadastroMedico> dadosCadastroMedicoJacksonTester;

    @Autowired
    private JacksonTester<DadosDetalhamentoMedico> dadosDetalhamentoMedicoJacksonTester;




    @Test
    @DisplayName("deveria devolver código http 400 quando informações não são válidas")
    @WithMockUser
    void cadastrarCenario1() throws Exception {
        var response = mvc.perform(post("/medicos")).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("deveria devolver código http 200 quando informações são válidas")
    @WithMockUser
    void cadastrarCenario2() throws Exception {
        var dadosEndereco = new DadosEndereco("rua poco", "pocoto", "12234256", "santos", "sp", "12", "12");
        var endereco = new Endereco(dadosEndereco);
        var dadosCadastro = new DadosCadastroMedico("medico", "medico@med.com", "1223412567", "178906",
                Especialidade.CARDIOLOGIA, dadosEndereco);
        var dadosDetalhamentoMedico = new DadosDetalhamentoMedico(null,"medico", "medico@med.com", "1223412567",
                "178906", Especialidade.CARDIOLOGIA, endereco);

        when(medicoRepository.save(any())).thenReturn(new Medico(dadosCadastro));

        var response = mvc.perform(post("/medicos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(dadosCadastroMedicoJacksonTester.write(dadosCadastro).getJson()))
                .andReturn().getResponse();

        var jsonEsperado = dadosDetalhamentoMedicoJacksonTester.write(dadosDetalhamentoMedico).getJson();


        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);
    }


//    @Test
//    @DisplayName("Deveria devolver codigo http 200 quando informacoes estao validas")
//    @WithMockUser
//    void cadastrar_cenario2() throws Exception {
//        var dadosCadastro = new DadosCadastroMedico(
//                "Medico",
//                "medico@voll.med",
//                "61999999999",
//                "123456",
//                Especialidade.CARDIOLOGIA,
//                dadosEndereco());
//
//        when(medicoRepository.save(any())).thenReturn(new Medico(dadosCadastro));
//
//        var response = mvc
//                .perform(post("/medicos")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(dadosCadastroMedicoJacksonTester.write(dadosCadastro).getJson()))
//                .andReturn().getResponse();
//
//        var dadosDetalhamento = new DadosDetalhamentoMedico(
//                null,
//                dadosCadastro.nome(),
//                dadosCadastro.email(),
//                dadosCadastro.telefone(),
//                dadosCadastro.crm(),
//                dadosCadastro.especialidade(),
//                new Endereco(dadosCadastro.endereco())
//        );
//        var jsonEsperado = dadosDetalhamentoMedicoJacksonTester.write(dadosDetalhamento).getJson();
//
//        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
//        assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);
//    }

    private DadosEndereco dadosEndereco() {
        return new DadosEndereco(
                "rua xpto",
                "bairro",
                "00000000",
                "Brasilia",
                "DF",
                null,
                null
        );
    }
}
