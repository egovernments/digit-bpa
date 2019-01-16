update egbpa_mstr_bpafeedetail set amount=3000 where bpafee in (select id from egbpa_mstr_bpafee where code in ('102','302','402','602','702'));
update egbpa_mstr_bpafeedetail set amount=15 where bpafee in (select id from egbpa_mstr_bpafee where code in ('009'));

alter table egbpa_application add column iseconomicallyweakersection boolean default false;
alter table egbpa_sitedetail alter column dwellingunitnt type bigint USING dwellingunitnt::bigint;