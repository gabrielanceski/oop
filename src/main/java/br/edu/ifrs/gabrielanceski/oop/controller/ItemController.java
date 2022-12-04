package br.edu.ifrs.gabrielanceski.oop.controller;

import br.edu.ifrs.gabrielanceski.oop.model.Item;
import br.edu.ifrs.gabrielanceski.oop.model.Solution;
import br.edu.ifrs.gabrielanceski.oop.service.BrandService;
import br.edu.ifrs.gabrielanceski.oop.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/itens")
public class ItemController {
    private final ItemService itemService;
    private final BrandService brandService;

    @Autowired
    public ItemController(ItemService itemService, BrandService brandService) {
        this.itemService = itemService;
        this.brandService = brandService;
    }

    @GetMapping
    public ModelAndView index(@RequestParam(required = false) String error) {
        ModelAndView view = new ModelAndView();
        view.setViewName("pages/items");
        view.addObject("itemList", itemService.findAll());
        view.addObject("brandList", brandService.findAll());
        if (error != null) view.addObject("error", error);
        return view;
    }

    @GetMapping("/buscar")
    public ModelAndView search(@RequestParam String itemName, @RequestParam String itemModel, @RequestParam String itemBrand) {
        List<Item> itemList = itemService.findBy(itemName, itemModel, itemBrand);
        ModelAndView view = new ModelAndView();
        view.setViewName("pages/items");
        view.addObject("itemList", itemList);
        view.addObject("brandList", brandService.findAll());
        return view;
    }

    @GetMapping("/novo-item")
    public ModelAndView newItem() {
        Item item = new Item();
        ModelAndView view = new ModelAndView();
        view.setViewName("pages/item-form");
        view.addObject(item);
        view.addObject("brandList", brandService.findAll());
        return view;
    }

    @GetMapping("/editar/{id}")
    public ModelAndView editItem(@PathVariable String id) {
        try {
            Optional<Item> itemOptional = itemService.findById(Integer.parseInt(id));
            if (itemOptional.isEmpty())
                throw new IllegalStateException("Não foi possível encontrar o item com o identificador informado!");
            ModelAndView view = new ModelAndView();
            view.setViewName("pages/item-form");
            view.addObject("item", itemOptional.get());
            view.addObject("brandList", brandService.findAll());
            return view;
        } catch (NumberFormatException e) {
            throw new IllegalStateException("Não foi possível encontrar o item com o identificador informado!");
        }
    }

    @PostMapping("/salvar")
    public String saveOrUpdate(@Validated Item item) {
        try {
            itemService.saveOrUpdate(item);
        } catch (Exception e) {
            return "redirect:/itens/novo-item";
        }
        return "redirect:/itens";
    }

    @GetMapping("/deletar/{id}")
    public ModelAndView delete(@PathVariable int id) {
        ModelAndView view = new ModelAndView("redirect:/itens");
        if (id > 0) {
            try {
                itemService.delete(id);
            } catch (Exception e) {
                view.addObject("error", e.getMessage());
            }
        }
        return view;
    }
}
