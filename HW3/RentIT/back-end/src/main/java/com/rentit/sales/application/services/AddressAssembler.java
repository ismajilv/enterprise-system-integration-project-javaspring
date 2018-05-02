package com.rentit.sales.application.services;

import com.rentit.sales.application.dto.AddressDTO;
import com.rentit.sales.domain.model.Address;

import java.util.List;
import java.util.stream.Collectors;

public class AddressAssembler {

    public static AddressDTO toResource(Address address) {
        return AddressDTO.of(address.getHref());
    }

    public static List<AddressDTO> toResources(List<Address> addresses) {
        return addresses
                .stream()
                .map(address -> toResource(address))
                .collect(Collectors.toList());
    }
}
