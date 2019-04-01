alter table IF EXISTS state.EGBPA_MSTR_STAKEHOLDERTYPE  ADD COLUMN IF NOT EXISTS regFee numeric not null default 500;

alter table IF EXISTS state.EGBPA_MSTR_STAKEHOLDERTYPE  ADD COLUMN IF NOT EXISTS autoGenerateLicenceDetails boolean not null default true;