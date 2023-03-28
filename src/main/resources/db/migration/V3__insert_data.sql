---
---Insert to roles entity
---
insert into roles (name) values ('admin'),('user');

---
---Insert to users entity for admin
---
insert into users (id,username,image,email,phone_number,last_updated,password,user_role) values
--- Admin test account: adminno1@gmail.com
--- Password: Admin2000#
('a00e6d84-c6c2-11ed-afa1-0242ac120002','AdminNo1',file_import('D:\thegioisach\src\main\resources\static\img\admin.jpg'),'adminno1@gmail.com','01663403287',current_timestamp,'$2a$12$UCTdC2fCVD0kADyVvj04/.P05FmbLWdQvNg.z2Er7RBBs57XunHnq',1);

---
---Insert to users entity for users
---
insert into users (id,username,image,email,phone_number,status,last_updated,password,user_role) values
--- Customer test account: customerno1@gmail.com
--- Password: Customer3000$
('a00e70ea-c6c2-11ed-afa1-0242ac120002','CustomerNo1',file_import('D:\thegioisach\src\main\resources\static\img\customer\customer.jpg'),'customerno1@gmail.com','0144222543','enabled',current_timestamp,'$2a$12$alkpiiT3sW4Lvf2nKUROk.Yi4DQNuEQcfgUW9j822tM5FDibLAcBi',2),

--- Customer test account: customersupreme007@gmail.com
--- Password: CustomerSupreme007%
('6e7cc348-cbf8-11ed-afa1-0242ac120002','CustomerSupreme',file_import('D:\thegioisach\src\main\resources\static\img\customer\customer_supreme.jpg'),'customersupreme007@gmail.com','0987263176','enabled',current_timestamp,'$2a$12$4.YocQdxO8nXYG5MXsUHk.rsGOnaw38j1msatuxbF35505oLPps4m',2);

---
---Insert to categories entity
---
insert into categories (name) values
('Education'),('Business'),('Cookery'),('Politics'),('Romance'),('Thriller'),('Computer'),('Art'),
('Horror'),('Science'),('Fantasy'),('History'),('Law'),('Travel'),('Science Fiction'),('Medical'),
('Bibles'),('Sport'),('Nature'),('Language & Literature'),('Mystery & Crime'),('Health & Fitness');

---
---Insert to books entity
---
insert into books (name,author,published_date,page,description,image,content,book_category) values
('Twisted Tales','Annette de Jonge','2004-06-19',55,
'Many of the stories contained in this book began as exercises created in writing classes I attended. Each completed
story was submitted, accepted and broadcast over two community radio stations. One radio station was in Brisbane and
the other in Rockhampton at their interestingly named N.A.G. Radio Station. My understanding is that N.A.G is the
first initial of each of the owners of the radio station.
Our small writing group met once a week and we all took turns in hosting at our respective homes. There were only
ever eight of us because that is all the chairs that would fit around the dining tables.
At each gathering, a casual word for our exercise was selected from a dictionary and a random sentence taken from a
book. A time limit, usually of fifteen minutes was chosen to complete our project. Our story had to have the necessary
requirements of a story and at times ending we read out loud what we had created.
It was amazing to learn how the creative minds there had all started with the one word and sentence yet wrote
something completely different for their story.',
file_import('D:\thegioisach\src\main\resources\static\img\book\Twisted Tales.png'),
file_import('D:\thegioisach\src\main\resources\static\file\book\twisted-tales-obooko.pdf'),6),

('The Hammer of God','Reginald Cook','2001-03-08',315,
'For centuries, deep inside the bowels of the church, hidden from all except a few, a hauntingly evil cancer has spread like a murderous plague, destroying everything and everyone in its path. No one is exempt from its lure, and it will go to the depths of hell to accomplish one single goal at all costs. Global domination through a new world order. This cancer has a name: The Order of Asmodeus.

Under a binding cloak of secrecy, a brother and sisterhood of handpicked servants have been chasing down The Order of Asmodeus for as long as the demonic sect has existed. Their mandate, root out the cancer and destroy it at every turn. Their name? Il Martello di Dio. The Hammer of God.',
file_import('D:\thegioisach\src\main\resources\static\img\book\Twisted Tales.png'),
file_import('D:\thegioisach\src\main\resources\static\file\book\HammerofGod-obooko-thr0155.pdf'),6),

('Mike Japan: Free Sake and Sex','Mike Dixon','2016-12-26',41,
'I have lots of Japanese friends and I often visit them in Japan. I have noticed that the
conversation flows more freely after a few glasses of sake. I then get to hear about
things I might not otherwise know. The stories in this book were written to entertain
and provide travel advice',
file_import('D:\thegioisach\src\main\resources\static\img\book\Mike Japan.png'),
file_import('D:\thegioisach\src\main\resources\static\file\book\MikesJapan-obooko-trav0073.pdf'),14),

('Mike Australia','Mike Dixon','2016-03-16',46,
'Some call it the world''s smallest continent. Others say it''s the world''s largest island. Either way, Australia is BIG. The distance from Perth to Cairns is about 3,500 km (2,000 miles), which is roughly the same as Gibraltar to St Petersburg, Vancouver to New Orleans or Tokyo to Hanoi. Australia is almost exactly the same size as the USA (minus Alaska) but has only 23 million people to America''s 300 million. Apart from the coastal fringes, it is a dry sun-baked land. The south has a temperate (sometimes cold) climate and the top third is in the tropics. A range of mountains runs down the east coast. Rain falls on the seaward side and this is where the bulk of the population lives. Further areas of habitation are to be found in soggy Tasmania, around Adelaide and in the vicinity of Perth. The rest of the continent is sparsely populated. There''s more in the book, lots more!',
file_import('D:\thegioisach\src\main\resources\static\img\book\Mike Australia.png'),
file_import('D:\thegioisach\src\main\resources\static\file\book\MikesAustralia-obooko-trav0072.pdf'),
14);

--('','','',46,
--'',
--file_import(''),
--file_import(''),
--14),


