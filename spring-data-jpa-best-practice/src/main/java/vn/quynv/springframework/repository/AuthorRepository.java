package vn.quynv.springframework.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vn.quynv.springframework.entity.Author;

import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
    @EntityGraph(value="Author.books")
    Author findAuthorById(Long id);

    @Query(value="select a from Author a inner join Bestseller b on a.id = b.authorId")
    List<Author> getBestSeller();

    @Query(value="select a from Author a where a.name =:name")
    Author findAuthorByName(String name);
}
