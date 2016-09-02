DELETE FROM CODE_LIST;

INSERT INTO CODE_LIST (ID, SOURCE, NAME, VALUE, ICON) values (1 , 'APP', 'habitatTypes', 'Forest', '');
INSERT INTO CODE_LIST (ID, SOURCE, NAME, VALUE, ICON) values (2 , 'APP', 'habitatTypes', 'Track', '');
INSERT INTO CODE_LIST (ID, SOURCE, NAME, VALUE, ICON) values (3 , 'APP', 'habitatTypes', 'Meadow', '');
INSERT INTO CODE_LIST (ID, SOURCE, NAME, VALUE, ICON) values (4 , 'APP', 'habitatTypes', 'Field', '');
INSERT INTO CODE_LIST (ID, SOURCE, NAME, VALUE, ICON) values (5 , 'APP', 'habitatTypes', 'Walley', '');
INSERT INTO CODE_LIST (ID, SOURCE, NAME, VALUE, ICON) values (6 , 'APP', 'habitatTrackTypes', 'Asphalt road', '');
INSERT INTO CODE_LIST (ID, SOURCE, NAME, VALUE, ICON) values (7 , 'APP', 'habitatTrackTypes', 'Forest road', '');
INSERT INTO CODE_LIST (ID, SOURCE, NAME, VALUE, ICON) values (8 , 'APP', 'habitatTrackTypes', 'Tourist route', '');
INSERT INTO CODE_LIST (ID, SOURCE, NAME, VALUE, ICON) values (9 , 'APP', 'habitatForestAgeTypes', 'Old', '');
INSERT INTO CODE_LIST (ID, SOURCE, NAME, VALUE, ICON) values (10, 'APP', 'habitatForestAgeTypes', 'New', '');
INSERT INTO CODE_LIST (ID, SOURCE, NAME, VALUE, ICON) values (11, 'APP', 'habitatForestAgeTypes', 'Mixed', '');
INSERT INTO CODE_LIST (ID, SOURCE, NAME, VALUE, ICON) values (12, 'APP', 'habitatTreeTypes', 'Leafless', '');
INSERT INTO CODE_LIST (ID, SOURCE, NAME, VALUE, ICON) values (13, 'APP', 'habitatTreeTypes', 'Leafs', '');
INSERT INTO CODE_LIST (ID, SOURCE, NAME, VALUE, ICON) values (14, 'APP', 'habitatTreeTypes', 'Needles', '');
INSERT INTO CODE_LIST (ID, SOURCE, NAME, VALUE, ICON) values (15, 'APP', 'habitatTreeTypes', 'Mix', '');
INSERT INTO CODE_LIST (ID, SOURCE, NAME, VALUE, ICON) values (16, 'APP', 'habitatForestTypes', 'Coniferas', '');
INSERT INTO CODE_LIST (ID, SOURCE, NAME, VALUE, ICON) values (17, 'APP', 'habitatForestTypes', 'Pines', '');
INSERT INTO CODE_LIST (ID, SOURCE, NAME, VALUE, ICON) values (18, 'APP', 'habitatForestTypes', 'Oak', '');
INSERT INTO CODE_LIST (ID, SOURCE, NAME, VALUE, ICON) values (19, 'APP', 'findingTypes', 'Footprints', '');
INSERT INTO CODE_LIST (ID, SOURCE, NAME, VALUE, ICON) values (20, 'APP', 'findingTypes', 'Feces', '');
INSERT INTO CODE_LIST (ID, SOURCE, NAME, VALUE, ICON) values (21, 'APP', 'findingTypes', 'Urine', '');
INSERT INTO CODE_LIST (ID, SOURCE, NAME, VALUE, ICON) values (22, 'APP', 'findingTypes', 'Carcass', '');
INSERT INTO CODE_LIST (ID, SOURCE, NAME, VALUE, ICON) values (23, 'APP', 'findingSpeciesTypes', 'Wolf', '');
INSERT INTO CODE_LIST (ID, SOURCE, NAME, VALUE, ICON) values (24, 'APP', 'findingSpeciesTypes', 'Lynx', '');
INSERT INTO CODE_LIST (ID, SOURCE, NAME, VALUE, ICON) values (25, 'APP', 'findingSpeciesTypes', 'Red deer', '');
INSERT INTO CODE_LIST (ID, SOURCE, NAME, VALUE, ICON) values (26, 'APP', 'findingSpeciesTypes', 'Roe deer', '');
INSERT INTO CODE_LIST (ID, SOURCE, NAME, VALUE, ICON) values (27, 'APP', 'findingSpeciesTypes', 'Wild boar', '');
INSERT INTO CODE_LIST (ID, SOURCE, NAME, VALUE, ICON) values (28, 'APP', 'findingSpeciesTypes', 'Brown hare', '');
INSERT INTO CODE_LIST (ID, SOURCE, NAME, VALUE, ICON) values (29, 'APP', 'findingSpeciesTypes', 'Red fox', '');

DELETE FROM SQLITE_SEQUENCE WHERE NAME = 'CODE_LIST';
INSERT INTO SQLITE_SEQUENCE (NAME, SEQ) VALUES ('CODE_LIST', 29);
