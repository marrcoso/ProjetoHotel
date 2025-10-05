ALTER TABLE `Hotel`.`veiculo`
    MODIFY COLUMN `funcionario_id` INT NULL,
    MODIFY COLUMN `fornecedor_id` INT NULL,
    MODIFY COLUMN `hospede_id` INT NULL;

ALTER TABLE `Hotel`.`veiculo`
    ADD CONSTRAINT chk_proprietario_unico
    CHECK (
        (funcionario_id IS NOT NULL AND fornecedor_id IS NULL AND hospede_id IS NULL) OR
        (funcionario_id IS NULL AND fornecedor_id IS NOT NULL AND hospede_id IS NULL) OR
        (funcionario_id IS NULL AND fornecedor_id IS NULL AND hospede_id IS NOT NULL)
    );