package br.edu.ifrs.gabrielanceski.oop.controller;

import br.edu.ifrs.gabrielanceski.oop.model.Item;
import br.edu.ifrs.gabrielanceski.oop.model.Solution;
import br.edu.ifrs.gabrielanceski.oop.model.User;
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
    private final SolutionService solutionService;
    private final UserService userService;

    @Autowired
    public SolutionController(ItemService itemService, SolutionService solutionService, UserService userService) {
        this.itemService = itemService;
        this.solutionService = solutionService;
        this.userService = userService;
    }

    @GetMapping
    public ModelAndView index() {
        ModelAndView view = new ModelAndView();
        view.setViewName("solutions/solutions");
        view.addObject("solutionList", solutionService.findAll());
        return view;
    }

    @GetMapping("/buscar")
    public ModelAndView search(@RequestParam(required = false) String keyword) {
        List<Solution> solutionList = solutionService.findAllByItemModel(keyword);
        ModelAndView view = new ModelAndView();
        view.setViewName("solutions/solutions");
        view.addObject("solutionList", solutionList);
        return view;
    }

    @GetMapping("/nova-solucao")
    public ModelAndView newSolution(@RequestParam(required = false) String itemId) {
        if (itemId == null || itemId.isEmpty()) return index();
        int id = Integer.parseInt(itemId);
        Optional<Item> itemOptional = itemService.findById(id);
        Optional<User> userOptional = userService.findByEmail("teste@exemplo.com");
        if (itemOptional.isEmpty() || userOptional.isEmpty()) {
            throw new IllegalStateException("Erro ao encontrar o item requisitado.");
        }

        Solution solution = new Solution();
        solution.setItem(itemOptional.get());
        solution.setUser(userOptional.get());
        ModelAndView view = new ModelAndView();
        view.setViewName("solutions/solution-form");
        view.addObject("solution", solution);
        return view;
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

    @GetMapping("/editar/{id}")
    public ModelAndView editSolution(@PathVariable String id) {
        try {
            Optional<Solution> solution = solutionService.findById(Integer.parseInt(id));
            if (solution.isEmpty())
                throw new IllegalStateException("Não foi possível encontrar uma solução com o identificador informado!");
            ModelAndView view = new ModelAndView();
            view.setViewName("solutions/solution-form");
            view.addObject("solution", solution.get());
            return view;
        } catch (NumberFormatException e) {
            throw new IllegalStateException("Não foi possível encontrar uma solução com o identificador informado!");
        }
    }
}
