package org.fpic974.riskservice.model;

import lombok.Builder;

import java.util.List;

@Builder
public class RiskAssessment {
    private int age;

    private char gender;

    private List<String> risks;

    public RiskAssessment(int age, char gender, List<String> risks) {
        this.age = age;
        this.gender = gender;
        this.risks = risks;
    }

    public boolean isNone() {
        return !isBorderLine() &&
                !isDanger() &&
                !isEarlyOnSet();
    }

    public boolean isBorderLine() {
        return age >= 30 && (risks.size() >= 2 && risks.size() <= 5);
    }

    public boolean isDanger() {
        if (isMale()) {
            if (age <= 30) {
                return risks.size() >= 3;
            } else {
                return risks.size() >= 6;
            }
        } else {
            if (age <= 30) {
                return risks.size() >= 4;
            } else {
                return risks.size() >= 7;
            }
        }
    }

    public boolean isEarlyOnSet() {
        if (isMale()) {
            if (age <= 30) {
                return risks.size() >= 5;
            } else {
                return risks.size() >= 8;
            }
        } else {
            if (age <= 30) {
                return risks.size() >= 7;
            } else {
                return risks.size() >= 8;
            }
        }
    }

    public boolean isMale() {
        return gender == 'M';
    }
}
