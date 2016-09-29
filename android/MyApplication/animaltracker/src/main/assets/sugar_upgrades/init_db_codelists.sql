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


INSERT INTO CODE_LIST (ID, SOURCE, NAME, VALUE, ICON) values (12, 'APP', 'habitatTreeTypes', 'Oak', '');
INSERT INTO CODE_LIST (ID, SOURCE, NAME, VALUE, ICON) values (13, 'APP', 'habitatTreeTypes', 'Beech', '');
INSERT INTO CODE_LIST (ID, SOURCE, NAME, VALUE, ICON) values (14, 'APP', 'habitatTreeTypes', 'Spruce', '');
INSERT INTO CODE_LIST (ID, SOURCE, NAME, VALUE, ICON) values (15, 'APP', 'habitatTreeTypes', 'Pinus', '');

INSERT INTO CODE_LIST (ID, SOURCE, NAME, VALUE, ICON) values (16, 'APP', 'habitatForestTypes', 'Broad leaft', '');
INSERT INTO CODE_LIST (ID, SOURCE, NAME, VALUE, ICON) values (17, 'APP', 'habitatForestTypes', 'Coniferous', '');
INSERT INTO CODE_LIST (ID, SOURCE, NAME, VALUE, ICON) values (18, 'APP', 'habitatForestTypes', 'Mixed', '');

INSERT INTO CODE_LIST (ID, SOURCE, NAME, VALUE, ICON) values (19, 'APP', 'findingTypes', 'Carcass', '');
INSERT INTO CODE_LIST (ID, SOURCE, NAME, VALUE, ICON) values (20, 'APP', 'findingTypes', 'Feathers', '');
INSERT INTO CODE_LIST (ID, SOURCE, NAME, VALUE, ICON) values (21, 'APP', 'findingTypes', 'Nest', '');

INSERT INTO CODE_LIST (ID, SOURCE, NAME, VALUE, ICON) values (22, 'APP', 'findingSpeciesTypes', 'Wolf', '');
INSERT INTO CODE_LIST (ID, SOURCE, NAME, VALUE, ICON) values (23, 'APP', 'findingSpeciesTypes', 'Lynx', '');
INSERT INTO CODE_LIST (ID, SOURCE, NAME, VALUE, ICON) values (24, 'APP', 'findingSpeciesTypes', 'Red deer', '');
INSERT INTO CODE_LIST (ID, SOURCE, NAME, VALUE, ICON) values (25, 'APP', 'findingSpeciesTypes', 'Roe deer', '');
INSERT INTO CODE_LIST (ID, SOURCE, NAME, VALUE, ICON) values (26, 'APP', 'findingSpeciesTypes', 'Wild boar', '');
INSERT INTO CODE_LIST (ID, SOURCE, NAME, VALUE, ICON) values (27, 'APP', 'findingSpeciesTypes', 'Brown hare', '');
INSERT INTO CODE_LIST (ID, SOURCE, NAME, VALUE, ICON) values (28, 'APP', 'findingSpeciesTypes', 'Red fox', '');

INSERT INTO CODE_LIST (ID, SOURCE, NAME, VALUE, ICON) values (29, 'APP', 'findingAge', '1 day', '');
INSERT INTO CODE_LIST (ID, SOURCE, NAME, VALUE, ICON) values (30, 'APP', 'findingAge', '3 day', '');
INSERT INTO CODE_LIST (ID, SOURCE, NAME, VALUE, ICON) values (31, 'APP', 'findingAge', '5 day', '');
INSERT INTO CODE_LIST (ID, SOURCE, NAME, VALUE, ICON) values (32, 'APP', 'findingAge', '1 week', '');

INSERT INTO CODE_LIST (ID, SOURCE, NAME, VALUE, ICON) values (33, 'APP', 'habitatTreeTypes', 'Fruit', '');

INSERT INTO CODE_LIST (ID, SOURCE, NAME, VALUE, ICON) values (34, 'APP', 'fecesState', 'Fresh', '');
INSERT INTO CODE_LIST (ID, SOURCE, NAME, VALUE, ICON) values (35, 'APP', 'fecesState', 'Old', '');
INSERT INTO CODE_LIST (ID, SOURCE, NAME, VALUE, ICON) values (36, 'APP', 'fecesState', 'Decayed', '');

INSERT INTO CODE_LIST (ID, SOURCE, NAME, VALUE, ICON) values (37, 'APP', 'findingOtherEvidence', 'Urine', '');

INSERT INTO CODE_LIST (ID, SOURCE, NAME, VALUE, ICON) values (38, 'APP', 'tranectRegion', 'Národný Park Muránska Planina', '');
INSERT INTO CODE_LIST (ID, SOURCE, NAME, VALUE, ICON) values (39, 'APP', 'tranectRegion', 'CHKO Poľana', '');
INSERT INTO CODE_LIST (ID, SOURCE, NAME, VALUE, ICON) values (40, 'APP', 'tranectRegion', 'Veporské Vrchy', '');

DELETE FROM SQLITE_SEQUENCE WHERE NAME = 'CODE_LIST';
INSERT INTO SQLITE_SEQUENCE (NAME, SEQ) VALUES ('CODE_LIST', 40);
