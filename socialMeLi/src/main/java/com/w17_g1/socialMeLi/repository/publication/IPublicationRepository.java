package com.w17_g1.socialMeLi.repository.publication;

import com.w17_g1.socialMeLi.model.Publication;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface IPublicationRepository {
    public Optional<Publication> createPublication(Publication publication);
}
