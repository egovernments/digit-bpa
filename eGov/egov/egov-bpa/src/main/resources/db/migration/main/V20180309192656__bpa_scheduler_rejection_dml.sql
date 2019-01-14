alter table egbpa_application add column failureInScheduler boolean DEFAULT false;
alter table egbpa_application add column schedulerFailedRemarks character varying(1024);

