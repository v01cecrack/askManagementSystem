package org.example.taskmanagementsystem.mapper;

import org.example.taskmanagementsystem.dto.AppUserGetDto;
import org.example.taskmanagementsystem.dto.AppUserPostDto;
import org.example.taskmanagementsystem.dto.TaskGetDto;
import org.example.taskmanagementsystem.dto.TaskPostDto;
import org.example.taskmanagementsystem.model.AppUser;
import org.example.taskmanagementsystem.model.Task;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface MapStructMapper {
    AppUserGetDto appUserGetDto(AppUser user);

    AppUser userPostDtoToAppUser(AppUserPostDto userDto);

    TaskGetDto taskToDto(Task task);

    Task taskDtoToTaskEntity(TaskPostDto task);

}
