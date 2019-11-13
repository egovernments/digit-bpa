
alter table EGBPA_MSTR_NOCCONFIG add column statusbypush boolean;

create table egbpa_noclist 
(
id bigint
);

create sequence seq_egbpa_noclist;

alter table EGBPA_APPLICATION add column noclistId bigint;



