package com.finalproject.bugme.projectControllerTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.finalproject.bugme.person.Person;
import com.finalproject.bugme.project.Project;
import com.finalproject.bugme.project.ProjectController;
import com.finalproject.bugme.project.ProjectRepository;
import com.finalproject.bugme.project.ProjectService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertAll;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ProjectController.class)
public class ProjectControllerTest {


    @MockBean
    private ProjectService projectService;

    ObjectMapper mapper = new ObjectMapper();
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void it_should_return_created_project() throws Exception{
        Project newProject = projectService.findById(2L);

        mockMvc.perform(get("/projects/project/2")
                .content(mapper.writeValueAsString(newProject))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());


    }


}
