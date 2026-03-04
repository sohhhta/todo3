package com.example.todo.service.task;

import com.example.todo.repository.task.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    public List<TaskEntity>find(TaskSearchEntity searchEntity)
    {
        return taskRepository.select(searchEntity);
    }

    public Optional<TaskEntity> findById(long taskId) {
        return taskRepository.selectById(taskId);
    }

    @Transactional /// 例外発生時にインサート文をロールバックする
    public void create(TaskEntity newEntity) {
        taskRepository.insert(newEntity);
    }

    @Transactional
    public void update(TaskEntity entity) {
        taskRepository.update(entity);
    }

    @Transactional
    public void delete(long id) {
        taskRepository.delete(id);
    }

    /// 削除機能のために追加した内容
    @Transactional
    public void deleteAll(List<Long> ids) {
        if (ids != null && !ids.isEmpty()) {
            taskRepository.deleteAll(ids);
        }
    }
}