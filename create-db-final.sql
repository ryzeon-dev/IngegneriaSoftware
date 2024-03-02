create table aircraft (
    id serial primary key,
    manufacturer text not null,
    model text not null,
    specification text not null,
    range integer not null,
    assistants_number integer not null,
    class char(1) not null,
    places integer not null
);

create table aircraft_instance (
    plate text primary key,
    aircraft integer not null,
    foreign key (aircraft) references aircraft(id)
);

create table airport (
    icao text primary key,
    class char(1) not null,
    name text not null,
    nation text not null,
    city text not null
);

create table route (
    id serial primary key,
    distance integer not null,
    duration integer not null,
    departure text not null,
    stepover text not null,
    arrival text not null,
    foreign key (departure) references airport(icao),
    foreign key (stepover) references airport(icao),
    foreign key (arrival) references airport(icao)
);

create table parking (
    aircraft text not null,
    airport text not null,
    primary key (aircraft, airport),
    foreign key (aircraft) references aircraft_instance(plate),
    foreign key (airport) references airport(icao)
);

create table flight (
    id serial primary key not null,
    departure_time text not null,
    passengers_number text not null,
    route integer not null,
    aircraft text not null,
    commander integer not null,
    firstOfficial integer not null,
    foreign key (route) references route(id),
    foreign key (aircraft) references aircraft_instance(plate),
    foreign key (commander) references personal(id),
    foreign key (firstOfficial) references personal(id)
);

create table personal (
    id serial primary key,
    name text not null,
    lastName text not null,
    role text not null,
    abilitation text
);

create table flightAssistants (
    flight integer,
    assistant integer,
    foreign key (flight) references flight(id),
    foreign key (assistant) references personal(id)
);
