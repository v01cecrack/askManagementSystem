package org.example.taskmanagementsystem.service;

import org.example.taskmanagementsystem.dto.*;
import org.example.taskmanagementsystem.mapper.MapStructMapper;
import org.example.taskmanagementsystem.model.AppUser;
import org.example.taskmanagementsystem.model.Task;
import org.example.taskmanagementsystem.repository.TaskRepository;
import org.example.taskmanagementsystem.util.UserUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final MapStructMapper mapper;
    private final UserService userService;

    public TaskService(TaskRepository taskRepository, MapStructMapper mapper, UserService userService) {
        this.taskRepository = taskRepository;
        this.mapper = mapper;
        this.userService = userService;
    }

    public TaskGetDto save(TaskPostDto taskPostDto) {
        Task task = mapper.taskDtoToTaskEntity(taskPostDto);
        taskRepository.save(task);
        return mapper.taskToDto(task);
    }

    public List<TaskGetDto> getAllTasksByUserName() {
        String email = UserUtil.getEmail();
        List<Task> list = taskRepository.findByUserEmail(email);
        return list.stream().map(mapper::taskToDto).toList();
    }

    public List<TaskGetDto> getAll() {
        return taskRepository.findAll()
                .stream()
                .map(mapper::taskToDto)
                .toList();
    }

    public TaskGetDto getOneTask(Long taskId) {
        return mapper.taskToDto(taskRepository.findById(taskId).get());
    }

    public void delete(Long taskId) {
        taskRepository.deleteById(taskId);
    }

    public AssignTaskResponse assignTaskToUser(AssignTaskRequest assignTaskRequest) {
        Optional<Task> oldTask = taskRepository.findById(assignTaskRequest.getTaskId());
        if (oldTask.isPresent()) {
            Task task = oldTask.get();
            Set<AppUser> members = assignTaskRequest
                    .getUserIds()
                    .stream()
                    .map(userId -> userService.findByUserId(userId).get())
                    .collect(Collectors.toSet());
            task.setMembers(members);
            taskRepository.save(task);
            Set<Members> member = members.stream().map(user -> {
                Members m = new Members();
                m.setUserId(user.getId());
                m.setUsername(user.getUsername());
                return m;
            }).collect(Collectors.toSet());
            return AssignTaskResponse.builder()
                    .id(task.getId())
                    .title(task.getTitle())
                    .assignedTo(task.getAssignedTo())
                    .createdDate(task.getCreatedDate())
                    .description(task.getDescription())
                    .dueDate(task.getDueDate())
                    .members(member)
                    .status(task.getStatus())
                    .build();
        }
        return null;
    }


}
