--Remove existing tables:
DROP TABLE Appointments CASCADE CONSTRAINTS ;
DROP TABLE Contacts CASCADE CONSTRAINTS ;
DROP TABLE InsuranceProviders CASCADE CONSTRAINTS ;
DROP TABLE Medications CASCADE CONSTRAINTS ;
DROP TABLE Patients CASCADE CONSTRAINTS ;
DROP TABLE Patients_Prescriptions CASCADE CONSTRAINTS ;
DROP TABLE Prescriptions CASCADE CONSTRAINTS ;
DROP TABLE Roles CASCADE CONSTRAINTS ;
DROP TABLE Users CASCADE CONSTRAINTS ;

--Start with fresh sequences and associated triggers:
DROP SEQUENCE Appointments_AppointmentID_SEQ;
DROP SEQUENCE Contacts_ContactID_SEQ;
DROP SEQUENCE InsuranceProviders_InsurancePr;
DROP SEQUENCE Medications_MedicationID_SEQ;
DROP SEQUENCE Patients_PatientID_SEQ;
DROP SEQUENCE Patients_Prescriptions_Patient;
DROP SEQUENCE Prescriptions_PrescriptionID;
DROP SEQUENCE Roles_RoleID_SEQ;
DROP SEQUENCE Users_UserID_SEQ;

--Add tables and foreign key constraints;
CREATE TABLE Appointments
  (
    AppointmentID INTEGER NOT NULL,
    AppointmentDate  DATE NOT NULL ,
    AppointmentTime  NUMBER NOT NULL ,
    RoomNumber    INTEGER ,
    Reason        VARCHAR2 (500) ,
    FK_PatientID  INTEGER NOT NULL ,
    FK_UserID   INTEGER NOT NULL
  ) ;
ALTER TABLE Appointments ADD CONSTRAINT AppointmentID PRIMARY KEY ( AppointmentID ) ;

CREATE TABLE Contacts
  (
    ContactID              INTEGER NOT NULL ,
    FK_PatientID           INTEGER ,
    AptHouseNumber         NUMBER ,
    Street                 VARCHAR2 (50) ,
    City                   VARCHAR2 (50) ,
    PostalCode             VARCHAR2 (10) ,
    Province               VARCHAR2 (3) ,
    PhoneNumber            NUMBER ,
    Notes                  VARCHAR2 (200)
  ) ;
ALTER TABLE Contacts ADD CONSTRAINT ContactID PRIMARY KEY ( ContactID ) ;

CREATE TABLE InsuranceProviders
  (
    InsuranceProviderID   INTEGER NOT NULL ,
    InsuranceProviderName VARCHAR2 (50 CHAR) NOT NULL ,
    PrivateOrPublic       CHAR (1)
  ) ;
ALTER TABLE InsuranceProviders ADD CONSTRAINT InsuranceProviderID PRIMARY KEY ( InsuranceProviderID ) ;

CREATE TABLE Medications
  (
    MedicationID   INTEGER NOT NULL,
    MedicationName VARCHAR2 (50) NOT NULL
  ) ;
ALTER TABLE Medications ADD CONSTRAINT MedicationID PRIMARY KEY ( MedicationID ) ;

CREATE TABLE Patients
  (
    PatientID    INTEGER NOT NULL ,
    FirstName    VARCHAR2 (50 CHAR) NOT NULL ,
    LastName     VARCHAR2 (50 CHAR) NOT NULL ,
    Gender       VARCHAR2 (50) NOT NULL ,
    FK_InsuranceProviderID INTEGER,
    FK_PatientPrescriptionID INTEGER
  ) ;
ALTER TABLE Patients ADD CONSTRAINT PatientID PRIMARY KEY ( PatientID ) ;

CREATE TABLE Patients_Prescriptions
  (
    PatientPrescriptionID INTEGER NOT NULL ,
    FK_PatientID        INTEGER NOT NULL ,
    FK_PrescriptionID   INTEGER NOT NULL
    ) ;
ALTER TABLE Patients_Prescriptions ADD CONSTRAINT PatientPrescriptionD PRIMARY KEY ( PatientPrescriptionID ) ;

CREATE TABLE Prescriptions
  (
    PrescriptionID  INTEGER NOT NULL ,
    FK_MedicationID INTEGER NOT NULL,
    Doseage         INTEGER  ,
    MedStartDate    DATE  ,
    MedEndDate      DATE  ,
    DailyDoses      INTEGER  ,
    GenericOK       CHAR (1)
  ) ;
ALTER TABLE Prescriptions ADD CONSTRAINT PrescriptionID PRIMARY KEY ( PrescriptionID ) ;

CREATE TABLE Roles
  (
    RoleID   INTEGER NOT NULL ,
    RoleName VARCHAR2 (50) NOT NULL
  ) ;
ALTER TABLE Roles ADD CONSTRAINT RoleID PRIMARY KEY ( RoleID ) ;

CREATE TABLE Users
  (
    UserID    INTEGER NOT NULL ,
    UserName  VARCHAR2 (50) NOT NULL ,
    Password  VARCHAR2 (12) NOT NULL ,
    FK_RoleID INTEGER
  ) ;
ALTER TABLE Users ADD CONSTRAINT UserID PRIMARY KEY ( UserID ) ;

--Create auto-increment sequences and before-insert triggers:

CREATE SEQUENCE Appointments_AppointmentID_SEQ START WITH 1 NOCACHE ORDER ;
CREATE OR REPLACE TRIGGER Appointments_AppointmentID_TRG BEFORE
  INSERT ON Appointments FOR EACH ROW WHEN (NEW.AppointmentID IS NULL) BEGIN
  SELECT Appointments_AppointmentID_SEQ.NEXTVAL
  INTO :NEW.AppointmentID
  FROM DUAL;
END;
/

CREATE SEQUENCE Contacts_ContactID_SEQ START WITH 1 NOCACHE ORDER ;
CREATE OR REPLACE TRIGGER Contacts_ContactID_TRG BEFORE
  INSERT ON Contacts FOR EACH ROW WHEN (NEW.ContactID IS NULL) BEGIN
  SELECT Contacts_ContactID_SEQ.NEXTVAL INTO :NEW.ContactID FROM DUAL;
END;
/

CREATE SEQUENCE InsuranceProviders_InsurancePr START WITH 1 NOCACHE ORDER ;
CREATE OR REPLACE TRIGGER InsuranceProviders_InsurancePr BEFORE
  INSERT ON InsuranceProviders FOR EACH ROW WHEN (NEW.InsuranceProviderID IS NULL) BEGIN
  SELECT InsuranceProviders_InsurancePr.NEXTVAL
  INTO :NEW.InsuranceProviderID
  FROM DUAL;
END;
/

CREATE SEQUENCE Medications_MedicationID_SEQ START WITH 1 NOCACHE ORDER ;
CREATE OR REPLACE TRIGGER Medications_MedicationID_TRG BEFORE
  INSERT ON Medications FOR EACH ROW WHEN (NEW.MedicationID IS NULL) BEGIN
  SELECT Medications_MedicationID_SEQ.NEXTVAL INTO :NEW.MedicationID FROM DUAL;
END;
/

CREATE SEQUENCE Patients_PatientID_SEQ START WITH 1 NOCACHE ORDER ;
CREATE OR REPLACE TRIGGER Patients_PatientID_TRG BEFORE
  INSERT ON Patients FOR EACH ROW WHEN (NEW.PatientID IS NULL) BEGIN
  SELECT Patients_PatientID_SEQ.NEXTVAL INTO :NEW.PatientID FROM DUAL;
END;
/

CREATE SEQUENCE Patients_Prescriptions_Patient START WITH 1 NOCACHE ORDER ;
CREATE OR REPLACE TRIGGER Patients_Prescriptions_Patient BEFORE
  INSERT ON Patients_Prescriptions FOR EACH ROW WHEN (NEW.PatientPrescriptionID IS NULL) BEGIN
  SELECT Patients_Prescriptions_Patient.NEXTVAL
  INTO :NEW.PatientPrescriptionID
  FROM DUAL;
END;
/

CREATE SEQUENCE Prescriptions_PrescriptionID START WITH 1 NOCACHE ORDER ;
CREATE OR REPLACE TRIGGER Prescriptions_PrescriptionID BEFORE
  INSERT ON Prescriptions FOR EACH ROW WHEN (NEW.PrescriptionID IS NULL) BEGIN
  SELECT Prescriptions_PrescriptionID.NEXTVAL
  INTO :NEW.PrescriptionID
  FROM DUAL;
END;
/

CREATE SEQUENCE Roles_RoleID_SEQ START WITH 1 NOCACHE ORDER ;
CREATE OR REPLACE TRIGGER Roles_RoleID_TRG BEFORE
  INSERT ON Roles FOR EACH ROW WHEN (NEW.RoleID IS NULL) BEGIN
  SELECT Roles_RoleID_SEQ.NEXTVAL INTO :NEW.RoleID FROM DUAL;
END;
/

CREATE SEQUENCE Users_UserID_SEQ START WITH 1 NOCACHE ORDER ;
CREATE OR REPLACE TRIGGER Users_UserID_TRG BEFORE
  INSERT ON Users FOR EACH ROW WHEN (NEW.UserID IS NULL) BEGIN
  SELECT Users_UserID_SEQ.NEXTVAL INTO :NEW.UserID FROM DUAL;
END;
/
--End sequences and triggers

--Insert of display/test data:

--(RoleName)
INSERT INTO Roles (RoleName) VALUES ('Receptionist');
INSERT INTO Roles (RoleName) VALUES ('Doctor');

--(UserName, Password, FK_RoleID)
INSERT INTO Users (UserName, Password, FK_RoleID) VALUES('NurseRatchet', 1234, 2);
INSERT INTO Users (UserName, Password, FK_RoleID) VALUES('DoctorOctopus', 1234, 2);
INSERT INTO Users (UserName, Password, FK_RoleID) VALUES('PamBeasley', 1234, 1);

--(InsuranceProviderName, PrivateOrPublic)
INSERT INTO InsuranceProviders (InsuranceProviderName, PrivateOrPublic) VALUES('MSP', 1);
INSERT INTO InsuranceProviders (InsuranceProviderName, PrivateOrPublic) VALUES('Blue Cross', 0);

--(MedicationName)
INSERT INTO Medications (MedicationName) VALUES('Vioxx');
INSERT INTO Medications (MedicationName) VALUES('Phenformin');
INSERT INTO Medications (MedicationName) VALUES('Tamiflu');
INSERT INTO Medications (MedicationName) VALUES('Lobotosome');
INSERT INTO Medications (MedicationName) VALUES('Seldane');
INSERT INTO Medications (MedicationName) VALUES('Rezulin');
INSERT INTO Medications (MedicationName) VALUES('Darvocet');
INSERT INTO Medications (MedicationName) VALUES('Meridia');

--(FK_MedicationID, Doseage, MedStartDate, MedEndDate, DailyDoses, GenericOK)
INSERT INTO Prescriptions(FK_MedicationID, Doseage, MedStartDate, MedEndDate, DailyDoses, GenericOK) VALUES(1 , 50, '02-JAN-17', '02-AUG-17', NULL , 1);
INSERT INTO Prescriptions (FK_MedicationID, Doseage, MedStartDate, MedEndDate, DailyDoses, GenericOK) VALUES(1, NULL, '07-FEB-17', '22-JUL-17', 4 , 0);

--(FK_PatientID, AptHouseNumber, Street, City, PostalCode, Province, PhoneNumber, Notes)
INSERT INTO Contacts (FK_PatientID, AptHouseNumber, Street, City, PostalCode, Province, PhoneNumber, Notes) VALUES(1, 10, 'Dead End Road', 'Calgary', 'I8T 0P1', 'AB', 8881422412, 'Not the current residence');
INSERT INTO Contacts (FK_PatientID, AptHouseNumber, Street, City, PostalCode, Province, PhoneNumber, Notes) VALUES(2, 28, 'Windy Road', 'Vancouver', 'U8Q 1KF', 'BC', 4211164952, 'Buzzer 0512');
INSERT INTO Contacts (FK_PatientID, AptHouseNumber, Street, City, PostalCode, Province, PhoneNumber, Notes) VALUES(3, 512, 'Straight Road', 'Toronto', 'N3K 3Q7', 'ON', 8325323254,'Watch for the dog');
INSERT INTO Contacts (FK_PatientID, AptHouseNumber, Street, City, PostalCode, Province, PhoneNumber, Notes) VALUES(4, 22, 'Curvy Road', 'San Hose', 'F73 3K0', 'BC', 33294232659,'Are you sure San Hose is his real address?');

--(AppointmentDate, AppointmentTime, RoomNumber,Reason,FK_PatientID, FK_UserID)
INSERT INTO Appointments (AppointmentDate, AppointmentTime, RoomNumber,Reason,FK_PatientID, FK_UserID) VALUES('2017-04-29', 13.00, 2, 'Lobotomy', 1, 1);
INSERT INTO Appointments (AppointmentDate, AppointmentTime, RoomNumber,Reason,FK_PatientID, FK_UserID) VALUES('2017-03-29', 14.15, 2, 'Flu Symptoms. May also need lobotomy', 2, 1);
INSERT INTO Appointments (AppointmentDate, AppointmentTime, RoomNumber,Reason,FK_PatientID, FK_UserID) VALUES('2017-04-06', 12.00, 101, 'Re-education', 2, 1);
INSERT INTO Appointments (AppointmentDate, AppointmentTime, RoomNumber,Reason,FK_PatientID, FK_UserID) VALUES('2017-05-02', 12.30, 101, 'Need ink refill', 2, 2);

--(FirstName, LastName, Gender, FK_InsuranceProviderID, FK_PatientPrescriptionID)
INSERT INTO Patients (FirstName, LastName, Gender, FK_InsuranceProviderID, FK_PatientPrescriptionID) VALUES('Randle', 'McMurphy', 'Male', 1, 1);
INSERT INTO Patients (FirstName, LastName, Gender, FK_InsuranceProviderID, FK_PatientPrescriptionID) VALUES('Billy', 'Bibbit', 'Male', 1, 2);
INSERT INTO Patients (FirstName, LastName, Gender, FK_InsuranceProviderID, FK_PatientPrescriptionID) VALUES('Chief', 'Bromden', 'Male', 2, 2);
INSERT INTO Patients (FirstName, LastName, Gender, FK_InsuranceProviderID, FK_PatientPrescriptionID) VALUES('Winston', 'Smith', 'Male', 2, NULL);

--(FK_PatientID, FK_PrescriptionID)
INSERT INTO Patients_Prescriptions (FK_PatientID, FK_PrescriptionID) VALUES(1,1);
INSERT INTO Patients_Prescriptions (FK_PatientID, FK_PrescriptionID) VALUES(2,2);
INSERT INTO Patients_Prescriptions (FK_PatientID, FK_PrescriptionID) VALUES(2,2);

--End insert statements

--Add foreign key contraints:

ALTER TABLE Appointments ADD CONSTRAINT Appointment_Patient_FK FOREIGN KEY ( FK_PatientID ) REFERENCES Patients ( PatientID ) ON DELETE CASCADE;

ALTER TABLE Appointments ADD CONSTRAINT Appointment_User_FK FOREIGN KEY ( FK_UserID ) REFERENCES Users ( UserID ) ;

ALTER TABLE Prescriptions ADD CONSTRAINT FK_MedicationID FOREIGN KEY ( FK_MedicationID ) REFERENCES Medications ( MedicationID ) ON
DELETE CASCADE ;

ALTER TABLE Patients_Prescriptions ADD CONSTRAINT FK_PatientID FOREIGN KEY ( FK_PatientID ) REFERENCES Patients ( PatientID ) ON
DELETE CASCADE ;

ALTER TABLE Patients_Prescriptions ADD CONSTRAINT FK_PrescriptionID FOREIGN KEY ( FK_PrescriptionID ) REFERENCES Prescriptions ( PrescriptionID ) ;

ALTER TABLE Contacts ADD CONSTRAINT Contact_Patient_FK FOREIGN KEY ( FK_PatientID ) REFERENCES Patients ( PatientID ) ON
DELETE CASCADE ;

ALTER TABLE Users ADD CONSTRAINT FK_RoleID FOREIGN KEY ( FK_RoleID ) REFERENCES Roles ( RoleID ) ON
DELETE CASCADE ;
--End add foreign key constraints
