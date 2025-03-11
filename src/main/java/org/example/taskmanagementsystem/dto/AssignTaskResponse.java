package org.example.taskmanagementsystem.dto;

import lombok.*;
import org.example.taskmanagementsystem.model.TaskStatus;

import java.util.Date;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AssignTaskResponse {
    private Long id;
    private String title;
    private String description;
    private Long assignedTo;
    private TaskStatus status;
    private Date createdDate;
    private Date dueDate;
    private Set<Members> members;
}
