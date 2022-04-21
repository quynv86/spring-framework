package vn.quynv.springframework.service.specification;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import vn.quynv.springframework.entity.Author;
import vn.quynv.springframework.entity.Book;

import javax.persistence.criteria.Join;
import java.util.List;

public class BookSpecifications {
    public static Specification<Book> hasTitle(String title) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("title"),title);
    }

    public static Specification<Book> hasIsbnLike(String isbn) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("isbn"),isbn);
    }

    public static Specification<Book> alwaysTrue() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.isTrue(criteriaBuilder.literal(true));
    }

    public static Specification<Book> ofAuthors(List<String> authors) {
        return (root, query, criteriaBuilder) -> {
            Join<Book, Author> authorJoin = root.join("author");
            return criteriaBuilder.in(authorJoin.get("name")).value(authors);
        };
    }
    public static Specification<Book> bySpecific(BookQuery query) {
        Specification<Book> rootSpc = alwaysTrue();

        if(StringUtils.hasLength(query.getIsbn())) {
            rootSpc = rootSpc.and(hasIsbnLike(query.getIsbn()));
        }
        if(StringUtils.hasLength(query.getTitle())) {
            rootSpc = rootSpc.and(hasTitle(query.getTitle()));
        }
        if(ObjectUtils.isEmpty(query.getAuthorNames())) {
            return rootSpc;
        }
        return rootSpc.and(ofAuthors(query.getAuthorNames()));
    }


    @Builder
    @Data
    public static class BookQuery{
        private String title;
        private String isbn;
        private List<String> authorNames;
    }
}
