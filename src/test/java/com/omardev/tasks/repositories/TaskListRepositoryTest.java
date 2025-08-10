package com.omardev.tasks.repositories;

import com.omardev.tasks.entities.TaskList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class TaskListRepositoryTest {

    @Autowired
    private TaskListRepository taskListRepository;

    private LocalDateTime now;

    @BeforeEach
    void setUp() {
        now = LocalDateTime.now();
    }

    @Test
    @DisplayName("Save and retrieve a task list by ID")
    void testSaveAndFindById() {
        TaskList list = new TaskList(null, "Main List", "Description", null, now, now);

        TaskList saved = taskListRepository.save(list);

        Optional<TaskList> found = taskListRepository.findById(saved.getId());
        assertThat(found).isPresent();
        assertThat(found.get().getTitle()).isEqualTo("Main List");
    }

    @Test
    @DisplayName("Find all task lists")
    void testFindAll() {
        taskListRepository.save(new TaskList(null, "List 1", null, null, now, now));
        taskListRepository.save(new TaskList(null, "List 2", null, null, now, now));

        assertThat(taskListRepository.findAll()).hasSize(2);
    }

    @Test
    @DisplayName("Delete a task list by ID")
    void testDeleteById() {
        TaskList list = taskListRepository.save(new TaskList(null, "Delete List", null, null, now, now));

        taskListRepository.deleteById(list.getId());

        assertThat(taskListRepository.findById(list.getId())).isEmpty();
    }
}
