package br.edu.ifrs.gabrielanceski.oop.controller;

import br.edu.ifrs.gabrielanceski.oop.model.Brand;
import br.edu.ifrs.gabrielanceski.oop.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("marcas")
public class BrandController {
    private final BrandService brandService;

    @Autowired
    public BrandController(BrandService brandService) {
        this.brandService = brandService;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("brandList", brandService.findAll());
        return "brands";
    }

    @GetMapping(value="/nova-marca")
    public String newBrand(Model model) {
        Brand brand = new Brand();
        model.addAttribute("brand", brand);
        return "create-brand";
    }

    @PostMapping(value = "/nova-marca")
    public String newBrand(@ModelAttribute Brand brand) {
        brandService.saveOrUpdate(brand);
        return "redirect:/marcas";
    }
}
