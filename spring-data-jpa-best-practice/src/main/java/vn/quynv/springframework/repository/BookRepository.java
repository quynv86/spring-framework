package vn.quynv.springframework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vn.quynv.springframework.entity.Book;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> , JpaSpecificationExecutor<Book>, BookRepositoryCustom {
    @Query(value="select b from Book b left join fetch b.author a left join fetch a.publisher p")
    List<Book> findAllUsingLeftJoinFetch();

}
