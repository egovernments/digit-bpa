ALTER TABLE egbpa_application DROP COLUMN IF EXISTS previousownerdetails,DROP COLUMN IF EXISTS landregistrationdetails;

ALTER TABLE EGBPA_SITEDETAIL add column previousownerdetails character varying(4000),add column landregistrationdetails character varying(4000);
