alter table egbpa_applicant add column user_id bigint not null;
alter table egbpa_applicant add column createdby bigint NOT NULL;
alter table egbpa_applicant add column createddate  timestamp without time zone;
alter table egbpa_applicant add column lastmodifiedby bigint NOT NULL;
alter table egbpa_applicant add column lastmodifieddate  timestamp without time zone;
alter table egbpa_applicant add CONSTRAINT fk_bpaapplicant_createdby FOREIGN KEY (createdby)
      REFERENCES eg_user (id);
alter table egbpa_applicant add  CONSTRAINT fk_bpaapplicant_lastmodifiedby FOREIGN KEY (lastmodifiedby)
      REFERENCES eg_user (id);
