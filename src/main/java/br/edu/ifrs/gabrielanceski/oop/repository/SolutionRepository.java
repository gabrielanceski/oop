package br.edu.ifrs.gabrielanceski.oop.repository;

import br.edu.ifrs.gabrielanceski.oop.model.Solution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SolutionRepository extends JpaRepository<Solution, Integer> {

    @Query("SELECT s FROM Solution s WHERE s.item.id = ?1")
    List<Solution> findAllByItemId(int itemId);

    @Query("SELECT s FROM Solution s WHERE s.item.model = ?1")
    List<Solution> findAllByItemModel(String model);

}
