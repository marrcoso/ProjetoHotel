-- Migration: Add Product and Historical Price to Copa Quarto
ALTER TABLE `Hotel`.`copa_quarto` 
ADD COLUMN `produto_id` INT NOT NULL,
ADD COLUMN `valor_unitario` FLOAT NOT NULL;

-- Update Constraints
ALTER TABLE `Hotel`.`copa_quarto`
ADD CONSTRAINT `fk_copa_quarto_produto1`
  FOREIGN KEY (`produto_id`)
  REFERENCES `Hotel`.`produto` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

CREATE INDEX `fk_copa_quarto_produto1_idx` ON `Hotel`.`copa_quarto` (`produto_id` ASC);

ALTER TABLE `Hotel`.`copa_quarto`
  CHANGE COLUMN `quarto_id` `check_quarto_id` INT NOT NULL;


