use watcha1234;
create table moneymember(
	id int auto_increment primary key,
	user_id varchar(500) not null,
	user_pw varchar(500) not null,
	user_name varchar(500) not null,
	profile_image varchar(500),
	created_date datetime default now(),
	update_date datetime
);

create table sheet(
	id int auto_increment primary key,
    member_id int,
	planner_title varchar(500),
	created_date datetime default now(),
	update_date datetime,
	foreign key(member_id) references moneymember(id)
    on update cascade on delete cascade
);
create table moneyboard(
	id int auto_increment primary key,
	member_id int,
    sheet_id int,
	event_date int not null,
    event_title varchar(500) not null,
	foreign key(member_id) references moneymember(id)
    on update cascade on delete cascade,
    foreign key(sheet_id) references sheet(id)
    on update cascade on delete cascade
);
create table profile_image(
	id int auto_increment primary key,
    member_id int,
    profile_image_directory varchar(1000) not null,
	foreign key(member_id) references moneymember(id)
    on update cascade on delete cascade    
);
select * from profile_image;
select * from moneymember;