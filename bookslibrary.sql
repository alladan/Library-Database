/* Save and run the entire script in one file */
/* Student information database tables 
   Run this from the root */

drop database bookslibrary;
create database bookslibrary;

use bookslibrary;

drop table if exists books;
drop table if exists users;

create table books (
  bookID integer not null unique, 
  isbn varchar(25),
  title varchar(35),
  author varchar(25), 
  checkout integer,
  constraint pkBooks primary key (bookID));

create table users (
  userID integer not null unique,
  name varchar(25),
  address varchar(35),
  constraint pkUsers primary key (userID));

insert into books values (
1,'0133761312','Intro to Java Programming (10e)','Y. Daniel Liang',null);
insert into books values (
2,'0133761312','Intro to Java Programming (10e)','Y. Daniel Liang',null);
insert into books values (
3,'0133761312','Intro to Java Programming (10e)','Y. Daniel Liang',null);

insert into books values(
4,'0062320130','The English Spy','Daniel Silva',null);
insert into books values(
5,'0062320130','The English Spy','Daniel Silva',null);
insert into books values(
6,'0062320130','The English Spy','Daniel Silva',null);

insert into books values(
7,'0345534182','Circling the Sun','Deckle Edge',null);
insert into books values(
8,'0345534182','Circling the Sun','Deckle Edge',null);

insert into books values(
9,'1627792120','Six of Crows','Leigh Bardugo',null);
insert into books values(
10,'1627792120','Six of Crows','Leigh Bardugo',null);

insert into users values(
1,'John Smith','3240 Walnut Avenue');
insert into users values(
2,'Mary Taylor','1782 Main Street West');
insert into users values(
3,'James Johnson','1384 New Street');
insert into users values(
4,'Patricia Williams','1662 Monroe Drive');
insert into users values(
5,'Robert Jones','7792 Railroad Street');
insert into users values(
6,'Linda Brown','1532 King Street');
insert into users values(
7,'Michael Davis','2914 Pennsylvania Avenue');
insert into users values(
8,'Barbara Miller','5054 12th Street East');
insert into users values(
9,'William Wilson','3604 Deerfield Drive');
insert into users values(
10,'Elizabeth Moore','9863 College Avenue');


