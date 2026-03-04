INSERT INTO users(id, username, password, authority) VALUES('user', 'ユーザー1', '$2a$10$oxSJl.keBwxmsMLkcT9lPeAIxfNTPH1oPEpOvkRspW5lgurNGzD.u', 'USER');
INSERT INTO users(id, username, password, authority) VALUES('admin', '管理者1', '$2a$10$oxSJl.keBwxmsMLkcT9lPeAIxfNTPH1oPEpOvkRspW5lgurNGzD.u', 'ADMIN');

///初期データ
INSERT INTO tasks(summary, description, status, user_id) VALUES('Spring Boot を学ぶ', 'TODO アプリを作る', 'DONE', 'user');
INSERT INTO tasks(summary, description, status, user_id) VALUES('Spring Security を学ぶ', 'ログイン機能を作る', 'TODO', 'admin');