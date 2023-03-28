---
---Insert to posts entity
---
insert into posts (id,title,content_text,content_image,created_time,user_post) values
('128bfa9a-cbf8-11ed-afa1-0242ac120002', 'My rules & politics of downloading of Mike''s travel books',
'Although you do not have to pay for this book, the author’s intellectual property rights
remain fully protected by international Copyright law. You are licensed to use this
digital copy strictly for your personal enjoyment only. This edition must not be hosted
or redistributed on other websites without the author’s written permission nor offered
for sale in any form. If you paid for this book, or to gain access to it, we suggest you
demand an immediate refund and report the transaction to the author.',
file_import('D:\thegioisach\src\main\resources\static\file\book\MikesJapan-obooko-trav0073.pdf'),
current_timestamp,'a00e70ea-c6c2-11ed-afa1-0242ac120002');

---
---Insert to chats entity
---
insert into chats (id,user1,user2) values
('38eacdbe-cbf9-11ed-afa1-0242ac120002','a00e70ea-c6c2-11ed-afa1-0242ac120002','6e7cc348-cbf8-11ed-afa1-0242ac120002');

---
---Insert to favorites entity
---
insert into favorites (id,name,created_time,user_favorite) values
('e0aa9ca4-cbfa-11ed-afa1-0242ac120002','My favorite books',current_timestamp,'a00e70ea-c6c2-11ed-afa1-0242ac120002'),
('eb505e1e-cbfa-11ed-afa1-0242ac120002','My travel',current_timestamp,'6e7cc348-cbf8-11ed-afa1-0242ac120002'),
('eb505aea-cbfa-11ed-afa1-0242ac120002','Thrilling time',current_timestamp,'6e7cc348-cbf8-11ed-afa1-0242ac120002');

---
---Insert to book_favorite_details entity
---
insert into book_favorite_details (book_id,favorite_id) values
(1,'eb505aea-cbfa-11ed-afa1-0242ac120002'),
(2,'eb505aea-cbfa-11ed-afa1-0242ac120002'),
(4,'eb505e1e-cbfa-11ed-afa1-0242ac120002'),
(2,'e0aa9ca4-cbfa-11ed-afa1-0242ac120002'),
(3,'e0aa9ca4-cbfa-11ed-afa1-0242ac120002');


