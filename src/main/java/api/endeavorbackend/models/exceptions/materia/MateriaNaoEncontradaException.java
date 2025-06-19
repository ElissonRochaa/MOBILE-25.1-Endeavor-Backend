package api.endeavorbackend.models.exceptions.materia;

public class MateriaNaoEncontradaException extends RuntimeException {
    public MateriaNaoEncontradaException() {
        super("Matéria não Encontrada");
    }
}
