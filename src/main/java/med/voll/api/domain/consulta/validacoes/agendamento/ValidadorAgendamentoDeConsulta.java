package med.voll.api.domain.consulta.validacoes.agendamento;

import med.voll.api.domain.consulta.DadosAgendamentoConsulta;

public interface ValidadorAgendamentoDeConsulta {

    // não é necessário public pq é implícito que todas os métodos de interfaces são públicos
    void validar(DadosAgendamentoConsulta dados);
}
