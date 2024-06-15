package med.voll.api.domain.medico;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import med.voll.api.domain.endereco.DadosEndereco;

public record DadosCadastroMedico(
        @NotBlank (message = "Necessário inserir nome")
        String nome,
        @NotBlank (message = "Necessário inserir e-mail")
        @Email
        String email,
        @NotBlank (message = "Necessário inserir telefone")
        @Pattern(regexp="\\d{7,14}")
        String telefone,
        @NotBlank (message = "Necessário inserir CRM")
        @Pattern(regexp="\\d{4,6}")
        String crm,
        @NotNull (message = "Necessário inserir especialidade")
        Especialidade especialidade,
        @NotNull (message = "Necessário inserir endereço")
        @Valid
        DadosEndereco endereco) {
}
