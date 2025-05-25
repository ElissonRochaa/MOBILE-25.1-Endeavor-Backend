package api.endeavorbackend.models.exceptions.areaEstudo;

public class AreaEstudoNaoEncontradaException extends RuntimeException{

    public AreaEstudoNaoEncontradaException() {
        super("Não foi possivel encontrar a área de estudo solicitada");
    }
}
