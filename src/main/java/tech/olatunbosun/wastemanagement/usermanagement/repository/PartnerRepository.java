package tech.olatunbosun.wastemanagement.usermanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.olatunbosun.wastemanagement.usermanagement.models.Partner;

@Repository
public interface PartnerRepository extends JpaRepository<Partner, Long> {


}
