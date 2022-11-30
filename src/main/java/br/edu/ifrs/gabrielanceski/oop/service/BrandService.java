package br.edu.ifrs.gabrielanceski.oop.service;

import br.edu.ifrs.gabrielanceski.oop.model.Brand;
import br.edu.ifrs.gabrielanceski.oop.repository.BrandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class BrandService {
    private final BrandRepository brandRepository;

    @Autowired
    public BrandService(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    public List<Brand> findAll() {
        return brandRepository.findAll();
    }

    public void saveOrUpdate(Brand brand) {
        if (brand.getId() == 0) {
            brand.setCreated_at(new Date());
        }
        brandRepository.save(brand);
    }
}
