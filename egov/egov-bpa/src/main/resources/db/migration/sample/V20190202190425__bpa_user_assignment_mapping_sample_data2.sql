
----New designations ---
insert into eg_designation (id,name, description, chartofaccounts,version,createddate,lastmodifieddate,createdby,lastmodifiedby,code) values(nextval('seq_eg_designation'),'Assistant Engineer','Assistant Engineer',null,0,current_date,current_date,1,1,'AE');

insert into eg_designation (id,name, description, chartofaccounts,version,createddate,lastmodifieddate,createdby,lastmodifiedby,code) values(nextval('seq_eg_designation'),'Town Surveyor','Town Surveyor',null,0,current_date,current_date,1,1,'TOWNSURV');

insert into eg_designation (id,name, description, chartofaccounts,version,createddate,lastmodifieddate,createdby,lastmodifiedby,code) values(nextval('seq_eg_designation'),'Section Clerk','Section Clerk',null,0,current_date,current_date,1,1,'SECCRK');


--department designation mapping --

INSERT INTO egeis_deptdesig (id, designation, department, outsourcedposts, sanctionedposts, version, createddate, lastmodifieddate, createdby, lastmodifiedby) VALUES (nextval('seq_egeis_deptdesig'), 
(select id from eg_designation where name = 'Town Surveyor'), (select id from eg_department where code  ='TP'), 0, 2, 0, now(), now(), 1, 1);

INSERT INTO egeis_deptdesig (id, designation, department, outsourcedposts, sanctionedposts, version, createddate, lastmodifieddate, createdby, lastmodifiedby) VALUES (nextval('seq_egeis_deptdesig'), 
(select id from eg_designation where name = 'Secretary'), (select id from eg_department where code  ='TP'), 0, 2, 0, now(), now(), 1, 1);

INSERT INTO egeis_deptdesig (id, designation, department, outsourcedposts, sanctionedposts, version, createddate, lastmodifieddate, createdby, lastmodifiedby) VALUES (nextval('seq_egeis_deptdesig'), 
(select id from eg_designation where name = 'Town Planning Building Overseer'), (select id from eg_department where code  ='TP'), 0, 4, 0, now(), now(), 1, 1);

INSERT INTO egeis_deptdesig (id, designation, department, outsourcedposts, sanctionedposts, version, createddate, lastmodifieddate, createdby, lastmodifiedby) VALUES (nextval('seq_egeis_deptdesig'), 
(select id from eg_designation where name = 'Corporation Engineer'), (select id from eg_department where code  ='TP'), 0, 4, 0, now(), now(), 1, 1);

INSERT INTO egeis_deptdesig (id, designation, department, outsourcedposts, sanctionedposts, version, createddate, lastmodifieddate, createdby, lastmodifiedby) VALUES (nextval('seq_egeis_deptdesig'), 
(select id from eg_designation where name = 'Executive engineer'), (select id from eg_department where code  ='TP'), 0, 4, 0, now(), now(), 1, 1);

INSERT INTO egeis_deptdesig (id, designation, department, outsourcedposts, sanctionedposts, version, createddate, lastmodifieddate, createdby, lastmodifiedby) VALUES (nextval('seq_egeis_deptdesig'), 
(select id from eg_designation where name = 'Assistant engineer'), (select id from eg_department where code  ='TP'), 0, 4, 0, now(), now(), 1, 1);

INSERT INTO egeis_deptdesig (id, designation, department, outsourcedposts, sanctionedposts, version, createddate, lastmodifieddate, createdby, lastmodifiedby) VALUES (nextval('seq_egeis_deptdesig'), 
(select id from eg_designation where name = 'Assistant executive engineer'), (select id from eg_department where code  ='TP'), 0, 4, 0, now(), now(), 1, 1);

INSERT INTO egeis_deptdesig (id, designation, department, outsourcedposts, sanctionedposts, version, createddate, lastmodifieddate, createdby, lastmodifiedby) VALUES (nextval('seq_egeis_deptdesig'), 
(select id from eg_designation where name = 'Superintendent'), (select id from eg_department where code  ='TP'), 0, 4, 0, now(), now(), 1, 1);

INSERT INTO egeis_deptdesig (id, designation, department, outsourcedposts, sanctionedposts, version, createddate, lastmodifieddate, createdby, lastmodifiedby) VALUES (nextval('seq_egeis_deptdesig'), 
(select id from eg_designation where name = 'Section Clerk'), (select id from eg_department where code  ='TP'), 0, 4, 0, now(), now(), 1, 1);

------- Position mapping ----

insert into eg_position(name,id,deptdesig,createddate,lastmodifieddate,createdby,lastmodifiedby,ispostoutsourced,version)
values('TP_Town Surveyor_11',nextval('seq_eg_position'),(select id from egeis_deptdesig where designation=(select id from eg_designation where name = 'Town Surveyor') and department=(select id from eg_department where code='TP')),current_date,current_date,1,1,false,0);
insert into eg_position(name,id,deptdesig,createddate,lastmodifieddate,createdby,lastmodifiedby,ispostoutsourced,version)
values('TP_Town Surveyor_2',nextval('seq_eg_position'),(select id from egeis_deptdesig where designation=(select id from eg_designation where name = 'Town Surveyor') and department=(select id from eg_department where code='TP')),current_date,current_date,1,1,false,0);

insert into eg_position(name,id,deptdesig,createddate,lastmodifieddate,createdby,lastmodifiedby,ispostoutsourced,version)
values('TP_Secretary_1',nextval('seq_eg_position'),(select id from egeis_deptdesig where designation=(select id from eg_designation where name = 'Secretary') and department=(select id from eg_department where code='TP')),current_date,current_date,1,1,false,0);
insert into eg_position(name,id,deptdesig,createddate,lastmodifieddate,createdby,lastmodifiedby,ispostoutsourced,version)
values('TP_Secretary_2',nextval('seq_eg_position'),(select id from egeis_deptdesig where designation=(select id from eg_designation where name = 'Secretary') and department=(select id from eg_department where code='TP')),current_date,current_date,1,1,false,0);


insert into eg_position(name,id,deptdesig,createddate,lastmodifieddate,createdby,lastmodifiedby,ispostoutsourced,version)
values('TP_Town Planning Building Overseer_1',nextval('seq_eg_position'),(select id from egeis_deptdesig where designation=(select id from eg_designation where name = 'Town Planning Building Overseer') and department=(select id from eg_department where code='TP')),current_date,current_date,1,1,false,0);
insert into eg_position(name,id,deptdesig,createddate,lastmodifieddate,createdby,lastmodifiedby,ispostoutsourced,version)
values('TP_Town Planning Building Overseer_2',nextval('seq_eg_position'),(select id from egeis_deptdesig where designation=(select id from eg_designation where name = 'Town Planning Building Overseer') and department=(select id from eg_department where code='TP')),current_date,current_date,1,1,false,0);
insert into eg_position(name,id,deptdesig,createddate,lastmodifieddate,createdby,lastmodifiedby,ispostoutsourced,version)
values('TP_Town Planning Building Overseer_3',nextval('seq_eg_position'),(select id from egeis_deptdesig where designation=(select id from eg_designation where name = 'Town Planning Building Overseer') and department=(select id from eg_department where code='TP')),current_date,current_date,1,1,false,0);
insert into eg_position(name,id,deptdesig,createddate,lastmodifieddate,createdby,lastmodifiedby,ispostoutsourced,version)
values('TP_Town Planning Building Overseer_4',nextval('seq_eg_position'),(select id from egeis_deptdesig where designation=(select id from eg_designation where name = 'Town Planning Building Overseer') and department=(select id from eg_department where code='TP')),current_date,current_date,1,1,false,0);

insert into eg_position(name,id,deptdesig,createddate,lastmodifieddate,createdby,lastmodifiedby,ispostoutsourced,version)
values('TP_Corporation Engineer_1',nextval('seq_eg_position'),(select id from egeis_deptdesig where designation=(select id from eg_designation where name = 'Corporation Engineer') and department=(select id from eg_department where code='TP')),current_date,current_date,1,1,false,0);
insert into eg_position(name,id,deptdesig,createddate,lastmodifieddate,createdby,lastmodifiedby,ispostoutsourced,version)
values('TP_Corporation Engineer_2',nextval('seq_eg_position'),(select id from egeis_deptdesig where designation=(select id from eg_designation where name = 'Corporation Engineer') and department=(select id from eg_department where code='TP')),current_date,current_date,1,1,false,0);
insert into eg_position(name,id,deptdesig,createddate,lastmodifieddate,createdby,lastmodifiedby,ispostoutsourced,version)
values('TP_Corporation Engineer_3',nextval('seq_eg_position'),(select id from egeis_deptdesig where designation=(select id from eg_designation where name = 'Corporation Engineer') and department=(select id from eg_department where code='TP')),current_date,current_date,1,1,false,0);
insert into eg_position(name,id,deptdesig,createddate,lastmodifieddate,createdby,lastmodifiedby,ispostoutsourced,version)
values('TP_Corporation Engineer_4',nextval('seq_eg_position'),(select id from egeis_deptdesig where designation=(select id from eg_designation where name = 'Corporation Engineer') and department=(select id from eg_department where code='TP')),current_date,current_date,1,1,false,0);

insert into eg_position(name,id,deptdesig,createddate,lastmodifieddate,createdby,lastmodifiedby,ispostoutsourced,version)
values('TP_Executive engineer_1',nextval('seq_eg_position'),(select id from egeis_deptdesig where designation=(select id from eg_designation where name = 'Executive engineer') and department=(select id from eg_department where code='TP')),current_date,current_date,1,1,false,0);
insert into eg_position(name,id,deptdesig,createddate,lastmodifieddate,createdby,lastmodifiedby,ispostoutsourced,version)
values('TP_Executive engineer_2',nextval('seq_eg_position'),(select id from egeis_deptdesig where designation=(select id from eg_designation where name = 'Executive engineer') and department=(select id from eg_department where code='TP')),current_date,current_date,1,1,false,0);
insert into eg_position(name,id,deptdesig,createddate,lastmodifieddate,createdby,lastmodifiedby,ispostoutsourced,version)
values('TP_Executive engineer_3',nextval('seq_eg_position'),(select id from egeis_deptdesig where designation=(select id from eg_designation where name = 'Executive engineer') and department=(select id from eg_department where code='TP')),current_date,current_date,1,1,false,0);
insert into eg_position(name,id,deptdesig,createddate,lastmodifieddate,createdby,lastmodifiedby,ispostoutsourced,version)
values('TP_Executive engineer_4',nextval('seq_eg_position'),(select id from egeis_deptdesig where designation=(select id from eg_designation where name = 'Executive engineer') and department=(select id from eg_department where code='TP')),current_date,current_date,1,1,false,0);

insert into eg_position(name,id,deptdesig,createddate,lastmodifieddate,createdby,lastmodifiedby,ispostoutsourced,version)
values('TP_Assistant engineer_1',nextval('seq_eg_position'),(select id from egeis_deptdesig where designation=(select id from eg_designation where name = 'Assistant engineer') and department=(select id from eg_department where code='TP')),current_date,current_date,1,1,false,0);
insert into eg_position(name,id,deptdesig,createddate,lastmodifieddate,createdby,lastmodifiedby,ispostoutsourced,version)
values('TP_Assistant engineer_2',nextval('seq_eg_position'),(select id from egeis_deptdesig where designation=(select id from eg_designation where name = 'Assistant engineer') and department=(select id from eg_department where code='TP')),current_date,current_date,1,1,false,0);
insert into eg_position(name,id,deptdesig,createddate,lastmodifieddate,createdby,lastmodifiedby,ispostoutsourced,version)
values('TP_Assistant engineer_3',nextval('seq_eg_position'),(select id from egeis_deptdesig where designation=(select id from eg_designation where name = 'Assistant engineer') and department=(select id from eg_department where code='TP')),current_date,current_date,1,1,false,0);
insert into eg_position(name,id,deptdesig,createddate,lastmodifieddate,createdby,lastmodifiedby,ispostoutsourced,version)
values('TP_Assistant engineer_4',nextval('seq_eg_position'),(select id from egeis_deptdesig where designation=(select id from eg_designation where name = 'Assistant engineer') and department=(select id from eg_department where code='TP')),current_date,current_date,1,1,false,0);

insert into eg_position(name,id,deptdesig,createddate,lastmodifieddate,createdby,lastmodifiedby,ispostoutsourced,version)
values('TP_Assistant executive engineer_1',nextval('seq_eg_position'),(select id from egeis_deptdesig where designation=(select id from eg_designation where name = 'Assistant executive engineer') and department=(select id from eg_department where code='TP')),current_date,current_date,1,1,false,0);
insert into eg_position(name,id,deptdesig,createddate,lastmodifieddate,createdby,lastmodifiedby,ispostoutsourced,version)
values('TP_Assistant executive engineer_2',nextval('seq_eg_position'),(select id from egeis_deptdesig where designation=(select id from eg_designation where name = 'Assistant executive engineer') and department=(select id from eg_department where code='TP')),current_date,current_date,1,1,false,0);
insert into eg_position(name,id,deptdesig,createddate,lastmodifieddate,createdby,lastmodifiedby,ispostoutsourced,version)
values('TP_Assistant executive engineer_3',nextval('seq_eg_position'),(select id from egeis_deptdesig where designation=(select id from eg_designation where name = 'Assistant executive engineer') and department=(select id from eg_department where code='TP')),current_date,current_date,1,1,false,0);
insert into eg_position(name,id,deptdesig,createddate,lastmodifieddate,createdby,lastmodifiedby,ispostoutsourced,version)
values('TP_Assistant executive engineer_4',nextval('seq_eg_position'),(select id from egeis_deptdesig where designation=(select id from eg_designation where name = 'Assistant executive engineer') and department=(select id from eg_department where code='TP')),current_date,current_date,1,1,false,0);

insert into eg_position(name,id,deptdesig,createddate,lastmodifieddate,createdby,lastmodifiedby,ispostoutsourced,version)
values('TP_Superintendent_1',nextval('seq_eg_position'),(select id from egeis_deptdesig where designation=(select id from eg_designation where name = 'Superintendent') and department=(select id from eg_department where code='TP')),current_date,current_date,1,1,false,0);
insert into eg_position(name,id,deptdesig,createddate,lastmodifieddate,createdby,lastmodifiedby,ispostoutsourced,version)
values('TP_Superintendent_2',nextval('seq_eg_position'),(select id from egeis_deptdesig where designation=(select id from eg_designation where name = 'Superintendent') and department=(select id from eg_department where code='TP')),current_date,current_date,1,1,false,0);
insert into eg_position(name,id,deptdesig,createddate,lastmodifieddate,createdby,lastmodifiedby,ispostoutsourced,version)
values('TP_Superintendent_3',nextval('seq_eg_position'),(select id from egeis_deptdesig where designation=(select id from eg_designation where name = 'Superintendent') and department=(select id from eg_department where code='TP')),current_date,current_date,1,1,false,0);
insert into eg_position(name,id,deptdesig,createddate,lastmodifieddate,createdby,lastmodifiedby,ispostoutsourced,version)
values('TP_Superintendent_4',nextval('seq_eg_position'),(select id from egeis_deptdesig where designation=(select id from eg_designation where name = 'Superintendent') and department=(select id from eg_department where code='TP')),current_date,current_date,1,1,false,0);

insert into eg_position(name,id,deptdesig,createddate,lastmodifieddate,createdby,lastmodifiedby,ispostoutsourced,version)
values('TP_Section Clerk_1',nextval('seq_eg_position'),(select id from egeis_deptdesig where designation=(select id from eg_designation where name = 'Section Clerk') and department=(select id from eg_department where code='TP')),current_date,current_date,1,1,false,0);
insert into eg_position(name,id,deptdesig,createddate,lastmodifieddate,createdby,lastmodifiedby,ispostoutsourced,version)
values('TP_Section Clerk_2',nextval('seq_eg_position'),(select id from egeis_deptdesig where designation=(select id from eg_designation where name = 'Section Clerk') and department=(select id from eg_department where code='TP')),current_date,current_date,1,1,false,0);
insert into eg_position(name,id,deptdesig,createddate,lastmodifieddate,createdby,lastmodifiedby,ispostoutsourced,version)
values('TP_Section Clerk_3',nextval('seq_eg_position'),(select id from egeis_deptdesig where designation=(select id from eg_designation where name = 'Section Clerk') and department=(select id from eg_department where code='TP')),current_date,current_date,1,1,false,0);
insert into eg_position(name,id,deptdesig,createddate,lastmodifieddate,createdby,lastmodifiedby,ispostoutsourced,version)
values('TP_Section Clerk_4',nextval('seq_eg_position'),(select id from egeis_deptdesig where designation=(select id from eg_designation where name = 'Section Clerk') and department=(select id from eg_department where code='TP')),current_date,current_date,1,1,false,0);


------- user creation ----


insert into eg_user(id,locale,username,password,pwdexpirydate,mobilenumber,createddate,lastmodifieddate,createdby,
lastmodifiedby,active,name,gender,type,version) values(nextval('seq_eg_user'),'en_IN','TS1',
'$2a$10$uheIOutTnD33x7CDqac1zOL8DMiuz7mWplToPgcf7oxAI9OzRKxmK','01-Jan-2099','1234567891',
current_date,current_date,1,1,true,'TS1',1,'EMPLOYEE',0);

insert into egeis_employee(id,code,employeestatus,employeetype,version) 
values((select id from eg_user where username='TS1'),'TS1','EMPLOYED',(select id from egeis_employeetype where name='Permanent'),0);


INSERT INTO egeis_assignment (id,fund,function,designation,functionary,department,position,grade,lastmodifiedby,lastmodifieddate,createddate,createdby,fromdate,todate,version,employee,isprimary) 
VALUES (nextval('seq_egeis_assignment'),null,null,(SELECT id FROM eg_designation WHERE name='Town Surveyor'),null,(SELECT id FROM eg_department WHERE name='TP'),(SELECT id FROM eg_position 
WHERE name='TP_Town Surveyor_11'),1,1,now(),now(),1,'01-Apr-2018','31-Mar-2020',1,(SELECT id FROM egeis_employee WHERE code='TS1'),true);


insert into eg_userrole(roleid,userid) values((select id from eg_role where upper(name)='EMPLOYEE'),(select id from eg_user where username='TS1'));

insert into egeis_jurisdiction (id,employee,boundarytype,createddate,lastmodifieddate,createdby,lastmodifiedby,version,boundary)
select nextval('seq_egeis_jurisdiction'), (select id from egeis_employee where upper(code)='TS1'),boundarytype ,current_date,
current_date,1,1,0, id from eg_boundary where boundarytype in ( select id from eg_boundary_type  where name='Ward' and hierarchytype=(select ID from eg_hierarchy_type where name='ADMINISTRATION')) AND name='Election Ward No 1' order by name ;
insert into egeis_jurisdiction (id,employee,boundarytype,createddate,lastmodifieddate,createdby,lastmodifiedby,version,boundary)
select nextval('seq_egeis_jurisdiction'), (select id from egeis_employee where upper(code)='TS1'),boundarytype ,current_date,
current_date,1,1,0, id from eg_boundary where boundarytype in ( select id from eg_boundary_type  where name='Ward' and hierarchytype=(select ID from eg_hierarchy_type where name='ADMINISTRATION')) AND name='Election Ward No 2' order by name ;
insert into egeis_jurisdiction (id,employee,boundarytype,createddate,lastmodifieddate,createdby,lastmodifiedby,version,boundary)
select nextval('seq_egeis_jurisdiction'), (select id from egeis_employee where upper(code)='TS1'),boundarytype ,current_date,
current_date,1,1,0, id from eg_boundary where boundarytype in ( select id from eg_boundary_type  where name='Ward' and hierarchytype=(select ID from eg_hierarchy_type where name='ADMINISTRATION')) AND name='Election Ward No 3' order by name ;

insert into egeis_jurisdiction (id,employee,boundarytype,createddate,lastmodifieddate,createdby,lastmodifiedby,version,boundary)
values(nextval('seq_egeis_jurisdiction'),(select id from egeis_employee where upper(code)='TS1'),(select id from eg_boundary_type where upper(name)=upper('City') 
and hierarchytype in(select id from eg_hierarchy_type where upper(name)='ADMINISTRATION')),current_date,current_date,1,1,0,(select id from eg_boundary where boundarytype =(select id from eg_boundary_type where upper(name)=upper('City') 
and hierarchytype in(select id from eg_hierarchy_type where upper(name)='ADMINISTRATION'))));


insert into eg_user(id,locale,username,password,pwdexpirydate,mobilenumber,createddate,lastmodifieddate,createdby,
lastmodifiedby,active,name,gender,type,version) values(nextval('seq_eg_user'),'en_IN','TS2',
'$2a$10$uheIOutTnD33x7CDqac1zOL8DMiuz7mWplToPgcf7oxAI9OzRKxmK','01-Jan-2099','1234567891',
current_date,current_date,1,1,true,'TS2',1,'EMPLOYEE',0);

insert into egeis_employee(id,code,employeestatus,employeetype,version) 
values((select id from eg_user where username='TS2'),'TS2','EMPLOYED',(select id from egeis_employeetype where name='Permanent'),0);


INSERT INTO egeis_assignment (id,fund,function,designation,functionary,department,position,grade,lastmodifiedby,lastmodifieddate,createddate,createdby,fromdate,todate,version,employee,isprimary) 
VALUES (nextval('seq_egeis_assignment'),null,null,(SELECT id FROM eg_designation WHERE name='Town Surveyor'),null,(SELECT id FROM eg_department WHERE name='TP'),(SELECT id FROM eg_position 
WHERE name='TP_Town Surveyor_2'),1,1,now(),now(),1,'01-Apr-2018','31-Mar-2020',1,(SELECT id FROM egeis_employee WHERE code='TS2'),true);


insert into eg_userrole(roleid,userid) values((select id from eg_role where upper(name)='EMPLOYEE'),(select id from eg_user where username='TS2'));

insert into egeis_jurisdiction (id,employee,boundarytype,createddate,lastmodifieddate,createdby,lastmodifiedby,version,boundary)
select nextval('seq_egeis_jurisdiction'), (select id from egeis_employee where upper(code)='TS2'),boundarytype ,current_date,
current_date,1,1,0, id from eg_boundary where boundarytype in ( select id from eg_boundary_type  where name='Ward' and hierarchytype=(select ID from eg_hierarchy_type where name='ADMINISTRATION')) AND name='Election Ward No 4' order by name ;
insert into egeis_jurisdiction (id,employee,boundarytype,createddate,lastmodifieddate,createdby,lastmodifiedby,version,boundary)
select nextval('seq_egeis_jurisdiction'), (select id from egeis_employee where upper(code)='TS2'),boundarytype ,current_date,
current_date,1,1,0, id from eg_boundary where boundarytype in ( select id from eg_boundary_type  where name='Ward' and hierarchytype=(select ID from eg_hierarchy_type where name='ADMINISTRATION')) AND name='Election Ward No 5' order by name ;
insert into egeis_jurisdiction (id,employee,boundarytype,createddate,lastmodifieddate,createdby,lastmodifiedby,version,boundary)
select nextval('seq_egeis_jurisdiction'), (select id from egeis_employee where upper(code)='TS2'),boundarytype ,current_date,
current_date,1,1,0, id from eg_boundary where boundarytype in ( select id from eg_boundary_type  where name='Ward' and hierarchytype=(select ID from eg_hierarchy_type where name='ADMINISTRATION')) AND name='Election Ward No 6' order by name ;

insert into egeis_jurisdiction (id,employee,boundarytype,createddate,lastmodifieddate,createdby,lastmodifiedby,version,boundary)
values(nextval('seq_egeis_jurisdiction'),(select id from egeis_employee where upper(code)='TS2'),(select id from eg_boundary_type where upper(name)=upper('City') 
and hierarchytype in(select id from eg_hierarchy_type where upper(name)='ADMINISTRATION')),current_date,current_date,1,1,0,(select id from eg_boundary where boundarytype =(select id from eg_boundary_type where upper(name)=upper('City') 
and hierarchytype in(select id from eg_hierarchy_type where upper(name)='ADMINISTRATION'))));




insert into eg_user(id,locale,username,password,pwdexpirydate,mobilenumber,createddate,lastmodifieddate,createdby,
lastmodifiedby,active,name,gender,type,version) values(nextval('seq_eg_user'),'en_IN','SEC1',
'$2a$10$uheIOutTnD33x7CDqac1zOL8DMiuz7mWplToPgcf7oxAI9OzRKxmK','01-Jan-2099','1234567891',
current_date,current_date,1,1,true,'SEC1',1,'EMPLOYEE',0);

insert into egeis_employee(id,code,employeestatus,employeetype,version) 
values((select id from eg_user where username='SEC1'),'SEC1','EMPLOYED',(select id from egeis_employeetype where name='Permanent'),0);


INSERT INTO egeis_assignment (id,fund,function,designation,functionary,department,position,grade,lastmodifiedby,lastmodifieddate,createddate,createdby,fromdate,todate,version,employee,isprimary) 
VALUES (nextval('seq_egeis_assignment'),null,null,(SELECT id FROM eg_designation WHERE name='Secretary'),null,(SELECT id FROM eg_department WHERE name='TP'),(SELECT id FROM eg_position 
WHERE name='TP_Secretary_1'),1,1,now(),now(),1,'01-Apr-2018','31-Mar-2020',1,(SELECT id FROM egeis_employee WHERE code='SEC1'),true);


insert into eg_userrole(roleid,userid) values((select id from eg_role where upper(name)='EMPLOYEE'),(select id from eg_user where username='SEC1'));

insert into egeis_jurisdiction (id,employee,boundarytype,createddate,lastmodifieddate,createdby,lastmodifiedby,version,boundary)
select nextval('seq_egeis_jurisdiction'), (select id from egeis_employee where upper(code)='SEC1'),boundarytype ,current_date,
current_date,1,1,0, id from eg_boundary where boundarytype in ( select id from eg_boundary_type  where name='Ward' and hierarchytype=(select ID from eg_hierarchy_type where name='ADMINISTRATION')) AND name='Election Ward No 1' order by name ;
insert into egeis_jurisdiction (id,employee,boundarytype,createddate,lastmodifieddate,createdby,lastmodifiedby,version,boundary)
select nextval('seq_egeis_jurisdiction'), (select id from egeis_employee where upper(code)='SEC1'),boundarytype ,current_date,
current_date,1,1,0, id from eg_boundary where boundarytype in ( select id from eg_boundary_type  where name='Ward' and hierarchytype=(select ID from eg_hierarchy_type where name='ADMINISTRATION')) AND name='Election Ward No 2' order by name ;
insert into egeis_jurisdiction (id,employee,boundarytype,createddate,lastmodifieddate,createdby,lastmodifiedby,version,boundary)
select nextval('seq_egeis_jurisdiction'), (select id from egeis_employee where upper(code)='SEC1'),boundarytype ,current_date,
current_date,1,1,0, id from eg_boundary where boundarytype in ( select id from eg_boundary_type  where name='Ward' and hierarchytype=(select ID from eg_hierarchy_type where name='ADMINISTRATION')) AND name='Election Ward No 3' order by name ;

insert into egeis_jurisdiction (id,employee,boundarytype,createddate,lastmodifieddate,createdby,lastmodifiedby,version,boundary)
values(nextval('seq_egeis_jurisdiction'),(select id from egeis_employee where upper(code)='SEC1'),(select id from eg_boundary_type where upper(name)=upper('City') 
and hierarchytype in(select id from eg_hierarchy_type where upper(name)='ADMINISTRATION')),current_date,current_date,1,1,0,(select id from eg_boundary where boundarytype =(select id from eg_boundary_type where upper(name)=upper('City') 
and hierarchytype in(select id from eg_hierarchy_type where upper(name)='ADMINISTRATION'))));



insert into eg_user(id,locale,username,password,pwdexpirydate,mobilenumber,createddate,lastmodifieddate,createdby,
lastmodifiedby,active,name,gender,type,version) values(nextval('seq_eg_user'),'en_IN','SEC2',
'$2a$10$uheIOutTnD33x7CDqac1zOL8DMiuz7mWplToPgcf7oxAI9OzRKxmK','01-Jan-2099','1234567891',
current_date,current_date,1,1,true,'SEC2',1,'EMPLOYEE',0);

insert into egeis_employee(id,code,employeestatus,employeetype,version) 
values((select id from eg_user where username='SEC2'),'SEC2','EMPLOYED',(select id from egeis_employeetype where name='Permanent'),0);


INSERT INTO egeis_assignment (id,fund,function,designation,functionary,department,position,grade,lastmodifiedby,lastmodifieddate,createddate,createdby,fromdate,todate,version,employee,isprimary) 
VALUES (nextval('seq_egeis_assignment'),null,null,(SELECT id FROM eg_designation WHERE name='Secretary'),null,(SELECT id FROM eg_department WHERE name='TP'),(SELECT id FROM eg_position 
WHERE name='TP_Secretary_2'),1,1,now(),now(),1,'01-Apr-2018','31-Mar-2020',1,(SELECT id FROM egeis_employee WHERE code='SEC2'),true);


insert into eg_userrole(roleid,userid) values((select id from eg_role where upper(name)='EMPLOYEE'),(select id from eg_user where username='SEC2'));

insert into egeis_jurisdiction (id,employee,boundarytype,createddate,lastmodifieddate,createdby,lastmodifiedby,version,boundary)
select nextval('seq_egeis_jurisdiction'), (select id from egeis_employee where upper(code)='SEC2'),boundarytype ,current_date,
current_date,1,1,0, id from eg_boundary where boundarytype in ( select id from eg_boundary_type  where name='Ward' and hierarchytype=(select ID from eg_hierarchy_type where name='ADMINISTRATION')) AND name='Election Ward No 4' order by name ;
insert into egeis_jurisdiction (id,employee,boundarytype,createddate,lastmodifieddate,createdby,lastmodifiedby,version,boundary)
select nextval('seq_egeis_jurisdiction'), (select id from egeis_employee where upper(code)='SEC2'),boundarytype ,current_date,
current_date,1,1,0, id from eg_boundary where boundarytype in ( select id from eg_boundary_type  where name='Ward' and hierarchytype=(select ID from eg_hierarchy_type where name='ADMINISTRATION')) AND name='Election Ward No 5' order by name ;
insert into egeis_jurisdiction (id,employee,boundarytype,createddate,lastmodifieddate,createdby,lastmodifiedby,version,boundary)
select nextval('seq_egeis_jurisdiction'), (select id from egeis_employee where upper(code)='SEC2'),boundarytype ,current_date,
current_date,1,1,0, id from eg_boundary where boundarytype in ( select id from eg_boundary_type  where name='Ward' and hierarchytype=(select ID from eg_hierarchy_type where name='ADMINISTRATION')) AND name='Election Ward No 6' order by name ;

insert into egeis_jurisdiction (id,employee,boundarytype,createddate,lastmodifieddate,createdby,lastmodifiedby,version,boundary)
values(nextval('seq_egeis_jurisdiction'),(select id from egeis_employee where upper(code)='SEC2'),(select id from eg_boundary_type where upper(name)=upper('City') 
and hierarchytype in(select id from eg_hierarchy_type where upper(name)='ADMINISTRATION')),current_date,current_date,1,1,0,(select id from eg_boundary where boundarytype =(select id from eg_boundary_type where upper(name)=upper('City') 
and hierarchytype in(select id from eg_hierarchy_type where upper(name)='ADMINISTRATION'))));


insert into eg_user(id,locale,username,password,pwdexpirydate,mobilenumber,createddate,lastmodifieddate,createdby,
lastmodifiedby,active,name,gender,type,version) values(nextval('seq_eg_user'),'en_IN','TPBO1',
'$2a$10$uheIOutTnD33x7CDqac1zOL8DMiuz7mWplToPgcf7oxAI9OzRKxmK','01-Jan-2099','1234567891',
current_date,current_date,1,1,true,'TPBO1',1,'EMPLOYEE',0);

insert into egeis_employee(id,code,employeestatus,employeetype,version) 
values((select id from eg_user where username='TPBO1'),'TPBO1','EMPLOYED',(select id from egeis_employeetype where name='Permanent'),0);


INSERT INTO egeis_assignment (id,fund,function,designation,functionary,department,position,grade,lastmodifiedby,lastmodifieddate,createddate,createdby,fromdate,todate,version,employee,isprimary) 
VALUES (nextval('seq_egeis_assignment'),null,null,(SELECT id FROM eg_designation WHERE name='Town Planning Building Overseer'),null,(SELECT id FROM eg_department WHERE name='TP'),(SELECT id FROM eg_position 
WHERE name='TP_Town Planning Building Overseer_1'),1,1,now(),now(),1,'01-Apr-2018','31-Mar-2020',1,(SELECT id FROM egeis_employee WHERE code='TPBO1'),true);


insert into eg_userrole(roleid,userid) values((select id from eg_role where upper(name)='EMPLOYEE'),(select id from eg_user where username='TPBO1'));

insert into egeis_jurisdiction (id,employee,boundarytype,createddate,lastmodifieddate,createdby,lastmodifiedby,version,boundary)
select nextval('seq_egeis_jurisdiction'), (select id from egeis_employee where upper(code)='TPBO1'),boundarytype ,current_date,
current_date,1,1,0, id from eg_boundary where boundarytype in ( select id from eg_boundary_type  where name='Ward' and hierarchytype=(select ID from eg_hierarchy_type where name='ADMINISTRATION')) AND name='Election Ward No 1' order by name ;
insert into egeis_jurisdiction (id,employee,boundarytype,createddate,lastmodifieddate,createdby,lastmodifiedby,version,boundary)
select nextval('seq_egeis_jurisdiction'), (select id from egeis_employee where upper(code)='TPBO1'),boundarytype ,current_date,
current_date,1,1,0, id from eg_boundary where boundarytype in ( select id from eg_boundary_type  where name='Ward' and hierarchytype=(select ID from eg_hierarchy_type where name='ADMINISTRATION')) AND name='Election Ward No 2' order by name ;


insert into egeis_jurisdiction (id,employee,boundarytype,createddate,lastmodifieddate,createdby,lastmodifiedby,version,boundary)
values(nextval('seq_egeis_jurisdiction'),(select id from egeis_employee where upper(code)='TPBO1'),(select id from eg_boundary_type where upper(name)=upper('City') 
and hierarchytype in(select id from eg_hierarchy_type where upper(name)='ADMINISTRATION')),current_date,current_date,1,1,0,(select id from eg_boundary where boundarytype =(select id from eg_boundary_type where upper(name)=upper('City') 
and hierarchytype in(select id from eg_hierarchy_type where upper(name)='ADMINISTRATION'))));



insert into eg_user(id,locale,username,password,pwdexpirydate,mobilenumber,createddate,lastmodifieddate,createdby,
lastmodifiedby,active,name,gender,type,version) values(nextval('seq_eg_user'),'en_IN','TPBO2',
'$2a$10$uheIOutTnD33x7CDqac1zOL8DMiuz7mWplToPgcf7oxAI9OzRKxmK','01-Jan-2099','1234567891',
current_date,current_date,1,1,true,'TPBO2',1,'EMPLOYEE',0);

insert into egeis_employee(id,code,employeestatus,employeetype,version) 
values((select id from eg_user where username='TPBO2'),'TPBO2','EMPLOYED',(select id from egeis_employeetype where name='Permanent'),0);


INSERT INTO egeis_assignment (id,fund,function,designation,functionary,department,position,grade,lastmodifiedby,lastmodifieddate,createddate,createdby,fromdate,todate,version,employee,isprimary) 
VALUES (nextval('seq_egeis_assignment'),null,null,(SELECT id FROM eg_designation WHERE name='Town Planning Building Overseer'),null,(SELECT id FROM eg_department WHERE name='TP'),(SELECT id FROM eg_position 
WHERE name='TP_Town Planning Building Overseer_2'),1,1,now(),now(),1,'01-Apr-2018','31-Mar-2020',1,(SELECT id FROM egeis_employee WHERE code='TPBO2'),true);


insert into eg_userrole(roleid,userid) values((select id from eg_role where upper(name)='EMPLOYEE'),(select id from eg_user where username='TPBO2'));

insert into egeis_jurisdiction (id,employee,boundarytype,createddate,lastmodifieddate,createdby,lastmodifiedby,version,boundary)
select nextval('seq_egeis_jurisdiction'), (select id from egeis_employee where upper(code)='TPBO2'),boundarytype ,current_date,
current_date,1,1,0, id from eg_boundary where boundarytype in ( select id from eg_boundary_type  where name='Ward' and hierarchytype=(select ID from eg_hierarchy_type where name='ADMINISTRATION')) AND name='Election Ward No 3' order by name ;
insert into egeis_jurisdiction (id,employee,boundarytype,createddate,lastmodifieddate,createdby,lastmodifiedby,version,boundary)
select nextval('seq_egeis_jurisdiction'), (select id from egeis_employee where upper(code)='TPBO2'),boundarytype ,current_date,
current_date,1,1,0, id from eg_boundary where boundarytype in ( select id from eg_boundary_type  where name='Ward' and hierarchytype=(select ID from eg_hierarchy_type where name='ADMINISTRATION')) AND name='Election Ward No 4' order by name ;


insert into egeis_jurisdiction (id,employee,boundarytype,createddate,lastmodifieddate,createdby,lastmodifiedby,version,boundary)
values(nextval('seq_egeis_jurisdiction'),(select id from egeis_employee where upper(code)='TPBO2'),(select id from eg_boundary_type where upper(name)=upper('City') 
and hierarchytype in(select id from eg_hierarchy_type where upper(name)='ADMINISTRATION')),current_date,current_date,1,1,0,(select id from eg_boundary where boundarytype =(select id from eg_boundary_type where upper(name)=upper('City') 
and hierarchytype in(select id from eg_hierarchy_type where upper(name)='ADMINISTRATION'))));


insert into eg_user(id,locale,username,password,pwdexpirydate,mobilenumber,createddate,lastmodifieddate,createdby,
lastmodifiedby,active,name,gender,type,version) values(nextval('seq_eg_user'),'en_IN','TPBO3',
'$2a$10$uheIOutTnD33x7CDqac1zOL8DMiuz7mWplToPgcf7oxAI9OzRKxmK','01-Jan-2099','1234567891',
current_date,current_date,1,1,true,'TPBO3',1,'EMPLOYEE',0);

insert into egeis_employee(id,code,employeestatus,employeetype,version) 
values((select id from eg_user where username='TPBO3'),'TPBO3','EMPLOYED',(select id from egeis_employeetype where name='Permanent'),0);


INSERT INTO egeis_assignment (id,fund,function,designation,functionary,department,position,grade,lastmodifiedby,lastmodifieddate,createddate,createdby,fromdate,todate,version,employee,isprimary) 
VALUES (nextval('seq_egeis_assignment'),null,null,(SELECT id FROM eg_designation WHERE name='Town Planning Building Overseer'),null,(SELECT id FROM eg_department WHERE name='TP'),(SELECT id FROM eg_position 
WHERE name='TP_Town Planning Building Overseer_3'),1,1,now(),now(),1,'01-Apr-2018','31-Mar-2020',1,(SELECT id FROM egeis_employee WHERE code='TPBO3'),true);


insert into eg_userrole(roleid,userid) values((select id from eg_role where upper(name)='EMPLOYEE'),(select id from eg_user where username='TPBO3'));

insert into egeis_jurisdiction (id,employee,boundarytype,createddate,lastmodifieddate,createdby,lastmodifiedby,version,boundary)
select nextval('seq_egeis_jurisdiction'), (select id from egeis_employee where upper(code)='TPBO3'),boundarytype ,current_date,
current_date,1,1,0, id from eg_boundary where boundarytype in ( select id from eg_boundary_type  where name='Ward' and hierarchytype=(select ID from eg_hierarchy_type where name='ADMINISTRATION')) AND name='Election Ward No 5' order by name ;


insert into egeis_jurisdiction (id,employee,boundarytype,createddate,lastmodifieddate,createdby,lastmodifiedby,version,boundary)
values(nextval('seq_egeis_jurisdiction'),(select id from egeis_employee where upper(code)='TPBO3'),(select id from eg_boundary_type where upper(name)=upper('City') 
and hierarchytype in(select id from eg_hierarchy_type where upper(name)='ADMINISTRATION')),current_date,current_date,1,1,0,(select id from eg_boundary where boundarytype =(select id from eg_boundary_type where upper(name)=upper('City') 
and hierarchytype in(select id from eg_hierarchy_type where upper(name)='ADMINISTRATION'))));


insert into eg_user(id,locale,username,password,pwdexpirydate,mobilenumber,createddate,lastmodifieddate,createdby,
lastmodifiedby,active,name,gender,type,version) values(nextval('seq_eg_user'),'en_IN','TPBO4',
'$2a$10$uheIOutTnD33x7CDqac1zOL8DMiuz7mWplToPgcf7oxAI9OzRKxmK','01-Jan-2099','1234567891',
current_date,current_date,1,1,true,'TPBO4',1,'EMPLOYEE',0);

insert into egeis_employee(id,code,employeestatus,employeetype,version) 
values((select id from eg_user where username='TPBO4'),'TPBO4','EMPLOYED',(select id from egeis_employeetype where name='Permanent'),0);


INSERT INTO egeis_assignment (id,fund,function,designation,functionary,department,position,grade,lastmodifiedby,lastmodifieddate,createddate,createdby,fromdate,todate,version,employee,isprimary) 
VALUES (nextval('seq_egeis_assignment'),null,null,(SELECT id FROM eg_designation WHERE name='Town Planning Building Overseer'),null,(SELECT id FROM eg_department WHERE name='TP'),(SELECT id FROM eg_position 
WHERE name='TP_Town Planning Building Overseer_4'),1,1,now(),now(),1,'01-Apr-2018','31-Mar-2020',1,(SELECT id FROM egeis_employee WHERE code='TPBO4'),true);


insert into eg_userrole(roleid,userid) values((select id from eg_role where upper(name)='EMPLOYEE'),(select id from eg_user where username='TPBO4'));

insert into egeis_jurisdiction (id,employee,boundarytype,createddate,lastmodifieddate,createdby,lastmodifiedby,version,boundary)
select nextval('seq_egeis_jurisdiction'), (select id from egeis_employee where upper(code)='TPBO4'),boundarytype ,current_date,
current_date,1,1,0, id from eg_boundary where boundarytype in ( select id from eg_boundary_type  where name='Ward' and hierarchytype=(select ID from eg_hierarchy_type where name='ADMINISTRATION')) AND name='Election Ward No 6' order by name ;


insert into egeis_jurisdiction (id,employee,boundarytype,createddate,lastmodifieddate,createdby,lastmodifiedby,version,boundary)
values(nextval('seq_egeis_jurisdiction'),(select id from egeis_employee where upper(code)='TPBO4'),(select id from eg_boundary_type where upper(name)=upper('City') 
and hierarchytype in(select id from eg_hierarchy_type where upper(name)='ADMINISTRATION')),current_date,current_date,1,1,0,(select id from eg_boundary where boundarytype =(select id from eg_boundary_type where upper(name)=upper('City') 
and hierarchytype in(select id from eg_hierarchy_type where upper(name)='ADMINISTRATION'))));



insert into eg_user(id,locale,username,password,pwdexpirydate,mobilenumber,createddate,lastmodifieddate,createdby,
lastmodifiedby,active,name,gender,type,version) values(nextval('seq_eg_user'),'en_IN','TPCORPENG1',
'$2a$10$uheIOutTnD33x7CDqac1zOL8DMiuz7mWplToPgcf7oxAI9OzRKxmK','01-Jan-2099','1234567891',
current_date,current_date,1,1,true,'TPCORPENG1',1,'EMPLOYEE',0);

insert into egeis_employee(id,code,employeestatus,employeetype,version) 
values((select id from eg_user where username='TPCORPENG1'),'TPCORPENG1','EMPLOYED',(select id from egeis_employeetype where name='Permanent'),0);


INSERT INTO egeis_assignment (id,fund,function,designation,functionary,department,position,grade,lastmodifiedby,lastmodifieddate,createddate,createdby,fromdate,todate,version,employee,isprimary) 
VALUES (nextval('seq_egeis_assignment'),null,null,(SELECT id FROM eg_designation WHERE name='Corporation Engineer'),null,(SELECT id FROM eg_department WHERE name='TP'),(SELECT id FROM eg_position 
WHERE name='TP_Corporation Engineer_1'),1,1,now(),now(),1,'01-Apr-2018','31-Mar-2020',1,(SELECT id FROM egeis_employee WHERE code='TPCORPENG1'),true);


insert into eg_userrole(roleid,userid) values((select id from eg_role where upper(name)='EMPLOYEE'),(select id from eg_user where username='TPCORPENG1'));

insert into egeis_jurisdiction (id,employee,boundarytype,createddate,lastmodifieddate,createdby,lastmodifiedby,version,boundary)
select nextval('seq_egeis_jurisdiction'), (select id from egeis_employee where upper(code)='TPCORPENG1'),boundarytype ,current_date,
current_date,1,1,0, id from eg_boundary where boundarytype in ( select id from eg_boundary_type  where name='Ward' and hierarchytype=(select ID from eg_hierarchy_type where name='ADMINISTRATION')) AND name='Election Ward No 1' order by name ;
insert into egeis_jurisdiction (id,employee,boundarytype,createddate,lastmodifieddate,createdby,lastmodifiedby,version,boundary)
select nextval('seq_egeis_jurisdiction'), (select id from egeis_employee where upper(code)='TPCORPENG1'),boundarytype ,current_date,
current_date,1,1,0, id from eg_boundary where boundarytype in ( select id from eg_boundary_type  where name='Ward' and hierarchytype=(select ID from eg_hierarchy_type where name='ADMINISTRATION')) AND name='Election Ward No 2' order by name ;


insert into egeis_jurisdiction (id,employee,boundarytype,createddate,lastmodifieddate,createdby,lastmodifiedby,version,boundary)
values(nextval('seq_egeis_jurisdiction'),(select id from egeis_employee where upper(code)='TPCORPENG1'),(select id from eg_boundary_type where upper(name)=upper('City') 
and hierarchytype in(select id from eg_hierarchy_type where upper(name)='ADMINISTRATION')),current_date,current_date,1,1,0,(select id from eg_boundary where boundarytype =(select id from eg_boundary_type where upper(name)=upper('City') 
and hierarchytype in(select id from eg_hierarchy_type where upper(name)='ADMINISTRATION'))));



insert into eg_user(id,locale,username,password,pwdexpirydate,mobilenumber,createddate,lastmodifieddate,createdby,
lastmodifiedby,active,name,gender,type,version) values(nextval('seq_eg_user'),'en_IN','TPCORPENG2',
'$2a$10$uheIOutTnD33x7CDqac1zOL8DMiuz7mWplToPgcf7oxAI9OzRKxmK','01-Jan-2099','1234567891',
current_date,current_date,1,1,true,'TPCORPENG2',1,'EMPLOYEE',0);

insert into egeis_employee(id,code,employeestatus,employeetype,version) 
values((select id from eg_user where username='TPCORPENG2'),'TPCORPENG2','EMPLOYED',(select id from egeis_employeetype where name='Permanent'),0);


INSERT INTO egeis_assignment (id,fund,function,designation,functionary,department,position,grade,lastmodifiedby,lastmodifieddate,createddate,createdby,fromdate,todate,version,employee,isprimary) 
VALUES (nextval('seq_egeis_assignment'),null,null,(SELECT id FROM eg_designation WHERE name='Corporation Engineer'),null,(SELECT id FROM eg_department WHERE name='TP'),(SELECT id FROM eg_position 
WHERE name='TP_Corporation Engineer_2'),1,1,now(),now(),1,'01-Apr-2018','31-Mar-2020',1,(SELECT id FROM egeis_employee WHERE code='TPCORPENG2'),true);


insert into eg_userrole(roleid,userid) values((select id from eg_role where upper(name)='EMPLOYEE'),(select id from eg_user where username='TPCORPENG2'));

insert into egeis_jurisdiction (id,employee,boundarytype,createddate,lastmodifieddate,createdby,lastmodifiedby,version,boundary)
select nextval('seq_egeis_jurisdiction'), (select id from egeis_employee where upper(code)='TPCORPENG2'),boundarytype ,current_date,
current_date,1,1,0, id from eg_boundary where boundarytype in ( select id from eg_boundary_type  where name='Ward' and hierarchytype=(select ID from eg_hierarchy_type where name='ADMINISTRATION')) AND name='Election Ward No 3' order by name ;
insert into egeis_jurisdiction (id,employee,boundarytype,createddate,lastmodifieddate,createdby,lastmodifiedby,version,boundary)
select nextval('seq_egeis_jurisdiction'), (select id from egeis_employee where upper(code)='TPCORPENG2'),boundarytype ,current_date,
current_date,1,1,0, id from eg_boundary where boundarytype in ( select id from eg_boundary_type  where name='Ward' and hierarchytype=(select ID from eg_hierarchy_type where name='ADMINISTRATION')) AND name='Election Ward No 4' order by name ;


insert into egeis_jurisdiction (id,employee,boundarytype,createddate,lastmodifieddate,createdby,lastmodifiedby,version,boundary)
values(nextval('seq_egeis_jurisdiction'),(select id from egeis_employee where upper(code)='TPCORPENG2'),(select id from eg_boundary_type where upper(name)=upper('City') 
and hierarchytype in(select id from eg_hierarchy_type where upper(name)='ADMINISTRATION')),current_date,current_date,1,1,0,(select id from eg_boundary where boundarytype =(select id from eg_boundary_type where upper(name)=upper('City') 
and hierarchytype in(select id from eg_hierarchy_type where upper(name)='ADMINISTRATION'))));


insert into eg_user(id,locale,username,password,pwdexpirydate,mobilenumber,createddate,lastmodifieddate,createdby,
lastmodifiedby,active,name,gender,type,version) values(nextval('seq_eg_user'),'en_IN','TPCORPENG3',
'$2a$10$uheIOutTnD33x7CDqac1zOL8DMiuz7mWplToPgcf7oxAI9OzRKxmK','01-Jan-2099','1234567891',
current_date,current_date,1,1,true,'TPCORPENG3',1,'EMPLOYEE',0);

insert into egeis_employee(id,code,employeestatus,employeetype,version) 
values((select id from eg_user where username='TPCORPENG3'),'TPCORPENG3','EMPLOYED',(select id from egeis_employeetype where name='Permanent'),0);


INSERT INTO egeis_assignment (id,fund,function,designation,functionary,department,position,grade,lastmodifiedby,lastmodifieddate,createddate,createdby,fromdate,todate,version,employee,isprimary) 
VALUES (nextval('seq_egeis_assignment'),null,null,(SELECT id FROM eg_designation WHERE name='Corporation Engineer'),null,(SELECT id FROM eg_department WHERE name='TP'),(SELECT id FROM eg_position 
WHERE name='TP_Corporation Engineer_3'),1,1,now(),now(),1,'01-Apr-2018','31-Mar-2020',1,(SELECT id FROM egeis_employee WHERE code='TPCORPENG3'),true);


insert into eg_userrole(roleid,userid) values((select id from eg_role where upper(name)='EMPLOYEE'),(select id from eg_user where username='TPCORPENG3'));

insert into egeis_jurisdiction (id,employee,boundarytype,createddate,lastmodifieddate,createdby,lastmodifiedby,version,boundary)
select nextval('seq_egeis_jurisdiction'), (select id from egeis_employee where upper(code)='TPCORPENG3'),boundarytype ,current_date,
current_date,1,1,0, id from eg_boundary where boundarytype in ( select id from eg_boundary_type  where name='Ward' and hierarchytype=(select ID from eg_hierarchy_type where name='ADMINISTRATION')) AND name='Election Ward No 5' order by name ;


insert into egeis_jurisdiction (id,employee,boundarytype,createddate,lastmodifieddate,createdby,lastmodifiedby,version,boundary)
values(nextval('seq_egeis_jurisdiction'),(select id from egeis_employee where upper(code)='TPCORPENG3'),(select id from eg_boundary_type where upper(name)=upper('City') 
and hierarchytype in(select id from eg_hierarchy_type where upper(name)='ADMINISTRATION')),current_date,current_date,1,1,0,(select id from eg_boundary where boundarytype =(select id from eg_boundary_type where upper(name)=upper('City') 
and hierarchytype in(select id from eg_hierarchy_type where upper(name)='ADMINISTRATION'))));


insert into eg_user(id,locale,username,password,pwdexpirydate,mobilenumber,createddate,lastmodifieddate,createdby,
lastmodifiedby,active,name,gender,type,version) values(nextval('seq_eg_user'),'en_IN','TPCORPENG4',
'$2a$10$uheIOutTnD33x7CDqac1zOL8DMiuz7mWplToPgcf7oxAI9OzRKxmK','01-Jan-2099','1234567891',
current_date,current_date,1,1,true,'TPCORPENG4',1,'EMPLOYEE',0);

insert into egeis_employee(id,code,employeestatus,employeetype,version) 
values((select id from eg_user where username='TPCORPENG4'),'TPCORPENG4','EMPLOYED',(select id from egeis_employeetype where name='Permanent'),0);


INSERT INTO egeis_assignment (id,fund,function,designation,functionary,department,position,grade,lastmodifiedby,lastmodifieddate,createddate,createdby,fromdate,todate,version,employee,isprimary) 
VALUES (nextval('seq_egeis_assignment'),null,null,(SELECT id FROM eg_designation WHERE name='Corporation Engineer'),null,(SELECT id FROM eg_department WHERE name='TP'),(SELECT id FROM eg_position 
WHERE name='TP_Corporation Engineer_4'),1,1,now(),now(),1,'01-Apr-2018','31-Mar-2020',1,(SELECT id FROM egeis_employee WHERE code='TPCORPENG4'),true);


insert into eg_userrole(roleid,userid) values((select id from eg_role where upper(name)='EMPLOYEE'),(select id from eg_user where username='TPCORPENG4'));

insert into egeis_jurisdiction (id,employee,boundarytype,createddate,lastmodifieddate,createdby,lastmodifiedby,version,boundary)
select nextval('seq_egeis_jurisdiction'), (select id from egeis_employee where upper(code)='TPCORPENG4'),boundarytype ,current_date,
current_date,1,1,0, id from eg_boundary where boundarytype in ( select id from eg_boundary_type  where name='Ward' and hierarchytype=(select ID from eg_hierarchy_type where name='ADMINISTRATION')) AND name='Election Ward No 6' order by name ;


insert into egeis_jurisdiction (id,employee,boundarytype,createddate,lastmodifieddate,createdby,lastmodifiedby,version,boundary)
values(nextval('seq_egeis_jurisdiction'),(select id from egeis_employee where upper(code)='TPCORPENG4'),(select id from eg_boundary_type where upper(name)=upper('City') 
and hierarchytype in(select id from eg_hierarchy_type where upper(name)='ADMINISTRATION')),current_date,current_date,1,1,0,(select id from eg_boundary where boundarytype =(select id from eg_boundary_type where upper(name)=upper('City') 
and hierarchytype in(select id from eg_hierarchy_type where upper(name)='ADMINISTRATION'))));



insert into eg_user(id,locale,username,password,pwdexpirydate,mobilenumber,createddate,lastmodifieddate,createdby,
lastmodifiedby,active,name,gender,type,version) values(nextval('seq_eg_user'),'en_IN','TPEXEENG1',
'$2a$10$uheIOutTnD33x7CDqac1zOL8DMiuz7mWplToPgcf7oxAI9OzRKxmK','01-Jan-2099','1234567891',
current_date,current_date,1,1,true,'TPEXEENG1',1,'EMPLOYEE',0);

insert into egeis_employee(id,code,employeestatus,employeetype,version) 
values((select id from eg_user where username='TPEXEENG1'),'TPEXEENG1','EMPLOYED',(select id from egeis_employeetype where name='Permanent'),0);


INSERT INTO egeis_assignment (id,fund,function,designation,functionary,department,position,grade,lastmodifiedby,lastmodifieddate,createddate,createdby,fromdate,todate,version,employee,isprimary) 
VALUES (nextval('seq_egeis_assignment'),null,null,(SELECT id FROM eg_designation WHERE name='Executive engineer'),null,(SELECT id FROM eg_department WHERE name='TP'),(SELECT id FROM eg_position 
WHERE name='TP_Executive engineer_1'),1,1,now(),now(),1,'01-Apr-2018','31-Mar-2020',1,(SELECT id FROM egeis_employee WHERE code='TPEXEENG1'),true);


insert into eg_userrole(roleid,userid) values((select id from eg_role where upper(name)='EMPLOYEE'),(select id from eg_user where username='TPEXEENG1'));

insert into egeis_jurisdiction (id,employee,boundarytype,createddate,lastmodifieddate,createdby,lastmodifiedby,version,boundary)
select nextval('seq_egeis_jurisdiction'), (select id from egeis_employee where upper(code)='TPEXEENG1'),boundarytype ,current_date,
current_date,1,1,0, id from eg_boundary where boundarytype in ( select id from eg_boundary_type  where name='Ward' and hierarchytype=(select ID from eg_hierarchy_type where name='ADMINISTRATION')) AND name='Election Ward No 1' order by name ;
insert into egeis_jurisdiction (id,employee,boundarytype,createddate,lastmodifieddate,createdby,lastmodifiedby,version,boundary)
select nextval('seq_egeis_jurisdiction'), (select id from egeis_employee where upper(code)='TPEXEENG1'),boundarytype ,current_date,
current_date,1,1,0, id from eg_boundary where boundarytype in ( select id from eg_boundary_type  where name='Ward' and hierarchytype=(select ID from eg_hierarchy_type where name='ADMINISTRATION')) AND name='Election Ward No 2' order by name ;


insert into egeis_jurisdiction (id,employee,boundarytype,createddate,lastmodifieddate,createdby,lastmodifiedby,version,boundary)
values(nextval('seq_egeis_jurisdiction'),(select id from egeis_employee where upper(code)='TPEXEENG1'),(select id from eg_boundary_type where upper(name)=upper('City') 
and hierarchytype in(select id from eg_hierarchy_type where upper(name)='ADMINISTRATION')),current_date,current_date,1,1,0,(select id from eg_boundary where boundarytype =(select id from eg_boundary_type where upper(name)=upper('City') 
and hierarchytype in(select id from eg_hierarchy_type where upper(name)='ADMINISTRATION'))));



insert into eg_user(id,locale,username,password,pwdexpirydate,mobilenumber,createddate,lastmodifieddate,createdby,
lastmodifiedby,active,name,gender,type,version) values(nextval('seq_eg_user'),'en_IN','TPEXEENG2',
'$2a$10$uheIOutTnD33x7CDqac1zOL8DMiuz7mWplToPgcf7oxAI9OzRKxmK','01-Jan-2099','1234567891',
current_date,current_date,1,1,true,'TPEXEENG2',1,'EMPLOYEE',0);

insert into egeis_employee(id,code,employeestatus,employeetype,version) 
values((select id from eg_user where username='TPEXEENG2'),'TPEXEENG2','EMPLOYED',(select id from egeis_employeetype where name='Permanent'),0);


INSERT INTO egeis_assignment (id,fund,function,designation,functionary,department,position,grade,lastmodifiedby,lastmodifieddate,createddate,createdby,fromdate,todate,version,employee,isprimary) 
VALUES (nextval('seq_egeis_assignment'),null,null,(SELECT id FROM eg_designation WHERE name='Executive engineer'),null,(SELECT id FROM eg_department WHERE name='TP'),(SELECT id FROM eg_position 
WHERE name='TP_Executive engineer_2'),1,1,now(),now(),1,'01-Apr-2018','31-Mar-2020',1,(SELECT id FROM egeis_employee WHERE code='TPEXEENG2'),true);


insert into eg_userrole(roleid,userid) values((select id from eg_role where upper(name)='EMPLOYEE'),(select id from eg_user where username='TPEXEENG2'));

insert into egeis_jurisdiction (id,employee,boundarytype,createddate,lastmodifieddate,createdby,lastmodifiedby,version,boundary)
select nextval('seq_egeis_jurisdiction'), (select id from egeis_employee where upper(code)='TPEXEENG2'),boundarytype ,current_date,
current_date,1,1,0, id from eg_boundary where boundarytype in ( select id from eg_boundary_type  where name='Ward' and hierarchytype=(select ID from eg_hierarchy_type where name='ADMINISTRATION')) AND name='Election Ward No 3' order by name ;
insert into egeis_jurisdiction (id,employee,boundarytype,createddate,lastmodifieddate,createdby,lastmodifiedby,version,boundary)
select nextval('seq_egeis_jurisdiction'), (select id from egeis_employee where upper(code)='TPEXEENG2'),boundarytype ,current_date,
current_date,1,1,0, id from eg_boundary where boundarytype in ( select id from eg_boundary_type  where name='Ward' and hierarchytype=(select ID from eg_hierarchy_type where name='ADMINISTRATION')) AND name='Election Ward No 4' order by name ;


insert into egeis_jurisdiction (id,employee,boundarytype,createddate,lastmodifieddate,createdby,lastmodifiedby,version,boundary)
values(nextval('seq_egeis_jurisdiction'),(select id from egeis_employee where upper(code)='TPEXEENG2'),(select id from eg_boundary_type where upper(name)=upper('City') 
and hierarchytype in(select id from eg_hierarchy_type where upper(name)='ADMINISTRATION')),current_date,current_date,1,1,0,(select id from eg_boundary where boundarytype =(select id from eg_boundary_type where upper(name)=upper('City') 
and hierarchytype in(select id from eg_hierarchy_type where upper(name)='ADMINISTRATION'))));


insert into eg_user(id,locale,username,password,pwdexpirydate,mobilenumber,createddate,lastmodifieddate,createdby,
lastmodifiedby,active,name,gender,type,version) values(nextval('seq_eg_user'),'en_IN','TPEXEENG3',
'$2a$10$uheIOutTnD33x7CDqac1zOL8DMiuz7mWplToPgcf7oxAI9OzRKxmK','01-Jan-2099','1234567891',
current_date,current_date,1,1,true,'TPEXEENG3',1,'EMPLOYEE',0);

insert into egeis_employee(id,code,employeestatus,employeetype,version) 
values((select id from eg_user where username='TPEXEENG3'),'TPEXEENG3','EMPLOYED',(select id from egeis_employeetype where name='Permanent'),0);


INSERT INTO egeis_assignment (id,fund,function,designation,functionary,department,position,grade,lastmodifiedby,lastmodifieddate,createddate,createdby,fromdate,todate,version,employee,isprimary) 
VALUES (nextval('seq_egeis_assignment'),null,null,(SELECT id FROM eg_designation WHERE name='Executive engineer'),null,(SELECT id FROM eg_department WHERE name='TP'),(SELECT id FROM eg_position 
WHERE name='TP_Executive engineer_3'),1,1,now(),now(),1,'01-Apr-2018','31-Mar-2020',1,(SELECT id FROM egeis_employee WHERE code='TPEXEENG3'),true);


insert into eg_userrole(roleid,userid) values((select id from eg_role where upper(name)='EMPLOYEE'),(select id from eg_user where username='TPEXEENG3'));

insert into egeis_jurisdiction (id,employee,boundarytype,createddate,lastmodifieddate,createdby,lastmodifiedby,version,boundary)
select nextval('seq_egeis_jurisdiction'), (select id from egeis_employee where upper(code)='TPEXEENG3'),boundarytype ,current_date,
current_date,1,1,0, id from eg_boundary where boundarytype in ( select id from eg_boundary_type  where name='Ward' and hierarchytype=(select ID from eg_hierarchy_type where name='ADMINISTRATION')) AND name='Election Ward No 5' order by name ;


insert into egeis_jurisdiction (id,employee,boundarytype,createddate,lastmodifieddate,createdby,lastmodifiedby,version,boundary)
values(nextval('seq_egeis_jurisdiction'),(select id from egeis_employee where upper(code)='TPEXEENG3'),(select id from eg_boundary_type where upper(name)=upper('City') 
and hierarchytype in(select id from eg_hierarchy_type where upper(name)='ADMINISTRATION')),current_date,current_date,1,1,0,(select id from eg_boundary where boundarytype =(select id from eg_boundary_type where upper(name)=upper('City') 
and hierarchytype in(select id from eg_hierarchy_type where upper(name)='ADMINISTRATION'))));


insert into eg_user(id,locale,username,password,pwdexpirydate,mobilenumber,createddate,lastmodifieddate,createdby,
lastmodifiedby,active,name,gender,type,version) values(nextval('seq_eg_user'),'en_IN','TPEXEENG4',
'$2a$10$uheIOutTnD33x7CDqac1zOL8DMiuz7mWplToPgcf7oxAI9OzRKxmK','01-Jan-2099','1234567891',
current_date,current_date,1,1,true,'TPEXEENG4',1,'EMPLOYEE',0);

insert into egeis_employee(id,code,employeestatus,employeetype,version) 
values((select id from eg_user where username='TPEXEENG4'),'TPEXEENG4','EMPLOYED',(select id from egeis_employeetype where name='Permanent'),0);


INSERT INTO egeis_assignment (id,fund,function,designation,functionary,department,position,grade,lastmodifiedby,lastmodifieddate,createddate,createdby,fromdate,todate,version,employee,isprimary) 
VALUES (nextval('seq_egeis_assignment'),null,null,(SELECT id FROM eg_designation WHERE name='Executive engineer'),null,(SELECT id FROM eg_department WHERE name='TP'),(SELECT id FROM eg_position 
WHERE name='TP_Executive engineer_4'),1,1,now(),now(),1,'01-Apr-2018','31-Mar-2020',1,(SELECT id FROM egeis_employee WHERE code='TPEXEENG4'),true);


insert into eg_userrole(roleid,userid) values((select id from eg_role where upper(name)='EMPLOYEE'),(select id from eg_user where username='TPEXEENG4'));

insert into egeis_jurisdiction (id,employee,boundarytype,createddate,lastmodifieddate,createdby,lastmodifiedby,version,boundary)
select nextval('seq_egeis_jurisdiction'), (select id from egeis_employee where upper(code)='TPEXEENG4'),boundarytype ,current_date,
current_date,1,1,0, id from eg_boundary where boundarytype in ( select id from eg_boundary_type  where name='Ward' and hierarchytype=(select ID from eg_hierarchy_type where name='ADMINISTRATION')) AND name='Election Ward No 6' order by name ;


insert into egeis_jurisdiction (id,employee,boundarytype,createddate,lastmodifieddate,createdby,lastmodifiedby,version,boundary)
values(nextval('seq_egeis_jurisdiction'),(select id from egeis_employee where upper(code)='TPEXEENG4'),(select id from eg_boundary_type where upper(name)=upper('City') 
and hierarchytype in(select id from eg_hierarchy_type where upper(name)='ADMINISTRATION')),current_date,current_date,1,1,0,(select id from eg_boundary where boundarytype =(select id from eg_boundary_type where upper(name)=upper('City') 
and hierarchytype in(select id from eg_hierarchy_type where upper(name)='ADMINISTRATION'))));




insert into eg_user(id,locale,username,password,pwdexpirydate,mobilenumber,createddate,lastmodifieddate,createdby,
lastmodifiedby,active,name,gender,type,version) values(nextval('seq_eg_user'),'en_IN','TPASSTENG1',
'$2a$10$uheIOutTnD33x7CDqac1zOL8DMiuz7mWplToPgcf7oxAI9OzRKxmK','01-Jan-2099','1234567891',
current_date,current_date,1,1,true,'TPASSTENG1',1,'EMPLOYEE',0);

insert into egeis_employee(id,code,employeestatus,employeetype,version) 
values((select id from eg_user where username='TPASSTENG1'),'TPASSTENG1','EMPLOYED',(select id from egeis_employeetype where name='Permanent'),0);


INSERT INTO egeis_assignment (id,fund,function,designation,functionary,department,position,grade,lastmodifiedby,lastmodifieddate,createddate,createdby,fromdate,todate,version,employee,isprimary) 
VALUES (nextval('seq_egeis_assignment'),null,null,(SELECT id FROM eg_designation WHERE name='Assistant engineer'),null,(SELECT id FROM eg_department WHERE name='TP'),(SELECT id FROM eg_position 
WHERE name='TP_Assistant engineer_1'),1,1,now(),now(),1,'01-Apr-2018','31-Mar-2020',1,(SELECT id FROM egeis_employee WHERE code='TPASSTENG1'),true);


insert into eg_userrole(roleid,userid) values((select id from eg_role where upper(name)='EMPLOYEE'),(select id from eg_user where username='TPASSTENG1'));

insert into egeis_jurisdiction (id,employee,boundarytype,createddate,lastmodifieddate,createdby,lastmodifiedby,version,boundary)
select nextval('seq_egeis_jurisdiction'), (select id from egeis_employee where upper(code)='TPASSTENG1'),boundarytype ,current_date,
current_date,1,1,0, id from eg_boundary where boundarytype in ( select id from eg_boundary_type  where name='Ward' and hierarchytype=(select ID from eg_hierarchy_type where name='ADMINISTRATION')) AND name='Election Ward No 1' order by name ;
insert into egeis_jurisdiction (id,employee,boundarytype,createddate,lastmodifieddate,createdby,lastmodifiedby,version,boundary)
select nextval('seq_egeis_jurisdiction'), (select id from egeis_employee where upper(code)='TPASSTENG1'),boundarytype ,current_date,
current_date,1,1,0, id from eg_boundary where boundarytype in ( select id from eg_boundary_type  where name='Ward' and hierarchytype=(select ID from eg_hierarchy_type where name='ADMINISTRATION')) AND name='Election Ward No 2' order by name ;


insert into egeis_jurisdiction (id,employee,boundarytype,createddate,lastmodifieddate,createdby,lastmodifiedby,version,boundary)
values(nextval('seq_egeis_jurisdiction'),(select id from egeis_employee where upper(code)='TPASSTENG1'),(select id from eg_boundary_type where upper(name)=upper('City') 
and hierarchytype in(select id from eg_hierarchy_type where upper(name)='ADMINISTRATION')),current_date,current_date,1,1,0,(select id from eg_boundary where boundarytype =(select id from eg_boundary_type where upper(name)=upper('City') 
and hierarchytype in(select id from eg_hierarchy_type where upper(name)='ADMINISTRATION'))));



insert into eg_user(id,locale,username,password,pwdexpirydate,mobilenumber,createddate,lastmodifieddate,createdby,
lastmodifiedby,active,name,gender,type,version) values(nextval('seq_eg_user'),'en_IN','TPASSTENG2',
'$2a$10$uheIOutTnD33x7CDqac1zOL8DMiuz7mWplToPgcf7oxAI9OzRKxmK','01-Jan-2099','1234567891',
current_date,current_date,1,1,true,'TPASSTENG2',1,'EMPLOYEE',0);

insert into egeis_employee(id,code,employeestatus,employeetype,version) 
values((select id from eg_user where username='TPASSTENG2'),'TPASSTENG2','EMPLOYED',(select id from egeis_employeetype where name='Permanent'),0);


INSERT INTO egeis_assignment (id,fund,function,designation,functionary,department,position,grade,lastmodifiedby,lastmodifieddate,createddate,createdby,fromdate,todate,version,employee,isprimary) 
VALUES (nextval('seq_egeis_assignment'),null,null,(SELECT id FROM eg_designation WHERE name='Assistant engineer'),null,(SELECT id FROM eg_department WHERE name='TP'),(SELECT id FROM eg_position 
WHERE name='TP_Assistant engineer_2'),1,1,now(),now(),1,'01-Apr-2018','31-Mar-2020',1,(SELECT id FROM egeis_employee WHERE code='TPASSTENG2'),true);


insert into eg_userrole(roleid,userid) values((select id from eg_role where upper(name)='EMPLOYEE'),(select id from eg_user where username='TPASSTENG2'));

insert into egeis_jurisdiction (id,employee,boundarytype,createddate,lastmodifieddate,createdby,lastmodifiedby,version,boundary)
select nextval('seq_egeis_jurisdiction'), (select id from egeis_employee where upper(code)='TPASSTENG2'),boundarytype ,current_date,
current_date,1,1,0, id from eg_boundary where boundarytype in ( select id from eg_boundary_type  where name='Ward' and hierarchytype=(select ID from eg_hierarchy_type where name='ADMINISTRATION')) AND name='Election Ward No 3' order by name ;
insert into egeis_jurisdiction (id,employee,boundarytype,createddate,lastmodifieddate,createdby,lastmodifiedby,version,boundary)
select nextval('seq_egeis_jurisdiction'), (select id from egeis_employee where upper(code)='TPASSTENG2'),boundarytype ,current_date,
current_date,1,1,0, id from eg_boundary where boundarytype in ( select id from eg_boundary_type  where name='Ward' and hierarchytype=(select ID from eg_hierarchy_type where name='ADMINISTRATION')) AND name='Election Ward No 4' order by name ;


insert into egeis_jurisdiction (id,employee,boundarytype,createddate,lastmodifieddate,createdby,lastmodifiedby,version,boundary)
values(nextval('seq_egeis_jurisdiction'),(select id from egeis_employee where upper(code)='TPASSTENG2'),(select id from eg_boundary_type where upper(name)=upper('City') 
and hierarchytype in(select id from eg_hierarchy_type where upper(name)='ADMINISTRATION')),current_date,current_date,1,1,0,(select id from eg_boundary where boundarytype =(select id from eg_boundary_type where upper(name)=upper('City') 
and hierarchytype in(select id from eg_hierarchy_type where upper(name)='ADMINISTRATION'))));


insert into eg_user(id,locale,username,password,pwdexpirydate,mobilenumber,createddate,lastmodifieddate,createdby,
lastmodifiedby,active,name,gender,type,version) values(nextval('seq_eg_user'),'en_IN','TPASSTENG3',
'$2a$10$uheIOutTnD33x7CDqac1zOL8DMiuz7mWplToPgcf7oxAI9OzRKxmK','01-Jan-2099','1234567891',
current_date,current_date,1,1,true,'TPASSTENG3',1,'EMPLOYEE',0);

insert into egeis_employee(id,code,employeestatus,employeetype,version) 
values((select id from eg_user where username='TPASSTENG3'),'TPASSTENG3','EMPLOYED',(select id from egeis_employeetype where name='Permanent'),0);


INSERT INTO egeis_assignment (id,fund,function,designation,functionary,department,position,grade,lastmodifiedby,lastmodifieddate,createddate,createdby,fromdate,todate,version,employee,isprimary) 
VALUES (nextval('seq_egeis_assignment'),null,null,(SELECT id FROM eg_designation WHERE name='Assistant engineer'),null,(SELECT id FROM eg_department WHERE name='TP'),(SELECT id FROM eg_position 
WHERE name='TP_Assistant engineer_3'),1,1,now(),now(),1,'01-Apr-2018','31-Mar-2020',1,(SELECT id FROM egeis_employee WHERE code='TPASSTENG3'),true);


insert into eg_userrole(roleid,userid) values((select id from eg_role where upper(name)='EMPLOYEE'),(select id from eg_user where username='TPASSTENG3'));

insert into egeis_jurisdiction (id,employee,boundarytype,createddate,lastmodifieddate,createdby,lastmodifiedby,version,boundary)
select nextval('seq_egeis_jurisdiction'), (select id from egeis_employee where upper(code)='TPASSTENG3'),boundarytype ,current_date,
current_date,1,1,0, id from eg_boundary where boundarytype in ( select id from eg_boundary_type  where name='Ward' and hierarchytype=(select ID from eg_hierarchy_type where name='ADMINISTRATION')) AND name='Election Ward No 5' order by name ;


insert into egeis_jurisdiction (id,employee,boundarytype,createddate,lastmodifieddate,createdby,lastmodifiedby,version,boundary)
values(nextval('seq_egeis_jurisdiction'),(select id from egeis_employee where upper(code)='TPASSTENG3'),(select id from eg_boundary_type where upper(name)=upper('City') 
and hierarchytype in(select id from eg_hierarchy_type where upper(name)='ADMINISTRATION')),current_date,current_date,1,1,0,(select id from eg_boundary where boundarytype =(select id from eg_boundary_type where upper(name)=upper('City') 
and hierarchytype in(select id from eg_hierarchy_type where upper(name)='ADMINISTRATION'))));


insert into eg_user(id,locale,username,password,pwdexpirydate,mobilenumber,createddate,lastmodifieddate,createdby,
lastmodifiedby,active,name,gender,type,version) values(nextval('seq_eg_user'),'en_IN','TPASSTENG4',
'$2a$10$uheIOutTnD33x7CDqac1zOL8DMiuz7mWplToPgcf7oxAI9OzRKxmK','01-Jan-2099','1234567891',
current_date,current_date,1,1,true,'TPASSTENG4',1,'EMPLOYEE',0);

insert into egeis_employee(id,code,employeestatus,employeetype,version) 
values((select id from eg_user where username='TPASSTENG4'),'TPASSTENG4','EMPLOYED',(select id from egeis_employeetype where name='Permanent'),0);


INSERT INTO egeis_assignment (id,fund,function,designation,functionary,department,position,grade,lastmodifiedby,lastmodifieddate,createddate,createdby,fromdate,todate,version,employee,isprimary) 
VALUES (nextval('seq_egeis_assignment'),null,null,(SELECT id FROM eg_designation WHERE name='Assistant engineer'),null,(SELECT id FROM eg_department WHERE name='TP'),(SELECT id FROM eg_position 
WHERE name='TP_Assistant engineer_4'),1,1,now(),now(),1,'01-Apr-2018','31-Mar-2020',1,(SELECT id FROM egeis_employee WHERE code='TPASSTENG4'),true);


insert into eg_userrole(roleid,userid) values((select id from eg_role where upper(name)='EMPLOYEE'),(select id from eg_user where username='TPASSTENG4'));

insert into egeis_jurisdiction (id,employee,boundarytype,createddate,lastmodifieddate,createdby,lastmodifiedby,version,boundary)
select nextval('seq_egeis_jurisdiction'), (select id from egeis_employee where upper(code)='TPASSTENG4'),boundarytype ,current_date,
current_date,1,1,0, id from eg_boundary where boundarytype in ( select id from eg_boundary_type  where name='Ward' and hierarchytype=(select ID from eg_hierarchy_type where name='ADMINISTRATION')) AND name='Election Ward No 6' order by name ;


insert into egeis_jurisdiction (id,employee,boundarytype,createddate,lastmodifieddate,createdby,lastmodifiedby,version,boundary)
values(nextval('seq_egeis_jurisdiction'),(select id from egeis_employee where upper(code)='TPASSTENG4'),(select id from eg_boundary_type where upper(name)=upper('City') 
and hierarchytype in(select id from eg_hierarchy_type where upper(name)='ADMINISTRATION')),current_date,current_date,1,1,0,(select id from eg_boundary where boundarytype =(select id from eg_boundary_type where upper(name)=upper('City') 
and hierarchytype in(select id from eg_hierarchy_type where upper(name)='ADMINISTRATION'))));



insert into eg_user(id,locale,username,password,pwdexpirydate,mobilenumber,createddate,lastmodifieddate,createdby,
lastmodifiedby,active,name,gender,type,version) values(nextval('seq_eg_user'),'en_IN','TPASSTEXEENG1',
'$2a$10$uheIOutTnD33x7CDqac1zOL8DMiuz7mWplToPgcf7oxAI9OzRKxmK','01-Jan-2099','1234567891',
current_date,current_date,1,1,true,'TPASSTEXEENG1',1,'EMPLOYEE',0);

insert into egeis_employee(id,code,employeestatus,employeetype,version) 
values((select id from eg_user where username='TPASSTEXEENG1'),'TPASSTEXEENG1','EMPLOYED',(select id from egeis_employeetype where name='Permanent'),0);


INSERT INTO egeis_assignment (id,fund,function,designation,functionary,department,position,grade,lastmodifiedby,lastmodifieddate,createddate,createdby,fromdate,todate,version,employee,isprimary) 
VALUES (nextval('seq_egeis_assignment'),null,null,(SELECT id FROM eg_designation WHERE name='Assistant executive engineer'),null,(SELECT id FROM eg_department WHERE name='TP'),(SELECT id FROM eg_position 
WHERE name='TP_Assistant executive engineer_1'),1,1,now(),now(),1,'01-Apr-2018','31-Mar-2020',1,(SELECT id FROM egeis_employee WHERE code='TPASSTEXEENG1'),true);


insert into eg_userrole(roleid,userid) values((select id from eg_role where upper(name)='EMPLOYEE'),(select id from eg_user where username='TPASSTEXEENG1'));

insert into egeis_jurisdiction (id,employee,boundarytype,createddate,lastmodifieddate,createdby,lastmodifiedby,version,boundary)
select nextval('seq_egeis_jurisdiction'), (select id from egeis_employee where upper(code)='TPASSTEXEENG1'),boundarytype ,current_date,
current_date,1,1,0, id from eg_boundary where boundarytype in ( select id from eg_boundary_type  where name='Ward' and hierarchytype=(select ID from eg_hierarchy_type where name='ADMINISTRATION')) AND name='Election Ward No 1' order by name ;
insert into egeis_jurisdiction (id,employee,boundarytype,createddate,lastmodifieddate,createdby,lastmodifiedby,version,boundary)
select nextval('seq_egeis_jurisdiction'), (select id from egeis_employee where upper(code)='TPASSTEXEENG1'),boundarytype ,current_date,
current_date,1,1,0, id from eg_boundary where boundarytype in ( select id from eg_boundary_type  where name='Ward' and hierarchytype=(select ID from eg_hierarchy_type where name='ADMINISTRATION')) AND name='Election Ward No 2' order by name ;


insert into egeis_jurisdiction (id,employee,boundarytype,createddate,lastmodifieddate,createdby,lastmodifiedby,version,boundary)
values(nextval('seq_egeis_jurisdiction'),(select id from egeis_employee where upper(code)='TPASSTEXEENG1'),(select id from eg_boundary_type where upper(name)=upper('City') 
and hierarchytype in(select id from eg_hierarchy_type where upper(name)='ADMINISTRATION')),current_date,current_date,1,1,0,(select id from eg_boundary where boundarytype =(select id from eg_boundary_type where upper(name)=upper('City') 
and hierarchytype in(select id from eg_hierarchy_type where upper(name)='ADMINISTRATION'))));



insert into eg_user(id,locale,username,password,pwdexpirydate,mobilenumber,createddate,lastmodifieddate,createdby,
lastmodifiedby,active,name,gender,type,version) values(nextval('seq_eg_user'),'en_IN','TPASSTEXEENG2',
'$2a$10$uheIOutTnD33x7CDqac1zOL8DMiuz7mWplToPgcf7oxAI9OzRKxmK','01-Jan-2099','1234567891',
current_date,current_date,1,1,true,'TPASSTEXEENG2',1,'EMPLOYEE',0);

insert into egeis_employee(id,code,employeestatus,employeetype,version) 
values((select id from eg_user where username='TPASSTEXEENG2'),'TPASSTEXEENG2','EMPLOYED',(select id from egeis_employeetype where name='Permanent'),0);


INSERT INTO egeis_assignment (id,fund,function,designation,functionary,department,position,grade,lastmodifiedby,lastmodifieddate,createddate,createdby,fromdate,todate,version,employee,isprimary) 
VALUES (nextval('seq_egeis_assignment'),null,null,(SELECT id FROM eg_designation WHERE name='Assistant executive engineer'),null,(SELECT id FROM eg_department WHERE name='TP'),(SELECT id FROM eg_position 
WHERE name='TP_Assistant executive engineer_2'),1,1,now(),now(),1,'01-Apr-2018','31-Mar-2020',1,(SELECT id FROM egeis_employee WHERE code='TPASSTEXEENG2'),true);


insert into eg_userrole(roleid,userid) values((select id from eg_role where upper(name)='EMPLOYEE'),(select id from eg_user where username='TPASSTEXEENG2'));

insert into egeis_jurisdiction (id,employee,boundarytype,createddate,lastmodifieddate,createdby,lastmodifiedby,version,boundary)
select nextval('seq_egeis_jurisdiction'), (select id from egeis_employee where upper(code)='TPASSTEXEENG2'),boundarytype ,current_date,
current_date,1,1,0, id from eg_boundary where boundarytype in ( select id from eg_boundary_type  where name='Ward' and hierarchytype=(select ID from eg_hierarchy_type where name='ADMINISTRATION')) AND name='Election Ward No 3' order by name ;
insert into egeis_jurisdiction (id,employee,boundarytype,createddate,lastmodifieddate,createdby,lastmodifiedby,version,boundary)
select nextval('seq_egeis_jurisdiction'), (select id from egeis_employee where upper(code)='TPASSTEXEENG2'),boundarytype ,current_date,
current_date,1,1,0, id from eg_boundary where boundarytype in ( select id from eg_boundary_type  where name='Ward' and hierarchytype=(select ID from eg_hierarchy_type where name='ADMINISTRATION')) AND name='Election Ward No 4' order by name ;


insert into egeis_jurisdiction (id,employee,boundarytype,createddate,lastmodifieddate,createdby,lastmodifiedby,version,boundary)
values(nextval('seq_egeis_jurisdiction'),(select id from egeis_employee where upper(code)='TPASSTEXEENG2'),(select id from eg_boundary_type where upper(name)=upper('City') 
and hierarchytype in(select id from eg_hierarchy_type where upper(name)='ADMINISTRATION')),current_date,current_date,1,1,0,(select id from eg_boundary where boundarytype =(select id from eg_boundary_type where upper(name)=upper('City') 
and hierarchytype in(select id from eg_hierarchy_type where upper(name)='ADMINISTRATION'))));


insert into eg_user(id,locale,username,password,pwdexpirydate,mobilenumber,createddate,lastmodifieddate,createdby,
lastmodifiedby,active,name,gender,type,version) values(nextval('seq_eg_user'),'en_IN','TPASSTEXEENG3',
'$2a$10$uheIOutTnD33x7CDqac1zOL8DMiuz7mWplToPgcf7oxAI9OzRKxmK','01-Jan-2099','1234567891',
current_date,current_date,1,1,true,'TPASSTEXEENG3',1,'EMPLOYEE',0);

insert into egeis_employee(id,code,employeestatus,employeetype,version) 
values((select id from eg_user where username='TPASSTEXEENG3'),'TPASSTEXEENG3','EMPLOYED',(select id from egeis_employeetype where name='Permanent'),0);


INSERT INTO egeis_assignment (id,fund,function,designation,functionary,department,position,grade,lastmodifiedby,lastmodifieddate,createddate,createdby,fromdate,todate,version,employee,isprimary) 
VALUES (nextval('seq_egeis_assignment'),null,null,(SELECT id FROM eg_designation WHERE name='Assistant executive engineer'),null,(SELECT id FROM eg_department WHERE name='TP'),(SELECT id FROM eg_position 
WHERE name='TP_Assistant executive engineer_3'),1,1,now(),now(),1,'01-Apr-2018','31-Mar-2020',1,(SELECT id FROM egeis_employee WHERE code='TPASSTEXEENG3'),true);


insert into eg_userrole(roleid,userid) values((select id from eg_role where upper(name)='EMPLOYEE'),(select id from eg_user where username='TPASSTEXEENG3'));

insert into egeis_jurisdiction (id,employee,boundarytype,createddate,lastmodifieddate,createdby,lastmodifiedby,version,boundary)
select nextval('seq_egeis_jurisdiction'), (select id from egeis_employee where upper(code)='TPASSTEXEENG3'),boundarytype ,current_date,
current_date,1,1,0, id from eg_boundary where boundarytype in ( select id from eg_boundary_type  where name='Ward' and hierarchytype=(select ID from eg_hierarchy_type where name='ADMINISTRATION')) AND name='Election Ward No 5' order by name ;


insert into egeis_jurisdiction (id,employee,boundarytype,createddate,lastmodifieddate,createdby,lastmodifiedby,version,boundary)
values(nextval('seq_egeis_jurisdiction'),(select id from egeis_employee where upper(code)='TPASSTEXEENG3'),(select id from eg_boundary_type where upper(name)=upper('City') 
and hierarchytype in(select id from eg_hierarchy_type where upper(name)='ADMINISTRATION')),current_date,current_date,1,1,0,(select id from eg_boundary where boundarytype =(select id from eg_boundary_type where upper(name)=upper('City') 
and hierarchytype in(select id from eg_hierarchy_type where upper(name)='ADMINISTRATION'))));


insert into eg_user(id,locale,username,password,pwdexpirydate,mobilenumber,createddate,lastmodifieddate,createdby,
lastmodifiedby,active,name,gender,type,version) values(nextval('seq_eg_user'),'en_IN','TPASSTEXEENG4',
'$2a$10$uheIOutTnD33x7CDqac1zOL8DMiuz7mWplToPgcf7oxAI9OzRKxmK','01-Jan-2099','1234567891',
current_date,current_date,1,1,true,'TPASSTEXEENG4',1,'EMPLOYEE',0);

insert into egeis_employee(id,code,employeestatus,employeetype,version) 
values((select id from eg_user where username='TPASSTEXEENG4'),'TPASSTEXEENG4','EMPLOYED',(select id from egeis_employeetype where name='Permanent'),0);


INSERT INTO egeis_assignment (id,fund,function,designation,functionary,department,position,grade,lastmodifiedby,lastmodifieddate,createddate,createdby,fromdate,todate,version,employee,isprimary) 
VALUES (nextval('seq_egeis_assignment'),null,null,(SELECT id FROM eg_designation WHERE name='Assistant executive engineer'),null,(SELECT id FROM eg_department WHERE name='TP'),(SELECT id FROM eg_position 
WHERE name='TP_Assistant executive engineer_4'),1,1,now(),now(),1,'01-Apr-2018','31-Mar-2020',1,(SELECT id FROM egeis_employee WHERE code='TPASSTEXEENG4'),true);


insert into eg_userrole(roleid,userid) values((select id from eg_role where upper(name)='EMPLOYEE'),(select id from eg_user where username='TPASSTEXEENG4'));

insert into egeis_jurisdiction (id,employee,boundarytype,createddate,lastmodifieddate,createdby,lastmodifiedby,version,boundary)
select nextval('seq_egeis_jurisdiction'), (select id from egeis_employee where upper(code)='TPASSTEXEENG4'),boundarytype ,current_date,
current_date,1,1,0, id from eg_boundary where boundarytype in ( select id from eg_boundary_type  where name='Ward' and hierarchytype=(select ID from eg_hierarchy_type where name='ADMINISTRATION')) AND name='Election Ward No 6' order by name ;


insert into egeis_jurisdiction (id,employee,boundarytype,createddate,lastmodifieddate,createdby,lastmodifiedby,version,boundary)
values(nextval('seq_egeis_jurisdiction'),(select id from egeis_employee where upper(code)='TPASSTEXEENG4'),(select id from eg_boundary_type where upper(name)=upper('City') 
and hierarchytype in(select id from eg_hierarchy_type where upper(name)='ADMINISTRATION')),current_date,current_date,1,1,0,(select id from eg_boundary where boundarytype =(select id from eg_boundary_type where upper(name)=upper('City') 
and hierarchytype in(select id from eg_hierarchy_type where upper(name)='ADMINISTRATION'))));


insert into eg_user(id,locale,username,password,pwdexpirydate,mobilenumber,createddate,lastmodifieddate,createdby,
lastmodifiedby,active,name,gender,type,version) values(nextval('seq_eg_user'),'en_IN','TPSUPENG1',
'$2a$10$uheIOutTnD33x7CDqac1zOL8DMiuz7mWplToPgcf7oxAI9OzRKxmK','01-Jan-2099','1234567891',
current_date,current_date,1,1,true,'TPSUPENG1',1,'EMPLOYEE',0);

insert into egeis_employee(id,code,employeestatus,employeetype,version) 
values((select id from eg_user where username='TPSUPENG1'),'TPSUPENG1','EMPLOYED',(select id from egeis_employeetype where name='Permanent'),0);


INSERT INTO egeis_assignment (id,fund,function,designation,functionary,department,position,grade,lastmodifiedby,lastmodifieddate,createddate,createdby,fromdate,todate,version,employee,isprimary) 
VALUES (nextval('seq_egeis_assignment'),null,null,(SELECT id FROM eg_designation WHERE name='Superintendent'),null,(SELECT id FROM eg_department WHERE name='TP'),(SELECT id FROM eg_position 
WHERE name='TP_Superintendent_1'),1,1,now(),now(),1,'01-Apr-2018','31-Mar-2020',1,(SELECT id FROM egeis_employee WHERE code='TPSUPENG1'),true);


insert into eg_userrole(roleid,userid) values((select id from eg_role where upper(name)='EMPLOYEE'),(select id from eg_user where username='TPSUPENG1'));

insert into egeis_jurisdiction (id,employee,boundarytype,createddate,lastmodifieddate,createdby,lastmodifiedby,version,boundary)
select nextval('seq_egeis_jurisdiction'), (select id from egeis_employee where upper(code)='TPSUPENG1'),boundarytype ,current_date,
current_date,1,1,0, id from eg_boundary where boundarytype in ( select id from eg_boundary_type  where name='Ward' and hierarchytype=(select ID from eg_hierarchy_type where name='ADMINISTRATION')) AND name='Election Ward No 1' order by name ;
insert into egeis_jurisdiction (id,employee,boundarytype,createddate,lastmodifieddate,createdby,lastmodifiedby,version,boundary)
select nextval('seq_egeis_jurisdiction'), (select id from egeis_employee where upper(code)='TPSUPENG1'),boundarytype ,current_date,
current_date,1,1,0, id from eg_boundary where boundarytype in ( select id from eg_boundary_type  where name='Ward' and hierarchytype=(select ID from eg_hierarchy_type where name='ADMINISTRATION')) AND name='Election Ward No 2' order by name ;


insert into egeis_jurisdiction (id,employee,boundarytype,createddate,lastmodifieddate,createdby,lastmodifiedby,version,boundary)
values(nextval('seq_egeis_jurisdiction'),(select id from egeis_employee where upper(code)='TPSUPENG1'),(select id from eg_boundary_type where upper(name)=upper('City') 
and hierarchytype in(select id from eg_hierarchy_type where upper(name)='ADMINISTRATION')),current_date,current_date,1,1,0,(select id from eg_boundary where boundarytype =(select id from eg_boundary_type where upper(name)=upper('City') 
and hierarchytype in(select id from eg_hierarchy_type where upper(name)='ADMINISTRATION'))));



insert into eg_user(id,locale,username,password,pwdexpirydate,mobilenumber,createddate,lastmodifieddate,createdby,
lastmodifiedby,active,name,gender,type,version) values(nextval('seq_eg_user'),'en_IN','TPSUPENG2',
'$2a$10$uheIOutTnD33x7CDqac1zOL8DMiuz7mWplToPgcf7oxAI9OzRKxmK','01-Jan-2099','1234567891',
current_date,current_date,1,1,true,'TPSUPENG2',1,'EMPLOYEE',0);

insert into egeis_employee(id,code,employeestatus,employeetype,version) 
values((select id from eg_user where username='TPSUPENG2'),'TPSUPENG2','EMPLOYED',(select id from egeis_employeetype where name='Permanent'),0);


INSERT INTO egeis_assignment (id,fund,function,designation,functionary,department,position,grade,lastmodifiedby,lastmodifieddate,createddate,createdby,fromdate,todate,version,employee,isprimary) 
VALUES (nextval('seq_egeis_assignment'),null,null,(SELECT id FROM eg_designation WHERE name='Superintendent'),null,(SELECT id FROM eg_department WHERE name='TP'),(SELECT id FROM eg_position 
WHERE name='TP_Superintendent_2'),1,1,now(),now(),1,'01-Apr-2018','31-Mar-2020',1,(SELECT id FROM egeis_employee WHERE code='TPSUPENG2'),true);


insert into eg_userrole(roleid,userid) values((select id from eg_role where upper(name)='EMPLOYEE'),(select id from eg_user where username='TPSUPENG2'));

insert into egeis_jurisdiction (id,employee,boundarytype,createddate,lastmodifieddate,createdby,lastmodifiedby,version,boundary)
select nextval('seq_egeis_jurisdiction'), (select id from egeis_employee where upper(code)='TPSUPENG2'),boundarytype ,current_date,
current_date,1,1,0, id from eg_boundary where boundarytype in ( select id from eg_boundary_type  where name='Ward' and hierarchytype=(select ID from eg_hierarchy_type where name='ADMINISTRATION')) AND name='Election Ward No 3' order by name ;
insert into egeis_jurisdiction (id,employee,boundarytype,createddate,lastmodifieddate,createdby,lastmodifiedby,version,boundary)
select nextval('seq_egeis_jurisdiction'), (select id from egeis_employee where upper(code)='TPSUPENG2'),boundarytype ,current_date,
current_date,1,1,0, id from eg_boundary where boundarytype in ( select id from eg_boundary_type  where name='Ward' and hierarchytype=(select ID from eg_hierarchy_type where name='ADMINISTRATION')) AND name='Election Ward No 4' order by name ;


insert into egeis_jurisdiction (id,employee,boundarytype,createddate,lastmodifieddate,createdby,lastmodifiedby,version,boundary)
values(nextval('seq_egeis_jurisdiction'),(select id from egeis_employee where upper(code)='TPSUPENG2'),(select id from eg_boundary_type where upper(name)=upper('City') 
and hierarchytype in(select id from eg_hierarchy_type where upper(name)='ADMINISTRATION')),current_date,current_date,1,1,0,(select id from eg_boundary where boundarytype =(select id from eg_boundary_type where upper(name)=upper('City') 
and hierarchytype in(select id from eg_hierarchy_type where upper(name)='ADMINISTRATION'))));


insert into eg_user(id,locale,username,password,pwdexpirydate,mobilenumber,createddate,lastmodifieddate,createdby,
lastmodifiedby,active,name,gender,type,version) values(nextval('seq_eg_user'),'en_IN','TPSUPENG3',
'$2a$10$uheIOutTnD33x7CDqac1zOL8DMiuz7mWplToPgcf7oxAI9OzRKxmK','01-Jan-2099','1234567891',
current_date,current_date,1,1,true,'TPSUPENG3',1,'EMPLOYEE',0);

insert into egeis_employee(id,code,employeestatus,employeetype,version) 
values((select id from eg_user where username='TPSUPENG3'),'TPSUPENG3','EMPLOYED',(select id from egeis_employeetype where name='Permanent'),0);


INSERT INTO egeis_assignment (id,fund,function,designation,functionary,department,position,grade,lastmodifiedby,lastmodifieddate,createddate,createdby,fromdate,todate,version,employee,isprimary) 
VALUES (nextval('seq_egeis_assignment'),null,null,(SELECT id FROM eg_designation WHERE name='Superintendent'),null,(SELECT id FROM eg_department WHERE name='TP'),(SELECT id FROM eg_position 
WHERE name='TP_Superintendent_3'),1,1,now(),now(),1,'01-Apr-2018','31-Mar-2020',1,(SELECT id FROM egeis_employee WHERE code='TPSUPENG3'),true);


insert into eg_userrole(roleid,userid) values((select id from eg_role where upper(name)='EMPLOYEE'),(select id from eg_user where username='TPSUPENG3'));

insert into egeis_jurisdiction (id,employee,boundarytype,createddate,lastmodifieddate,createdby,lastmodifiedby,version,boundary)
select nextval('seq_egeis_jurisdiction'), (select id from egeis_employee where upper(code)='TPSUPENG3'),boundarytype ,current_date,
current_date,1,1,0, id from eg_boundary where boundarytype in ( select id from eg_boundary_type  where name='Ward' and hierarchytype=(select ID from eg_hierarchy_type where name='ADMINISTRATION')) AND name='Election Ward No 5' order by name ;


insert into egeis_jurisdiction (id,employee,boundarytype,createddate,lastmodifieddate,createdby,lastmodifiedby,version,boundary)
values(nextval('seq_egeis_jurisdiction'),(select id from egeis_employee where upper(code)='TPSUPENG3'),(select id from eg_boundary_type where upper(name)=upper('City') 
and hierarchytype in(select id from eg_hierarchy_type where upper(name)='ADMINISTRATION')),current_date,current_date,1,1,0,(select id from eg_boundary where boundarytype =(select id from eg_boundary_type where upper(name)=upper('City') 
and hierarchytype in(select id from eg_hierarchy_type where upper(name)='ADMINISTRATION'))));


insert into eg_user(id,locale,username,password,pwdexpirydate,mobilenumber,createddate,lastmodifieddate,createdby,
lastmodifiedby,active,name,gender,type,version) values(nextval('seq_eg_user'),'en_IN','TPSUPENG4',
'$2a$10$uheIOutTnD33x7CDqac1zOL8DMiuz7mWplToPgcf7oxAI9OzRKxmK','01-Jan-2099','1234567891',
current_date,current_date,1,1,true,'TPSUPENG4',1,'EMPLOYEE',0);

insert into egeis_employee(id,code,employeestatus,employeetype,version) 
values((select id from eg_user where username='TPSUPENG4'),'TPSUPENG4','EMPLOYED',(select id from egeis_employeetype where name='Permanent'),0);


INSERT INTO egeis_assignment (id,fund,function,designation,functionary,department,position,grade,lastmodifiedby,lastmodifieddate,createddate,createdby,fromdate,todate,version,employee,isprimary) 
VALUES (nextval('seq_egeis_assignment'),null,null,(SELECT id FROM eg_designation WHERE name='Superintendent'),null,(SELECT id FROM eg_department WHERE name='TP'),(SELECT id FROM eg_position 
WHERE name='TP_Superintendent_4'),1,1,now(),now(),1,'01-Apr-2018','31-Mar-2020',1,(SELECT id FROM egeis_employee WHERE code='TPSUPENG4'),true);


insert into eg_userrole(roleid,userid) values((select id from eg_role where upper(name)='EMPLOYEE'),(select id from eg_user where username='TPSUPENG4'));

insert into egeis_jurisdiction (id,employee,boundarytype,createddate,lastmodifieddate,createdby,lastmodifiedby,version,boundary)
select nextval('seq_egeis_jurisdiction'), (select id from egeis_employee where upper(code)='TPSUPENG4'),boundarytype ,current_date,
current_date,1,1,0, id from eg_boundary where boundarytype in ( select id from eg_boundary_type  where name='Ward' and hierarchytype=(select ID from eg_hierarchy_type where name='ADMINISTRATION')) AND name='Election Ward No 6' order by name ;


insert into egeis_jurisdiction (id,employee,boundarytype,createddate,lastmodifieddate,createdby,lastmodifiedby,version,boundary)
values(nextval('seq_egeis_jurisdiction'),(select id from egeis_employee where upper(code)='TPSUPENG4'),(select id from eg_boundary_type where upper(name)=upper('City') 
and hierarchytype in(select id from eg_hierarchy_type where upper(name)='ADMINISTRATION')),current_date,current_date,1,1,0,(select id from eg_boundary where boundarytype =(select id from eg_boundary_type where upper(name)=upper('City') 
and hierarchytype in(select id from eg_hierarchy_type where upper(name)='ADMINISTRATION'))));


insert into eg_user(id,locale,username,password,pwdexpirydate,mobilenumber,createddate,lastmodifieddate,createdby,
lastmodifiedby,active,name,gender,type,version) values(nextval('seq_eg_user'),'en_IN','TPSECCLRK1',
'$2a$10$uheIOutTnD33x7CDqac1zOL8DMiuz7mWplToPgcf7oxAI9OzRKxmK','01-Jan-2099','1234567891',
current_date,current_date,1,1,true,'TPSECCLRK1',1,'EMPLOYEE',0);

insert into egeis_employee(id,code,employeestatus,employeetype,version) 
values((select id from eg_user where username='TPSECCLRK1'),'TPSECCLRK1','EMPLOYED',(select id from egeis_employeetype where name='Permanent'),0);


INSERT INTO egeis_assignment (id,fund,function,designation,functionary,department,position,grade,lastmodifiedby,lastmodifieddate,createddate,createdby,fromdate,todate,version,employee,isprimary) 
VALUES (nextval('seq_egeis_assignment'),null,null,(SELECT id FROM eg_designation WHERE name='Section Clerk'),null,(SELECT id FROM eg_department WHERE name='TP'),(SELECT id FROM eg_position 
WHERE name='TP_Section Clerk_1'),1,1,now(),now(),1,'01-Apr-2018','31-Mar-2020',1,(SELECT id FROM egeis_employee WHERE code='TPSECCLRK1'),true);


insert into eg_userrole(roleid,userid) values((select id from eg_role where upper(name)='EMPLOYEE'),(select id from eg_user where username='TPSECCLRK1'));

insert into egeis_jurisdiction (id,employee,boundarytype,createddate,lastmodifieddate,createdby,lastmodifiedby,version,boundary)
select nextval('seq_egeis_jurisdiction'), (select id from egeis_employee where upper(code)='TPSECCLRK1'),boundarytype ,current_date,
current_date,1,1,0, id from eg_boundary where boundarytype in ( select id from eg_boundary_type  where name='Ward' and hierarchytype=(select ID from eg_hierarchy_type where name='ADMINISTRATION')) AND name='Election Ward No 1' order by name ;
insert into egeis_jurisdiction (id,employee,boundarytype,createddate,lastmodifieddate,createdby,lastmodifiedby,version,boundary)
select nextval('seq_egeis_jurisdiction'), (select id from egeis_employee where upper(code)='TPSECCLRK1'),boundarytype ,current_date,
current_date,1,1,0, id from eg_boundary where boundarytype in ( select id from eg_boundary_type  where name='Ward' and hierarchytype=(select ID from eg_hierarchy_type where name='ADMINISTRATION')) AND name='Election Ward No 2' order by name ;


insert into egeis_jurisdiction (id,employee,boundarytype,createddate,lastmodifieddate,createdby,lastmodifiedby,version,boundary)
values(nextval('seq_egeis_jurisdiction'),(select id from egeis_employee where upper(code)='TPSECCLRK1'),(select id from eg_boundary_type where upper(name)=upper('City') 
and hierarchytype in(select id from eg_hierarchy_type where upper(name)='ADMINISTRATION')),current_date,current_date,1,1,0,(select id from eg_boundary where boundarytype =(select id from eg_boundary_type where upper(name)=upper('City') 
and hierarchytype in(select id from eg_hierarchy_type where upper(name)='ADMINISTRATION'))));



insert into eg_user(id,locale,username,password,pwdexpirydate,mobilenumber,createddate,lastmodifieddate,createdby,
lastmodifiedby,active,name,gender,type,version) values(nextval('seq_eg_user'),'en_IN','TPSECCLRK2',
'$2a$10$uheIOutTnD33x7CDqac1zOL8DMiuz7mWplToPgcf7oxAI9OzRKxmK','01-Jan-2099','1234567891',
current_date,current_date,1,1,true,'TPSECCLRK2',1,'EMPLOYEE',0);

insert into egeis_employee(id,code,employeestatus,employeetype,version) 
values((select id from eg_user where username='TPSECCLRK2'),'TPSECCLRK2','EMPLOYED',(select id from egeis_employeetype where name='Permanent'),0);


INSERT INTO egeis_assignment (id,fund,function,designation,functionary,department,position,grade,lastmodifiedby,lastmodifieddate,createddate,createdby,fromdate,todate,version,employee,isprimary) 
VALUES (nextval('seq_egeis_assignment'),null,null,(SELECT id FROM eg_designation WHERE name='Section Clerk'),null,(SELECT id FROM eg_department WHERE name='TP'),(SELECT id FROM eg_position 
WHERE name='TP_Section Clerk_2'),1,1,now(),now(),1,'01-Apr-2018','31-Mar-2020',1,(SELECT id FROM egeis_employee WHERE code='TPSECCLRK2'),true);


insert into eg_userrole(roleid,userid) values((select id from eg_role where upper(name)='EMPLOYEE'),(select id from eg_user where username='TPSECCLRK2'));

insert into egeis_jurisdiction (id,employee,boundarytype,createddate,lastmodifieddate,createdby,lastmodifiedby,version,boundary)
select nextval('seq_egeis_jurisdiction'), (select id from egeis_employee where upper(code)='TPSECCLRK2'),boundarytype ,current_date,
current_date,1,1,0, id from eg_boundary where boundarytype in ( select id from eg_boundary_type  where name='Ward' and hierarchytype=(select ID from eg_hierarchy_type where name='ADMINISTRATION')) AND name='Election Ward No 3' order by name ;
insert into egeis_jurisdiction (id,employee,boundarytype,createddate,lastmodifieddate,createdby,lastmodifiedby,version,boundary)
select nextval('seq_egeis_jurisdiction'), (select id from egeis_employee where upper(code)='TPSECCLRK2'),boundarytype ,current_date,
current_date,1,1,0, id from eg_boundary where boundarytype in ( select id from eg_boundary_type  where name='Ward' and hierarchytype=(select ID from eg_hierarchy_type where name='ADMINISTRATION')) AND name='Election Ward No 4' order by name ;


insert into egeis_jurisdiction (id,employee,boundarytype,createddate,lastmodifieddate,createdby,lastmodifiedby,version,boundary)
values(nextval('seq_egeis_jurisdiction'),(select id from egeis_employee where upper(code)='TPSECCLRK2'),(select id from eg_boundary_type where upper(name)=upper('City') 
and hierarchytype in(select id from eg_hierarchy_type where upper(name)='ADMINISTRATION')),current_date,current_date,1,1,0,(select id from eg_boundary where boundarytype =(select id from eg_boundary_type where upper(name)=upper('City') 
and hierarchytype in(select id from eg_hierarchy_type where upper(name)='ADMINISTRATION'))));


insert into eg_user(id,locale,username,password,pwdexpirydate,mobilenumber,createddate,lastmodifieddate,createdby,
lastmodifiedby,active,name,gender,type,version) values(nextval('seq_eg_user'),'en_IN','TPSECCLRK3',
'$2a$10$uheIOutTnD33x7CDqac1zOL8DMiuz7mWplToPgcf7oxAI9OzRKxmK','01-Jan-2099','1234567891',
current_date,current_date,1,1,true,'TPSECCLRK3',1,'EMPLOYEE',0);

insert into egeis_employee(id,code,employeestatus,employeetype,version) 
values((select id from eg_user where username='TPSECCLRK3'),'TPSECCLRK3','EMPLOYED',(select id from egeis_employeetype where name='Permanent'),0);


INSERT INTO egeis_assignment (id,fund,function,designation,functionary,department,position,grade,lastmodifiedby,lastmodifieddate,createddate,createdby,fromdate,todate,version,employee,isprimary) 
VALUES (nextval('seq_egeis_assignment'),null,null,(SELECT id FROM eg_designation WHERE name='Section Clerk'),null,(SELECT id FROM eg_department WHERE name='TP'),(SELECT id FROM eg_position 
WHERE name='TP_Section Clerk_3'),1,1,now(),now(),1,'01-Apr-2018','31-Mar-2020',1,(SELECT id FROM egeis_employee WHERE code='TPSECCLRK3'),true);


insert into eg_userrole(roleid,userid) values((select id from eg_role where upper(name)='EMPLOYEE'),(select id from eg_user where username='TPSECCLRK3'));

insert into egeis_jurisdiction (id,employee,boundarytype,createddate,lastmodifieddate,createdby,lastmodifiedby,version,boundary)
select nextval('seq_egeis_jurisdiction'), (select id from egeis_employee where upper(code)='TPSECCLRK3'),boundarytype ,current_date,
current_date,1,1,0, id from eg_boundary where boundarytype in ( select id from eg_boundary_type  where name='Ward' and hierarchytype=(select ID from eg_hierarchy_type where name='ADMINISTRATION')) AND name='Election Ward No 5' order by name ;


insert into egeis_jurisdiction (id,employee,boundarytype,createddate,lastmodifieddate,createdby,lastmodifiedby,version,boundary)
values(nextval('seq_egeis_jurisdiction'),(select id from egeis_employee where upper(code)='TPSECCLRK3'),(select id from eg_boundary_type where upper(name)=upper('City') 
and hierarchytype in(select id from eg_hierarchy_type where upper(name)='ADMINISTRATION')),current_date,current_date,1,1,0,(select id from eg_boundary where boundarytype =(select id from eg_boundary_type where upper(name)=upper('City') 
and hierarchytype in(select id from eg_hierarchy_type where upper(name)='ADMINISTRATION'))));


insert into eg_user(id,locale,username,password,pwdexpirydate,mobilenumber,createddate,lastmodifieddate,createdby,
lastmodifiedby,active,name,gender,type,version) values(nextval('seq_eg_user'),'en_IN','TPSECCLRK4',
'$2a$10$uheIOutTnD33x7CDqac1zOL8DMiuz7mWplToPgcf7oxAI9OzRKxmK','01-Jan-2099','1234567891',
current_date,current_date,1,1,true,'TPSECCLRK4',1,'EMPLOYEE',0);

insert into egeis_employee(id,code,employeestatus,employeetype,version) 
values((select id from eg_user where username='TPSECCLRK4'),'TPSECCLRK4','EMPLOYED',(select id from egeis_employeetype where name='Permanent'),0);


INSERT INTO egeis_assignment (id,fund,function,designation,functionary,department,position,grade,lastmodifiedby,lastmodifieddate,createddate,createdby,fromdate,todate,version,employee,isprimary) 
VALUES (nextval('seq_egeis_assignment'),null,null,(SELECT id FROM eg_designation WHERE name='Section Clerk'),null,(SELECT id FROM eg_department WHERE name='TP'),(SELECT id FROM eg_position 
WHERE name='TP_Section Clerk_4'),1,1,now(),now(),1,'01-Apr-2018','31-Mar-2020',1,(SELECT id FROM egeis_employee WHERE code='TPSECCLRK4'),true);


insert into eg_userrole(roleid,userid) values((select id from eg_role where upper(name)='EMPLOYEE'),(select id from eg_user where username='TPSECCLRK4'));

insert into egeis_jurisdiction (id,employee,boundarytype,createddate,lastmodifieddate,createdby,lastmodifiedby,version,boundary)
select nextval('seq_egeis_jurisdiction'), (select id from egeis_employee where upper(code)='TPSECCLRK4'),boundarytype ,current_date,
current_date,1,1,0, id from eg_boundary where boundarytype in ( select id from eg_boundary_type  where name='Ward' and hierarchytype=(select ID from eg_hierarchy_type where name='ADMINISTRATION')) AND name='Election Ward No 6' order by name ;


insert into egeis_jurisdiction (id,employee,boundarytype,createddate,lastmodifieddate,createdby,lastmodifiedby,version,boundary)
values(nextval('seq_egeis_jurisdiction'),(select id from egeis_employee where upper(code)='TPSECCLRK4'),(select id from eg_boundary_type where upper(name)=upper('City') 
and hierarchytype in(select id from eg_hierarchy_type where upper(name)='ADMINISTRATION')),current_date,current_date,1,1,0,(select id from eg_boundary where boundarytype =(select id from eg_boundary_type where upper(name)=upper('City') 
and hierarchytype in(select id from eg_hierarchy_type where upper(name)='ADMINISTRATION'))));

