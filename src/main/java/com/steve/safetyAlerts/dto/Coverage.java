package com.steve.safetyAlerts.dto;

import java.util.List;

public class Coverage {
  private List<FirePerson> firePersons;
  private int nombreAdulte;
  private int nombreEnfant;

    public List<FirePerson> getFirePersons() {
        return firePersons;
    }

    public void setFirePersons(List<FirePerson> firePersons) {
        this.firePersons = firePersons;
    }

    public int getNombreAdulte() {
        return nombreAdulte;
    }

    public void setNombreAdulte(int nombreAdulte) {
        this.nombreAdulte = nombreAdulte;
    }

    public int getNombreEnfant() {
        return nombreEnfant;
    }

    public void setNombreEnfant(int nombreEnfant) {
        this.nombreEnfant = nombreEnfant;
    }
}
