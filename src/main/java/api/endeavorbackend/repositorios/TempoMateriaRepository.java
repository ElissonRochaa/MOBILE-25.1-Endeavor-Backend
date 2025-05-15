package api.endeavorbackend.repositorios;

import api.endeavorbackend.enuns.StatusCronometro;
import api.endeavorbackend.models.TempoMateria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TempoMateriaRepository extends JpaRepository<TempoMateria, Long> {

    @Query(value = "SELECT * FROM tempo_materia " +
            "WHERE id_materia = :idMateria " +
            "AND DATE(inicio) = :date",
            nativeQuery = true)
    List<TempoMateria> getTempoMateriaNoDia(@Param("idMateria") Long idMateria,
                                            @Param("date") LocalDate date);

    @Query(value = "SELECT * FROM tempo_materia " +
            "WHERE DATE(inicio) = :date ",
            nativeQuery = true)
    List<TempoMateria> getTempoTotalNoDia(@Param("date") LocalDate date);

    @Query(value = "SELECT * FROM tempo_materia " +
            "WHERE id_materia = :idMateria",
            nativeQuery = true)
    List<TempoMateria> getTempoMateria(@Param("idMateria") Long idMateria);

    @Query(value = "SELECT * FROM tempo_materia " +
            "WHERE id_materia = :idMateria " +
            "AND inicio >= :inicioSemana " +
            "AND fim <= :fimSemana",
            nativeQuery = true)
    List<TempoMateria> getTempoNaSemanaPorMateria(@Param("idMateria") Long idMateria,
                                                  @Param("inicioSemana") LocalDate inicioSemana,
                                                  @Param("fimSemana") LocalDate fimSemana);


    @Query(value = "SELECT * FROM tempo_materia " +
            "WHERE inicio >= :inicioSemana " +
            "AND fim <= :fimSemana",
            nativeQuery = true)
    List<TempoMateria> getTempoNaSemana(@Param("inicioSemana") LocalDate inicioSemana,
                                        @Param("fimSemana") LocalDate fimSemana);


    boolean existsByUsuarioIdAndMateriaIdAndStatus(Long usuarioId, Long materiaId, StatusCronometro statusCronometro);
}
