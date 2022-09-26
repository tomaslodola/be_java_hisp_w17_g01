package com.w17_g1.socialMeLi.unitTest;

import com.w17_g1.socialMeLi.dto.output.UserFollowedOutputListDTO;
import com.w17_g1.socialMeLi.dto.output.UserFollowersOutputListDTO;
import com.w17_g1.socialMeLi.factory.UserFactory;
import com.w17_g1.socialMeLi.repository.user.UserRepositoryImp;
import com.w17_g1.socialMeLi.services.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    UserRepositoryImp userRepository;

    @InjectMocks
    UserService userService;

    @Test
    @DisplayName("Verificar ordenamiento alfabetico followers asc")
    public void test01(){
        // Arrange
        String optionExpected = "name_asc";
        //UserFollowersOutputListDTO responseDTO = UserFactory.createJsonUser();
        // Act
        //Mokito

        // Assert

    }


    @Test
    public void test02(){
       // aSSERMAS
    }



}
