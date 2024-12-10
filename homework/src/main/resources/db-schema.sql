create table if not exists sample(
    id serial PRIMARY KEY,
    name VARCHAR(64) NOT NULL,
    data text,
    value int default 0
);

create FUNCTION sample_trigger() RETURNS TRIGGER AS
'
    BEGIN
        IF (SELECT value FROM sample where id = NEW.id ) > 1000
           THEN
           RAISE SQLSTATE ''23503'';
           END IF;
        RETURN NEW;
    END;
' LANGUAGE plpgsql;

create TRIGGER sample_value AFTER insert ON sample
    FOR EACH ROW EXECUTE PROCEDURE sample_trigger();

create table if not exists product(
  id serial primary key ,
  name varchar(64) not null ,
  description text,
  brandName text
);

create table if not exists company(
 name varchar(64) primary key ,
 country text,
 zip int,
 streetInfo text,
 phoneNumber text not null unique
);

create table if not exists email(
 name text,
 email text,
 primary key (name, email),
 foreign key (name) REFERENCES company(name) on delete cascade
);

create table if not exists city(
 zip int primary key,
 city text
);

create table if not exists produce(
 name varchar(64),
 id int,
 capacity int,
 foreign key (id) references product(id),
 foreign key (name) references company(name),
 primary key (name, id)
);

create table if not exists transaction(
 name text,
 id int,
 amount int,
 date date,
 foreign key (id) references product(id),
 foreign key (name) references company(name),
 primary key (id, name)
);

CREATE FUNCTION order_trigger() RETURNS TRIGGER AS
'
    BEGIN
        IF (SELECT SUM(amount) FROM transaction WHERE name = NEW.name AND id = NEW.id ) >
           (SELECT capacity FROM produce WHERE name = NEW.name AND id = NEW.id )
        THEN
            RAISE SQLSTATE ''23503'';
        END IF;
        RETURN NEW;
    END;
' LANGUAGE plpgsql;

CREATE TRIGGER order_limit
    AFTER INSERT
    ON transaction
    FOR EACH ROW EXECUTE PROCEDURE order_trigger();



