package vn.quynv.springframework.repository;

import vn.quynv.springframework.entity.Book;

import java.util.List;

public interface BookRepositoryCustom {
     List<Book> findBookUsingEntityManager();
     Long queryTotalBooksUsingCriteria();
}
