Insert into EG_ACTION (ID,NAME,URL,QUERYPARAMS,PARENTMODULE,ORDERNUMBER,DISPLAYNAME,ENABLED,CONTEXTROOT,VERSION,CREATEDBY,CREATEDDATE,LASTMODIFIEDBY,LASTMODIFIEDDATE,APPLICATION) values (NEXTVAL('SEQ_EG_ACTION'),'THIRDPARTY_GRIEVANCE_CRN_REQUIRED_CHECK','/grievance/thirdparty/isCrnRequired',null,(select id from EG_MODULE where name = 'PGR_MAIN'),1,'RCCRNRequiredThirdParty','f','pgr',0,1,now(),1,now(),(select id from eg_module  where name = 'PGR'));