package com.w17_g1.socialMeLi.unitTest;

import com.w17_g1.socialMeLi.model.Publication;
import com.w17_g1.socialMeLi.repository.publication.PublicationRepositoryImp;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static com.w17_g1.socialMeLi.factory.PublicationFactory.createJsonPublication;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class PublicationRepoTest {

    @Autowired
    PublicationRepositoryImp publicationRepository;


    @Test
    @DisplayName("GetFollowed debe devolver solo las lista no expiradas")
    public void test01() {
        // Arrange
        LocalDate seartchAfterDate = LocalDate.of(2022,10,5);
        List<Publication> expected = List.of(createJsonPublication());

        // Act
        List<Publication> obtained = publicationRepository.getPublicationsFromUser(2,seartchAfterDate);

        // Assert
        assertEquals(expected,obtained);
    }

}
