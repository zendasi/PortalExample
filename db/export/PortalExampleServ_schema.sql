--
-- PostgreSQL database dump
--

-- Dumped from database version 9.1.2
-- Dumped by pg_dump version 9.1.2
-- Started on 2012-03-13 21:24:36 EDT

SET statement_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

--
-- TOC entry 169 (class 3079 OID 11646)
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: -
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- TOC entry 1905 (class 0 OID 0)
-- Dependencies: 169
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: -
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = true;

--
-- TOC entry 161 (class 1259 OID 40074)
-- Dependencies: 6
-- Name: authgroups; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE authgroups (
    groupname character varying(20) NOT NULL,
    descr character varying(50)
);


--
-- TOC entry 162 (class 1259 OID 40077)
-- Dependencies: 6
-- Name: authpermissions; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE authpermissions (
    permissionname character varying(30) NOT NULL,
    descr character varying(50)
);


--
-- TOC entry 163 (class 1259 OID 40080)
-- Dependencies: 6
-- Name: authrolepermissions; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE authrolepermissions (
    rolename character varying(30) NOT NULL,
    permissionname character varying(30) NOT NULL
);


--
-- TOC entry 164 (class 1259 OID 40083)
-- Dependencies: 6
-- Name: authroles; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE authroles (
    rolename character varying(20) NOT NULL,
    descr character varying(50)
);


--
-- TOC entry 165 (class 1259 OID 40086)
-- Dependencies: 6
-- Name: authusergroups; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE authusergroups (
    username character varying(40) NOT NULL,
    groupname character varying(20) NOT NULL
);


SET default_with_oids = false;

--
-- TOC entry 166 (class 1259 OID 40089)
-- Dependencies: 6
-- Name: authuserroles; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE authuserroles (
    username character varying(40) NOT NULL,
    rolename character varying(20) NOT NULL
);


SET default_with_oids = true;

--
-- TOC entry 167 (class 1259 OID 40092)
-- Dependencies: 6
-- Name: authusers; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE authusers (
    username character varying(40) NOT NULL,
    passwd character varying(40),
    registrationdate date NOT NULL,
    lastaccess date,
    lastpasswordchange date,
    active smallint
);


--
-- TOC entry 168 (class 1259 OID 40095)
-- Dependencies: 6
-- Name: authusershortcuts; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE authusershortcuts (
    username character varying(40) NOT NULL,
    config character varying NOT NULL
);


--
-- TOC entry 1881 (class 2606 OID 40116)
-- Dependencies: 161 161
-- Name: authgroups_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY authgroups
    ADD CONSTRAINT authgroups_pkey PRIMARY KEY (groupname);


--
-- TOC entry 1883 (class 2606 OID 40118)
-- Dependencies: 162 162
-- Name: authpermissions_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY authpermissions
    ADD CONSTRAINT authpermissions_pkey PRIMARY KEY (permissionname);


--
-- TOC entry 1885 (class 2606 OID 40120)
-- Dependencies: 163 163 163
-- Name: authrolepermissions_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY authrolepermissions
    ADD CONSTRAINT authrolepermissions_pkey PRIMARY KEY (rolename, permissionname);


--
-- TOC entry 1887 (class 2606 OID 40122)
-- Dependencies: 164 164
-- Name: authroles_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY authroles
    ADD CONSTRAINT authroles_pkey PRIMARY KEY (rolename);


--
-- TOC entry 1889 (class 2606 OID 40124)
-- Dependencies: 165 165 165
-- Name: authusergroups_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY authusergroups
    ADD CONSTRAINT authusergroups_pkey PRIMARY KEY (username, groupname);


--
-- TOC entry 1891 (class 2606 OID 40126)
-- Dependencies: 166 166 166
-- Name: authuserroles_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY authuserroles
    ADD CONSTRAINT authuserroles_pkey PRIMARY KEY (username, rolename);


--
-- TOC entry 1893 (class 2606 OID 40128)
-- Dependencies: 167 167
-- Name: authusers_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY authusers
    ADD CONSTRAINT authusers_pkey PRIMARY KEY (username);


--
-- TOC entry 1895 (class 2606 OID 40130)
-- Dependencies: 168 168
-- Name: authusershortcuts_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY authusershortcuts
    ADD CONSTRAINT authusershortcuts_pkey PRIMARY KEY (username);


--
-- TOC entry 1896 (class 2606 OID 40131)
-- Dependencies: 1882 163 162
-- Name: authrolepermissions_permissionname_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY authrolepermissions
    ADD CONSTRAINT authrolepermissions_permissionname_fkey FOREIGN KEY (permissionname) REFERENCES authpermissions(permissionname) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 1897 (class 2606 OID 40136)
-- Dependencies: 164 1886 163
-- Name: authrolepermissions_rolename_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY authrolepermissions
    ADD CONSTRAINT authrolepermissions_rolename_fkey FOREIGN KEY (rolename) REFERENCES authroles(rolename) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 1898 (class 2606 OID 40141)
-- Dependencies: 1880 165 161
-- Name: authusergroups_groupname_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY authusergroups
    ADD CONSTRAINT authusergroups_groupname_fkey FOREIGN KEY (groupname) REFERENCES authgroups(groupname) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 1899 (class 2606 OID 40146)
-- Dependencies: 1886 164 166
-- Name: authuserroles_rolename_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY authuserroles
    ADD CONSTRAINT authuserroles_rolename_fkey FOREIGN KEY (rolename) REFERENCES authroles(rolename) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 1904 (class 0 OID 0)
-- Dependencies: 6
-- Name: public; Type: ACL; Schema: -; Owner: -
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


-- Completed on 2012-03-13 21:24:37 EDT

--
-- PostgreSQL database dump complete
--

