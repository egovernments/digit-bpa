INSERT INTO eg_appconfig ( ID, KEY_NAME, DESCRIPTION, VERSION, MODULE ) VALUES
(nextval('SEQ_EG_APPCONFIG'), 'PERMIT_RENEWAL_APPLY_PRIOR_DAYS_BEFORE_EXPIRY', 'Prior permit expiry how many days prior need to apply for renewal ',0, (select id from eg_module where name='BPA'));

INSERT INTO eg_appconfig_values ( ID, KEY_ID, EFFECTIVE_FROM, VALUE, VERSION ) 
VALUES (nextval('SEQ_EG_APPCONFIG_VALUES'),(SELECT id FROM EG_APPCONFIG WHERE KEY_NAME='PERMIT_RENEWAL_APPLY_PRIOR_DAYS_BEFORE_EXPIRY'
 AND MODULE =(select id from eg_module where name='BPA')),current_date, '60',0);

INSERT INTO eg_appconfig ( ID, KEY_NAME, DESCRIPTION, VERSION, MODULE ) VALUES
(nextval('SEQ_EG_APPCONFIG'), 'PERMIT_RENEWAL_ALLOWED_UPTO_AFTER_EXPIRY', 'After current permit expiry updto how many days can apply for the renewal ',0, (select id from eg_module where name='BPA'));

INSERT INTO eg_appconfig_values ( ID, KEY_ID, EFFECTIVE_FROM, VALUE, VERSION ) 
VALUES (nextval('SEQ_EG_APPCONFIG_VALUES'),(SELECT id FROM EG_APPCONFIG WHERE KEY_NAME='PERMIT_RENEWAL_ALLOWED_UPTO_AFTER_EXPIRY'
 AND MODULE =(select id from eg_module where name='BPA')),current_date, '365',0);