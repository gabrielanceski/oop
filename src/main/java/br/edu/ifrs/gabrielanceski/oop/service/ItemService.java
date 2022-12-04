package br.edu.ifrs.gabrielanceski.oop.service;

import br.edu.ifrs.gabrielanceski.oop.model.Item;
import br.edu.ifrs.gabrielanceski.oop.repository.ItemRepository;
import br.edu.ifrs.gabrielanceski.oop.repository.SolutionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ItemService {
    private final ItemRepository itemRepository;
    private SolutionRepository solutionRepository;

    @Autowired
    public ItemService(ItemRepository itemRepository, SolutionRepository solutionRepository) {
        this.itemRepository = itemRepository;
        this.solutionRepository = solutionRepository;
    }

    public List<Item> findAll() {
        return itemRepository.findAll();
    }

    public void saveOrUpdate(Item item) {
        if (item != null) {
            if (item.getBrand() == null || item.getBrand().getId() == 0) throw new IllegalStateException("A marca selecionada é inválida!");
            itemRepository.save(item);
        }
    }

    public Optional<Item> findById(int itemId) {
        return itemRepository.findById(itemId);
    }

    public void delete(int itemId) {
        Optional<Item> itemOptional = findById(itemId);
        if (itemOptional.isPresent()) {
            if (solutionRepository.findAllByItemId(itemId).isEmpty())
                itemRepository.deleteById(itemId);
            else throw new IllegalStateException("Este item possui uma ou mais soluções. Portanto, não será possível removê-lo");
        }
    }

    public List<Item> findBy(String itemName, String itemModel, String itemBrand) {
        List<Item> itemList = itemRepository.filter(itemName, itemModel);
        if (itemBrand.isEmpty()) return itemList;
        else return itemList.stream()
                .filter(item -> String.valueOf(item.getBrand().getId()).equalsIgnoreCase(itemBrand))
                .collect(Collectors.toList());
    }

    public List<Item> findByBrand(int brandId) {
        return itemRepository.findByBrandId(brandId);
    }
}
