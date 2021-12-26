package vn.quynv.springframework.webflux.repository;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import vn.quynv.springframework.webflux.domain.Task;
import vn.quynv.springframework.webflux.domain.TaskDescriptionOnly;

@Repository
public interface TaskRepository extends ReactiveCrudRepository<Task, Integer> {
    /*Custom Query*/
    @Query("update tasks set status= :status where id= :id")
    Mono<Integer> updateStatus(Integer id, Boolean status);

    @Query("select description from tasks ")
    Flux<TaskDescriptionOnly> getAllWithDescriptionOnly();
}
