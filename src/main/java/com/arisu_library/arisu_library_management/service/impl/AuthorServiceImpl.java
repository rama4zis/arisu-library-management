package com.arisu_library.arisu_library_management.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.arisu_library.arisu_library_management.entity.Author;
import com.arisu_library.arisu_library_management.model.app.SimpleMap;
import com.arisu_library.arisu_library_management.model.request.AuthorRequestRecord;
import com.arisu_library.arisu_library_management.repository.AuthorRepository;
import com.arisu_library.arisu_library_management.service.AuthorService;
import com.arisu_library.arisu_library_management.service.ValidationService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;
    private final ValidationService validationService;

    @Override
    public SimpleMap create(AuthorRequestRecord req) {
        validationService.validate(req);
        var entity = Author.builder()
                .name(req.name())
                .biography(req.biography())
                .dob(req.dob())
                .country(req.country())
                .website(req.website())
                .build();
        entity = authorRepository.save(entity);
        return toMap(entity);
    }

    @Override
    public SimpleMap update(AuthorRequestRecord req) {
        validationService.validate(req);
        var entity = authorRepository.findById(req.id())
                .orElseThrow(() -> new RuntimeException("Author not found"));

        entity.setName(req.name());
        entity.setBiography(req.biography());
        entity.setDob(req.dob());
        entity.setCountry(req.country());
        entity.setWebsite(req.website());
        authorRepository.save(entity);
        return toMap(entity);
    }

    @Override
    public Page<SimpleMap> findAll(String name, String country, Pageable pageable) {
        Specification<Author> spec = (root, q, cb) -> null;
        if (name != null && !name.isBlank()) {
            spec = spec.and((root, q, cb) -> cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%"));
        }
        if (country != null && !country.isBlank()) {
            spec = spec.and((root, q, cb) -> cb.like(cb.lower(root.get("country")), "%" + country.toLowerCase() + "%"));
        }
        return authorRepository.findAll(spec, pageable).map(this::toMap);
    }

    @Override
    public SimpleMap findById(String id) {
        var entity = authorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Author not found"));
        return toMap(entity);
    }

    @Override
    public void deleteById(String id) {
        if (!authorRepository.existsById(id)) {
            throw new RuntimeException("Author not found");
        }
        authorRepository.deleteById(id);
    }

    private SimpleMap toMap(Author a) {
        return SimpleMap.createMap()
                .add("id", a.getId())
                .add("name", a.getName())
                .add("biography", a.getBiography())
                .add("dob", a.getDob())
                .add("country", a.getCountry())
                .add("website", a.getWebsite());
    }
}
