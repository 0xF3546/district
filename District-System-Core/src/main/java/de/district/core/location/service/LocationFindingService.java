package de.district.core.location.service;

import de.district.core.economy.location.atm.repository.AtmRepository;
import de.district.core.economy.repository.BankRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LocationFindingService {

    @Autowired
    private BankRepository bankRepository;

    @Autowired
    private AtmRepository atmRepository;

    public void findNearbyBanks(final double radius) {

    }
}
