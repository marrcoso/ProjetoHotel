ALTER TABLE funcionario
    MODIFY COLUMN cep CHAR(8),
    MODIFY COLUMN cpf CHAR(11),
    MODIFY COLUMN fone CHAR(11),
    MODIFY COLUMN fone2 CHAR(11),
    MODIFY data_cadastro DATE NULL,
    MODIFY status CHAR(1) DEFAULT 'A';

CREATE TRIGGER set_data_cadastro_funcionario
BEFORE INSERT ON funcionario
FOR EACH ROW
SET NEW.data_cadastro = IFNULL(NEW.data_cadastro, CURRENT_DATE);