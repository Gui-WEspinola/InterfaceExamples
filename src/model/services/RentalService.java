package model.services;

import model.entities.Invoice;
import model.entities.CarRental;

public class RentalService {

    private Double pricePerHour;
    private Double pricePerDay;

    private BrazilTaxService taxService;

    public RentalService(Double pricePerHour, Double pricePerDay, BrazilTaxService taxService) {
        this.pricePerHour = pricePerHour;
        this.pricePerDay = pricePerDay;
        this.taxService = taxService;
    }

    public void processInvoice (CarRental carRental){
        long t1 = carRental.getStart().getTime(); //getTime retorna o valor em milissegundos de uma data.
        long t2 = carRental.getFinish().getTime();
        double hours = (double) (t2 - t1) / 1000 / 60 / 60;

        double basicPayment;
        if(hours <= 12.0){
            basicPayment = Math.ceil(hours) * pricePerHour; //math.ceil arredonda para o inteiro cima.
        }
        else {
            basicPayment = Math.ceil(hours / 24) * pricePerDay;
        }

        double tax = taxService.tax(basicPayment);

        carRental.setInvoice(new Invoice(basicPayment, tax));
    }
}
