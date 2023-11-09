package org.emmek.beu2w2d4.services;

import org.emmek.beu2w2d4.entities.Author;
import org.emmek.beu2w2d4.exceptions.BadRequestException;
import org.emmek.beu2w2d4.exceptions.NotFoundException;
import org.emmek.beu2w2d4.repositories.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@Service
public class AuthorService {
    @Autowired
    AuthorRepository authorRepository;


    public Author save(Author author) {
        authorRepository.findByEmail(author.getEmail()).ifPresent(a -> {
            throw new BadRequestException("Author with email " + a.getEmail() + " already exists");
        });
        author.setAvatar("http://ui-avatars.com/api/?name=" + author.getName() + "+" + author.getSurname());
        return authorRepository.save(author);
    }

    public Page<Author> getAuthors(int page, int size, String sort) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        return authorRepository.findAll(pageable);
    }

    public Author findById(int id) throws NotFoundException {
        return authorRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public void findByIdAndDelete(int id) throws MethodArgumentTypeMismatchException {
        authorRepository.deleteById(id);
    }

    public Author findByIdAndUpdate(int id, Author author) throws NotFoundException {
        Author a = authorRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
        a.setName(author.getName());
        a.setSurname(author.getSurname());
        a.setEmail(author.getEmail());
        a.setBirthDate(author.getBirthDate());
        author.setAvatar("http://ui-avatars.com/api/?name=" + author.getName() + "+" + author.getSurname());
        return authorRepository.save(a);
    }

}
