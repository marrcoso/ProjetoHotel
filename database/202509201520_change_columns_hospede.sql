ALTER TABLE hospede
    MODIFY cep CHAR(8),
    MODIFY fone CHAR(11),
    MODIFY fone2 CHAR(11) NULL,
    MODIFY rg VARCHAR(14) NULL,
    MODIFY cpf CHAR(11) NULL,
    MODIFY cnpj CHAR(11) NULL,
    MODIFY razao_social VARCHAR(100) NULL,
    MODIFY inscricao_estadual VARCHAR(15) NULL,
    MODIFY data_cadastro DATE NULL,
    MODIFY status CHAR(1) DEFAULT 'A';

CREATE TRIGGER set_data_cadastro_hospede
BEFORE INSERT ON hospede
FOR EACH ROW
SET NEW.data_cadastro = IFNULL(NEW.data_cadastro, CURRENT_DATE);