package med.voll.api.domain.consulta.validacoes.agendamento;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;

@Component
public class ValidadorHorarioFuncionamentoClinica implements ValidadorAgendamentoDeConsulta{

    public void validar (DadosAgendamentoConsulta dados) {
        var dataConsulta = dados.data();
        var domingo = dataConsulta.getDayOfWeek().equals(DayOfWeek.SUNDAY);
        boolean antesDaAbertura = dataConsulta.getHour() < 7;
        boolean depoisDoFechamento = dataConsulta.getHour() > 18;

        if (domingo || antesDaAbertura || depoisDoFechamento) {
            throw new ValidacaoException("Consulta fora do horário de funcionamento da clínica");
        }
    }
}
