package com.finalproject.bugme.projectControllerTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.finalproject.bugme.person.Person;
import com.finalproject.bugme.project.Project;
import com.finalproject.bugme.project.ProjectController;
import com.finalproject.bugme.project.ProjectRepository;
import com.finalproject.bugme.project.ProjectService;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@WebMvcTest(ProjectController.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ProjectControllerTest {


    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;


    @InjectMocks
    ProjectService projectService;

    @Test
    public void get_whenProjectFound_thenReturnProject() throws Exception{
        //arrange
        Person person = new Person("login","passowrd","name");
        Project project = new Project("TEST","Description",person);

        Mockito.when(projectService.findByName("TEST")).thenReturn(project);

        String expectedJson = objectMapper.writeValueAsString(project);

        //act
        mockMvc.perform(get("/projects/project/2")).andExpect(status().isOk())
                .andExpect(content().json(expectedJson));


    }
}
