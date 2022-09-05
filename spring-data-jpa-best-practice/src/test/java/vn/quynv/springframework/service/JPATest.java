package vn.quynv.springframework.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import vn.quynv.springframework.domain.BookDTO;
import vn.quynv.springframework.entity.Author;
import vn.quynv.springframework.entity.Bestseller;
import vn.quynv.springframework.entity.Book;
import vn.quynv.springframework.entity.Publisher;
import vn.quynv.springframework.repository.AuthorRepository;
import vn.quynv.springframework.repository.BestsellerRepository;
import vn.quynv.springframework.repository.BookRepository;
import vn.quynv.springframework.repository.PublisherRepository;

import javax.persistence.EntityManager;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.Arrays;
import java.util.Collections;

@SpringBootTest
@Slf4j
public class JPATest {
    @Autowired
    private EntityManager entityManager;
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
    void simpleQuery_Using_EntityManager(){
        log.info("Start query all book using JPA API - Criteria - Query");
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Book> query = cb.createQuery(Book.class);
        Root<Book> book = query.from(Book.class);
        cb.selectCase(book);
        query.where(
                cb.like(book.get("title"),"Hello"),
                cb.like(book.get("isbn"),"ISBN")
                ,cb.or(
                        cb.like(book.get("title"),"Hello"),
                        cb.like(book.get("isbn"),"ISBN")
                )
        );
        TypedQuery<Book> typedQuery = entityManager.createQuery(query);
        typedQuery.getResultList().stream()
                .forEach(bookEntity -> {
                    log.info("Book: {}", bookEntity);

                });
    }

    @Test
    void query_With_InClause_Using_SubQuery() {
        log.info("Query With In Clause Using Sub Query");
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Book> query = cb.createQuery(Book.class);
        Root<Book> rBook = query.from(Book.class);
        Subquery<Long> subQueryAuthorIds =query.subquery(Long.class);
        Root<Bestseller> rBestseller = subQueryAuthorIds.from(Bestseller.class);
        subQueryAuthorIds.select(rBestseller.get("authorId"));
        Predicate withBestSellAuthor = rBook.get("author").get("id").in(subQueryAuthorIds);
        query.select(rBook).where(cb.not(withBestSellAuthor));

        TypedQuery<Book> typedQuery = entityManager.createQuery(query);
        typedQuery.getResultList().stream().forEach((b) -> {
            log.info("Got : {}", b);
        });
        // SQL Will be like :
        /**
         *  select
         *         book0_.id as id1_2_,
         *         book0_.deleted as deleted2_2_,
         *         book0_.author_id as author_i5_2_,
         *         book0_.isbn as isbn3_2_,
         *         book0_.title as title4_2_
         *     from
         *         Book book0_
         *     where
         *         (
         *             book0_.deleted = 0
         *         )
         *         and (
         *             book0_.author_id in (
         *                 select
         *                     bestseller1_.authorId
         *                 from
         *                     Bestseller bestseller1_
         *             )
         *         )
         */
    }

    @Test
    void query_distinct_Title_Of_Book() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<String> query = cb.createQuery(String.class);
        Root<Book> book = query.from(Book.class);
        query.select(book.get("title")).distinct(true);
        TypedQuery<String> typedQuery = entityManager.createQuery(query);
        typedQuery.getResultList().stream().forEach((title) -> {
            log.info("Got title: {}", title);
        });
    }
    @Test
    void query_Using_Cross_Join() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Book> criteriaQuery = cb.createQuery(Book.class);
        Root<Book> rBook = criteriaQuery.from(Book.class);
        Root<Bestseller> rBestSeller = criteriaQuery.from(Bestseller.class);
        criteriaQuery.select(rBook)
                .where(cb.equal(rBook.get("author").get("id"),rBestSeller.get("id"))
                , cb.in(rBestSeller.get("id")).value(1));
        entityManager.createQuery(criteriaQuery).getResultList()
                .stream().forEach((b)-> {
                    log.info("Got Book: {}", b);
        });
    }

    @Test
    void query_Using_Parameter_Expression() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Book> query = cb.createQuery(Book.class);
        Root<Book> fromBook = query.from(Book.class);
        ParameterExpression<String> titleLikeParam = cb.parameter(String.class);
        query.select(fromBook).where(cb.like(fromBook.get("title"),titleLikeParam));
        TypedQuery<Book> typedQuery  = entityManager.createQuery(query);
        typedQuery.setParameter(titleLikeParam,"Linux Toval");
        typedQuery.getResultList().stream().forEach(this::printBook);
    }

    @Test
    void query_Using_Criteria_And_Map_To_DTO() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<BookDTO> query = cb.createQuery(BookDTO.class);
        Root<Book> fromBook = query.from(Book.class);
        ParameterExpression<String> titleLikeParam = cb.parameter(String.class);
        query.select(cb.construct(
                        BookDTO.class,
                        fromBook.get("id"),
                        fromBook.get("isbn"),
                        fromBook.get("title"),
                        fromBook.get("author").get("id")
                )
        ).where(cb.like(fromBook.get("title"), titleLikeParam));
        TypedQuery<BookDTO> typedQuery = entityManager.createQuery(query);
        typedQuery.setParameter(titleLikeParam, "Spring Best%");
        typedQuery.setFirstResult(0);
        typedQuery.setMaxResults(10);
        typedQuery.getResultList().stream().forEach(this::printBookDTO);
    }

    @Test
    void query_Return_Tuple_Instate_Of_DTO() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tuple> query = cb.createQuery(Tuple.class);
        Root<Book> fromBook = query.from(Book.class);

        query.multiselect(
                fromBook.get("id").alias("bookId")
                ,fromBook.get("isbn").alias("bookISBN")
        );

        TypedQuery<Tuple> typeQuery = entityManager.createQuery(query);
        typeQuery.getResultList().stream().forEach((row) -> {
            log.info("[Book: ID: {}, ISBN: {}]", row.get("bookId"), row.get("bookISBN"));
        });
    }
    public void printBook(Book book) {
        log.info("Got book: {}", book);
    }
    public void printBookDTO(BookDTO book) {
        log.info("Got book: {}", book);
    }
}
