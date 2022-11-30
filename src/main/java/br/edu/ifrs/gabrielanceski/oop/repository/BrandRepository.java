package br.edu.ifrs.gabrielanceski.oop.repository;

import br.edu.ifrs.gabrielanceski.oop.model.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Integer> {

    @Query("SELECT b FROM Brand b WHERE b.name = ?1")
    Optional<Brand> findByName(String name);

}
