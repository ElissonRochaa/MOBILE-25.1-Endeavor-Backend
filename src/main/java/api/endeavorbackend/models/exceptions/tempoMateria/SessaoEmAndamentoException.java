package api.endeavorbackend.models.exceptions.tempoMateria;

public class SessaoEmAndamentoException extends RuntimeException{
    public SessaoEmAndamentoException() {
        super("Já existe uma sessão em andamento, pausar ela para iniciar nova sessão");
    }
}
