package com.w17_g1.socialMeLi.services;


import com.w17_g1.socialMeLi.dto.output.PublicationDTO;
import com.w17_g1.socialMeLi.repository.publication.IPublicationRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PublicationService {

    @Autowired
    IPublicationRepository publicationRepository;

    private ModelMapper mapper = new ModelMapper();
    public List<PublicationDTO> getPublicationListSorted(Integer userId,String order){
        var publications =  publicationRepository.sortPublicationList(userId,order);

        return publications.stream()
                .map(p->mapper.map(p,PublicationDTO.class))
                .collect(Collectors.toList());
    }
}
