/*
SQLyog Ultimate v10.00 Beta1
MySQL - 5.5.38 : Database - project4.1
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`project4.1` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `project4.1`;

/*Table structure for table `admin_details` */

DROP TABLE IF EXISTS `admin_details`;

CREATE TABLE `admin_details` (
  `admin_details_id` int(11) NOT NULL AUTO_INCREMENT,
  `firstname` varchar(45) DEFAULT NULL,
  `lastname` varchar(45) DEFAULT NULL,
  `mobile` varchar(45) DEFAULT NULL,
  `gender_gender_id` int(11) NOT NULL,
  PRIMARY KEY (`admin_details_id`),
  KEY `fk_admin_details_gender1_idx` (`gender_gender_id`),
  CONSTRAINT `fk_admin_details_gender1` FOREIGN KEY (`gender_gender_id`) REFERENCES `gender` (`gender_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Table structure for table `administrator` */

DROP TABLE IF EXISTS `administrator`;

CREATE TABLE `administrator` (
  `administrator_id` int(11) NOT NULL AUTO_INCREMENT,
  `login_details_id` int(11) NOT NULL,
  `admin_details_id` int(11) NOT NULL,
  `profile_pic_id` int(11) NOT NULL,
  PRIMARY KEY (`administrator_id`),
  KEY `fk_administrator_login_details1_idx` (`login_details_id`),
  KEY `fk_administrator_admin_details1_idx` (`admin_details_id`),
  KEY `fk_administrator_profile_picture1_idx` (`profile_pic_id`),
  CONSTRAINT `fk_administrator_admin_details1` FOREIGN KEY (`admin_details_id`) REFERENCES `admin_details` (`admin_details_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_administrator_login_details1` FOREIGN KEY (`login_details_id`) REFERENCES `login_details` (`login_details_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_administrator_profile_picture1` FOREIGN KEY (`profile_pic_id`) REFERENCES `profile_picture` (`profile_pic_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Table structure for table `cab` */

DROP TABLE IF EXISTS `cab`;

CREATE TABLE `cab` (
  `cab_id` varchar(45) NOT NULL,
  `cost_per_km` double DEFAULT NULL,
  `service_provider_id` int(11) NOT NULL,
  `vehicle_model_id` int(11) NOT NULL,
  `vehicle_colour_id` int(11) NOT NULL,
  `vehicle_status_id` int(11) NOT NULL,
  PRIMARY KEY (`cab_id`),
  KEY `fk_cab_service_provider1_idx` (`service_provider_id`),
  KEY `fk_cab_vehicle_model1_idx` (`vehicle_model_id`),
  KEY `fk_cab_vehicle_colour1_idx` (`vehicle_colour_id`),
  KEY `fk_cab_vehical_status1_idx` (`vehicle_status_id`),
  CONSTRAINT `fk_cab_service_provider1` FOREIGN KEY (`service_provider_id`) REFERENCES `service_provider` (`service_provider_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_cab_vehical_status1` FOREIGN KEY (`vehicle_status_id`) REFERENCES `vehicle_status` (`vehicle_status_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_cab_vehicle_colour1` FOREIGN KEY (`vehicle_colour_id`) REFERENCES `vehicle_colour` (`vehicle_colour_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_cab_vehicle_model1` FOREIGN KEY (`vehicle_model_id`) REFERENCES `vehicle_model` (`vehicle_model_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `cab_driver` */

DROP TABLE IF EXISTS `cab_driver`;

CREATE TABLE `cab_driver` (
  `cab_driver_id` int(11) NOT NULL AUTO_INCREMENT,
  `cab_driver_details_cab_driver_details_id` int(11) NOT NULL,
  `profile_picture_profile_pic_id` int(11) NOT NULL,
  `service_provider_service_provider_id` int(11) NOT NULL,
  `cab_driver_location_cab_driver_location_id` int(11) NOT NULL,
  `login_details_login_details_id` int(11) NOT NULL,
  PRIMARY KEY (`cab_driver_id`),
  KEY `fk_cab_driver_cab_driver_details1_idx` (`cab_driver_details_cab_driver_details_id`),
  KEY `fk_cab_driver_profile_picture1_idx` (`profile_picture_profile_pic_id`),
  KEY `fk_cab_driver_service_provider1_idx` (`service_provider_service_provider_id`),
  KEY `fk_cab_driver_cab_driver_location1_idx` (`cab_driver_location_cab_driver_location_id`),
  KEY `fk_cab_driver_login_details1_idx` (`login_details_login_details_id`),
  CONSTRAINT `fk_cab_driver_cab_driver_details1` FOREIGN KEY (`cab_driver_details_cab_driver_details_id`) REFERENCES `cab_driver_details` (`cab_driver_details_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_cab_driver_cab_driver_location1` FOREIGN KEY (`cab_driver_location_cab_driver_location_id`) REFERENCES `cab_driver_location` (`cab_driver_location_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_cab_driver_login_details1` FOREIGN KEY (`login_details_login_details_id`) REFERENCES `login_details` (`login_details_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_cab_driver_profile_picture1` FOREIGN KEY (`profile_picture_profile_pic_id`) REFERENCES `profile_picture` (`profile_pic_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_cab_driver_service_provider1` FOREIGN KEY (`service_provider_service_provider_id`) REFERENCES `service_provider` (`service_provider_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

/*Table structure for table `cab_driver_details` */

DROP TABLE IF EXISTS `cab_driver_details`;

CREATE TABLE `cab_driver_details` (
  `cab_driver_details_id` int(11) NOT NULL AUTO_INCREMENT,
  `first_name` varchar(45) DEFAULT NULL,
  `last_name` varchar(45) DEFAULT NULL,
  `dob` varchar(45) DEFAULT NULL,
  `address1` varchar(45) DEFAULT NULL,
  `address2` varchar(45) DEFAULT NULL,
  `city_city_id` int(11) NOT NULL,
  PRIMARY KEY (`cab_driver_details_id`),
  KEY `fk_cab_driver_details_city1_idx` (`city_city_id`),
  CONSTRAINT `fk_cab_driver_details_city1` FOREIGN KEY (`city_city_id`) REFERENCES `city` (`city_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

/*Table structure for table `cab_driver_location` */

DROP TABLE IF EXISTS `cab_driver_location`;

CREATE TABLE `cab_driver_location` (
  `cab_driver_location_id` int(11) NOT NULL AUTO_INCREMENT,
  `lattitude` double DEFAULT NULL,
  `longitude` double DEFAULT NULL,
  PRIMARY KEY (`cab_driver_location_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

/*Table structure for table `city` */

DROP TABLE IF EXISTS `city`;

CREATE TABLE `city` (
  `city_id` int(11) NOT NULL AUTO_INCREMENT,
  `google_place_id` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`city_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

/*Table structure for table `destination` */

DROP TABLE IF EXISTS `destination`;

CREATE TABLE `destination` (
  `destination_id` int(11) NOT NULL AUTO_INCREMENT,
  `latitude` double DEFAULT NULL,
  `longitude` double DEFAULT NULL,
  PRIMARY KEY (`destination_id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;

/*Table structure for table `driver_contact_no` */

DROP TABLE IF EXISTS `driver_contact_no`;

CREATE TABLE `driver_contact_no` (
  `driver_cab_no_id` int(11) NOT NULL AUTO_INCREMENT,
  `contact_no` varchar(45) DEFAULT NULL,
  `cab_driver_cab_driver_id` int(11) NOT NULL,
  PRIMARY KEY (`driver_cab_no_id`),
  KEY `fk_driver_contact_no_cab_driver1_idx` (`cab_driver_cab_driver_id`),
  CONSTRAINT `fk_driver_contact_no_cab_driver1` FOREIGN KEY (`cab_driver_cab_driver_id`) REFERENCES `cab_driver` (`cab_driver_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `duration_months` */

DROP TABLE IF EXISTS `duration_months`;

CREATE TABLE `duration_months` (
  `duration_months_id` int(11) NOT NULL AUTO_INCREMENT,
  `months` int(11) DEFAULT NULL,
  PRIMARY KEY (`duration_months_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

/*Table structure for table `gender` */

DROP TABLE IF EXISTS `gender`;

CREATE TABLE `gender` (
  `gender_id` int(11) NOT NULL AUTO_INCREMENT,
  `type` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`gender_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

/*Table structure for table `leavingrequest` */

DROP TABLE IF EXISTS `leavingrequest`;

CREATE TABLE `leavingrequest` (
  `leavingRequestId` int(11) NOT NULL AUTO_INCREMENT,
  `reason` varchar(200) DEFAULT NULL,
  `requestStatusId` int(11) DEFAULT NULL,
  `cab_driver_id` int(11) DEFAULT NULL,
  `date_time` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`leavingRequestId`),
  KEY `fk_leavingRequest_has_requestStatus` (`requestStatusId`),
  KEY `fk_leavingRequest_has_cab_driver` (`cab_driver_id`),
  CONSTRAINT `fk_leavingRequest_has_cab_driver` FOREIGN KEY (`cab_driver_id`) REFERENCES `cab_driver` (`cab_driver_id`),
  CONSTRAINT `fk_leavingRequest_has_requestStatus` FOREIGN KEY (`requestStatusId`) REFERENCES `requeststatus` (`requestStatusId`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

/*Table structure for table `location_type` */

DROP TABLE IF EXISTS `location_type`;

CREATE TABLE `location_type` (
  `location_type_id` int(11) NOT NULL AUTO_INCREMENT,
  `location_type` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`location_type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `login_details` */

DROP TABLE IF EXISTS `login_details`;

CREATE TABLE `login_details` (
  `login_details_id` int(11) NOT NULL AUTO_INCREMENT,
  `email` varchar(100) DEFAULT NULL,
  `password` varchar(100) DEFAULT NULL,
  `user_type_id` int(11) NOT NULL,
  `last_update` timestamp NULL DEFAULT NULL,
  `login_status_login_status_id` int(11) NOT NULL,
  PRIMARY KEY (`login_details_id`),
  KEY `fk_login_details_user_type1_idx` (`user_type_id`),
  KEY `fk_login_details_login_status1_idx` (`login_status_login_status_id`),
  CONSTRAINT `fk_login_details_login_status1` FOREIGN KEY (`login_status_login_status_id`) REFERENCES `login_status` (`login_status_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_login_details_user_type1` FOREIGN KEY (`user_type_id`) REFERENCES `user_type` (`user_type_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

/*Table structure for table `login_status` */

DROP TABLE IF EXISTS `login_status`;

CREATE TABLE `login_status` (
  `login_status_id` int(11) NOT NULL AUTO_INCREMENT,
  `login_status` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`login_status_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

/*Table structure for table `logo` */

DROP TABLE IF EXISTS `logo`;

CREATE TABLE `logo` (
  `logo_id` int(11) NOT NULL AUTO_INCREMENT,
  `logo_url` varchar(150) DEFAULT NULL,
  PRIMARY KEY (`logo_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

/*Table structure for table `membership_duration` */

DROP TABLE IF EXISTS `membership_duration`;

CREATE TABLE `membership_duration` (
  `membership_duration_id` int(11) NOT NULL AUTO_INCREMENT,
  `duration_months_id` int(11) NOT NULL,
  `duration_fee` double DEFAULT NULL,
  `membership_plan_id` int(11) NOT NULL,
  PRIMARY KEY (`membership_duration_id`),
  KEY `fk_membership_duration_membership_plan1_idx` (`membership_plan_id`),
  KEY `fk_membership_duration_duration_months1_idx` (`duration_months_id`),
  CONSTRAINT `fk_membership_duration_duration_months1` FOREIGN KEY (`duration_months_id`) REFERENCES `duration_months` (`duration_months_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_membership_duration_membership_plan1` FOREIGN KEY (`membership_plan_id`) REFERENCES `membership_plan` (`membership_plan_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

/*Table structure for table `membership_payment_history` */

DROP TABLE IF EXISTS `membership_payment_history`;

CREATE TABLE `membership_payment_history` (
  `membership_payment_id` int(11) NOT NULL AUTO_INCREMENT,
  `paypal_transaction_id` varchar(250) DEFAULT NULL,
  `paid_amount` double DEFAULT NULL,
  `paypal_fee` double DEFAULT NULL,
  `currency_rate` double DEFAULT NULL,
  `date_time` timestamp NULL DEFAULT NULL,
  `membership_upgrade_id` int(11) NOT NULL,
  `payment_status_id` int(11) NOT NULL,
  PRIMARY KEY (`membership_payment_id`),
  KEY `fk_membership_payment_membership_upgrades1_idx` (`membership_upgrade_id`),
  KEY `fk_membership_payment_payment_stage1_idx` (`payment_status_id`),
  CONSTRAINT `fk_membership_payment_membership_upgrades1` FOREIGN KEY (`membership_upgrade_id`) REFERENCES `membership_upgrade_history` (`membership_upgrade_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_membership_payment_payment_stage1` FOREIGN KEY (`payment_status_id`) REFERENCES `payment_status` (`payment_status_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

/*Table structure for table `membership_plan` */

DROP TABLE IF EXISTS `membership_plan`;

CREATE TABLE `membership_plan` (
  `membership_plan_id` int(11) NOT NULL AUTO_INCREMENT,
  `membership_type_id` int(11) NOT NULL,
  `membership_plan_name` varchar(45) DEFAULT NULL,
  `cabs` int(11) DEFAULT NULL,
  `drivers` int(11) DEFAULT NULL,
  `co_op_agreements` int(11) DEFAULT NULL,
  `per_month` double DEFAULT NULL,
  PRIMARY KEY (`membership_plan_id`),
  KEY `fk_membership_plan_membership_type1_idx` (`membership_type_id`),
  CONSTRAINT `fk_membership_plan_membership_type1` FOREIGN KEY (`membership_type_id`) REFERENCES `membership_type` (`membership_type_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

/*Table structure for table `membership_status` */

DROP TABLE IF EXISTS `membership_status`;

CREATE TABLE `membership_status` (
  `membership_status_id` int(11) NOT NULL AUTO_INCREMENT,
  `membership_status` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`membership_status_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

/*Table structure for table `membership_type` */

DROP TABLE IF EXISTS `membership_type`;

CREATE TABLE `membership_type` (
  `membership_type_id` int(11) NOT NULL AUTO_INCREMENT,
  `membership` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`membership_type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

/*Table structure for table `membership_upgrade_history` */

DROP TABLE IF EXISTS `membership_upgrade_history`;

CREATE TABLE `membership_upgrade_history` (
  `membership_upgrade_id` int(11) NOT NULL AUTO_INCREMENT,
  `activated_datetime` timestamp NULL DEFAULT NULL,
  `service_provider_service_provider_id` int(11) NOT NULL,
  `membership_plan_id` int(11) NOT NULL,
  `expires_datetime` timestamp NULL DEFAULT NULL,
  `membership_duration_id` int(11) NOT NULL,
  PRIMARY KEY (`membership_upgrade_id`),
  KEY `fk_membership_upgrades_service_provider1_idx` (`service_provider_service_provider_id`),
  KEY `fk_membership_upgrades_membership_plan1_idx` (`membership_plan_id`),
  KEY `fk_membership_upgrade_history_membership_duration1_idx` (`membership_duration_id`),
  CONSTRAINT `fk_membership_upgrades_membership_plan1` FOREIGN KEY (`membership_plan_id`) REFERENCES `membership_plan` (`membership_plan_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_membership_upgrades_service_provider1` FOREIGN KEY (`service_provider_service_provider_id`) REFERENCES `service_provider` (`service_provider_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_membership_upgrade_history_membership_duration1` FOREIGN KEY (`membership_duration_id`) REFERENCES `membership_duration` (`membership_duration_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

/*Table structure for table `message` */

DROP TABLE IF EXISTS `message`;

CREATE TABLE `message` (
  `msg_id` int(11) NOT NULL AUTO_INCREMENT,
  `msg_from` int(11) DEFAULT NULL,
  `msg_to` int(11) DEFAULT NULL,
  `reply_for` int(11) DEFAULT NULL,
  `subject` varchar(45) DEFAULT NULL,
  `content` text,
  `msg_type_id` int(11) NOT NULL,
  `msg_status_id` int(11) NOT NULL,
  `msg_priority_id` int(11) NOT NULL,
  `date_time` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`msg_id`),
  KEY `fk_message_msg_type1_idx` (`msg_type_id`),
  KEY `fk_message_msg_status1_idx` (`msg_status_id`),
  KEY `fk_message_msg_priority1_idx` (`msg_priority_id`),
  KEY `fk_msg_has_reply_for` (`reply_for`),
  KEY `fk_message_has_msg_from` (`msg_from`),
  KEY `fk_message_has_msg_to` (`msg_to`),
  CONSTRAINT `fk_message_has_msg_from` FOREIGN KEY (`msg_from`) REFERENCES `login_details` (`login_details_id`),
  CONSTRAINT `fk_message_has_msg_to` FOREIGN KEY (`msg_to`) REFERENCES `login_details` (`login_details_id`),
  CONSTRAINT `fk_message_msg_priority1` FOREIGN KEY (`msg_priority_id`) REFERENCES `msg_priority` (`msg_priority_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_message_msg_status1` FOREIGN KEY (`msg_status_id`) REFERENCES `msg_status` (`msg_status_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_message_msg_type1` FOREIGN KEY (`msg_type_id`) REFERENCES `msg_type` (`msg_type_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_msg_has_reply_for` FOREIGN KEY (`reply_for`) REFERENCES `message` (`msg_id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8;

/*Table structure for table `msg_priority` */

DROP TABLE IF EXISTS `msg_priority`;

CREATE TABLE `msg_priority` (
  `msg_priority_id` int(11) NOT NULL AUTO_INCREMENT,
  `msg_priority` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`msg_priority_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

/*Table structure for table `msg_status` */

DROP TABLE IF EXISTS `msg_status`;

CREATE TABLE `msg_status` (
  `msg_status_id` int(11) NOT NULL AUTO_INCREMENT,
  `msg_status` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`msg_status_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

/*Table structure for table `msg_type` */

DROP TABLE IF EXISTS `msg_type`;

CREATE TABLE `msg_type` (
  `msg_type_id` int(11) NOT NULL AUTO_INCREMENT,
  `msg_type` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`msg_type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

/*Table structure for table `order_status` */

DROP TABLE IF EXISTS `order_status`;

CREATE TABLE `order_status` (
  `order_status_id` int(11) NOT NULL AUTO_INCREMENT,
  `order_status` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`order_status_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

/*Table structure for table `outside_payout` */

DROP TABLE IF EXISTS `outside_payout`;

CREATE TABLE `outside_payout` (
  `outside_payout_id` int(11) NOT NULL AUTO_INCREMENT,
  `date_time` timestamp NULL DEFAULT NULL,
  `description` text,
  `payout_amount` double DEFAULT NULL,
  `administrator_id` int(11) NOT NULL,
  `service_provider_id` int(11) NOT NULL,
  `payout_status_id` int(11) NOT NULL,
  PRIMARY KEY (`outside_payout_id`),
  KEY `fk_outside_payout_administrator1_idx` (`administrator_id`),
  KEY `fk_outside_payout_service_provider1_idx` (`service_provider_id`),
  KEY `fk_outside_payout_payout_status1_idx` (`payout_status_id`),
  CONSTRAINT `fk_outside_payout_administrator1` FOREIGN KEY (`administrator_id`) REFERENCES `administrator` (`administrator_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_outside_payout_payout_status1` FOREIGN KEY (`payout_status_id`) REFERENCES `payout_status` (`payout_status_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_outside_payout_service_provider1` FOREIGN KEY (`service_provider_id`) REFERENCES `service_provider` (`service_provider_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `paid_history` */

DROP TABLE IF EXISTS `paid_history`;

CREATE TABLE `paid_history` (
  `paid_history_id` int(11) NOT NULL AUTO_INCREMENT,
  `pay_later_service_id` int(11) NOT NULL,
  `paid_amount` double NOT NULL,
  `due_amount` double NOT NULL,
  `date_time` timestamp NULL DEFAULT NULL,
  `payment_method_id` int(11) NOT NULL,
  `payment_status_id` int(11) NOT NULL,
  PRIMARY KEY (`paid_history_id`),
  KEY `fk_paid_history_pay_later_service1_idx` (`pay_later_service_id`),
  KEY `fk_paid_history_has_payment_method_idx` (`payment_method_id`),
  KEY `fk_paid_history_payment_status1_idx` (`payment_status_id`),
  CONSTRAINT `fk_paid_history_has_payment_method` FOREIGN KEY (`payment_method_id`) REFERENCES `payment_method` (`payment_method_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_paid_history_payment_status1` FOREIGN KEY (`payment_status_id`) REFERENCES `payment_status` (`payment_status_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_paid_history_pay_later_service1` FOREIGN KEY (`pay_later_service_id`) REFERENCES `pay_later_service` (`pay_later_service_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `pay_later_item` */

DROP TABLE IF EXISTS `pay_later_item`;

CREATE TABLE `pay_later_item` (
  `pay_later_item_id` int(11) NOT NULL AUTO_INCREMENT,
  `pay_later_service_id` int(11) NOT NULL,
  `service_invoice_id` int(11) NOT NULL,
  `date_time` timestamp NULL DEFAULT NULL,
  `is_paid` tinyint(1) NOT NULL,
  PRIMARY KEY (`pay_later_item_id`),
  KEY `pay_later_item_has_pay_later_service_idx` (`pay_later_service_id`),
  KEY `pay_later_item_has_service_invoice_idx` (`service_invoice_id`),
  CONSTRAINT `pay_later_item_has_pay_later_service` FOREIGN KEY (`pay_later_service_id`) REFERENCES `pay_later_service` (`pay_later_service_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `pay_later_item_has_service_invoice` FOREIGN KEY (`service_invoice_id`) REFERENCES `service_invoice` (`service_invoice_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `pay_later_service` */

DROP TABLE IF EXISTS `pay_later_service`;

CREATE TABLE `pay_later_service` (
  `pay_later_service_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `service_provider_id` int(11) NOT NULL,
  `basic_payment` double NOT NULL,
  `pay_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `agreed_date` timestamp NULL DEFAULT NULL,
  `current_balance` double NOT NULL,
  `service_duration_id` int(11) NOT NULL,
  `expire_date` timestamp NULL DEFAULT NULL,
  `service_status_id` int(11) NOT NULL,
  PRIMARY KEY (`pay_later_service_id`),
  KEY `fk_pay_later_service_user1_idx` (`user_id`),
  KEY `fk_pay_later_service_service_provider1_idx` (`service_provider_id`),
  KEY `fk_pay_later_service_service_duration1_idx` (`service_duration_id`),
  KEY `fk_pay_later_service_service_status1_idx` (`service_status_id`),
  CONSTRAINT `fk_pay_later_service_service_duration1` FOREIGN KEY (`service_duration_id`) REFERENCES `service_duration` (`service_duration_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_pay_later_service_service_provider1` FOREIGN KEY (`service_provider_id`) REFERENCES `service_provider` (`service_provider_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_pay_later_service_service_status1` FOREIGN KEY (`service_status_id`) REFERENCES `service_status` (`service_status_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_pay_later_service_user1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `payment_info_status` */

DROP TABLE IF EXISTS `payment_info_status`;

CREATE TABLE `payment_info_status` (
  `payment_info_status_id` int(11) NOT NULL AUTO_INCREMENT,
  `payment_info_status` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`payment_info_status_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `payment_method` */

DROP TABLE IF EXISTS `payment_method`;

CREATE TABLE `payment_method` (
  `payment_method_id` int(11) NOT NULL AUTO_INCREMENT,
  `payment_method` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`payment_method_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Table structure for table `payment_option` */

DROP TABLE IF EXISTS `payment_option`;

CREATE TABLE `payment_option` (
  `payment_option_id` int(11) NOT NULL AUTO_INCREMENT,
  `account_id` varchar(100) DEFAULT NULL,
  `payment_method_id` int(11) NOT NULL,
  `priority_order_id` int(11) NOT NULL,
  `payment_info_status_id` int(11) NOT NULL,
  `login_details_login_details_id` int(11) NOT NULL,
  PRIMARY KEY (`payment_option_id`),
  KEY `fk_payment_info_payment_method1_idx` (`payment_method_id`),
  KEY `fk_payment_info_priority_order1_idx` (`priority_order_id`),
  KEY `fk_payment_info_payment_info_stage1_idx` (`payment_info_status_id`),
  KEY `fk_payment_option_login_details1_idx` (`login_details_login_details_id`),
  CONSTRAINT `fk_payment_info_payment_info_stage1` FOREIGN KEY (`payment_info_status_id`) REFERENCES `payment_info_status` (`payment_info_status_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_payment_info_payment_method1` FOREIGN KEY (`payment_method_id`) REFERENCES `payment_method` (`payment_method_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_payment_info_priority_order1` FOREIGN KEY (`priority_order_id`) REFERENCES `priority_order` (`priority_order_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_payment_option_login_details1` FOREIGN KEY (`login_details_login_details_id`) REFERENCES `login_details` (`login_details_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `payment_status` */

DROP TABLE IF EXISTS `payment_status`;

CREATE TABLE `payment_status` (
  `payment_status_id` int(11) NOT NULL AUTO_INCREMENT,
  `payment_status` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`payment_status_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

/*Table structure for table `payout_status` */

DROP TABLE IF EXISTS `payout_status`;

CREATE TABLE `payout_status` (
  `payout_status_id` int(11) NOT NULL AUTO_INCREMENT,
  `payout_status` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`payout_status_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

/*Table structure for table `priority_order` */

DROP TABLE IF EXISTS `priority_order`;

CREATE TABLE `priority_order` (
  `priority_order_id` int(11) NOT NULL AUTO_INCREMENT,
  `priority` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`priority_order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `profile_picture` */

DROP TABLE IF EXISTS `profile_picture`;

CREATE TABLE `profile_picture` (
  `profile_pic_id` int(11) NOT NULL AUTO_INCREMENT,
  `profile_pic_url` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`profile_pic_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

/*Table structure for table `requeststatus` */

DROP TABLE IF EXISTS `requeststatus`;

CREATE TABLE `requeststatus` (
  `requestStatusId` int(11) NOT NULL AUTO_INCREMENT,
  `requestStatus` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`requestStatusId`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

/*Table structure for table `service_details` */

DROP TABLE IF EXISTS `service_details`;

CREATE TABLE `service_details` (
  `service_details_id` int(11) NOT NULL AUTO_INCREMENT,
  `cost_per_km` double DEFAULT NULL,
  `coverage_area` double DEFAULT NULL,
  `minimum_distance` double DEFAULT NULL,
  `last_updated` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`service_details_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

/*Table structure for table `service_duration` */

DROP TABLE IF EXISTS `service_duration`;

CREATE TABLE `service_duration` (
  `service_duration_id` int(11) NOT NULL AUTO_INCREMENT,
  `description` varchar(45) NOT NULL,
  `value` int(11) NOT NULL,
  PRIMARY KEY (`service_duration_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `service_invoice` */

DROP TABLE IF EXISTS `service_invoice`;

CREATE TABLE `service_invoice` (
  `service_invoice_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_user_id` int(11) NOT NULL,
  `date_time` timestamp NULL DEFAULT NULL,
  `total_amount` double DEFAULT NULL,
  `discount` double DEFAULT NULL,
  `net_amount` double DEFAULT NULL,
  `service_invoice_status_id` int(11) NOT NULL,
  PRIMARY KEY (`service_invoice_id`),
  KEY `fk_service_invoice_user1_idx` (`user_user_id`),
  KEY `fk_service_invoice_service_invoice_stage1_idx` (`service_invoice_status_id`),
  CONSTRAINT `fk_service_invoice_service_invoice_stage1` FOREIGN KEY (`service_invoice_status_id`) REFERENCES `service_invoice_status` (`service_invoice_status_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_service_invoice_user1` FOREIGN KEY (`user_user_id`) REFERENCES `user` (`user_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;

/*Table structure for table `service_invoice_status` */

DROP TABLE IF EXISTS `service_invoice_status`;

CREATE TABLE `service_invoice_status` (
  `service_invoice_status_id` int(11) NOT NULL AUTO_INCREMENT,
  `service_invoice_status` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`service_invoice_status_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

/*Table structure for table `service_order` */

DROP TABLE IF EXISTS `service_order`;

CREATE TABLE `service_order` (
  `service_order_id` int(11) NOT NULL AUTO_INCREMENT,
  `service_invoice_id` int(11) NOT NULL,
  `start_point_id` int(11) NOT NULL,
  `destination_id` int(11) NOT NULL,
  `km` double DEFAULT NULL,
  `cost_per_km` double DEFAULT NULL,
  `total` double DEFAULT NULL,
  `order_status_id` int(11) NOT NULL,
  `service_provider_id` int(11) NOT NULL,
  `ordered_at` timestamp NULL DEFAULT NULL,
  `scheduled_date_time` timestamp NULL DEFAULT NULL,
  `cab_id` varchar(45) DEFAULT NULL,
  `cab_driver_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`service_order_id`),
  KEY `fk_service_order_service_invoice1_idx` (`service_invoice_id`),
  KEY `fk_service_order_service_order_stage1_idx` (`order_status_id`),
  KEY `fk_service_order_service_provider1_idx` (`service_provider_id`),
  KEY `fk_service_order_cab1_idx` (`cab_id`),
  KEY `fk_service_order_cab_driver1_idx` (`cab_driver_id`),
  KEY `fk_service_order_start_point1_idx` (`start_point_id`),
  KEY `fk_service_order_destination1_idx` (`destination_id`),
  CONSTRAINT `fk_service_order_cab1` FOREIGN KEY (`cab_id`) REFERENCES `cab` (`cab_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_service_order_cab_driver1` FOREIGN KEY (`cab_driver_id`) REFERENCES `cab_driver` (`cab_driver_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_service_order_destination1` FOREIGN KEY (`destination_id`) REFERENCES `destination` (`destination_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_service_order_service_invoice1` FOREIGN KEY (`service_invoice_id`) REFERENCES `service_invoice` (`service_invoice_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_service_order_service_order_stage1` FOREIGN KEY (`order_status_id`) REFERENCES `order_status` (`order_status_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_service_order_service_provider1` FOREIGN KEY (`service_provider_id`) REFERENCES `service_provider` (`service_provider_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_service_order_start_point1` FOREIGN KEY (`start_point_id`) REFERENCES `start_point` (`start_point_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;

/*Table structure for table `service_payment_history` */

DROP TABLE IF EXISTS `service_payment_history`;

CREATE TABLE `service_payment_history` (
  `service_payment_id` int(11) NOT NULL AUTO_INCREMENT,
  `transaction_id` varchar(200) DEFAULT NULL,
  `service_invoice_service_invoice_id` int(11) NOT NULL,
  `currency_rate` double DEFAULT NULL,
  `paid_amount` double DEFAULT NULL,
  `paypal_fee` double DEFAULT NULL,
  `due_amount` double DEFAULT NULL,
  `date_time` timestamp NULL DEFAULT NULL,
  `payment_stage_payment_stage_id` int(11) NOT NULL,
  `payment_method_id` int(11) NOT NULL,
  PRIMARY KEY (`service_payment_id`),
  KEY `fk_service_payment_service_invoice1_idx` (`service_invoice_service_invoice_id`),
  KEY `fk_service_payment_payment_stage1_idx` (`payment_stage_payment_stage_id`),
  KEY `fk_service_payment_history_has_payment_method_idx` (`payment_method_id`),
  CONSTRAINT `fk_service_payment_history_has_payment_method` FOREIGN KEY (`payment_method_id`) REFERENCES `payment_method` (`payment_method_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_service_payment_payment_stage1` FOREIGN KEY (`payment_stage_payment_stage_id`) REFERENCES `payment_status` (`payment_status_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_service_payment_service_invoice1` FOREIGN KEY (`service_invoice_service_invoice_id`) REFERENCES `service_invoice` (`service_invoice_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

/*Table structure for table `service_provider` */

DROP TABLE IF EXISTS `service_provider`;

CREATE TABLE `service_provider` (
  `service_provider_id` int(11) NOT NULL AUTO_INCREMENT,
  `service_provider_name` varchar(45) DEFAULT NULL,
  `service_details_id` int(11) DEFAULT NULL,
  `logo_logo_id` int(11) NOT NULL,
  `service_provider_location_details_id` int(11) DEFAULT NULL,
  `login_details_id` int(11) NOT NULL,
  `membership_status_id` int(11) DEFAULT NULL,
  `contact_no` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`service_provider_id`),
  KEY `fk_service_provider_service_details1_idx` (`service_details_id`),
  KEY `fk_service_provider_logo1_idx` (`logo_logo_id`),
  KEY `fk_service_provider_service_provider_location_details1_idx` (`service_provider_location_details_id`),
  KEY `fk_service_provider_login_details1_idx` (`login_details_id`),
  KEY `fk_service_provider_has_membership_status` (`membership_status_id`),
  CONSTRAINT `fk_service_provider_has_membership_status` FOREIGN KEY (`membership_status_id`) REFERENCES `membership_status` (`membership_status_id`),
  CONSTRAINT `fk_service_provider_login_details1` FOREIGN KEY (`login_details_id`) REFERENCES `login_details` (`login_details_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_service_provider_logo1` FOREIGN KEY (`logo_logo_id`) REFERENCES `logo` (`logo_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_service_provider_service_details1` FOREIGN KEY (`service_details_id`) REFERENCES `service_details` (`service_details_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_service_provider_service_provider_location_details1` FOREIGN KEY (`service_provider_location_details_id`) REFERENCES `service_provider_location_details` (`service_provider_location_details_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

/*Table structure for table `service_provider_location_details` */

DROP TABLE IF EXISTS `service_provider_location_details`;

CREATE TABLE `service_provider_location_details` (
  `service_provider_location_details_id` int(11) NOT NULL AUTO_INCREMENT,
  `address1` varchar(45) DEFAULT NULL,
  `address2` varchar(45) DEFAULT NULL,
  `lattitude` double DEFAULT NULL,
  `longitude` double DEFAULT NULL,
  `city_city_id` int(11) NOT NULL,
  PRIMARY KEY (`service_provider_location_details_id`),
  KEY `fk_service_provider_location_details_city1_idx` (`city_city_id`),
  CONSTRAINT `fk_service_provider_location_details_city1` FOREIGN KEY (`city_city_id`) REFERENCES `city` (`city_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

/*Table structure for table `service_status` */

DROP TABLE IF EXISTS `service_status`;

CREATE TABLE `service_status` (
  `service_status_id` int(11) NOT NULL AUTO_INCREMENT,
  `service_status` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`service_status_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `start_point` */

DROP TABLE IF EXISTS `start_point`;

CREATE TABLE `start_point` (
  `start_point_id` int(11) NOT NULL AUTO_INCREMENT,
  `latitude` double DEFAULT NULL,
  `longitude` double DEFAULT NULL,
  PRIMARY KEY (`start_point_id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_details_user_details_id` int(11) NOT NULL,
  `login_details_login_details_id` int(11) NOT NULL,
  PRIMARY KEY (`user_id`),
  KEY `fk_user_user_details1_idx` (`user_details_user_details_id`),
  KEY `fk_user_login_details1_idx` (`login_details_login_details_id`),
  CONSTRAINT `fk_user_login_details1` FOREIGN KEY (`login_details_login_details_id`) REFERENCES `login_details` (`login_details_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_user_details1` FOREIGN KEY (`user_details_user_details_id`) REFERENCES `user_details` (`user_details_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

/*Table structure for table `user_details` */

DROP TABLE IF EXISTS `user_details`;

CREATE TABLE `user_details` (
  `user_details_id` int(11) NOT NULL AUTO_INCREMENT,
  `firstname` varchar(45) DEFAULT NULL,
  `lastname` varchar(45) DEFAULT NULL,
  `gender_gender_id` int(11) NOT NULL,
  `dob` date DEFAULT NULL,
  `address1` varchar(45) DEFAULT NULL,
  `address2` varchar(45) DEFAULT NULL,
  `city_city_id` int(11) NOT NULL,
  `mobile` varchar(45) DEFAULT NULL,
  `profile_picture_profile_pic_id` int(11) NOT NULL,
  `last_updated` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`user_details_id`),
  KEY `fk_user_details_city1_idx` (`city_city_id`),
  KEY `fk_user_details_gender1_idx` (`gender_gender_id`),
  KEY `fk_user_details_profile_picture1_idx` (`profile_picture_profile_pic_id`),
  CONSTRAINT `fk_user_details_city1` FOREIGN KEY (`city_city_id`) REFERENCES `city` (`city_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_details_gender1` FOREIGN KEY (`gender_gender_id`) REFERENCES `gender` (`gender_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_details_profile_picture1` FOREIGN KEY (`profile_picture_profile_pic_id`) REFERENCES `profile_picture` (`profile_pic_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

/*Table structure for table `user_locations` */

DROP TABLE IF EXISTS `user_locations`;

CREATE TABLE `user_locations` (
  `user_locations_id` int(11) NOT NULL AUTO_INCREMENT,
  `longitute` double DEFAULT NULL,
  `lattitude` double DEFAULT NULL,
  `location_type_location_type_id` int(11) NOT NULL,
  `user_user_id` int(11) NOT NULL,
  PRIMARY KEY (`user_locations_id`),
  KEY `fk_user_locations_location_type1_idx` (`location_type_location_type_id`),
  KEY `fk_user_locations_user1_idx` (`user_user_id`),
  CONSTRAINT `fk_user_locations_location_type1` FOREIGN KEY (`location_type_location_type_id`) REFERENCES `location_type` (`location_type_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_locations_user1` FOREIGN KEY (`user_user_id`) REFERENCES `user` (`user_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `user_privillege` */

DROP TABLE IF EXISTS `user_privillege`;

CREATE TABLE `user_privillege` (
  `user_privillege_uri` varchar(150) NOT NULL,
  `user_privillege_name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`user_privillege_uri`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `user_type` */

DROP TABLE IF EXISTS `user_type`;

CREATE TABLE `user_type` (
  `user_type_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_type` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`user_type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

/*Table structure for table `user_type_has_user_privillege` */

DROP TABLE IF EXISTS `user_type_has_user_privillege`;

CREATE TABLE `user_type_has_user_privillege` (
  `user_type_has_user_privillege_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_privillege_uri` varchar(150) NOT NULL,
  `user_type_id` int(11) NOT NULL,
  `allow` tinyint(1) DEFAULT NULL,
  `allowed_page` varchar(200) DEFAULT NULL,
  `disallowed_page` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`user_type_has_user_privillege_id`),
  KEY `fk_user_type_has_user_privillege_user_type1_idx` (`user_type_id`),
  KEY `fk_user_type_has_user_privillege_user_privillege1_idx` (`user_privillege_uri`),
  CONSTRAINT `fk_user_type_has_user_privillege_user_privillege1` FOREIGN KEY (`user_privillege_uri`) REFERENCES `user_privillege` (`user_privillege_uri`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_type_has_user_privillege_user_type1` FOREIGN KEY (`user_type_id`) REFERENCES `user_type` (`user_type_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8;

/*Table structure for table `vehicle_colour` */

DROP TABLE IF EXISTS `vehicle_colour`;

CREATE TABLE `vehicle_colour` (
  `vehicle_colour_id` int(11) NOT NULL AUTO_INCREMENT,
  `vehicle_colour` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`vehicle_colour_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

/*Table structure for table `vehicle_make` */

DROP TABLE IF EXISTS `vehicle_make`;

CREATE TABLE `vehicle_make` (
  `vehicle_make_id` int(11) NOT NULL AUTO_INCREMENT,
  `vehicle_make` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`vehicle_make_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

/*Table structure for table `vehicle_model` */

DROP TABLE IF EXISTS `vehicle_model`;

CREATE TABLE `vehicle_model` (
  `vehicle_model_id` int(11) NOT NULL AUTO_INCREMENT,
  `vehicle_model` varchar(45) DEFAULT NULL,
  `vehicle_make_vehicle_make_id` int(11) NOT NULL,
  PRIMARY KEY (`vehicle_model_id`),
  KEY `fk_vehicle_model_vehicle_make1_idx` (`vehicle_make_vehicle_make_id`),
  CONSTRAINT `fk_vehicle_model_vehicle_make1` FOREIGN KEY (`vehicle_make_vehicle_make_id`) REFERENCES `vehicle_make` (`vehicle_make_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

/*Table structure for table `vehicle_status` */

DROP TABLE IF EXISTS `vehicle_status`;

CREATE TABLE `vehicle_status` (
  `vehicle_status_id` int(11) NOT NULL AUTO_INCREMENT,
  `vehicle_status` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`vehicle_status_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

/*Table structure for table `expired_services` */

DROP TABLE IF EXISTS `expired_services`;

/*!50001 DROP VIEW IF EXISTS `expired_services` */;
/*!50001 DROP TABLE IF EXISTS `expired_services` */;

/*!50001 CREATE TABLE  `expired_services`(
 `service_provider_id` int(11) ,
 `service_provider_name` varchar(45) ,
 `service_details_id` int(11) ,
 `logo_logo_id` int(11) ,
 `service_provider_location_details_id` int(11) ,
 `login_details_id` int(11) ,
 `membership_status_id` int(11) ,
 `contact_no` varchar(45) 
)*/;

/*View structure for view expired_services */

/*!50001 DROP TABLE IF EXISTS `expired_services` */;
/*!50001 DROP VIEW IF EXISTS `expired_services` */;

/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `expired_services` AS select `sp`.`service_provider_id` AS `service_provider_id`,`sp`.`service_provider_name` AS `service_provider_name`,`sp`.`service_details_id` AS `service_details_id`,`sp`.`logo_logo_id` AS `logo_logo_id`,`sp`.`service_provider_location_details_id` AS `service_provider_location_details_id`,`sp`.`login_details_id` AS `login_details_id`,`sp`.`membership_status_id` AS `membership_status_id`,`sp`.`contact_no` AS `contact_no` from `service_provider` `sp` where `sp`.`service_provider_id` in (select `m`.`service_provider_service_provider_id` from `membership_upgrade_history` `m` where (`m`.`expires_datetime` <= now())) */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
