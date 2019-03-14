ALTER TABLE eg_checklist ADD CONSTRAINT fk_eg_checklist_crtby FOREIGN KEY (createdby) REFERENCES state.eg_user(id);
ALTER TABLE eg_checklist ADD CONSTRAINT fk_eg_checklist_mdfdby FOREIGN KEY (lastmodifiedby) REFERENCES state.eg_user(id);
ALTER TABLE eg_checklist_type ADD CONSTRAINT fk_eg_mstr_checklist_type_crtby FOREIGN KEY (createdby) REFERENCES state.eg_user(id);
ALTER TABLE eg_checklist_type ADD CONSTRAINT fk_eg_mstr_checklist_type_mdfdby FOREIGN KEY (lastmodifiedby) REFERENCES state.eg_user(id);
ALTER TABLE eg_object_history ADD CONSTRAINT fk_modified_by FOREIGN KEY (modifed_by) REFERENCES state.eg_user(id);
