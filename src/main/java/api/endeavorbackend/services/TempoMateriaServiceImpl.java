package api.endeavorbackend.services;

import api.endeavorbackend.enuns.StatusCronometro;
import api.endeavorbackend.models.Materia;
import api.endeavorbackend.models.TempoMateria;
import api.endeavorbackend.models.Usuario;
import api.endeavorbackend.repositorios.MateriaRepository;
import api.endeavorbackend.repositorios.TempoMateriaRepository;
import api.endeavorbackend.repositorios.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class TempoMateriaServiceImpl implements TempoMateriaService {
    private final TempoMateriaRepository tempoMateriaRepository;
    private final UsuarioRepository usuarioRepository;
    private final MateriaRepository materiaRepository;

    public TempoMateriaServiceImpl(TempoMateriaRepository tempoMateriaRepository, UsuarioRepository usuarioRepository, MateriaRepository materiaRepository) {
        this.tempoMateriaRepository = tempoMateriaRepository;
        this.usuarioRepository = usuarioRepository;
        this.materiaRepository = materiaRepository;
    }

    public TempoMateria iniciarSessao(Long usuarioId, Long materiaId) throws RuntimeException {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
        Materia materia = materiaRepository.findById(materiaId)
                .orElseThrow(() -> new EntityNotFoundException("Matéria não encontrada"));

        if (existeSessaoEmAndamento(usuarioId, materiaId)) {
            throw new RuntimeException("Já existe uma sessão em andamento para este usuário e matéria.");
        }

        TempoMateria tempoMateria = new TempoMateria();
        tempoMateria.setUsuario(usuario);
        tempoMateria.setMateria(materia);
        tempoMateria.setInicio(new Timestamp(System.currentTimeMillis()));
        tempoMateria.setStatus(StatusCronometro.EM_ANDAMENTO);
        tempoMateria.setTempoTotalAcumulado(0L);


        return tempoMateriaRepository.save(tempoMateria);
    }

    public TempoMateria pausarTempoMateria(Long id) {
        Optional<TempoMateria> tempoMateriaOptional = tempoMateriaRepository.findById(id);
        if (tempoMateriaOptional.isPresent()) {
            TempoMateria tempoMateria = tempoMateriaOptional.get();
            if (tempoMateria.getStatus() == StatusCronometro.EM_ANDAMENTO) {
                long tempoPausado = (System.currentTimeMillis() - tempoMateria.getInicio().getTime()) / 1000;
                tempoMateria.setTempoTotalAcumulado(tempoMateria.getTempoTotalAcumulado() + tempoPausado);
                tempoMateria.setFim(new Timestamp(System.currentTimeMillis()));
                tempoMateria.setStatus(StatusCronometro.PAUSADO);
                return tempoMateriaRepository.save(tempoMateria);
            }
            throw new RuntimeException("A sessão já está pausada ou finalizada.");
        }
        throw new RuntimeException("Sessão de estudo não encontrada.");
    }

    public TempoMateria continuarTempoMateria(Long id) {
        Optional<TempoMateria> tempoMateriaOptional = tempoMateriaRepository.findById(id);
        if (tempoMateriaOptional.isPresent()) {
            TempoMateria tempoMateria = tempoMateriaOptional.get();
            if (tempoMateria.getStatus() == StatusCronometro.PAUSADO) {
                tempoMateria.setInicio(new Timestamp(System.currentTimeMillis()));
                tempoMateria.setStatus(StatusCronometro.EM_ANDAMENTO);
                return tempoMateriaRepository.save(tempoMateria);
            }
            throw new RuntimeException("A sessão já está em andamento ou finalizada.");
        }
        throw new RuntimeException("Sessão de estudo não encontrada.");
    }

    //a única diferença entre esse finalizar e pausar é que depois do finalizar não tem volta. Pensando em deixar só o pausar mesmo e finalizar automático quando mudar o dia

    public TempoMateria finalizarTempoMateria(Long id) {
        Optional<TempoMateria> tempoMateriaOptional = tempoMateriaRepository.findById(id);
        if (tempoMateriaOptional.isPresent()) {
            TempoMateria tempoMateria = tempoMateriaOptional.get();
            if (tempoMateria.getStatus() == StatusCronometro.EM_ANDAMENTO) {
                long tempoFinalizado = (System.currentTimeMillis() - tempoMateria.getInicio().getTime()) / 1000;
                tempoMateria.setTempoTotalAcumulado(tempoMateria.getTempoTotalAcumulado() + tempoFinalizado);
                tempoMateria.setFim(new Timestamp(System.currentTimeMillis()));
                tempoMateria.setStatus(StatusCronometro.FINALIZADO);
                return tempoMateriaRepository.save(tempoMateria);
            }
            throw new RuntimeException("A sessão já está finalizada ou pausada.");
        }
        throw new RuntimeException("Sessão de estudo não encontrada.");
    }

    public void deleteTempoMateria(TempoMateria tempoMateria) {
        tempoMateriaRepository.delete(tempoMateria);
    }

    public List<TempoMateria> listar() {
        return tempoMateriaRepository.findAll();
    }

    public Optional<TempoMateria> buscar(Long id) {
        return tempoMateriaRepository.findById(id);
    }

    public Long getTotalTempoMateria(Long idMateria) {
        long total = 0;
        for (TempoMateria tempoMateria : tempoMateriaRepository.getTempoMateria(idMateria)) {
            total += tempoMateria.getTempoTotalAcumulado();
        }
        return total;
    }

    public Long getTempoNoDia(LocalDate date) {
        long total = 0;
        for (TempoMateria tempoMateria : tempoMateriaRepository.getTempoTotalNoDia(date)) {
            total += tempoMateria.getTempoTotalAcumulado();
        }
        return total;
    }

    public Long getTempoNoDiaPorMateria(Long idMateria, LocalDate date) {
        long total = 0;
        for (TempoMateria tempoMateria : tempoMateriaRepository.getTempoMateriaNoDia(idMateria, date)) {
            total += tempoMateria.getTempoTotalAcumulado();
        }
        return total;
    }

    public Long getTempoNaSemana(LocalDate inicioSemana, LocalDate fimSemana) {
        long total = 0;
        for (TempoMateria tempoMateria : tempoMateriaRepository.getTempoNaSemana(inicioSemana, fimSemana)) {
            total += tempoMateria.getTempoTotalAcumulado();
        }
        return total;
    }

    public Long getTempoNaSemanaPorMateria(Long idMateria, LocalDate inicioSemana, LocalDate fimSemana) {
        long total = 0;
        for (TempoMateria tempoMateria : tempoMateriaRepository.getTempoNaSemanaPorMateria(idMateria, inicioSemana, fimSemana)) {
            total += tempoMateria.getTempoTotalAcumulado();
        }
        return total;
    }

    public Long getDuracaoSessaoEstudo(Long materia) {
        Optional<TempoMateria> tempoMateria = tempoMateriaRepository.findById(materia);
        return tempoMateria
                .map(TempoMateria::getDuracao)
                .orElseThrow(() -> new RuntimeException("Sessão de estudo não encontrada para a matéria: " + materia)); // Se não encontrar, lança a exceção
    }

    public Long getTempoTotalAcumulado(Long id) {
        return tempoMateriaRepository.findById(id).get().getTempoTotalAcumulado();
    }

    public boolean existeSessaoEmAndamento(Long usuarioId, Long materiaId) {
        return tempoMateriaRepository.existsByUsuarioIdAndMateriaIdAndStatus(usuarioId, materiaId, StatusCronometro.EM_ANDAMENTO);
    }

}
