package br.edu.ifrs.gabrielanceski.oop.controller;

import br.edu.ifrs.gabrielanceski.oop.Utils;
import br.edu.ifrs.gabrielanceski.oop.model.Item;
import br.edu.ifrs.gabrielanceski.oop.model.Solution;
import br.edu.ifrs.gabrielanceski.oop.model.SolutionStatus;
import br.edu.ifrs.gabrielanceski.oop.model.User;
import br.edu.ifrs.gabrielanceski.oop.service.BrandService;
import br.edu.ifrs.gabrielanceski.oop.service.ItemService;
import br.edu.ifrs.gabrielanceski.oop.service.SolutionService;
import br.edu.ifrs.gabrielanceski.oop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/solucoes")
public class SolutionController {
    private final ItemService itemService;
    private final BrandService brandService;
    private final SolutionService solutionService;
    private final UserService userService;

    @Autowired
    public SolutionController(ItemService itemService, BrandService brandService, SolutionService solutionService, UserService userService) {
        this.itemService = itemService;
        this.brandService = brandService;
        this.solutionService = solutionService;
        this.userService = userService;
    }

    @GetMapping
    public ModelAndView index() {
        ModelAndView view = new ModelAndView();
        view.setViewName("pages/solutions");
        view.addObject("brandList", brandService.findAll());
        view.addObject("solutionList", solutionService.findAll());
        return view;
    }

    @GetMapping("/buscar")
    public ModelAndView search(@RequestParam String itemModel, @RequestParam String itemBrand, @RequestParam String issueOrCause) {
        List<Solution> solutionList = solutionService.findBy(itemModel, itemBrand, issueOrCause);
        ModelAndView view = new ModelAndView();
        view.setViewName("pages/solutions");
        view.addObject("brandList", brandService.findAll());
        view.addObject("solutionList", solutionList);
        return view;
    }

    @GetMapping("/item/{id}")
    public ModelAndView itemSolutions(@PathVariable int id) {
        ModelAndView redirectView = new ModelAndView("redirect:/solucoes");
        if (id > 0) {
            List<Solution> solutionList = solutionService.findAllByItemId(id);

            if (solutionList.isEmpty()) return redirectView;

            ModelAndView view = new ModelAndView();
            view.setViewName("pages/solutions");
            view.addObject("brandList", brandService.findAll());
            view.addObject("solutionList", solutionList);
            return view;
        }
        return redirectView;
    }

    @GetMapping("/nova-solucao")
    public ModelAndView newSolution(@RequestParam(required = false) String itemId) {
        if (itemId == null || itemId.isEmpty()) {
            return new ModelAndView("redirect:/itens");
        }
        int id = Integer.parseInt(itemId);
        Optional<Item> itemOptional = itemService.findById(id);
        Optional<User> userOptional = userService.findByEmail("teste@exemplo.com");

        if (userOptional.isEmpty()) {
            User dummy = Utils.dummyUser();
            userService.saveOrUpdate(dummy);
            userOptional = Optional.of(dummy);
        }

        if (itemOptional.isEmpty()) {
            throw new IllegalStateException("Erro ao encontrar o item requisitado.");
        }

        Solution solution = new Solution();
        solution.setItem(itemOptional.get());
        solution.setUser(userOptional.get());
        ModelAndView view = new ModelAndView();
        view.setViewName("pages/solution-form");
        view.addObject("solution", solution);
        view.addObject("statusList", SolutionStatus.values());
        return view;
    }

    @GetMapping("/editar/{id}")
    public ModelAndView editSolution(@PathVariable int id) {
        if (id > 0) {
            Optional<Solution> solution = solutionService.findById(id);
            if (solution.isEmpty())
                throw new IllegalStateException("Não foi possível encontrar uma solução com o identificador informado!");
            ModelAndView view = new ModelAndView();
            view.setViewName("pages/solution-form");
            view.addObject("solution", solution.get());
            view.addObject("statusList", SolutionStatus.values());
            return view;
        }
        return new ModelAndView("redirect:/solucoes");
    }

    @PostMapping("/salvar")
    public String saveOrUpdate(@Validated Solution solution) {
        try {
            solutionService.saveOrUpdate(solution);
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return "redirect:/solucoes";
    }

    @GetMapping("/deletar/{id}")
    public String delete(@PathVariable int id) {
        if (id > 0) {
            Optional<Solution> solution = solutionService.findById(id);
            if (solution.isPresent()) {
                solutionService.delete(id);
            }
        }
        return "redirect:/solucoes";
    }
}
