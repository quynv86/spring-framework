package vn.quynv.springframework.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import vn.quynv.springframework.entity.Author;
import vn.quynv.springframework.entity.Bestseller;
import vn.quynv.springframework.entity.Book;
import vn.quynv.springframework.entity.Publisher;
import vn.quynv.springframework.repository.AuthorRepository;
import vn.quynv.springframework.repository.BestsellerRepository;
import vn.quynv.springframework.repository.BookRepository;
import vn.quynv.springframework.repository.PublisherRepository;
import vn.quynv.springframework.service.specification.BookSpecifications;

import javax.persistence.Tuple;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@SpringBootTest
@Slf4j
public class BookStoreServiceTest {
    @Autowired
    private BookStoreService bookStoreService;

    @Autowired
    private PublisherRepository publisherRepository;

    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private BestsellerRepository bestsellerRepository;


    @BeforeEach
    void setup(){
        Publisher publisher = publisherRepository.saveAndFlush(Publisher.builder().company("NXB - Kim Dong").authors(Collections.emptyList()).build());
        Author author = authorRepository.saveAndFlush(Author.builder()
                        .books(Collections.emptyList())
                .publisher(publisher).age(30).genre("Information Technology").name("Linux Toval").build());

        Book book = Book.builder().isbn("ISBN-1").title("Spring Best Practice").author(author).build();
        Book book_2 = Book.builder().isbn("ISBN-2").title("Hello Spring Boot").author(author).build();
        bookRepository.saveAll(Arrays.asList(book, book_2));
    }


    @Test
    void fetchBooks_Using_LefJoinFetch() {
        bookStoreService.findAllUsingLeftJoinFetch().forEach(BookStoreServiceTest::printBook);
    }

    @Test
    void fetch_Bestseller_Books() {
        Author author = authorRepository.findAuthorByName("Linux Toval");
        Bestseller bestseller = Bestseller.builder().authorId(author.getId())
                .rank(10).title("Best seller in Jan-2021").build();
        bestsellerRepository.save(bestseller);
        authorRepository.getBestSeller().forEach(b -> {log.info("Bestseller: {}", b.getName());});
    }

    @Test
    void findAll_Using_Specification() {
        bookStoreService.findAll_Using_Specification("ISBN", "Spring Best Practice", PageRequest.of(0,1))
                .forEach(b -> log.info("Bestseller: {}", b.getIsbn()));
    }

    @Test
    void fetch_by_specific_Full_Params() {
        bookStoreService.queryBySpecification(BookSpecifications.BookQuery.builder().authorNames(Arrays.asList("AUTHOR"))
                        .isbn("ISBN")
                        .title("TITLE")
                .build()
        );
    }
    @Test
    void fetch_by_specific_with_Isbn_and_Title() {
        bookStoreService.queryBySpecification(BookSpecifications.BookQuery.builder()
                        .isbn("ISBN")
                        .title("TITLE")
                .build()
        );
        bookStoreService.queryBySpecification(BookSpecifications.BookQuery.builder()
                .authorNames(Arrays.asList("AUTHOR"))
                .build()
        );
    }

    @Test
    void find_Books_Using_EntityManager() {
        log.info("Start finding books using entity manager instated of JpaRepository...");
        bookRepository.findBookUsingEntityManager().forEach(BookStoreServiceTest::printBook);
        log.info("Total Books: {}", bookRepository.queryTotalBooksUsingCriteria());
    }

    final static void printBook(Book book) {
        log.info("ISBN: {}, Author: {}, Publisher: {}", book.getIsbn(), book.getAuthor().getName(), book.getAuthor().getPublisher().getCompany());
    }

    @Test
    void queryBookReturnListArrayOfObject(){
       List<Object[]> listArraysOfObject = bookRepository.queryAndReturnListArrayOfObject();
       listArraysOfObject.forEach(objects -> {
           log.info("Arrays size: {}", objects.length);
       });
    }

    @Test
    void testCustomizeDTO_Using_ClassBase() {
        bookRepository.summaryBookByAuthor().forEach(sumDTO -> {
            log.info("SUM_RESULT [Author_ID: {}, Count: {} ]",sumDTO.getAuthorId(), sumDTO.getTotalBook());
        });
    }

    @Test
    void findAll_And_Paginate() {
        log.info("Total elements: {}", bookRepository.findAllAndPaginate(PageRequest.of(1,1)).getTotalElements());
    }


    @Test
    void testCustomizeDTO_Using_ClassBase_And_Paginate() {
        bookRepository.summaryBookByAuthor_And_Paginate(PageRequest.of(1,1)).forEach(sumDTO -> {
            log.info("SUM_RESULT [Author_ID: {}, Count: {} ]",sumDTO.getAuthorId(), sumDTO.getTotalBook());
        });
    }

    @Test
    void test_Tuple_Using_And_Paginate() {
        Page<Tuple> page = bookRepository.findAll_Return_Tuple_And_Paginate(PageRequest.of(1,1));
        log.info("Total page: {}", page.getTotalPages());

    }
}
