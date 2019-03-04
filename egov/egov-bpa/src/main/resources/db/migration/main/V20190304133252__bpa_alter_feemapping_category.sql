ALTER TABLE egbpa_mstr_bpafee_common ALTER COLUMN category TYPE bigint USING (trim(category)::bigint);


ALTER TABLE egbpa_mstr_bpafee_common ADD CONSTRAINT fk_bpafeecommon_category
   FOREIGN KEY (category)
   REFERENCES eg_reason_category (id);

alter table egbpa_application_feedetails drop constraint fk_application_feedetails_bpafee;

ALTER TABLE EGBPA_APPLICATION_FEEDETAILS RENAME COLUMN bpafee TO bpafeemapping;

      ALTER TABLE EGBPA_APPLICATION_FEEDETAILS
ADD CONSTRAINT fk_appfeedetails_bpafeemapping
   FOREIGN KEY (bpafeemapping)
   REFERENCES egbpa_mstr_bpafeemapping (id);


ALTER TABLE egbpa_application_feedetails_aud RENAME COLUMN bpafee TO bpafeemapping;