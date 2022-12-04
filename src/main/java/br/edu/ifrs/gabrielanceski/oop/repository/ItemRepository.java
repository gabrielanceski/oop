package br.edu.ifrs.gabrielanceski.oop.repository;

import br.edu.ifrs.gabrielanceski.oop.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemRepository extends JpaRepository<Item, Integer> {

    @Query("SELECT i FROM Item i WHERE i.name = ?1")
    Optional<Item> findByName(String name);

    @Query("SELECT i FROM Item i WHERE i.brand.id = ?1")
    List<Item> findByBrandId(int brandId);

    @Query("SELECT i FROM Item i WHERE i.name LIKE %?1% AND i.model LIKE %?2%")
    List<Item> filter(String name, String model);

}
