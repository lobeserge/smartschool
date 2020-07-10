package ub.fet.smartschool.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ub.fet.smartschool.model.Result;

@Repository
public interface ResultRepository extends JpaRepository<Result,Long> {
}
