//package med.voll.api.domain.consulta.validacoes.cancelamento;
//
//import med.voll.api.domain.ValidacaoException;
//import med.voll.api.domain.consulta.DadosCancelamentoConsulta;
//import med.voll.api.domain.consulta.MotivoCancelamento;
//import org.springframework.stereotype.Component;
//
//import java.util.EnumSet;
//
//@Component
//public class ValidadorDeMotivoDeCancelamento implements ValidadorCancelamentoDeConsultas {
//
//    public void validar(DadosCancelamentoConsulta dados) {
//
//        EnumSet<MotivoCancelamento> motivos = EnumSet.allOf(MotivoCancelamento.class);
//        if (!motivos.contains(dados.motivo())) {
//            throw new ValidacaoException("Motivo inválido.");
//        }
//
//    }
//}
