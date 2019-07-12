ALTER TABLE egbpa_sitedetail DROP CONSTRAINT fk_egbpa_sitedetail_adminboundary;
alter table EGBPA_SITEDETAIL alter column adminboundary type varchar(100) using adminboundary::varchar


ALTER TABLE egbpa_sitedetail DROP CONSTRAINT fk_egbpa_sitedetail_electionboundary;
alter table EGBPA_SITEDETAIL alter column electionboundary type varchar(100) using electionboundary::varchar;


ALTER TABLE egbpa_sitedetail DROP CONSTRAINT fk_egbpa_sitedetail_locationboundary;
alter table EGBPA_SITEDETAIL alter column locationboundary type varchar(100) using locationboundary::varchar;


ALTER TABLE egbpa_sitedetail DROP CONSTRAINT fk_egbpa_sitedetail_street;
alter table EGBPA_SITEDETAIL alter column street type varchar(100) using street::varchar;

	