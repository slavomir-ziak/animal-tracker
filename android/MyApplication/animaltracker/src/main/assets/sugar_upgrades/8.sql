INSERT INTO CODE_LIST (ID, SOURCE, NAME, VALUE, ICON) values (38, 'APP', 'tranectRegion', 'Národný Park Muránska Planina', '');
INSERT INTO CODE_LIST (ID, SOURCE, NAME, VALUE, ICON) values (39, 'APP', 'tranectRegion', 'CHKO Poľana', '');
INSERT INTO CODE_LIST (ID, SOURCE, NAME, VALUE, ICON) values (40, 'APP', 'tranectRegion', 'Veporské Vrchy', '');

DELETE FROM SQLITE_SEQUENCE WHERE NAME = 'CODE_LIST';
INSERT INTO SQLITE_SEQUENCE (NAME, SEQ) VALUES ('CODE_LIST', 40);