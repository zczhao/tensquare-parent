package zzc.springcloud.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import zzc.springcloud.pojo.Label;

public interface LabelDao extends JpaRepository<Label, String>, JpaSpecificationExecutor<Label> {

}
