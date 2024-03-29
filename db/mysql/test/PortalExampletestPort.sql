-- MySQL dump 10.13  Distrib 5.1.49, for debian-linux-gnu (i686)
--
-- Host: localhost    Database: PortalExampletestPort
-- ------------------------------------------------------
-- Server version	5.1.49-1ubuntu8.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Current Database: `PortalExampletestPort`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `PortalExampletestPort` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `PortalExampletestPort`;

--
-- Table structure for table `categories`
--

DROP TABLE IF EXISTS `categories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `categories` (
  `catcode` varchar(30) NOT NULL,
  `parentcode` varchar(30) NOT NULL,
  `titles` longtext NOT NULL,
  PRIMARY KEY (`catcode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categories`
--

LOCK TABLES `categories` WRITE;
/*!40000 ALTER TABLE `categories` DISABLE KEYS */;
INSERT INTO `categories` VALUES ('Attach','resource_root','<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<properties>\n<property key=\"it\">Attach</property>\n</properties>\n\n'),('cat1','home','<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<properties>\n<property key=\"en\">Animal</property>\n<property key=\"it\">Animali</property>\n</properties>\n\n'),('evento','home','<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<properties>\n<property key=\"en\">Event</property>\n<property key=\"it\">Evento</property>\n</properties>\n\n'),('general','home','<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<properties>\n<property key=\"en\">General</property>\n<property key=\"it\">Generale</property>\n</properties>\n\n'),('general_cat1','general','<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<properties>\n<property key=\"en\">Category 1</property>\n<property key=\"it\">Categoria 1</property>\n</properties>\n\n'),('general_cat2','general','<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<properties>\n<property key=\"en\">Category 2</property>\n<property key=\"it\">Categoria 2</property>\n</properties>\n\n'),('home','home','<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<properties>\n<property key=\"it\">Home</property>\n</properties>\n'),('Image','resource_root','<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<properties>\n<property key=\"it\">Image</property>\n</properties>\n\n'),('resCat1','Image','<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<properties>\n<property key=\"en\">Images Resource Category 1</property>\n<property key=\"it\">Categoria Risorse Immagine 1</property>\n</properties>\n\n'),('resCat2','Image','<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<properties>\n<property key=\"en\">Images Resource Category 2</property>\n<property key=\"it\">Categoria Risorsa Immagine 2</property>\n</properties>\n\n'),('resource_root','home','<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<properties>\n<property key=\"it\">Root Risorse</property>\n</properties>\n\n');
/*!40000 ALTER TABLE `categories` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `contentmodels`
--

DROP TABLE IF EXISTS `contentmodels`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `contentmodels` (
  `modelid` int(11) NOT NULL,
  `contenttype` varchar(30) NOT NULL,
  `descr` varchar(50) NOT NULL,
  `model` longtext,
  `stylesheet` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`modelid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contentmodels`
--

LOCK TABLES `contentmodels` WRITE;
/*!40000 ALTER TABLE `contentmodels` DISABLE KEYS */;
INSERT INTO `contentmodels` VALUES (1,'ART','Main Model','#if ($content.Titolo.text != \"\")<h1 class=\"titolo\">$content.Titolo.text</h1>#end\n#if ($content.Data.longDate != \"\")<p>Data: $content.Data.longDate</p>#end\n$content.TextBody.getTextBeforeImage(0)\n#if ( $content.Foto.imagePath(\"2\") != \"\" )\n<img class=\"left\" src=\"$content.Foto.imagePath(\"2\")\" alt=\"$content.Foto.text\" />\n#end\n$content.CorpoTesto.getTextAfterImage(0)\n#if ($content.Numero.Number != null)<p>Numero: $content.Numero.Number</p>#end\n#if ($content.Autori && $content.Autori.size() > 0)\n<h2 class=\"titolo\">Autori:</h2>\n<ul title=\"Authors\">\n#foreach ($author in $content.Autori)\n	<li>$author.text;</li>\n#end\n</ul>\n#end\n#if ($content.VediAnche.text != \"\")\n<h2 class=\"titolo\">Link:</h2>\n<p>\n<li><a href=\"$content.VediAnche.destination\">$content.VediAnche.text</a></li>\n</p>\n#end',NULL),(2,'ART','per test rendering\n','$content.id;\n#foreach ($autore in $content.Autori)\n$autore.text;\n#end\n$content.Titolo.getText();\n$content.VediAnche.text,$content.VediAnche.destination;\n$content.Foto.text,$content.Foto.imagePath(\"1\");\n$content.Data.mediumDate;\n\n',NULL),(3,'ART','scheda di un articolo','------ RENDERING CONTENUTO: id = $content.id; ---------\nATTRIBUTI:\n  - AUTORI (Monolist-Monotext): \n#foreach ($autore in $content.Autori)\n         testo=$autore.text;\n#end\n  - TITOLO (Text): testo=$content.Titolo.getText(); \n  - VEDI ANCHE (Link): testo=$content.VediAnche.text, dest=$content.VediAnche.destination;\n  - FOTO (Image): testo=$content.Foto.text, src(1)=$content.Foto.imagePath(\"1\");\n  - DATA (Date): data_media = $content.Data.mediumDate;\n------ END ------\n\n',NULL);
/*!40000 ALTER TABLE `contentmodels` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `contentrelations`
--

DROP TABLE IF EXISTS `contentrelations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `contentrelations` (
  `contentid` varchar(16) NOT NULL,
  `refpage` varchar(30) DEFAULT NULL,
  `refcontent` varchar(16) DEFAULT NULL,
  `refresource` varchar(16) DEFAULT NULL,
  `refcategory` varchar(30) DEFAULT NULL,
  `refgroup` varchar(20) DEFAULT NULL,
  KEY `contentrelations_contentid_fkey` (`contentid`),
  KEY `contentrelations_refcategory_fkey` (`refcategory`),
  KEY `contentrelations_refcontent_fkey` (`refcontent`),
  KEY `contentrelations_refpage_fkey` (`refpage`),
  KEY `contentrelations_refresource_fkey` (`refresource`),
  CONSTRAINT `contentrelations_contentid_fkey` FOREIGN KEY (`contentid`) REFERENCES `contents` (`contentid`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `contentrelations_refcategory_fkey` FOREIGN KEY (`refcategory`) REFERENCES `categories` (`catcode`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `contentrelations_refcontent_fkey` FOREIGN KEY (`refcontent`) REFERENCES `contents` (`contentid`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `contentrelations_refpage_fkey` FOREIGN KEY (`refpage`) REFERENCES `pages` (`code`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `contentrelations_refresource_fkey` FOREIGN KEY (`refresource`) REFERENCES `resources` (`resid`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contentrelations`
--

LOCK TABLES `contentrelations` WRITE;
/*!40000 ALTER TABLE `contentrelations` DISABLE KEYS */;
INSERT INTO `contentrelations` VALUES ('RAH1',NULL,NULL,NULL,NULL,'free'),('RAH1',NULL,NULL,'7',NULL,NULL),('RAH101',NULL,NULL,NULL,NULL,'customers'),('ART187',NULL,NULL,NULL,NULL,'free'),('ART120',NULL,NULL,NULL,NULL,'administrators'),('ART1',NULL,NULL,NULL,NULL,'free'),('ART1',NULL,NULL,'44',NULL,NULL),('ART122',NULL,NULL,NULL,NULL,'administrators'),('ART122',NULL,NULL,NULL,NULL,'customers'),('ART104',NULL,NULL,NULL,NULL,'coach'),('ART121',NULL,NULL,NULL,NULL,'free'),('ART121',NULL,NULL,NULL,NULL,'administrators'),('ART112',NULL,NULL,NULL,NULL,'coach'),('ART112',NULL,NULL,NULL,NULL,'customers'),('ART112',NULL,NULL,NULL,NULL,'helpdesk'),('ART111',NULL,NULL,NULL,NULL,'coach'),('ART111',NULL,NULL,NULL,NULL,'customers'),('ART111',NULL,NULL,NULL,NULL,'helpdesk'),('ART180',NULL,NULL,NULL,'cat1',NULL),('ART180',NULL,NULL,NULL,NULL,'free'),('ART180',NULL,NULL,'44',NULL,NULL),('ART102',NULL,NULL,NULL,'general',NULL),('ART102',NULL,NULL,NULL,'general_cat1',NULL),('ART102',NULL,NULL,NULL,NULL,'customers'),('ART102',NULL,'ART111',NULL,NULL,NULL),('EVN20',NULL,NULL,NULL,NULL,'free'),('EVN192',NULL,NULL,NULL,'evento',NULL),('EVN192',NULL,NULL,NULL,NULL,'free'),('EVN103',NULL,NULL,NULL,NULL,'coach'),('EVN23',NULL,NULL,NULL,NULL,'free'),('EVN24',NULL,NULL,NULL,NULL,'free'),('EVN41',NULL,NULL,NULL,NULL,'coach'),('EVN21',NULL,NULL,NULL,NULL,'free'),('EVN25',NULL,NULL,NULL,NULL,'free'),('EVN25',NULL,NULL,NULL,NULL,'coach'),('EVN191',NULL,NULL,NULL,NULL,'free'),('EVN194',NULL,NULL,NULL,NULL,'free'),('EVN194',NULL,'ART1',NULL,NULL,NULL),('EVN194','pagina_11',NULL,NULL,NULL,NULL),('EVN193',NULL,NULL,NULL,'evento',NULL),('EVN193',NULL,NULL,NULL,NULL,'free'),('EVN193',NULL,'ART1',NULL,NULL,NULL),('EVN193','pagina_11',NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `contentrelations` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `contents`
--

DROP TABLE IF EXISTS `contents`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `contents` (
  `contentid` varchar(16) NOT NULL,
  `contenttype` varchar(30) NOT NULL,
  `descr` varchar(260) NOT NULL,
  `status` varchar(12) NOT NULL,
  `workxml` longtext NOT NULL,
  `created` varchar(20) DEFAULT NULL,
  `lastmodified` varchar(20) DEFAULT NULL,
  `onlinexml` longtext,
  `maingroup` varchar(20) NOT NULL,
  `currentversion` varchar(7) NOT NULL,
  `lasteditor` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`contentid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contents`
--

LOCK TABLES `contents` WRITE;
/*!40000 ALTER TABLE `contents` DISABLE KEYS */;
INSERT INTO `contents` VALUES ('ART1','ART','Articolo','DRAFT','<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<content id=\"ART1\" typecode=\"ART\" typedescr=\"Articolo rassegna stampa\"><descr>Articolo</descr><groups mainGroup=\"free\" /><categories /><attributes><attribute name=\"Titolo\" attributetype=\"Text\"><text lang=\"it\">Il titolo</text><text lang=\"en\">The title</text></attribute><list attributetype=\"Monolist\" name=\"Autori\" nestedtype=\"Monotext\"><attribute name=\"Autori\" attributetype=\"Monotext\"><monotext>Pippo</monotext></attribute><attribute name=\"Autori\" attributetype=\"Monotext\"><monotext>Paperino</monotext></attribute><attribute name=\"Autori\" attributetype=\"Monotext\"><monotext>Pluto</monotext></attribute></list><attribute name=\"VediAnche\" attributetype=\"Link\"><link type=\"external\"><urldest>http://www.spiderman.org</urldest></link><text lang=\"it\">Spiderman</text></attribute><attribute name=\"CorpoTesto\" attributetype=\"Hypertext\" /><attribute name=\"Foto\" attributetype=\"Image\"><resource resourcetype=\"Image\" id=\"44\" lang=\"it\" /><text lang=\"it\">Image description</text></attribute><attribute name=\"Data\" attributetype=\"Date\"><date>20040310</date></attribute><attribute name=\"Numero\" attributetype=\"Number\" /></attributes><status>DRAFT</status></content>\n','20050503181212\n','20060622202051','<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<content id=\"ART1\" typecode=\"ART\" typedescr=\"Articolo rassegna stampa\"><descr>Articolo</descr><groups mainGroup=\"free\" /><categories /><attributes><attribute name=\"Titolo\" attributetype=\"Text\"><text lang=\"it\">Il titolo</text><text lang=\"en\">The title</text></attribute><list attributetype=\"Monolist\" name=\"Autori\" nestedtype=\"Monotext\"><attribute name=\"Autori\" attributetype=\"Monotext\"><monotext>Pippo</monotext></attribute><attribute name=\"Autori\" attributetype=\"Monotext\"><monotext>Paperino</monotext></attribute><attribute name=\"Autori\" attributetype=\"Monotext\"><monotext>Pluto</monotext></attribute></list><attribute name=\"VediAnche\" attributetype=\"Link\"><link type=\"external\"><urldest>http://www.spiderman.org</urldest></link><text lang=\"it\">Spiderman</text></attribute><attribute name=\"CorpoTesto\" attributetype=\"Hypertext\" /><attribute name=\"Foto\" attributetype=\"Image\"><resource resourcetype=\"Image\" id=\"44\" lang=\"it\" /><text lang=\"it\">Image description</text></attribute><attribute name=\"Data\" attributetype=\"Date\"><date>20040310</date></attribute><attribute name=\"Numero\" attributetype=\"Number\" /></attributes><status>DRAFT</status></content>\n','free','1.0','admin'),('ART102','ART','Contenuto 2 Customers','DRAFT','<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<content id=\"ART102\" typecode=\"ART\" typedescr=\"Articolo rassegna stampa\"><descr>Contenuto 2 Customers</descr><groups mainGroup=\"customers\" /><categories><category id=\"general_cat1\" /></categories><attributes><attribute name=\"Titolo\" attributetype=\"Text\"><text lang=\"it\">Titolo Contenuto 2 Customers</text></attribute><list attributetype=\"Monolist\" name=\"Autori\" nestedtype=\"Monotext\" /><attribute name=\"VediAnche\" attributetype=\"Link\"><link type=\"content\"><contentdest>ART111</contentdest></link><text lang=\"it\">Contenuto autorizzato Gruppo Coath</text></attribute><attribute name=\"CorpoTesto\" attributetype=\"Hypertext\"><hypertext lang=\"it\"><![CDATA[<p>Corpo Testo Contenuto&nbsp;2 Customers</p>]]></hypertext></attribute><attribute name=\"Foto\" attributetype=\"Image\" /><attribute name=\"Data\" attributetype=\"Date\" /><attribute name=\"Numero\" attributetype=\"Number\" /></attributes><status>DRAFT</status></content>\n','20061221164629','20071215174925','<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<content id=\"ART102\" typecode=\"ART\" typedescr=\"Articolo rassegna stampa\"><descr>Contenuto 2 Customers</descr><groups mainGroup=\"customers\" /><categories><category id=\"general_cat1\" /></categories><attributes><attribute name=\"Titolo\" attributetype=\"Text\"><text lang=\"it\">Titolo Contenuto 2 Customers</text></attribute><list attributetype=\"Monolist\" name=\"Autori\" nestedtype=\"Monotext\" /><attribute name=\"VediAnche\" attributetype=\"Link\"><link type=\"content\"><contentdest>ART111</contentdest></link><text lang=\"it\">Contenuto autorizzato Gruppo Coath</text></attribute><attribute name=\"CorpoTesto\" attributetype=\"Hypertext\"><hypertext lang=\"it\"><![CDATA[<p>Corpo Testo Contenuto&nbsp;2 Customers</p>]]></hypertext></attribute><attribute name=\"Foto\" attributetype=\"Image\" /><attribute name=\"Data\" attributetype=\"Date\" /><attribute name=\"Numero\" attributetype=\"Number\" /></attributes><status>DRAFT</status></content>\n','customers','1.0','admin'),('ART104','ART','Contenuto 2 Coach','DRAFT','<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<content id=\"ART104\" typecode=\"ART\" typedescr=\"Articolo rassegna stampa\"><descr>Contenuto 2 Coach</descr><groups mainGroup=\"coach\" /><categories /><attributes><attribute name=\"Titolo\" attributetype=\"Text\"><text lang=\"it\">Titolo Contenuto 2 Coach</text></attribute><list attributetype=\"Monolist\" name=\"Autori\" nestedtype=\"Monotext\"><attribute name=\"Autori\" attributetype=\"Monotext\"><monotext>Walter</monotext></attribute><attribute name=\"Autori\" attributetype=\"Monotext\"><monotext>Marco</monotext></attribute><attribute name=\"Autori\" attributetype=\"Monotext\"><monotext>Eugenio</monotext></attribute><attribute name=\"Autori\" attributetype=\"Monotext\"><monotext>William</monotext></attribute></list><attribute name=\"VediAnche\" attributetype=\"Link\"><link type=\"external\"><urldest>http://www.japsportal.org</urldest></link><text lang=\"it\">Home jAPS</text></attribute><attribute name=\"CorpoTesto\" attributetype=\"Hypertext\"><hypertext lang=\"it\"><![CDATA[Corpo Testo Contenuto&nbsp;2 Coach]]></hypertext></attribute><attribute name=\"Foto\" attributetype=\"Image\" /><attribute name=\"Data\" attributetype=\"Date\"><date>20070104</date></attribute><attribute name=\"Numero\" attributetype=\"Number\" /></attributes><status>DRAFT</status></content>\n','20061221165750','20070103143539','<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<content id=\"ART104\" typecode=\"ART\" typedescr=\"Articolo rassegna stampa\"><descr>Contenuto 2 Coach</descr><groups mainGroup=\"coach\" /><categories /><attributes><attribute name=\"Titolo\" attributetype=\"Text\"><text lang=\"it\">Titolo Contenuto 2 Coach</text></attribute><list attributetype=\"Monolist\" name=\"Autori\" nestedtype=\"Monotext\"><attribute name=\"Autori\" attributetype=\"Monotext\"><monotext>Walter</monotext></attribute><attribute name=\"Autori\" attributetype=\"Monotext\"><monotext>Marco</monotext></attribute><attribute name=\"Autori\" attributetype=\"Monotext\"><monotext>Eugenio</monotext></attribute><attribute name=\"Autori\" attributetype=\"Monotext\"><monotext>William</monotext></attribute></list><attribute name=\"VediAnche\" attributetype=\"Link\"><link type=\"external\"><urldest>http://www.japsportal.org</urldest></link><text lang=\"it\">Home jAPS</text></attribute><attribute name=\"CorpoTesto\" attributetype=\"Hypertext\"><hypertext lang=\"it\"><![CDATA[Corpo Testo Contenuto&nbsp;2 Coach]]></hypertext></attribute><attribute name=\"Foto\" attributetype=\"Image\" /><attribute name=\"Data\" attributetype=\"Date\"><date>20070104</date></attribute><attribute name=\"Numero\" attributetype=\"Number\" /></attributes><status>DRAFT</status></content>\n','coach','1.0','admin'),('ART111','ART','Contenuto 3 Coach','DRAFT','<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<content id=\"ART111\" typecode=\"ART\" typedescr=\"Articolo rassegna stampa\"><descr>Contenuto 3 Coach</descr><groups mainGroup=\"coach\"><group name=\"customers\" /><group name=\"helpdesk\" /></groups><categories /><attributes><attribute name=\"Titolo\" attributetype=\"Text\"><text lang=\"it\">Titolo Contenuto 3 Coach</text></attribute><list attributetype=\"Monolist\" name=\"Autori\" nestedtype=\"Monotext\"><attribute name=\"Autori\" attributetype=\"Monotext\"><monotext>Walter</monotext></attribute><attribute name=\"Autori\" attributetype=\"Monotext\"><monotext>Marco</monotext></attribute><attribute name=\"Autori\" attributetype=\"Monotext\"><monotext>Eugenio</monotext></attribute><attribute name=\"Autori\" attributetype=\"Monotext\"><monotext>William</monotext></attribute></list><attribute name=\"VediAnche\" attributetype=\"Link\" /><attribute name=\"CorpoTesto\" attributetype=\"Hypertext\"><hypertext lang=\"it\"><![CDATA[<p>Corpo Testo Contenuto 3 Coach</p>]]></hypertext></attribute><attribute name=\"Foto\" attributetype=\"Image\" /><attribute name=\"Data\" attributetype=\"Date\"><date>20061213</date></attribute><attribute name=\"Numero\" attributetype=\"Number\" /></attributes><status>DRAFT</status><version>3.0</version></content>\n','20071215174627','20071215175041','<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<content id=\"ART111\" typecode=\"ART\" typedescr=\"Articolo rassegna stampa\"><descr>Contenuto 3 Coach</descr><groups mainGroup=\"coach\"><group name=\"customers\" /><group name=\"helpdesk\" /></groups><categories /><attributes><attribute name=\"Titolo\" attributetype=\"Text\"><text lang=\"it\">Titolo Contenuto 3 Coach</text></attribute><list attributetype=\"Monolist\" name=\"Autori\" nestedtype=\"Monotext\"><attribute name=\"Autori\" attributetype=\"Monotext\"><monotext>Walter</monotext></attribute><attribute name=\"Autori\" attributetype=\"Monotext\"><monotext>Marco</monotext></attribute><attribute name=\"Autori\" attributetype=\"Monotext\"><monotext>Eugenio</monotext></attribute><attribute name=\"Autori\" attributetype=\"Monotext\"><monotext>William</monotext></attribute></list><attribute name=\"VediAnche\" attributetype=\"Link\" /><attribute name=\"CorpoTesto\" attributetype=\"Hypertext\"><hypertext lang=\"it\"><![CDATA[<p>Corpo Testo Contenuto 3 Coach</p>]]></hypertext></attribute><attribute name=\"Foto\" attributetype=\"Image\" /><attribute name=\"Data\" attributetype=\"Date\"><date>20061213</date></attribute><attribute name=\"Numero\" attributetype=\"Number\" /></attributes><status>DRAFT</status><version>3.0</version></content>\n','coach','3.0','admin'),('ART112','ART','Contenuto 4 Coach','DRAFT','<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<content id=\"ART112\" typecode=\"ART\" typedescr=\"Articolo rassegna stampa\"><descr>Contenuto 4 Coach</descr><groups mainGroup=\"coach\"><group name=\"customers\" /><group name=\"helpdesk\" /></groups><categories /><attributes><attribute name=\"Titolo\" attributetype=\"Text\"><text lang=\"it\">Titolo Contenuto 4 Coach</text></attribute><list attributetype=\"Monolist\" name=\"Autori\" nestedtype=\"Monotext\"><attribute name=\"Autori\" attributetype=\"Monotext\"><monotext>Walter</monotext></attribute><attribute name=\"Autori\" attributetype=\"Monotext\"><monotext>Marco</monotext></attribute><attribute name=\"Autori\" attributetype=\"Monotext\"><monotext>Eugenio</monotext></attribute><attribute name=\"Autori\" attributetype=\"Monotext\"><monotext>William</monotext></attribute></list><attribute name=\"VediAnche\" attributetype=\"Link\" /><attribute name=\"CorpoTesto\" attributetype=\"Hypertext\"><hypertext lang=\"it\"><![CDATA[<p>Corpo Testo Contenuto 4 Coach</p>]]></hypertext></attribute><attribute name=\"Foto\" attributetype=\"Image\" /><attribute name=\"Data\" attributetype=\"Date\"><date>20060213</date></attribute><attribute name=\"Numero\" attributetype=\"Number\" /></attributes><status>DRAFT</status></content>\n','20071216174627','20071216175041','<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<content id=\"ART112\" typecode=\"ART\" typedescr=\"Articolo rassegna stampa\"><descr>Contenuto 4 Coach</descr><groups mainGroup=\"coach\"><group name=\"customers\" /><group name=\"helpdesk\" /></groups><categories /><attributes><attribute name=\"Titolo\" attributetype=\"Text\"><text lang=\"it\">Titolo Contenuto 4 Coach</text></attribute><list attributetype=\"Monolist\" name=\"Autori\" nestedtype=\"Monotext\"><attribute name=\"Autori\" attributetype=\"Monotext\"><monotext>Walter</monotext></attribute><attribute name=\"Autori\" attributetype=\"Monotext\"><monotext>Marco</monotext></attribute><attribute name=\"Autori\" attributetype=\"Monotext\"><monotext>Eugenio</monotext></attribute><attribute name=\"Autori\" attributetype=\"Monotext\"><monotext>William</monotext></attribute></list><attribute name=\"VediAnche\" attributetype=\"Link\" /><attribute name=\"CorpoTesto\" attributetype=\"Hypertext\"><hypertext lang=\"it\"><![CDATA[<p>Corpo Testo Contenuto 4 Coach</p>]]></hypertext></attribute><attribute name=\"Foto\" attributetype=\"Image\" /><attribute name=\"Data\" attributetype=\"Date\"><date>20060213</date></attribute><attribute name=\"Numero\" attributetype=\"Number\" /></attributes><status>DRAFT</status></content>\n','coach','1.0','admin'),('ART120','ART','Contenuto degli amministratori 1','DRAFT','<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<content id=\"ART120\" typecode=\"ART\" typedescr=\"Articolo rassegna stampa\"><descr>Contenuto degli amministratori 1</descr><groups mainGroup=\"administrators\" /><categories /><attributes><attribute name=\"Titolo\" attributetype=\"Text\"><text lang=\"it\">Titolo Contenuto degli \"Amministratori\"</text><text lang=\"en\">Title of Administrator\'s Content</text></attribute><list attributetype=\"Monolist\" name=\"Autori\" nestedtype=\"Monotext\" /><attribute name=\"VediAnche\" attributetype=\"Link\"><link type=\"external\"><urldest>http://www.japsportal.org</urldest></link><text lang=\"it\">Pagina Iniziale jAPSPortal</text><text lang=\"en\">jAPSPortal HomePage</text></attribute><attribute name=\"CorpoTesto\" attributetype=\"Hypertext\"><hypertext lang=\"it\"><![CDATA[<p>Testo primo contenuto del gruppo degli Amministratori</p>]]></hypertext><hypertext lang=\"en\"><![CDATA[<p>Text of first Administrators Group\'s Content</p>]]></hypertext></attribute><attribute name=\"Foto\" attributetype=\"Image\" /><attribute name=\"Data\" attributetype=\"Date\"><date>20090328</date></attribute><attribute name=\"Numero\" attributetype=\"Number\"><number>7</number></attribute></attributes><status>DRAFT</status></content>\n','20080721125725','20090221161620','<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<content id=\"ART120\" typecode=\"ART\" typedescr=\"Articolo rassegna stampa\"><descr>Contenuto degli amministratori 1</descr><groups mainGroup=\"administrators\" /><categories /><attributes><attribute name=\"Titolo\" attributetype=\"Text\"><text lang=\"it\">Titolo Contenuto degli \"Amministratori\"</text><text lang=\"en\">Title of Administrator\'s Content</text></attribute><list attributetype=\"Monolist\" name=\"Autori\" nestedtype=\"Monotext\" /><attribute name=\"VediAnche\" attributetype=\"Link\"><link type=\"external\"><urldest>http://www.japsportal.org</urldest></link><text lang=\"it\">Pagina Iniziale jAPSPortal</text><text lang=\"en\">jAPSPortal HomePage</text></attribute><attribute name=\"CorpoTesto\" attributetype=\"Hypertext\"><hypertext lang=\"it\"><![CDATA[<p>Testo primo contenuto del gruppo degli Amministratori</p>]]></hypertext><hypertext lang=\"en\"><![CDATA[<p>Text of first Administrators Group\'s Content</p>]]></hypertext></attribute><attribute name=\"Foto\" attributetype=\"Image\" /><attribute name=\"Data\" attributetype=\"Date\"><date>20090328</date></attribute><attribute name=\"Numero\" attributetype=\"Number\"><number>7</number></attribute></attributes><status>DRAFT</status></content>\n','administrators','1.0','admin'),('ART121','ART','Contenuto degli amministratori 2','DRAFT','<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<content id=\"ART121\" typecode=\"ART\" typedescr=\"Articolo rassegna stampa\"><descr>Contenuto degli amministratori 2</descr><groups mainGroup=\"administrators\"><group name=\"free\" /></groups><categories /><attributes><attribute name=\"Titolo\" attributetype=\"Text\"><text lang=\"it\">Titolo Contenuto degli \"Amministratori\" 2</text><text lang=\"en\">Title of Administrator\'s Content &lt;2&gt;</text></attribute><list attributetype=\"Monolist\" name=\"Autori\" nestedtype=\"Monotext\" /><attribute name=\"VediAnche\" attributetype=\"Link\"><link type=\"external\"><urldest>http://www.w3.org/</urldest></link><text lang=\"it\">Pagina Iniziale W3C</text><text lang=\"en\">World Wide Web Consortium - Web Standards</text></attribute><attribute name=\"CorpoTesto\" attributetype=\"Hypertext\"><hypertext lang=\"it\"><![CDATA[<p>Testo secondo contenuto del gruppo degli Amministratori</p>\n<p>Questo contenuto appartiene al Gruppo degli Amministratori ma dichiarato visibile agli utenti del gruppo del gruppo free.</p>]]></hypertext><hypertext lang=\"en\"><![CDATA[<p>Text of second Administrators Group\'s Content</p>\n<p>This content belongs to the Administrators Group was declared visible to guest users.</p>]]></hypertext></attribute><attribute name=\"Foto\" attributetype=\"Image\" /><attribute name=\"Data\" attributetype=\"Date\"><date>20090330</date></attribute><attribute name=\"Numero\" attributetype=\"Number\"><number>78</number></attribute></attributes><status>DRAFT</status></content>\n','20080721143834','20090318161630','<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<content id=\"ART121\" typecode=\"ART\" typedescr=\"Articolo rassegna stampa\"><descr>Contenuto degli amministratori 2</descr><groups mainGroup=\"administrators\"><group name=\"free\" /></groups><categories /><attributes><attribute name=\"Titolo\" attributetype=\"Text\"><text lang=\"it\">Titolo Contenuto degli \"Amministratori\" 2</text><text lang=\"en\">Title of Administrator\'s Content &lt;2&gt;</text></attribute><list attributetype=\"Monolist\" name=\"Autori\" nestedtype=\"Monotext\" /><attribute name=\"VediAnche\" attributetype=\"Link\"><link type=\"external\"><urldest>http://www.w3.org/</urldest></link><text lang=\"it\">Pagina Iniziale W3C</text><text lang=\"en\">World Wide Web Consortium - Web Standards</text></attribute><attribute name=\"CorpoTesto\" attributetype=\"Hypertext\"><hypertext lang=\"it\"><![CDATA[<p>Testo secondo contenuto del gruppo degli Amministratori</p>\n<p>Questo contenuto appartiene al Gruppo degli Amministratori ma dichiarato visibile agli utenti del gruppo del gruppo free.</p>]]></hypertext><hypertext lang=\"en\"><![CDATA[<p>Text of second Administrators Group\'s Content</p>\n<p>This content belongs to the Administrators Group was declared visible to guest users.</p>]]></hypertext></attribute><attribute name=\"Foto\" attributetype=\"Image\" /><attribute name=\"Data\" attributetype=\"Date\"><date>20090330</date></attribute><attribute name=\"Numero\" attributetype=\"Number\"><number>78</number></attribute></attributes><status>DRAFT</status></content>\n','administrators','1.0','admin'),('ART122','ART','Contenuto degli amministratori 3','DRAFT','<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<content id=\"ART122\" typecode=\"ART\" typedescr=\"Articolo rassegna stampa\"><descr>Contenuto degli amministratori 3</descr><groups mainGroup=\"administrators\"><group name=\"customers\" /></groups><categories /><attributes><attribute name=\"Titolo\" attributetype=\"Text\"><text lang=\"it\">Titolo Contenuto degli \"Amministratori\" 3</text></attribute><list attributetype=\"Monolist\" name=\"Autori\" nestedtype=\"Monotext\" /><attribute name=\"VediAnche\" attributetype=\"Link\" /><attribute name=\"CorpoTesto\" attributetype=\"Hypertext\"><hypertext lang=\"it\"><![CDATA[<p>Testo terzo contenuto del gruppo degli Amministratori</p>\n<p>Questo contenuto appartiene al Gruppo degli Amministratori ma dichiarato visibile agli utenti del gruppo customers.</p>]]></hypertext><hypertext lang=\"en\"><![CDATA[<p>Text of third Administrators Group\'s Content</p>\n<p>This content belongs to the Administrators Group was declared visible to customers users.</p>]]></hypertext></attribute><attribute name=\"Foto\" attributetype=\"Image\" /><attribute name=\"Data\" attributetype=\"Date\" /><attribute name=\"Numero\" attributetype=\"Number\" /></attributes><status>DRAFT</status></content>\n','20080721143945','20090327161636','<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<content id=\"ART122\" typecode=\"ART\" typedescr=\"Articolo rassegna stampa\"><descr>Contenuto degli amministratori 3</descr><groups mainGroup=\"administrators\"><group name=\"customers\" /></groups><categories /><attributes><attribute name=\"Titolo\" attributetype=\"Text\"><text lang=\"it\">Titolo Contenuto degli \"Amministratori\" 3</text></attribute><list attributetype=\"Monolist\" name=\"Autori\" nestedtype=\"Monotext\" /><attribute name=\"VediAnche\" attributetype=\"Link\" /><attribute name=\"CorpoTesto\" attributetype=\"Hypertext\"><hypertext lang=\"it\"><![CDATA[<p>Testo terzo contenuto del gruppo degli Amministratori</p>\n<p>Questo contenuto appartiene al Gruppo degli Amministratori ma dichiarato visibile agli utenti del gruppo customers.</p>]]></hypertext><hypertext lang=\"en\"><![CDATA[<p>Text of third Administrators Group\'s Content</p>\n<p>This content belongs to the Administrators Group was declared visible to customers users.</p>]]></hypertext></attribute><attribute name=\"Foto\" attributetype=\"Image\" /><attribute name=\"Data\" attributetype=\"Date\" /><attribute name=\"Numero\" attributetype=\"Number\" /></attributes><status>DRAFT</status></content>\n','administrators','1.0','admin'),('ART179','ART','una descrizione ON LINE','DRAFT','<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<content id=\"ART179\" typecode=\"ART\" typedescr=\"Articolo rassegna stampa\"><descr>una descrizione ON LINE</descr><groups mainGroup=\"free\" /><categories><category id=\"general_cat1\" /><category id=\"general_cat2\" /></categories><attributes><attribute name=\"Titolo\" attributetype=\"Text\" /><list attributetype=\"Monolist\" name=\"Autori\" nestedtype=\"Monotext\" /><attribute name=\"VediAnche\" attributetype=\"Link\" /><attribute name=\"CorpoTesto\" attributetype=\"Hypertext\" /><attribute name=\"Foto\" attributetype=\"Image\" /><attribute name=\"Data\" attributetype=\"Date\"><date>20090716</date></attribute><attribute name=\"Numero\" attributetype=\"Number\" /></attributes><status>DRAFT</status></content>','20051012105533','20080210180714',NULL,'free','0.1','admin'),('ART180','ART','una descrizione','READY','<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<content id=\"ART180\" typecode=\"ART\" typedescr=\"Articolo rassegna stampa\"><descr>una descrizione</descr><groups mainGroup=\"free\" /><categories><category id=\"cat1\" /><category id=\"general_cat1\" /></categories><attributes><attribute name=\"Titolo\" attributetype=\"Text\" /><list attributetype=\"Monolist\" name=\"Autori\" nestedtype=\"Monotext\" /><attribute name=\"VediAnche\" attributetype=\"Link\" /><attribute name=\"CorpoTesto\" attributetype=\"Hypertext\" /><attribute name=\"Foto\" attributetype=\"Image\"><resource resourcetype=\"Image\" id=\"44\" lang=\"it\" /><text lang=\"it\">Descrizione foto</text></attribute><attribute name=\"Data\" attributetype=\"Date\" /><attribute name=\"Numero\" attributetype=\"Number\" /></attributes><status>READY</status></content>\n','20051012105757','20061221161136','<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<content id=\"ART180\" typecode=\"ART\" typedescr=\"Articolo rassegna stampa\"><descr>una descrizione</descr><groups mainGroup=\"free\" /><categories><category id=\"cat1\" /></categories><attributes><attribute name=\"Titolo\" attributetype=\"Text\" /><list attributetype=\"Monolist\" name=\"Autori\" nestedtype=\"Monotext\" /><attribute name=\"VediAnche\" attributetype=\"Link\" /><attribute name=\"CorpoTesto\" attributetype=\"Hypertext\" /><attribute name=\"Foto\" attributetype=\"Image\"><resource resourcetype=\"Image\" id=\"44\" lang=\"it\" /><text lang=\"it\">Descrizione foto</text></attribute><attribute name=\"Data\" attributetype=\"Date\" /><attribute name=\"Numero\" attributetype=\"Number\" /></attributes><status>READY</status></content>\n','free','1.0','admin'),('ART187','ART','una descrizione particolare','DRAFT','<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<content id=\"ART187\" typecode=\"ART\" typedescr=\"Articolo rassegna stampa\"><descr>una descrizione particolare</descr><groups mainGroup=\"free\" /><categories /><attributes><attribute name=\"Titolo\" attributetype=\"Text\" /><list attributetype=\"Monolist\" name=\"Autori\" nestedtype=\"Monotext\" /><attribute name=\"VediAnche\" attributetype=\"Link\" /><attribute name=\"CorpoTesto\" attributetype=\"Hypertext\" /><attribute name=\"Foto\" attributetype=\"Image\" /><attribute name=\"Data\" attributetype=\"Date\" /><attribute name=\"Numero\" attributetype=\"Number\" /></attributes><status>DRAFT</status></content>\n','20051012164415','20060622194219','<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<content id=\"ART187\" typecode=\"ART\" typedescr=\"Articolo rassegna stampa\"><descr>una descrizione particolare</descr><groups mainGroup=\"free\" /><categories /><attributes><attribute name=\"Titolo\" attributetype=\"Text\" /><list attributetype=\"Monolist\" name=\"Autori\" nestedtype=\"Monotext\" /><attribute name=\"VediAnche\" attributetype=\"Link\" /><attribute name=\"CorpoTesto\" attributetype=\"Hypertext\" /><attribute name=\"Foto\" attributetype=\"Image\" /><attribute name=\"Data\" attributetype=\"Date\" /><attribute name=\"Numero\" attributetype=\"Number\" /></attributes><status>DRAFT</status></content>\n','free','1.0','admin'),('EVN103','EVN','Contenuto 1 Coach','DRAFT','<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<content id=\"EVN103\" typecode=\"EVN\" typedescr=\"Evento\"><descr>Contenuto 1 Coach</descr><groups mainGroup=\"coach\" /><categories /><attributes><attribute name=\"Titolo\" attributetype=\"Text\"><text lang=\"it\">Titolo Contenuto 1 Coach</text></attribute><attribute name=\"CorpoTesto\" attributetype=\"Hypertext\"><hypertext lang=\"it\"><![CDATA[Corpo Testo Contenuto 1 Coach]]></hypertext></attribute><attribute name=\"DataInizio\" attributetype=\"Date\"><date>19990415</date></attribute><attribute name=\"DataFine\" attributetype=\"Date\"><date>20000414</date></attribute><attribute name=\"Foto\" attributetype=\"Image\" /><list attributetype=\"Monolist\" name=\"LinkCorrelati\" nestedtype=\"Link\" /></attributes><status>DRAFT</status></content>\n','20061221165150','20061223125859','<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<content id=\"EVN103\" typecode=\"EVN\" typedescr=\"Evento\"><descr>Contenuto 1 Coach</descr><groups mainGroup=\"coach\" /><categories /><attributes><attribute name=\"Titolo\" attributetype=\"Text\"><text lang=\"it\">Titolo Contenuto 1 Coach</text></attribute><attribute name=\"CorpoTesto\" attributetype=\"Hypertext\"><hypertext lang=\"it\"><![CDATA[Corpo Testo Contenuto 1 Coach]]></hypertext></attribute><attribute name=\"DataInizio\" attributetype=\"Date\"><date>19990415</date></attribute><attribute name=\"DataFine\" attributetype=\"Date\"><date>20000414</date></attribute><attribute name=\"Foto\" attributetype=\"Image\" /><list attributetype=\"Monolist\" name=\"LinkCorrelati\" nestedtype=\"Link\" /></attributes><status>DRAFT</status></content>\n','coach','1.0','admin'),('EVN191','EVN','Evento 1','DRAFT','<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<content id=\"EVN191\" typecode=\"EVN\" typedescr=\"Evento\"><descr>Evento 1</descr><groups mainGroup=\"free\" /><categories /><attributes><attribute name=\"Titolo\" attributetype=\"Text\"><text lang=\"it\">Titolo A - Evento 1</text><text lang=\"en\">Title C - Event 1</text></attribute><attribute name=\"CorpoTesto\" attributetype=\"Hypertext\"><hypertext lang=\"it\"><![CDATA[<p>CorpoTesto Evento 1</p>]]></hypertext></attribute><attribute name=\"DataInizio\" attributetype=\"Date\"><date>19960417</date></attribute><attribute name=\"DataFine\" attributetype=\"Date\"><date>19960617</date></attribute><attribute name=\"Foto\" attributetype=\"Image\" /><list attributetype=\"Monolist\" name=\"LinkCorrelati\" nestedtype=\"Link\" /></attributes><status>DRAFT</status></content>\n','20060418142200','20061221161157','<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<content id=\"EVN191\" typecode=\"EVN\" typedescr=\"Evento\"><descr>Evento 1</descr><groups mainGroup=\"free\" /><categories /><attributes><attribute name=\"Titolo\" attributetype=\"Text\"><text lang=\"it\">Titolo A - Evento 1</text><text lang=\"en\">Title C - Event 1</text></attribute><attribute name=\"CorpoTesto\" attributetype=\"Hypertext\"><hypertext lang=\"it\"><![CDATA[<p>CorpoTesto Evento 1</p>]]></hypertext></attribute><attribute name=\"DataInizio\" attributetype=\"Date\"><date>19960417</date></attribute><attribute name=\"DataFine\" attributetype=\"Date\"><date>19960617</date></attribute><attribute name=\"Foto\" attributetype=\"Image\" /><list attributetype=\"Monolist\" name=\"LinkCorrelati\" nestedtype=\"Link\" /></attributes><status>DRAFT</status></content>\n','free','1.0','admin'),('EVN192','EVN','Evento 2','DRAFT','<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<content id=\"EVN192\" typecode=\"EVN\" typedescr=\"Evento\"><descr>Evento 2</descr><groups mainGroup=\"free\" /><categories><category id=\"evento\" /><category id=\"general_cat1\" /></categories><attributes><attribute name=\"Titolo\" attributetype=\"Text\"><text lang=\"it\">Titolo B - Evento 2</text><text lang=\"en\">Title B - Event 2</text></attribute><attribute name=\"CorpoTesto\" attributetype=\"Hypertext\"><hypertext lang=\"it\"><![CDATA[<p>CorpoTesto evento 2</p>]]></hypertext></attribute><attribute name=\"DataInizio\" attributetype=\"Date\"><date>19990414</date></attribute><attribute name=\"DataFine\" attributetype=\"Date\"><date>19990614</date></attribute><attribute name=\"Foto\" attributetype=\"Image\" /><list attributetype=\"Monolist\" name=\"LinkCorrelati\" nestedtype=\"Link\" /></attributes><status>DRAFT</status></content>\n','20060418142303','20061221161202','<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<content id=\"EVN192\" typecode=\"EVN\" typedescr=\"Evento\"><descr>Evento 2</descr><groups mainGroup=\"free\" /><categories><category id=\"evento\" /></categories><attributes><attribute name=\"Titolo\" attributetype=\"Text\"><text lang=\"it\">Titolo B - Evento 2</text><text lang=\"en\">Title B - Event 2</text></attribute><attribute name=\"CorpoTesto\" attributetype=\"Hypertext\"><hypertext lang=\"it\"><![CDATA[<p>CorpoTesto evento 2</p>]]></hypertext></attribute><attribute name=\"DataInizio\" attributetype=\"Date\"><date>19990414</date></attribute><attribute name=\"DataFine\" attributetype=\"Date\"><date>19990614</date></attribute><attribute name=\"Foto\" attributetype=\"Image\" /><list attributetype=\"Monolist\" name=\"LinkCorrelati\" nestedtype=\"Link\" /></attributes><status>DRAFT</status></content>\n','free','1.0','admin'),('EVN193','EVN','Evento 3','DRAFT','<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<content id=\"EVN193\" typecode=\"EVN\" typedescr=\"Evento\"><descr>Evento 3</descr><groups mainGroup=\"free\" /><categories><category id=\"evento\" /><category id=\"general_cat2\" /></categories><attributes><attribute name=\"Titolo\" attributetype=\"Text\"><text lang=\"it\">Titolo C - Evento 3</text><text lang=\"en\">Title D - Evento 3</text></attribute><attribute name=\"CorpoTesto\" attributetype=\"Hypertext\"><hypertext lang=\"it\"><![CDATA[<p>CorpoTesto Evento 3</p>]]></hypertext></attribute><attribute name=\"DataInizio\" attributetype=\"Date\"><date>20170412</date></attribute><attribute name=\"DataFine\" attributetype=\"Date\"><date>20170912</date></attribute><attribute name=\"Foto\" attributetype=\"Image\" /><list attributetype=\"Monolist\" name=\"LinkCorrelati\" nestedtype=\"Link\"><attribute name=\"LinkCorrelati\" attributetype=\"Link\"><link type=\"content\"><contentdest>ART1</contentdest></link><text lang=\"it\">Link 1</text></attribute><attribute name=\"LinkCorrelati\" attributetype=\"Link\"><link type=\"page\"><pagedest>pagina_11</pagedest></link><text lang=\"it\">Link 2</text></attribute></list></attributes><status>DRAFT</status></content>\n','20060418142409','20061221161125','<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<content id=\"EVN193\" typecode=\"EVN\" typedescr=\"Evento\"><descr>Evento 3</descr><groups mainGroup=\"free\" /><categories><category id=\"evento\" /></categories><attributes><attribute name=\"Titolo\" attributetype=\"Text\"><text lang=\"it\">Titolo C - Evento 3</text><text lang=\"en\">Title D - Evento 3</text></attribute><attribute name=\"CorpoTesto\" attributetype=\"Hypertext\"><hypertext lang=\"it\"><![CDATA[<p>CorpoTesto Evento 3</p>]]></hypertext></attribute><attribute name=\"DataInizio\" attributetype=\"Date\"><date>20170412</date></attribute><attribute name=\"DataFine\" attributetype=\"Date\"><date>20170912</date></attribute><attribute name=\"Foto\" attributetype=\"Image\" /><list attributetype=\"Monolist\" name=\"LinkCorrelati\" nestedtype=\"Link\"><attribute name=\"LinkCorrelati\" attributetype=\"Link\"><link type=\"content\"><contentdest>ART1</contentdest></link><text lang=\"it\">Link 1</text></attribute><attribute name=\"LinkCorrelati\" attributetype=\"Link\"><link type=\"page\"><pagedest>pagina_11</pagedest></link><text lang=\"it\">Link 2</text></attribute></list></attributes><status>DRAFT</status></content>\n','free','1.0','admin'),('EVN194','EVN','Evento 4','DRAFT','<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<content id=\"EVN194\" typecode=\"EVN\" typedescr=\"Evento\"><descr>Evento 4</descr><groups mainGroup=\"free\" /><categories /><attributes><attribute name=\"Titolo\" attributetype=\"Text\"><text lang=\"it\">Titolo D - Evento 4</text><text lang=\"en\">Title A - Event 4</text></attribute><attribute name=\"CorpoTesto\" attributetype=\"Hypertext\"><hypertext lang=\"it\"><![CDATA[<p>CorpoTesto&nbsp;Evento 4</p>]]></hypertext></attribute><attribute name=\"DataInizio\" attributetype=\"Date\"><date>20220219</date></attribute><attribute name=\"DataFine\" attributetype=\"Date\"><date>20220419</date></attribute><attribute name=\"Foto\" attributetype=\"Image\" /><list attributetype=\"Monolist\" name=\"LinkCorrelati\" nestedtype=\"Link\"><attribute name=\"LinkCorrelati\" attributetype=\"Link\"><link type=\"content\"><contentdest>ART1</contentdest></link><text lang=\"it\">Link 1</text></attribute><attribute name=\"LinkCorrelati\" attributetype=\"Link\"><link type=\"page\"><pagedest>pagina_11</pagedest></link><text lang=\"it\">Link 2</text></attribute></list></attributes><status>DRAFT</status></content>\n','20060418142507','20061221161128','<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<content id=\"EVN194\" typecode=\"EVN\" typedescr=\"Evento\"><descr>Evento 4</descr><groups mainGroup=\"free\" /><categories /><attributes><attribute name=\"Titolo\" attributetype=\"Text\"><text lang=\"it\">Titolo D - Evento 4</text><text lang=\"en\">Title A - Event 4</text></attribute><attribute name=\"CorpoTesto\" attributetype=\"Hypertext\"><hypertext lang=\"it\"><![CDATA[<p>CorpoTesto&nbsp;Evento 4</p>]]></hypertext></attribute><attribute name=\"DataInizio\" attributetype=\"Date\"><date>20220219</date></attribute><attribute name=\"DataFine\" attributetype=\"Date\"><date>20220419</date></attribute><attribute name=\"Foto\" attributetype=\"Image\" /><list attributetype=\"Monolist\" name=\"LinkCorrelati\" nestedtype=\"Link\"><attribute name=\"LinkCorrelati\" attributetype=\"Link\"><link type=\"content\"><contentdest>ART1</contentdest></link><text lang=\"it\">Link 1</text></attribute><attribute name=\"LinkCorrelati\" attributetype=\"Link\"><link type=\"page\"><pagedest>pagina_11</pagedest></link><text lang=\"it\">Link 2</text></attribute></list></attributes><status>DRAFT</status></content>\n','free','1.0','admin'),('EVN20','EVN','Mostra zootecnica','DRAFT','<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<content id=\"EVN20\" typecode=\"EVN\" typedescr=\"Evento\"><descr>Mostra zootecnica</descr><groups mainGroup=\"free\" /><categories /><attributes><attribute name=\"Titolo\" attributetype=\"Text\"><text lang=\"it\">Mostra Zootecnica</text></attribute><attribute name=\"CorpoTesto\" attributetype=\"Hypertext\"><hypertext lang=\"it\"><![CDATA[<p>Annuncio svolgimento mostra zootecnicaMostra</p>]]></hypertext></attribute><attribute name=\"DataInizio\" attributetype=\"Date\"><date>20060213</date></attribute><attribute name=\"DataFine\" attributetype=\"Date\"><date>20060220</date></attribute><attribute name=\"Foto\" attributetype=\"Image\" /><list attributetype=\"Monolist\" name=\"LinkCorrelati\" nestedtype=\"Link\" /></attributes><status>DRAFT</status></content>\n','20080209100217','20080209123357','<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<content id=\"EVN20\" typecode=\"EVN\" typedescr=\"Evento\"><descr>Mostra zootecnica</descr><groups mainGroup=\"free\" /><categories /><attributes><attribute name=\"Titolo\" attributetype=\"Text\"><text lang=\"it\">Mostra Zootecnica</text></attribute><attribute name=\"CorpoTesto\" attributetype=\"Hypertext\"><hypertext lang=\"it\"><![CDATA[<p>Annuncio svolgimento mostra zootecnicaMostra</p>]]></hypertext></attribute><attribute name=\"DataInizio\" attributetype=\"Date\"><date>20060213</date></attribute><attribute name=\"DataFine\" attributetype=\"Date\"><date>20060220</date></attribute><attribute name=\"Foto\" attributetype=\"Image\" /><list attributetype=\"Monolist\" name=\"LinkCorrelati\" nestedtype=\"Link\" /></attributes><status>DRAFT</status></content>\n','free','1.0','admin'),('EVN21','EVN','Sagra delle fragole','DRAFT','<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<content id=\"EVN21\" typecode=\"EVN\" typedescr=\"Evento\"><descr>Sagra delle fragole</descr><groups mainGroup=\"free\" /><categories /><attributes><attribute name=\"Titolo\" attributetype=\"Text\"><text lang=\"it\">Mostra delle fragole</text></attribute><attribute name=\"CorpoTesto\" attributetype=\"Hypertext\"><hypertext lang=\"it\"><![CDATA[<p>Mostre delle fragole</p>]]></hypertext></attribute><attribute name=\"DataInizio\" attributetype=\"Date\"><date>20060113</date></attribute><attribute name=\"DataFine\" attributetype=\"Date\"><date>20060304</date></attribute><attribute name=\"Foto\" attributetype=\"Image\" /><list attributetype=\"Monolist\" name=\"LinkCorrelati\" nestedtype=\"Link\" /></attributes><status>DRAFT</status></content>\n','20080209123547','20080209123637','<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<content id=\"EVN21\" typecode=\"EVN\" typedescr=\"Evento\"><descr>Sagra delle fragole</descr><groups mainGroup=\"free\" /><categories /><attributes><attribute name=\"Titolo\" attributetype=\"Text\"><text lang=\"it\">Mostra delle fragole</text></attribute><attribute name=\"CorpoTesto\" attributetype=\"Hypertext\"><hypertext lang=\"it\"><![CDATA[<p>Mostre delle fragole</p>]]></hypertext></attribute><attribute name=\"DataInizio\" attributetype=\"Date\"><date>20060113</date></attribute><attribute name=\"DataFine\" attributetype=\"Date\"><date>20060304</date></attribute><attribute name=\"Foto\" attributetype=\"Image\" /><list attributetype=\"Monolist\" name=\"LinkCorrelati\" nestedtype=\"Link\" /></attributes><status>DRAFT</status></content>\n','free','1.0','admin'),('EVN23','EVN','Collezione Ingrao','DRAFT','<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<content id=\"EVN23\" typecode=\"EVN\" typedescr=\"Evento\"><descr>Collezione Ingrao</descr><groups mainGroup=\"free\" /><categories /><attributes><attribute name=\"Titolo\" attributetype=\"Text\"><text lang=\"it\">Collezione Ingri</text></attribute><attribute name=\"CorpoTesto\" attributetype=\"Hypertext\"><hypertext lang=\"it\"><![CDATA[Nei rinnovati spazi della Galleria Comunale d\'Arte sono ospitate le opere pittoriche e scultoree, quelle che dell\'intero lascito di Giovanni Ingri rientrano nel periodo moderno e contemporaneo.]]></hypertext></attribute><attribute name=\"DataInizio\" attributetype=\"Date\"><date>20080213</date></attribute><attribute name=\"DataFine\" attributetype=\"Date\"><date>20080222</date></attribute><attribute name=\"Foto\" attributetype=\"Image\" /><list attributetype=\"Monolist\" name=\"LinkCorrelati\" nestedtype=\"Link\" /></attributes><status>DRAFT</status></content>\n','20080209100541','20080209100546','<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<content id=\"EVN23\" typecode=\"EVN\" typedescr=\"Evento\"><descr>Collezione Ingrao</descr><groups mainGroup=\"free\" /><categories /><attributes><attribute name=\"Titolo\" attributetype=\"Text\"><text lang=\"it\">Collezione Ingri</text></attribute><attribute name=\"CorpoTesto\" attributetype=\"Hypertext\"><hypertext lang=\"it\"><![CDATA[Nei rinnovati spazi della Galleria Comunale d\'Arte sono ospitate le opere pittoriche e scultoree, quelle che dell\'intero lascito di Giovanni Ingri rientrano nel periodo moderno e contemporaneo.]]></hypertext></attribute><attribute name=\"DataInizio\" attributetype=\"Date\"><date>20080213</date></attribute><attribute name=\"DataFine\" attributetype=\"Date\"><date>20080222</date></attribute><attribute name=\"Foto\" attributetype=\"Image\" /><list attributetype=\"Monolist\" name=\"LinkCorrelati\" nestedtype=\"Link\" /></attributes><status>DRAFT</status></content>\n','free','1.0','admin'),('EVN24','EVN','Castello dei bambini','DRAFT','<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<content id=\"EVN24\" typecode=\"EVN\" typedescr=\"Evento\"><descr>Castello dei bambini</descr><groups mainGroup=\"free\" /><categories /><attributes><attribute name=\"Titolo\" attributetype=\"Text\"><text lang=\"it\">Castello dei bambini</text></attribute><attribute name=\"CorpoTesto\" attributetype=\"Hypertext\"><hypertext lang=\"it\"><![CDATA[<p>Descrizion evento Castello dei bambini</p>]]></hypertext></attribute><attribute name=\"DataInizio\" attributetype=\"Date\"><date>20090318</date></attribute><attribute name=\"DataFine\" attributetype=\"Date\"><date>20090326</date></attribute><attribute name=\"Foto\" attributetype=\"Image\" /><list attributetype=\"Monolist\" name=\"LinkCorrelati\" nestedtype=\"Link\" /></attributes><status>DRAFT</status></content>\n','20080209100714','20080209100719','<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<content id=\"EVN24\" typecode=\"EVN\" typedescr=\"Evento\"><descr>Castello dei bambini</descr><groups mainGroup=\"free\" /><categories /><attributes><attribute name=\"Titolo\" attributetype=\"Text\"><text lang=\"it\">Castello dei bambini</text></attribute><attribute name=\"CorpoTesto\" attributetype=\"Hypertext\"><hypertext lang=\"it\"><![CDATA[<p>Descrizion evento Castello dei bambini</p>]]></hypertext></attribute><attribute name=\"DataInizio\" attributetype=\"Date\"><date>20090318</date></attribute><attribute name=\"DataFine\" attributetype=\"Date\"><date>20090326</date></attribute><attribute name=\"Foto\" attributetype=\"Image\" /><list attributetype=\"Monolist\" name=\"LinkCorrelati\" nestedtype=\"Link\" /></attributes><status>DRAFT</status></content>\n','free','1.0','admin'),('EVN25','EVN','TEATRO DELLE MERAVIGLIE','DRAFT','<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<content id=\"EVN25\" typecode=\"EVN\" typedescr=\"Evento\"><descr>TEATRO DELLE MERAVIGLIE</descr><groups mainGroup=\"coach\"><group name=\"free\" /></groups><categories /><attributes><attribute name=\"Titolo\" attributetype=\"Text\"><text lang=\"it\">TEATRO DELLE MERAVIGLIE</text></attribute><attribute name=\"CorpoTesto\" attributetype=\"Hypertext\"><hypertext lang=\"it\"><![CDATA[<p>TEATRO DELLE MERAVIGLIE  Laboratori Creativi</p>]]></hypertext></attribute><attribute name=\"DataInizio\" attributetype=\"Date\"><date>20071212</date></attribute><attribute name=\"DataFine\" attributetype=\"Date\"><date>20071222</date></attribute><attribute name=\"Foto\" attributetype=\"Image\" /><list attributetype=\"Monolist\" name=\"LinkCorrelati\" nestedtype=\"Link\" /></attributes><status>DRAFT</status></content>\n','20080209100902','20080209100915','<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<content id=\"EVN25\" typecode=\"EVN\" typedescr=\"Evento\"><descr>TEATRO DELLE MERAVIGLIE</descr><groups mainGroup=\"coach\"><group name=\"free\" /></groups><categories /><attributes><attribute name=\"Titolo\" attributetype=\"Text\"><text lang=\"it\">TEATRO DELLE MERAVIGLIE</text></attribute><attribute name=\"CorpoTesto\" attributetype=\"Hypertext\"><hypertext lang=\"it\"><![CDATA[<p>TEATRO DELLE MERAVIGLIE  Laboratori Creativi</p>]]></hypertext></attribute><attribute name=\"DataInizio\" attributetype=\"Date\"><date>20071212</date></attribute><attribute name=\"DataFine\" attributetype=\"Date\"><date>20071222</date></attribute><attribute name=\"Foto\" attributetype=\"Image\" /><list attributetype=\"Monolist\" name=\"LinkCorrelati\" nestedtype=\"Link\" /></attributes><status>DRAFT</status></content>\n','coach','1.0','admin'),('EVN41','EVN','Mostra della ciliegia','DRAFT','<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<content id=\"EVN41\" typecode=\"EVN\" typedescr=\"Evento\"><descr>Mostra della ciliegia</descr><groups mainGroup=\"coach\" /><categories /><attributes><attribute name=\"Titolo\" attributetype=\"Text\"><text lang=\"it\">Sagra della ciliegia</text></attribute><attribute name=\"CorpoTesto\" attributetype=\"Hypertext\"><hypertext lang=\"it\"><![CDATA[<p>Sagra della ciliegia</p>]]></hypertext></attribute><attribute name=\"DataInizio\" attributetype=\"Date\"><date>20080106</date></attribute><attribute name=\"DataFine\" attributetype=\"Date\"><date>20080124</date></attribute><attribute name=\"Foto\" attributetype=\"Image\" /><list attributetype=\"Monolist\" name=\"LinkCorrelati\" nestedtype=\"Link\" /></attributes><status>DRAFT</status></content>\n','20080209102901','20080209102903','<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<content id=\"EVN41\" typecode=\"EVN\" typedescr=\"Evento\"><descr>Mostra della ciliegia</descr><groups mainGroup=\"coach\" /><categories /><attributes><attribute name=\"Titolo\" attributetype=\"Text\"><text lang=\"it\">Sagra della ciliegia</text></attribute><attribute name=\"CorpoTesto\" attributetype=\"Hypertext\"><hypertext lang=\"it\"><![CDATA[<p>Sagra della ciliegia</p>]]></hypertext></attribute><attribute name=\"DataInizio\" attributetype=\"Date\"><date>20080106</date></attribute><attribute name=\"DataFine\" attributetype=\"Date\"><date>20080124</date></attribute><attribute name=\"Foto\" attributetype=\"Image\" /><list attributetype=\"Monolist\" name=\"LinkCorrelati\" nestedtype=\"Link\" /></attributes><status>DRAFT</status></content>\n','coach','1.0','admin'),('RAH1','RAH','Articolo','DRAFT','<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<content id=\"RAH1\" typecode=\"RAH\" typedescr=\"Tipo_Semplice\"><descr>Articolo</descr><groups mainGroup=\"free\" /><categories /><attributes><attribute name=\"Titolo\" attributetype=\"Text\"><text lang=\"it\">Un bel titolo</text></attribute><attribute name=\"CorpoTesto\" attributetype=\"Hypertext\"><hypertext lang=\"it\"><![CDATA[test test]]></hypertext></attribute><attribute name=\"Foto\" attributetype=\"Image\" /><attribute name=\"email\" attributetype=\"Monotext\" /><attribute name=\"Numero\" attributetype=\"Number\" /><attribute name=\"Correlati\" attributetype=\"Link\" /><attribute name=\"Allegati\" attributetype=\"Attach\"><resource resourcetype=\"Attach\" id=\"7\" lang=\"it\" /><text lang=\"it\">lop</text><text lang=\"en\">linux</text></attribute><attribute name=\"Checkbox\" attributetype=\"CheckBox\" /></attributes><status>DRAFT</status></content>\n','20050503181212','20061221161143','<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<content id=\"RAH1\" typecode=\"RAH\" typedescr=\"Tipo_Semplice\"><descr>Articolo</descr><groups mainGroup=\"free\" /><categories /><attributes><attribute name=\"Titolo\" attributetype=\"Text\"><text lang=\"it\">Un bel titolo</text></attribute><attribute name=\"CorpoTesto\" attributetype=\"Hypertext\"><hypertext lang=\"it\"><![CDATA[test test]]></hypertext></attribute><attribute name=\"Foto\" attributetype=\"Image\" /><attribute name=\"email\" attributetype=\"Monotext\" /><attribute name=\"Numero\" attributetype=\"Number\" /><attribute name=\"Correlati\" attributetype=\"Link\" /><attribute name=\"Allegati\" attributetype=\"Attach\"><resource resourcetype=\"Attach\" id=\"7\" lang=\"it\" /><text lang=\"it\">lop</text><text lang=\"en\">linux</text></attribute><attribute name=\"Checkbox\" attributetype=\"CheckBox\" /></attributes><status>DRAFT</status></content>\n','free','1.0','admin'),('RAH101','RAH','Contenuto 1 Customers','DRAFT','<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<content id=\"RAH101\" typecode=\"RAH\" typedescr=\"Tipo_Semplice\"><descr>Contenuto 1 Customers</descr><groups mainGroup=\"customers\" /><categories /><attributes><attribute name=\"Titolo\" attributetype=\"Text\"><text lang=\"it\">Titolo Contenuto 1 Customers</text></attribute><attribute name=\"CorpoTesto\" attributetype=\"Hypertext\"><hypertext lang=\"it\"><![CDATA[CorpoTesto Contenuto 1 Customers]]></hypertext></attribute><attribute name=\"Foto\" attributetype=\"Image\" /><attribute name=\"email\" attributetype=\"Monotext\" /><attribute name=\"Numero\" attributetype=\"Number\" /><attribute name=\"Correlati\" attributetype=\"Link\" /><attribute name=\"Allegati\" attributetype=\"Attach\" /><attribute name=\"Checkbox\" attributetype=\"CheckBox\" /></attributes><status>DRAFT</status></content>\n','20061221164536','20061221165755','<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<content id=\"RAH101\" typecode=\"RAH\" typedescr=\"Tipo_Semplice\"><descr>Contenuto 1 Customers</descr><groups mainGroup=\"customers\" /><categories /><attributes><attribute name=\"Titolo\" attributetype=\"Text\"><text lang=\"it\">Titolo Contenuto 1 Customers</text></attribute><attribute name=\"CorpoTesto\" attributetype=\"Hypertext\"><hypertext lang=\"it\"><![CDATA[CorpoTesto Contenuto 1 Customers]]></hypertext></attribute><attribute name=\"Foto\" attributetype=\"Image\" /><attribute name=\"email\" attributetype=\"Monotext\" /><attribute name=\"Numero\" attributetype=\"Number\" /><attribute name=\"Correlati\" attributetype=\"Link\" /><attribute name=\"Allegati\" attributetype=\"Attach\" /><attribute name=\"Checkbox\" attributetype=\"CheckBox\" /></attributes><status>DRAFT</status></content>\n','customers','1.0','admin');
/*!40000 ALTER TABLE `contents` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `contentsearch`
--

DROP TABLE IF EXISTS `contentsearch`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `contentsearch` (
  `contentid` varchar(16) NOT NULL,
  `attrname` varchar(30) NOT NULL,
  `textvalue` varchar(255) DEFAULT NULL,
  `datevalue` date DEFAULT NULL,
  `numvalue` int(11) DEFAULT NULL,
  `langcode` varchar(2) DEFAULT NULL,
  KEY `contentsearch_contentid_fkey` (`contentid`),
  CONSTRAINT `contentsearch_contentid_fkey` FOREIGN KEY (`contentid`) REFERENCES `contents` (`contentid`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contentsearch`
--

LOCK TABLES `contentsearch` WRITE;
/*!40000 ALTER TABLE `contentsearch` DISABLE KEYS */;
INSERT INTO `contentsearch` VALUES ('ART120','Data',NULL,'2009-03-28',NULL,NULL),('ART120','Numero',NULL,NULL,7,NULL),('ART1','Data',NULL,'2004-03-10',NULL,NULL),('ART104','Data',NULL,'2007-01-04',NULL,NULL),('ART121','Data',NULL,'2009-03-30',NULL,NULL),('ART121','Numero',NULL,NULL,78,NULL),('ART112','Data',NULL,'2006-02-13',NULL,NULL),('ART111','Data',NULL,'2006-12-13',NULL,NULL),('EVN20','Titolo','Mostra Zootecnica',NULL,NULL,'it'),('EVN20','Titolo','Mostra Zootecnica',NULL,NULL,'en'),('EVN20','DataInizio',NULL,'2006-02-13',NULL,NULL),('EVN20','DataFine',NULL,'2006-02-20',NULL,NULL),('EVN192','Titolo','Titolo B - Evento 2',NULL,NULL,'it'),('EVN192','Titolo','Title B - Event 2',NULL,NULL,'en'),('EVN192','DataInizio',NULL,'1999-04-14',NULL,NULL),('EVN192','DataFine',NULL,'1999-06-14',NULL,NULL),('EVN103','Titolo','Titolo Contenuto 1 Coach',NULL,NULL,'it'),('EVN103','Titolo','Titolo Contenuto 1 Coach',NULL,NULL,'en'),('EVN103','DataInizio',NULL,'1999-04-15',NULL,NULL),('EVN103','DataFine',NULL,'2000-04-14',NULL,NULL),('EVN23','Titolo','Collezione Ingri',NULL,NULL,'it'),('EVN23','Titolo','Collezione Ingri',NULL,NULL,'en'),('EVN23','DataInizio',NULL,'2008-02-13',NULL,NULL),('EVN23','DataFine',NULL,'2008-02-22',NULL,NULL),('EVN24','Titolo','Castello dei bambini',NULL,NULL,'it'),('EVN24','Titolo','Castello dei bambini',NULL,NULL,'en'),('EVN24','DataInizio',NULL,'2009-03-18',NULL,NULL),('EVN24','DataFine',NULL,'2009-03-26',NULL,NULL),('EVN41','Titolo','Sagra della ciliegia',NULL,NULL,'it'),('EVN41','Titolo','Sagra della ciliegia',NULL,NULL,'en'),('EVN41','DataInizio',NULL,'2008-01-06',NULL,NULL),('EVN41','DataFine',NULL,'2008-01-24',NULL,NULL),('EVN21','Titolo','Mostra delle fragole',NULL,NULL,'it'),('EVN21','Titolo','Mostra delle fragole',NULL,NULL,'en'),('EVN21','DataInizio',NULL,'2006-01-13',NULL,NULL),('EVN21','DataFine',NULL,'2006-03-04',NULL,NULL),('EVN25','Titolo','TEATRO DELLE MERAVIGLIE',NULL,NULL,'it'),('EVN25','Titolo','TEATRO DELLE MERAVIGLIE',NULL,NULL,'en'),('EVN25','DataInizio',NULL,'2007-12-12',NULL,NULL),('EVN25','DataFine',NULL,'2007-12-22',NULL,NULL),('EVN191','Titolo','Titolo A - Evento 1',NULL,NULL,'it'),('EVN191','Titolo','Title C - Event 1',NULL,NULL,'en'),('EVN191','DataInizio',NULL,'1996-04-17',NULL,NULL),('EVN191','DataFine',NULL,'1996-06-17',NULL,NULL),('EVN194','Titolo','Titolo D - Evento 4',NULL,NULL,'it'),('EVN194','Titolo','Title A - Event 4',NULL,NULL,'en'),('EVN194','DataInizio',NULL,'2022-02-19',NULL,NULL),('EVN194','DataFine',NULL,'2022-04-19',NULL,NULL),('EVN193','Titolo','Titolo C - Evento 3',NULL,NULL,'it'),('EVN193','Titolo','Title D - Evento 3',NULL,NULL,'en'),('EVN193','DataInizio',NULL,'2017-04-12',NULL,NULL),('EVN193','DataFine',NULL,'2017-09-12',NULL,NULL);
/*!40000 ALTER TABLE `contentsearch` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `localstrings`
--

DROP TABLE IF EXISTS `localstrings`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `localstrings` (
  `keycode` varchar(50) NOT NULL,
  `langcode` varchar(2) NOT NULL,
  `stringvalue` longtext NOT NULL,
  PRIMARY KEY (`keycode`,`langcode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `localstrings`
--

LOCK TABLES `localstrings` WRITE;
/*!40000 ALTER TABLE `localstrings` DISABLE KEYS */;
INSERT INTO `localstrings` VALUES ('PAGE','en','page'),('PAGE','it','pagina'),('PAGE_MODEL','en','page model'),('PAGE_MODEL','it','modello pagina'),('PAGE_TITLE','en','page title'),('PAGE_TITLE','it','titolo pagina');
/*!40000 ALTER TABLE `localstrings` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pagemodels`
--

DROP TABLE IF EXISTS `pagemodels`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pagemodels` (
  `code` varchar(40) NOT NULL,
  `descr` varchar(50) NOT NULL,
  `frames` longtext,
  `plugincode` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pagemodels`
--

LOCK TABLES `pagemodels` WRITE;
/*!40000 ALTER TABLE `pagemodels` DISABLE KEYS */;
INSERT INTO `pagemodels` VALUES ('home','Modello home page','<frames>\n	<frame pos=\"0\"><descr>Box sinistra alto</descr></frame>\n	<frame pos=\"1\"><descr>Box sinistra basso</descr></frame>\n	<frame pos=\"2\" main=\"true\"><descr>Box centrale 1</descr></frame>\n	<frame pos=\"3\"><descr>Box centrale 2</descr></frame>\n	<frame pos=\"4\"><descr>Box destra alto</descr></frame>\n	<frame pos=\"5\"><descr>Box destra basso</descr></frame>\n</frames>',NULL),('internal','Internal Page','<frames>\n	<frame pos=\"0\">\n		<descr>Choose Language</descr>\n	</frame>\n	<frame pos=\"1\">\n		<descr>Search Form</descr>\n	</frame>\n	<frame pos=\"2\">\n		<descr>Breadcrumbs</descr>\n	</frame>\n	<frame pos=\"3\">\n		<descr>First Column: Box 1</descr>\n		<defaultShowlet code=\"leftmenu\">\n			<properties>\n				<property key=\"navSpec\">code(homepage).subtree(1)</property>\n			</properties>\n		</defaultShowlet>		\n	</frame>\n	<frame pos=\"4\">\n		<descr>First Column: Box 2</descr>\n	</frame>\n	<frame pos=\"5\" main=\"true\">\n		<descr>Main Column: Box 1</descr>\n	</frame>\n	<frame pos=\"6\">\n		<descr>Main Column: Box 2</descr>\n	</frame>\n	<frame pos=\"7\">\n		<descr>Third Column: Box 1</descr>\n	</frame>\n	<frame pos=\"8\">\n		<descr>Third Column: Box 2</descr>\n	</frame>		\n</frames>',NULL),('service','Modello pagine di servizio','<frames>\n	<frame pos=\"0\"><descr>Navigazione orizzontale</descr></frame>\n	<frame pos=\"1\"><descr>Lingue</descr></frame>\n	<frame pos=\"2\"><descr>Navigazione verticale sinistra</descr></frame>\n	<frame pos=\"3\" main=\"true\"><descr>Area principale</descr></frame>\n</frames>',NULL);
/*!40000 ALTER TABLE `pagemodels` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pages`
--

DROP TABLE IF EXISTS `pages`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pages` (
  `code` varchar(30) NOT NULL,
  `parentcode` varchar(30) DEFAULT NULL,
  `pos` int(11) NOT NULL,
  `modelcode` varchar(40) NOT NULL,
  `titles` longtext,
  `groupcode` varchar(30) NOT NULL,
  `showinmenu` tinyint(4) NOT NULL,
  `extraconfig` longtext,
  PRIMARY KEY (`code`),
  KEY `pages_modelcode_fkey` (`modelcode`),
  CONSTRAINT `pages_modelcode_fkey` FOREIGN KEY (`modelcode`) REFERENCES `pagemodels` (`code`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pages`
--

LOCK TABLES `pages` WRITE;
/*!40000 ALTER TABLE `pages` DISABLE KEYS */;
INSERT INTO `pages` VALUES ('administrators_page','homepage',6,'home','<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<properties>\n<property key=\"en\">Administrators Page</property>\n<property key=\"it\">Pagina gruppo Amministratori</property>\n</properties>','administrators',1,NULL),('coach_page','homepage',4,'home','<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<properties>\n<property key=\"en\">Coach Page</property>\n<property key=\"it\">Pagina gruppo Coach</property>\n</properties>\n\n','coach',1,NULL),('contentview','service',4,'home','<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<properties>\n<property key=\"en\">Content Publishing</property>\n<property key=\"it\">Publicazione Contenuto</property>\n</properties>\n\n','free',1,'<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<config>\n  <useextratitles>false</useextratitles>\n</config>\n\n'),('customers_page','homepage',5,'home','<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<properties>\n<property key=\"en\">Customers Page</property>\n<property key=\"it\">Pagina gruppo Customers</property>\n</properties>\n\n','customers',1,NULL),('customer_subpage_1','customers_page',1,'home','<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<properties>\n<property key=\"en\">Customer SubPage 1</property>\n<property key=\"it\">Customer SubPage 1</property>\n</properties>\n\n','customers',0,'<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<config>\n  <useextratitles>false</useextratitles>\n  <extragroups>\n    <group name=\"coach\" />\n  </extragroups>\n</config>\n\n'),('customer_subpage_2','customers_page',2,'home','<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<properties>\n<property key=\"en\">Customer SubPage 2</property>\n<property key=\"it\">Customer SubPage 2</property>\n</properties>\n\n','customers',0,'<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<config />\n\n'),('errorpage','service',5,'service','<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<properties>\n<property key=\"it\">Pagina di errore</property>\n</properties>\n\n','free',1,NULL),('homepage','homepage',-1,'home','<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<properties>\n<property key=\"en\">Start Page</property>\n<property key=\"it\">Pagina iniziale</property>\n</properties>\n\n','free',1,NULL),('login','service',3,'service','<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<properties>\n<property key=\"it\">Pagina di login</property>\n</properties>\n\n','free',1,NULL),('notfound','service',2,'service','<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<properties>\n<property key=\"it\">Pagina non trovata</property>\n</properties>\n\n','free',1,NULL),('pagina_1','homepage',2,'home','<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<properties>\n<property key=\"en\">Page 1</property>\n<property key=\"it\">Pagina 1</property>\n</properties>\n\n','free',1,'<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<config>\n  <useextratitles>false</useextratitles>\n</config>\n\n'),('pagina_11','pagina_1',1,'home','<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<properties>\n<property key=\"en\">Page 1-1</property>\n<property key=\"it\">Pagina 1-1</property>\n</properties>\n\n','free',1,NULL),('pagina_12','pagina_1',2,'home','<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<properties>\n<property key=\"en\">Page 1-2</property>\n<property key=\"it\">Pagina 1-2</property>\n</properties>\n\n','free',1,NULL),('pagina_2','homepage',3,'home','<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<properties>\n<property key=\"en\">Page 2</property>\n<property key=\"it\">Pagina 2</property>\n</properties>\n\n','free',1,'<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<config>\n  <useextratitles>false</useextratitles>\n</config>\n\n'),('primapagina','service',1,'service','<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<properties>\n<property key=\"it\">Nodo pagine di servizio</property>\n</properties>\n\n\n','free',0,NULL),('service','homepage',1,'service','<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<properties>\n<property key=\"it\">Nodo pagine di servizio</property>\n</properties>\n\n','free',0,NULL);
/*!40000 ALTER TABLE `pages` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `resourcerelations`
--

DROP TABLE IF EXISTS `resourcerelations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `resourcerelations` (
  `resid` varchar(16) NOT NULL,
  `refcategory` varchar(30) DEFAULT NULL,
  KEY `resourcerelations_refcategory_fkey` (`refcategory`),
  KEY `resourcerelations_resid_fkey` (`resid`),
  CONSTRAINT `resourcerelations_refcategory_fkey` FOREIGN KEY (`refcategory`) REFERENCES `categories` (`catcode`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `resourcerelations_resid_fkey` FOREIGN KEY (`resid`) REFERENCES `resources` (`resid`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `resourcerelations`
--

LOCK TABLES `resourcerelations` WRITE;
/*!40000 ALTER TABLE `resourcerelations` DISABLE KEYS */;
INSERT INTO `resourcerelations` VALUES ('44','resource_root'),('44','Image'),('44','resCat1');
/*!40000 ALTER TABLE `resourcerelations` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `resources`
--

DROP TABLE IF EXISTS `resources`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `resources` (
  `resid` varchar(16) NOT NULL,
  `restype` varchar(30) NOT NULL,
  `descr` varchar(260) NOT NULL,
  `maingroup` varchar(20) NOT NULL,
  `xml` longtext NOT NULL,
  `masterfilename` varchar(100) NOT NULL,
  PRIMARY KEY (`resid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `resources`
--

LOCK TABLES `resources` WRITE;
/*!40000 ALTER TABLE `resources` DISABLE KEYS */;
INSERT INTO `resources` VALUES ('22','Image','jAPS Team','free','<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<resource typecode=\"Image\" id=\"22\"><descr>jAPS Team</descr><groups mainGroup=\"free\" /><categories /><masterfile>jAPSTeam.jpg</masterfile><instance><size>3</size><filename>jAPSTeam_d3.jpg</filename><mimetype>image/jpeg</mimetype><weight>2 Kb</weight></instance><instance><size>2</size><filename>jAPSTeam_d2.jpg</filename><mimetype>image/jpeg</mimetype><weight>2 Kb</weight></instance><instance><size>1</size><filename>jAPSTeam_d1.jpg</filename><mimetype>image/jpeg</mimetype><weight>1 Kb</weight></instance><instance><size>0</size><filename>jAPSTeam_d0.jpg</filename><mimetype>image/jpeg</mimetype><weight>9 Kb</weight></instance></resource>\n','jAPSTeam.jpg'),('44','Image','logo','free','<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<resource typecode=\"Image\" id=\"44\"><descr>logo</descr><groups mainGroup=\"free\" /><categories><category id=\"resCat1\" /></categories><masterfile>lvback.jpg</masterfile><instance><size>3</size><filename>lvback_d3.jpg</filename><mimetype>image/jpeg</mimetype><weight>4 Kb</weight></instance><instance><size>2</size><filename>lvback_d2.jpg</filename><mimetype>image/jpeg</mimetype><weight>4 Kb</weight></instance><instance><size>1</size><filename>lvback_d1.jpg</filename><mimetype>image/jpeg</mimetype><weight>2 Kb</weight></instance><instance><size>0</size><filename>lvback_d0.jpg</filename><mimetype>image/jpeg</mimetype><weight>7 Kb</weight></instance></resource>\n','lvback.jpg'),('7','Attach','configurazione','free','<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<resource typecode=\"Attach\" id=\"7\"><descr>configurazione</descr><groups mainGroup=\"free\" /><categories /><masterfile>configurazione.txt</masterfile><instance><size>0</size><filename>configurazione.txt</filename><mimetype>application/msword</mimetype><weight>55 Kb</weight></instance></resource>\n','configurazione.txt'),('82','Image','jAPS','customers','<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<resource typecode=\"Image\" id=\"82\"><descr>jAPS</descr><groups mainGroup=\"customers\" /><categories /><masterfile>jAPS.jpg</masterfile><instance><size>3</size><filename>jAPS_d3.jpg</filename><mimetype>image/jpeg</mimetype><weight>2 Kb</weight></instance><instance><size>2</size><filename>jAPS_d2.jpg</filename><mimetype>image/jpeg</mimetype><weight>2 Kb</weight></instance><instance><size>1</size><filename>jAPS_d1.jpg</filename><mimetype>image/jpeg</mimetype><weight>1 Kb</weight></instance><instance><size>0</size><filename>jAPS_d0.jpg</filename><mimetype>image/jpeg</mimetype><weight>9 Kb</weight></instance></resource>\n','jAPS.jpg');
/*!40000 ALTER TABLE `resources` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `showletcatalog`
--

DROP TABLE IF EXISTS `showletcatalog`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `showletcatalog` (
  `code` varchar(40) NOT NULL,
  `titles` longtext NOT NULL,
  `parameters` longtext,
  `plugincode` varchar(30) DEFAULT NULL,
  `parenttypecode` varchar(40) DEFAULT NULL,
  `defaultconfig` longtext,
  `locked` tinyint(4) NOT NULL,
  PRIMARY KEY (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `showletcatalog`
--

LOCK TABLES `showletcatalog` WRITE;
/*!40000 ALTER TABLE `showletcatalog` DISABLE KEYS */;
INSERT INTO `showletcatalog` VALUES ('90_events','<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<properties>\n<property key=\"en\">Lista contenuti anni \'90</property>\n<property key=\"it\">Lista contenuti anni \'90</property>\n</properties>',NULL,NULL,'content_viewer_list','<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<properties>\n<property key=\"maxElemForItem\">10</property>\n<property key=\"filters\">(order=ASC;attributeFilter=true;end=31/12/1999;key=DataInizio;start=01/01/1990)</property>\n<property key=\"contentType\">EVN</property>\n</properties>',0),('content_viewer','<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<properties>\n<property key=\"en\">Contents - Publish a Content</property>\n<property key=\"it\">Contenuti - Pubblica un Contenuto</property>\n</properties>','<config>\n	<parameter name=\"contentId\">\n		Identificativo del Contenuto\n	</parameter>\n	<parameter name=\"modelId\">\n		Identificativo del Modello di Contenuto\n	</parameter>\n	<action name=\"viewerConfig\"/>\n</config>\n\n','jacms',NULL,NULL,1),('content_viewer_list','<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<properties>\n<property key=\"en\">Contents - Publish a List of Contents</property>\n<property key=\"it\">Contenuti - Pubblica una Lista di Contenuti</property>\n</properties>','<config>\n	<parameter name=\"contentType\">Content Type (mandatory)</parameter>\n	<parameter name=\"modelId\">Content Model</parameter>\n	<parameter name=\"userFilters\">Front-End user filter options</parameter>\n	<parameter name=\"category\">Content Category **deprecated**</parameter>\n	<parameter name=\"categories\">Content Category codes (comma separeted)</parameter>\n	<parameter name=\"maxElemForItem\">Contents for each page</parameter>\n	<parameter name=\"filters\" />\n	<parameter name=\"title_{lang}\">Showlet Title in lang {lang}</parameter>\n	<parameter name=\"pageLink\">The code of the Page to link</parameter>\n	<parameter name=\"linkDescr_{lang}\">Link description in lang {lang}</parameter>\n	<action name=\"listViewerConfig\"/>\n</config>','jacms',NULL,NULL,1),('formAction','<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<properties>\n<property key=\"en\">Internal Servlet</property>\n<property key=\"it\">Invocazione di una Servlet Interna</property>\n</properties>','<config>\n	<parameter name=\"actionPath\">\n		Path relativo di una action o una Jsp\n	</parameter>\n	<action name=\"configSimpleParameter\"/>\n</config>',NULL,NULL,NULL,1),('leftmenu','<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<properties>\n<property key=\"en\">Vertical Navigation Menu</property>\n<property key=\"it\">Menu di navigazione verticale</property>\n</properties>','<config>\n	<parameter name=\"navSpec\">Rules for the Page List auto-generation</parameter>\n	<action name=\"navigatorConfig\" />\n</config>',NULL,NULL,NULL,1),('logic_type','<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<properties>\n<property key=\"en\">Logic type for test</property>\n<property key=\"it\">Tipo logico per test</property>\n</properties>',NULL,NULL,'formAction','<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<properties>\n<property key=\"maxElemForItem\">10</property>\n<property key=\"filters\">(order=ASC;attributeFilter=true;end=31/12/1999;key=DataInizio;start=01/01/1990)</property>\n<property key=\"contentType\">EVN</property>\n</properties>',0),('login_form','<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<properties>\n<property key=\"en\">Showlet di Login</property>\n<property key=\"it\">Showlet di Login</property>\n</properties>',NULL,NULL,NULL,NULL,1),('messages_system','<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<properties>\n<property key=\"en\">System Messages</property>\n<property key=\"it\">Messaggi di Sistema</property>\n</properties>',NULL,NULL,NULL,NULL,1),('search_result','<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<properties>\n<property key=\"en\">Search - Search Result</property>\n<property key=\"it\">Ricerca - Risultati della Ricerca</property>\n</properties>',NULL,'jacms',NULL,NULL,1);
/*!40000 ALTER TABLE `showletcatalog` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `showletconfig`
--

DROP TABLE IF EXISTS `showletconfig`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `showletconfig` (
  `pagecode` varchar(30) NOT NULL,
  `framepos` int(11) NOT NULL,
  `showletcode` varchar(40) NOT NULL,
  `config` longtext,
  `publishedcontent` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`pagecode`,`framepos`),
  KEY `showletconfig_showletcode_fkey` (`showletcode`),
  CONSTRAINT `showletconfig_pagecode_fkey` FOREIGN KEY (`pagecode`) REFERENCES `pages` (`code`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `showletconfig_showletcode_fkey` FOREIGN KEY (`showletcode`) REFERENCES `showletcatalog` (`code`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `showletconfig`
--

LOCK TABLES `showletconfig` WRITE;
/*!40000 ALTER TABLE `showletconfig` DISABLE KEYS */;
INSERT INTO `showletconfig` VALUES ('coach_page',2,'content_viewer','<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<properties>\n<property key=\"contentId\">ART187</property>\n</properties>\n\n','ART187'),('contentview',1,'login_form',NULL,NULL),('contentview',2,'content_viewer',NULL,NULL),('customers_page',2,'content_viewer','<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<properties>\n<property key=\"contentId\">ART111</property>\n</properties>\n\n','ART111'),('customer_subpage_2',2,'content_viewer','<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<properties>\n<property key=\"contentId\">ART112</property>\n</properties>\n\n','ART112'),('homepage',0,'content_viewer_list','<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<properties>\n<property key=\"maxElemForItem\">5</property>\n<property key=\"modelId\">11</property>\n<property key=\"contentType\">NEW</property>\n<property key=\"filters\">(order=DESC;attributeFilter=true;likeOption=false;key=Date)+(order=ASC;attributeFilter=true;likeOption=false;key=Title)</property>\n</properties>',NULL),('homepage',2,'content_viewer','<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<properties>\n<property key=\"modelId\">2</property>\n<property key=\"contentId\">ART1</property>\n</properties>\n\n','ART1'),('pagina_1',2,'leftmenu','<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<properties>\n<property key=\"navSpec\">abs(1).subtree(2)</property>\n</properties>\n\n',NULL),('pagina_11',2,'content_viewer','<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<properties>\n<property key=\"contentId\">ART187</property>\n</properties>\n\n','ART187'),('pagina_2',1,'content_viewer','<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<properties>\n<property key=\"contentId\">ART187</property>\n</properties>\n\n','ART187'),('pagina_2',2,'formAction','<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<properties>\n<property key=\"actionPath\">/do/login</property>\n</properties>\n\n',NULL);
/*!40000 ALTER TABLE `showletconfig` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sysconfig`
--

DROP TABLE IF EXISTS `sysconfig`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sysconfig` (
  `version` varchar(10) NOT NULL,
  `item` varchar(40) NOT NULL,
  `descr` varchar(100) DEFAULT NULL,
  `config` longtext,
  PRIMARY KEY (`version`,`item`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sysconfig`
--

LOCK TABLES `sysconfig` WRITE;
/*!40000 ALTER TABLE `sysconfig` DISABLE KEYS */;
INSERT INTO `sysconfig` VALUES ('test','contentTypes','Definition of the Content Types','<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<contenttypes>\n	<contenttype typecode=\"ART\" typedescr=\"Articolo rassegna stampa\" viewpage=\"contentview\" listmodel=\"11\" defaultmodel=\"1\">\n		<attributes>\n			<attribute name=\"Titolo\" attributetype=\"Text\" indexingtype=\"text\">\n				<validations>\n					<required>true</required>\n				</validations>\n			</attribute>\n			<list name=\"Autori\" attributetype=\"Monolist\">\n				<nestedtype>\n					<attribute name=\"Autori\" attributetype=\"Monotext\" />\n				</nestedtype>\n			</list>\n			<attribute name=\"VediAnche\" attributetype=\"Link\" />\n			<attribute name=\"CorpoTesto\" attributetype=\"Hypertext\" indexingtype=\"text\" />\n			<attribute name=\"Foto\" attributetype=\"Image\" />\n			<attribute name=\"Data\" attributetype=\"Date\" searcheable=\"true\" />\n			<attribute name=\"Numero\" attributetype=\"Number\" searcheable=\"true\" />\n		</attributes>\n	</contenttype>\n	<contenttype typecode=\"EVN\" typedescr=\"Evento\" viewpage=\"contentview\" listmodel=\"51\" defaultmodel=\"5\">\n		<attributes>\n			<attribute name=\"Titolo\" attributetype=\"Text\" searcheable=\"true\" indexingtype=\"text\" />\n			<attribute name=\"CorpoTesto\" attributetype=\"Hypertext\" indexingtype=\"text\" />\n			<attribute name=\"DataInizio\" attributetype=\"Date\" searcheable=\"true\" />\n			<attribute name=\"DataFine\" attributetype=\"Date\" searcheable=\"true\" />\n			<attribute name=\"Foto\" attributetype=\"Image\" />\n			<list name=\"LinkCorrelati\" attributetype=\"Monolist\">\n				<nestedtype>\n					<attribute name=\"LinkCorrelati\" attributetype=\"Link\" />\n				</nestedtype>\n			</list>\n		</attributes>\n	</contenttype>\n	<contenttype typecode=\"RAH\" typedescr=\"Tipo_Semplice\" viewpage=\"contentview\" listmodel=\"126\" defaultmodel=\"457\">\n		<attributes>\n			<attribute name=\"Titolo\" attributetype=\"Text\" indexingtype=\"text\">\n				<validations>\n					<minlength>10</minlength>\n					<maxlength>100</maxlength>\n				</validations>\n			</attribute>\n			<attribute name=\"CorpoTesto\" attributetype=\"Hypertext\" indexingtype=\"text\" />\n			<attribute name=\"Foto\" attributetype=\"Image\" />\n			<attribute name=\"email\" attributetype=\"Monotext\">\n				<validations>\n					<regexp><![CDATA[.+@.+.[a-z]+]]></regexp>\n				</validations>\n			</attribute>\n			<attribute name=\"Numero\" attributetype=\"Number\" />\n			<attribute name=\"Correlati\" attributetype=\"Link\" />\n			<attribute name=\"Allegati\" attributetype=\"Attach\" />\n			<attribute name=\"Checkbox\" attributetype=\"CheckBox\" />\n		</attributes>\n	</contenttype>\n</contenttypes>\n\n'),('test','imageDimensions','Definition of the resized image dimensions','<Dimensions>\n	<Dimension>\n		<id>1</id>\n		<dimx>90</dimx>\n		<dimy>90</dimy>\n	</Dimension>\n	<Dimension>\n		<id>2</id>\n		<dimx>130</dimx>\n		<dimy>130</dimy>\n	</Dimension>\n	<Dimension>\n		<id>3</id>\n		<dimx>150</dimx>\n		<dimy>150</dimy>\n	</Dimension>\n</Dimensions>\n'),('test','langs','Definition of the system languages','<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<Langs>\n  <Lang>\n    <code>it</code>\n    <descr>Italiano</descr>\n    <default>true</default>\n  </Lang>\n  <Lang>\n    <code>en</code>\n    <descr>English</descr>\n  </Lang>\n</Langs>\n\n'),('test','params','Configuration params. Tags other than \"Param\" are ignored','<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<Params>\n	<Param name=\"urlStyle\">classic</Param>\n	<Param name=\"hypertextEditor\">none</Param>\n	<Param name=\"treeStyle_page\">classic</Param>\n	<Param name=\"treeStyle_category\">classic</Param>\n	<Param name=\"startLangFromBrowser\">false</Param>\n	<SpecialPages>\n		<Param name=\"notFoundPageCode\">notfound</Param>\n		<Param name=\"homePageCode\">homepage</Param>\n		<Param name=\"errorPageCode\">errorpage</Param>\n		<Param name=\"loginPageCode\">login</Param>\n	</SpecialPages>\n	<ExtendendPrivacyModule>\n		<Param name=\"extendedPrivacyModuleEnabled\">false</Param>\n		<Param name=\"maxMonthsSinceLastAccess\">6</Param>\n		<Param name=\"maxMonthsSinceLastPasswordChange\">3</Param>        \n	</ExtendendPrivacyModule>\n</Params>'),('test','subIndexDir','Name of the sub-directory containing content indexing files','index');
/*!40000 ALTER TABLE `sysconfig` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `uniquekeys`
--

DROP TABLE IF EXISTS `uniquekeys`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `uniquekeys` (
  `id` int(11) NOT NULL DEFAULT '0',
  `keyvalue` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `uniquekeys`
--

LOCK TABLES `uniquekeys` WRITE;
/*!40000 ALTER TABLE `uniquekeys` DISABLE KEYS */;
INSERT INTO `uniquekeys` VALUES (1,200);
/*!40000 ALTER TABLE `uniquekeys` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `workcontentrelations`
--

DROP TABLE IF EXISTS `workcontentrelations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `workcontentrelations` (
  `contentid` varchar(16) NOT NULL,
  `refcategory` varchar(30) DEFAULT NULL,
  KEY `workcontentrelations_contentid_fkey` (`contentid`),
  KEY `workcontentrelations_refcategory_fkey` (`refcategory`),
  CONSTRAINT `workcontentrelations_contentid_fkey` FOREIGN KEY (`contentid`) REFERENCES `contents` (`contentid`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `workcontentrelations`
--

LOCK TABLES `workcontentrelations` WRITE;
/*!40000 ALTER TABLE `workcontentrelations` DISABLE KEYS */;
INSERT INTO `workcontentrelations` VALUES ('ART179','general'),('ART179','general_cat1'),('ART179','general_cat2'),('ART180','cat1'),('ART180','general'),('ART180','general_cat1'),('ART102','general'),('ART102','general_cat1'),('EVN192','evento'),('EVN192','general'),('EVN192','general_cat1'),('EVN193','evento'),('EVN193','general'),('EVN193','general_cat2');
/*!40000 ALTER TABLE `workcontentrelations` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `workcontentsearch`
--

DROP TABLE IF EXISTS `workcontentsearch`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `workcontentsearch` (
  `contentid` varchar(16) DEFAULT NULL,
  `attrname` varchar(30) NOT NULL,
  `textvalue` varchar(255) DEFAULT NULL,
  `datevalue` date DEFAULT NULL,
  `numvalue` int(11) DEFAULT NULL,
  `langcode` varchar(2) DEFAULT NULL,
  KEY `workcontentsearch_contentid_fkey` (`contentid`),
  CONSTRAINT `workcontentsearch_contentid_fkey` FOREIGN KEY (`contentid`) REFERENCES `contents` (`contentid`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `workcontentsearch`
--

LOCK TABLES `workcontentsearch` WRITE;
/*!40000 ALTER TABLE `workcontentsearch` DISABLE KEYS */;
INSERT INTO `workcontentsearch` VALUES ('ART120','Data',NULL,'2009-03-28',NULL,NULL),('ART120','Numero',NULL,NULL,7,NULL),('ART179','Data',NULL,'2009-07-16',NULL,NULL),('ART1','Data',NULL,'2004-03-10',NULL,NULL),('ART104','Data',NULL,'2007-01-04',NULL,NULL),('ART121','Data',NULL,'2009-03-30',NULL,NULL),('ART121','Numero',NULL,NULL,78,NULL),('ART112','Data',NULL,'2006-02-13',NULL,NULL),('ART111','Data',NULL,'2006-12-13',NULL,NULL),('EVN20','Titolo','Mostra Zootecnica',NULL,NULL,'it'),('EVN20','Titolo','Mostra Zootecnica',NULL,NULL,'en'),('EVN20','DataInizio',NULL,'2006-02-13',NULL,NULL),('EVN20','DataFine',NULL,'2006-02-20',NULL,NULL),('EVN192','Titolo','Titolo B - Evento 2',NULL,NULL,'it'),('EVN192','Titolo','Title B - Event 2',NULL,NULL,'en'),('EVN192','DataInizio',NULL,'1999-04-14',NULL,NULL),('EVN192','DataFine',NULL,'1999-06-14',NULL,NULL),('EVN103','Titolo','Titolo Contenuto 1 Coach',NULL,NULL,'it'),('EVN103','Titolo','Titolo Contenuto 1 Coach',NULL,NULL,'en'),('EVN103','DataInizio',NULL,'1999-04-15',NULL,NULL),('EVN103','DataFine',NULL,'2000-04-14',NULL,NULL),('EVN23','Titolo','Collezione Ingri',NULL,NULL,'it'),('EVN23','Titolo','Collezione Ingri',NULL,NULL,'en'),('EVN23','DataInizio',NULL,'2008-02-13',NULL,NULL),('EVN23','DataFine',NULL,'2008-02-22',NULL,NULL),('EVN24','Titolo','Castello dei bambini',NULL,NULL,'it'),('EVN24','Titolo','Castello dei bambini',NULL,NULL,'en'),('EVN24','DataInizio',NULL,'2009-03-18',NULL,NULL),('EVN24','DataFine',NULL,'2009-03-26',NULL,NULL),('EVN41','Titolo','Sagra della ciliegia',NULL,NULL,'it'),('EVN41','Titolo','Sagra della ciliegia',NULL,NULL,'en'),('EVN41','DataInizio',NULL,'2008-01-06',NULL,NULL),('EVN41','DataFine',NULL,'2008-01-24',NULL,NULL),('EVN21','Titolo','Mostra delle fragole',NULL,NULL,'it'),('EVN21','Titolo','Mostra delle fragole',NULL,NULL,'en'),('EVN21','DataInizio',NULL,'2006-01-13',NULL,NULL),('EVN21','DataFine',NULL,'2006-03-04',NULL,NULL),('EVN25','Titolo','TEATRO DELLE MERAVIGLIE',NULL,NULL,'it'),('EVN25','Titolo','TEATRO DELLE MERAVIGLIE',NULL,NULL,'en'),('EVN25','DataInizio',NULL,'2007-12-12',NULL,NULL),('EVN25','DataFine',NULL,'2007-12-22',NULL,NULL),('EVN191','Titolo','Titolo A - Evento 1',NULL,NULL,'it'),('EVN191','Titolo','Title C - Event 1',NULL,NULL,'en'),('EVN191','DataInizio',NULL,'1996-04-17',NULL,NULL),('EVN191','DataFine',NULL,'1996-06-17',NULL,NULL),('EVN194','Titolo','Titolo D - Evento 4',NULL,NULL,'it'),('EVN194','Titolo','Title A - Event 4',NULL,NULL,'en'),('EVN194','DataInizio',NULL,'2022-02-19',NULL,NULL),('EVN194','DataFine',NULL,'2022-04-19',NULL,NULL),('EVN193','Titolo','Titolo C - Evento 3',NULL,NULL,'it'),('EVN193','Titolo','Title D - Evento 3',NULL,NULL,'en'),('EVN193','DataInizio',NULL,'2017-04-12',NULL,NULL),('EVN193','DataFine',NULL,'2017-09-12',NULL,NULL);
/*!40000 ALTER TABLE `workcontentsearch` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2011-04-25 17:04:53
