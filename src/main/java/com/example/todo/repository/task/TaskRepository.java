package com.example.todo.repository.task;

import com.example.todo.service.task.TaskEntity;
import com.example.todo.service.task.TaskSearchEntity;
import com.example.todo.service.user.UserEntity;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;

@Mapper
public interface TaskRepository {

    @Select("""
        <script>
            SELECT id, summary, description, status, user_id
            FROM tasks
            WHERE user_id = #{condition.userId}
            <if test='condition.summary != null and !condition.summary.isBlank()'>
                AND summary LIKE CONCAT('%', #{condition.summary}, '%')
            </if>
            <if test='condition.status != null and !condition.status.isEmpty()'>
                AND status IN (
                <foreach item='item' index='index' collection='condition.status' separator=','>
                    #{item}
                </foreach>
                )
            </if>
        </script>
        """)
    List<TaskEntity> select(@Param("condition") TaskSearchEntity condition);

    @Select("SELECT id, summary, description, status, user_id FROM tasks WHERE id = #{taskId}")
    Optional<TaskEntity> selectById(@Param("taskId") long taskId);

    @Insert("""
    INSERT INTO tasks (summary, description, status, user_id)
    VALUES(#{task.summary}, #{task.description}, #{task.status}, #{task.userId})
    """)
    void insert(@Param("task") TaskEntity newEntity);

    @Update("""
            UPDATE tasks
            SET
                summary = #{task.summary},
                description = #{task.description},
                status = #{task.status}
            WHERE
                id = #{task.id}
            """)
    void update(@Param("task") TaskEntity entity);

    @Delete("DELETE FROM tasks WHERE id = #{taskId}")
    void delete(@Param("taskId") long id);

    /// 削除機能のために追加した内容 foreachはループ処理
    @Delete("""
            <script>
            DELETE FROM tasks WHERE id IN (
                <foreach item='id' collection='ids' separator=','>
                    #{id}
                </foreach>
            )
            </script>
            """)
    void deleteAll(@Param("ids") List<Long> ids);

    @Select("SELECT id, username, password, authority FROM users WHERE id = #{userId}")
    Optional<UserEntity> selectUserById(@Param("userId") String userId);

    @Insert("""
            INSERT INTO users (id, username, password, authority)
            VALUES (#{user.id}, #{user.username}, #{user.password}, #{user.authority})
            """)
    void insertUser(@Param("user") UserEntity user);
}