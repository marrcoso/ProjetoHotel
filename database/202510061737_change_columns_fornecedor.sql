ALTER TABLE fornecedor
    MODIFY cep CHAR(8),
    MODIFY fone CHAR(11),
    MODIFY fone2 CHAR(11) NULL,
    MODIFY rg VARCHAR(14) NULL,
    MODIFY cpf CHAR(11) NULL,
    MODIFY cnpj CHAR(14) NULL,
    MODIFY razao_social VARCHAR(100) NULL,
    MODIFY contato VARCHAR(100) NULL,
    MODIFY inscricao_estadual VARCHAR(15) NULL,
    MODIFY data_cadastro DATE NULL,
    MODIFY status CHAR(1) DEFAULT 'A',
    DROP COLUMN usuario,
    DROP COLUMN senha;

CREATE TRIGGER set_data_cadastro_fornecedor
BEFORE INSERT ON fornecedor
FOR EACH ROW
SET NEW.data_cadastro = IFNULL(NEW.data_cadastro, CURRENT_DATE);