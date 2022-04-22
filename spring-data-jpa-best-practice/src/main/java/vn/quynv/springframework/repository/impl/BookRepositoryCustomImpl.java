package vn.quynv.springframework.repository.impl;

import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import vn.quynv.springframework.entity.Author;
import vn.quynv.springframework.entity.Bestseller;
import vn.quynv.springframework.entity.Book;
import vn.quynv.springframework.repository.BookRepositoryCustom;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.util.List;

@Component
public class BookRepositoryCustomImpl implements BookRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    public BookRepositoryCustomImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Book> findBookUsingEntityManager() {
        return entityManager.createQuery("select a from Book a", Book.class).getResultList();
    }

    public Long queryTotalBooksUsingCriteria() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = criteriaBuilder.createQuery(Long.class);
        Root<Book>  root = query.from(Book.class);

        Join<Book, Author>  bookJoinAuthor = root.join("author", JoinType.INNER);
        bookJoinAuthor.on(criteriaBuilder.equal(bookJoinAuthor.get("name"),"Nguyen"))
                        .on(criteriaBuilder.like(root.get("title"),"TITLE"));

        query.select(criteriaBuilder.count(bookJoinAuthor));
        return entityManager.createQuery(query).getSingleResult();
    }

}
