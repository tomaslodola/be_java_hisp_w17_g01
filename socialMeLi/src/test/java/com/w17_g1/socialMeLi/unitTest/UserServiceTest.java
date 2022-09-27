package com.w17_g1.socialMeLi.unitTest;

import com.w17_g1.socialMeLi.controller.UserController;
import com.w17_g1.socialMeLi.dto.output.MessageResponseDTO;
import com.w17_g1.socialMeLi.dto.output.UserCountFollowersDTO;
import com.w17_g1.socialMeLi.dto.output.UserFollowedOutputListDTO;
import com.w17_g1.socialMeLi.dto.output.UserFollowersOutputListDTO;
import com.w17_g1.socialMeLi.exceptions.ElementNotFoundException;
import com.w17_g1.socialMeLi.factory.UserFactory;
import com.w17_g1.socialMeLi.repository.user.UserRepositoryImp;
import com.w17_g1.socialMeLi.services.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.engine.support.hierarchical.ThrowableCollector;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cglib.proxy.Factory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

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
    @DisplayName("Verificar que el usuario a seguir exista. (US-0001) caso donde se cumple: ")
    public void  test0001(){
        //Arrange
        Mockito.when(userRepository.getUser(2)).thenReturn(Optional.of(UserFactory.createUser()));
        Mockito.when(userRepository.getUser(1)).thenReturn(Optional.of(UserFactory.createUser()));

        //act
        MessageResponseDTO obtained = userService.followUser(1,2);
        //assert
        assertEquals(obtained,new MessageResponseDTO("Se ha seguido al usuario "+ 2 + " con exito."));

    }
    @Test
    @DisplayName("Verificar que el usuario a seguir exista. (US-0001) caso donde no se cumple")
    public void test0001f(){
        Mockito.when(userRepository.getUser(1)).thenReturn(Optional.of(UserFactory.createUser()));
        //assert
        assertThrows(ElementNotFoundException.class, ()->userService.followUser(1,2))  ;
    }


    @Test
    @DisplayName("Verificar que el usuario a dejar de seguir exista. (US-0007) caso donde se cumple: ")
    public void test0002(){
        //Arrange
        Mockito.when(userRepository.getUser(2)).thenReturn(Optional.of(UserFactory.createUser()));
        Mockito.when(userRepository.getUser(1)).thenReturn(Optional.of(UserFactory.createUser()));
        //act
        MessageResponseDTO followFirst = userService.followUser(1,2);
        MessageResponseDTO obtained = userService.unfollowUser(1,2);
        //assert
        assertEquals(obtained, new MessageResponseDTO("Se ha dejado de seguir al usuario " + 2 + " con exito."));
    }
    @Test
    @DisplayName("Verificar que el usuario a dejar de seguir exista. (US-0007)caso donde no se cumple")
    public void test0002f(){
        Mockito.when(userRepository.getUser(1)).thenReturn(Optional.of(UserFactory.createUser()));
        //assert
        assertThrows(ElementNotFoundException.class, ()->userService.unfollowUser(1,2))  ;
    }


    @Test
    @DisplayName("Verificar que la cantidad de seguidores de un determinado usuario sea correcta. (US-0002)")
    public void test0007(){
        //Arrange
        Mockito.when(userRepository.getUser(6)).thenReturn(Optional.of(UserFactory.createJsonUser()));
        //Mockito.when(userRepository.usersFollowedIds(1)).thenReturn(followed);
        //act
        UserCountFollowersDTO obtained = userService.countNumberOfFollowers(6);
        //assert
        assertEquals(new UserCountFollowersDTO(3),obtained);
    }
}
