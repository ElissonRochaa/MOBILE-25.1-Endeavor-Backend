package api.endeavorbackend.repositorios;

import api.endeavorbackend.models.enuns.StatusCronometro;
import api.endeavorbackend.models.TempoMateria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface TempoMateriaRepository extends JpaRepository<TempoMateria, UUID> {

    @Query("SELECT t FROM TempoMateria t " +
            "WHERE t.materia.id = :idMateria " +
            "AND t.usuario.id = :usuarioId " +
            "AND FUNCTION('DATE', t.inicio) = :date")
    List<TempoMateria> findByMateriaAndUsuarioAndDate(
            @Param("idMateria") UUID idMateria,
            @Param("usuarioId") UUID usuarioId,
            @Param("date") LocalDate date
    );

    @Query("SELECT t FROM TempoMateria t " +
            "WHERE t.usuario.id = :usuarioId " +
            "AND FUNCTION('DATE', t.inicio) = :date")
    List<TempoMateria> findByUsuarioAndDate(
            @Param("usuarioId") UUID usuarioId,
            @Param("date") LocalDate date
    );

    @Query("SELECT t FROM TempoMateria t " +
            "WHERE t.materia.id = :idMateria " +
            "AND t.usuario.id = :usuarioId")
    List<TempoMateria> findByMateriaAndUsuario(
            @Param("idMateria") UUID idMateria,
            @Param("usuarioId") UUID usuarioId
    );

    @Query("SELECT t FROM TempoMateria t " +
            "WHERE t.materia.id = :idMateria " +
            "AND t.usuario.id = :usuarioId " +
            "AND t.inicio >= :inicioSemana " +
            "AND t.fim <= :fimSemana")
    List<TempoMateria> findByMateriaAndUsuarioAndSemana(
            @Param("idMateria") UUID idMateria,
            @Param("usuarioId") UUID usuarioId,
            @Param("inicioSemana") LocalDateTime inicioSemana,
            @Param("fimSemana") LocalDateTime fimSemana
    );

    @Query("SELECT t FROM TempoMateria t " +
            "WHERE t.usuario.id = :usuarioId " +
            "AND t.inicio >= :inicioSemana " +
            "AND t.fim <= :fimSemana")
    List<TempoMateria> findByUsuarioAndSemana(
            @Param("usuarioId") UUID usuarioId,
            @Param("inicioSemana") LocalDateTime inicioSemana,
            @Param("fimSemana") LocalDateTime fimSemana
    );

    boolean existsByUsuarioIdAndMateriaIdAndStatus(UUID usuarioId, UUID materiaId, StatusCronometro statusCronometro);

    @Query("SELECT t FROM TempoMateria t " +
            "WHERE t.usuario.id = :usuarioId " +
            "AND t.materia.id = :materiaId " +
            "AND t.status IN (:statuses) ")
    TempoMateria getTempoMateriaByUsuarioIdAndMateriaId(@Param("usuarioId") UUID usuarioId,
                                                        @Param("materiaId") UUID materiaId,
                                                        @Param("statuses") List<StatusCronometro> statuses);

    @Query("SELECT t FROM TempoMateria t " +
            "WHERE t.usuario.id = :usuarioId " +
            "AND t.materia.id = :materiaId " +
            "AND t.status IN (:statuses) " +
            "AND t.inicio >= :inicioDoDia " +
            "AND t.inicio < :fimDoDia")
    TempoMateria getTempoMateriaByUsuarioIdAndMateriaIdAtiva(@Param("usuarioId") UUID usuarioId,
                                                            @Param("materiaId") UUID materiaId,
                                                            @Param("statuses") List<StatusCronometro> statuses,
                                                            @Param("inicioDoDia") LocalDateTime inicioDoDia,
                                                            @Param("fimDoDia") LocalDateTime fimDoDia);

    List<TempoMateria> getTempoMateriaByStatusAndUsuarioId(StatusCronometro status, UUID usuario_id);
}
