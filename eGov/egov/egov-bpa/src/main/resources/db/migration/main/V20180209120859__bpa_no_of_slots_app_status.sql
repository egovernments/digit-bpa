INSERT INTO eg_appconfig ( ID, KEY_NAME, DESCRIPTION, VERSION, MODULE ) VALUES
(nextval('SEQ_EG_APPCONFIG'), 'NOOFDAYSFORASSIGNINGSLOTS', 'Number of Days for assigning slots',0, (select id from eg_module where name='BPA'));

INSERT INTO eg_appconfig_values ( ID, KEY_ID, EFFECTIVE_FROM, VALUE, VERSION ) 
VALUES (nextval('SEQ_EG_APPCONFIG_VALUES'),(SELECT id FROM EG_APPCONFIG WHERE KEY_NAME='NOOFDAYSFORASSIGNINGSLOTS'
 AND MODULE =(select id from eg_module where name='BPA')),current_date, '3',0);

insert into egbpa_status values(nextval('seq_egbpa_status'),'Scheduled For Document Scrutiny','Scheduled For Document Scrutiny','REGISTRATION',true,0,1,now(),1, now());

insert into egbpa_status values(nextval('seq_egbpa_status'),'Rescheduled For Document Scrutiny','Rescheduled For Document Scrutiny','REGISTRATION',true,0,1,now(),1, now());

insert into egbpa_status values(nextval('seq_egbpa_status'),'Pending For Rescheduling For Document Scrutiny','Pending For Rescheduling For Document Scrutiny','REGISTRATION',true,0,1,now(),1, now());