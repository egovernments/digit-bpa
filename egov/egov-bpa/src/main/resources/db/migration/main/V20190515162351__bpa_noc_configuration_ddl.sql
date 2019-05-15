create table egbpa_master_nocconfiguration(
id bigint not null,
department character varying(20) not null,
applicationType character varying(25) not null,
integrationtype character varying(20) not null,
integrationInitiation character varying(20) not null,
sla bigint not null,
isactive boolean default true);

CREATE SEQUENCE SEQ_EGBPA_MSTR_NOCCONFIG;