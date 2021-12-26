package vn.quynv.springframework.webflux.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import vn.quynv.springframework.webflux.domain.Task;
import vn.quynv.springframework.webflux.domain.TaskDescriptionOnly;
import vn.quynv.springframework.webflux.repository.TaskRepository;

@Service
@Slf4j
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;

    public Boolean isValid(final Task task) {
        return !(task ==null || task.getDescription().isEmpty());
    }

    public Flux<Task> getAllTasks () {
        return taskRepository.findAll();
    }
    public Mono<Task> createTask(final Task task) {
        return taskRepository.save(task);
    }
    @Transactional
    public Mono<Task> updateTask(final Task task) {
       return taskRepository.findById(task.getId())
                .flatMap(oldTask -> {
                    oldTask.setDescription(task.getDescription());
                    oldTask.setCompleted(task.getCompleted());
                    return taskRepository.save(oldTask);
                });
    }

    @Transactional
    public Mono<Void> deleteTask(final Task task) {
        return taskRepository.findById(task.getId())
                .flatMap((existingTask)-> {
                    return taskRepository.delete(existingTask);
                });
    }

    public Mono<Integer> updateStatus(Integer id, Boolean status) {
        return taskRepository.updateStatus(id,status);
    }

    public Flux<TaskDescriptionOnly> getAllWithDescriptionOnly() {
        return taskRepository.getAllWithDescriptionOnly();
    }
}
