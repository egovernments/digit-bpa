ALTER TABLE egf_dishonorcheque ADD CONSTRAINT egf_dishchq_cruse_fk FOREIGN KEY (createdby) REFERENCES state.eg_user(id);
ALTER TABLE egf_dishonorcheque ADD CONSTRAINT egf_dishchq_mbuse_fk FOREIGN KEY (lastmodifiedby) REFERENCES state.eg_user(id);
ALTER TABLE miscbilldetail ADD CONSTRAINT fk_mbd_pbi FOREIGN KEY (paidbyid) REFERENCES state.eg_user(id);
