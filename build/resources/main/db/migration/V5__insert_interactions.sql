---
---Insert to likes entity for posts
---
insert into likes (user_like,post_like) values
('a00e70ea-c6c2-11ed-afa1-0242ac120002','128bfa9a-cbf8-11ed-afa1-0242ac120002'),
('6e7cc348-cbf8-11ed-afa1-0242ac120002','128bfa9a-cbf8-11ed-afa1-0242ac120002');

---
---Insert to likes entity for books
---
insert into likes (user_like,book_like) values
('a00e70ea-c6c2-11ed-afa1-0242ac120002',1),
('a00e70ea-c6c2-11ed-afa1-0242ac120002',2),
('6e7cc348-cbf8-11ed-afa1-0242ac120002',1),
('6e7cc348-cbf8-11ed-afa1-0242ac120002',3);

---
---Insert to dislikes entity for posts
---
insert into dislikes (user_dislike,post_dislike) values
('a00e70ea-c6c2-11ed-afa1-0242ac120002','128bfa9a-cbf8-11ed-afa1-0242ac120002');

---
---Insert to dislikes entity for books
---
insert into dislikes (user_dislike,book_dislike) values
('a00e70ea-c6c2-11ed-afa1-0242ac120002',1),
('a00e70ea-c6c2-11ed-afa1-0242ac120002',2),
('a00e70ea-c6c2-11ed-afa1-0242ac120002',4),
('6e7cc348-cbf8-11ed-afa1-0242ac120002',2),
('6e7cc348-cbf8-11ed-afa1-0242ac120002',3);

---
---Insert to comments entity for posts
---
insert into comments (text,created_time,user_comment,post_comment) values
('Please read follow the rule everyone.',current_timestamp,'a00e70ea-c6c2-11ed-afa1-0242ac120002','128bfa9a-cbf8-11ed-afa1-0242ac120002'),
('Ok.',current_timestamp,'6e7cc348-cbf8-11ed-afa1-0242ac120002','128bfa9a-cbf8-11ed-afa1-0242ac120002');

---
---Insert to comments entity for books
---
insert into comments (text,created_time,user_comment,book_comment) values
('This book is bloody inspiring.',current_timestamp,'a00e70ea-c6c2-11ed-afa1-0242ac120002',1),
('What a thrilling tale omg!',current_timestamp,'a00e70ea-c6c2-11ed-afa1-0242ac120002',2),
('I don''t really agree with the author experience. Just my opinion.',current_timestamp,'6e7cc348-cbf8-11ed-afa1-0242ac120002',3),
('I''m jealous with the author for his travels.',current_timestamp,'6e7cc348-cbf8-11ed-afa1-0242ac120002',4);

---
---Insert to replies entity for books
---
insert into replies (text,created_time,user_reply,comment_reply) values
('Couldn''t agree more haha.',current_timestamp,'6e7cc348-cbf8-11ed-afa1-0242ac120002',1);