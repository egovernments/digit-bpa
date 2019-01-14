alter table egbpa_lettertoparty add column currentstatevalueoflp character varying(200);
alter table egbpa_lettertoparty add column currentapplnstatus bigint;
ALTER TABLE egbpa_lettertoparty ADD CONSTRAINT fk_egbpa_lettertoparty_appln_status FOREIGN KEY (currentapplnstatus) REFERENCES egbpa_status (id);