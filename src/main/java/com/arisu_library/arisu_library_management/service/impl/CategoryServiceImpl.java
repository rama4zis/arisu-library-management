package com.arisu_library.arisu_library_management.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.arisu_library.arisu_library_management.entity.Category;
import com.arisu_library.arisu_library_management.model.app.SimpleMap;
import com.arisu_library.arisu_library_management.model.request.CategoryRequestRecord;
import com.arisu_library.arisu_library_management.repository.CategoryRepository;
import com.arisu_library.arisu_library_management.service.CategoryService;
import com.arisu_library.arisu_library_management.service.ValidationService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ValidationService validationService;

    @Override
    public SimpleMap create(CategoryRequestRecord req) {
        validationService.validate(req);
        categoryRepository.findByName(req.name()).ifPresent(c -> {
            throw new RuntimeException("Category name already exists");
        });

        var entity = Category.builder()
                .name(req.name())
                .description(req.description())
                .build();
        entity = categoryRepository.save(entity);
        return toMap(entity);
    }

    @Override
    public SimpleMap update(CategoryRequestRecord req) {
        validationService.validate(req);
        var entity = categoryRepository.findById(req.id())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        if (!entity.getName().equals(req.name())) {
            categoryRepository.findByName(req.name()).ifPresent(c -> {
                throw new RuntimeException("Category name already exists");
            });
        }

        entity.setName(req.name());
        entity.setDescription(req.description());
        categoryRepository.save(entity);
        return toMap(entity);
    }

    @Override
    public Page<SimpleMap> findAll(String name, Pageable pageable) {
        Specification<Category> spec = (root, query, cb) -> {
            if (name == null || name.isBlank()) return null;
            var like = "%" + name.toLowerCase() + "%";
            return cb.like(cb.lower(root.get("name")), like);
        };
        return categoryRepository.findAll(spec, pageable)
                .map(this::toMap);
    }

    @Override
    public SimpleMap findById(String id) {
        var entity = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        return toMap(entity);
    }

    @Override
    public void deleteById(String id) {
        if (!categoryRepository.existsById(id)) {
            throw new RuntimeException("Category not found");
        }
        categoryRepository.deleteById(id);
    }

    private SimpleMap toMap(Category c) {
        return SimpleMap.createMap()
                .add("id", c.getId())
                .add("name", c.getName())
                .add("description", c.getDescription());
    }
}
