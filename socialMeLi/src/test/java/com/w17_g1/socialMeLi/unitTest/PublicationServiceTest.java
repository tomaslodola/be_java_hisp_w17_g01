package com.w17_g1.socialMeLi.unitTest;

import com.w17_g1.socialMeLi.repository.publication.PublicationRepositoryImp;
import com.w17_g1.socialMeLi.repository.user.UserRepositoryImp;
import com.w17_g1.socialMeLi.services.PublicationService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class PublicationServiceTest {
    @Mock
    PublicationRepositoryImp publicationRepository;

    @Mock
    UserRepositoryImp userRepositoryImp;

    @InjectMocks
    PublicationService publicationService;



}
