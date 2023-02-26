package com.finalproject.bugme.issueRepositoryTest;

import com.finalproject.bugme.BugmeApplication;
import com.finalproject.bugme.config.SecurityConfig;
import com.finalproject.bugme.issue.Issue;
import com.finalproject.bugme.issue.IssueRepository;
import com.finalproject.bugme.person.Person;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@EntityScan("com.finalproject.bugme")
@ContextConfiguration(classes= BugmeApplication.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class IssueRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;



    @Autowired
    IssueRepository issueRepository;


    @Test
    public void should_find_all_issues(){

        Iterable<Issue> issues = issueRepository.findAll();

        assertThat(issues).hasSize(1);
    }
}
