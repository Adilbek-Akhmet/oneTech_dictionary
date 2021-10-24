
create table word (id bigint generated by default as identity, definition varchar(255) not null, name varchar(255) not null, type_id bigint, primary key (id));
create table word_synonym_list (word_id bigint not null, synonym_list_id bigint not null);
create table word_type (id bigint generated by default as identity, name varchar(255) not null, primary key (id));
alter table word add constraint UK_word_name unique (name);
alter table word_synonym_list add constraint UK_synonym_list_id unique (synonym_list_id);
alter table word_type add constraint UK_word_type_name unique (name);
alter table word add constraint FK_word_type_id foreign key (type_id) references word_type;
alter table word_synonym_list add constraint FK_synonym_list_id foreign key (synonym_list_id) references word;
alter table word_synonym_list add constraint FK_word_id foreign key (word_id) references word;

insert into word_type(name) values ('термин');
insert into word_type(name) values ('не термин');

insert into word(name, definition, type_id) values ('привет', 'привет это', 2);
insert into word(name, definition, type_id) values ('ноутбук', 'ноутбук это', 1);
insert into word(name, definition, type_id) values ('джава', 'джава это', 1)