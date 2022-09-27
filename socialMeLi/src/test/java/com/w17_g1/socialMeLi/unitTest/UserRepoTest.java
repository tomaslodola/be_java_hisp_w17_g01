package com.w17_g1.socialMeLi.unitTest;

import com.w17_g1.socialMeLi.exceptions.ElementNotFoundException;
import com.w17_g1.socialMeLi.factory.UserFactory;
import com.w17_g1.socialMeLi.model.User;
import com.w17_g1.socialMeLi.repository.user.UserRepositoryImp;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class UserRepoTest {

    @Autowired
    UserRepositoryImp userRepo;

    private final Integer validID = 6;
    private final Integer invalidID = 999;

    @Test
    @DisplayName("Verificar que el usuario a seguir exista. (US-0001) Caso donde se cumple")
    public void test01(){
        // Arrange
        User expected = UserFactory.createJsonUser();
        // Act
        User obtained = userRepo.getUser(validID);
        // Assert
        assertEquals(expected,obtained);
    }

    @Test
    @DisplayName("Verificar que el usuario a seguir exista. (US-0001) Caso donde no se cumple")
    public void test02(){
        // Assert
        assertThrows(ElementNotFoundException.class, () -> userRepo.getUser(invalidID));
    }

}
