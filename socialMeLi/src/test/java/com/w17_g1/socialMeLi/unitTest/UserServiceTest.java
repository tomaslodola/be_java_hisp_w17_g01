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
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
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
    public void testUS4A(){
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
    public void testUS4B(){
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
    public void testUS3(){
        // Arrange
        MockGetUser();
        MockGetFollowersUsers();
        // Assert
        Assertions.assertThrows(
                ElementNotFoundException.class,
                ()-> userService.sortFollowersList(userId,invalid_order)
        );
    }

    @DisplayName("Verificar que el usuario a seguir exista. (US-0001) caso donde se cumple: ")
    public void  testUS1A(){
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
    public void testUS2B(){
        Mockito.when(userRepository.getUser(1)).thenReturn(Optional.of(UserFactory.createUser()));
        //assert
        assertThrows(ElementNotFoundException.class, ()->userService.followUser(1,2))  ;
    }

    @Test
    @DisplayName("Verificar que el usuario a dejar de seguir exista. (US-0007) caso donde se cumple: ")
    public void testUS7A(){
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
    public void testUS7B(){
        Mockito.when(userRepository.getUser(1)).thenReturn(Optional.of(UserFactory.createUser()));
        //assert
        assertThrows(ElementNotFoundException.class, ()->userService.unfollowUser(1,2))  ;
    }


    @Test
    @DisplayName("Verificar que la cantidad de seguidores de un determinado usuario sea correcta. (US-0002)")
    public void testUS2(){
        //Arrange
        Mockito.when(userRepository.getUser(6)).thenReturn(Optional.of(UserFactory.createJsonUser()));
        //Mockito.when(userRepository.usersFollowedIds(1)).thenReturn(followed);
        //act
        UserCountFollowersDTO obtained = userService.countNumberOfFollowers(6);
        //assert
        assertEquals(new UserCountFollowersDTO(3),obtained);
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
