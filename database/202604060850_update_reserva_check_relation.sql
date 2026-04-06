ALTER TABLE `reserva` DROP FOREIGN KEY `fk_reserva_check1`;
ALTER TABLE `reserva` DROP COLUMN `check_id`;

ALTER TABLE `check` ADD COLUMN `reserva_id` INT NULL;
ALTER TABLE `check` ADD CONSTRAINT `fk_check_reserva1`
    FOREIGN KEY (`reserva_id`)
    REFERENCES `reserva` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION;

CREATE INDEX `fk_check_reserva1_idx` ON `check` (`reserva_id` ASC);
