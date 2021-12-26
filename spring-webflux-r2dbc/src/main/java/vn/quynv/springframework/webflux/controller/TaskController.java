package vn.quynv.springframework.webflux.controller;

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
public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping()
    public ResponseEntity<Flux<Task>> get() {
        return ResponseEntity.ok().body(taskService.getAllTasks());
    }

    @PostMapping()
    public ResponseEntity<Mono<Task>> post(@RequestBody final Task task) {
        if(taskService.isValid(task)) {
            return ResponseEntity.ok().body(taskService.createTask(task));
        }else {
            return ResponseEntity.badRequest().body(Mono.empty());
        }
    }

    @PutMapping()
    public ResponseEntity<Mono<Task>> put(@RequestBody final Task task) {
        if(taskService.isValid(task)) {
            return ResponseEntity.ok().body(taskService.updateTask(task));
        }else {
            return ResponseEntity.badRequest().body(Mono.empty());
        }
    }

    @PutMapping("/updateStatus")
    public ResponseEntity<Mono<Integer>> updateStatus(@RequestParam Integer id, @RequestParam  Boolean status) {
        if(id<=0) {
            return ResponseEntity.badRequest().body(Mono.empty());
        }
        return ResponseEntity.ok().body(taskService.updateStatus(id, status));
    }

    @GetMapping("/descriptionOnly")
    public ResponseEntity<Flux<TaskDescriptionOnly>> getAllWithDescriptionOnly() {
        return ResponseEntity.ok().body(taskService.getAllWithDescriptionOnly());
    }
}
