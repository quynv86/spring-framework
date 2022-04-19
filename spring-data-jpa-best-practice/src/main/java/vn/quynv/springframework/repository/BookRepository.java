package vn.quynv.springframework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.quynv.springframework.entity.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
}
