package api.endeavorbackend.repositorios;

import api.endeavorbackend.enuns.StatusCronometro;
import api.endeavorbackend.models.TempoMateria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TempoMateriaRepository extends JpaRepository<TempoMateria, Long> {

    @Query("SELECT t FROM TempoMateria t " +
            "WHERE t.materia.id = :idMateria " +
            "AND t.usuario.id = :usuarioId " +
            "AND FUNCTION('DATE', t.inicio) = :date")
    List<TempoMateria> findByMateriaAndUsuarioAndDate(
            @Param("idMateria") Long idMateria,
            @Param("usuarioId") Long usuarioId,
            @Param("date") LocalDate date
    );

    @Query("SELECT t FROM TempoMateria t " +
            "WHERE t.usuario.id = :usuarioId " +
            "AND FUNCTION('DATE', t.inicio) = :date")
    List<TempoMateria> findByUsuarioAndDate(
            @Param("usuarioId") Long usuarioId,
            @Param("date") LocalDate date
    );

    @Query("SELECT t FROM TempoMateria t " +
            "WHERE t.materia.id = :idMateria " +
            "AND t.usuario.id = :usuarioId")
    List<TempoMateria> findByMateriaAndUsuario(
            @Param("idMateria") Long idMateria,
            @Param("usuarioId") Long usuarioId
    );

    @Query("SELECT t FROM TempoMateria t " +
            "WHERE t.materia.id = :idMateria " +
            "AND t.usuario.id = :usuarioId " +
            "AND t.inicio >= :inicioSemana " +
            "AND t.fim <= :fimSemana")
    List<TempoMateria> findByMateriaAndUsuarioAndSemana(
            @Param("idMateria") Long idMateria,
            @Param("usuarioId") Long usuarioId,
            @Param("inicioSemana") LocalDateTime inicioSemana,
            @Param("fimSemana") LocalDateTime fimSemana
    );

    @Query("SELECT t FROM TempoMateria t " +
            "WHERE t.usuario.id = :usuarioId " +
            "AND t.inicio >= :inicioSemana " +
            "AND t.fim <= :fimSemana")
    List<TempoMateria> findByUsuarioAndSemana(
            @Param("usuarioId") Long usuarioId,
            @Param("inicioSemana") LocalDateTime inicioSemana,
            @Param("fimSemana") LocalDateTime fimSemana
    );

    boolean existsByUsuarioIdAndMateriaIdAndStatus(Long usuarioId, Long materiaId, StatusCronometro statusCronometro);

    @Query("SELECT t FROM TempoMateria t " +
            "WHERE t.usuario.id = :usuarioId " +
            "AND t.materia.id = :materiaId " +
            "AND t.status IN (:statuses) " +
            "AND t.inicio >= :inicioDoDia " +
            "AND t.inicio < :fimDoDia")
    TempoMateria getTempoMateriaByUsuarioIdAndMateriaId(@Param("usuarioId") Long usuarioId,
                                                        @Param("materiaId") Long materiaId,
                                                        @Param("statuses") List<StatusCronometro> statuses,
                                                        @Param("inicioDoDia") LocalDateTime inicioDoDia,
                                                        @Param("fimDoDia") LocalDateTime fimDoDia);
}
