package com.service.FileAndEmailService.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.service.FileAndEmailService.entity.QrtzJobDetail;

@Repository
public interface QrtzJobDetailRepo extends JpaRepository<QrtzJobDetail, String> {

}
