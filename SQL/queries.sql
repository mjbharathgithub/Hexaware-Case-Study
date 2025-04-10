-- Incidents:
-- • IncidentID (Primary Key)
-- © Hexaware Technologies Limited. All rights www.hexaware.com
-- • IncidentType (e.g., Robbery, Homicide, Theft)
-- • IncidentDate
-- • Location (Geospatial Data: Latitude and Longitude)
-- • Description
-- • Status (e.g., Open, Closed, Under Investigation)
-- • VictimID (Foreign Key, linking to Victims)
-- • SuspectId(Foreign Key, Linking to Suspect)
-- drop table incidents,officers,victims,suspects,law_agencies,evidences,reports;

-- create database crime_analysis_reporting_system;
-- use crime_analysis_reporting_system;
create table Incidents(
	incident_id int primary key auto_increment,
    incident_type  set('Robbery','Homicie','Theft'),
    incident_date timestamp default current_timestamp,
    location varchar(255),
    description text,
    status set('Open','Closed','Under Investigation'),
    victim_id int,
    suspect_id int,
    foreign key(victim_id) references victims(victim_id),
    foreign key(suspect_id) references suspects(suspect_id)
    );



-- select @@foreign_key_checks;
-- set @@foreign_key_checks=0;




-- 17:18:49	create table Incidents(  incident_id int primary key,     incident_type  set('Robbery','Homicie','Theft'),     incident_date date default current_date,     location varchar(255),     description text,     status set('Open','Closed','Under Investigation'),     victim_id int,     suspect_id int,     foreign key(victim_id) references victims(victim_id),     foreign key(suspect_id) references suspects(supspect_id)     )	Error Code: 1064. You have an error in your SQL syntax; check the manual that corresponds to your MySQL server version for the right syntax to use near 'current_date,     location varchar(255),     description text,     status set('O' at line 4	0.000 sec


-- 2. Victims:
-- • VictimID (Primary Key)
-- • FirstName
-- • LastName
-- • DateOfBirth
-- • Gender
-- • Contact Information (e.g., Address, Phone Number)

create table victims(
	victim_id int primary key auto_increment,
    first_name varchar(255),
    last_name varchar(255),
    date_of_birth date default null,
    gender enum('Male','Female','Others'),
    contact_information text
    );
    
-- 3. Suspects:
-- • SuspectID (Primary Key)
-- • FirstName
-- • LastName
-- • DateOfBirth
-- • Gender
-- • Contact Information

create table suspects(
	suspect_id int primary key auto_increment,
    first_name varchar(255),
    last_name varchar(255),
    date_of_birth date default null,
    gender enum('Male','Female','Others'),
    contact_information text
    );
    
    
-- 4. Law Enforcement Agencies:
-- • AgencyID (Primary Key)
-- • AgencyName
-- • Jurisdiction
-- • Contact Information
-- • Officer(s) (Link to Officers within the agency)

create table law_agencies(
	agency_id int primary key auto_increment,
    Agency_name varchar(255),
    jurisdiction text,
    contact_information text,
    officer_id int,
    foreign key(officer_id) references officers(officer_id)
    );
    
    
-- 5. Officers:
-- © Hexaware Technologies Limited. All rights www.hexaware.com
-- • OfficerID (Primary Key)
-- • FirstName
-- • LastName
-- • BadgeNumber
-- • Rank
-- • Contact Information
-- • AgencyID (Foreign Key, linking to Law Enforcement Agencies)

create table officers(
	officer_id int primary key auto_increment,
    first_name varchar(255) not null,
    last_name varchar(255) not null,
    badge_number int not null,
    officer_rank int,
    contact_information text not null,
    agency_id int,
    foreign key(agency_id) references law_agencies(officer_id)
    );
    
    
-- 6. Evidence:
-- • EvidenceID (Primary Key)
-- • Description
-- • Location Found
-- • IncidentID (Foreign Key, linking to Incidents)

create table evidences(
	evidence_id int primary key auto_increment,
    description text,
    location_found varchar(255),
    incident_id int,
    foreign key(incident_id) references Incidents(incident_id));
    
   -- desc 
-- 7. Reports:
-- • ReportID (Primary Key)practice
-- • IncidentID (Foreign Key, linking to Incidents)
-- • ReportingOfficer (Foreign Key, linking to Officers)
-- • ReportDate
-- • ReportDetails
-- • Status (e.g., Draft, )

	create table reports(
    report_id int primary key auto_increment,
    incident_id int,
    reporting_officer_id int,
    report_date date,
    report_details text,
    status enum('Draft','Finalized'),
    foreign key(incident_id) references incidents(incident_id),
    foreign key(reporting_officer_id) references officers(officer_id)
    
    );
    use crime_analysis_reporting_system;
    select * from officers;
    
    desc officers;
    
    alter table officers
    modify column badge_number varchar(255);
    
    -- working on java part
    desc incidents;
    
    alter table incidents
    modify column incident_type SET('Theft', 'Assault', 'Robbery', 'Vandalism', 'Burglary', 'Traffic Violation', 'Chain Snatching', 'Mischief', 'Cheating',
    'Trespassing', 'Other');
    
    INSERT INTO law_agencies (agency_name, jurisdiction, contact_information) VALUES
('Tamil Nadu Police - Chennai Division', 'Chennai, Tamil Nadu', '044-23456789'),
('Tamil Nadu Police - Madurai District', 'Madurai, Tamil Nadu', '0452-2876543'),
('Karnataka State Police - Bangalore Central', 'Bangalore, Karnataka', '080-22943000'),
('Karnataka State Police - Mysore District', 'Mysore, Karnataka', '0821-2444800'),
('Telangana State Police - Hyderabad City', 'Hyderabad, Telangana', '040-27852345'),
('Telangana State Police - Cyberabad', 'Hyderabad (Cyberabad), Telangana', '040-23001234'),
('Maharashtra Police - Mumbai City', 'Mumbai, Maharashtra', '022-22633333'),
('Maharashtra Police - Thane Commissionerate', 'Thane, Maharashtra', '022-25368800'),
('Tamil Nadu Police - Coimbatore City', 'Coimbatore, Tamil Nadu', '0422-2302930'),
('Karnataka State Police - Mangalore City', 'Mangalore, Karnataka', '0824-2220500');

INSERT INTO officers (first_name, last_name, badge_number, officer_rank, contact_information, agency_id) VALUES
('Arun', 'Kumar', 'TN001', 3, 'arun.kumar@tnpolice.gov.in', 1),
('Priya', 'Devi', 'TN002', 4, 'priya.devi@tnpolice.gov.in', 2),
('Ramesh', 'Gowda', 'KA001', 2, 'ramesh.gowda@ksp.gov.in', 3),
('Shalini', 'Patel', 'KA003', 3, 'shalini.patel@ksp.gov.in', 4),
('Suresh', 'Reddy', 'TS005', 4, 'suresh.reddy@tsp.gov.in', 5),
('Deepika', 'Rao', 'TS008', 2, 'deepika.rao@tsp.gov.in', 6),
('Amit', 'Verma', 'MH010', 3, 'amit.verma@mahapolice.gov.in', 7),
('Sneha', 'Joshi', 'MH012', 4, 'sneha.joshi@mahapolice.gov.in', 8),
('Karthik', 'Rajan', 'TN005', 2, 'karthik.rajan@tnpolice.gov.in', 9),
('Divya', 'Hegde', 'KA007', 3, 'divya.hegde@ksp.gov.in', 10);

desc victims;

INSERT INTO victims (first_name, last_name, date_of_birth, gender, contact_information) VALUES
('Lakshmi', 'Narayanan', '1985-07-15', 'Female', 'lakshmi.n@email.com, 9876543210'),
('Govindan', 'Pillai', '1972-11-20', 'Male', 'govindan.p@email.com, 8765432109'),
('Smitha', 'Reddy', '1992-03-10', 'Female', 'smitha.r@email.com, 7654321098'),
('Prakash', 'Rao', '1988-09-01', 'Male', 'prakash.r@email.com, 6543210987'),
('Farah', 'Khan', '1978-05-25', 'Female', 'farah.k@email.com, 5432109876'),
('Rahul', 'Sharma', '1995-12-05', 'Male', 'rahul.s@email.com, 4321098765'),
('Anjali', 'Patil', '1983-01-30', 'Female', 'anjali.p@email.com, 3210987654'),
('Suresh', 'Yadav', '1970-06-18', 'Male', 'suresh.y@email.com, 2109876543'),
('Meena', 'Sundaram', '1998-04-02', 'Female', 'meena.s@email.com, 1098765432'),
('Kiran', 'Shetty', '1981-10-22', 'Male', 'kiran.s@email.com, 9012345678');

INSERT INTO suspects (first_name, last_name, date_of_birth, gender, contact_information) VALUES
('Vasanth', 'Kumar', '1990-02-10', 'Male', 'vasanth.k@email.com, 9988776655'),
('Divya', 'Menon', '1987-08-28', 'Female', 'divya.m@email.com, 8877665544'),
('Arjun', 'Naidu', '1993-05-12', 'Male', 'arjun.n@email.com, 7766554433'),
('Shweta', 'Verma', '1980-11-01', 'Female', 'shweta.v@email.com, 6655443322'),
('Imran', 'Ali', '1975-09-15', 'Male', 'imran.a@email.com, 5544332211'),
('Pooja', 'Gupta', '1997-01-20', 'Female', 'pooja.g@email.com, 4433221100'),
('Siddharth', 'Jain', '1982-07-05', 'Male', 'siddharth.j@email.com, 3322110099'),
('Kajal', 'Thakur', '1999-03-25', 'Female', 'kajal.t@email.com, 2211009988'),
('Ganesh', 'Achari', '1979-12-30', 'Male', 'ganesh.a@email.com, 1100998877'),
('Aishwarya', 'Rai', '1985-06-01', 'Female', 'aishwarya.r@email.com, 9123456789');
desc reports;
INSERT INTO reports (incident_id, reporting_officer_id, report_date, report_details, status) VALUES
(1, 1, '2025-04-05', 'Report filed for mobile theft.', 'Finalized'),
(2, 1, '2025-04-06', 'Assault case initial assessment.', 'Draft'),
(3, 3, '2025-04-03', 'Detailed report on jewelry shop robbery - awaiting review.', 'Draft'),
(4, 3, '2025-04-07', 'Vandalism incident reported by resident - initial notes.', 'Draft'),
(5, 5, '2025-04-02', 'Burglary case with inventory of missing items - confirmed.', 'Finalized'),
(6, 5, '2025-04-08', 'Initial report on hit and run, witnesses sought.', 'Draft'),
(7, 7, '2025-04-04', 'Chain snatching incident with description of suspect - ready for review.', 'Draft'),
(8, 7, '2025-04-09', 'Report on public mischief and property damage - approved.', 'Finalized'),
(9, 9, '2025-04-01', 'Online cheating complaint registered - under investigation.', 'Draft'),
(10, 9, '2025-04-06', 'Trespassing incident, suspect apprehended - case closed.', 'Finalized');

INSERT INTO evidences (incident_id, description, location_found) VALUES
(1, 'Broken screen protector', 'Theft location'),
(2, 'Scrapings from the wall', 'Assault location'),
(3, 'CCTV footage', 'Jewelry shop'),
(4, 'Paint chips', 'Near the damaged car'),
(5, 'Footprints outside the window', 'Burglary site'),
(6, '碎片 of broken headlight', 'Hit and run location'),
(7, 'Broken chain link', 'Snatching location'),
(8, 'Spray paint cans', 'Near damaged wall'),
(9, 'Email screenshots', 'Victim\'s computer'),
(10, 'Muddy footprints', 'Outside the property');


select * from reports;

alter table incidents
modify column incident_id int;
desc incidents;
    set @@foreign_key_checks =0;
    select * from incidents;
    INSERT INTO incidents (incident_type, incident_date, location, description, status, victim_id, suspect_id) VALUES
('Theft', '2025-04-05 10:00:00', 'T. Nagar, Chennai', 'Mobile phone stolen from pedestrian.', 'Open', 1, 1),
('Assault', '2025-04-06 14:30:00', 'Anna Salai, Chennai', 'Minor scuffle leading to injuries.', 'Closed', 2, 2),
('Robbery', '2025-04-03 21:15:00', 'MG Road, Bangalore', 'Jewelry shop looted by masked men.', 'Open', 3, 3),
('Vandalism', '2025-04-07 08:45:00', 'Koramangala, Bangalore', 'Car windows smashed overnight.', 'Open', 4, 4),
('Burglary', '2025-04-02 19:00:00', 'Jubilee Hills, Hyderabad', 'House broken into, valuables missing.', 'Closed', 5, 5),
('Traffic Violation', '2025-04-08 11:20:00', 'Banjara Hills, Hyderabad', 'Hit and run incident.', 'Open', 6, 6),
('Chain Snatching', '2025-04-04 16:55:00', 'Bandra, Mumbai', 'Gold chain snatched from elderly woman.', 'Open', 7, 7),
('Mischief', '2025-04-09 09:30:00', 'Dadar, Mumbai', 'Public property damaged by a group.', 'Open', 8, 8),
('Cheating', '2025-04-01 13:00:00', 'Adyar, Chennai', 'Online fraud case reported.', 'Open', 9, 9),
('Trespassing', '2025-04-06 22:00:00', 'Whitefield, Bangalore', 'Unauthorized entry into private property.', 'Closed', 10, 10);
