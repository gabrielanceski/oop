package br.edu.ifrs.gabrielanceski.oop.controller;

import br.edu.ifrs.gabrielanceski.oop.model.Item;
import br.edu.ifrs.gabrielanceski.oop.service.BrandService;
import br.edu.ifrs.gabrielanceski.oop.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

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
    public ModelAndView index() {
        ModelAndView view = new ModelAndView();
        view.setViewName("items");
        view.addObject("itemList", itemService.findAll());
        return view;
    }

    @GetMapping("/novo-item")
    public ModelAndView newItem() {
        Item item = new Item();
        ModelAndView view = new ModelAndView();
        view.setViewName("create-item");
        view.addObject(item);
        view.addObject("brandList", brandService.findAll());
        return view;
    }

    @PostMapping("/novo-item")
    public String newItem(@Validated Item item) {
        try {
            itemService.saveOrUpdate(item);
        } catch (Exception e) {
            return "redirect:/novo-item";
        }
        return "redirect:/itens";
    }
}
