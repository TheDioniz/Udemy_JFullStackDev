package devopsbuddy.backend.service;

import devopsbuddy.backend.persistance.domain.backend.Plan;
import devopsbuddy.backend.persistance.repositories.PlanRepository;
import devopsbuddy.enums.PlansEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author TheDioniz, created on 25.06.2017.
 */
@Service
@Transactional(readOnly = true)
public class PlanService {

    @Autowired
    private PlanRepository planRepository;

    public Plan findPlanById(int planId) { return planRepository.findOne(planId); }

    @Transactional
    public Plan createPlan(int planId) {

        Plan plan = null;
        if (planId == 1) {
            plan = planRepository.save(new Plan(PlansEnum.BASIC));
        } else if (planId == 2) {
            plan = planRepository.save(new Plan(PlansEnum.PRO));
        } else {
            throw new IllegalArgumentException("Plan id " + planId + " not recognised.");
        }

        return plan;
    }

}
