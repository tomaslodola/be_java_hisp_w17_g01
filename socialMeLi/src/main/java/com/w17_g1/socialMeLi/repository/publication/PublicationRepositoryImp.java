package com.w17_g1.socialMeLi.repository.publication;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.w17_g1.socialMeLi.model.Publication;
import org.springframework.stereotype.Repository;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Repository
public class PublicationRepositoryImp implements IPublicationRepository{

    private List<Publication> publications;

    public PublicationRepositoryImp()
    {
        this.publications = this.loadDataBase();
    }

    @Override
    public List<Publication> loadDataBase()
    {
        List<Publication> publicationList = null;
        File file;
        ObjectMapper objectMapper = new ObjectMapper()
                .configure(SerializationFeature.WRAP_ROOT_VALUE, false) //nueva
                .registerModule(new JavaTimeModule()); // nueva
        TypeReference<List<Publication>> typeRef = new TypeReference<>() {};
        try
        {
            file = ResourceUtils.getFile("classpath:Publications.json");
            publicationList = objectMapper.readValue(file, typeRef);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return publicationList;
    }

    @Override
    public List<Publication> getPublicationByUserId(Integer uid) {
        return publications.stream().filter(p-> Objects.equals(p.getUserId(),uid)).collect(Collectors.toList());
    }

    @Override
    public List<Publication> sortPublicationList(Integer userId,String order)
    {
        List<Publication> publicationByUser = this.getPublicationByUserId(userId);

        if(publicationByUser.size()<2)
            return publicationByUser;

        if(order.equals("date_desc"))
        {
            return publicationByUser.stream().sorted(Comparator.comparing(Publication::getPublishDate).reversed()).collect(Collectors.toList());
        }
        else
        {
            return publicationByUser.stream().sorted(Comparator.comparing(Publication::getPublishDate)).collect(Collectors.toList());
        }
    }
}
