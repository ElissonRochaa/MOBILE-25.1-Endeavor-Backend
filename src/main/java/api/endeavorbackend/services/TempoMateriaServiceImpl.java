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
import java.time.LocalDateTime;
import java.util.Arrays;
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

        if (existeSessao(usuarioId, materiaId, StatusCronometro.EM_ANDAMENTO)) {
            throw new IllegalStateException("Já existe uma sessão em andamento.");
        }

        if (existeSessao(usuarioId, materiaId, StatusCronometro.PAUSADO)) {
            return continuarSessao(buscarSessaoPorUsuarioIdMateria(usuarioId, materiaId).getId());
        }

            TempoMateria tempoMateria = new TempoMateria();
            tempoMateria.setUsuario(usuario);
            tempoMateria.setMateria(materia);
            tempoMateria.setInicio(new Timestamp(System.currentTimeMillis()));
            tempoMateria.setStatus(StatusCronometro.EM_ANDAMENTO);
            tempoMateria.setTempoTotalAcumulado(0L);


            return tempoMateriaRepository.save(tempoMateria);

    }

    public TempoMateria pausarSessao(Long id) {
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

    public TempoMateria continuarSessao(Long id) {
        TempoMateria sessao = tempoMateriaRepository.findById(id).orElseThrow(() -> new RuntimeException("Sessão não encontrada"));

        if (sessao.getStatus() != StatusCronometro.PAUSADO) {
            throw new IllegalStateException("A sessão não está pausada.");
        }

        sessao.setInicio(new Timestamp(System.currentTimeMillis()));
        sessao.setStatus(StatusCronometro.EM_ANDAMENTO);

        return tempoMateriaRepository.save(sessao);
    }

    public TempoMateria finalizarSessao(Long id) {
        Optional<TempoMateria> tempoMateriaOptional = tempoMateriaRepository.findById(id);
        if (tempoMateriaOptional.isPresent()) {
            TempoMateria tempoMateria = tempoMateriaOptional.get();
            if (tempoMateria.getStatus() == StatusCronometro.EM_ANDAMENTO || tempoMateria.getStatus() == StatusCronometro.PAUSADO) {
                long tempoFinalizado = (System.currentTimeMillis() - tempoMateria.getInicio().getTime()) / 1000;
                tempoMateria.setTempoTotalAcumulado(tempoMateria.getTempoTotalAcumulado() + tempoFinalizado);
                tempoMateria.setFim(new Timestamp(System.currentTimeMillis()));
                tempoMateria.setStatus(StatusCronometro.FINALIZADO);
                return tempoMateriaRepository.save(tempoMateria);
            }
            throw new RuntimeException("A sessão já está finalizada");
        }
        throw new RuntimeException("Sessão de estudo não encontrada");
    }

    public void deleteSessao(Long id) {
        tempoMateriaRepository.delete(buscar(id));
    }

    public List<TempoMateria> listar() {
        return tempoMateriaRepository.findAll();
    }

    private boolean existeSessao(Long usuarioId, Long materiaId, StatusCronometro status) {
        return tempoMateriaRepository.existsByUsuarioIdAndMateriaIdAndStatus(usuarioId, materiaId, status);
    }

    public TempoMateria buscar(Long id) {
        return tempoMateriaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Sessão não encontrada."));
    }


    public TempoMateria buscarSessaoPorUsuarioIdMateria(Long usuarioId, Long materiaId){
        LocalDateTime inicioDoDia = LocalDate.now().atStartOfDay();
        LocalDateTime fimDoDia = inicioDoDia.plusDays(1);

        List<StatusCronometro> statusList = Arrays.asList(StatusCronometro.EM_ANDAMENTO, StatusCronometro.PAUSADO);
        return tempoMateriaRepository.getTempoMateriaByUsuarioIdAndMateriaId(usuarioId, materiaId, statusList, inicioDoDia, fimDoDia);
    }

}
