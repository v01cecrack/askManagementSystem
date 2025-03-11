package org.example.taskmanagementsystem.controller;

import org.example.taskmanagementsystem.dto.AssignTaskRequest;
import org.example.taskmanagementsystem.dto.AssignTaskResponse;
import org.example.taskmanagementsystem.dto.TaskGetDto;
import org.example.taskmanagementsystem.dto.TaskPostDto;
import org.example.taskmanagementsystem.service.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/task")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public ResponseEntity<List<TaskGetDto>> getAllTasks() {
        return ResponseEntity.ok(taskService.getAll());
    }

    @PostMapping
    public ResponseEntity<TaskGetDto> add(@RequestBody TaskPostDto task) {
        return ResponseEntity.ok(taskService.save(task));
    }

    @PutMapping
    public ResponseEntity<TaskGetDto> update(@RequestBody TaskPostDto task) throws Exception {
        if (task.getId() == null) {
            throw new Exception("Task Id is required");
        }
        TaskPostDto taskPostDto = TaskPostDto
                .builder()
                .title(task.getTitle())
                .assignedTo(task.getAssignedTo())
                .createdDate(task.getCreatedDate())
                .description(task.getDescription())
                .dueDate(task.getDueDate())
                .id(task.getId())
                .build();
        return ResponseEntity.ok(taskService.save(taskPostDto));
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable Long id) {
        taskService.delete(id);
    }

    @PostMapping("/assign")
    public ResponseEntity<AssignTaskResponse> assign(@RequestBody AssignTaskRequest taskRequest) {
        return ResponseEntity.ok(taskService.assignTaskToUser(taskRequest));
    }


}
