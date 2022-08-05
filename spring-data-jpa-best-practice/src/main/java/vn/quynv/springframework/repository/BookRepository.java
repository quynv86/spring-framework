package vn.quynv.springframework.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vn.quynv.springframework.domain.AuthorSummaryDTO;
import vn.quynv.springframework.entity.Book;

import javax.persistence.Tuple;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> , JpaSpecificationExecutor<Book>, BookRepositoryCustom {
    @Query(value="select b from Book b left join fetch b.author a left join fetch a.publisher p")
    List<Book> findAllUsingLeftJoinFetch();

    @Query(value="select b.id, b.isbn, b.title from Book b")
    List<Object[]> queryAndReturnListArrayOfObject();

    @Query(value="select author_id as authorId, count(*) as totalBook from Book group by author_id", nativeQuery = true)
    List<AuthorSummaryDTO> summaryBookByAuthor();

    @Query(value="select b from Book b")
    Page<Book> findAllAndPaginate(Pageable pageable);


    @Query(value="select author_id as authorId, count(*) as totalBook from Book group by author_id"
            , nativeQuery = true
    , countQuery ="select count(*) as totalRec from (select author_id as authorId, count(*) as totalBook from Book group by author_id)")
    List<AuthorSummaryDTO> summaryBookByAuthor_And_Paginate(Pageable pageable);

    @Query(value="select b.title, b.isbn from Book b")
    Page<Tuple> findAll_Return_Tuple_And_Paginate(Pageable pageable);
}
