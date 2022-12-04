package br.edu.ifrs.gabrielanceski.oop.service;

import br.edu.ifrs.gabrielanceski.oop.model.Item;
import br.edu.ifrs.gabrielanceski.oop.model.Solution;
import br.edu.ifrs.gabrielanceski.oop.repository.SolutionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SolutionService {
    private final SolutionRepository solutionRepository;

    @Autowired
    public SolutionService(SolutionRepository solutionRepository) {
        this.solutionRepository = solutionRepository;
    }

    public void saveOrUpdate(Solution solution) {
        solutionRepository.save(solution);
    }

    public List<Solution> findAllByItemId(int itemId) {
        if (itemId <= 0) return Collections.emptyList();
        return solutionRepository.findAllByItemId(itemId);
    }

    public List<Solution> findAllByItemModel(String itemModel) {
        if (itemModel.isBlank()) return findAll();
        return solutionRepository.findAllByItemModel(itemModel);
    }

    public List<Solution> findAll() {
        return solutionRepository.findAll();
    }

    public Optional<Solution> findById(int id) {
       if (id <= 0) return Optional.empty();
       return solutionRepository.findById(id);
    }

    public List<Solution> findBy(String itemModel, String itemBrand, String issueOrCause) {
        List<Solution> solutionList = solutionRepository.filter(itemModel, issueOrCause);
        if (itemBrand.isEmpty()) return solutionList;
        else return solutionList.stream()
                .filter(solution -> String.valueOf(solution.getItem().getBrand().getId()).equalsIgnoreCase(itemBrand))
                .collect(Collectors.toList());
    }

    public void delete(int id) {
        solutionRepository.deleteById(id);
    }
}
