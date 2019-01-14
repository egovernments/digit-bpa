alter table egbpa_mstr_occupancy add column ordernumber bigint;
update egbpa_mstr_occupancy set ordernumber=1 where code='01';
update egbpa_mstr_occupancy set ordernumber=2 where code='02';
update egbpa_mstr_occupancy set ordernumber=3 where code='03';
update egbpa_mstr_occupancy set ordernumber=4 where code='04';
update egbpa_mstr_occupancy set ordernumber=5 where code='05';
update egbpa_mstr_occupancy set ordernumber=6 where code='06';
update egbpa_mstr_occupancy set ordernumber=7 where code='07';
update egbpa_mstr_occupancy set ordernumber=8 where code='08';
update egbpa_mstr_occupancy set ordernumber=9 where code='09';
update egbpa_mstr_occupancy set ordernumber=10 where code='10';
update egbpa_mstr_occupancy set ordernumber=11 where code='11';
update egbpa_mstr_occupancy set ordernumber=12 where code='12';
update egbpa_mstr_occupancy set ordernumber=13 where code='14';

update eg_wf_matrix set validactions ='Forward' where currentstate='NOC updation in progress' and nextstatus='NOC Updated' and objecttype='BpaApplication';

update eg_wf_matrix set validactions ='Approve,Reject'  where nextaction='Digital Sign Pending' and objecttype='BpaApplication' and fromqty=0 and toqty=299;

update eg_wf_matrix set validactions ='Forward,Reject'  where nextaction='Forwarded to AEE' and objecttype='BpaApplication' and fromqty=300 and toqty=749;

update eg_wf_matrix set validactions ='Approve,Reject'  where nextaction='Digital Sign Pending' and objecttype='BpaApplication' and fromqty=300 and toqty=749;

update eg_wf_matrix set validactions ='Forward,Reject'  where nextaction='Forwarded to AEE' and objecttype='BpaApplication' and fromqty=750 and toqty=1499;

update eg_wf_matrix set validactions ='Forward,Reject'  where nextaction='Forwarded to EE' and objecttype='BpaApplication' and fromqty=750 and toqty=1499;

update eg_wf_matrix set validactions ='Approve,Reject'  where nextaction='Digital Sign Pending' and objecttype='BpaApplication' and fromqty=750 and toqty=1499;

update eg_wf_matrix set validactions ='Forward,Reject'  where nextaction='Forwarded to AEE' and objecttype='BpaApplication' and fromqty=1500 and toqty=9999;

update eg_wf_matrix set validactions ='Forward,Reject'  where nextaction='Forwarded to EE' and objecttype='BpaApplication' and fromqty=1500 and toqty=9999;

update eg_wf_matrix set validactions ='Forward,Reject'  where nextaction='Forwarded to Corporation Engineer' and objecttype='BpaApplication' and fromqty=1500 and toqty=9999;

update eg_wf_matrix set validactions ='Approve,Reject'  where nextaction='Digital Sign Pending' and objecttype='BpaApplication' and fromqty=1500 and toqty=9999;

update eg_wf_matrix set validactions ='Forward,Reject'  where nextaction='Forwarded to AEE' and objecttype='BpaApplication' and fromqty=10000 and toqty=1000000;

update eg_wf_matrix set validactions ='Forward,Reject'  where nextaction='Forwarded to EE' and objecttype='BpaApplication' and fromqty=10000 and toqty=1000000;

update eg_wf_matrix set validactions ='Forward,Reject'  where nextaction='Forwarded to Corporation Engineer' and objecttype='BpaApplication' and fromqty=10000 and toqty=1000000;

update eg_wf_matrix set validactions ='Forward,Reject'  where nextaction='Forwarded to Secretary' and objecttype='BpaApplication' and fromqty=10000 and toqty=1000000;

update eg_wf_matrix set validactions ='Approve,Reject'  where nextaction='Digital Sign Pending' and objecttype='BpaApplication' and fromqty=10000 and toqty=1000000;

