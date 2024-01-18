package com.project.todo.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.todo.Entities.Enums.TaskStatus;
import com.project.todo.Entities.ToDoTask;
import com.project.todo.Repository.ToDoRepository;
import com.project.todo.Request.CreateRequest;
import lombok.RequiredArgsConstructor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = ToDoController.class)
@RequiredArgsConstructor
public class ToDoControllerTest {

    private final MockMvc mockMvc;
    @Mock
    private ToDoRepository toDoRepository;

    @Mock
    private ToDoController toDoController;
    @Mock
    private ObjectMapper objectMapper;

    @Test
    public void it_should_create_task() throws Exception {
        // Given
        CreateRequest createRequest = new CreateRequest();
        createRequest.setTaskName("taskName");
        createRequest.setTaskStatus(TaskStatus.IN_PROGRESS);
        createRequest.setTaskDescription("This is a trial task run.");

        given(toDoRepository.save(any(ToDoTask.class)))
                .willAnswer((invocationOnMock -> invocationOnMock.getArgument(0)));

        // When
        ResultActions response = mockMvc.perform(post("/createTask")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createRequest)));

        // Then
        response.andExpect(status().isOk()).
                andExpect(status().isCreated())
                .andExpect(jsonPath("$.taskName").value("taskName"))
                .andExpect(jsonPath("$.taskStatus").value(TaskStatus.IN_PROGRESS.name()))
                .andExpect(jsonPath("$.taskDescription").value("This is a trial task run."));
    }

    @Test
    public void it_should_get_task() throws Exception {
        // Given
        ToDoTask toDoTask = new ToDoTask();
        toDoTask.setTaskName("taskName");
        toDoTask.setTaskStatus(TaskStatus.IN_PROGRESS);
        toDoTask.setDescription("This is a trial task run.");

        given(toDoController.getTasks()).willReturn(ResponseEntity.of(Optional.of(List.of(toDoTask))));

        // When
        ResultActions response = mockMvc.perform(get("/taskList", toDoTask));

        // Then
        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.taskName").value("taskName"))
                .andExpect(jsonPath("$.taskStatus").value(TaskStatus.IN_PROGRESS.name()))
                .andExpect(jsonPath("$.taskDescription").value("This is a trial task run."));
    }

    @Test
    public void it_should_update_task() throws Exception {
        // Given
        ToDoTask toDoTask = new ToDoTask();
        toDoTask.setTaskName("update");
        toDoTask.setTaskStatus(TaskStatus.DONE);
        toDoTask.setDescription("This is a trial task run for update.");

        given(toDoRepository.save(any(ToDoTask.class)))
                .willAnswer((invocationOnMock -> invocationOnMock.getArgument(0)));

        // When
        ResultActions response = mockMvc.perform(put("/update/{taskName}", toDoTask)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(toDoTask)));

        // Then
        response.andExpect(status().isOk()).
                andExpect(status().isCreated())
                .andExpect(jsonPath("$.taskName").value("update"))
                .andExpect(jsonPath("$.taskStatus").value(TaskStatus.DONE.name()))
                .andExpect(jsonPath("$.taskDescription")
                        .value("This is a trial task run for update."));
    }

    @Test
    public void it_should_delete_task() throws Exception {
        // given - precondition or setup
        String taskName = "toBeDeletedTask";
        willDoNothing().given(toDoController).deleteTask(taskName);

        // When
        ResultActions response = mockMvc.perform(delete("/delete/{taskName}", taskName));

        // Then
        response.andExpect(status().isOk());
    }
}
