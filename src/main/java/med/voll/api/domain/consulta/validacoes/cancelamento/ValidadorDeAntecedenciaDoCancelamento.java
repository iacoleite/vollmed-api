package med.voll.api.domain.consulta.validacoes.cancelamento;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consulta.Consulta;
import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.consulta.DadosCancelamentoConsulta;
import med.voll.api.domain.consulta.DadosDetalhamentoConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;


@Component
public class ValidadorDeAntecedenciaDoCancelamento implements ValidadorCancelamentoDeConsultas {

    @Autowired
    private ConsultaRepository consultaRepository;

    @Override
    public void validar(DadosCancelamentoConsulta dados) {
        var dataDoCancelamento = LocalDateTime.now();
        var consulta = consultaRepository.getReferenceById(dados.idConsulta());
        var dataDaConsulta = consulta.getData();
        boolean antecedencia = dataDoCancelamento.isBefore(dataDaConsulta.minusHours(24));
        if (!antecedencia) {
            throw new ValidacaoException("É necessário 24h de antecedência para cancelar a consulta.");
        }
    }
}
