/* matching-service */

drop table dislikes cascade constraints;
drop table likes cascade constraints;

create table dislikes (
	affected_id number(10) not null,
	user_id number(10) not null
);

create table likes (
	affected_id number(10) not null,
	user_id number(10) not null
);

alter table dislikes add constraint dislikes_pk primary key (affected_id, user_id);
alter table likes add constraint likes_pk primary key (affected_id, user_id);

commit;