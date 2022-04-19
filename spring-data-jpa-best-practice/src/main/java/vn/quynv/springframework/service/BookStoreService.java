package vn.quynv.springframework.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.quynv.springframework.domain.BookDTO;
import vn.quynv.springframework.entity.Author;
import vn.quynv.springframework.entity.Book;
import vn.quynv.springframework.repository.AuthorRepository;
import vn.quynv.springframework.repository.BookRepository;

@Service
@Slf4j
public class BookStoreService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    @Autowired
    public BookStoreService(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    @Transactional(readOnly = true)
    public void loadAllAuthorAndPrintThem() {
        // Default load: using lazy loading
        authorRepository.findAll().forEach(author -> log.info("{}",author));
    }
    @Transactional
    public void addBook(BookDTO bookDTO) {
        log.info("Add new BOOK {}", bookDTO);
        // Load using EnityGrap -> Load Eager
        Author author = authorRepository.findAuthorById(bookDTO.getAuthorId());
        Book book = new Book();
        book.setAuthor(author);
        book.setIsbn(bookDTO.getIsbn());
        book.setTitle(bookDTO.getTitle());
        author.getBooks().add(book);
        Book firstBook = author.getBooks().get(0);
        firstBook.setAuthor(null);
        author.getBooks().remove(firstBook);
    }
}
