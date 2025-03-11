package org.example.taskmanagementsystem.dto;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AssignTaskRequest {
    private Set<Long> userIds;
    private Long taskId;
}
