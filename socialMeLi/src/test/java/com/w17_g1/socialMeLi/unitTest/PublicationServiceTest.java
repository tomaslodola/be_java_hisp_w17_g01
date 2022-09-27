package com.w17_g1.socialMeLi.unitTest;

import com.w17_g1.socialMeLi.exceptions.ElementNotFoundException;
import com.w17_g1.socialMeLi.factory.UserFactory;
import com.w17_g1.socialMeLi.repository.publication.PublicationRepositoryImp;
import com.w17_g1.socialMeLi.repository.user.UserRepositoryImp;
import com.w17_g1.socialMeLi.services.PublicationService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class PublicationServiceTest {
    @Mock
    PublicationRepositoryImp publicationRepository;

    @Mock
    UserRepositoryImp userRepositoryImp;

    @InjectMocks
    PublicationService publicationService;

    @Test
    @DisplayName("")
    public void test01(){
        // Arrange
        Mockito.when(userRepositoryImp.getUser(8)).thenReturn(Optional.of(UserFactory.createUser()));
        Mockito.when(userRepositoryImp.usersFollowedIds(8)).thenReturn(List.of(1,2,3));

        // Act
        // Assert
        //Assertions.assertThrows(ElementNotFoundException.class,
               //() -> publicationService.getLatestPublicationsFromUser(8,"invalid_order"));
    }





}
