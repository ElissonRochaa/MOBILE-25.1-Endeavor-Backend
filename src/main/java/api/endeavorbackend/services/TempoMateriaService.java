package api.endeavorbackend.services;

import api.endeavorbackend.enuns.StatusCronometro;
import api.endeavorbackend.models.TempoMateria;
import api.endeavorbackend.repositorios.TempoMateriaRepository;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class TempoMateriaService {
    private final TempoMateriaRepository tempoMateriaRepository;

    public TempoMateriaService(TempoMateriaRepository tempoMateriaRepository) {
        this.tempoMateriaRepository = tempoMateriaRepository;
    }

    public TempoMateria addTempoMateria(TempoMateria tempoMateria) {
        tempoMateria.setStatus(StatusCronometro.EM_ANDAMENTO); // Define o status como em andamento ao criar
        tempoMateria.setInicio(new Timestamp(System.currentTimeMillis()));
        tempoMateria.setTempoTotalAcumulado(0L); // Inicializa o tempo total acumulado
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
}
