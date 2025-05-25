package api.endeavorbackend.models.exceptions.grupoEstudo;

public class GrupoEstudoNaoEncontradoException extends RuntimeException{

    public GrupoEstudoNaoEncontradoException() {
        super("Não foi possivel encontrar o grupo de estudo solicitado");
    }
}
