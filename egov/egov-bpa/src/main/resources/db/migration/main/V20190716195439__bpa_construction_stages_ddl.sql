alter table EGBPA_MSTR_CONST_STAGES add column requireforregularization boolean;
alter table EGBPA_MSTR_CONST_STAGES add column requireforpermitrenewal boolean;
alter table EGBPA_MSTR_CONST_STAGES add column orderNumber bigint;

INSERT INTO egbpa_mstr_const_stages(
            id, code, description, isactive, version, createdby, createddate, 
            lastmodifiedby, lastmodifieddate, requireforregularization, requireforpermitrenewal, orderNumber)
    VALUES (nextval('SEQ_EGBPA_MSTR_CONST_STAGES'), 'Not Started', 'Not Started', true, 0, 1, now(), 
            1, now(), false, true, 1);

update egbpa_mstr_const_stages set requireforregularization = true, requireforpermitrenewal=true, orderNumber=2 where code='In Progress';
update egbpa_mstr_const_stages set requireforregularization = true, requireforpermitrenewal=false, orderNumber=3 where code='Completed';