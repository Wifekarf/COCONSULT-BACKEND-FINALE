package com.test.COCONSULT.Reposotories;

import com.test.COCONSULT.Entity.ActivitySalesTeam;
import com.test.COCONSULT.Entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivitySalesTeamRepository extends JpaRepository<ActivitySalesTeam,Long> {
}
