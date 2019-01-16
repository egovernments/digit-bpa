
---------------------------new designations for building plan approval------------------------------

insert into eg_designation (id,name, description, chartofaccounts,version,createddate,lastmodifieddate,createdby,lastmodifiedby,code) values(nextval('seq_eg_designation'),'Town Planning Building Overseer','Town Planning Building Overseer',null,0,current_date,current_date,1,1,'TPB');

insert into eg_designation (id,name, description, chartofaccounts,version,createddate,lastmodifieddate,createdby,lastmodifiedby,code) values(nextval('seq_eg_designation'),'Superintendent of NOC','Superintendent of NOC',null,0,current_date,current_date,1,1,'SUPRNOC');

insert into eg_designation (id,name, description, chartofaccounts,version,createddate,lastmodifieddate,createdby,lastmodifiedby,code) values(nextval('seq_eg_designation'),'Superintendent of Approval','Superintendent of Approval',null,0,current_date,current_date,1,1,'SUPRAPPRL');

insert into eg_designation (id,name, description, chartofaccounts,version,createddate,lastmodifieddate,createdby,lastmodifiedby,code) values(nextval('seq_eg_designation'),'Corporation Engineer','Corporation Engineer',null,0,current_date,current_date,1,1,'CORPENG');

insert into eg_designation (id,name, description, chartofaccounts,version,createddate,lastmodifieddate,createdby,lastmodifiedby,code) values(nextval('seq_eg_designation'),'Secretary','Secretary',null,0,current_date,current_date,1,1,'SEC');


-------------userrole action mapping--------------------------

  
INSERT INTO eg_userrole (roleid,userid)
SELECT (select id from eg_role where name='BPA Approver'), (select id from eg_user where username='shahid')
WHERE
    NOT EXISTS (select roleid,userid from eg_userrole where roleid in (select id from eg_role where name='BPA Approver') and userid in 
  (select id from eg_user where username='shahid'));

INSERT INTO eg_userrole (roleid,userid)
SELECT (select id from eg_role where name='Employee'), (select id from eg_user where username='shahid')
WHERE
    NOT EXISTS (select roleid,userid from eg_userrole where roleid in (select id from eg_role where name='Employee') and userid in 
  (select id from eg_user where username='shahid'));

INSERT INTO eg_userrole (roleid,userid)
SELECT (select id from eg_role where name='BPA Approver'), (select id from eg_user where username='syed')
WHERE
    NOT EXISTS (select roleid,userid from eg_userrole where roleid in (select id from eg_role where name='BPA Approver') and userid in 
  (select id from eg_user where username='syed'));

INSERT INTO eg_userrole (roleid,userid)
SELECT (select id from eg_role where name='Employee'), (select id from eg_user where username='syed')
WHERE
    NOT EXISTS (select roleid,userid from eg_userrole where roleid in (select id from eg_role where name='Employee') and userid in 
  (select id from eg_user where username='syed'));

INSERT INTO eg_userrole (roleid,userid)
SELECT (select id from eg_role where name='BPA Approver'), (select id from eg_user where username='nayeemalla')
WHERE
    NOT EXISTS (select roleid,userid from eg_userrole where roleid in (select id from eg_role where name='BPA Approver') and userid in 
  (select id from eg_user where username='nayeemalla'));

INSERT INTO eg_userrole (roleid,userid)
SELECT (select id from eg_role where name='Employee'), (select id from eg_user where username='nayeemalla')
WHERE
    NOT EXISTS (select roleid,userid from eg_userrole where roleid in (select id from eg_role where name='Employee') and userid in 
  (select id from eg_user where username='nayeemalla'));

INSERT INTO eg_userrole (roleid,userid)
SELECT (select id from eg_role where name='BPA Approver'), (select id from eg_user where username='satyam')
WHERE
    NOT EXISTS (select roleid,userid from eg_userrole where roleid in (select id from eg_role where name='BPA Approver') and userid in 
  (select id from eg_user where username='satyam'));

INSERT INTO eg_userrole (roleid,userid)
SELECT (select id from eg_role where name='Employee'), (select id from eg_user where username='satyam')
WHERE
    NOT EXISTS (select roleid,userid from eg_userrole where roleid in (select id from eg_role where name='Employee') and userid in 
  (select id from eg_user where username='satyam'));

INSERT INTO eg_userrole (roleid,userid)
SELECT (select id from eg_role where name='BPA Approver'), (select id from eg_user where username='satish')
WHERE
    NOT EXISTS (select roleid,userid from eg_userrole where roleid in (select id from eg_role where name='BPA Approver') and userid in 
  (select id from eg_user where username='satish'));

INSERT INTO eg_userrole (roleid,userid)
SELECT (select id from eg_role where name='Employee'), (select id from eg_user where username='satish')
WHERE
    NOT EXISTS (select roleid,userid from eg_userrole where roleid in (select id from eg_role where name='Employee') and userid in 
  (select id from eg_user where username='satish'));

INSERT INTO eg_userrole (roleid,userid)
SELECT (select id from eg_role where name='BPA Approver'), (select id from eg_user where username='pradeep')
WHERE
    NOT EXISTS (select roleid,userid from eg_userrole where roleid in (select id from eg_role where name='BPA Approver') and userid in 
  (select id from eg_user where username='pradeep'));

INSERT INTO eg_userrole (roleid,userid)
SELECT (select id from eg_role where name='Employee'), (select id from eg_user where username='pradeep')
WHERE
    NOT EXISTS (select roleid,userid from eg_userrole where roleid in (select id from eg_role where name='Employee') and userid in 
  (select id from eg_user where username='pradeep'));

INSERT INTO eg_userrole (roleid,userid)
SELECT (select id from eg_role where name='BPA Approver'), (select id from eg_user where username='vaibhav')
WHERE
    NOT EXISTS (select roleid,userid from eg_userrole where roleid in (select id from eg_role where name='BPA Approver') and userid in 
  (select id from eg_user where username='vaibhav'));

INSERT INTO eg_userrole (roleid,userid)
SELECT (select id from eg_role where name='Employee'), (select id from eg_user where username='vaibhav')
WHERE
    NOT EXISTS (select roleid,userid from eg_userrole where roleid in (select id from eg_role where name='Employee') and userid in 
  (select id from eg_user where username='vaibhav'));

INSERT INTO eg_userrole (roleid,userid)
SELECT (select id from eg_role where name='BPA Approver'), (select id from eg_user where username='rishi')
WHERE
    NOT EXISTS (select roleid,userid from eg_userrole where roleid in (select id from eg_role where name='BPA Approver') and userid in 
  (select id from eg_user where username='rishi'));

INSERT INTO eg_userrole (roleid,userid)
SELECT (select id from eg_role where name='Employee'), (select id from eg_user where username='rishi')
WHERE
    NOT EXISTS (select roleid,userid from eg_userrole where roleid in (select id from eg_role where name='Employee') and userid in 
  (select id from eg_user where username='rishi'));

INSERT INTO eg_userrole (roleid,userid)
SELECT (select id from eg_role where name='BPA Approver'), (select id from eg_user where username='sankar')
WHERE
    NOT EXISTS (select roleid,userid from eg_userrole where roleid in (select id from eg_role where name='BPA Approver') and userid in 
  (select id from eg_user where username='sankar'));

INSERT INTO eg_userrole (roleid,userid)
SELECT (select id from eg_role where name='Employee'), (select id from eg_user where username='sankar')
WHERE
    NOT EXISTS (select roleid,userid from eg_userrole where roleid in (select id from eg_role where name='Employee') and userid in 
  (select id from eg_user where username='sankar'));

INSERT INTO eg_userrole (roleid,userid)
SELECT (select id from eg_role where name='BPA Approver'), (select id from eg_user where username='manikanta')
WHERE
    NOT EXISTS (select roleid,userid from eg_userrole where roleid in (select id from eg_role where name='BPA Approver') and userid in 
  (select id from eg_user where username='manikanta'));

INSERT INTO eg_userrole (roleid,userid)
SELECT (select id from eg_role where name='Employee'), (select id from eg_user where username='manikanta')
WHERE
    NOT EXISTS (select roleid,userid from eg_userrole where roleid in (select id from eg_role where name='Employee') and userid in 
  (select id from eg_user where username='manikanta'));

INSERT INTO eg_userrole (roleid,userid)
SELECT (select id from eg_role where name='BPA Approver'), (select id from eg_user where username='rajkumar')
WHERE
    NOT EXISTS (select roleid,userid from eg_userrole where roleid in (select id from eg_role where name='BPA Approver') and userid in 
  (select id from eg_user where username='rajkumar'));

INSERT INTO eg_userrole (roleid,userid)
SELECT (select id from eg_role where name='Employee'), (select id from eg_user where username='rajkumar')
WHERE
    NOT EXISTS (select roleid,userid from eg_userrole where roleid in (select id from eg_role where name='Employee') and userid in 
  (select id from eg_user where username='rajkumar'));

INSERT INTO eg_userrole (roleid,userid)
SELECT (select id from eg_role where name='BPA Approver'), (select id from eg_user where username='sathish')
WHERE
    NOT EXISTS (select roleid,userid from eg_userrole where roleid in (select id from eg_role where name='BPA Approver') and userid in 
  (select id from eg_user where username='sathish'));

INSERT INTO eg_userrole (roleid,userid)
SELECT (select id from eg_role where name='Employee'), (select id from eg_user where username='sathish')
WHERE
    NOT EXISTS (select roleid,userid from eg_userrole where roleid in (select id from eg_role where name='Employee') and userid in 
  (select id from eg_user where username='sathish'));

  
----------------------------user dept_desig_postion assignment mapping------------------------------

INSERT INTO egeis_deptdesig (id, designation, department, outsourcedposts, sanctionedposts, version, createddate, lastmodifieddate, createdby, lastmodifiedby) VALUES (nextval('seq_egeis_deptdesig'), 
(select id from eg_designation where name = 'Town Planning Building Overseer'), (select id from eg_department where name = 'Revenue'), 0, 2, 0, now(), now(), 1, 1);

INSERT INTO eg_position (id, name, deptdesig, createddate, lastmodifieddate, createdby, lastmodifiedby, ispostoutsourced, version) VALUES (nextval('seq_eg_position'),'TPBO-Town Planning Building Overseer-01',
(select id from egeis_deptdesig where designation = (select id from eg_designation where name = 'Town Planning Building Overseer') and department = (select id from eg_department where name = 'Revenue')), now(), now(), 1, 1, false, 0);

INSERT INTO egeis_assignment (id,fund,function,designation,functionary,department,position,grade,lastmodifiedby,lastmodifieddate,createddate,createdby,fromdate,todate,version,employee,isprimary) VALUES (nextval('seq_egeis_assignment'),null,null,
(SELECT id FROM eg_designation WHERE name='Town Planning Building Overseer'),null,(SELECT id FROM eg_department WHERE name='Revenue'),(SELECT id FROM eg_position 
WHERE name='TPBO-Town Planning Building Overseer-01'),1,1,now(),now(),1,'01-Apr-2017','31-Mar-2020',1,(SELECT id FROM egeis_employee WHERE code='E034'),true);


INSERT INTO egeis_deptdesig (id, designation, department, outsourcedposts, sanctionedposts, version, createddate, lastmodifieddate, createdby, lastmodifiedby) VALUES (nextval('seq_egeis_deptdesig'), 
(select id from eg_designation where name = 'Superintendent of Approval'), (select id from eg_department where name = 'Administration'), 0, 2, 0, now(), now(), 1, 1);

INSERT INTO eg_position (id, name, deptdesig, createddate, lastmodifieddate, createdby, lastmodifiedby,ispostoutsourced, version) VALUES (nextval('seq_eg_position'),'APPVL-Superintendent-01',
(select id from egeis_deptdesig where designation = (select id from eg_designation where name = 'Superintendent of Approval') and department = (select id from eg_department where name = 'Administration')), now(), now(), 1, 1, false, 0);

INSERT INTO egeis_assignment (id,fund,function,designation,functionary,department,position,grade,lastmodifiedby,lastmodifieddate,createddate,createdby,fromdate,todate,version,employee,isprimary) 
VALUES (nextval('seq_egeis_assignment'),null,null,(SELECT id FROM eg_designation WHERE name='Superintendent of Approval'),null,(SELECT id FROM eg_department WHERE name='Administration'),(SELECT id FROM eg_position 
WHERE name='APPVL-Superintendent-01'),1,1,now(),now(),1,'01-Apr-2017','31-Mar-2020',1,(SELECT id FROM egeis_employee WHERE code='E036'),true);


INSERT INTO egeis_deptdesig (id, designation, department, outsourcedposts, sanctionedposts,version, createddate, lastmodifieddate, createdby, lastmodifiedby) VALUES (nextval('seq_egeis_deptdesig'), 
(select id from eg_designation where name = 'Corporation Engineer'), (select id from eg_department where name = 'Engineering'), 0, 2, 0, now(), now(), 1, 1);

INSERT INTO eg_position (id, name, deptdesig, createddate, lastmodifieddate, createdby, lastmodifiedby,ispostoutsourced, version) VALUES (nextval('seq_eg_position'),'CORP-Corporation Engineer-01',
(select id from egeis_deptdesig where designation = (select id from eg_designation where name = 'Corporation Engineer') and department = (select id from eg_department where name = 'Engineering')), now(), now(), 1, 1, false, 0);

INSERT INTO egeis_assignment (id,fund,function,designation,functionary,department,position,grade,lastmodifiedby,lastmodifieddate,createddate,createdby,fromdate,todate,version,employee,isprimary) 
VALUES (nextval('seq_egeis_assignment'),null,null,(SELECT id FROM eg_designation WHERE name='Corporation Engineer'),null,(SELECT id FROM eg_department WHERE name='Engineering'),(SELECT id FROM eg_position 
WHERE name='CORP-Corporation Engineer-01'),1,1,now(),now(),1,'01-Apr-2017','31-Mar-2020',1,(SELECT id FROM egeis_employee WHERE code='E025'),true);


INSERT INTO egeis_deptdesig (id, designation, department, outsourcedposts, sanctionedposts,
 version, createddate, lastmodifieddate, createdby, lastmodifiedby) VALUES (nextval('seq_egeis_deptdesig'), 
 (select id from eg_designation where name = 'Secretary'), (select id from eg_department where name = 'Administration'),
 0, 2, 0, now(), now(), 1, 1);

INSERT INTO eg_position (id, name, deptdesig, createddate, lastmodifieddate, createdby, lastmodifiedby,
 ispostoutsourced, version) VALUES (nextval('seq_eg_position'),'SEC-Secretary-01',
(select id from egeis_deptdesig where designation = (select id from eg_designation where name = 'Secretary') 
and department = 
(select id from eg_department where name = 'Administration')), now(), now(), 1, 1, false, 0);

INSERT INTO egeis_assignment (id,fund,function,designation,functionary,department,position,grade
,lastmodifiedby,lastmodifieddate,createddate,createdby,fromdate,todate,version,employee,isprimary) VALUES (nextval('seq_egeis_assignment'),null,null,(SELECT id FROM eg_designation WHERE name='Secretary'),null,
(SELECT id FROM eg_department WHERE name='Administration'),(SELECT id FROM eg_position WHERE name='SEC-Secretary-01'),1,1,now(),now(),1,'01-Apr-2017','31-Mar-2020',1,(SELECT id FROM egeis_employee WHERE code='E021'),true);


INSERT INTO egeis_deptdesig (id, designation, department, outsourcedposts, sanctionedposts, version, createddate, lastmodifieddate, createdby, lastmodifiedby) VALUES (nextval('seq_egeis_deptdesig'), 
 (select id from eg_designation where name = 'Superintendent of NOC'), (select id from eg_department where name = 'Administration'), 0, 2, 0, now(), now(), 1, 1);

INSERT INTO eg_position (id, name, deptdesig, createddate, lastmodifieddate, createdby, lastmodifiedby, ispostoutsourced, version) VALUES (nextval('seq_eg_position'),'NOC-Superintendent-01',
(select id from egeis_deptdesig where designation = (select id from eg_designation where name = 'Superintendent of NOC') and department = (select id from eg_department where name = 'Administration')), now(), now(), 1, 1, false, 0);

INSERT INTO egeis_assignment (id,fund,function,designation,functionary,department,position,grade,lastmodifiedby,lastmodifieddate,createddate,createdby,fromdate,todate,version,employee,isprimary) 
VALUES (nextval('seq_egeis_assignment'),null,null,(SELECT id FROM eg_designation WHERE name='Superintendent of NOC'),null,(SELECT id FROM eg_department WHERE name='Administration'),(SELECT id FROM eg_position 
WHERE name='NOC-Superintendent-01'),1,1,now(),now(),1,'01-Apr-2017','31-Mar-2020',1,(SELECT id FROM egeis_employee WHERE code='E028'),true);