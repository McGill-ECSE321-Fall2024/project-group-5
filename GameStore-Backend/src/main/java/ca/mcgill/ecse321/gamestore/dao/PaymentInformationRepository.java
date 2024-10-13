package ca.mcgill.ecse321.gamestore.dao;

import ca.mcgill.ecse321.gamestore.model.PaymentInformation;

import org.springframework.data.repository.CrudRepository;

public interface PaymentInformationRepository extends CrudRepository<PaymentInformation, Integer> {

}