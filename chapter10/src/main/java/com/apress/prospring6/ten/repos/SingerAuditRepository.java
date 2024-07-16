package com.apress.prospring6.ten.repos;

import com.apress.prospring6.ten.entities.SingerAudit;
import com.apress.prospring6.ten.repos.envers.CustomSingerAuditRepository;
import org.springframework.data.jpa.repository.JpaRepository;


public interface SingerAuditRepository extends JpaRepository<SingerAudit, Long>, CustomSingerAuditRepository {

}
