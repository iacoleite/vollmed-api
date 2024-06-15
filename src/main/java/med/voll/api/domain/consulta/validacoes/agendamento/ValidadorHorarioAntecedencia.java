package med.voll.api.domain.consulta.validacoes.agendamento;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class ValidadorHorarioAntecedencia implements ValidadorAgendamentoDeConsulta {

    public void validar(DadosAgendamentoConsulta dados) {
        var dataConsulta = dados.data();
        var agora = LocalDateTime.now();

        var diferencaEmMinutos = Duration.between(agora, dataConsulta).toMinutes();
        if (diferencaEmMinutos < 30) {
            throw new ValidacaoException("A consulta deve ser agendada com ao menos 30 minutos de antecedência.");
        }

// evitando o cálculo explícito:
//        boolean antecedenciaSuficiente = dataConsulta.isBefore(agora.minusMinutes(30));
//
//        if (!antecedenciaSuficiente) {
//            throw new ValidacaoException("A consulta deve ser agendada com ao menos 30 minutos de antecedência.");
//        }

    }
}
