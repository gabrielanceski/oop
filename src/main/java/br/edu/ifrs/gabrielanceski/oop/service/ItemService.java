package br.edu.ifrs.gabrielanceski.oop.service;

import br.edu.ifrs.gabrielanceski.oop.model.Item;
import br.edu.ifrs.gabrielanceski.oop.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemService {
    private final ItemRepository itemRepository;

    @Autowired
    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
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
        itemRepository.deleteById(itemId);
    }
}
