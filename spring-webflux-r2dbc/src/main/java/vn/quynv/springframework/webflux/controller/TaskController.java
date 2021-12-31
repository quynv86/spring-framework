package vn.quynv.springframework.webflux.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import vn.quynv.springframework.webflux.domain.Task;
import vn.quynv.springframework.webflux.domain.TaskDescriptionOnly;
import vn.quynv.springframework.webflux.service.TaskService;

@RestController
@RequestMapping("/tasks")
@Slf4j
public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping()
    public ResponseEntity<Flux<Task>> get() {
        log.info("Getting All Task");
        return ResponseEntity.ok().body(taskService.getAllTasks());
    }

    @PostMapping()
    public ResponseEntity<Mono<Task>> post(@RequestBody final Task task) {
        log.info("Create new task {}", task);
        if(taskService.isValid(task)) {
            return ResponseEntity.ok().body(taskService.createTask(task));
        }else {
            return ResponseEntity.badRequest().body(Mono.empty());
        }
    }

    @PutMapping()
    public ResponseEntity<Mono<Task>> put(@RequestBody final Task task) {
        log.info("Update a task: {}", task);
        if(taskService.isValid(task)) {
            return ResponseEntity.ok().body(taskService.updateTask(task));
        }else {
            return ResponseEntity.badRequest().body(Mono.empty());
        }
    }

    @PutMapping("/updateStatus")
    public ResponseEntity<Mono<Integer>> updateStatus(@RequestParam Integer id, @RequestParam  Boolean status) {
        log.info("Update status to {} for taskId {}", status, id);
        if(id<=0) {
            return ResponseEntity.badRequest().body(Mono.empty());
        }
        return ResponseEntity.ok().body(taskService.updateStatus(id, status));
    }

    @GetMapping("/descriptionOnly")
    public ResponseEntity<Flux<TaskDescriptionOnly>> getAllWithDescriptionOnly() {
        log.info("Get All Tasks with Description property only");
        return ResponseEntity.ok().body(taskService.getAllWithDescriptionOnly());
    }
}


