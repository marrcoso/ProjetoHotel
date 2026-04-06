-- Fix table name and quarto_id nullability for Ordem de Serviço
USE `Hotel`;

-- 1. Rename the table
RENAME TABLE `oderm_servico` TO `ordem_servico`;

-- 2. Modify quarto_id to be nullable
ALTER TABLE `ordem_servico` 
MODIFY COLUMN `quarto_id` INT NULL;

-- 3. (Optional but recommended) Update Foreign Key names to match correct table name
-- Drop misspelled FKs
ALTER TABLE `ordem_servico` 
DROP FOREIGN KEY `fk_oderm_servico_check1`,
DROP FOREIGN KEY `fk_oderm_servico_servico1`,
DROP FOREIGN KEY `fk_oderm_servico_quarto1`;

-- Add correctly named FKs
ALTER TABLE `ordem_servico` 
ADD CONSTRAINT `fk_ordem_servico_check1`
  FOREIGN KEY (`check_id`)
  REFERENCES `check` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION,
ADD CONSTRAINT `fk_ordem_servico_servico1`
  FOREIGN KEY (`servico_id`)
  REFERENCES `servico` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION,
ADD CONSTRAINT `fk_ordem_servico_quarto1`
  FOREIGN KEY (`quarto_id`)
  REFERENCES `quarto` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

-- Create correctly named indexes
CREATE INDEX `fk_ordem_servico_check1_idx` ON `ordem_servico` (`check_id` ASC);
CREATE INDEX `fk_ordem_servico_servico1_idx` ON `ordem_servico` (`servico_id` ASC);
CREATE INDEX `fk_ordem_servico_quarto1_idx` ON `ordem_servico` (`quarto_id` ASC);
