package com.teste.elleve.doacaosangue.model;

public class Request {
    int id;
    String hospital, patient, bloodType;

    public Request( String hospital, String patient, String bloodType) {
        this.hospital = hospital;
        this.patient = patient;
        this.bloodType = bloodType;
    }
    public Request(int id, String hospital, String patient, String bloodType) {
        this.id = id;
        this.hospital = hospital;
        this.patient = patient;
        this.bloodType = bloodType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHospital() {
        return hospital;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
    }

    public String getPatient() {
        return patient;
    }

    public void setPatient(String patient) {
        this.patient = patient;
    }

    public String getBloodType() {

        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }
}
