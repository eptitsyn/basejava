create table resume
(
    uuid      char(36) not null
        constraint resume_pk
            primary key,
    full_name text     not null
);

alter table resume
    owner to postgres;

create table contact
(
    id          serial
        constraint contact_pk
            primary key,
    type        text     not null,
    value       text     not null,
    resume_uuid char(36) not null
        constraint contact_resume_uuid_fk
            references resume
            on update restrict on delete cascade
);

alter table contact
    owner to postgres;

create unique index contact_uuid_index
    on contact (resume_uuid, type);

create table section
(
    resume_uuid  char(36)
        constraint string_section_uuid_fk
            references resume
            on update restrict on delete cascade,
    section_type text not null,
    data         text
);

alter table section
    owner to postgres;