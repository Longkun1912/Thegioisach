create table roles (
    id int primary key generated by default as identity,
    name varchar(64)
);

create table users (
    id uuid primary key,
    username varchar(100),
    image bytea,
    email varchar(200),
    phone_number varchar(11),
    status varchar(8),
    last_updated timestamp,
    password varchar(200),
    user_role int not null,
    foreign key(user_role) references roles(id)
);

create table posts (
    id uuid primary key,
    title varchar(200),
    content_image bytea,
    content_text varchar(10000),
    created_time timestamp,
    user_post uuid not null,
    foreign key(user_post) references users(id) on delete cascade
);


create table post_share (
    user_id uuid,
    post_id uuid,
    foreign key (user_id) references users(id) on delete cascade,
    foreign key (post_id) references posts(id) on delete cascade
);

create table chats (
    id uuid primary key,
    user1 uuid not null,
    user2 uuid not null,
    foreign key (user1) references users(id),
    foreign key (user2) references users(id)
);

create table categories (
    id int primary key generated by default as identity,
    name varchar(64)
);

create table books (
    id int primary key generated by default as identity,
    title varchar(64),
    author varchar(64),
    published_date date,
    page int,
    description varchar(10000),
    image bytea,
    content bytea,
    recommended_age int,
    book_category int not null,
    foreign key(book_category) references categories(id)
);

create table favorites (
    id uuid primary key,
    name varchar(64),
    created_time timestamp,
    user_favorite uuid not null,
    foreign key(user_favorite) references users(id)
);

create table book_favorite_details (
    book_id int not null,
    favorite_id uuid not null,
    foreign key (book_id) references books(id),
    foreign key (favorite_id) references favorites(id)
);

create table comments (
    id int primary key generated by default as identity,
    text varchar(1000),
    parent_id int,
    created_time timestamp,
    user_comment uuid not null,
    post_comment uuid,
    foreign key(user_comment) references users(id),
    foreign key(post_comment) references posts(id)
);

create table rates (
    id int primary key generated by default as identity,
    rate_value int check (rate_value between 1 and 5),
    created_time timestamp,
    user_rate uuid not null,
    post_rate uuid not null,
    foreign key(user_rate) references users(id),
    foreign key(post_rate) references posts(id)
)