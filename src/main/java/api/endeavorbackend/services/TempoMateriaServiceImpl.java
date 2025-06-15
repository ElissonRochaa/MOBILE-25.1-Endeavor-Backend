package api.endeavorbackend.services;

import api.endeavorbackend.models.enuns.StatusCronometro;
import api.endeavorbackend.models.Materia;
import api.endeavorbackend.models.TempoMateria;
import api.endeavorbackend.models.Usuario;
import api.endeavorbackend.repositorios.MateriaRepository;
import api.endeavorbackend.repositorios.TempoMateriaRepository;
import api.endeavorbackend.repositorios.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import java.time.ZoneId;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Stream;

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

    public TempoMateria iniciarSessao(UUID usuarioId, UUID materiaId) throws RuntimeException {

        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
        Materia materia = materiaRepository.findById(materiaId)
                .orElseThrow(() -> new EntityNotFoundException("Matéria não encontrada"));

        if (existeSessao(usuarioId, materiaId, StatusCronometro.EM_ANDAMENTO)) {
            TempoMateria sessao = buscarSessaoPorUsuarioIdMateria(usuarioId, materiaId);

            LocalDate inicioLocalDate = sessao.getInicio().toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();
            if (inicioLocalDate.isBefore(LocalDate.now())) {
                finalizarSessao(sessao.getId());
            } else {
                throw new IllegalStateException("Já existe uma sessão em andamento.");
            }
        }

        if (existeSessao(usuarioId, materiaId, StatusCronometro.PAUSADO)) {
            TempoMateria sessao = buscarSessaoPorUsuarioIdMateria(usuarioId, materiaId);

            LocalDate inicioLocalDate = sessao.getInicio().toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();
            if (inicioLocalDate.isBefore(LocalDate.now())) {
                finalizarSessao(sessao.getId());
            } else {
                return continuarSessao(buscarSessaoPorUsuarioIdMateria(usuarioId, materiaId).getId());
            }
        }

        if (!tempoMateriaRepository.getTempoMateriaByStatusAndUsuarioId(StatusCronometro.EM_ANDAMENTO, usuarioId).isEmpty()) {
            throw new IllegalStateException("Já existe uma matéria em andamento");
        }

        TempoMateria tempoMateria = new TempoMateria();
        tempoMateria.setUsuario(usuario);
        tempoMateria.setMateria(materia);
        tempoMateria.setInicio(new Timestamp(System.currentTimeMillis()));
        tempoMateria.setStatus(StatusCronometro.EM_ANDAMENTO);
        tempoMateria.setTempoTotalAcumulado(0L);


        return tempoMateriaRepository.save(tempoMateria);

    }

    public TempoMateria pausarSessao(UUID id) {
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

    public TempoMateria continuarSessao(UUID id) {
        TempoMateria sessao = tempoMateriaRepository.findById(id).orElseThrow(() -> new RuntimeException("Sessão não encontrada"));

        if (sessao.getStatus() != StatusCronometro.PAUSADO) {
            throw new IllegalStateException("A sessão não está pausada.");
        }

        if (!tempoMateriaRepository.getTempoMateriaByStatusAndUsuarioId(StatusCronometro.EM_ANDAMENTO, sessao.getUsuario().getId()).isEmpty()) {
            throw new IllegalStateException("Já existe uma matéria em andamento");
        }

        sessao.setInicio(new Timestamp(System.currentTimeMillis()));
        sessao.setStatus(StatusCronometro.EM_ANDAMENTO);

        return tempoMateriaRepository.save(sessao);
    }

    public TempoMateria finalizarSessao(UUID id) {
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

    public UUID deleteSessao(UUID id) {
        if (tempoMateriaRepository.findById(id).isPresent()) {
            tempoMateriaRepository.deleteById(id);
            return id;
        } else {
            throw new RuntimeException("Sessão não existe com id " + id);
        }
    }

    @Override
    public List<TempoMateria> buscarPorStatusUsuario(StatusCronometro status, UUID usuarioId) {
        if (tempoMateriaRepository.getTempoMateriaByStatusAndUsuarioId(status, usuarioId).isEmpty()) {
            throw new RuntimeException("Não existe sessão com esse status para esse usuário");
        } else {
            return tempoMateriaRepository.getTempoMateriaByStatusAndUsuarioId(status, usuarioId);
        }
    }

    @Override
    public List<TempoMateria> buscarSessoesDeHojePorUsuario(UUID usuarioId) {
        LocalDate hoje = LocalDate.now();
        LocalDateTime inicioDoDia = hoje.atStartOfDay();
        LocalDateTime fimDoDia = hoje.plusDays(1).atStartOfDay().minusNanos(1);

        Timestamp inicio = Timestamp.valueOf(inicioDoDia);
        Timestamp fim = Timestamp.valueOf(fimDoDia);

        return tempoMateriaRepository.findByUsuarioIdAndInicioBetween(usuarioId, inicio, fim);
    }

    @Override
    public List<TempoMateria> buscarPorUsuario(UUID usuarioId) {
        finalizarSessoesAntigas(usuarioId);
        return tempoMateriaRepository.findByUsuario(usuarioRepository.findById(usuarioId).isPresent() ? usuarioRepository.findById(usuarioId).get() : null);
    }

    @Override
    public void finalizarSessaoComFimPersonalizado(TempoMateria sessao, Timestamp fimPersonalizado) {
        if (sessao.getStatus() == StatusCronometro.EM_ANDAMENTO || sessao.getStatus() == StatusCronometro.PAUSADO) {
            long tempoFinalizado = (fimPersonalizado.getTime() - sessao.getInicio().getTime()) / 1000;
            sessao.setTempoTotalAcumulado(sessao.getTempoTotalAcumulado() + tempoFinalizado);
            sessao.setFim(fimPersonalizado);
            sessao.setStatus(StatusCronometro.FINALIZADO);
            tempoMateriaRepository.save(sessao);
        }
    }

    public List<TempoMateria> listar() {
        return tempoMateriaRepository.findAll();
    }

    private boolean existeSessao(UUID usuarioId, UUID materiaId, StatusCronometro status) {
        return tempoMateriaRepository.existsByUsuarioIdAndMateriaIdAndStatus(usuarioId, materiaId, status);
    }

    public TempoMateria buscar(UUID id) {
        return tempoMateriaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Sessão não encontrada."));
    }


    public TempoMateria buscarSessaoPorUsuarioIdMateria(UUID usuarioId, UUID materiaId) {

        List<StatusCronometro> statusList = Arrays.asList(StatusCronometro.EM_ANDAMENTO, StatusCronometro.PAUSADO);

        return tempoMateriaRepository.getTempoMateriaByUsuarioIdAndMateriaId(usuarioId, materiaId, statusList);
    }

    public TempoMateria buscarSessaoPorUsuarioIdMateriaAtiva(UUID usuarioId, UUID materiaId) {

        List<StatusCronometro> statusList = Arrays.asList(StatusCronometro.EM_ANDAMENTO, StatusCronometro.PAUSADO);

        return tempoMateriaRepository.getTempoMateriaByUsuarioIdAndMateriaId(usuarioId, materiaId, statusList);
    }

    private void finalizarSessoesAntigas(UUID usuarioId) {
        List<TempoMateria> sessoes = Stream.of(StatusCronometro.EM_ANDAMENTO, StatusCronometro.PAUSADO)
                .flatMap(status -> tempoMateriaRepository
                        .getTempoMateriaByStatusAndUsuarioId(status, usuarioId)
                        .stream())
                .toList();

        LocalDate hoje = LocalDate.now();

        for (TempoMateria sessao : sessoes) {
            LocalDate dataInicio = sessao.getInicio().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            if (dataInicio.isBefore(hoje)) {
                Timestamp fimPersonalizado;

                if (sessao.getFim() != null) {
                    fimPersonalizado = sessao.getFim();
                } else {
                    LocalDateTime fimDoDia = dataInicio.atTime(23, 59, 59, 999_000_000);
                    fimPersonalizado = Timestamp.valueOf(fimDoDia);
                }

                finalizarSessaoComFimPersonalizado(sessao, fimPersonalizado);

            }
        }
    }
}