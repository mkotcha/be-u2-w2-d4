package org.emmek.beu2w2d4.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.emmek.beu2w2d4.config.EmailSender;
import org.emmek.beu2w2d4.entities.Author;
import org.emmek.beu2w2d4.exceptions.BadRequestException;
import org.emmek.beu2w2d4.exceptions.NotFoundException;
import org.emmek.beu2w2d4.payloads.author.AuthorPostDTO;
import org.emmek.beu2w2d4.repositories.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class AuthorService {
    @Autowired
    AuthorRepository authorRepository;
    @Autowired
    private Cloudinary cloudinary;

    @Autowired
    private EmailSender emailSender;

    public Author save(AuthorPostDTO author) throws IOException {
        authorRepository.findByEmail(author.email()).ifPresent(a -> {
            throw new BadRequestException("Author with email " + a.getEmail() + " already exists");
        });
        Author newAuthor = new Author();
        newAuthor.setAvatar("http://ui-avatars.com/api/?name=" + author.name() + "+" + author.surname());
        newAuthor.setName(author.name());
        newAuthor.setSurname(author.surname());
        newAuthor.setEmail(author.email());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate date = LocalDate.parse(author.birthDate(), formatter);
        newAuthor.setBirthDate(date);
        Author savedAuthor = authorRepository.save(newAuthor);
        emailSender.sendRegistrationEmail(author.email());
        return savedAuthor;
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

    public String uploadPicture(int id, MultipartFile file) throws IOException {
        Author author = authorRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
        String url = (String) cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap()).get("url");
        author.setAvatar(url);
        authorRepository.save(author);
        return url;
    }
}
