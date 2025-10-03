-- Troca o nome da coluna de check_quarto_id para quarto_id
ALTER TABLE `Hotel`.`copa_quarto`
  CHANGE COLUMN `check_quarto_id` `quarto_id` INT NOT NULL;

-- Remove a FK de produto_copa e adiciona em copa_quarto
ALTER TABLE `Hotel`.`produto_copa`
  DROP FOREIGN KEY `fk_produto_copa_copa_quarto1`,
  DROP COLUMN `copa_quarto_id`;

ALTER TABLE `Hotel`.`copa_quarto`
  ADD COLUMN `produto_copa_id` INT,
  ADD CONSTRAINT `fk_copa_quarto_produto_copa1`
    FOREIGN KEY (`produto_copa_id`)
    REFERENCES `Hotel`.`produto_copa` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION;