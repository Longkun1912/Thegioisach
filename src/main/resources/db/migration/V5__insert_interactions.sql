---
---Insert to rates entity for posts
---
insert into rates (rate_value,created_time,user_rate,post_rate) values
(5,timestamp '2022-01-06 08:11:08','a00e70ea-c6c2-11ed-afa1-0242ac120002','128bfa9a-cbf8-11ed-afa1-0242ac120002'),
(4,timestamp '2022-06-08 01:13:05','6e7cc348-cbf8-11ed-afa1-0242ac120002','128bfa9a-cbf8-11ed-afa1-0242ac120002'),
(5,timestamp '2022-07-01 07:12:02','a00e6d84-c6c2-11ed-afa1-0242ac120002','128bfa9a-cbf8-11ed-afa1-0242ac120002'),

(1,timestamp '2022-09-02 03:16:04','a00e70ea-c6c2-11ed-afa1-0242ac120002','ac964e56-dc4c-11ed-afa1-0242ac120002'),
(3,timestamp '2022-11-11 02:10:11','6e7cc348-cbf8-11ed-afa1-0242ac120002','ac964e56-dc4c-11ed-afa1-0242ac120002'),
(4,timestamp '2022-07-01 07:12:02','a00e6d84-c6c2-11ed-afa1-0242ac120002','ac964e56-dc4c-11ed-afa1-0242ac120002'),

(2,timestamp '2022-07-01 07:12:02','a00e6d84-c6c2-11ed-afa1-0242ac120002','77e36764-dc49-11ed-afa1-0242ac120002'),
(4,timestamp '2022-11-11 05:07:04','6e7cc348-cbf8-11ed-afa1-0242ac120002','77e36764-dc49-11ed-afa1-0242ac120002'),
(2,timestamp '2022-07-09 09:19:01','a00e70ea-c6c2-11ed-afa1-0242ac120002','77e36764-dc49-11ed-afa1-0242ac120002');

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