create table music_genres
(
    id int auto_increment
        primary key,
    genre_name varchar(100) not null
);

create table musics
(
    id int auto_increment
        primary key,
    music_name varchar(100) null,
    music_genre_id int not null,
    music_price int not null,
    music_artist_name varchar(100) not null,
    release_date date not null,
    constraint musics_music_genres_id_fk
        foreign key (music_genre_id) references music_genres (id)
            on update cascade on delete cascade
);

create table users
(
    id int auto_increment
        primary key,
    username varchar(100) not null,
    email varchar(100) not null,
    password varchar(100) not null,
    role int not null,
    gender varchar(100) not null
);

create table history_header
(
    id int auto_increment
        primary key,
    total_purchase int not null,
    date_purchase date not null,
    user_id int not null,
    constraint history_header_users_id_fk
        foreign key (user_id) references users (id)
            on update cascade on delete cascade
);

create table history_detail
(
    history_id int not null,
    music_id int not null,
    constraint history_detail_history_header_id_fk
        foreign key (history_id) references history_header (id)
            on update cascade on delete cascade,
    constraint history_detail_musics_id_fk
        foreign key (music_id) references musics (id)
            on update cascade on delete cascade
);

INSERT INTO claymusicstore.users (username, email, password, role, gender) VALUES ('admin', 'admin@email.com', 'admin', 1, 'none');
