# ************************************************************
# Sequel Pro SQL dump
# Version 4096
#
# http://www.sequelpro.com/
# http://code.google.com/p/sequel-pro/
#
# Host: localhost (MySQL 5.6.24)
# Database: carloan
# Generation Time: 2015-11-02 13:45:59 +0000
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


# Dump of table admin
# ------------------------------------------------------------

DROP TABLE IF EXISTS `admin`;

CREATE TABLE `admin` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `Username` varchar(30) NOT NULL DEFAULT '',
  `Password` varchar(50) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`),
  UNIQUE KEY `Username` (`Username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `admin` WRITE;
/*!40000 ALTER TABLE `admin` DISABLE KEYS */;

INSERT INTO `admin` (`id`, `Username`, `Password`)
VALUES
	(10,'maqix','47bce5c74f589f4867dbd57e9ca9f808');

/*!40000 ALTER TABLE `admin` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table agenzia
# ------------------------------------------------------------

DROP TABLE IF EXISTS `agenzia`;

CREATE TABLE `agenzia` (
  `PartitaIVA` varchar(11) NOT NULL,
  `Nome` varchar(45) NOT NULL,
  `Citta` varchar(45) NOT NULL DEFAULT '',
  `Provincia` varchar(2) NOT NULL,
  `Via` varchar(45) NOT NULL,
  `Civico` varchar(11) DEFAULT NULL,
  PRIMARY KEY (`PartitaIVA`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `agenzia` WRITE;
/*!40000 ALTER TABLE `agenzia` DISABLE KEYS */;

INSERT INTO `agenzia` (`PartitaIVA`, `Nome`, `Citta`, `Provincia`, `Via`, `Civico`)
VALUES
	('12345678910','MaqixLoan','Taranto','TA','Minniti','s.n.C.'),
	('12345678911','CarZion','Mordor','MD','Tal dei Tali','s.n.c.');

/*!40000 ALTER TABLE `agenzia` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table auto
# ------------------------------------------------------------

DROP TABLE IF EXISTS `auto`;

CREATE TABLE `auto` (
  `Targa` varchar(7) NOT NULL,
  `Fascia` int(11) NOT NULL,
  `Modello` varchar(30) NOT NULL,
  `Agenzia` varchar(11) NOT NULL,
  `Stato` int(11) NOT NULL,
  `Chilometraggio` int(11) NOT NULL,
  PRIMARY KEY (`Targa`),
  KEY `FasciaAuto_idx` (`Fascia`),
  KEY `AgenziaAuto_idx` (`Agenzia`),
  CONSTRAINT `AgenziaAuto` FOREIGN KEY (`Agenzia`) REFERENCES `agenzia` (`PartitaIVA`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FasciaAuto` FOREIGN KEY (`Fascia`) REFERENCES `fascia` (`idFascia`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `auto` WRITE;
/*!40000 ALTER TABLE `auto` DISABLE KEYS */;

INSERT INTO `auto` (`Targa`, `Fascia`, `Modello`, `Agenzia`, `Stato`, `Chilometraggio`)
VALUES
	('AZ066GH',1,'Lancia Y','12345678911',1,55500),
	('CZ571EM',1,'Smart','12345678910',2,99564),
	('DF567GH',1,'Fiat Punto','12345678910',2,1),
	('EK689FL',1,'Lexus CT200h','12345678910',3,34000),
	('ER465FG',1,'Ford Ka','12345678911',1,34577),
	('JJ666KK',2,'EvilCarl','12345678910',1,6660),
	('XX000XX',3,'BMW 530Gt','12345678910',2,100);

/*!40000 ALTER TABLE `auto` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table cliente
# ------------------------------------------------------------

DROP TABLE IF EXISTS `cliente`;

CREATE TABLE `cliente` (
  `CF` varchar(16) NOT NULL DEFAULT '',
  `Nome` varchar(45) NOT NULL,
  `Cognome` varchar(45) NOT NULL,
  `Telefono` varchar(10) NOT NULL,
  `Contratto` int(11) DEFAULT NULL,
  PRIMARY KEY (`CF`),
  KEY `Contratto_idx` (`Contratto`),
  CONSTRAINT `cliente_ibfk_1` FOREIGN KEY (`Contratto`) REFERENCES `contratto` (`idContratto`) ON DELETE SET NULL ON UPDATE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `cliente` WRITE;
/*!40000 ALTER TABLE `cliente` DISABLE KEYS */;

INSERT INTO `cliente` (`CF`, `Nome`, `Cognome`, `Telefono`, `Contratto`)
VALUES
	('DLGNGL94R25L049B','Angelo','Del Giudice','324232',NULL),
	('GRGMHL94A01L049U','Michele','Gargaro','3894567213',NULL),
	('QRTMCL93A03F205Q','Marcello','Quarta','3920457316',NULL);

/*!40000 ALTER TABLE `cliente` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table contratto
# ------------------------------------------------------------

DROP TABLE IF EXISTS `contratto`;

CREATE TABLE `contratto` (
  `idContratto` int(11) NOT NULL,
  `Auto` varchar(7) NOT NULL,
  `AgenziaApertura` varchar(11) NOT NULL,
  `AgenziaChiusura` varchar(11) NOT NULL,
  `Inizio` date NOT NULL,
  `Fine` date NOT NULL,
  `TotaleVersato` int(11) NOT NULL,
  `KmIniziali` int(11) NOT NULL,
  `Cliente` varchar(16) NOT NULL DEFAULT '',
  `isAperto` binary(1) NOT NULL,
  `TipoNoleggio` varchar(20) NOT NULL DEFAULT '',
  `TipoChilometraggio` varchar(20) NOT NULL DEFAULT '',
  `KmPrevisti` int(11) DEFAULT NULL,
  PRIMARY KEY (`idContratto`),
  KEY `ContrattoAuto_idx` (`Auto`),
  KEY `AgInizio_idx` (`AgenziaApertura`),
  KEY `AgFinale_idx` (`AgenziaChiusura`),
  KEY `Cliente` (`Cliente`),
  CONSTRAINT `ContrattoAuto` FOREIGN KEY (`Auto`) REFERENCES `auto` (`Targa`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `contratto_ibfk_1` FOREIGN KEY (`Cliente`) REFERENCES `cliente` (`CF`),
  CONSTRAINT `contratto_ibfk_2` FOREIGN KEY (`AgenziaApertura`) REFERENCES `agenzia` (`PartitaIVA`),
  CONSTRAINT `contratto_ibfk_3` FOREIGN KEY (`AgenziaChiusura`) REFERENCES `agenzia` (`PartitaIVA`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table dipendente
# ------------------------------------------------------------

DROP TABLE IF EXISTS `dipendente`;

CREATE TABLE `dipendente` (
  `idDipendente` int(11) NOT NULL AUTO_INCREMENT,
  `Agenzia` varchar(11) NOT NULL,
  `Nome` varchar(45) NOT NULL,
  `Cognome` varchar(45) NOT NULL,
  `Telefono` varchar(10) NOT NULL,
  `Password` varchar(50) NOT NULL DEFAULT '',
  `Username` varchar(30) NOT NULL DEFAULT '',
  PRIMARY KEY (`idDipendente`),
  UNIQUE KEY `Username` (`Username`),
  KEY `AgDip_idx` (`Agenzia`),
  CONSTRAINT `AgDip` FOREIGN KEY (`Agenzia`) REFERENCES `agenzia` (`PartitaIVA`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `dipendente` WRITE;
/*!40000 ALTER TABLE `dipendente` DISABLE KEYS */;

INSERT INTO `dipendente` (`idDipendente`, `Agenzia`, `Nome`, `Cognome`, `Telefono`, `Password`, `Username`)
VALUES
	(2,'12345678910','Marcello','Quarta','3920457316','47bce5c74f589f4867dbd57e9ca9f808','marcello');

/*!40000 ALTER TABLE `dipendente` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table fascia
# ------------------------------------------------------------

DROP TABLE IF EXISTS `fascia`;

CREATE TABLE `fascia` (
  `idFascia` int(11) NOT NULL DEFAULT '0',
  `PrezzoKm` float NOT NULL,
  `Requisiti` varchar(225) DEFAULT NULL,
  `PrezzoFisso` float NOT NULL,
  PRIMARY KEY (`idFascia`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `fascia` WRITE;
/*!40000 ALTER TABLE `fascia` DISABLE KEYS */;

INSERT INTO `fascia` (`idFascia`, `PrezzoKm`, `Requisiti`, `PrezzoFisso`)
VALUES
	(1,10,'Citycar',70),
	(2,20,'Berlina',80),
	(3,30,'Lusso',100);

/*!40000 ALTER TABLE `fascia` ENABLE KEYS */;
UNLOCK TABLES;



/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
