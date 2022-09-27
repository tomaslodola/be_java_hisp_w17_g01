package com.w17_g1.socialMeLi.unitTest;

import com.w17_g1.socialMeLi.dto.output.UserFollowersOutputListDTO;
import com.w17_g1.socialMeLi.exceptions.ElementNotFoundException;
import com.w17_g1.socialMeLi.factory.UserFactory;
import com.w17_g1.socialMeLi.repository.user.UserRepositoryImp;
import com.w17_g1.socialMeLi.services.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
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
public class UserServiceTest {

    @Mock
    UserRepositoryImp userRepository;

    @InjectMocks
    UserService userService;

    private static final Integer userId = 6;
    private static final Integer userFollower1=1;
    private static final Integer userFollower2=2;
    private static final Integer userFollower3=3;
    private static final String asc = "name_asc";
    private static final String desc = "name_desc";
    private static final String invalid_order = "name_";

    @Test
    @DisplayName("Verificar ordenamiento alfabetico followers asc")
    public void test01(){
        // Arrange

        // Obtenemos una lista ordenada de manera ascendente
        UserFollowersOutputListDTO userFollowersExpectedSortedAsc = UserFactory.createUserFollowersOutputListDTOSortAsc();
        MockGetUser();
        MockGetFollowersUsers();

        // Act
        UserFollowersOutputListDTO userActual = userService.sortFollowersList(userId,asc);
        // Assert
        Assertions.assertEquals(userFollowersExpectedSortedAsc,userActual);
    }


    @Test
    @DisplayName("Verificar ordenamiento alfabetico followers desc")
    public void test02(){
        // Arrange
        UserFollowersOutputListDTO userFollowersExpectedSortedDesc = UserFactory.createUserFollowersOutputListDTOSortDesc();
        MockGetUser();
        MockGetFollowersUsers();
        // Act
        UserFollowersOutputListDTO userActual = userService.sortFollowersList(userId,desc);
        // Assert
        Assertions.assertEquals(userFollowersExpectedSortedDesc,userActual);
    }

    @Test
    @DisplayName("Verificar que el tipo de ordenamiento alfabético no exista")
    public void test03(){
        // Arrange
        MockGetUser();
        MockGetFollowersUsers();
        // Assert
        Assertions.assertThrows(
                ElementNotFoundException.class,
                ()-> userService.sortFollowersList(userId,invalid_order)
        );
    }

    private void MockGetUser(){
        // Mockeamos el usuario obtenido para realizar el ordenamiento
        Mockito.when(userRepository.getUser(userId)).thenReturn(Optional.of(UserFactory.createJsonUser()));
    }

    private void MockGetFollowersUsers(){
        // Mockeamos los usuarios que sigue al usuario principal
        Mockito.when(userRepository.getUser(userFollower1)).thenReturn(Optional.of(UserFactory.user1()));
        Mockito.when(userRepository.getUser(userFollower2)).thenReturn(Optional.of(UserFactory.user2()));
        Mockito.when(userRepository.getUser(userFollower3)).thenReturn(Optional.of(UserFactory.user3()));
    }


}
