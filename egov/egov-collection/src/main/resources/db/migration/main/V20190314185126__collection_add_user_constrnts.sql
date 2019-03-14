ALTER TABLE egcl_branchuser_map ADD CONSTRAINT fk_egcl_branchuser_map_bankuser FOREIGN KEY (bankuser) REFERENCES state.eg_user(id);
ALTER TABLE egcl_branchuser_map ADD CONSTRAINT fk_egcl_branchuser_map_crtby FOREIGN KEY (createdby) REFERENCES state.eg_user(id);
ALTER TABLE egcl_branchuser_map ADD CONSTRAINT fk_egcl_branchuser_map_mfdby FOREIGN KEY (modifiedby) REFERENCES state.eg_user(id);
ALTER TABLE egcl_dishonorcheque ADD CONSTRAINT fk_egcl_dishchq_crtdby FOREIGN KEY (createdby) REFERENCES state.eg_user(id);
ALTER TABLE egcl_dishonorcheque ADD CONSTRAINT fk_egcl_dishchq_lastmodby FOREIGN KEY (lastmodifiedby) REFERENCES state.eg_user(id);
ALTER TABLE egcl_servicemodule_mapping ADD CONSTRAINT fk_egcl_servicemodule_map_createdby FOREIGN KEY (created_by) REFERENCES state.eg_user(id);
ALTER TABLE egcl_servicemodule_mapping ADD CONSTRAINT fk_egcl_servicemodule_map_modifiedby FOREIGN KEY (modified_by) REFERENCES state.eg_user(id);
