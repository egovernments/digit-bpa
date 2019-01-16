update egbpa_mstr_bpafeedetail set amount=5000 where bpafee in (select id from egbpa_mstr_bpafee where code in ('102','302','402','602','702'));

alter table egbpa_NOC_Document add column nocstatus character varying(40);

CREATE TABLE egbpa_noc_document_store
(
  filestoreid bigint NOT NULL,
  nocdocumentid bigint NOT NULL,
  CONSTRAINT fk_egbpa_noc_document FOREIGN KEY (nocdocumentid)
      REFERENCES egbpa_noc_document (id)
);