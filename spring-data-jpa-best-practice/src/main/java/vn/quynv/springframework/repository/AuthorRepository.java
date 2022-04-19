package vn.quynv.springframework.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.quynv.springframework.entity.Author;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
    @EntityGraph(value="Author.books")
    Author findAuthorById(Long id);
}
