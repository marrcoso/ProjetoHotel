ALTER TABLE `check`
    DROP FOREIGN KEY `fk_check_check_quarto1`,
    DROP COLUMN `check_quarto_id`;

ALTER TABLE `check_quarto`
    ADD COLUMN `check_id` INT NOT NULL AFTER `id`,
    ADD CONSTRAINT `fk_check_quarto_check1`
        FOREIGN KEY (`check_id`) REFERENCES `check` (`id`)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION;