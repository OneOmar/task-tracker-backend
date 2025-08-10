package com.omardev.tasks.repositories;

import com.omardev.tasks.entities.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class TaskRepositoryTest {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TaskListRepository taskListRepository;

    private LocalDateTime now;

    @BeforeEach
    void setUp() {
        now = LocalDateTime.now();
    }

    @Test
    @DisplayName("Find all tasks by task list ID")
    void testFindAllByTaskListId() {
        TaskList list = taskListRepository.save(
                new TaskList(null, "List 1", "Desc", null, now, now));

        Task task1 = new Task(null, "Task 1", "Desc 1", null,
                TaskPriority.HIGH, TaskStatus.OPEN, list, now, now);
        Task task2 = new Task(null, "Task 2", "Desc 2", null,
                TaskPriority.LOW, TaskStatus.OPEN, list, now, now);

        taskRepository.saveAll(List.of(task1, task2));

        List<Task> tasks = taskRepository.findAllByTaskListId(list.getId());
        assertThat(tasks).hasSize(2);
    }

    @Test
    @DisplayName("Find a task by list ID and task ID")
    void testFindByTaskListIdAndId() {
        TaskList list = taskListRepository.save(
                new TaskList(null, "List 2", null, null, now, now));

        Task task = taskRepository.save(
                new Task(null, "Single Task", null, null,
                        TaskPriority.MEDIUM, TaskStatus.OPEN, list, now, now));

        Optional<Task> found = taskRepository.findByTaskListIdAndId(list.getId(), task.getId());
        assertThat(found).isPresent();
        assertThat(found.get().getTitle()).isEqualTo("Single Task");
    }

    @Test
    @DisplayName("Delete a task by list ID and task ID")
    void testDeleteByTaskListIdAndId() {
        TaskList list = taskListRepository.save(
                new TaskList(null, "List 3", null, null, now, now));

        Task task = taskRepository.save(
                new Task(null, "Delete Me", null, null,
                        TaskPriority.LOW, TaskStatus.OPEN, list, now, now));

        taskRepository.deleteByTaskListIdAndId(list.getId(), task.getId());

        assertThat(taskRepository.findById(task.getId())).isEmpty();
    }
}
