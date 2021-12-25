package vn.quynv.springframework.repository.customer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vn.quynv.springframework.domain.customer.Customer;
import vn.quynv.springframework.domain.customer.CustomerStatus;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;

@Repository
public interface CustomerRes extends JpaRepository<Customer, Long> {
    Collection<Customer> queryCustomerByNameEndingWith(String nameEndWith);

    Collection<Customer> queryCustomerByStatusIs(CustomerStatus status);

    @Query("select c from Customer c  where c.status=1")
    Collection<Customer> queryActivatedCustomer();

    @Query("from Customer c left join c.customerType t where t.custType in ?1")
    Collection<Customer> queryCustomerByCustTypeIn(List<String> custTypes);
}
